$("#title").keypress(function (e) {
    return e.keyCode != 13;
});

$("#editForm").on("submit", function () {
    let nameInput = document.getElementById("title");
    let desInput = document.getElementById("description");
    let nameInputValue = nameInput.value.replace(/\s+/g, ' ').trim();
    if (nameInputValue.length == 0 || nameInputValue.toString().length == 0 || nameInputValue.toString().length > 100) {
        return false;
    } else {
        $("#title").val(nameInputValue);
    }
    if (desInput.value.length == 0 || desInput.value.toString().trim().length == 0 || desInput.value.toString().trim().length > 100000) {
        return false;
    }
    //trim for address
    let addressInputValue = document.getElementById("address");
    $("#address").val(addressInputValue.value.replace(/\s+/g, ' ').trim());
    return true;
});

$("#createForm").on("submit", function () {
    let nameInput = document.getElementById("title");
    let desInput = document.getElementById("description");
    let alertNameMes = document.getElementById("alertName");
    let alertDesMes = document.getElementById("alertDes");
    let nameInputValue = nameInput.value.replace(/\s+/g, ' ').trim();
    if (nameInputValue.length == 0 || nameInputValue.toString().length == 0 || nameInputValue.toString().length > 100) {
        alertNameMes.innerHTML = "Name must not be empty, not whitespace-only, not longer than 100 characters";
        return false;
    } else {
        $("#title").val(nameInputValue);
    }
    if (desInput.value.length == 0 || desInput.value.toString().trim().length == 0 || desInput.value.toString().trim().length > 100000) {
        alertDesMes.innerHTML = "Description must not be empty, not whitespace only, not longer than 100.000 characters";
        return false;

    }
    return true;
});

$(document).ready(function () {
    let title = $("#title");
    title.on('input', function () {
        console.log(title.val());
        let nameEdit = title.val().replace(/\s+/g, ' ').trim(),
            alertNameMes = document.getElementById("alertName");
        if (nameEdit.length == 0 || nameEdit.toString().trim().length == 0 || nameEdit.toString().trim().length > 100) {
            alertNameMes.innerHTML = "Name must not be empty, not whitespace-only, not longer than 100 characters";
        } else {
            alertNameMes.innerHTML = "";
        }
    })
})

$(document).ready(function () {
    let title = $("#description");
    title.on('input', function () {
        console.log(title.val());
        let desEdit = title.val(),
            alertDesMes = document.getElementById("alertDes");
        if (desEdit.length == 0 || desEdit.toString().trim().length == 0 || desEdit.toString().trim().length > 100000) {
            alertDesMes.innerHTML = "Description must not be empty, not whitespace only, not longer than 100.000 characters";
        } else {
            alertDesMes.innerHTML = "";
        }
    })
})
