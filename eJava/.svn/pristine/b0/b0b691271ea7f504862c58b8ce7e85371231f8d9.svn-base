/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.iss.bubble.entity.BubbleHierarchy;
import java.util.Comparator;

/**
 *
 * @author Test
 */
class MyComparator2 implements Comparator<BubbleHierarchy>
        {

    public MyComparator2() {
    }

    @Override
    public int compare(BubbleHierarchy o1, BubbleHierarchy o2) {
       if(o1.getBubble().getCreateTimestamp().after(o2.getBubble().getCreateTimestamp())|| o1.getBubble().getCreateTimestamp().equals(o2.getBubble().getCreateTimestamp()))
               return -1;
            return 1;
    }
    
}
