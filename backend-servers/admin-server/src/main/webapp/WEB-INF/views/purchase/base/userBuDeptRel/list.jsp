<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<body>
<template scope='app' slot='controls'>
    <H1>采购与事业部关系</H1>
</template>
<template scope='app'>
    <control-table :app='app' tablekey='usertable' style='width: 1000px;' :loading='app.controls.usertable.loading'>
        <template scope='app' slot='controls'>
            <el-row>
                <el-col :span='8'>
                    <control-item type='selectAsync'
                                  :complete-array-url-func="url => new Promise((ac, re) => window.app.ajax('/admin/user/buyerAutoComplement?condition=' + url).then(result => ac(result.data.item)))"
                                  :complete-props="{value: 'id', label: 'lastname'}"
                                  :local='app.local.searchParams'
                                  datakey='userId'
                                  label='用户'></control-item>
                </el-col>
               <el-col :span='8'>
                    <control-item type='selectcascade'
                                  :treetolist='true'
                                  :treelistprops='{key: "name", value: "id", children: "children"}'
                                  :treeprops='{label: "name", children: "children", value: "id"}'
                                  :tree='window.app.common.departmentList'
                                  :local='app.local.searchParams'
                                  datakey='buDepartmentId'
                                  label='部门'></control-item>
                </el-col>
                <el-col :span='8'>
                    <control-item type='select' :list="[{key: '新建', value: 0},{key: '有效', value: 1}, {key: '无效', value: 2}]"
                                  :local='app.local.searchParams' datakey='enable' label='是否有效'></control-item>
                </el-col>
            </el-row>
            <div class='height: 20px;'>&nbsp;</div>
                <el-col :span='8'>
                    <el-button icon='search' type='primary' @click='app.controls.usertable.searchClick()'>搜索</el-button>
                    <el-button icon='plus' type='new' @click='app.local.seladd()'
                               :disabled='!app.controls.app.Perm.write'>新增
                    </el-button>
                </el-col>
            </el-row>
        </template>

        <el-table-column label='操作'>
            <template scope='inner'>
                <el-button size='mini' type='danger' @click='app.controls.usertable.delete(inner.row)'
                           v-if='inner.row.enable!=1' :disabled='!app.controls.app.Perm.write'>删除
                </el-button>
                <el-button size='mini' type='danger' @click='app.controls.usertable.disable(inner.row)'
                           v-if='inner.row.enable==1' :disabled='!app.controls.app.Perm.write'>关闭
                </el-button>
                <el-button size='mini' type='success' @click='app.controls.usertable.enable(inner.row)'
                           v-if='inner.row.enable!=1' :disabled='!app.controls.app.Perm.write'>开启
                </el-button>
            </template>
        </el-table-column>
        <el-table-column label='用户' prop='userName'></el-table-column>
        <el-table-column label='部门编号' prop='buDepartmentNo'></el-table-column>
        <el-table-column label='部门' prop='buDepartmentName'></el-table-column>
        <el-table-column label='是否有效'>
            <template scope='inner'>
               {{inner.row.enable==1 ? '有效' : (inner.row.enable==2? '无效':'新建')}}
            </template>
        </el-table-column>
    </control-table>


    <dialog-create :title='"添加关系"' :local='app.controls.usertable.add' :visible='app.controls.usertable.dialog.add'
                   :controls='window.app.controls'
                   @update:visible='val => window.app.controls.usertable.dialog.add = val'
                   @ok='app.controls.usertable.doAdd()'>
        <el-card>
            <el-col>
                <control-item type='selectAsync'
                              :complete-array-url-func="url => window.app.ajax('/admin/user/buyerAutoComplement?condition=' + url).then(result => result.data.item)"
                              :complete-props="{value: 'id', label: 'lastname'}"
                              :local='app.controls.usertable.add'
                              datakey='userId'
                              label='用户'></control-item>
            </el-col>
            <el-col :span='8'>
                    <control-item type='selectcascade'
                                  :treetolist='true'
                                  :treelistprops='{key: "name", value: "id", children: "children"}'
                                  :treeprops='{label: "name", children: "children", value: "id"}'
                                  :tree='window.app.common.departmentList'
                                  :local='app.controls.usertable.add'
                                  datakey='buDepartmentId'
                                  label='部门'></control-item>
            </el-col>
            <el-col>&nbsp;</el-col>
        </el-card>
    </dialog-create>
