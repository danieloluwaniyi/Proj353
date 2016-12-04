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
@FacesValidator("passwordUpdateChecker")
public class PasswordUpdateChecker implements Validator {

    private Pattern pattern;
    private Matcher matcher;
    @ManagedProperty("#{profileDAO}")
    private ProfileDAO profileDAO = new ProfileDAO();
    private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[--@#$%]).{6,20})";

    public PasswordUpdateChecker() {
        pattern = Pattern.compile(PASS_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String confirmPassword = value.toString();
        
        UIInput uiInputPassword = (UIInput) component.getAttributes().get("password");
        String inputPass = null;
        try {
	inputPass = uiInputPassword.getSubmittedValue().toString();
        } catch (NullPointerException ne) {                
            System.out.println("NullPointerException was caught");
            uiInputPassword.setValid(false);
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return;
        }

        matcher = pattern.matcher(confirmPassword);
        if (!matcher.matches()) {
            throw new ValidatorException(new FacesMessage(
                    "The password does not match the requirements:<br />- 6 to 20 characters<br />- Has at least one digit, one uppler case letter, lower case letter<br />- One special special symbol (\"@#$%-\")"));
        }
        
//        Not working well.
//        if (getProfileDAO().checkPasswordExists(password)) {
//            throw new ValidatorException(new FacesMessage(
//                    "New password has to be different."));
//        }
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