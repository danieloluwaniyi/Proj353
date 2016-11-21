/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Suguru
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%-]).{6,20})";

    public PasswordValidator() {
        pattern = Pattern.compile(PASS_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String password = value.toString();
        
        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes().get("passwordConfirmation");
	String confirmPassword = uiInputConfirmPassword.getSubmittedValue().toString();

        Profile aProfile = new Profile();
        if (aProfile.checkPassword(password)) {
            throw new ValidatorException(new FacesMessage(
                    "Weak password"));
        }
        
        if (password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            return;
        }
        
        
        if (!password.equals(confirmPassword)) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException(new FacesMessage(
            "Password must match confirm password."));
        }
        
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException (new FacesMessage(
            "The password does not match the requirements:\n- 6 to 20 characters\n- Has at least one digit, one uppler case letter, lower case letter\n- One special special symbol (\"@#$%-\")"));
        }
        
    }
}
