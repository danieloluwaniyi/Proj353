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

/**
 *
 * @author Suguru
 */
@FacesValidator("passwordMatchChecker")
public class PasswordMatchChecker implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String passwordConfirmation = value.toString();
        
        UIInput uiInputPassword = (UIInput) component.getAttributes().get("password");
        String password = null;
        try {
	password = uiInputPassword.getSubmittedValue().toString();
        } catch (NullPointerException ne) {
            System.out.println("NullPointerException was caught");
        }
        
        if (passwordConfirmation == null || passwordConfirmation.isEmpty() || password == null || password.isEmpty()) {
            return;
        }
        
        if (!passwordConfirmation.equals(password)) {
            uiInputPassword.setValid(false);
            throw new ValidatorException(new FacesMessage(
            "Password must match confirm password."));
        }        
        
    }
}