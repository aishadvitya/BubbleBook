/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.ejb;

import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.Discussion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sathish
 */
@Stateless
@RolesAllowed({"ADMIN", "USER"})
public class DiscussionEJB {

    @PersistenceContext
    EntityManager em;

    public DiscussionEJB() {
    }

    public List<Discussion> retrieveDiscussionsByUsersID(BubbleBookUser user) {
        List<Discussion> discussions = em.createNamedQuery("Discussion.findDiscussionByUserId").setParameter("user", user).getResultList();
        orderByTimeLine(discussions);
        return discussions;
    }

    private void orderByTimeLine(List<Discussion> discussions) {
        if (discussions != null) {
            if (discussions.size() > 0) {
                Collections.sort(discussions, new Comparator<Discussion>() {
                    @Override
                    public int compare(Discussion d1, Discussion d2) {
                        return d1.getCreateTimestamp().compareTo(d2.getCreateTimestamp());
                    }
                });
                Collections.reverse(discussions);;
            }
        }
    }

    public List<Discussion> retrieveParticipantDiscussionsByUsersID(BubbleBookUser user) {
        String queryString = "select d.DISCUSSION_ID from EJAVA.DISCUSSION_PARTICIPANTS d where d.USER_ID=?";
        Query query = em.createNativeQuery(queryString);
        query.setParameter(1, user.getUserId());
        List<Integer> discussionIds = (List<Integer>) query.getResultList();
        List<Discussion> discussions = new ArrayList<>();
        if (discussionIds.size() > 0) {
            discussions = retrieveDiscussionsByID(discussionIds);
        }
        orderByTimeLine(discussions);
        return discussions;
    }

    @RolesAllowed("ADMIN")
    public List<Discussion> retrieveAllDiscussions() {
        List<Discussion> discussions = em.createNamedQuery("Discussion.findAll", Discussion.class).getResultList();
        orderByTimeLine(discussions);
        return discussions;
    }

    public List<Discussion> retrieveDiscussionsByID(List<Integer> discussionIds) {
        TypedQuery<Discussion> query = em.createNamedQuery("Discussion.findAllByDiscussionIds", Discussion.class);
        query.setParameter("discussionIds", discussionIds);
        List<Discussion> discussions = query.getResultList();
        return discussions;
    }

    /**
     *
     * @param participantList
     * @param d
     */
    public void addUsersToDiscussion(List<BubbleBookUser> participantList, Discussion d) {
        d.setBubbleBookUserCollection(participantList);
        System.out.println("participant list edit " + participantList);
        System.out.print("disc id" + d.getDiscussionId());
        em.merge(d);
    }

    public Discussion findDiscussionByID(Integer key) {
        return em.find(Discussion.class, key);
    }

    public void saveDiscussion(Discussion d) {
        try {
            em.merge(d);
            System.out.println("inside persist" + d.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @RolesAllowed("ADMIN")
    public void removeDiscussion(Discussion d) {
        try {
            em.remove(d);
            System.out.println("inside remove" + d.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
