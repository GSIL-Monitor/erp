<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>语言列表</H1>
  </template>

  <template scope='app'>
      <control-table tablekey='table' :app='app' style='width: 1000px;' :loading='app.controls.table.loading'>
            <template scope='app' slot='controls'>
                <el-row>

                    <el-col :span='6'>
                        <control-item type='select'
                                      :list='window.app.common.countryList'
                                      :local='app.controls.table.searchParams'
                                      datakey='zoneId'
                                      label='地区'>
                        </control-item>
                    </el-col>

                    <el-col :span='8'>
                        <control-item type='select'
                                      :list='window.app.common.zoneWarehouseList'
                                      :local='app.controls.table.searchParams'
                                      datakey='warehouseId'
                                      label='仓库'>
                        </control-item>
                    </el-col>

                    <el-col :span='8'><el-button @click='app.controls.table.doSearch()' type='primary' icon='search'>搜索</el-button></el-col>
                    
                </el-row>
                <el-row>
                    <el-col :span='8'><el-button @click='app.controls.app.clearField(app.controls.table.add); app.controls.table.dialog.add=true' type='new' icon='plus'  :disabled='!app.controls.app.Perm.write'>新增</el-button></el-col>
                </el-row>
            </template>
            <el-table-column prop='zoneName' label='地区'>
                <%--<template scope='inner'>--%>
                    <%--<control-item :app='app' :local='inner.row' datakey='zoneName' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>--%>
                <%--</template>--%>
            </el-table-column>
            <el-table-column prop='warehouseName' label='仓库'>
                <%--<template scope='inner'>--%>
                    <%--<control-item :app='app' :local='inner.row' datakey='warehouseName' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>--%>
                <%--</template>--%>
            </el-table-column>
            <el-table-column prop='priority' label='排序值'>
                <%--<template scope='inner'>--%>
                    <%--<control-item :app='app' :local='inner.row' datakey='priority' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>--%>
                <%--</template>--%>
            </el-table-column>
          <el-table-column label='操作' >
              <template scope='inner'>
                  <el-button type='danger' size='mini' @click='app.controls.table.doDelete(inner.row)' :disabled='!app.controls.app.Perm.write'>删除</el-button>
              </template>
          </el-table-column>
      </control-table>

      <dialog-create 
          :visible='app.controls.table.dialog.add' 
          @update:visible='app.controls.table.dialog.add=arguments[0]'
          :title='"新增"'
          :app='app.controls.app'
          :controls='app.controls'
          @ok='app.controls.table.doAdd()'
      >
      <template scope='app'>
            <el-card>
                <el-row>
                    <el-col>
                        <control-item type='select' label='地区' :list="window.app.common.countryList"
                                      :app='app.controls.app'
                                      :local='app.controls.table.add' datakey='zoneId'></control-item>
                        <%--<control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='zoneId' label='地区'></control-item>--%>
                    </el-col>
                </el-row>
                <el-row style='padding: 5px 15px;'>
                    <control-item type='select' label='仓库' :list="window.app.common.zoneWarehouseList"
                                  :app='app.controls.app'
                                  :local='app.controls.table.add' datakey='warehouseId'></control-item>
                </el-row>
                <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='priority' label='排序值'></control-item></el-col></el-row>
            </el-card>
      </template>
    </dialog-create>
  </template>


</body>
<js>
    <style>
        .el-col{
            padding: 5px 15px;
        }
    </style>
    <script>
        // window.layoutData = {controls: {}};
        window.layoutData.common.countryList = [];
        window.layoutData.common.zoneWarehouseList = [];
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
                zoneId: null,
                warehouseId: null
            },
            add: {
                title: "",
                langCode: "",
            },
            dialog:{
                add: false,
                history: false,
            },
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                this.search.start = (this.page - 1) * this.pageSize ;
                app.ajax.zoneWareHouse.find(this.search).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            },
            doAdd(){
                let re = /^[0-9]+$/;
                if (re.test(this.add.priority)) {
                    app.ajax.zoneWareHouse.add(this.add).then(_ => {
                        this.get();
                        this.dialog.add = false;
                    })
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
                app.ajax.zoneWareHouse.delete(Object.assign({languageId: row.id}, row)).then( _ => this.get(), _ => this.get() );
            },
            doUpdate(row){
                app.ajax.zoneWareHouse.update(row).then( _ => this.get(), _ => this.get());
            }
        };
        $(function(){
            app.service['common/pagination/init'](c.table, _ => c.table.get());
            c.table.get();
            window.app.ajax('/product/base/zone/find').then((msg) => {
                window.app.common.countryList = msg.data.item.map(({title, id}) => ({value: id, key: title}));
             });

            app.ajax.zoneWareHouse.findAllWarehouse().then(msg => {
                window.app.common.zoneWarehouseList = msg.data.item.map(({id, name}) => ({value: id, key: name}));
            });

        })
    </script>
</js>