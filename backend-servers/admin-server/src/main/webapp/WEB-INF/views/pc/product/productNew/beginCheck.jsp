<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <body>
            <template scope='app' slot='controls'>
                <H1>开始查重</H1>
            </template>
            <el-card :style="`width: \${window.innerWidth}px;`" tablekey='newtable' :loading='app.controls.newtable.loading'>

                <el-row>
                    <el-col :span='6'>
                        <el-card v-if='app.controls.currentProduct' id='floatBar'>
                            <el-row v-for='item in app.controls.currentProduct'>
                                <el-row>
                                    <el-col>
                                        <img :src='(window.uploadPrefix + "/" + item.mainImageUrl) || "/static/images/placeholder.jpg"' style='max-width: 100%;'>
                                    </el-col>
                                </el-row>
                                <div style='height: 16px;'>&nbsp;</div>
                                <el-row>
                                    <el-col :span='8'>
                                        <el-button type='warn' icon='close' size='medium'
                                                   :actionkey='{type: "warn", id:item.id, row: item}'
                                                   actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                                                   :loading='app.controls.newtable.running'>有风险
                                            <%--v-if="!item.spu"--%>
                                        </el-button>
                                    </el-col>
                                    <el-col :span='8'>
                                        <el-button type='success'
                                                   icon='check' size='medium'
                                                   :actionkey='{type: "leftCheckOk", id: item.id, row: item}'
                                                   actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                                                   :loading='app.controls.newtable.running'>通过
                                        </el-button>
                                        <%--v-if="!item.spu"--%>
                                    </el-col>
                                    <el-col :span='8'> 
                                        <el-button type='info' icon='edit' size='medium' :actionkey='{type: "decline", id: item.id, row: item}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">拒绝</el-button>
                                    </el-col>
                                </el-row>
                                <div style='height: 16px;'>&nbsp;</div>
                                <el-row>
                                    <el-col style='font-size: 12px; line-height: 18px; color: #AAA; '>
                                        ID: {{item.id}}<br>
                                        SPU:{{item.spu}}
                                        <BR> 标题: {{item.title}}
                                        <el-tag v-if="item.classifyEnumName!=null && item.classifyEnumName!=''">
                                            {{item.classifyEnumName}}
                                        </el-tag>
                                        <el-tag v-if="item.customEnum!=null && item.customEnum!='normal'">
                                            {{item.customEnumName}}
                                        </el-tag>
                                        <el-tag v-if="item.goalEnum!=null && item.goalEnum!='normal'">
                                            {{item.goalEnumName}}
                                        </el-tag>
                                        <br>
                                        创建人: <span style='font-weight: bold; color: #1c2ccc; font-size: 15px;'>{{item.creator}}</span>
                                        <span style='font-weight: bold; color: #1c2ccc; font-size: 15px;'>{{item.departmentName}}</span>
                                        {{item.createAt}}
                                        <br>
                                        一级分类: {{item.topCategoryName}}<br>
                                        细化分类: {{item.categoryName}}<br>
                                        <%--{{item.creator}} {{item.departmentName}} {{item.createAt}}--%>
                                        来源: {{item.sourceEnumName}}
                                        <a :href='window.app.normalizeURL(item.sourceUrl)' target="_blank">来源链接</a>
                                        <%--<a :href='window.app.normalizeURL(item.purchaseUrl)'--%>
                                        <%--target="_blank">采购链接</a>--%><br>
                                        业务区域:
                                        <span v-for="zone in item.productNewZones"
                                              style="color: #1c2ccc; font-size: 15px;">{{zone.zoneName}} </span>
                                        <span v-for='r in item.productNewCountries'
                                              style="color: #1c2ccc; font-size: 15px;">{{r.countryName}}-{{r.countryCode}}</span><br>
                                        备注: {{item.memo}}<br>
                                        属性描述: <span class="attr-desc"
                                                    v-html="window.app.formatHtml(item.attributeDesc)"></span>
                                    </el-col>
                                </el-row>
                            </el-row>
                        </el-card>
                    </el-col>
                    <el-col :span='18'>
                        <el-row>
                            <el-col :span='12' style='padding: 5px 15px 5px 0px;' v-if='false'>
                                <control-item type='input' :local='app.controls.newtable.searchParams' datakey='regionId' label='区域'></control-item>
                            </el-col>
                            <el-col :span='10' style='padding: 5px 15px 5px 0px;'>
                                <control-item type='selectcascade' :treetolist='true' :treelistprops='{key: "name", value: "id", children: "children"}' :treeprops='{label: "name", children: "children", value: "id"}'
                                              :list='app.controls.newtable.catelist'
                                              :local='app.controls.newtable.searchParams' datakey='categoryIds'
                                              :item-disabled='app.controls.app.hide.readOnly'
                                              label='详细分类'></control-item>
                            </el-col>
                            <el-col :span='5' style='padding: 5px 15px;'>
                                <control-item type='input' :local='app.controls.newtable.searchParams' datakey='title'
                                              label='产品标题'
                                              :item-disabled='app.controls.app.hide.readOnly'
                                ></control-item>
                            </el-col>
                            <el-col :span='5' style='padding: 5px 15px;'>
                                <control-item type='input' :local='app.controls.newtable.searchParams' datakey='spu'
                                              :item-disabled='app.controls.app.hide.readOnly'
                                              label='SPU'></control-item>
                            </el-col>
                            <el-col :span='5' style='padding: 5px 15px 5px 0px;'>
                                <control-item type='selectmultiple' :listfunc='app.controls.app.enum' listkey='ProductState' :local='app.controls.newtable.searchParams'
                                              :item-disabled='app.controls.app.hide.readOnly'
                                              datakey='productStates' label='状态'></control-item>
                            </el-col>
                            <el-col :span='5' style='padding: 5px 15px;'>
                                <control-item type='input' :local='app.controls.newtable.searchParams' datakey='id'
                                              :item-disabled='app.controls.app.hide.readOnly'
                                              label='产品id'></control-item>
                            </el-col>
                            <el-col :span='5' style='padding: 5px 15px;'>
                                <control-item type='select' :list='[{key: "效果1", value: 1}, {key: "效果2", value: 2}, {key: "效果3", value: 3}]' :local='app.controls.newtable'
                                              :item-disabled='app.controls.app.hide.readOnly'
                                              datakey='test' label='区域显示方式'></control-item>
                            </el-col>
                            <el-col :span="3" style="line-height: 42px;margin-left: 30px;padding-left: 33px;">
                                <el-checkbox label="系统推荐比对" datakey="isSystemMatch"
                                             :local='app.controls.newtable.searchParams'
                                             @change="app.controls.newtable.compare()"></el-checkbox>
                            </el-col>

                            <el-col :span='4' style='padding: 5px 15px;'>
                                <el-button :loading='app.controls.newtable.loading' type='primary' icon='search' style='margin-top: 2px;' @click='app.controls.newtable.doSearch()'>搜索</el-button>
                                <el-button type='primary' style='margin-top: 2px;' @click='app.controls.newtable.dealCategorys()' :loading='app.controls.newtable.loading'>
                                    细化分类
                                </el-button>
                            </el-col>
                        </el-row>
                        <el-row :style='`position: relative; display: flex; flex-flow: row wrap; height: \${window.innerHeight-200}px; overflow-y: auto;`'
                            v-loading='app.controls.newtable.loading'>
                            <template v-for='(item,index) in app.controls.newtable.data'>    
                                <div :style="'position: relative; height: auto; width: 15%; margin-bottom: 15px; margin-right:' + (index % 6 == 5 ? 0 : 10) + 'px;'">
                                    <el-card>
                                        <el-row><img :src='window.uploadPrefix + "/" + item.mainImageUrl'
                                                     style='position: relative; width: 100%;height: 155px;'></el-row>
                                        <div style='height: 15px;'>&nbsp;</div>
                                        <el-row>
                                            <el-button type='success' size='mini' :actionkey='{type: "rightCheckOk", id: item.id, row: item, spu: item.spu, prefix: `和[\${item.spu}][\${item.name}]产品有关联通过`}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">通过</el-button>
                                            <el-button type='danger' size='mini' :actionkey='{type: "warn", id: item.id, row: item, spu: item.spu, prefix: `和[\${item.spu}][\${item.name}]产品有风险`}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">有风险</el-button>
                                            <span><el-checkbox v-model='item.selected'>选中</el-checkbox></span>
                                        </el-row>

                                        <el-row>
                                            <!--<span v-if='item.productZoneList  && item.productZoneList.length' style='font-size: 12px; line-height: 22px;'> </span>-->
                                            <%--  <span :title="(item.productZoneList||[]).map(x => x.zoneName).join(',')" v-if='item.productZoneList  && item.productZoneList.length'
                                                  style='white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: inline-block; width: 100px; position: relative; font-size: 12px; line-height: 22px;'>
                                                  在售区域:
                                          <span v-for='r in item.productZoneList||[]' style='font-size: 12px; line-height: 22px;'>
                                              {{r.zoneName}}
                                          </span></span>--%>
                                            <span v-if='!(item.productZoneList && item.productZoneList.length)'
                                                            style='font-size: 12px; line-height: 22px;'>未设置区域</span><br>
                                            <span>
                                                    <el-switch style='float: right;'v-model='item.showDetail' on-text='隐藏' off-text='显示' size='mini' style='font-size: 12px;'></el-switch>
                                                </span>
                                        </el-row>
                                        <el-row v-if='item.showDetail' style='font-size: 12px;'>
                                            ID：{{item.id}}<br> SPU: {{item.spu}}<br> 标题: {{item.title}}<br> 分类:
                                            {{item.name || '暂无'}}<br>相似度：{{item.siftValue == null ? '暂无':
                                            item.siftValue+'%'}}<br>
                                            <%--<span v-if='!item.sourceURL && !item.purchaseURL'>来源: </span><a
                                                :href='window.app.normalizeURL(item.sourceURL)'>{{item.sourceEnumName ||
                                            '暂无'}}</a>
                                            <a :href='window.app.normalizeURL(item.purchaseURL)' v-if='item.purchaseURL'>采购链接</a><br> --%>
                                            库存: {{item.totalStock}}<br> 审核: {{item.checker || '暂无'}}<br> 区域:

                                            <el-tabs v-if='item.app.controls.newtable.test == 1'>
                                                <el-tab-pane v-for='r in item.productZoneList||[]' :key='r.zoneId' :name='r.zoneId'>
                                                    <span slot='label' style='font-weight: bold; font-size: 12px;'>{{r.zoneName}}</span>
                                                    {{r.stateTime && window.app.util.formatDate(new Date(Date.parse(r.stateTime)))}}<br>{{r.departmentName}}
                                                    {{r.stateName || r.state}}
                                                    <el-button type='danger' size='mini' style='margin-left: 5px;'
                                                               :actionkey='{type: "duplicate", id: item.id, row: item, spu: item.spu, productZoneId: r.id, prefix: `[\${item.name}]下的[\${item.spu}]产品已经有了区域[\${r.zoneName}],不能重复`}'
                                                               actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                                                               :loading='app.controls.newtable.running'>
                                                        重复
                                                    </el-button>

                                                </el-tab-pane>
                                            </el-tabs>

                                            <div v-for='r in item.productZoneList||[]' v-if='item.app.controls.newtable.test == 2'>
                                                <span style='font-weight: bold; font-size: 12px;'>[{{r.zoneName}}]</span><br>
                                                {{r.stateTime && window.app.util.formatDate(new Date(Date.parse(r.stateTime)))}}<br>{{r.departmentName}}
                                                {{r.stateName || r.state}}
                                                <el-button type='danger' size='mini' style='margin-left: 5px;'
                                                           :actionkey='{type: "duplicate", id: item.id, row: item, spu: item.spu, productZoneId: r.id, prefix: `[\${item.name}]下的[\${item.spu}]产品已经有了区域[\${r.zoneName}],不能重复`}'
                                                           actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                                                           :loading='app.controls.newtable.running'>
                                                    重复
                                                </el-button>
                                                <br>
                                            </div>

                                            <el-popover v-if='item.app.controls.newtable.test == 3' trigger='hover'>
                                                <el-button slot='reference' type='success' icon='more' size='mini'>显示区域</el-button>
                                                <el-table :data='item.productZoneList||[]' class='main-table' style='width: 500px;'>
                                                    <el-table-column prop='zoneName' label='区域'>
                                                    </el-table-column>
                                                    <el-table-column prop='stateTime' label='状态时间' class-name='breakspace'></el-table-column>
                                                    <el-table-column prop='stateName' label='状态'></el-table-column>
                                                    <el-table-column prop='departmentName' label='部门'></el-table-column>
                                                    <el-table-column prop='creator' label='创建人'></el-table-column>
                                                    <el-table-column>
                                                        <template scope='inner'>
                                                            <el-button type='danger' size='mini'
                                                                       style='margin-left: 5px;'
                                                                       :actionkey='{type: "duplicate", id: item.id, row: item, spu: item.spu, productZoneId: inner.row.id, prefix: ` `}'
                                                                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                                                                       :loading='app.controls.newtable.running'>
                                                                重复
                                                            </el-button>
                                                        </template>
                                                    </el-table-column>
                                                </el-table>
                                            </el-popover>
                                        </el-row>
                                    </el-card>
                                </div>
                                <br v-if='index % 6 == 5'>
                            </template>

                        </el-row>
                        <el-pagination class='end' @size-change="app.controls.newtable.pagesizeChange(arguments[0])" @current-change="app.controls.newtable.pageChange(arguments[0])"
                            :current-page.sync="app.controls.newtable.page" :page-size="app.controls.newtable.pageSize" layout="total, sizes, prev, pager, next, jumper"
                            :total="app.controls.newtable.total">
                        </el-pagination>
                    </el-col>


                </el-row>





            </el-card>


    <dialog-create :title='"设置分类"' :visible='app.controls.newtable.dialog.category' :controls='window.app.controls'
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
        </body>
        <js>
            <style>
                a {
                    text-decoration: none;
                    color: #0080FF;
                }

                .el-col {
                    padding: 5px 15px;
                }
            </style>


            <script>
                var C = window.layoutData.controls;
                C.currentProduct = undefined;
                C.newtable = {
                    catelist: [],
                    data: [],
                    search: {},
                    searchParams: {
                        title: "",
                        stateTime: undefined,
                        categoryId: "",
                        categoryIds: [],
                        topCategoryId: "",
                        productStates: ['onsale', 'archiving', 'offsale', 'waitFill'],
                        spu: "",
                        id: "",
                        isSystemMatch: false,
                        productNewId: ""
                    },
                    test: 3,
                    selected: {},
                    loading: false,
                    initList: false,
                    page: 1,
                    pageSize: 100,
                    running: false,
                    total: 1000,
                    dialogShow: false,
                    dialog:{
                        category: false,
                    },
                    cate:{
                        id: [],
                        categoryId: [],
                        title: [],
                    },
                    add: {

                    },
                    createEle (ele,options,) {

                    },
                    action(opt) {
                        app.$prompt("备注:").then(function (x) {
                            app.controls.newtable.running = true;
                            if (x.action == 'confirm') {
                                if(opt.type == 'leftCheckOk'){
                                    opt.spu = '';
                                    opt.type = 'checkOk';
                                }else if(opt.type == 'rightCheckOk'){
                                    opt.type = 'checkOk';
                                }
                                var actionParameters = {
                                    event: opt.type,
                                    state: app.controls.currentProduct[0].stateEnum,
                                    //      memo: (opt.prefix || "") + (opt.prefix ? " " : "") + x.value,
                                    memo: x.value,
                                    id: app.controls.currentProduct[0].id,
                                    fsmName: 'ProductNew',
                                    spu: opt.spu,
                                    productZoneId: opt.productZoneId
                                }
                                app.ajax("/product/product/productNew/processEvent", {
                                    method: 'post',
                                    data: app.postform(actionParameters)
                                }).then(function () {
                                    app.controls.newtable.running = false;
                                    top.app.removeTab(window.app.subName);
                                }).catch(function () {
                                    app.controls.newtable.running = false;
                                });
                            }
                        });
                    },
//                    action(opt) {
////
//                        const h = window.app.$createElement;
//                        console.log(h)
//                        app.$msgbox({
//                            title: '备注:',
//                            message: h('textarea', {style:'width:300px'}, [
//
//                            ]),
//
//
//                        }).then(function (x) {
//
//                            console.log(x,1111)
////                            x.action
//                            if (x == 'confirm') {
//                                var actionParameters = {
//                                    event: opt.type,
//                                    state: app.controls.currentProduct[0].stateEnum,
//                                    memo: (opt.prefix || "") + (opt.prefix ? " " : "") + x.value,
//                                    id: app.controls.currentProduct[0].id,
//                                    fsmName: 'ProductNew',
//                                    spu: opt.spu,
//                                    productZoneId: opt.productZoneId
//                                }
//                                console.log(actionParameters)
//                                app.ajax("/product/product/productNew/processEvent", { method: 'post', data: app.postform(actionParameters) }).then(function () {
//                                    top.app.removeTab(window.app.subName);
//                                    console.log(window.app.subName)
//                                }).catch(function () {
//
//                                });
//                            }
//                        });
//                    },
                    get() {
                        this.search.limit = this.pageSize;
                        this.search.start = (this.page - 1) * this.pageSize;
                        this.loading = true;
                        let parameters = Object.assign({}, C.newtable.search, { limit: C.newtable.pageSize });
                        if (parameters.categoryIds && parameters.categoryIds.length) {
                            parameters.categoryId = parameters.categoryIds.slice(-1)[0];
                            delete parameters.categoryIds;
                        } else {
                            delete parameters.categoryId;
                        }
                        parameters.productStates = parameters.productStates.join(",")
                        app.ajax.product.find(parameters).then(x => {
                            var selected = {};
                            C.newtable.data = x.data.item && x.data.item.slice().map(y => { y.showDetail = false; y.selected = false; selected[y.id] = false; y.app = app; return y; });
                            C.newtable.selected = selected;
                            C.newtable.total = x.data.total;
                            C.newtable.loading = false;
                        })
                    },
                    dealCategorys(){
                        var data = app.controls.newtable.data.filter(x => x.selected);
                        if (data.length > 0) {
                            var ids = [];
                            var titles = [];
                            data.forEach(function (x) {
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

                    doSearch() {
                        Object.assign(this.search, this.searchParams);
                        this.get();
                    },
                    compare(){
                        var flag = this.searchParams.isSystemMatch;
                        if (flag) {
                            app.controls.app.hide.readOnly = false;
                            this.searchParams.isSystemMatch = false;
                        } else {
                            app.controls.app.hide.readOnly = true;
                            this.searchParams.isSystemMatch = true;
                        }
                        console.log("系统推荐比对状态是否选中：" + this.searchParams.isSystemMatch + "新品id:" + this.searchParams.productNewId);
                        Object.assign(this.search, this.searchParams);
                        this.get();
                    }
                };
                $(function () {

                    app.ajax.category.tree().then(x => {
                        app.controls.newtable.catelist = x.data.item.children
                        app.controls.newtable.searchParams.productNewId = '${id}';
                        app.ajax("/product/product/productNew/get", { params: { id: ${id }}, method: 'GET'}).then(x => {
                            app.controls.currentProduct = [x.data.item];
                            app.controls.newtable.searchParams.spu = app.controls.currentProduct[0].spu;
                            setTitle(x.data.item.title);
                            app.controls.newtable.searchParams.categoryId = x.data.item.categoryId;
                            app.controls.newtable.searchParams.categoryIds = app.findCategoryPath(app.controls.newtable.catelist, x.data.item.categoryId);
                            app.service['common/pagination/init'](C.newtable, _ => C.newtable.get());
                            C.newtable.doSearch();
                            /*
                            let f;
                            $(window).on('scroll', function(){
                                if(f) clearTimeout(f);
                                f = setTimeout(function(){
                                    var scrolltop=document.documentElement.scrollTop||document.body.scrollTop;
                                    if(scrolltop > 100){
                                        $("#floatBar").clearQueue().animate({'top': scrolltop + 10});
                                        $("#floatBar").css('position', 'absolute');
                                    }else{
                                        $("#floatBar").animate({'top': 0});
                                        $("#floatBar").css('position', 'absolute');
                                    }
                                }, 500);
                            })
                            */
                        });
                    });
                   
                });
            </script>
        </js>