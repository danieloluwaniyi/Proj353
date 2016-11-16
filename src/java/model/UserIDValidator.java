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

@FacesValidator("userIDValidator")
public class UserIDValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String user = value.toString();
        
        ProfileDAO aProfileDAO = new ProfileDAO();
        boolean val = aProfileDAO.CheckUserExists(user);
        if (aProfileDAO.CheckUserExists(user)) {
            throw new ValidatorException (new FacesMessage (
            "User ID is used already"));
        }
    }
}