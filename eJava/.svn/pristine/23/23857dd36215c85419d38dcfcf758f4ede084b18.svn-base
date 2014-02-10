/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.iss.bubble.ejb.BubbleEJB;
import com.iss.bubble.ejb.DiscussionEJB;
import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.Bubble;
import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.BubbleMap;
import com.iss.bubble.entity.Discussion;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Vincent
 */
@Named(value = "textBubble")
@RequestScoped
public class TextBubble {


    private Bubble persistedEntityText;

    public Bubble getPersistedEntityText() {
        return persistedEntityText;
    }

    public void setPersistedEntityText(Bubble persistedEntityText) {
        this.persistedEntityText = persistedEntityText;
    }
    private boolean editable;
    private String text;

    public TextBubble() {
        editable = true;
    }

    public boolean getEditable() {
        return editable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void render() {
        editable = true;
    }


}
