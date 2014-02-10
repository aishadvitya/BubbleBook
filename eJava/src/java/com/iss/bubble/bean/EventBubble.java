package com.iss.bubble.bean;    

import com.iss.bubble.ejb.BubbleEJB;
import com.iss.bubble.ejb.DiscussionEJB;
import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.BubbleEvent;
import com.iss.bubble.entity.Discussion;
import java.io.Serializable;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class EventBubble implements Serializable {
    @EJB BubbleEJB eventEjb;
    @EJB UserEJB userEJB;
    @EJB DiscussionEJB discussionEjb;
    private BubbleEvent bubble;
    private boolean isCreated=false;

    public boolean isIsCreated() {
        return isCreated;
    }

    public void setIsCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }
    
    public BubbleEvent getBubble() {
        return bubble;
        
    }

    public void setBubble(BubbleEvent bubble) {
        this.bubble = bubble;
    }
    private String eventTitle;
    private String eventDesc;
    private Date startDateTime;
    private Date endDateTime;
    private int goingCount=0;
    private int mayBeCount=0;
    private  int notGoing=0;

   String response=" ";
    public EventBubble() {
  bubble=new BubbleEvent();
     }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(int goingCount) {
        bubble.setGoingCount(bubble.getGoingCount()+1);
       goingCount=(bubble!=null)?bubble.getGoingCount():0;
    }

    public int getMayBeCount() {
        return mayBeCount;
    }

    public void setMayBeCount(int mayBeCount) {
        bubble.setMaybeCount(bubble.getMaybeCount()+1);
         mayBeCount=(bubble!=null)?bubble.getMaybeCount():0;
    }

    public int getNotGoing() {
        return notGoing;
    }

    public void setNotGoing(int notGoing) {
        bubble.setNotgoingCount(bubble.getNotgoingCount()+1);
        notGoing=(bubble!=null)?bubble.getNotgoingCount():0;
    }

       public void respond(BubbleEvent be){
       System.out.println("REsponse"+be.getBubbleId());
        if(be.getResponse().equals("1")){
              
              be.setGoingCount(be.getGoingCount()+1);
        }
        
        if(be.getResponse().equals("2"))
        {
              be.setNotgoingCount(be.getNotgoingCount()+1);
        }
        
        if(be.getResponse().equals("3"))
         {
             be.setMaybeCount(be.getMaybeCount()+1);
         }
               if(null!=bubble){ 
                   System.out.println("New values:"+bubble.getGoingCount()+","+bubble.getMaybeCount()+","+bubble.getNotgoingCount());
                   eventEjb.saveBubble(be,null);
               }
    }
    public void createEntityToPersist() {
        try {
           
            BubbleBookUser user = userEJB.findUserByID(1);
            Discussion discussion = discussionEjb.findDiscussionByID(1);
            bubble.setDiscussionId(discussion);
            bubble.setUserId(user);
            bubble.setBubbleType("BUBBLE_EVENT");
            bubble.setCreateTimestamp(new Date());
           bubble=(BubbleEvent) eventEjb.saveBubble(bubble,null);
            isCreated=true;
            System.out.println(bubble.getBubbleId()+"gggg");
            }
            
         catch (Exception ex) {
            Logger.getLogger(EventBubble.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
