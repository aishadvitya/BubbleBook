/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.iss.bubble.entity.Bubble;
import java.util.Comparator;

/**
 *
 * @author Test
 */

        
        class MyComparator implements Comparator<Bubble>
    {
        @Override
        public int compare(Bubble o1,Bubble o2)
        {
            if(o1.getCreateTimestamp().after(o2.getCreateTimestamp())|| o1.getCreateTimestamp().equals(o2.getCreateTimestamp()))
               return -1;
            return 1;
        }
    }
