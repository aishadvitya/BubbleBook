/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sathish
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BUBBLE_TYPE")
@DiscriminatorValue("TEXT")
@Table(name = "BUBBLE", schema = "EJAVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bubble.findAll", query = "SELECT b FROM Bubble b"),
    @NamedQuery(name = "Bubble.findByBubbleId", query = "SELECT b FROM Bubble b WHERE b.bubbleId = :bubbleId"),
    @NamedQuery(name = "Bubble.findByUserId", query = "SELECT b FROM Bubble b WHERE b.userId = :userId"),
    @NamedQuery(name = "Bubble.findByCreateTimestamp", query = "SELECT b FROM Bubble b WHERE b.createTimestamp = :createTimestamp"),
    @NamedQuery(name = "Bubble.findByBubbleType", query = "SELECT b FROM Bubble b WHERE b.bubbleType = :bubbleType"),
    @NamedQuery(name = "Bubble.findByDiscussionId", query = "SELECT b FROM Bubble b WHERE b.discussionId = :discussionId"),
    @NamedQuery(name = "Bubble.findByBubbleContent", query = "SELECT b FROM Bubble b WHERE b.bubbleContent = :bubbleContent")})
  
public class Bubble implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableGenerator(
            name = "bubble_id_generator",
            schema = "EJAVA",
            table = "PKID_GEN_TABLE",
            pkColumnName = "GEN_KEY",
            valueColumnName = "GEN_VALUE",
            pkColumnValue = "bubbleId",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "bubble_id_generator")
    @Basic(optional = false)
    @Column(name = "BUBBLE_ID")
    protected Integer bubbleId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    @ManyToOne(optional = false)
    private BubbleBookUser userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATE_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BUBBLE_TYPE")
    private String bubbleType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "BUBBLE_CONTENT")
    private String bubbleContent;    
    @JoinColumn(name = "BUBBLE_ID", referencedColumnName = "BUBBLE_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private BubbleHierarchy bubbleHierarchy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentBubble",fetch = FetchType.LAZY)
    private Collection<BubbleHierarchy> bubbleHierarchyCollection;
    @JoinColumn(name = "DISCUSSION_ID", referencedColumnName = "DISCUSSION_ID")
    @ManyToOne(optional = false)
    private Discussion discussionId;

    @Transient
    private boolean isEditable=false;

    public boolean isIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Bubble() {
    }

    public Bubble(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public Bubble(Integer bubbleId, BubbleBookUser userId, Date createTimestamp, String bubbleType, String bubbleContent) {
        this.bubbleId = bubbleId;
        this.userId = userId;
        this.createTimestamp = createTimestamp;
        this.bubbleType = bubbleType;
        this.bubbleContent = bubbleContent;
    }

    public Integer getBubbleId() {
        return bubbleId;
    }

    public void setBubbleId(Integer bubbleId) {
        this.bubbleId = bubbleId;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getBubbleType() {
        return bubbleType;
    }

    public void setBubbleType(String bubbleType) {
        this.bubbleType = bubbleType;
    }

    public String getBubbleContent() {
        return bubbleContent;
    }

    public void setBubbleContent(String bubbleContent) {
        this.bubbleContent = bubbleContent;
    }

    public BubbleHierarchy getBubbleHierarchy() {
        return bubbleHierarchy;
    }

    public void setBubbleHierarchy(BubbleHierarchy bubbleHierarchy) {
        this.bubbleHierarchy = bubbleHierarchy;
    }

    @XmlTransient
    public Collection<BubbleHierarchy> getBubbleHierarchyCollection() {
        return bubbleHierarchyCollection;
    }

    public void setBubbleHierarchyCollection(Collection<BubbleHierarchy> bubbleHierarchyCollection) {
        this.bubbleHierarchyCollection = bubbleHierarchyCollection;
    }

    public Discussion getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(Discussion discussionId) {
        this.discussionId = discussionId;
    }

    public BubbleBookUser getUserId() {
        return userId;
    }

    public void setUserId(BubbleBookUser userId) {
        this.userId = userId;
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
        if (!(object instanceof Bubble)) {
            return false;
        }
        Bubble other = (Bubble) object;
        if ((this.bubbleId == null && other.bubbleId != null) || (this.bubbleId != null && !this.bubbleId.equals(other.bubbleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bubble{" + "bubbleId=" + bubbleId + ", userId=" + userId + ", createTimestamp=" + createTimestamp + ", bubbleType=" + bubbleType + ", bubbleContent=" + bubbleContent + ", bubbleHierarchy=" + bubbleHierarchy + ", bubbleHierarchyCollection=" + bubbleHierarchyCollection + ", discussionId=" + discussionId + '}';
    }

}
