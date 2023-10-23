// https://github.com/wso2/identity-apps/blob/master/apps/myaccount/src/api/multi-factor-fido.ts
const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

const lookup = new Uint8Array(256);

for (let i = 0; i < chars.length; i++) {
    lookup[chars.charCodeAt(i)] = i;
}

function encodeBase64(arraybuffer) {
    var bytes = new Uint8Array(arraybuffer),
        i, len = bytes.length, base64 = "";

    for (i = 0; i < len; i += 3) {
        base64 += chars[bytes[i] >> 2];
        base64 += chars[((bytes[i] & 3) << 4) | (bytes[i + 1] >> 4)];
        base64 += chars[((bytes[i + 1] & 15) << 2) | (bytes[i + 2] >> 6)];
        base64 += chars[bytes[i + 2] & 63];
    }

    if ((len % 3) === 2) {
        base64 = base64.substring(0, base64.length - 1);
    } else if (len % 3 === 1) {
        base64 = base64.substring(0, base64.length - 2);
    }

    return base64;
}

function decodeBase64(base64) {
    let bufferLength = base64.length * 0.75,
        len = base64.length, i, p = 0,
        encoded1, encoded2, encoded3, encoded4;

    let arraybuffer = new ArrayBuffer(bufferLength),
        bytes = new Uint8Array(arraybuffer);

    for (i = 0; i < len; i += 4) {
        encoded1 = lookup[base64.charCodeAt(i)];
        encoded2 = lookup[base64.charCodeAt(i + 1)];
        encoded3 = lookup[base64.charCodeAt(i + 2)];
        encoded4 = lookup[base64.charCodeAt(i + 3)];

        bytes[p++] = (encoded1 << 2) | (encoded2 >> 4);
        bytes[p++] = ((encoded2 & 15) << 4) | (encoded3 >> 2);
        bytes[p++] = ((encoded3 & 3) << 6) | (encoded4 & 63);
    }

    return arraybuffer;
}

// TODO: Decipher this...
function responseToObject(response) {
    if (response.u2fResponse) {
        return response;
    } else {
        let clientExtensionResults = {};

        try {
            clientExtensionResults = response.getClientExtensionResults();
        } catch (e) {
            // No need to show UI errors here.
            // Add debug logs here one a logger is added.
            // Tracked here https://github.com/wso2/product-is/issues/11650.
        }

        if (response.response.attestationObject) {
            return {
                // tslint:disable-next-line:object-literal-sort-keys
                clientExtensionResults,
                id: response.id,
                response: {
                    attestationObject: encodeBase64(
                        response.response.attestationObject
                    ),
                    clientDataJSON: encodeBase64(response.response.clientDataJSON)
                },
                type: response.type
            };
        } else {
            return {
                // tslint:disable-next-line:object-literal-sort-keys
                clientExtensionResults,
                id: response.id,
                response: {
                    authenticatorData: encodeBase64(
                        response.response.authenticatorData
                    ),
                    clientDataJSON: encodeBase64(response.response.clientDataJSON),
                    signature: encodeBase64(response.response.signature),
                    userHandle:
                        response.response.userHandle &&
                        encodeBase64(response.response.userHandle)
                },
                type: response.type
            };
        }
    }
};

async function createCredentials(request) {
    // Decode IDs in excludeCredentials
    const excludeCredentials = request.excludeCredentials.map(
        (credential) => {
            return { ...credential, id: decodeBase64(credential.id) };
        }
    );

    // Decode challenge and user id
    const publicKeyCredentialCreationOptions = {
        ...request,
        attestation: "direct",
        challenge: decodeBase64(request.challenge),
        excludeCredentials,
        user: {
            ...request.user,
            id: decodeBase64(request.user.id)
        }
    };

    // Invoke CTAP to create a credential
    return await navigator.credentials.create({
        publicKey: publicKeyCredentialCreationOptions
    });
}

async function registerFinish(requestId, credential) {
    // Create the payload, containing the request's ID and the generated credential
    const payload = {
        requestId: requestId,
        credential: responseToObject(credential)
    };

    // Call the backend to finish registration
    let result = await fetch("/register/finish", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(payload)
    });

    return await result.json();
}

async function registerStart() {
    // Call the backend to initiate registration
    let result = await fetch("/register/start", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    return await result.json();
}

async function fidoFlow() {
    // Initiate FIDO creation
    const request = await registerStart();

    // Ask user to generate a credential
    const credential = await createCredentials(request?.publicKeyCredentialCreationOptions);

    // Finish the creation
    return await registerFinish(request?.requestId, credential);
}