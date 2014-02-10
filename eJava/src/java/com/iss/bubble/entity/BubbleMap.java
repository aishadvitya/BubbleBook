/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Sathish
 */
@Entity
@DiscriminatorColumn(name = "BUBBLE_TYPE")
@DiscriminatorValue("MAP")
@PrimaryKeyJoinColumn(name = "BUBBLE_ID")
@Table(name = "BUBBLE_MAP", schema = "EJAVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BubbleMap.findAll", query = "SELECT b FROM BubbleMap b"),
    @NamedQuery(name = "BubbleMap.findByBubbleId", query = "SELECT b FROM BubbleMap b WHERE b.bubbleId = :bubbleId"),
    @NamedQuery(name = "BubbleMap.findByLocationLat", query = "SELECT b FROM BubbleMap b WHERE b.locationLat = :locationLat"),
    @NamedQuery(name = "BubbleMap.findByLocationLon", query = "SELECT b FROM BubbleMap b WHERE b.locationLon = :locationLon"),
    @NamedQuery(name = "BubbleMap.findByPlace", query = "SELECT b FROM BubbleMap b WHERE b.place = :place")})
public class BubbleMap extends Bubble {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LOCATION_LAT")
    private Double locationLat;
    @Column(name = "LOCATION_LON")
    private Double locationLon;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PLACE")
    private String place;

    @Transient
    private MapModel simpleModel;

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }
    
    public BubbleMap() {
    }

    public BubbleMap(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public BubbleMap(Integer bubbleId, String place) {
        this.bubbleId = bubbleId;
        this.place = place;
    }

    public BubbleMap(double locationLat, double locationLon, String place) {
        this.locationLat = locationLat;
        this.locationLon = locationLon;
        this.place = place;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(Double locationLon) {
        this.locationLon = locationLon;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    
    public MapModel getSimpleMapModel(Double lat, Double lng,String place) {
        LatLng coord1 = new LatLng(lat, lng);
        Marker marker = new Marker(coord1,place);
        simpleModel = new DefaultMapModel();
        simpleModel.addOverlay(marker);
        return simpleModel;
    }
    
    

    @Override
    public String toString() {
        return "com.iss.bubble.entity.BubbleMap[ bubbleId=" + bubbleId + " ]";
    }

}
