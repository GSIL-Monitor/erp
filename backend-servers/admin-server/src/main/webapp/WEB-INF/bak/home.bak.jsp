<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
        <template scope='app' slot='controls'>
            <div class='hero'>
                <H1>123</H1>
            </div>
        </template>
        <template scope='app'>
            部门管理
            <el-tree :data="app.controls.departmentData" style='width: 200px;'></el-tree>
            职位管理
            <control-table :app='app' style='width: 600px;' tablekey='maintable' @page-change='app.controls.maintable.pageChange(arguments[0])'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='job' label='职位'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.search' datakey='desc' label='职位描述'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='select' :local='app.local.search' datakey='status' label='状态' :list='app.local.options'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <el-button @click='alert(JSON.stringify(app.local.search))'>搜索</el-button>
                    </el-col>
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column prop='职位授权' label='职位授权'>
                    <template scope='inner'>
                        <el-button type='primary' size='mini' @click='app.controls.maintable.rolePrivId = inner.row.id; app.controls.maintable.dialogShow = true;'>职位授权</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='createTime' label='创建时间' ></el-table-column>
                <el-table-column prop='creator' label='创建人' ></el-table-column>
                <el-table-column prop='leaf' label='是否末级' ></el-table-column>
               
            
  
                <el-table-column prop='id' label='id'></el-table-column>
                <el-table-column prop='job' label='职位'></el-table-column>
                <el-table-column prop='desc' label='描述'></el-table-column>
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status ? '开' : '关'}}</template>
                </el-table-column> 
            </control-table>
            <el-dialog :visible.sync='app.controls.maintable.dialogShow' title='职位授权'> 
                <el-tree :data='app.controls.roleData' node-key='label' show-checkbox @check-change='app.controls.maintable.checkChange(arguments[0].label, arguments[1])'>
                    
                </el-tree>
                <div slot='footer'>
                    <el-button type='primary' @click='app.controls.maintable.dialogShow = false; window.app.$message(app.controls.maintable.rolePrivId + "\n" + Object.keys(app.controls.maintable.checkedKeys).join("\n"))'>确定</el-button>
                </div>
            </el-dialog>
            用户管理
            <control-table :app='app' style='width: 600px;' tablekey='usertable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='lastname' label='姓名'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.search' datakey='loginid' label='账号'></control-item>
                    </el-col>
                     <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='departmentId' label='部门'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.search' datakey='jobId' label='职位'></control-item>
                    </el-col>
                    </el-row><el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='select' :local='app.local.search' datakey='status' label='状态' :list='app.local.options'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <el-button @click='alert(JSON.stringify(app.local.search))'>搜索</el-button>
                    </el-col>
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column prop='lastname' label='姓名'></el-table-column>
                <el-table-column prop='loginid' label='账号'></el-table-column>
                <el-table-column prop='departmentId' label='部门'></el-table-column>
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status ? '开' : '关'}}</template>
                </el-table-column> 
            </control-table>
            国家列表
            <control-table :app='app' style='width: 1200px;' tablekey='regiontable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='country' label='国家'></control-item>
                    </el-col>
                    <el-col :span=3>&nbsp;</el-col>
                    <el-col :span='6'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                        <el-button type='primary' @click='app.local.dialogShow=true' icon='search' style='margin-top: 2px;'>新增</el-button>
                    </el-col>
                    </el-row>
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='ID' label='id'></el-table-column>
                <el-table-column prop='country' label='国家' min-width='100'></el-table-column>
                <el-table-column prop='remark' label='备注'></el-table-column>
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status ? '开' : '关'}}</template>
                </el-table-column> 
            </control-table>
            <dialog-create :title='"新增国家"' :app='app' :local='app.controls.regiontable.add' 
                :visible='app.controls.regiontable.dialogShow'
                @update:visible='val => window.app.controls.regiontable.dialogShow = val'>
                <template scope='app'>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='country' label='国家'></control-item>
                        </el-col>    
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='select' :local='app.local' datakey='status' label='状态'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='symbol' label='备注'></control-item>
                        </el-col>    
                    </el-row>
                    
                </template>
            </dialog-create>
            币种列表
             <control-table :app='app' style='width: 1200px;' tablekey='currencytable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='code' label='币种'></control-item>
                        
                    </el-col>
                    <el-col :span=3>&nbsp;</el-col>
                    <el-col :span='6'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                        <el-button type='primary' @click='app.controls.currencytable.dialogShow=true' icon='search' style='margin-top: 2px;'>新增</el-button>
                    </el-col>
                    </el-row>
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='id'></el-table-column>
                <el-table-column prop='name' label='币种' min-width='100'></el-table-column>
                <el-table-column prop='code' label='代码'></el-table-column>
                <el-table-column prop='symbol' label='符号'></el-table-column>
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status ? '开' : '关'}}</template>
                </el-table-column> 
            </control-table>
            <dialog-create :title='"新增币种"' :app='app' :local='app.controls.currencytable.add' 
                :visible='app.controls.currencytable.dialogShow'
                @update:visible='val => window.app.controls.currencytable.dialogShow = val'>
                <template scope='app'>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='code' label='币种'></control-item>
                        </el-col>    
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='name' label='英文缩写'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='symbol' label='符号'></control-item>
                        </el-col>    
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='ratio' label='汇率'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='select' :local='app.local' datakey='status' label='状态'></control-item>
                        </el-col>    
                    </el-row>
                </template>
            </dialog-create>
            新品列表
            <control-table :app='app' style='width: 1200px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='一级分类'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='产品标题'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='daterange' :local='app.local.search' datakey='time' label='创建时间'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='状态'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='国家'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='创建人'></control-item>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='primary' icon='plus' style='margin-top: 2px;'>新增</el-button>
                    </el-col>
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>提交</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='id'></el-table-column>
                <el-table-column prop='field' label='一级分类' ></el-table-column>
                <el-table-column prop='field' label='产品图片' ></el-table-column>
                <el-table-column prop='field' label='产品标题' ></el-table-column>
                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                <el-table-column prop='field' label='状态时间' ></el-table-column>
                <el-table-column prop='field' label='国家' ></el-table-column>
                <el-table-column prop='field' label='创建人' ></el-table-column>
                <el-table-column prop='field' label='创建时间' ></el-table-column>
                <el-table-column prop='field' label='备注' ></el-table-column>
            </control-table>
            新品列表(主管)
            <control-table :app='app' style='width: 1200px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='一级分类'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='产品标题'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='daterange' :local='app.local.search' datakey='time' label='创建时间'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='状态'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='国家'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='创建人'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                    </el-col>
                    
                    </el-row>
                    <el-row>
                    
                    <el-col :span='6' style='text-align: '>
                         <el-button type='success' type='mini' icon='check' style='margin-top: 2px;'>审批</el-button>
                            <el-button type='danger' type='mini' icon='close' style='margin-top: 2px;'>驳回</el-button>
                    </el-col>
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>提交</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='id'></el-table-column>
                <el-table-column prop='field' label='分类' ></el-table-column>
                <el-table-column prop='field' label='产品图片' ></el-table-column>
                <el-table-column prop='field' label='产品标题' ></el-table-column>
                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                <el-table-column prop='field' label='地区' ></el-table-column>
                <el-table-column prop='field' label='广告专员' ></el-table-column>
                <el-table-column prop='field' label='制定时间' ></el-table-column>
            </control-table>
            排重列表
            <control-table :app='app' style='width: 1200px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='分类'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='产品标题'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='内名称'></control-item>
                    </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='SPU'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='状态'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='国家'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                    </el-col>
                    </el-row>
                    <el-row>
                    
                    <el-col :span='12' style='padding: 5px 15px; '>
                         <el-button type='primary' icon='check' style='margin-top: 2px;'>提交</el-button>
                         <el-button type='success' icon='check' style='margin-top: 2px;'>审核</el-button>
                         <el-button type='danger' icon='close' style='margin-top: 2px;'>关闭</el-button>
                         <el-button type='danger' icon='view' style='margin-top: 2px;'>重复</el-button>
                         <el-button type='danger' icon='circle-close' style='margin-top: 2px;'>驳回</el-button>
                    </el-col>
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='ID'></el-table-column>
                <el-table-column prop='field' label='分类' ></el-table-column>
                <el-table-column prop='field' label='产品图片' ></el-table-column>
                <el-table-column prop='field' label='产品名称' ></el-table-column>
                <el-table-column prop='field' label='内名称' ></el-table-column>
                <el-table-column prop='field' label='SKU' ></el-table-column>
                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                <el-table-column prop='field' label='地区' ></el-table-column>
                
            </control-table>
             产品列表
            <control-table :app='app' style='width: 1200px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='分类'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='部门'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='产品标题'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='内名称'></control-item>
                    </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='SPU'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='状态'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :list='[]' :local='app.local.search' datakey='field' label='产品图片'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                        <el-button type='primary' icon='arrow-right' style='margin-top: 2px;'>批量导入</el-button>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span='12' style='padding: 5px 15px; '>
                         <el-button type='primary' icon='plus' style='margin-top: 2px;'>新增</el-button>
                         <el-button type='success' icon='check' style='margin-top: 2px;'>审核</el-button>
                         <el-button type='danger' icon='circle-close' style='margin-top: 2px;'>驳回</el-button>
                    </el-col>
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column prop='id' label='ID'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                
                <el-table-column prop='field' label='分类' ></el-table-column>
                <el-table-column prop='field' label='产品图片' ></el-table-column>
                <el-table-column prop='field' label='产品名称' ></el-table-column>
                <el-table-column prop='field' label='内名称' ></el-table-column>
                <el-table-column prop='field' label='SPU' ></el-table-column>
                <el-table-column prop='field' label='进货价' ></el-table-column>
                <el-table-column prop='field' label='销售价' ></el-table-column>
                <el-table-column prop='field' label='库存' ></el-table-column>
                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                
                
            </control-table>
             供应商列表
            <control-table :app='app' style='width: 1200px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='供应商链接'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span='12' style='padding: 5px 15px; '>
                         <el-button type='primary' icon='plus' style='margin-top: 2px;'>新增</el-button>
                    </el-col>
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='ID'></el-table-column>
                <el-table-column prop='field' label='供应商名称' ></el-table-column>
                <el-table-column prop='field' label='供应商连接' ></el-table-column>
                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                
                
                
            </control-table>
             采购列表
            <control-table :app='app' style='width: 1500px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :local='app.local.search' datakey='field' label='部门'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :local='app.local.search' datakey='field' label='仓库'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :local='app.local.search' datakey='field' label='状态'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.search' datakey='field' label='SPU'></control-item>
                    </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span='6' style='padding: 5px 15px; '>
                            <control-item type='daterange' :local='app.local.search' datakey='field' label='创建日期'></control-item>
                        </el-col>
                        <el-col :span='6' style='padding: 5px 15px; '>
                            <control-item type='daterange' :local='app.local.search' datakey='field' label='采购日期'></control-item>
                        </el-col>
                        <el-col :span='6' style='padding: 5px 15px; '>
                            <control-item type='input' :local='app.local.search' datakey='field' label='采购单号'></control-item>
                        </el-col>
                         <el-col :span='6' style='padding: 5px 15px;'>
                            <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <el-button type='primary' icon='plus' style='margin-top: 2px;'>新增</el-button>
                            <el-button type='success' icon='check' style='margin-top: 2px;'>提交</el-button>
                            <el-button type='danger' icon='delete' style='margin-top: 2px;'>删除</el-button>
                        </el-col>                    
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                
                <el-table-column label='操作'  min-width='150'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='ID'></el-table-column>
                <el-table-column prop='field' label='采购单号' ></el-table-column>
                <el-table-column prop='field' label='采购时间' ></el-table-column>
                <el-table-column prop='field' label='仓库' ></el-table-column>
                <el-table-column prop='field' label='供应商' ></el-table-column>
                <el-table-column prop='field' label='产品名称' ></el-table-column>
                <el-table-column prop='field' label='SPU' ></el-table-column>

                <el-table-column prop='field' label='采购单价' ></el-table-column>
                <el-table-column prop='field' label='采购数量' ></el-table-column>
                <el-table-column prop='field' label='采购金额' ></el-table-column>
                <el-table-column prop='field' label='总金额' ></el-table-column>
                <el-table-column prop='field' label='运费' ></el-table-column>
                <el-table-column prop='field' label='采购渠道' ></el-table-column>

                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                <el-table-column prop='field' label='创建人' ></el-table-column>
   
            </control-table>
            
            <form action='/pc/productNew/upload' method='post' enctype="multipart/form-data">
        	<input name='file' type='file'>
        	<button type='submit'>提交</button>
        </form>
        </template>
