<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
<template slot='controls' scope='app'>
    <H1>系统类别列表</H1>
</template>

<template scope='app'>
    <control-table tablekey='table' :app='app' style='width: 1000px;' :loading='app.controls.table.loading'>
        <template scope='app' slot='controls'>
            <el-row>
                <el-col :span='8'>
                    <control-item type='input' label='系统类别key'   :app='app.controls.app' :local='app.controls.table.searchParams' datakey='typeKey'></control-item>
                    <control-item type='input' label='系统类别value' :app='app.controls.app' :local='app.controls.table.searchParams' datakey='typeValue'></control-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span='8'><el-button @click='app.controls.table.doSearch()' type='primary' icon='search'>搜索</el-button></el-col>
            </el-row>
            <el-row>
                <el-col :span='8'><el-button @click='app.controls.app.clearField(app.controls.table.add); app.controls.table.dialog.add=true' type='new' icon='plus'  :disabled='!app.controls.app.Perm.write'>新增</el-button></el-col>
            </el-row>
        </template>
        <el-table-column label='操作' >
            <template scope='inner'>
                <el-button type='danger' size='mini' @click='app.controls.table.doDelete(inner.row)' :disabled='!app.controls.app.Perm.write'>删除</el-button>
                <el-button type='primary' size='mini' @click='app.controls.table.doUpdate(inner.row)' :disabled='!app.controls.app.Perm.write'>子类别</el-button>
            </template>
        </el-table-column>

        <el-table-column prop='id' label='id' width='50'></el-table-column>

        <el-table-column prop='typeKey' label='系统类别key'>
            <template scope='inner'>
                <control-item :app='app' :local='inner.row' datakey='typeKey' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
            </template>
        </el-table-column>
        <el-table-column prop='typeValue' label='系统类别value'>
            <template scope='inner'>
                <control-item :app='app' :local='inner.row' datakey='typeValue' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
            </template>
        </el-table-column>
        <el-table-column prop='typeDesc' label='系统类别说明'>
            <template scope='inner'>
                <control-item :app='app' :local='inner.row' datakey='typeDesc' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
            </template>
        </el-table-column>
    </control-table>

    <dialog-create
            :visible='app.controls.table.dialog.add'
            @update:visible='app.controls.table.dialog.add=arguments[0]'
            :title='"添加"'
            :app='app.controls.app'
            :controls='app.controls'
            @ok='app.controls.table.doAdd()'>
        <template scope='app'>
            <el-card>
                <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='typeKey' label='系统类别key'></control-item></el-col></el-row>
                <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='typeValue' label='系统类别value'></control-item></el-col></el-row>
                <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='typeDesc' label='系统类别说明'></control-item></el-col></el-row>
            </el-card>
        </template>
    </dialog-create>
</template>

</body>
<js>
    <script>
        var c = window.layoutData.controls;
        c.table = {
            data: [],
            page: 1,
            pageSize: 20,
            total: 0,
            search: {
                start: 0,
            },
            searchParams: {
                typeKey: "",
                typeValue: "",
            },
            add: {
                typeKey: "",
                typeValue: "",
                typeDesc: "",
            },
            dialog:{
                add: false,
            },
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                this.search.start = (this.page - 1) * this.pageSize ;
                app.ajax.system_type.find(this.search).then(x => {
                    this.data = x.data.item;
                this.total = x.data.total;
                this.loading = false;
            });
            },
            doAdd(){
                if (true) {
                    app.ajax.system_type.insert(this.add).then(_ => {
                        this.get();
                    this.dialog.add = false;
                });
                } else {
                    app.$message({
                        message: '排序请输入正整数',
                        type: 'warning'
                    })
                }

            },
            doSearch(){
                Object.assign(this.search, this.searchParams);
                this.get();
            },
            doDelete(row){
                app.ajax.system_type.delete(Object.assign({id: row.id}, row)).then( _ => this.get(), _ => this.get() );
            },
            doUpdate(row){
                app.ajax.system_type.update(row).then( _ => this.get(), _ => this.get());
            }
        };
        $(function(){
            app.service['common/pagination/init'](c.table, _ => c.table.get());
            c.table.get();
            app.controls.app.Perm.write=true;
        })
    </script>
</js>