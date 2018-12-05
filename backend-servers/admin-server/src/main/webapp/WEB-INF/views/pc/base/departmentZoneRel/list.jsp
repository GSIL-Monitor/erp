<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/static/images/favicon.ico" type="image/x-icon">
<title>erp登录</title>
</head>
-->
<body>

<template scope='app' slot='controls'>
    <H1>部门与地区关系</H1>
</template>

<template scope='app'>
    <control-table :app='app' tablekey='usertable' style='width: 1000px;' :loading='app.controls.usertable.loading'>
        <template scope='app' slot='controls'>
            <el-row >

                <el-col :span='8'>
                    <control-item type='selectcascade'
                                  :treetolist='true'
                                  :treelistprops='{key: "name", value: "id", children: "children"}'
                                  :treeprops='{label: "name", children: "children", value: "id"}'
                                  :tree='window.app.common.departmentList'
                                  :local='app.local.searchParams'
                                  datakey='departmentId'
                                  label='部门'></control-item>
                </el-col>

                <el-col :span='8'>
                    <control-item type='select'
                                  :list='window.app.common.countryList'
                                  :local='app.local.searchParams'
                                  datakey='zoneId'
                                  label='地区'>
                    </control-item>


                        <%--<control-item :app='app.controls.app' type='select' :list='app.controls.table.countryList'--%>
                                      <%--&lt;%&ndash;@select-change="app.controls.table.findParentList(app.controls.table.edit.countryId)"&ndash;%&gt;--%>
                                      <%--:local='app.controls.table.edit' datakey='countryId' label='地区'>--%>
                        <%--</control-item>--%>

                </el-col>

                <el-col :span='8'>
                    <control-item type='select' :list = "[{key: '有效', value: true}, {key: '无效', value: false}]" :local='app.local.searchParams' datakey='usable' label='是否有效'></control-item>
                </el-col>

                <el-col :span='8'>
                    <el-button icon='search' type='primary' @click='app.controls.usertable.searchClick()'>搜索</el-button>
                    <el-button icon='plus' type='new' @click='app.local.seladd()' :disabled='!app.controls.app.Perm.write'>新增</el-button>
                </el-col>

            </el-row>

        </template>

        <el-table-column label='操作'>
            <template scope='inner'>
                <el-button size='mini' type='danger' @click='app.controls.usertable.delete(inner.row)'
                           v-if='!inner.row.usable' :disabled='!app.controls.app.Perm.write'>删除
                </el-button>
                <el-button size='mini' type='danger' @click='app.controls.usertable.disable(inner.row)'
                           v-if='inner.row.usable' :disabled='!app.controls.app.Perm.write'>关闭
                </el-button>
                <el-button size='mini' type='success' @click='app.controls.usertable.enable(inner.row)'
                           v-if='!inner.row.usable' :disabled='!app.controls.app.Perm.write'>开启
                </el-button>
            </template>
        </el-table-column>
        <el-table-column label='部门' prop='departmentName'></el-table-column>
        <el-table-column label='地区' prop='zoneName'>
            <template scope='inner'>
                <%--{{app.controls.usertable.typetrans[inner.row.userType]}}--%>
                {{inner.row.zoneName}}
            </template>
        </el-table-column>
        <%--是否有效--%>
        <el-table-column label='是否有效'>
            <template scope='inner'>
                {{inner.row.usable ? '有效' : "无效"}}
            </template>
        </el-table-column>
    </control-table>


    <dialog-create :title='"添加关系"' :local='app.controls.usertable.add' :visible='app.controls.usertable.dialog.add' :controls='window.app.controls'
                   @update:visible='val => window.app.controls.usertable.dialog.add = val' @ok='app.controls.usertable.doAdd()'>
        <el-card>

            <el-col>

                <el-col >
                    <control-item type='selectcascade'
                                  :treetolist='true'
                                  :treelistprops='{key: "name", value: "id", children: "children"}'
                                  :treeprops='{label: "name", children: "children", value: "id"}'
                                  :tree='window.app.common.departmentList'
                                  :local='app.controls.usertable.add'
                                  datakey='departmentId'
                                  label='部门'></control-item>
                </el-col>

            </el-col>

            <el-col>
                <control-item type='select'
                              :list='window.app.common.countryList'
                              :local='app.controls.usertable.add'
                              datakey='zoneId'
                              label='地区'>
                </control-item>
            </el-col>


            <el-col>&nbsp;</el-col>
        </el-card>
    </dialog-create>
</template>




