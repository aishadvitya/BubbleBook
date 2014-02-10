/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.ejb;

import com.iss.bubble.entity.Bubble;
import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.BubbleHierarchy;
import com.iss.bubble.entity.Discussion;
import java.util.Collection;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sathish
 */
@Stateless
@RolesAllowed({"ADMIN", "USER"})
public class BubbleEJB {

    @PersistenceContext
    EntityManager em;

    public Bubble saveBubble(Bubble child, Bubble parent) {
        try {
            System.out.println("Save Bubble");
            child = em.merge(child);
            em.flush();
            System.out.println("Child id" + child.getBubbleId());
            if (parent != null) {
                System.out.println("");
                parent = em.find(Bubble.class, parent.getBubbleId());
                child.setBubbleHierarchy(new BubbleHierarchy(child, parent));
                em.merge(child);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return child;
    }

    //crappy code 
    private BubbleHierarchy saveHierarchyBubble(BubbleHierarchy b) {
        try {
            BubbleHierarchy bubble = em.merge(b);
            System.out.println("inside persist" + b.toString());
            return bubble;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Collection<Bubble> retrieveBubbles(int discussionId) {

        Discussion dis = em.find(Discussion.class, discussionId);//        
        em.refresh(dis);
//        
        System.out.println("dis id " + dis.getDiscussionId());
        System.out.println("tit " + dis.getTitle());
        List<Bubble> bubbles = (List<Bubble>) dis.getBubbleCollection();
        System.out.println("bub size " + bubbles.size());
        return dis.getBubbleCollection();
    }

    public Bubble findBubble(Integer bubbleId) {
        Bubble bubble = em.find(Bubble.class, bubbleId);
        System.out.println("cont " + bubble.getBubbleContent());
        return bubble;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Collection<Discussion> getAllDiscussions(int userID) {
        BubbleBookUser user = em.find(BubbleBookUser.class, userID);
        if (null == user) {
            return null;
        }
        System.out.println("get all discussion" + user.getBubbleBookUserCollection().size());
        return user.getDiscussionCollection();
    }
}
