package com.stosz.tms.model.zwy;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Items")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {

    @XmlElement(name = "Item",nillable=true)
    private List<Item> Items;

    public List<Item> getItems() {
        return Items;
    }

    public void setItems(List<Item> items) {
        Items = items;
    }
}
