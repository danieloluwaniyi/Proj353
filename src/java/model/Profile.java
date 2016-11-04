/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Daniel, Suguru, Sneh
 */
public class Profile {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private boolean paid = false;
    private String nameOnCard;
    private Integer creditCardNum;
    private int expirationMonth;
    private int expirationYear;
    private TreeMap<String, Integer> months = new TreeMap<>();
    private ArrayList<Integer> years = new ArrayList<>();
    
    
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
//        months.put("01", 1);
//        months.put("02", 2);
//        months.put("03", 3);
//        months.put("04", 4);
//        months.put("05", 5);
//        months.put("06", 6);
//        months.put("07", 7);
//        months.put("08", 8);
//        months.put("09", 9);
//        months.put("10", 10);
//        months.put("11", 11);
//        months.put("12", 12);
        
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
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
    public Integer getCreditCardNum() {
        return creditCardNum;
    }

    /**
     * @param creditCardNum the creditCardNum to set
     */
    public void setCreditCardNum(Integer creditCardNum) {
        this.creditCardNum = creditCardNum;
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
    
    

}
