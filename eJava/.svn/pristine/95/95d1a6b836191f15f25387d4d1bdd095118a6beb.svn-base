/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sathish
 */
@Entity
@Table(schema = "EJAVA", name = "GROUP_TABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupTable.findAll", query = "SELECT g FROM GroupTable g"),
    @NamedQuery(name = "GroupTable.findByGroupName", query = "SELECT g FROM GroupTable g WHERE g.groupTablePK.groupName = :groupName"),
    @NamedQuery(name = "GroupTable.findByEmail", query = "SELECT g FROM GroupTable g WHERE g.groupTablePK.email = :email")})
public class GroupTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupTablePK groupTablePK;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BubbleBookUser bubbleBookUser;

    public GroupTable() {
    }

    public GroupTable(GroupTablePK groupTablePK) {
        this.groupTablePK = groupTablePK;
    }

    public GroupTable(String groupName, String email) {
        this.groupTablePK = new GroupTablePK(groupName, email);
    }

    public GroupTablePK getGroupTablePK() {
        return groupTablePK;
    }

    public void setGroupTablePK(GroupTablePK groupTablePK) {
        this.groupTablePK = groupTablePK;
    }

    public BubbleBookUser getBubbleBookUser() {
        return bubbleBookUser;
    }

    public void setBubbleBookUser(BubbleBookUser bubbleBookUser) {
        this.bubbleBookUser = bubbleBookUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupTablePK != null ? groupTablePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupTable)) {
            return false;
        }
        GroupTable other = (GroupTable) object;
        if ((this.groupTablePK == null && other.groupTablePK != null) || (this.groupTablePK != null && !this.groupTablePK.equals(other.groupTablePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iss.bubble.entity.GroupTable[ groupTablePK=" + groupTablePK + " ]";
    }

}
