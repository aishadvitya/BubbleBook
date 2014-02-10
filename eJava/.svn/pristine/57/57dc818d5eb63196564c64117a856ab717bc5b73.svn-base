/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.BubbleBookUser;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sathish
 */
@FacesConverter(forClass = BubbleBookUser.class, value = "contact")
public class ContactConverter implements Converter {

    @EJB
    UserEJB userEJB;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            System.out.println("getAsObject:" + value);
            int number = Integer.parseInt(value);
            return userEJB.findUserByID(number);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            System.out.println("csd1:" + ((BubbleBookUser) value).getUserId());
            return String.valueOf(((BubbleBookUser) value).getUserId());
        }
    }
}
