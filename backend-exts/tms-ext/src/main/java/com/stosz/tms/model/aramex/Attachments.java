package com.stosz.tms.model.aramex;

import java.io.Serializable;
import java.util.List;

/**
 * attachment -> attachments -> shipment ->shipments
 */
public class Attachments implements Serializable{
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
