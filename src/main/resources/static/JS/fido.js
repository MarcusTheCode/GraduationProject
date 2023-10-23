const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

/**
 * Use a lookup table to find the index.
 * @type {Uint8Array}
 */
const lookup = new Uint8Array(256);

for (let i = 0; i < chars.length; i++) {
    lookup[chars.charCodeAt(i)] = i;
}

function decode(base64) {
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

async function registerStart() {
    let result = await fetch("/register/start", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    const jsonResponse = await result.json();

    console.log(jsonResponse);

    const request = jsonResponse.publicKeyCredentialCreationOptions;

    const excludeCredentials = request.excludeCredentials.map(
        (credential) => {
            return { ...credential, id: decode(credential.id) };
        }
    );

    const publicKeyCredentialCreationOptions = {
        ...request,
        attestation: "direct",
        challenge: decode(request.challenge),
        excludeCredentials,
        user: {
            ...request.user,
            id: decode(request.user.id)
        }
    };

    console.log(publicKeyCredentialCreationOptions);

    const credential = await navigator.credentials.create({
        publicKey: publicKeyCredentialCreationOptions
    });

    console.log(credential);

    return credential;
}