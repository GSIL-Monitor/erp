<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <body>

            <template scope='app' slot='controls'>
                <H1>新品管理（广告）</H1>
            </template>
            <template scope='app' >
                <control-table :app='app' tablekey='newtable' :style="`width: ${app.window.innerWidth - 250}  px;`" v-loading='app.controls.newtable.loading'>
                    <template scope='app' slot='controls'>
                        <el-row>
                            <el-col :span='8' style='padding: 5px 15px;'>
                                <control-item type='daterange' :local='app.local.searchParams' datakey='createAt' label='创建时间' :actionfunc='window.alert.bind(window)'
                                    :actionkey='"Hello world"'></control-item>
                            </el-col>
                            <el-col :span='6' style='padding: 5px 15px;'>
                                <control-item type='input' :local='app.local.searchParams' datakey='title' label='产品标题'></control-item>
                            </el-col>
                            <el-col :span='6' style='padding: 5px 15px;'>
                                <control-item type='select' :listfunc='app.controls.app.enum' listkey='ProductNewState' :local='app.local.searchParams' datakey='state'
                                    label='状态'></control-item>
                            </el-col>
                            <el-col :span='6' style='padding: 5px 15px;'>
                                <control-item type='select' :listfunc='app.controls.app.firstCategoryListFunc' listkey='' :local='app.local.searchParams' datakey='topCategoryId'
                                    label='一级分类'></control-item>
                            </el-col>
                            <el-col :span='6' style='padding: 5px 15px;'>
                                    <el-button type='primary' icon='search' style='margin-top: 2px;' @click='app.controls.newtable.searchClick()' :loading='app.controls.newtable.loading'>搜索</el-button>
                            </el-col>
                            <el-col :span='12' style='padding: 5px 15px;'>
                                    &nbsp;
                            </el-col>
                        </el-row>
                        <el-row>    
                            <el-col :span='6' style='padding: 5px 15px;'>
                                <el-button type='new' icon='plus' style='margin-top: 2px;'
                                           @click='app.controls.newtable.addClick()'
                                           :disabled='!app.controls.app.Perm.write'>新增
                                </el-button>
                            </el-col>
                            
                        </el-row>

                    </template>
                    <%@ include file='/WEB-INF/views/partial/productnew_control.jsp' %>
                    <el-table-column prop='topCategoryName' label='一级分类' width='100'></el-table-column>
                    <el-table-column label="图片" width="150">
                        <template scope='inner'>
                            <img :src='window.uploadPrefix + "/" + inner.row.mainImageUrl'
                                 style='width: 120px; height:140px;'>
                        </template>
                    </el-table-column>

                    <el-table-column label='产品信息' width='220'>
                        <template scope='inner'>
                             
                                <el-row>
                                    <%--<el-col style='width: 64px; margin-right: 16px;'><img :src='window.uploadPrefix + "/" + inner.row.mainImageUrl' style='width: 64px; height: 64px;'></el-col>--%>
                                        <el-col :span='12'>
                                            <el-row>新品ID: {{inner.row.id}}</el-row>
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
                    <el-table-column prop='countryName' label='区域' width='125'>
                        <template scope='inner'>
                            <el-row v-if='inner.row.state == "draft"'>
                                <el-button size='mini' @click='app.controls.newtable.regionedit(inner.row.id)' v-if="!inner.row.zoneName">设置</el-button>
                            </el-row>
                            <el-row v-for='item in inner.row.productNewZones'>
                                {{item.zoneName}}-{{item.zoneCode}}
                            </el-row>
                        </template>
                    </el-table-column>
                  
                    <el-table-column label='来源' prop='sourceEnumName' width='125'>
                        <template scope='inner'>
                            <el-row>{{inner.row.sourceEnumName}}</el-row>
                            <el-row><a :href='window.app.normalizeURL(inner.row.sourceUrl)' target='_blank'>来源链接</a> </el-row>
                            <%--去掉采购url--%>
                            <%--<el-row><a :href='window.app.normalizeURL(inner.row.purchaseUrl)' target='_blank'>采购链接</a> </el-row>--%>
                        </template>
                    </el-table-column>
                    <el-table-column label='审核' width='120'>
                        <template scope='inner'>
                            {{inner.row.checker}}
                        </template>
                    </el-table-column>
                    <el-table-column label='状态' width='120' class='breakspace'>
                        <template scope='inner'>
                            <el-row>{{inner.row.stateName}}</el-row>
                            <el-row class='breakspace'><a href='javascript: void(0);' @click='window.showFSM(inner.row.id, "ProductNew")'>{{inner.row.stateTime}}</a></el-row>
                        </template>

                    </el-table-column>
                    <%--<el-table-column prop='memo' label='备注' width='80'></el-table-column>--%>
                    <el-table-column label='备注' width='240'>
                        <template scope="inner">
                            <el-row>{{inner.row.memo}}</el-row>
                            <el-row v-if="inner.row.attributeDesc"><a href='javascript: void(0);' @evt="app.controls.newtable.fn(1,2,3)"
                                                                      @click='app.controls.newtable.showAttributeDesc(inner.row.attributeDesc)'>属性描述</a>
                            </el-row>
                        </template>
                    </el-table-column>
                </control-table>
                <%@ include file='/WEB-INF/views/partial/product_new.jsp' %>
            </template>
        </body>
        <js>
            <script>
                var C = window.layoutData.controls;
                C.page = 'adList';
                window.layoutData.common = {
                    firstCategoryList: [],
                };
                C.attributeDescData = {
                    attribute_desc: ""
                };
                C.newregion = {
                    data: [],
                    search: {},
                    searchParams: {
                        title: "",
                        stateTime: undefined,
                        categoryId: "",
                        topCategoryId: "",
                        state: "",
                    },
                    loading: false,
                    initList: false,
                    page: 1,
                    pageSize: 20,
                    total: 1000,
                    countryName: undefined,
                    countryCode: undefined,
                }
                C.newtable = {
                    readonly: true,
                    url: '/queryAdList',
                    data: [],
                    search: {
                        usable: 1,
                    },
                    searchParams: {
                        title: "",
                        createAt: [],
                        topCategoryId: '',
                        state: "",
                        usable: 1,
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
                    add: {
                        validate: {},
                        classifyEnum: "",
                        categoryId: "",
                        title: "",
                        purchaseUrl: "",
                        mainImageUrl: "",
                        memo: "",
                        topCategoryId: "",
                        sourceEnum: "",
                        sourceUrl:"",
                        customEnum: "",
                        goalEnum: "",
                        spu: "",
                        attributeDesc: "",
                        innerName: ""

                    },
                    edit: {
                        validate: {},
                        classifyEnum: "",
                        categoryId: "",
                        title: "",
                        purchaseUrl: "",
                        mainImageUrl: "",
                        memo: "",
                        topCategoryId: "",
                        sourceEnum: "",
                        sourceUrl:"",
                        customEnum: "",
                        goalEnum: "",
                        spu: ""
                    },
                    cate: {
                        id: undefined,
                        title: undefined,
                        regions: undefined,
                        categoryIds: [],
                    },
                    region: {
                        countryCode: undefined,
                        countryName: undefined,
                    }
                }
                initFSM();
                $(function () {
                    app.service['productNewAd/init'](app.controls.newtable);
                });
            </script>
        </js>