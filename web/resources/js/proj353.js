function checkpassword(form) {
  var pass = form.password.getValue;

  if (pass.length < 6) {
    alert("Password needs to be at least 6 characters.")
    return false;
  }
  if (pass.indexOf(""))



}

function checkPasswordMatch(form) {
  var pass = form.password.getValue;
  var passConf = form.passwordConf.getValue;
  if (pass != passConf) {
    return false;
  }
}