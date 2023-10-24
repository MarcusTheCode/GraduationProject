function switchToEditMode(button, credentialId) {
    const row = button.parentNode.parentNode;
    const nameCell = row.querySelector("#deviceName"); //finds element with id deviceName in the row
    const oldName = nameCell.innerText;

    const inputElement = document.createElement("input");
    inputElement.type = "text";
    inputElement.value = oldName;

    const saveButton = document.createElement("button");
    saveButton.innerHTML = "Save";
    saveButton.onclick = function() {
        const newName = inputElement.value;

       saveDevice(credentialId, newName).then(r => {});

        nameCell.innerText = newName;
    };

    nameCell.innerHTML = '';
    nameCell.appendChild(inputElement);
    nameCell.appendChild(saveButton)

}

async function saveDevice(credentialId, newName) {
    return await fetch("fidoDevices/editDevice/" + credentialId + "/" + newName, {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
}