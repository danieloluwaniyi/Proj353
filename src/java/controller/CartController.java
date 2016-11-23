/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProfileDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Order;
import model.Profile;
import model.Submission;

/**
 *
 * @author vyass
 */
@ManagedBean
@SessionScoped
public class CartController {

    private Profile profile;
    private ProfileDAO profiledao;
    private Order order;
    private Submission sub;

    public boolean addToCart()throws Exception  {
        boolean retVal = false;
        try {
            int[] list = order.getCart();
            int cartLength;
            cartLength = list.length - 1;
            list[cartLength] = sub.getSubmissionId();
            retVal = true;
        }
        catch(Exception e){ System.out.println("error");}
        return retVal;
    }

    public String plcaeOrder(){
        String retStr = "Something is wrong in processing the order";
        
            
        
        return retStr;
    }
    
}
