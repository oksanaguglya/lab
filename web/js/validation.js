function validateForm() {
    var x = document.forms["log"]["login"].value;
    var y = document.forms["log"]["password"].value;
    if (x == null || x == "") {
        alert("Name must be filled out");
        return false;
    }
    if (y == null || y == "") {
        alert("Password must be filled out");
        return false;
    }
}

function validateRegisterForm() {
    var x = document.forms["reg"]["login"].value;
    var y = document.forms["reg"]["password"].value;
    var z = document.forms["reg"]["repeatPassword"].value;
    if (x == null || x == "") {
        alert("Name must be filled out");
        return false;
    }
    if (y == null || y == "") {
        alert("Password must be filled out");
        return false;
    }
    if (z == null || z == "") {
        alert("The second password must be filled out");
        return false;
    }
}
