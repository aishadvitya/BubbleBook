/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.ejb;

import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.GroupTable;
import com.iss.bubble.entity.GroupTablePK;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sathish
 */
@PermitAll
@Stateless
public class UserEJB {

    @PersistenceContext
    EntityManager em;

    public List<BubbleBookUser> getAllUsers() {
        Query queryForAllUsers = em.createNamedQuery("BubbleBookUser.findAll", BubbleBookUser.class);
        return (List<BubbleBookUser>) queryForAllUsers.getResultList();
    }

    public BubbleBookUser findUserByID(Integer key) {
        return em.find(BubbleBookUser.class, key);
    }

    public boolean emailExists(String email) {
        System.out.println("email :" + email);
        List<BubbleBookUser> results = em.createNamedQuery("BubbleBookUser.findByEmail").setParameter("email", email).getResultList();
        if (results == null || results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public BubbleBookUser findUserByEmail(String email) {
        List<BubbleBookUser> results = em.createNamedQuery("BubbleBookUser.findByEmail").setParameter("email", email).getResultList();
        if (results == null) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public BubbleBookUser isAuthenticated(String email, String password) {

        List<BubbleBookUser> results = em.createNamedQuery("BubbleBookUser.findByEmail").setParameter("email", email).getResultList();

        if (results.isEmpty()) {
            System.err.println("Unable to find email id " + email);
            return null;
        } else {
            BubbleBookUser user = results.get(0);
            if (user.getPassword().equals(password)) {
                System.out.println("Email, password authenticated. " + email);
                return user;
            } else {
                return null;
            }
        }
    }

    public Boolean saveBookUser(BubbleBookUser user, String role) {
        try {
            System.out.println("Trying to persist BubbleBookUser " + user);
            em.persist(user);
            GroupTable gpt = new GroupTable(new GroupTablePK(role, user.getEmail()));
            em.persist(gpt);
            return true;
        } catch (Exception e) {
            System.err.println("Unable to persist BubbleBookUser " + user);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean updateBookUser(BubbleBookUser user) {
        try {
            System.out.println("Trying to merge BubbleBookUser " + user);
            em.merge(user);
            return true;
        } catch (Exception e) {
            System.err.println("Unable to merge BubbleBookUser " + user);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<BubbleBookUser> retrieveFriendUsersByUserID(BubbleBookUser bbUser) {
        for (BubbleBookUser bbUser2 : bbUser.getBubbleBookUserCollection1()) {
            System.out.println("INSIDE 2 :" + bbUser2.toString());
        }
        return (List<BubbleBookUser>) bbUser.getBubbleBookUserCollection1();
    }

    @RolesAllowed("ADMIN")
    public boolean removeBookUser(BubbleBookUser user) {
        try {
            System.out.println("Trying to remove BubbleBookUser " + user);
            em.remove(user);
            return true;
        } catch (Exception e) {
            System.err.println("Unable to remove BubbleBookUser " + user);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<GroupTable> getAllGroups() {
        return em.createNamedQuery("GroupTable.findAll", GroupTable.class).getResultList();
    }

    public List<GroupTable> getGroupByUserEmail(String email) {
        return em.createNamedQuery("GroupTable.findByEmail", GroupTable.class).setParameter("email", email).getResultList();
    }
}
