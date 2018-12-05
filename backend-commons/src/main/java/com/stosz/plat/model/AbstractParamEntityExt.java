package com.stosz.plat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractParamEntityExt {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_LIMIT = 50;
    private static final boolean DEFAULT_ORDER = true;

    private static final String REQUEST_OFFSET_ATTRIBUTE_NAME = "start";
    private static final String REQUEST_LIMIT_ATTRIBUTE_NAME = "limit";
    private static final String REQUEST_SORT_ATTRIBUTE_NAME = "orderBy";
    private static final String REQUEST_ORDER_ATTRIBUTE_NAME = "order";

    @JsonIgnore
    private Integer start = 0;
    @JsonIgnore
    private Integer limit = 20;
    @JsonIgnore
    private String orderBy;
    @JsonIgnore
    private boolean order = DEFAULT_ORDER;

    private Integer creatorId;
    @JsonIgnore
    private LocalDateTime minCreateAt;
    @JsonIgnore
    private LocalDateTime maxCreateAt;




    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getMinCreateAt() {
        return minCreateAt;
    }

    public void setMinCreateAt(LocalDateTime minCreateAt) {
        this.minCreateAt = minCreateAt;
    }

    public LocalDateTime getMaxCreateAt() {
        return maxCreateAt;
    }

    public void setMaxCreateAt(LocalDateTime maxCreateAt) {
        this.maxCreateAt = maxCreateAt;
    }

    public Integer getStart() {
        return start;
    }

//    public void setStart(int offset) {
//        this.start = offset;
//    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean getOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public abstract Long getId();

    public abstract void setId(Long id);

    public static AbstractParamEntityExt fromRequest(HttpServletRequest request) {
        Integer requestOffset = (Integer) request.getAttribute(REQUEST_OFFSET_ATTRIBUTE_NAME);
        Integer requestLimit = (Integer) request.getAttribute(REQUEST_LIMIT_ATTRIBUTE_NAME);
        String requestSort = (String) request.getAttribute(REQUEST_SORT_ATTRIBUTE_NAME);
        Boolean requestOrder = (Boolean) request.getAttribute(REQUEST_ORDER_ATTRIBUTE_NAME);

        AbstractParamEntityExt pageParam = new AbstractParamEntityExt() {
            private Long id;

            @Override
            public Long getId() {
                return id;
            }

            @Override
            public void setId(Long id) {
                this.id = id;
            }
        };
        pageParam.setStart(requestOffset == null ? DEFAULT_OFFSET : requestOffset);
        pageParam.setLimit(requestLimit == null ? DEFAULT_LIMIT : requestLimit);
        if (StringUtils.isNotBlank(requestSort)) {
            pageParam.setOrderBy(requestSort);
            pageParam.setOrder(requestOrder == null ? true : requestOrder);
        }
        return pageParam;
    }


    @Override
    public String toString() {
        Object objThis = this;

        Method[] methods = this.getClass().getMethods();
        //1.将所有get开头的方法、参数数量为0、并且方法大于3个字符的对象方法过滤出来。
        //2.将每个符合条件的方法，组合成 "属性名=属性值"的集合
        //3.将集合用“, ”连接，前缀“类名+[”，后缀“]”
        String str = Stream.of(methods).filter(m -> m.getName().startsWith("get")
                && m.getParameterCount() == 0 && m.getName().length() > 3).map(m -> {
            String name = Character.toLowerCase(m.getName().charAt(3))
                    + m.getName().substring(4);
            try {
                Optional<Object> val = Optional.ofNullable(m.invoke(objThis));
                String strVal = val.orElse("Null").toString();
                if (strVal.length() > 25) {
                    strVal = strVal.substring(0, 25) + "...";
                }
                return name + "=" + val.orElse("Null");

            } catch (Exception e) {
                return name + "=<Exception:" + e.getMessage() + ">";
            }
        }).collect(Collectors.joining(", ", objThis.getClass().getSimpleName() + "[", "]"));
        return str;
    }

}
