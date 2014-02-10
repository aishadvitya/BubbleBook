/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.iss.bubble.ejb.BubbleEJB;
import com.iss.bubble.ejb.DiscussionEJB;
import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Sathish
 */
@Named
@ViewScoped
public class MapBubble implements Serializable {

    @EJB
    BubbleEJB bubbleEjb;
    @EJB
    DiscussionEJB discussionEjb;
    @EJB
    UserEJB userEjb;
    private BubbleMap persistedEntity;

    public BubbleMap getPersistedEntity() {
        return persistedEntity;
    }

    public void setPersistedEntity(BubbleMap persistedEntity) {
        this.persistedEntity = persistedEntity;
    }
    private String title;
    private LatLng coord;
    private String comment;
    private String place;
    private boolean editable;
    private MapModel simpleModel;
    private Marker marker;

    public MapBubble() {
        simpleModel = new DefaultMapModel();
        //Shared coordinates  
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        //      LatLng coord4 = new LatLng(36.885233, 30.702323);
        coord = coord3;
        //Basic marker  
        place = "Chennai,Tamil Nadu";
        editable = true;
//        simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki")); 
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LatLng getCoord() {
        return coord;
    }

    public void setCoord(LatLng coord) {
        this.coord = coord;
    }

    public void updateMap() {
        final Geocoder geocoder = new Geocoder();
        LatLng coord2= new LatLng(0, 0);;
        List<GeocoderResult> results = new ArrayList<GeocoderResult>();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(place).setLanguage("en").getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        if(geocoderResponse!=null)
        {
        results = geocoderResponse.getResults();
        coord2 = new LatLng(results.get(0).getGeometry().getLocation().getLat().floatValue(), results.get(0).getGeometry().getLocation().getLng().floatValue());
        }
        
        coord = coord2;
        simpleModel.addOverlay(new Marker(coord2, place));
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void onStateChange(StateChangeEvent event) {
        LatLngBounds bounds = event.getBounds();
        int zoomLevel = event.getZoomLevel();

        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Zoom Level", String.valueOf(zoomLevel)));
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Center", event.getCenter().toString()));
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "NorthEast", bounds.getNorthEast().toString()));
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "SouthWest", bounds.getSouthWest().toString()));
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Selected", "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng()));
    }

    public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getTitle()));
    }

    public Marker getMarker() {
        return marker;
    }

    public void addMarker(PointSelectEvent actionEvent) {
        simpleModel.getMarkers().clear();
        Marker marker = new Marker(actionEvent.getLatLng(), place);
        simpleModel.addOverlay(marker);
        coord = actionEvent.getLatLng();
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + actionEvent.getLatLng().getLat() + ", Lng:" + actionEvent.getLatLng().getLng()));
    }

    public void render() {
        editable = true;
    }

    public void createEntityToPersist() {
        editable = false;
        BubbleBookUser user = userEjb.findUserByID(1);
        Discussion discussion = discussionEjb.findDiscussionByID(1);

        BubbleMap be = new BubbleMap(coord.getLat(), coord.getLng(),place);
        be.setBubbleContent(comment);
        be.setBubbleType("MAP");
        be.setCreateTimestamp(new Date());
        be.setDiscussionId(discussion);
        be.setUserId(user);
       //  persistedEntity = (BubbleMap) bubbleEjb.saveBubble(be);
        
    }
}
