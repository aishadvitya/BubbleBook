package com.iss.bubble.services;

import com.iss.bubble.ejb.BubbleEJB;
import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.Discussion;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * REST Web Service
 *
 * @author aishwarya
 */
@Path("/allDiscussions/{userId}")
@RequestScoped
public class DiscussionService {

    @Context
    private UriInfo context;
    @PersistenceContext
    EntityManager em;
    @EJB
    BubbleEJB bub;
    @EJB
    UserEJB user;

    /**
     * Creates a new instance of EventDescriptionService
     */
    public DiscussionService() {
    }

    /**
     * Retrieves representation of an instance of
     * com.cream.crawler.service.EventDescriptionService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<Discussion> getJson(@PathParam("userId") String userId) {

        List<Discussion> lstDiscussions = (List<Discussion>) bub.getAllDiscussions(Integer.parseInt(userId));
        for (Discussion d : lstDiscussions) {
            d.getUser().setPassword("*********");
        }
        return lstDiscussions;

    }

    /**
     * PUT method for updating or creating an instance of
     * EventDescriptionService
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {

    }
}
