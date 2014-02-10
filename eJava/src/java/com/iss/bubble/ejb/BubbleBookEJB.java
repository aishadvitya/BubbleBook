/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iss.bubble.ejb;

import com.iss.bubble.entity.Bubble;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author Sathish
 */
@Singleton
public class BubbleBookEJB {
    List<Bubble> bubbles;

    public List<Bubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(List<Bubble> bubbles) {
        this.bubbles = bubbles;
    }
}
