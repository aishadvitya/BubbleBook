/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iss.bubble.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sathish
 */
@Entity
@DiscriminatorColumn(name = "BUBBLE_TYPE")
@DiscriminatorValue("EMAIL")
@PrimaryKeyJoinColumn(name = "BUBBLE_ID")
@Table(name = "BUBBLE_EMAIL", schema = "EJAVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BubbleEmail.findAll", query = "SELECT b FROM BubbleEmail b"),
    @NamedQuery(name = "BubbleEmail.findByBubbleId", query = "SELECT b FROM BubbleEmail b WHERE b.bubbleId = :bubbleId"),
    @NamedQuery(name = "BubbleEmail.findBySubject", query = "SELECT b FROM BubbleEmail b WHERE b.subject = :subject"),
    @NamedQuery(name = "BubbleEmail.findByToAddress", query = "SELECT b FROM BubbleEmail b WHERE b.toAddress = :toAddress")})
public class BubbleEmail extends Bubble {
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SUBJECT")
    private String subject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TO_ADDRESS")
    private String toAddress;

    public BubbleEmail() {
    }

    public BubbleEmail(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public BubbleEmail(Integer bubbleId, String subject, String toAddress) {
        this.bubbleId = bubbleId;
        this.subject = subject;
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bubbleId != null ? bubbleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BubbleEmail)) {
            return false;
        }
        BubbleEmail other = (BubbleEmail) object;
        if ((this.bubbleId == null && other.bubbleId != null) || (this.bubbleId != null && !this.bubbleId.equals(other.bubbleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iss.bubble.entity.BubbleEmail[ bubbleId=" + bubbleId + " ]";
    }
    
}
