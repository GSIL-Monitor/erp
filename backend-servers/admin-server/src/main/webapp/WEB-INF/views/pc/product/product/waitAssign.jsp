<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>

<template scope='app' slot='controls'>
    <H1>产品细化分类</H1>
</template>
<template scope='app'>
    <control-table :app='app' tablekey='newtable' :v-loading='app.controls.newtable.loading'
                   :style="`width: ${app.window.innerWidth - 250}  px;`"
                   @select-change="app.controls.newtable.handleSelectionChange"
                   @row-click="app.controls.newtable.rowClick">
        <template scope='app' slot='controls'>
            <el-row>
                <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='title'
                                  label='产品标题'></control-item>
                </el-col>

                <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='select' :listfunc='app.controls.app.firstCategoryListAll' listkey=''
                                  :local='app.local.searchParams' datakey='categoryId'
                                  label='待细化分类'></control-item>
                </el-col>

                <el-col :span='3' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='id' label='产品id'></control-item>
                </el-col>

                <el-col :span='2' style='padding: 5px 15px; '>
                    <el-button type='primary' icon='search' style='margin-top: 2px;'
                               @click='app.controls.newtable.findClick()' :loading='app.controls.newtable.loading'>搜索
                    </el-button>
                </el-col>

                <el-col :span='2' style='padding: 5px 15px; '>
                    <el-button type='primary' style='margin-top: 2px;'
                               @click='app.controls.newtable.dealCategorys()' :loading='app.controls.newtable.loading'>
                        细化分类
                    </el-button>
                </el-col>
            </el-row>

        </template>

        <el-table-column type='selection' width='50'></el-table-column>
        <el-table-column label='操作' width='100'>
            <template scope='inner'>
                <el-button size='mini' type='text' @click='app.controls.newtable.selcate(inner.row)'>细化分类
                </el-button>
            </template>
        </el-table-column>


        <el-table-column prop='categoryName' label='分类' width='120'>
            <template scope='inner'>
                            <span v-if='inner.row.name'>
                                <el-tag type='primary' size='mini'>{{inner.row.name}}</el-tag></span>
                <span><el-tag type='danger' v-if='!inner.row.name || inner.row.name==""'>品类未设置</el-tag></span>
                <span v-if='inner.row.name'><el-row
                        v-for='(item, index) in inner.row.name.split(",")' v-if="index>0">{{Array(index * 2 + 3).join("&nbsp;")}}<el-tag
                        type='success' size='mini'>{{item}}</el-tag></el-row></span>
            </template>

        </el-table-column>
        <el-table-column label='产品图片' width='150'>
            <template scope='inner'>
                <el-row>
                    <el-col style='width: 64px; margin-right: 16px;'><img
                            :src='window.uploadPrefix + "/" + inner.row.mainImageUrl'
                            style='width: 120px; height: 140px;'></el-col>
                </el-row>
            </template>
        </el-table-column>

        <el-table-column label='产品信息' width='250'>
            <template scope='inner'>
                <el-row>
                    <el-col :span='12'>
                        <el-row>{{inner.row.title}}</el-row>
                        <el-row>ID: {{inner.row.id}}</el-row>
                        <el-row v-if="inner.row.spu">SPU: {{inner.row.spu}}</el-row>
                        <el-row v-if="inner.row.classifyEnumName!=null && inner.row.classifyEnumName!=''">
                            <span><el-tag>{{inner.row.classifyEnumName}}</el-tag></span></el-row>
                        <el-row v-if="inner.row.customEnum!=null && inner.row.customEnum!='normal'">
                            <span><el-tag>{{inner.row.customEnumName}}</el-tag></span>
                        </el-row>
                        <el-row v-if="inner.row.goalEnum!=null && inner.row.goalEnum!='normal'">
                            <span><el-tag>{{inner.row.goalEnumName}}</el-tag></span>
                        </el-row>
                    </el-col>
                </el-row>
                <el-row>{{inner.row.title}}</el-row>

            </template>
        </el-table-column>

        <el-table-column label='来源' prop='sourceEnumName' width='90'>
            <template scope='inner'>
                <el-row>{{inner.row.sourceEnumName}}</el-row>
                <el-row v-if="inner.row.sourceUrl"><a :href='window.app.normalizeURL(inner.row.sourceUrl)'
                                                      target='_blank'>来源链接</a></el-row>
                <%--<el-row v-if="inner.row.purchaseUrl"><a :href='window.app.normalizeURL(inner.row.purchaseUrl)'
                                                        target='_blank'>采购链接</a></el-row>--%>
            </template>

        </el-table-column>

        <el-table-column label='状态' width='100'>
            <template scope='inner'>
                <el-row>{{inner.row.stateName}}</el-row>
            </template>

        </el-table-column>
        <%--<el-table-column prop='memo' label='备注' width='100'></el-table-column>--%>
        <el-table-column label='备注' width='240'>
            <template scope="inner">
                <el-row>{{inner.row.memo}}</el-row>
                <el-row v-if="inner.row.attributeDesc">
                    <a href='javascript: void(0);'
                       @click='app.controls.newtable.showAttributeDesc(inner.row.attributeDesc)'>属性描述</a>
                </el-row>
            </template>
        </el-table-column>
    </control-table>

    <dialog-create :title='"细化分类"' :visible='app.controls.newtable.dialog.category' :controls='window.app.controls'
                   @update:visible='val => window.app.controls.newtable.dialog.category = val'
                   @ok='app.controls.newtable.saveCategory()'>
        <el-card>
            <el-row>
                <el-col style='padding: 5px 15px;'>
                    <control-item :local='app.controls.newtable.cate' datakey='id' label='产品ID'
                                  type='readonly'></control-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col style='padding: 5px 15px;'>
                    <control-item :local='app.controls.newtable.cate' datakey='title' label='名称'
                                  type='readonly'></control-item>
                </el-col>
            </el-row>
            <el-row>
                <%-- <el-col style='padding: 5px 15px;'>
                    <control-item :local='app.controls.newtable.cate'
                                  :list='app.controls.newtable.catelist'
                                  datakey='categoryIds' label='设置分类' type='selectcascade'
                                  select-leaf>
           			</control-item>
                </el-col> --%>
                <%-- <el-col style='padding: 5px 15px;'>
                	<control-item type='selectAsync' 
                           :complete-array-url-func="url => window.app.ajax('/leafSearch?name=' + url).then(result => result.data.item)"
                           :complete-props = "{value: 'id', label: 'name'}"
                           :local='app.controls.newtable.cate' 
                           datakey='categoryId'
                           label='设置分类'>
                     </control-item>
                </el-col> --%>
                <el-col style='padding: 5px 15px;'>
                	<control-item type='selectAsync' 
                           :complete-array-url-func="url => window.app.ajax('/leafSearch?name=' + url).then(result => result.data.item)"
                           :complete-props = "{value: 'id', label: 'name'}"
                           :local='app.controls.newtable.cate' 
                           datakey='categoryId'
                           label='设置分类'>
                     </control-item>
                </el-col>
            </el-row>
        </el-card>
    </dialog-create>

    <dialog-create :title='"属性描述"' :visible='app.controls.newtable.dialog.attributeDesc'
                   @update:visible='val => window.app.controls.newtable.dialog.attributeDesc = val'
                   @ok='app.controls.newtable.dialog.attributeDesc=false'
                   :controls='window.app.controls' size="tiny">
        <control-item class="attr-desc" v-html="app.controls.attributeDescData.attribute_desc">
        </control-item>
    </dialog-create>
