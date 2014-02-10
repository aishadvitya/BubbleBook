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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sathish
 */
@Entity
@Table(name = "BUBBLE_BOOK_USER", schema = "EJAVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BubbleBookUser.findAll", query = "SELECT b FROM BubbleBookUser b"),
    @NamedQuery(name = "BubbleBookUser.findByUserId", query = "SELECT b FROM BubbleBookUser b WHERE b.userId = :userId"),
    @NamedQuery(name = "BubbleBookUser.findByName", query = "SELECT b FROM BubbleBookUser b WHERE b.name = :name"),
    @NamedQuery(name = "BubbleBookUser.findByPassword", query = "SELECT b FROM BubbleBookUser b WHERE b.password = :password"),
    @NamedQuery(name = "BubbleBookUser.findByAbtMe", query = "SELECT b FROM BubbleBookUser b WHERE b.abtMe = :abtMe"),
    @NamedQuery(name = "BubbleBookUser.findByLastLogonTime", query = "SELECT b FROM BubbleBookUser b WHERE b.lastLogonTime = :lastLogonTime"),
    @NamedQuery(name = "BubbleBookUser.findByEmail", query = "SELECT b FROM BubbleBookUser b WHERE b.email = :email")})
public class BubbleBookUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableGenerator(
            name = "bubble_user_id_generator",
            schema = "EJAVA",
            table = "PKID_GEN_TABLE",
            pkColumnName = "GEN_KEY",
            valueColumnName = "GEN_VALUE",
            pkColumnValue = "bubbleUserId",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "bubble_user_id_generator")
    @Basic(optional = false)
    @Column(name = "USER_ID")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PASSWORD")
    private String password;

    @Basic(optional = true)
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Size(max = 500)
    @Column(name = "ABT_ME")
    private String abtMe;
    @Column(name = "LAST_LOGON_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogonTime;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    //if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;

    @JoinTable(schema = "EJAVA", name = "BUBBLE_BOOK_USER_FRIENDS", joinColumns = {
        @JoinColumn(name = "FRIEND_USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")})
    @ManyToMany
    private Collection<BubbleBookUser> bubbleBookUserCollection;
    //contacts
    @ManyToMany(mappedBy = "bubbleBookUserCollection")
    private Collection<BubbleBookUser> bubbleBookUserCollection1;
    //Participants
    @ManyToMany(mappedBy = "bubbleBookUserCollection")
    private Collection<Discussion> discussionCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private Collection<Bubble> bubbleCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Discussion> discussionCollection1;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bubbleBookUser")
    private Collection<GroupTable> groupTableCollection;

    public BubbleBookUser() {
    }

    public BubbleBookUser(Integer userId) {
        this.userId = userId;
    }

    public BubbleBookUser(Integer userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbtMe() {
        return abtMe;
    }

    public void setAbtMe(String abtMe) {
        this.abtMe = abtMe;
    }

    public Date getLastLogonTime() {
        return lastLogonTime;
    }

    public void setLastLogonTime(Date lastLogonTime) {
        this.lastLogonTime = lastLogonTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<BubbleBookUser> getBubbleBookUserCollection() {
        return bubbleBookUserCollection;
    }

    public void setBubbleBookUserCollection(Collection<BubbleBookUser> bubbleBookUserCollection) {
        this.bubbleBookUserCollection = bubbleBookUserCollection;
    }

    @XmlTransient
    public Collection<BubbleBookUser> getBubbleBookUserCollection1() {
        return bubbleBookUserCollection1;
    }

    public void setBubbleBookUserCollection1(Collection<BubbleBookUser> bubbleBookUserCollection1) {
        this.bubbleBookUserCollection1 = bubbleBookUserCollection1;
    }

    @XmlTransient
    public Collection<Discussion> getDiscussionCollection() {
        return discussionCollection;
    }

    public void setDiscussionCollection(Collection<Discussion> discussionCollection) {
        this.discussionCollection = discussionCollection;
    }

    @XmlTransient
    public Collection<Bubble> getBubbleCollection() {
        return bubbleCollection;
    }

    public void setBubbleCollection(Collection<Bubble> bubbleCollection) {
        this.bubbleCollection = bubbleCollection;
    }

    @XmlTransient
    public Collection<Discussion> getDiscussionCollection1() {
        return discussionCollection1;
    }

    public void setDiscussionCollection1(Collection<Discussion> discussionCollection1) {
        this.discussionCollection1 = discussionCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BubbleBookUser)) {
            return false;
        }
        BubbleBookUser other = (BubbleBookUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BubbleBookUser{" + "userId=" + userId + ", name=" + name + ", password=" + password + ", imageUrl=" + imageUrl + ", abtMe=" + abtMe + ", lastLogonTime=" + lastLogonTime + ", email=" + email + ", bubbleBookUserCollection=" + bubbleBookUserCollection + ", bubbleBookUserCollection1=" + bubbleBookUserCollection1 + ", discussionCollection=" + discussionCollection + ", bubbleCollection=" + bubbleCollection + ", discussionCollection1=" + discussionCollection1 + ", groupTableCollection=" + groupTableCollection + '}';
    }

    @XmlTransient
    public Collection<GroupTable> getGroupTableCollection() {
        return groupTableCollection;
    }

    public void setGroupTableCollection(Collection<GroupTable> groupTableCollection) {
        this.groupTableCollection = groupTableCollection;
    }

}
