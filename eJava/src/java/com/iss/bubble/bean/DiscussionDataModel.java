/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.iss.bubble.entity.Discussion;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Sathish
 */
public class DiscussionDataModel extends ListDataModel<Discussion> implements SelectableDataModel<Discussion> {

    public DiscussionDataModel() {
    }

    public DiscussionDataModel(List<Discussion> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Discussion object) {
        return object.getDiscussionId();
    }

    @Override
    public Discussion getRowData(String rowKey) {
        List<Discussion> discs = (List<Discussion>) getWrappedData();

        for (Discussion disc : discs) {
            if (disc.getDiscussionId().toString().equals(rowKey)) {
                return disc;
            }
        }
        return null;
    }

}
