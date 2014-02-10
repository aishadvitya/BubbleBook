/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sathish
 */
@Entity
@Table(name = "BUBBLE_HIERARCHY", schema = "EJAVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BubbleHierarchy.findAll", query = "SELECT b FROM BubbleHierarchy b"),
    @NamedQuery(name = "BubbleHierarchy.findByBubbleId", query = "SELECT b FROM BubbleHierarchy b WHERE b.bubbleId = :bubbleId")})
public class BubbleHierarchy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUBBLE_ID")
    private Integer bubbleId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bubbleHierarchy")
    private Bubble bubble;
    @JoinColumn(name = "PARENT_BUBBLE_ID", referencedColumnName = "BUBBLE_ID", nullable = true)
    @ManyToOne
    private Bubble parentBubble;

    public BubbleHierarchy() {
    }
    
    public BubbleHierarchy(Bubble bubble,Bubble parentBubble){
        this.bubbleId = bubble.getBubbleId();
        this.bubble=bubble;
        this.parentBubble=parentBubble;
    }

    public BubbleHierarchy(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public Integer getBubbleId() {
        return bubbleId;
    }

    public void setBubbleId(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public Bubble getBubble() {
        return bubble;
    }

    public void setBubble(Bubble bubble) {
        this.bubble = bubble;
    }

    public Bubble getParentBubble() {
        return parentBubble;
    }

    public void setParentBubble(Bubble parentBubble) {
        this.parentBubble = parentBubble;
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
        if (!(object instanceof BubbleHierarchy)) {
            return false;
        }
        BubbleHierarchy other = (BubbleHierarchy) object;
        if ((this.bubbleId == null && other.bubbleId != null) || (this.bubbleId != null && !this.bubbleId.equals(other.bubbleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iss.bubble.entity.BubbleHierarchy[ bubbleId=" + bubbleId + " ]";
    }

}
