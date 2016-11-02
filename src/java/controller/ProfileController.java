/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.ProfileBean;

/**
 *
 * @author Suguru, Daniel, Sneh
 */
@ManagedBean
@SessionScoped
public class ProfileController {
    
    private ProfileBean profileBean;
    
    public ProfileController() {
        profileBean = new ProfileBean();
    }
}