</body>
<js>

    <script>
        window.layoutData.common.departmentList = [];
        window.layoutData.common.countryList = [];
        window.layoutData.common.firstCategoryList = [];
        var c = window.layoutData.controls;

        c.table = {
            countryList: [],
            get(){
//                this.search.limit = this.pageSize;
//                this.search.start = (this.page - 1) * this.pageSize ;
                app.ajax[key].find(this.search).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            }
        }
        window.layoutData.controls.usertable = {
            data: [],
            departmentList: [],
            search: {
//                status: '1',
//                start: 0,
//                name: '',
                usable: '',
                departmentNo: '',
                departmentId: '',
                zoneId: '',
                id:''
            },
            searchParams: {
//                name: "",
//                remark: '',
//                status: '',
//                start: 0,
//                field: '',
//                categoryId: undefined,
                userId: undefined,
                usable: '',
                departmentNo: undefined,
                id:'',
                departmentId: '',
                departmentName: '',
                zoneId: ''

            },
            loading: false,
            initList: false,
            page: 1,
            pageSize: 20,
            total: 1000,
            dialog: {
                add: false,
            },
            checkedKeys: {},
            menus: [[]],
            add:{

                departmentNo: 0,
                departmentId: 0,
                zoneId: 0,
                id: 0

            },

            editingName: ''
        };
        $(function(){
            app.service['userrel/init'] = function(con){
                if(!con.initList){
                    con.initList = true;
                    app.service['common/pagination/init'](con, 'userrel/list');
//                    var prefix = 'UserRel/';
                    const prefix = '/product/base/departmentZoneRel/'
                    con.list = {
                        search:  app.ajaxFunc(function(obj){
//                            var rel = app.pluck(obj, ['userId', 'categoryId', 'usable', 'departmentNo', 'start', 'limit', 'userType']);
                            var rel = app.pluck(obj, ['departmentId', 'zoneId', 'usable']);
//                            if(rel.departmentNo) rel.departmentNo = rel.departmentNo[rel.departmentNo.length-1];
                            if(rel.departmentId) rel.departmentId = rel.departmentId[rel.departmentId.length-1];

                            return {
//                                url: prefix + "query",
                                url: prefix + 'findList',
                                method: 'get',
                                params: rel,
//                                data: app.postform(app.pluck(obj, ['departmentId', 'zoneId','usable'])),
//                                data: app.postform(rel)
                            }
                        }),
                        add:  app.ajaxFunc(function(obj){
                            var rel = app.pluck(obj, ['departmentId', 'zoneId', 'usable']);
                            if(rel.departmentNo) rel.departmentNo = rel.departmentNo[rel.departmentNo.length-1];
                            if(rel.departmentId) {
                                rel.departmentId = rel.departmentId[rel.departmentId.length-1];
                                console.log(rel.departmentId,8888)
                                return {
                                    url: prefix + "add",
                                    method: 'post',
//                                    data: app.postform(app.pluck(obj, ['departmentId', 'zoneId'])),
//                                data: app.postform(app.pluck(obj, ['departmentId', 'zoneId'])),
//                                    data: app.postform(app.pluck(obj, ['departmentId', 'zoneId'])),
                                    data: app.postform(rel)

                                }
                            }


                        }),
                        delete:  app.ajaxFunc(function(obj){
                            return {
                                url: prefix + "delete",
                                method: 'post',
                                data: app.postform(app.pluck(obj, ['id']))
                            }
                        }),
                        enable:  app.ajaxFunc(function(obj){
                            return {
                                url: prefix + "updateUsable",
                                method: 'post',
                                data: app.postform(Object.assign({usable: '1'}, app.pluck(obj, ['id'])))
                            }
                        }),
                        disable:  app.ajaxFunc(function(obj){
                            return {
                                url: prefix + "updateUsable",
                                method: 'post',
                                data: app.postform(Object.assign({usable: '0'}, app.pluck(obj, ['id'])))
                            }
                        }),
                    };
                    con.seladd = function(){
                        app.clearField(con.add);
                        con.add.mainImageUrl = '';
                        con.dialog.add = true;
                    };
                    con.enable = function(row){
                        con.list.enable(row).then(_ => con.refresh());
                    };
                    con.disable = function(row){
                        con.list.disable(row).then(_ => con.refresh());
                    };
                    con.delete = function(row){
                        app.$confirm(`确实要删除${app.formatNameId(row)}吗?`).then( _ => con.list.delete(row).then(_ => con.refresh()));
                    }
                    con.refresh = function(){
                        app.service['userrel/list'](con);
                    }
                    con.doAdd = function(){
                        let add = Object.assign({}, con.add);
                        //if(add.departmentNo && add.departmentNo.length) add.departmentNo = add.departmentNo.slice(-1)[0];
                        //else delete add.departmentNo;
                        con.list.add(add).then(_ => {
                            con.refresh();
                            con.dialog.add = false;
                        }, _ => con.dialog.add = true );
                    }
                    app.service['userrel/list'](con);
                }
            };

            app.service['userrel/list'] = function(con){
                let parameters = Object.assign({}, con.search, {limit: con.pageSize});
                return con.list.search(parameters).then(x => {con.data = x.data.item; con.total = x.data.total; con.loading = false;});
            };

            app.departmentListFunc();
            app.enum3('CategoryUserTypeEnum').then(x => {
                app.controls.usertable.typelist = x;
                var trans = app.controls.usertable.typetrans = {};
                x.forEach(y => trans[y.value] = y.key);
            });
            app.service['userrel/init'](app.controls.usertable, 'userrel/list');

            app.service['common/pagination/init'](c.table, _ => app.service['userrel/list'](c.table));
            app.controls.countryTable = {};
//            app.controls.app.ajax.country.find().then(x => x.data.item).then(x => c.table.countryList = x.map(y => ({key: y.name, value: y.id}))).then(_ => c.table.countryList.forEach(x => app.controls.countryTable[x.value] = x.key)).then(_ =>   c.table.get());

            app.controls.app.ajax.zone.top({parentId: 0}).then(x => x.data.item).then(x => c.table.parentList = [{key: "无", value: 0}].concat(x.map(y => ({key: y.title, value: y.id}))))

            window.app.ajax('/product/base/zone/find').then((msg) => {
                window.app.common.countryList = msg.data.item.map(({title, id}) => ({value: id, key: title}));
            })

        })
    </script>

</js>
