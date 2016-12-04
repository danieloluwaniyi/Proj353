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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public CartController() {
        
        this.order = new Order();
        cartDAO = new CartDAO();
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
        
        this.order.setCart(cart);
        FacesContext fc = FacesContext.getCurrentInstance();;
        Map<String,String> params =
                fc.getExternalContext().getRequestParameterMap();
	 String userID = params.get("userId");
        cartDAO.placeOrder(userID, order, total);
        
        return "orderConfirmation.xhtml?faces-redirect=true";
        
        
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    

}
