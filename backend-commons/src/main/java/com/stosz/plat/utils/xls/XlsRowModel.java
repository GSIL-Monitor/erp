package com.stosz.plat.utils.xls;

import com.google.common.collect.Lists;
import com.stosz.plat.utils.CollectionUtils;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @auther carter
 * create time    2017-12-06
 */
public class XlsRowModel implements Serializable {

    private LinkedList<XlsCellModel> cells = Lists.newLinkedList();

    public LinkedList<XlsCellModel> getCells() {
        return cells;
    }

    public void setCells(LinkedList<XlsCellModel> cells) {
        this.cells = cells;
    }

    public void addCells(LinkedList<XlsCellModel> cells) {

        if(CollectionUtils.isNullOrEmpty(cells)) return;
        cells.forEach(item->addCell(item));

    }

    public void addCell(XlsCellModel cell)
    {
        if(null == cell) return;
        this.cells.add(cell);
    }


}
