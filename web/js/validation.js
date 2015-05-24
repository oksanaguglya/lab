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

