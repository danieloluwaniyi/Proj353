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
@FacesValidator("emailMatchChecker")
public class EmailMatchChecker implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String email = value.toString();
        UIInput uiInputConfEmail = (UIInput) component.getAttributes().get("emailConfirmation");
        String confEmail = null;
        try {
            confEmail = uiInputConfEmail.getSubmittedValue().toString();
        } catch (NullPointerException ne) {
            System.out.println("NullPointerException was caught");
        }

        if (email != null && confEmail != null) {
            if (!email.equals(confEmail)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "info", "Password must match confirm password."));
                throw new ValidatorException(new FacesMessage(
                        "Email must match the confirm email."));
            }
        }

    }
}
