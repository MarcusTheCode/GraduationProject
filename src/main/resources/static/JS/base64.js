/**
 * Copyright (c) 2019, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// https://github.com/wso2/identity-apps/blob/master/apps/myaccount/src/api/multi-factor-fido.ts

// Setup lookup tables
const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
const lookup = new Uint8Array(256);
for (let i = 0; i < chars.length; i++) {
    lookup[chars.charCodeAt(i)] = i;
}

/**
 * Converts an ArrayBuffer to a base64 encoded string
 * @param {ArrayBuffer} arraybuffer The array to encode
 * @returns {string} The base64 encoded string
 */
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

/**
 * Converts a base64 encoded string to an ArrayBuffer
 * @param {string} base64 The string to decode
 * @returns {ArrayBuffer} The decoded ArrayBuffer
 */
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