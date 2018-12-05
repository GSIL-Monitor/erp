<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
    <control-table :app='app' tablekey='table' style='width: 1100px;'>
        <template slot='controls' :scope='app'>
            <el-button type='new' icon='plus' @click='app.controls.app.clearField(app.controls.table.add); app.controls.table.dialog.add=true'>新增</el-button>
        </template>
        <el-table-column label='操作' width='180'>
            <template scope='inner'>
                <el-button @click='app.controls.table.deleteElement(inner.row)' size='mini' type='danger'>删除</el-button>
            </template>
        </el-table-column>
        <el-table-column prop='id' label='id' width='100'></el-table-column>
        <el-table-column prop='name' label='名称' width='300'>
            <template scope='inner'>
                <control-item type='edit' label='名称' :local='inner.row' datakey='name' @saved='app.controls.table.updateRow(inner.row, arguments[0])'></control-item>
            </template>
        </el-table-column>
        <el-table-column prop='keyword' label='关键字' width='300'>
                <template scope='inner'>
                        <control-item type='edit' label='关键字' :local='inner.row' datakey='keyword' @saved='app.controls.table.updateRow(inner.row, arguments[0])'></control-item>
                </template>
        </el-table-column>
        <el-table-column prop='remark' label='备注' width='300'>
                <template scope='inner'>
                        <control-item type='edit' label='备注' :local='inner.row' datakey='remark' @saved='app.controls.table.updateRow(inner.row, arguments[0])'></control-item>
                </template>
        </el-table-column>
    </control-table>
    

    <el-dialog v-model='app.controls.table.dialog.add'>
        <el-row><control-item type='input' label='名称' :local='app.controls.table.add' datakey='name'></control-item></el-row>
        <el-row><control-item type='input' label='关键字' :local='app.controls.table.add' datakey='keyword'></control-item></el-row>
        <el-row><control-item type='textarea' label='备注' :local='app.controls.table.add' datakey='remark'></control-item></el-row>
        <div slot='footer'>
            <el-button type='primary' icon='arrow-up' @click='app.controls.table.save()'>保存</el-button>
        </div>
    </el-dialog>
</body>
<js>
    <style>
        .el-row{
            padding: 5px 15px;
        }
    </style>
    <script>
        let C = window.layoutData.controls;
        let con = C.table = {
            data: [],
            search: {
                status: '1',
                start: 0,
                name: '',
            },
            searchParams:{
                name: "",
                remark: '',
                status: '1',
                start: 0,
            },
            elementList: [],
            has: [],
            lastList: [],
            loading: false,
            initList: false,
            page: 1,
            pageSize: 20,
            total: 1000,
            rolePrivId: -1,
            dialogShow: false,
            checkedKeys: {},
            menus: [[]],
            editingName: '',
            dialog: {
                add: false,
            },
            add:{
                name: "",
                remark: "",
                keyword: "",
            },
            get(){
                this.loading = true;
                app.ajax.element.list().then(x => {
                    this.data = x.data.item;
                    this.total = x.data.item.length;
                    this.pageSize = this.total;
                    this.page = 1;
                    this.loading = false;
                });
            },
            save(){
                app.ajax.element.save(this.add).then(x => {
                    this.get();
                    this.dialog.add = false;
                });
            },
            updateRow(row, old){
                app.ajax.element.update(row).then(x => this.get(), x => this.get());
            },
            deleteElement(row){
                if (row.id) {
                    app.$confirm("确实要删除元素" + app.formatNameId(row) + "吗?").then(function () {
                        app.ajax.element.deleteElement(row).then(_ => app.controls.table.get())
                    })
                }
            }
        };

        $(function(){
            C.table.get();
        });
    </script>
</js>