</template>
</body>
<js>
    <script>
        var C = window.layoutData.controls;
        C.page = 'checkList';
        window.layoutData.common = {
            firstCategoryList: [],
        };
        C.attributeDescData = {
            attribute_desc: ""
        };
        C.newtable = {
            data: [],
            search: {
                usable: 1,
                checkerId: window.user,
            },
            searchParams: {
                id: "",
                title: "",
                categoryId: ""
            },
            loading: false,
            initList: false,
            page: 1,
            pageSize: 20,
            total: 1000,
            dialog: {
                add: false,
                category: false,
                region: false,
                edit: false,
                attributeDesc: false
            },
            cate: {
                id: undefined,
                title: undefined,
                regions: undefined,
                categoryIds: [],
            },
            multipleTable: [],
            get(){
                this.loading = true;
                let parameters = Object.assign({}, this.searchParams, {
                    limit: this.pageSize,
                    start: this.page * this.pageSize - this.pageSize
                });
                app.ajax.product.getWaitDivideProduct(parameters).then(x => {
                    this.data = x.data.item;
                    var total = this.total = x.data.total;
                    this.loading = false;
                });
            },
            findCategorys(){
                var opt = {url: '/tree', data: 'no=', method: 'post'};
                app.controls.app.ajax(opt.url, opt).then(x => x.data.item.children).then(x => {
                    this.catelist = x;
                });
            },
            selcate(obj){
                this.cate = Object.assign({}, obj);
                this.cate.categoryId = "";
              //  this.cate.categoryIds = app.findCategoryPath(this.catelist, this.cate.categoryId);
                this.dialog.category = true;
            },
            findClick(){
                this.get();
            },
            saveCategory(){
                //防止重复提交，当点击完确定按钮后，将按钮变成不可点击样式，动作完成之后恢复
                app.controls.app.clickShow.clickFlag = true;
                let a = app.pluck(this.cate, ['id', 'categoryId']);
                if (a.categoryId != null) {
//                     a.categoryId = a.categoryId.slice(-1)[0];
//                    delete a.categoryId;
                    console.log("ids:" + JSON.stringify(a.id) + "==categoryId:" + a.categoryId);
                    var param = {id: JSON.stringify(a.id), categoryId: a.categoryId};
                    var url = "/product/product/product/updateCategoryBatch";
                    if (!(a.id instanceof Array)) {
                        url = "/product/product/product/updateCategory"
                    }
                    app.ajax(url, {
                        method: 'post',
                        data: app.postform(param)
                    }).then(data => {
                        app.controls.newtable.dialog.category = false;
                        app.controls.app.clickShow.clickFlag = false;
                        this.get();
                    }, function () {
                        app.controls.app.clickShow.clickFlag = false;
                    });
                } else {
                    app.$message({
                        message: '请设置分类',
                        type: 'error'
                    });
                    app.controls.app.clickShow.clickFlag = false;
                }
            },
            dealdealCategorys(){
                console.log("批量细化分类" + app.controls.newtable.multipleTable);
                if (app.controls.newtable.multipleTable.length > 0) {
                    var ids = [];
                    var titles = [];
                    app.controls.newtable.multipleTable.forEach(function (x) {
                        ids.push(x.id);
                        titles.push(x.title);
                    });
                    this.cate.id = ids;
                    this.cate.title = titles;
                    this.dialog.category = true;
                } else {
                    app.$message({
                        message: '至少选择一个',
                        type: 'warning'
                    });
                }

            },
            handleSelectionChange(val){
                app.controls.newtable.multipleTable = val;
                console.log("状态值" + app.controls.newtable.multipleTable);
            },
            rowClick(row, obj){
                obj.toggleRowSelection(row);
            }
        };
        $(function () {
            var c = app.controls.newtable;
            app.service['common/pagination/init'](c, _ => c.get());
            c.get();
            c.findCategorys();
        });
    </script>
</js>