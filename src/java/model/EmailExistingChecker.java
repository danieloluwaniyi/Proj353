package model;

import dao.ProfileDAO;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
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
@FacesValidator("emailExistingChecker")
public class EmailExistingChecker implements Validator {
    
    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO = new ProfileDAO();
    
    @Override
        public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String email = value.toString();
                
        if (!profileDAO.checkEmailExists(email)) {
            throw new ValidatorException (new FacesMessage (
            "There is no registration under this email. Use another email."));
        }
    }

    /**
     * @return the profileDAO
     */
    public ProfileDAO getProfileDAO() {
        if (profileDAO == null) {
            profileDAO = new ProfileDAO();
        }
        return profileDAO;
    }

    /**
     * @param profileDAO the profileDAO to set
     */
    public void setProfileDAO(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }
    
}
