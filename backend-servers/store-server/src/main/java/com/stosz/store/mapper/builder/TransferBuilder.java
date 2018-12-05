package com.stosz.store.mapper.builder;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.store.ext.dto.request.SearchTransferReq;
import com.stosz.store.ext.model.Transfer;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.BeanUtils;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 16:52
 */
public class TransferBuilder extends AbstractBuilder<Transfer> {

    @Override
    public void buildSelectOther(SQL sql) {

    }

    @Override
    public void buildJoin(SQL sql) {

    }

    @Override
    public void buildWhere(SQL sql, Transfer param) {

    }

    private void buildFindWhere(StringBuilder sql, SearchTransferReq param) {
        sql.append(" WHERE 1=1 ");
        eq(sql, "_this.id", "id", param.getId());
        eq(sql, "_this.type", "type", param.getType());
        eq(sql, "_this.state", "state", param.getState());
        eq(sql, "_this.out_wms_id", "outWmsId", param.getOutWmsId());
        eq(sql, "_this.in_wms_id", "inWmsId", param.getInWmsId());
        ge(sql, "_this.create_at", "createStartTime", param.getCreateStartTime());
        lt(sql, "_this.create_at", "createEndTime", param.getCreateEndTime());

        String jobAuthorityRel = param.getJobAuthorityRel();
        if (jobAuthorityRel == "all") {
            return;
        } else if ("myDepartment".equals(jobAuthorityRel)) {
            sql.append(String.format(" AND ( _this.in_dept_id = #{%s} OR _this.out_dept_id = #{%s} OR _this.create_dept_id = #{%s} ) ", "deptId"));
        } else if ("myself".equals(jobAuthorityRel)) {
            sql.append(String.format(" AND _this.creator_id=#{%s}", "userId"));
        }
    }

    public void lt(StringBuilder sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.append(String.format(" AND %s<#{%s}", column, field));
        }
    }

    private void ge(StringBuilder sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.append(String.format(" AND %s>=#{%s}", column, field));
        }
    }

    private void eq(StringBuilder sql, String column, String field, Object value) {
        if (notEmpty(value)) {
            sql.append(String.format(" AND %s=#{%s}", column, field));
        }
    }


    public String findPage(SearchTransferReq param) {
        Transfer transfer = new Transfer();
        BeanUtils.copyProperties(param, transfer);
        String pageStr = buildSearchPageSql(transfer);
        return findList(param) + pageStr;
    }

    public String findList(SearchTransferReq param) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT _this.*,GROUP_CONCAT(it.sku) AS sku,GROUP_CONCAT(it.in_dept_name) AS inDeptName,GROUP_CONCAT(it.out_dept_name) AS outDeptName");

        builder.append(" ,(SELECT w.name FROM wms w WHERE w.id=_this.out_wms_id) AS outWmsName ");

        builder.append(" ,(SELECT w.name FROM wms w WHERE w.id=_this.in_wms_id)  AS inWmsName ");

        builder.append(" FROM transfer _this ");

        builder.append(" LEFT JOIN transfer_item it ON _this.id=it.tran_id ");

        buildFindWhere(builder, param);
        builder.append(" GROUP BY _this.id");
        return builder.toString();
    }

    public String countSearch(SearchTransferReq param) {

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT count(1) ");
        builder.append(" FROM transfer _this ");
        this.buildFindWhere(builder, param);

        return builder.toString();
    }

}
