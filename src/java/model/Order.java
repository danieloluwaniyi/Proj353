/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author vyass
 */
public class Order {

    private String userID;
    private List cart;
    private int orderID;
    private String nameOnCard;
    private String creditCardNum;
    private String securityCode;
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

        for (int i = 2016; i < 2130; i++) {
            getYears().add(i);

        }
    }

    public Order() {

    }

    public Order(String userID, List<OrderItems> cart) {
        this.cart = cart;
        this.userID = userID;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List getCart() {
        return cart;
    }

    public void setCart(List cart) {
        this.cart = cart;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

}
