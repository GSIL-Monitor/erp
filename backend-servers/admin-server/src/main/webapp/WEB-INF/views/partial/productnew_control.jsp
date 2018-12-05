<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<el-table-column type='selection' width='50'></el-table-column>
<el-table-column label='*操作' width='100'>
 <template scope='inner'>
    <%@ include file="/WEB-INF/views/partial/productnew_control_fsm.jsp" %>
     <el-row v-if='inner.row.state == "waitCheck"'>
        <el-row v-if='app.controls.page=="checkList"'>
            <el-button size='mini' type='text'
                       @click='window.top.app.collapsed=true;window.top.nativePub("collapsed"); window.top.app.open("/pc/product/productNew/beginCheck?id="+inner.row.id, window.top.app.currentTable)'
                       :disabled='!app.controls.app.Perm["product_new.beginCheck"] || !inner.row.categoryName'>开始查重
            </el-button>
        </el-row>
    </el-row>
     <el-row v-if='inner.row.state=="waitAssign"'>
         <el-row v-if='app.controls.page=="checkList"'>
             <el-button size='mini' type='text' @click='app.controls.newtable.selcate(inner.row.id)'
                        :disabled='!app.controls.app.Perm["product_new.beginCheck"]'>细化分类
             </el-button>
             <el-button size='mini' type='text' :actionkey='{type: "backToCreated", id: inner.row.id, row: inner.row}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">驳回</el-button>
         </el-row>
     </el-row>
    <el-row v-if='inner.row.state == "draft"'>
        <el-row v-if='app.controls.page == "adList" || app.controls.page == "checkList"'>
            <el-button size='mini' type='text' @click='app.controls.newtable.seledit(inner.row)'
                       :disabled='!app.controls.app.Perm.write'>编辑
            </el-button>
        </el-row>
        <el-row v-if="app.controls.page=='adList'">
            <el-button size='mini' type='text' @click='app.controls.newtable.delete(inner.row)'
                       :disabled='!app.controls.app.Perm.write'>删除
            </el-button>
        </el-row>
    </el-row>
  </template>
</el-table-column>

<%--<js>--%>

    <%--<script>--%>
        <%--var C = window.layoutData.controls;--%>
        <%--C.methods = {--%>
            <%--action(opt) {--%>
                <%--const h = window.app.$createElement;--%>
                <%--console.log(h)--%>
                <%--app.$msgbox({--%>
                    <%--title: '备注:',--%>
                    <%--message: h('ctlConfirm', {style:'width:300px'}, [--%>
                        <%--h('textarea', {style:'width:100%'})--%>
                    <%--]),--%>


                <%--}).then(function (x) {--%>

                    <%--console.log(x,1111)--%>
<%--//                            x.action--%>
                    <%--if (x == 'confirm') {--%>
                        <%--var actionParameters = {--%>
                            <%--event: opt.type,--%>
                            <%--state: app.controls.currentProduct[0].stateEnum,--%>
                            <%--memo: (opt.prefix || "") + (opt.prefix ? " " : "") + x.value,--%>
                            <%--id: app.controls.currentProduct[0].id,--%>
                            <%--fsmName: 'ProductNew',--%>
                            <%--spu: opt.spu,--%>
                            <%--productZoneId: opt.productZoneId--%>
                        <%--}--%>
                        <%--console.log(actionParameters)--%>
                        <%--app.ajax("/product/product/productNew/processEvent", { method: 'post', data: app.postform(actionParameters) }).then(function () {--%>
                            <%--top.app.removeTab(window.app.subName);--%>
                            <%--console.log(window.app.subName)--%>
                        <%--}).catch(function () {--%>

                        <%--});--%>
                    <%--}--%>
                <%--});--%>
            <%--}--%>
        <%--}--%>
    <%--</script>--%>

<%--</js>--%>