/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sathish
 */
@Entity
@DiscriminatorColumn(name = "BUBBLE_TYPE")
@DiscriminatorValue("EVENT")
@PrimaryKeyJoinColumn(name = "BUBBLE_ID")
@Table(name = "BUBBLE_EVENT", schema = "EJAVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BubbleEvent.findAll", query = "SELECT b FROM BubbleEvent b"),
    @NamedQuery(name = "BubbleEvent.findByBubbleId", query = "SELECT b FROM BubbleEvent b WHERE b.bubbleId = :bubbleId"),
    @NamedQuery(name = "BubbleEvent.findByTitle", query = "SELECT b FROM BubbleEvent b WHERE b.title = :title"),
    @NamedQuery(name = "BubbleEvent.findByUserId", query = "SELECT b FROM BubbleEvent b WHERE b.userId = :userId"),
    @NamedQuery(name = "BubbleEvent.findByStartdatetime", query = "SELECT b FROM BubbleEvent b WHERE b.startdatetime = :startdatetime"),
    @NamedQuery(name = "BubbleEvent.findByEnddatetime", query = "SELECT b FROM BubbleEvent b WHERE b.enddatetime = :enddatetime"),
    @NamedQuery(name = "BubbleEvent.findByMaybeCount", query = "SELECT b FROM BubbleEvent b WHERE b.maybeCount = :maybeCount"),
    @NamedQuery(name = "BubbleEvent.findByNotgoingCount", query = "SELECT b FROM BubbleEvent b WHERE b.notgoingCount = :notgoingCount"),
    @NamedQuery(name = "BubbleEvent.findByGoingCount", query = "SELECT b FROM BubbleEvent b WHERE b.goingCount = :goingCount"),
    @NamedQuery(name = "BubbleEvent.findByCreateTimestamp", query = "SELECT b FROM BubbleEvent b WHERE b.createTimestamp = :createTimestamp")})
public class BubbleEvent extends Bubble {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTDATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdatetime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDDATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddatetime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAYBE_COUNT")
    private int maybeCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOTGOING_COUNT")
    private int notgoingCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GOING_COUNT")
    private int goingCount;
    
    @Transient
    private String response=" ";

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    

    public BubbleEvent() {
    }

    public BubbleEvent(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public BubbleEvent(String title, Date startdatetime, Date enddatetime, int maybeCount, int notgoingCount, int goingCount) {
        this.title = title;
        this.startdatetime = startdatetime;
        this.enddatetime = enddatetime;
        this.maybeCount = maybeCount;
        this.notgoingCount = notgoingCount;
        this.goingCount = goingCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(Date startdatetime) {
        this.startdatetime = startdatetime;
    }

    public Date getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(Date enddatetime) {
        this.enddatetime = enddatetime;
    }

    public int getMaybeCount() {
        return maybeCount;
    }

    public void setMaybeCount(int maybeCount) {
        this.maybeCount = maybeCount;
    }

    public int getNotgoingCount() {
        return notgoingCount;
    }

    public void setNotgoingCount(int notgoingCount) {
        this.notgoingCount = notgoingCount;
    }

    public int getGoingCount() {
        return goingCount;
    }

    public void setGoingCount(int goingCount) {
        this.goingCount = goingCount;
    }

    @Override
    public String toString() {
        return "com.iss.bubble.entity.BubbleEvent[ bubbleId=" + bubbleId + " ]";
    }

}
