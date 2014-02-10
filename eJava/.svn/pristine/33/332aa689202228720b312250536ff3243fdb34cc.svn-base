/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;


import com.iss.bubble.ejb.BubbleEJB;
import com.iss.bubble.ejb.DiscussionEJB;
import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.Bubble;
import com.iss.bubble.entity.BubbleAV;
import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.Discussion;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author Sathish
 */

@Named("aVBubble")
@RequestScoped
public class AVBubble implements Serializable{
    
    @EJB
    BubbleEJB bubbleEjb;
    @EJB
    DiscussionEJB discussionEjb;
    @EJB
    UserEJB userEjb;
    
    private boolean editable;
    private String title;
    private String url;
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public void render() {
        this.editable = true;
    }
    
    public AVBubble(){
        this.editable = true;
        this.title="Messi";
        this.url="http://www.youtube.com/v/KZnUr8lcqjo";
    }
    
    private int width = 420;
    private int height = 315;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }

    public void reloadUrl(){
        System.out.println("Called settile"+ this.title);
       
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        System.out.println("Setting url " + url);

         if (url.contains("v=")){
            System.out.println("Converting link format --> ");
            String vid = url.split("v=")[1];
            this.url = "http://www.youtube.com/v/" + vid;
        }else
            this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
        public void add() {
        System.out.println("Trying to persist AVBubble ---> ");
        editable = false;
        BubbleBookUser user = userEjb.findUserByID(1);
        Discussion discussion = discussionEjb.findDiscussionByID(1);

        BubbleAV bubble = new BubbleAV(url);
        
        bubble.setBubbleContent(title);
        bubble.setBubbleType("AV");
        
        bubble.setCreateTimestamp(new Date());
        bubble.setDiscussionId(discussion);
        bubble.setUserId(user);
        
        System.out.println("Contents of BubbleAV ---> " + bubble);
      //  bubbleEjb.saveBubble(bubble);
    }
    
}
