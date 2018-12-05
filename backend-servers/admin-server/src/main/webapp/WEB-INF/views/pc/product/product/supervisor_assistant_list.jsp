<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
<template scope='app' slot='controls'>
    <H1>产品列表(主管助理)</H1>
</template>
<template scope='app'>
    <control-table :app='app' :style="`width: ${app.window.innerWidth - 250}  px;`" tablekey='product_table'
                   :loading='app.controls.product_table.loading'>
        <template scope='app' slot='controls'>
            <el-row>
                <el-col :span='4' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='id' label='产品ID'></control-item>
                </el-col>
                <el-col :span='4' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='spu' label='SPU'></control-item>
                </el-col>
                <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='title'
                                  label='产品标题'></control-item>
                </el-col>
                <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='innerName'
                                  label='内部名'></control-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span='4' style='padding: 5px 15px;'>
                    <control-item type='selectAsync'
                                  :complete-array-url-func="url => window.app.ajax('/admin/user/userAutoComplement?condition=' + url).then(result => result.data.item)"
                                  :complete-props="{value: 'id', label: 'lastname'}"
                                  :local='app.local.searchParams'
                                  datakey='creatorId'
                                  label='创建人'></control-item>
                </el-col>
                <el-col :span='4' style='padding: 5px 15px;'>
                    <control-item type='select' :list='app.controls.product_table.zones' :local='app.local.searchParams'
                                  datakey='zoneCode' label='区域'></control-item>
                </el-col>
                <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='daterange' :local='app.local.searchParams' datakey='createAt'
                                  label='创建时间'></control-item>
                </el-col>
                <el-col :span='6' style='padding: 5px 15px;'>
                    <el-button type='primary' icon='search' style='margin-top: 2px;'
                               @click='app.controls.product_table.searchClick()'
                               :loading='app.controls.product_table.loading'>搜索
                    </el-button>
                </el-col>
            </el-row>


        </template>
        <el-table-column type='selection' width='50'></el-table-column>
        <el-table-column prop='id' label='产品ID' width='80'></el-table-column>
        <el-table-column label='操作' width='100'>
            <template scope='inner'>
                <el-button size='mini' @click='app.controls.product_table.showDetail(inner.row)'>详情</el-button>
                <el-button size='mini' :disabled='!app.controls.app.Perm.revoke' style="margin-left: 0px;"
                           v-if="inner.row.stateEnum=='archiving'"
                           @click='app.controls.product_table.revokeProductThing(inner.row)'>撤回
                </el-button>
                <el-dialog
                        title="提示"
                        :visible.sync="window.app.controls.product_table.revokeDialogVisible"
                        size="tiny">
                    <span>您确定要将该产品"{{window.app.controls.product_table.revokeProduct.title}}"撤回到带填充状态吗？</span>
                    <span slot="footer" class="dialog-footer">
                                            <el-button
                                                    @click="window.app.controls.product_table.revokeDialogVisible = false">取 消</el-button>
                                                <el-button type="primary"
                                                           @click="window.app.controls.product_table.submitRevoke()"
                                                           v-loading.fullscreen.lock="window.app.controls.product_table.fullscreenLoading">确 定</el-button>
                            </span>
                </el-dialog>
            </template>
        </el-table-column>
        <el-table-column label="图片" width="150">
            <template scope='inner'>
                <img :src='window.uploadPrefix + "/" + inner.row.mainImageUrl' style='width: 120px; height:140px;'>
            </template>
        </el-table-column>

        <el-table-column label='产品信息' width='250'>
            <template scope='inner'>
                <el-row>
                    <%-- <el-col style='width: 64px; margin-right: 16px;'><img
                             :src='window.uploadPrefix + "/" + inner.row.mainImageUrl'
                             style='width: 64px; height: 64px;'></el-col>--%>
                    <el-col :span='12'>
                        <%--<el-row>ID: {{inner.row.id}}</el-row>--%>
                        <el-row>SPU: {{inner.row.spu || '空'}}</el-row>
                        <el-row v-if="inner.row.classifyEnumName!=null && inner.row.classifyEnumName!=''"><span><el-tag>{{inner.row.classifyEnumName}}</el-tag></span>
                        </el-row>
                        <el-row v-if="inner.row.customEnum!=null && inner.row.customEnum!='normal'"><span><el-tag>{{inner.row.customEnumName}}</el-tag></span>
                        </el-row>
                    </el-col>
                </el-row>
                <el-row>{{inner.row.title}}</el-row>
            </template>
        </el-table-column>
        <el-table-column label='状态' width='100'>
            <template scope='inner'>
                <el-row>{{inner.row.stateName}}</el-row>
                <el-row class='breakspace'><a href='javascript: void(0);'
                                              @click='window.app.controls.product_table.showFSM(inner.row)'>{{inner.row.stateTime}}</a>
                </el-row>
            </template>
        </el-table-column>

        <el-table-column label='来源' prop='sourceEnumName' width='120'>
            <template scope='inner'>
                <el-row>{{inner.row.sourceEnumName || '暂无来源'}}</el-row>
                <el-row>
                    <a v-if="inner.row.sourceUrl" :href='window.app.normalizeURL(inner.row.sourceUrl)' target='_blank'>
                        来源链接</a>
                    <a v-if="!inner.row.sourceUrl" href='javascript:void(0)' target='_blank'>
                        暂无来源链接</a>
                </el-row>
                <el-row>
                    <%--<a v-if="inner.row.purchaseUrl" :href='window.app.normalizeURL(inner.row.purchaseUrl)'
                       target='_blank'>采购链接</a>
                    <a v-if="!inner.row.purchaseUrl" href='javascript:void(0)' target='_blank'>暂无采购链接</a>--%>
                </el-row>
            </template>
        </el-table-column>
        <el-table-column label='创建' width='100'>
            <template scope='inner'>
                <el-row>{{inner.row.creator}}</el-row>
                <el-row class='breakspace'>{{inner.row.createAt}}</el-row>
            </template>
        </el-table-column>
        <el-table-column label='审核' width='100'>
            <template scope='inner'>
                <el-row>{{inner.row.checker}}</el-row>
            </template>
        </el-table-column>

        <el-table-column label="内部名" width="100">
            <template scope="inner">
                <el-row>{{inner.row.innerName}}</el-row>
            </template>
        </el-table-column>

        <el-table-column label="品类名" width="200">
            <template scope="inner">
                <el-row>{{inner.row.name}}</el-row>
            </template>
        </el-table-column>
        <%--<el-table-column label='在售地区' width='300'>
            <template scope='inner'>
                <!--
                <template v-for='item in inner.row.zoneSet'>
                    <span style='font-weight: bold; font-size: 12px;'>{{item.zoneName}}</span><br>
                    <span class='breakspace'>{{item.stateTime && window.app.util.formatDate(new Date(Date.parse(item.stateTime)))}}</span><br>
                    <span>{{item.departmentName}} {{item.stateName || item.state}}</span>
                </template>
            -->
                <!--<el-tabs v-if='inner.row.zoneSet'>-->
                <!--<el-tab-pane :key='r.zoneId' :name='r.zoneId'>-->
                <div v-for='r in inner.row.zoneSet||[]' style='white-space: nowrap;'>{{r.departmentName}} <span
                        slot='label' style='font-weight: bold; font-size: 12px;'>{{r.zoneName}}</span>{{r.zoneState}}
                    库存:{{ r.stock}} 最后下单: {{r.lastOrderAt || '暂无'}}
                </div>
                <!--</el-tab-pane>-->
                <!--</el-tabs>-->

            </template>
        </el-table-column>--%>

        <el-table-column label='库存' width='100' prop='totalStock'>
        </el-table-column>
        <!--
        <el-table-column label='最后下单时间' width='100' prop='totalStock' class-name='breakspace'>
        </el-table-column>
    -->
    </control-table>

    <dialog-create :title="`产品详情`" :visible='app.controls.product_table.dialog.detail'
                   @update:visible='app.controls.product_table.dialog.detail=arguments[0]'
                   v-if='app.controls.product_table.selected' v-for='item in [app.controls.product_table.selected]'>
        <el-card>
            <el-row style='font-size: 12px; line-height: 24px;'>
                <el-col :span='8'>
                    <img :src='window.uploadPrefix + "/" + item.mainImageUrl' style='max-width: 100%;'>
                </el-col>
                <el-col :span='2'>&nbsp;</el-col>
                <el-col :span='14'>
                    ID：{{item.id}}<br> SPU: {{item.spu}}<br>
                    <control-item :app='app' :local='item' datakey='innerName' type='edit'
                                  @saved='app.controls.product_table.update(item, arguments[0])'></control-item>
                    标题: {{item.title}}<br>
                    品类: <span v-html="item.name || '暂无'"></span><br>
                    特性:
                    <el-tag v-if='item.classifyEnumName'>{{item.classifyEnumName}}</el-tag>
                    <span v-if='!item.classifyEnumName'>无</span>
                    <%--<span v-if='!item.sourceURL && !item.purchaseURL'>来源: 暂无</span><a
                        :href='window.app.normalizeURL(item.sourceURL)'>{{item.sourceEnumName}}</a> <a
                        :href='window.app.normalizeURL(item.purchaseURL)' v-if='item.purchaseURL'>采购链接</a><br>--%>
                    采购价: {{item.purchasePrice}}<br>
                    创建人: {{item.creator}}<br>
                    产品状态: {{item.stateName}}<br>
                    总库存: {{item.totalStock}}<br>
                    备注: {{item.memo}}<br>
                </el-col>
            </el-row>
            <div style='height: 20px'>&nbsp;</div>
            <el-row>
                <%@ include file="/WEB-INF/views/partial/product_attr.jsp" %>
            </el-row>
        </el-card>
    </dialog-create>
    <%@ include file="/WEB-INF/views/partial/product_fsm_page.jsp" %>

