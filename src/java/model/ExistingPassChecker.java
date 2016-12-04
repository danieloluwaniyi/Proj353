package model;

import dao.ProfileDAO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
@ManagedBean
@SessionScoped
@FacesValidator("existingPassChecker")
public class ExistingPassChecker implements Validator {

    @ManagedProperty("#{profile}")
    private Profile profile;
    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String oldPass = value.toString();
        
//        Not working well.
        if (getProfileDAO().checkPasswordExists(oldPass)) {
            throw new ValidatorException(new FacesMessage("New password has to be different."));
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

    /**
     * @return the profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}