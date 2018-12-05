<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

        <body>

            <template scope='app' slot='controls'>
                <H1>新品审批（主管）</H1>
            </template>
            <template scope='app'>
                <control-table :app='app' tablekey='newtable' :style="`width: ${app.window.innerWidth - 250}  px;`"  v-loading='app.controls.newtable.loading'>
                    <template scope='app' slot='controls'>
                        <el-row>
                            <el-col :span='4' style='padding: 5px 15px;'>
                                <control-item type='selectAsync' 
                                    :complete-array-url-func="url => window.app.ajax('/admin/user/userAutoComplement?condition=' + url).then(result => result.data.item)"
                                    :complete-props = "{value: 'id', label: 'lastname'}"
                                    :local='app.local.searchParams' 
                                    datakey='creatorId'
                                    label='创建人'></control-item>
                            </el-col>
                            <el-col :span='7' style='padding: 5px 15px;'>
                                <control-item type='daterange' :local='app.local.searchParams' datakey='createAt' label='创建时间' ></control-item>
                            </el-col>
                            <el-col :span='6' style='padding: 5px 15px;'>
                                <control-item type='input' :local='app.local.searchParams' datakey='title' label='产品标题'></control-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span='4' style='padding: 5px 15px;'>
                                <control-item type='select' :listfunc='app.controls.app.enum' listkey='ProductNewState' :local='app.local.searchParams' datakey='state'
                                    label='状态'></control-item>
                            </el-col>
                           
                            <el-col :span='3' style='padding: 5px 15px;'>
                                    <control-item type='input' :local='app.local.searchParams' datakey='id' label='新品id'></control-item>        
                            </el-col>

                            <el-col :span='4' style='padding: 5px 15px;'>
                                    <control-item type='input' :local='app.local.searchParams' datakey='spu' label='SPU'></control-item>        
                            </el-col>
                            <el-col :span='3' style='padding: 5px 15px;'>
                                    <el-button type='primary' icon='search' style='margin-top: 2px;' @click='app.controls.newtable.searchClick()' :loading='app.controls.newtable.loading'>搜索</el-button>
                            </el-col>                            
                        </el-row>
                        <!--
                        <el-row>
                                <el-col :span='6' style='padding: 5px 15px;'>
                                        <el-button type='new' icon='plus' style='margin-top: 2px;' @click='app.controls.newtable.addClick()'>新增</el-button>
                                </el-col>
                        </el-row>
                    -->
                    </template>
                   <%@ include file='/WEB-INF/views/partial/productnew_control.jsp' %>
                    <el-table-column prop='categoryName' label='分类' width='120'>
                        <template scope='inner'>
                                <span v-if='inner.row.topCategoryName'>
                                    <el-tag type='primary' size='mini'>{{inner.row.topCategoryName}}</el-tag></span>
                            <span>
                                        <el-tag type='danger'
                                                v-if='!inner.row.categoryName || inner.row.categoryName==""'>品类未设置</el-tag></span>
                            <span v-if='inner.row.categoryName'>
                                            <el-row v-for='(item, index) in inner.row.categoryName.split(",")'
                                                    v-if="index>0">{{Array(index * 2 + 3).join("&nbsp;")}}<el-tag
                                                    type='success' size='mini'>{{item}}</el-tag></el-row>
                                        </span>
                        </template>
                    </el-table-column>

                    <el-table-column label="图片" width="150">
                        <template scope='inner'>
                            <img :src='window.uploadPrefix + "/" + inner.row.mainImageUrl'
                                 style='width: 120px; height:140px;'>
                        </template>
                    </el-table-column>

                    <el-table-column label=' 新品信息' width='220'>
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
                    <el-table-column prop='countryName' label='区域' width='150'>
                        <template scope='inner'>
                            <el-row v-if='inner.row.state == "created"'>
                                <el-button size='mini' @click='app.controls.newtable.regionedit(inner.row.id)'>设置</el-button>
                            </el-row>
                            <el-row v-for='item in inner.row.productNewZones'>
                                    {{item.zoneName}}-{{item.zoneCode}}
                            </el-row>

                        </template>
                    </el-table-column>
                  
                    <el-table-column label='来源' prop='sourceEnumName' width='90'>
                        <template scope='inner'>
                            <el-row>{{inner.row.sourceEnumName}}</el-row>
                            <el-row><a :href='window.app.normalizeURL(inner.row.sourceUrl)' target='_blank'>来源链接</a> </el-row>
                            <%--<el-row><a :href='window.app.normalizeURL(inner.row.purchaseUrl)' target='_blank'>采购链接</a> </el-row>--%>
                        </template>

                    </el-table-column>
                    <el-table-column label='审核' width='100'>
                        <template scope='inner'>
                            {{inner.row.checker}}
                        </template>
                    </el-table-column>
                    <el-table-column label='状态' width='100'>
                        <template scope='inner'>
                            <el-row>{{inner.row.stateName}}</el-row>
                            <el-row class='breakspace'><a href='javascript: void(0);' @click='window.showFSM(inner.row.id, "ProductNew")'>{{inner.row.stateTime}}</a></el-row>
                        </template>

                    </el-table-column>
                    <%--<el-table-column prop='memo' label='备注' width='100'></el-table-column>--%>
                    <el-table-column label='备注' width='240'>
                        <template scope="inner">
                            <el-row>{{inner.row.memo}}</el-row>
                            <el-row v-if="inner.row.attributeDesc"><a href='javascript: void(0);'
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
                C.page = 'approveList';
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
                        state: "checkWarn",
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
                    url: "/queryApproveList",
                    data: [],
                    search: {
                        usable: 1,
                    },
                    searchParams: {
                        title: "",
                        createAt: [],
                        topCategoryId: '',
                        state: "checkWarn",
                        creatorId: "",
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
                $(function () {
                    app.service['productNewAd/init'](app.controls.newtable);
                });
            </script>
        </js>