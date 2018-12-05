package com.stosz.tms.vo;

import java.util.List;

public class ShippingWayStoreRelLableListVo {

    private Integer type; // 0:无法寄出的产品 1：特货可配 2:普货可配

    private String typeName;

    private List<LableRelationVo> lableRelationVos;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<LableRelationVo> getLableRelationVos() {
        return lableRelationVos;
    }

    public void setLableRelationVos(List<LableRelationVo> lableRelationVos) {
        this.lableRelationVos = lableRelationVos;
    }

    public static class LableRelationVo {

        private Integer  id;

        private String name;

        private Boolean selected = false;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