</body>
<js>
<script>
    /*2
        window.menu =  {children: []};
        window.user = '123';
        */
        window.layoutData = {
            controls: {},
        };
        var C = window.layoutData.controls;
        C.departmentData = [{"label":"布谷鸟信息科技","children":[{"label":"业务部"},{"label":"技术研发部"},{"label":"采购部"},{"label":"仓储部"},{"label":"客服部"},{"label":"财务部"},{"label":"企业发展中心","children":[{"label":"事业一部"},{"label":"事业二部"},{"label":"事业三部"}]},{"label":"物流部"},{"label":"设计部"}]}];
        C.maintable = {
            data: " ".repeat(10).split("").map((x, i) => ({
                id: i,
                job: '总经理',
                desc: 'id = ' + i,
                status: true
            })),
            search:{
                job: "",
                desc: '',
                status: '',
            },
            page: 1,
            pageSize: 100,
            total: 1000,
            hello: "123",
            val: "5",
            rolePrivId: -1,
            dialogShow: false,
            checkedKeys: {},
            options: [{
                key: "启用", value: "1",
            },{
                key: "未启用", value: "0",
            }],
            pageChange(args){
                this.data.forEach((x, i) => x.id = args * 10 + i - 10);
            },checkChange(name, value){
                if(value){
                    this.checkedKeys[name] = true;
                }else{
                    delete this.checkedKeys[name];
                }
            }
        };
        C.roleData = [{"label":"用户中心","children":[{"label":"部门设置","children":[{"label":"查看"}]},{"label":"职位设置"},{"label":"用户设置"}]},{"label":"产品中心","children":[{"label":"基础设置","children":[{"label":"属性管理"},{"label":"分类管理"}]}]},{"label":"新品管理","children":[{"label":"查看"},{"label":"新增"},{"label":"删除"},{"label":"编辑"}]},{"label":"排重管理"}]
        C.usertable = {
            data: [],
            search: {
                lastname: "", 
                loginid: "",
                deparetmentId: 0,
                jobId: 0,
                managerId: 0,
                status: "",
            }
        }

        C.regiontable = {
            data: [{
                id: 1,
                country: '中国',
                remark: '备注',
                status: true,
            },{
                id: 2,
                country: '日本',
                remark: '备注',
                status: true,
            },{
                id: 3,
                country: '中国香港',
                remark: '备注',
                status: true,
            },{
                id: 4,
                country: '中国台湾',
                remark: '备注',
                status: true,
            }],
            search: {
                name: "",
            },
            add:{

            },
            dialogShow: false,
        }

        C.currencytable = {
            data: [{
                id: 1,
                name: '人民币',
                code: 'RMB',
                symbol: '¥',
                ratio: '汇率',
                status: true
                
            }],
            search: {
                code: "",
            },
            add:{
                code: '',
                name: '',
                symbol: '',
                ratio: '',
                status: 0,
            },
            dialogShow: false,
        }
        C.newtable = {
            data: [],
            search: {
                field: '搜索字段字段'
            }
        }
    </script>
</js>    

