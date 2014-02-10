/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.iss.bubble.ejb.UserEJB;

import com.iss.bubble.entity.BubbleBookUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Sathish
 */
@Named
@SessionScoped
public class ContactsBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    UserEJB userEjb;

    private List<BubbleBookUser> myContacts;
    private List<BubbleBookUser> selectedContacts;

    public ContactsBean() {
    }

    @PostConstruct
    public void initialize() {
        System.out.println("init contacts");
        reset();
        //BubbleBookUser bbUser = (BubbleBookUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bubblebookuser");
    }

    public void fetchMyContacts(BubbleBookUser bbUser) {
        // myContacts = userEjb.retrieveFriendUsersByUserID(bbUser);
        myContacts = userEjb.getAllUsers();
        myContacts.remove(bbUser);
        System.out.println("mycont :" + myContacts.size());
    }

    public void reset() {
        myContacts = new ArrayList<>();
        selectedContacts = new ArrayList<>();
    }

    public List<BubbleBookUser> getSelectedContacts() {
        return selectedContacts;
    }

    public void setSelectedContacts(List<BubbleBookUser> selectedContacts) {
        this.selectedContacts = selectedContacts;
    }

    public List<BubbleBookUser> getMyContacts() {
        return myContacts;
    }

    public void setMyContacts(List<BubbleBookUser> myContacts) {
        this.myContacts = myContacts;
    }

    public void addToFilteredContacts() {
        StringBuilder sb = new StringBuilder();
        System.out.println("sel " + selectedContacts.size());
        for (BubbleBookUser b : selectedContacts) {
            System.out.println("neam:" + b.toString());
            if (!sb.toString().isEmpty()) {
                sb.append(",");
            }
            sb.append(b.getName());
        }
        if (sb.length() > 0) {
            FacesMessage msg = new FacesMessage("Added ", sb.toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void clearSelectedContacts(){
        selectedContacts.clear();
    }
}
