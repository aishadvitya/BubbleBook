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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sathish JR
 */
@Entity
@DiscriminatorColumn(name = "BUBBLE_TYPE")
@DiscriminatorValue("AV")
@PrimaryKeyJoinColumn(name = "BUBBLE_ID")
@Table(name = "BUBBLE_AV", schema = "EJAVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BubbleAV.findAll", query = "SELECT b FROM BubbleAV b"),
    @NamedQuery(name = "BubbleAV.findByBubbleId", query = "SELECT b FROM BubbleAV b WHERE b.bubbleId = :bubbleId"),
    @NamedQuery(name = "BubbleAV.findByUrl", query = "SELECT b FROM BubbleAV b WHERE b.url = :url")})
public class BubbleAV extends Bubble {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "URL")
    private String url;

    public BubbleAV() {
    }

    public BubbleAV(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public BubbleAV(Integer bubbleId, String url) {
        this.bubbleId = bubbleId;
        this.url = url;
    }

    public BubbleAV(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
       

         if (url.contains("v=")){
            System.out.println("Converting link format --> ");
            String vid = url.split("v=")[1];
            this.url = "http://www.youtube.com/v/" + vid;
        }else
             this.url=url;
            
         
    }

    @Override
    public String toString() {
        return "com.iss.bubble.entity.BubbleAV[ bubbleId=" + bubbleId + " ]";
    }
}
