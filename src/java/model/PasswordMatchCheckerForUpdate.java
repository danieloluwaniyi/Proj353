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
@FacesValidator("passwordMatchCheckerForUpdate")
public class PasswordMatchCheckerForUpdate implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String confirmPassword = value.toString();
                
        UIInput uiInputPassword = (UIInput) component.getAttributes().get("password");
        String password = null;
        
        try {
        password = uiInputPassword.getSubmittedValue().toString();
        } catch (NullPointerException ne) {                
            System.out.println("NullPointerException was caught");
            uiInputPassword.setValid(false);
        }
        
        
            if (confirmPassword == null || confirmPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            return;
        }

        
        if (!confirmPassword.equals(password)) {
            uiInputPassword.setValid(false);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "info", "Password must match confirm password."));
            throw new ValidatorException(new FacesMessage(
            "Password must match confirm password."));
        }        
        
    }
}