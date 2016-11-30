/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.ProfileDAO;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Suguru
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
    
    @Override
        public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String email = value.toString();
        
        ProfileDAO aProfileDAO = new ProfileDAO();
        boolean val = aProfileDAO.checkEmailValidity(email);
        boolean val2 = aProfileDAO.checkEmailExists(email);
        if (!aProfileDAO.checkEmailValidity(email)) {
            throw new ValidatorException (new FacesMessage (
            "Invalid email. Check the input again."));
        } else if (aProfileDAO.checkEmailExists(email)) {
            throw new ValidatorException (new FacesMessage (
            "This email is taken. Use another one."));
        }
    }
}
