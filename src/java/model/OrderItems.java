/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author daniel
 */
@ManagedBean
public class OrderItems {
    private Submission item;
    private int quantity;

    public Submission getItem() {
        return item;
    }

    public void setItem(Submission item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
}
