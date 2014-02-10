package com.iss.bubble.services;

import com.iss.bubble.ejb.DiscussionEJB;
import com.iss.bubble.entity.Bubble;
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
import javax.persistence.Query;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author aishwarya
 */
@Path("/allBubbles/{discussionId}")
@RequestScoped
public class BubblesService {

    @Context
    private UriInfo context;
    @PersistenceContext
    EntityManager em;

    @EJB
    DiscussionEJB bubble;

    /**
     * Creates a new instance of EventDescriptionService
     */
    public BubblesService() {
    }

    /**
     * Retrieves representation of an instance of
     * com.cream.crawler.service.EventDescriptionService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<Bubble> getJson(@PathParam("discussionId") String discussionId, @QueryParam("fetchSize") String fetchSize, @QueryParam("cursor") String cursor) {

        Discussion d = bubble.findDiscussionByID(Integer.parseInt(discussionId));
        Query query = em.createNamedQuery("Bubble.findByDiscussionId");
        query.setParameter("discussionId", d);
//          query.setMaxResults(Integer.parseInt(fetchSize));
//          query.setFirstResult(0);

        List<Bubble> b = query.getResultList();
        if (b != null) {
            for (Bubble bb : b) {
                bb.getUserId().setPassword("*******");
            }
        }
        return b;
//     List<Bubble> lstDiscussions=(List<Bubble>)
//      for(Discussion d:lstDiscussions){
//         d.getUser().setPassword("*********");
//         }     
//    return lstDiscussions;
//    

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
