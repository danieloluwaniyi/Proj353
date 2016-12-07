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
import dao.CartDAO;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.OrderItems;


/**
 *
 * @author vyass
 */
@ManagedBean(name="sp")
@SessionScoped
public class CartController {

    private Profile profile;
    private ProfileDAO profiledao;
    private Order order;
    private Submission sub;
    private CartDAO cartDAO;
    private List <OrderItems>cart = new ArrayList<>();
    private double total;
    private String formattedtotal;

    public CartController() {
        
        NumberFormat formatter = new DecimalFormat("#0.00");     
        
        this.order = new Order();
        cartDAO = new CartDAO();
        this.formattedtotal = formatter.format(total);
    }
    
    
    
    public String addtoCart(Submission s)
    {
        //Increment duplicate product
        for(OrderItems item:cart){
            if(item.getItem().getSubmissionId()== s.getSubmissionId()){
                item.setQuantity(item.getQuantity()+1);
                return "cart.xhtml?faces-redirect=true";
            }
        }
        
        //Create new cart item
        OrderItems i = new OrderItems();
        i.setQuantity(1);
        i.setItem(s);
        cart.add(i);
        return "cart.xhtml?faces-redirect=true";
        
    }
    
    public String checkIfEmpty(){
        String retVal = "checkout.xhtml?faces-redirect=true";
        if(cart.isEmpty()){
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Oops you can't buy if you dont have any products buddy!"));

            retVal = "";
            
        }
        
        return retVal;
    }
    
    public List getCart() {
        return cart;
    }

    public void setCart(List cart) {
        this.cart = cart;
    }

    public double getTotal() {
        total=0;
        for(OrderItems item:cart){
            total=total+(item.getQuantity()*item.getItem().getPrice());
        }
        
       
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void remove(OrderItems i){
        for(OrderItems item: cart){
            if(item.equals(i)){
                cart.remove(item);
                break;
            }
        }
    }
    
    public void update(){
        //Skeleton to update page
    }
    
    public String processOrder() throws SQLException{
        //remember to check if cart is empty
        
        if(cart.isEmpty()){
            return "cart.xhtml";
        }
        
        this.order.setCart(cart);
        FacesContext fc = FacesContext.getCurrentInstance();;
        Map<String,String> params =
                fc.getExternalContext().getRequestParameterMap();
	 String userID = params.get("userId");
        cartDAO.placeOrder(userID, order, total);
        cart.clear();
        
        return "orderConfirmation.xhtml?faces-redirect=true";
        
        
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getFormattedtotal() {
        
        NumberFormat formatter = new DecimalFormat("#0.00"); 
        this.formattedtotal= formatter.format(getTotal());
        return formattedtotal;
    }

    public void setFormattedtotal(String formattedtotal) {
             
        NumberFormat formatter = new DecimalFormat("#0.00"); 
        this.formattedtotal= formatter.format(getTotal());
        
    }
    

}
