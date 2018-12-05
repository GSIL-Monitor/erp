<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body>
       
            <template scope='app' slot='controls'>
                <H1>新品列表</H1>
            </template>
            <template scope='app'>
            <control-table :app='app'  :style="`width: ${app.window.innerWidth - 250}  px;`" tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='daterange' :local='app.local.searchParams' datakey='time' label='创建时间'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.searchParams' datakey='title' label='产品标题'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.searchParams' datakey='state' label='状态'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :listfunc='app.controls.app.firstCategoryListFunc' listkey='1' :local='app.local.searchParams' datakey='topCategoryId' label='一级分类'></control-item>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='new' icon='plus' style='margin-top: 2px;' @click='app.controls.newtable.dialogShow=true'>新增</el-button>    
                    </el-col>
                    <el-col :span='12' style='padding: 5px 15px;'>
                        &nbsp;
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px; text-align: right;'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;' @click='app.local.searchClick()'>搜索</el-button>
                    </el-col>
                    
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  width='150'>
                    <template scope='inner'>
                        <el-button size='mini'>提交</el-button>
                        <el-button size='mini'>细化分类</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='新品id' width='90'></el-table-column>
                <el-table-column prop='spu' label='spu' width='90'></el-table-column>
                <el-table-column prop='topCategoryId' label='一级分类' width='100'></el-table-column>
                <el-table-column prop='title' label='标题' width='100'></el-table-column>
                <el-table-column prop='regions' label='区域' width='100'></el-table-column>
                <el-table-column prop='mainImageUrl' label='产品信息' width='150'>
                    <template scope='inner'>
                        {{inner.row.title}}
                        <img :src='inner.row.mainImageUrl'>
                    </template>
                </el-table-column>
                <el-table-column label='来源' width='90'></el-table-column>
                <el-table-column label='审核' width='90'></el-table-column>
                <el-table-column label='状态' width='90'></el-table-column>
                <el-table-column prop='memo' label='备注' width='300' ></el-table-column>
            </control-table>
            <!--
             <dialog-create :title='"新品添加"' :local='app.controls.newtable.add' 
                :visible='app.controls.newtable.dialogShow'
                :controls='window.app.controls'
                @update:visible='val => window.app.controls.newtable.dialogShow = val'
                @ok='app.controls.newtable.doAdd()'>-->
            新增，编辑
            <el-card style='width: 300px;'>
                <template scope='app'>
                    <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='select' :listfunc='app.controls.app.firstCategoryListFunc' listkey='1' :local='app.local' datakey='topCategoryId' label='分类'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='title' label='产品标题'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='selectMultiple' :list='[]' :local='app.local' datakey='field' label='产品特性'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='select' :list='[]' :local='app.local' datakey='field' label='来源'></control-item>
                        </el-col>    
                    </el-row>
                    <%--<el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='field' label='来源url'></control-item>
                        </el-col>    
                    </el-row>--%>
                    <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='field' label='采购url'></control-item>
                        </el-col>    
                    </el-row>
                     <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='upload' :local='app.local' datakey='field' label='图片'></control-item>
                        </el-col>    
                    </el-row>
                     <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='textarea' :local='app.local' datakey='field' label='备注'></control-item>
                        </el-col>    
                    </el-row>
                        
                    
                </template>
            <!--</dialog-create>-->
            </el-card>
            区域设置
            <el-card style='width: 300px;'>
                <el-row>
                    <el-col :span='24'>
                        <el-table>
                            <el-table-column label='操作'></el-table-column>
                            <el-table-column label='新品id'></el-table-column>
                            <el-table-column label='区域编码'></el-table-column>
                            <el-table-column label='区域名称'></el-table-column>
                        </el-table>
                    </el-col>
                    <el-col :span='6'>
                        <el-select>
                            <el-option key='台湾' label='台湾' value='1'></el-option>
                        </el-select>
                    </el-col>
                </el-row>
            </el-card>
        </template>
</body>
<js>http://localhost:8080/admin/department#/pc/product/product/detailsPage?id=87589&iframe=87589
    <script>
        var C = window.layoutData.controls;
       C.newtable = {
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
            dialogShow: false,
            add:{

            }
        }
        $(function(){
           app.service['productNew/init'](app.controls.newtable);
        });
    </script>
</js>