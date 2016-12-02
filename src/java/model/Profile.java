/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.TreeMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

/**
 *
 * @author Daniel, Suguru, Sneh
 */
public class Profile {

    private String firstName;
    private String lastName;
    private String userID;
    private String email;
    private String password;
    private String passwordConf;
    private boolean paid = false;
    private String nameOnCard;
    private String creditCardNum;
    private String securityCode;
    private int expirationMonth;
    private int expirationYear;
    private TreeMap<String, Integer> months = new TreeMap<>();
    private ArrayList<Integer> years = new ArrayList<>();
    private boolean loggedIn;

    {
        for (int i = 1; i <= 12; i++) {
            String label;
            if (i < 10) {
                label = "0" + i;
                months.put(label, i);
            } else {
                label = Integer.toString(i);
                months.put(label, i);
            }
        }

        for (int i = 2016; i < 2130; i++) {
            getYears().add(i);

        }
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the userName
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userName to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the passwordConf
     */
    public String getPasswordConf() {
        return passwordConf;
    }

    /**
     * @param passwordConf the passwordConf to set
     */
    public void setPasswordConf(String passwordConf) {
        this.passwordConf = passwordConf;
    }

    /**
     * @return the paid
     */
    public boolean getPaid() {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * @return the nameOnCard
     */
    public String getNameOnCard() {
        return nameOnCard;
    }

    /**
     * @param nameOnCard the nameOnCard to set
     */
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    /**
     * @return the creditCardNum
     */
    public String getCreditCardNum() {
//        String retVal = null;
//        String num = creditCardNum;
//        int length = 0;
//        try {
//        length = creditCardNum.length();
//        } catch (NullPointerException ne) {
//            System.out.println("NullPointerException was caught");
//        }
//        try {
//            if (creditCardNum.length() < 5) {
//                retVal = this.creditCardNum;
//            } else if (creditCardNum.length() > 4 && creditCardNum.length() <= 8) {
//                retVal = creditCardNum.substring(0, 5) + " " + creditCardNum.substring(5);
//            } else if (creditCardNum.length() > 8 && creditCardNum.length() <= 12) {
//                retVal = creditCardNum.substring(0, 5) + " " + creditCardNum.substring(5, 9) + " " + creditCardNum.substring(9);
//            } else if (creditCardNum.length() > 12 && creditCardNum.length() <= 16) {
//                retVal = creditCardNum.substring(0, 5) + " " + creditCardNum.substring(5, 9) + " " + creditCardNum.substring(9, 13) + " " + creditCardNum.substring(13);
//            }
//        } catch (NullPointerException ne) {
//            return creditCardNum;
//        }
        return creditCardNum;
    }

    /**
     * @param creditCardNum the creditCardNum to set
     */
    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    /**
     * @return the securityCode
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * @param securityCode the securityCode to set
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * @return the expirationMont
     */
    public int getExpirationMonth() {
        return expirationMonth;
    }

    /**
     * @param expirationMonth the expirationMont to set
     */
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    /**
     * @return the expirationYear
     */
    public int getExpirationYear() {
        return expirationYear;
    }

    /**
     * @param expirationYear the expirationYear to set
     */
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    /**
     * @return the months
     */
    public TreeMap<String, Integer> getMonths() {
        return months;
    }

    /**
     * @param months the months to set
     */
    public void setMonths(TreeMap<String, Integer> months) {
        this.months = months;
    }

    /**
     * @return the years
     */
    public ArrayList<Integer> getYears() {
        return years;
    }

    /**
     * @param years the years to set
     */
    public void setYears(ArrayList<Integer> years) {
        this.years = years;
    }

    boolean checkPassword(String password) {
        boolean retVal = false;

//        Pattern checkRegex = Pattern.compile("[A-Za-z0-9_@#$%]{2,}");
//        Matcher regexMatcher = checkRegex.matcher(password);
//        
//        while (regexMatcher.find()) {
//            if (regexMatcher.group().length() != 0) {
//                retVal = true;
//            }
//        }
        if (password.length() < 7) {
            retVal = false;
        }
        if (password.indexOf("ABCDEFGHIJKLMNOPQRSTUVWXYZ") != -1) {
            retVal = false;
        }
        if (password.indexOf("@") != -1 || password.indexOf("#") != -1 || password.indexOf("$") != -1 || password.indexOf("%") != -1) {
            retVal = false;
        }
        return retVal;
    }

    boolean checkPassMatch(String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}