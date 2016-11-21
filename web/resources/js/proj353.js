var form = document.egtElementByID("freeSingupForm");
var pass = form.password.value;
var passConf = form.passwordConf;

pass.onblur = function() {
    alert("!");
    return checkPassStrength();
}

passConf.onblur = function() {
    return checkPasswordMatch(form);
}

function checkPassStrength(form) {
    var containsDigit = false;
    
//    var pass = document.getElementById("password").value;
    alert("pass with getElementById: " + pass);
    
    
    var pass = form[form.id + ":password"].value;
    alert("pass: " + pass);
    
    var message = form[form.id + ":passwordMessage"].value;
        
    for (i = 0; i < pass.length; i++) {
        if (isDigit(pass.charAt(i)))
            containsDigit = true;
        if (pass.length >= 6 && containsDigit && pass.indexOf("@" || "#" || "$" || "%") != -1) {
            message.innerHTML = "Password is strong";
            return true;
        } else {
            message.innerHTML = "Password is weak";
            return false;
        }
    }
}

function isDigit(num) {
    alert("Here");
    if (num.length < 1) {
        return false;
    }
    var string = "1234567890";
    if (string.indexOf(num) != -1) {
        return true;
    }
    return false;
}

function checkPasswordMatch(form) {
    var pass = form[form.id + ":password"].value;
    var passConf = form[form.id + ":passwordConf"].value;
//    alert("pass: " + pass);
//    alert("passConf: " + passConf);
    if (pass == passConf) {
        return true;
    } else {
        alert("Password and password confirm fields don't match.");
        return false;
    }
}
