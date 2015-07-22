function validateForm() {
    var x = document.forms["log"]["login"].value;
    var y = document.forms["log"]["password"].value;
    if (x == null || x == "") {
        alert("Login must be filled out!");
        return false;
    }
    if (y == null || y == "") {
        alert("Password must be filled out!");
        return false;
    }
}

function validateRegisterForm() {
    var x = document.forms["reg"]["login"].value;
    var y = document.forms["reg"]["password"].value;
    var z = document.forms["reg"]["repeatPassword"].value;
    if (x == null || x == "") {
        alert("Login must be filled out!");
        return false;
    }
    if(x.length > 20){
        alert("Login is too long!Input not more than 20 symbols!");
        return false;
    }
    if (y == null || y == "") {
        alert("Password must be filled out!");
        return false;
    }
    if(y.length > 20){
        alert("Password is too long!Input not more than 20 symbols!");
        return false;
    }
    if (z == null || z == "") {
        alert("The repeated password must be filled out!");
        return false;
    }
    if (!(z == y)) {
        alert("The repeated password is not equals to password!");
        return false;
    }
}

function validateBookForm() {
    var x = document.forms["editorBook"]["title"].value;
    var y = document.forms["editorBook"]["author"].value;
    var z = document.forms["editorBook"]["year"].value;
    var q = document.forms["editorBook"]["quantity"].value;
    if (x == null || x == "") {
        alert("Title must be filled out!");
        return false;
    }
    if(x.length > 45){
        alert("Title is too long!Input not more than 45 symbols!");
        return false;
    }
    if (y == null || y == "") {
        alert("Author must be filled out!");
        return false;
    }
    if(y.length > 45){
        alert("Author is too long!Input not more than 45 symbols!");
        return false;
    }
    if (z == null || z == "") {
        alert("Year must be filled out!");
        return false;
    }
    var yearRegex = /^[12][0-9]{3}$/;
    if(!z.match(yearRegex)){
        alert("Incorrect year!");
        return false;
    }
    if (q == null || q == "") {
        alert("Quantity must be filled out!");
        return false;
    }
    var digitRegex = /^[0-9]+$/;
    if(!q.match(digitRegex)){
        alert("Quantity is not a number!");
        return false;
    }
    if (q > 1000) {
        alert("Incorrect quantity!");
        return false;
    }
}

