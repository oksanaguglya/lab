function validateForm() {
    var login = document.forms["log"]["login"].value;
    var password = document.forms["log"]["password"].value;
    if (login == null || login == "") {
        alert("Login must be filled out!");
        return false;
    }
    if (password == null || password == "") {
        alert("Password must be filled out!");
        return false;
    }
}

function validateRegisterForm() {
    var login = document.forms["reg"]["login"].value;
    var password = document.forms["reg"]["password"].value;
    var repeatPassword = document.forms["reg"]["repeatPassword"].value;
    var lengthRegex = /^([a-zA-Z\u0430-\u044f\u0410-\u044F_-]{8,20})$/;
    if (login == null || login == "") {
        alert("Login must be filled out!");
        return false;
    }
    if(!login.match(lengthRegex)){
        alert("Login is too long!Input not more than 20 symbols!");
        return false;
    }
    if (password == null || password == "") {
        alert("Password must be filled out!");
        return false;
    }
    if(password.match(lengthRegex)){
        alert("Password is too long!Input not more than 20 symbols!");
        return false;
    }
    if (repeatPassword == null || repeatPassword == "") {
        alert("The repeated password must be filled out!");
        return false;
    }
    if (!(repeatPassword == password)) {
        alert("The repeated password is not equals to password!");
        return false;
    }
}

function validateBookForm() {
    var title = document.forms["editorBook"]["title"].value;
    var author = document.forms["editorBook"]["author"].value;
    var year = document.forms["editorBook"]["year"].value;
    var quantity = document.forms["editorBook"]["quantity"].value;
    var lengthRegex = /^([a-zA-Z\u0430-\u044f\u0410-\u044F_-]{1,45})$/;
    if (title == null || title == "") {
        alert("Title must be filled out!");
        return false;
    }
    if(!title.match(lengthRegex)){
        alert("Title is too long!Input not more than 45 symbols!");
        return false;
    }
    if (author == null || author == "") {
        alert("Author must be filled out!");
        return false;
    }
    if(!author.match(lengthRegex)){
        alert("Author is too long!Input not more than 45 symbols!");
        return false;
    }
    if (year == null || year == "") {
        alert("Year must be filled out!");
        return false;
    }
    var yearRegex = /^[^0][0-9]{0,3}$/;
    if(!year.match(yearRegex)){
        alert("Incorrect year!");
        return false;
    }
    if (quantity == null || quantity == "") {
        alert("Quantity must be filled out!");
        return false;
    }
    var digitRegex = /^[0-9]+$/;
    if(!quantity.match(digitRegex)){
        alert("Quantity is not a number!");
        return false;
    }
}

