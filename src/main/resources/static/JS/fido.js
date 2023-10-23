
async function registerStart() {
    let result = await fetch("/register/start", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    console.log(result);

    return result;
}