</template>


</body>

<js>
    <style>
        .el-col {
            padding: 5px 15px;
        }
    </style>
    <script>
        window.layoutData.common.departmentList = [];
        window.layoutData.common.firstCategoryList = [];
        window.layoutData.controls.usertable = {
            data: [],
            search: {
                status: '1',
                start: 0,
                name: '',
                usable: '',
                departmentNo: ''
            },
            searchParams: {
                name: "",
                remark: '',
                status: '',
                start: 0,
                field: '',
                userId: undefined,
                enable: '',
                buDepartmentId: undefined
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
            add: {
                categoryId: 0,
                userType: '',
                userId: 0,
            },

            editingName: ''
        };
        $(function () {

            app.service['userBuDeptRel/init'] = function (con) {
                if (!con.initList) {
                    con.initList = true;
                    app.service['common/pagination/init'](con, 'userBuDeptRel/list');
                    var prefix = '/purchase/base/';
                    con.list = {
                        search: app.ajaxFunc(function (obj) {
                            var rel = app.pluck(obj, ['userId', 'enable', 'buDepartmentId','start', 'limit']);
                            if (rel.buDepartmentId) rel.buDepartmentId = rel.buDepartmentId[rel.buDepartmentId.length - 1];
                            return {
                                url: prefix + "userBuDeptRel/queryList",
                                method: 'get',
                                params: rel,
                            }
                        }),
                        add: app.ajaxFunc(function (obj) {
                        	var rel = app.pluck(obj, ['buDepartmentId', 'userId']);
                            if(rel.buDepartmentId) {
                                   rel.buDepartmentId = rel.buDepartmentId[rel.buDepartmentId.length-1];
		                           return {
		                                url: prefix + "userBuDeptRel/add",
		                                method: 'post',
		                                data: app.postform(rel)
		                           }
                            }
                        }),
                        delete: app.ajaxFunc(function (obj) {
                            return {
                                url: prefix + "userBuDeptRel/del",
                                method: 'post',
                                data: app.postform(app.pluck(obj, ['id']))
                            }
                        }),
                        enable: app.ajaxFunc(function (obj) {
                            return {
                                url: prefix + "userBuDeptRel/update",
                                method: 'post',
                                data: app.postform(Object.assign({enable: '1'}, app.pluck(obj, ['id'])))
                            }
                        }),
                        disable: app.ajaxFunc(function (obj) {
                            return {
                                url: prefix + "userBuDeptRel/update",
                                method: 'post',
                                data: app.postform(Object.assign({enable: '2'}, app.pluck(obj, ['id'])))
                            }
                        }),
                    };
                    con.seladd = function () {
                        app.clearField(con.add);
                        con.add.mainImageUrl = '';
                        con.dialog.add = true;
                    };
                    con.enable = function (row) {
                        con.list.enable(row).then(_ => con.refresh());
                    };
                    con.disable = function (row) {
                        con.list.disable(row).then(_ => con.refresh());
                    };
                    con.delete = function (row) {
                        app.$confirm(`确实要删除${app.formatNameId(row)}吗?`).then(_ => con.list.delete(row).then(_ => con.refresh()));
                    }
                    con.refresh = function () {
                        app.service['userBuDeptRel/list'](con);
                    }
                    con.doAdd = function () {
                        let add = Object.assign({}, con.add);
                        //if(add.departmentNo && add.departmentNo.length) add.departmentNo = add.departmentNo.slice(-1)[0];
                        //else delete add.departmentNo;
                        con.list.add(add).then(_ => {
                            con.refresh();
                            con.dialog.add = false;
                        }, _ => con.dialog.add = true);
                    }
                    app.service['userBuDeptRel/list'](con);
                }
            };

            app.service['userBuDeptRel/list'] = function (con) {
                let parameters = Object.assign({}, con.search, {limit: con.pageSize});
                return con.list.search(parameters).then(x => {
                    con.data = x.data.item;
                    con.total = x.data.total;
                    con.loading = false;
                });
            };

            app.departmentListFunc();
            app.enum3('CategoryUserTypeEnum').then(x => {
                app.controls.usertable.typelist = x;
                var trans = app.controls.usertable.typetrans = {};
                x.forEach(y => trans[y.value] = y.key);
            });
            app.service['userBuDeptRel/init'](app.controls.usertable, 'userBuDeptRel/list');
        })
    </script>
</js>


</script>

