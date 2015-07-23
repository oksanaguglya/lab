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
    var lengthRegex = /^([a-zA-Z0-9_-]{8,20})$/;
    if (login == null || login == "") {
        alert("Login must be filled out!");
        return false;
    }
    if(!login.match(lengthRegex)){
        alert("Incorrect login format (input 8-20 latin symbols, 0-9 or _-)!");
        return false;
    }
    if (password == null || password == "") {
        alert("Password must be filled out!");
        return false;
    }
    if(!password.match(lengthRegex)){
        alert("Incorrect password format (input 8-20 latin symbols, 0-9 or _-)!");
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
    var lengthRegex = /^([a-zA-Z\u0430-\u044F\u0410-\u042F0-9_-]{1,45})$/;
    var yearRegex = /^[1-2][0-9]{3}$/;
    var digitRegex = /^[0-9]+$/;
    if (title == null || title == "") {
        alert("Title must be filled out!");
        return false;
    }
    if(!title.match(lengthRegex)){
        alert("Incorrect title format (input 1-45 symbols, 0-9 or _-)!");
        return false;
    }
    if (author == null || author == "") {
        alert("Author must be filled out!");
        return false;
    }
    if(!author.match(lengthRegex)){
        alert("Incorrect author format (input 1-45 symbols, 0-9 or _-)!");
        return false;
    }
    if (year == null || year == "") {
        alert("Year must be filled out!");
        return false;
    }
    if(!year.match(yearRegex)){
        alert("Incorrect year!");
        return false;
    }
    if (quantity == null || quantity == "") {
        alert("Quantity must be filled out!");
        return false;
    }
    if(!quantity.match(digitRegex)){
        alert("Incorrect quantity!");
        return false;
    }
}

