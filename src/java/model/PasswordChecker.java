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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Suguru
 */
@FacesValidator("passwordChecker")
public class PasswordChecker implements Validator {

    @ManagedProperty("#{profileDAO}")
    ProfileDAO profileDAO;
//    private Pattern pattern;
//    private Matcher matcher;
//    private static final String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%-]).{6,20})";
//
//    public PasswordChecker() {
//        pattern = Pattern.compile(PASS_PATTERN);
//    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String password = value.toString();

        if (password == null || password.isEmpty()) {
            return;
        }        

//        matcher = pattern.matcher(password);
//        if (!matcher.matches()) {
//            throw new ValidatorException(new FacesMessage(
//                    "The password does not match the requirements:<br />- 6 to 20 characters<br />- Has at least one digit, one uppler case letter, lower case letter<br />- One special special symbol (\"@#$%-\")"));
//        }

    }
}