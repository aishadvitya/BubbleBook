/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Test
 */

public class Participant implements Serializable  {
    String name;
    int number;
    String photo;
    String position;
      public  Participant(String name, int i, String photo, String forward) {
        this.name=name;
        this.number=i;
        this.photo=photo;
        this.position=forward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
}