</template>


</body>
<js>
    <script>
        window.layoutData.controls.product_table = {
            data: [],
            search: {},
            searchParams: {
                id: "",
                spu: "",
                title: "",
                innerName: "",
                zoneId: "",
                createAt: [],
            },
            dialog: {
                detail: false,
            },
            revokeProduct: {},
            revokeDialogVisible: false,
            loading: false,
            initList: false,
            page: 1,
            pageSize: 20,
            total: 1000,
            dialogShow: false,
            checkedKeys: {},
            menus: [[]],
            selected: undefined,
            fsm: undefined,
            zones: [],
            editingName: '',
            group(items){
                var hash = {};
                if (!items) return [];
                items.forEach(function (item) {
                    var zoneInfo = app.pluck(item, ['departmentName', 'zoneCode', 'zoneState', 'stock', 'lastOrderAt'])
                    if (!hash[item.id]) {
                        hash[item.id] = Object.assign({}, item);
                    }
                    if (item.departmentName && item.zoneCode) {
                        if (!hash[item.id].zoneSet) {
                            hash[item.id].zoneSet = [zoneInfo];
                        } else {
                            hash[item.id].zoneSet.push(zoneInfo);
                        }
                    }
                });
                return Object.keys(hash).sort().reverse().map(x => hash[x]);
            },
            get(){
                let parameters = Object.assign({}, this.search, {limit: this.pageSize});
                app.util.formatRange(parameters, 'createAt', 'minCreateAt', 'maxCreateAt');
                delete parameters.createAt;
                this.loading = true;
                app.ajax.product.queryApproveList(parameters).then(result => {
                    this.data = this.group(result.data.item);
                    this.total = result.data.total;
                    this.loading = false;
                });
            },
            update(row, old){
                app.ajax.product.updateInnerName(app.pluck(row, ['id', 'innerName'])).then(_ => this.showDetail(row), _ => this.showDetail(row));
            },
            showDetail(row){
                window.top.app.open('/product/product/product/approve_detailsPage?id=' + row.id + "&iframe=" + row.id);
                /*
                 this.refresh = _ => this.showDetail(row);

                 app.ajax.product.detail({id: row.id}).then(x => {
                 app.controls.product_table.selected = x.data.item;
                 app.controls.product_table.dialog.detail = true;
                 app.service["attr/init"](app.controls.attrtable);
                 app.service["attrvalue/init"](app.controls.attrValueTable);
                 });
                 */
            },
            revokeProductThing(row){
                this.revokeProduct = row;
                this.revokeDialogVisible = true;

            },
            submitRevoke(){
                console.log("要撤回的产品id=====" + this.revokeProduct.id);
                app.ajax('/product/product/product/revokeProduct?id=' + this.revokeProduct.id).then(_ => {
                    this.get();
                    this.revokeDialogVisible = false;
                });
            },
            showFSM(row){
                window.showFSM(row.id, 'Product');
            }
        };

        var C = window.layoutData.controls;
        C.attrtable = {
            attrList: [],
            attrValueList: [],
            attrPanel: {
                tabName: 'attrList',
            },
            tag: 'product',
            search: {},
            attrName: "",
            loading: false,
            searchParams: {
                title: '',
                status: null,
                bindIs: null,
            },
            add: {},
            edit: {},
            pageSize: 20,
            page: 1,
            total: 1,
        };
        C.attrValueTable = {
            data: [],
            search: {},
            searchParams: {
                title: '',
                status: null,
            },
            pageSize: 20,
            page: 1,
            total: 1,
        };

        $(function () {
            var c = app.controls.product_table;
            app.ajax.zone.find().then(x => x.data.item).then(x => c.zones = x.map(y => ({
                key: y.title,
                value: y.code,
            })));
            app.service['common/pagination/init'](c, _ => c.get());
            c.get();
            app.controls.app.ajax.lang.find().then(x => x.data.item).then(x => x.map(x => ({
                key: x.name,
                value: x.langCode
            }))).then(x => app.controls.attrtable.langs = x);
        });
    </script>
</js>