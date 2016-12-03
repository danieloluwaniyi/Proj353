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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;

/**
 *
 * @author Suguru
 */
@ManagedBean
@SessionScoped
@FacesValidator("passwordChecker")
public class PasswordChecker implements Validator {

    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String password = value.toString();
        UIInput uiUserID = (UIInput) component.getAttributes().get("userID");
        
        String userID = null;
        try {
            uiUserID.getSubmittedValue().toString();
        } catch (NullPointerException ne) {
            throw new ValidatorException(new FacesMessage("Null was caught"));
        }

        if (password == null || password.isEmpty()) {
            throw new ValidatorException(new FacesMessage("It's null"));
        }
        
        if (!profileDAO.checkPasswordExists(userID, password)) {
            throw new ValidatorException(new FacesMessage("User ID and Password doeesn't match or the ID doesn't exist."));
        }
        
    }

    /**
     * @return the profileDAO
     */
    public ProfileDAO getProfileDAO() {
        return profileDAO;
    }

    /**
     * @param profileDAO the profileDAO to set
     */
    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }
}