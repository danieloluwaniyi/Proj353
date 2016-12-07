package model;

import dao.ProfileDAO;
import java.util.Map;
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
@FacesValidator("oldPassUpdateChecker")
public class OldPassChecker implements Validator {

    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO = new ProfileDAO();

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String oldPass = value.toString();
           
        FacesContext fc = FacesContext.getCurrentInstance();

        Map<String, Object> params = fc.getExternalContext().getSessionMap();
        Object profile = params.get("profile");
        String userID = ((Profile) profile).getUserID();

        if (oldPass == null || oldPass.isEmpty()) {
            return;
        }
        

        if (!profileDAO.checkPasswordExists(userID, oldPass)) {
            throw new ValidatorException(new FacesMessage(
                    "The current password does not match."));

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