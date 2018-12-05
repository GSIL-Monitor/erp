<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>区域设置</H1>
  </template>

  <template scope='app'>
      <control-table tablekey='table' :app='app' style='width: 1000px;'  :loading='app.controls.table.loading'>
            <template scope='app' slot='controls'>
                <el-row>
                    <el-col :span='8'><control-item type='input' label='关键词' :app='app.controls.app' :local='app.controls.table.searchParams' datakey='title'></control-item></el-col>
                    <el-col :span='8'><el-button @click='app.controls.table.doSearch()' type='primary' icon='search'>搜索</el-button></el-col>
                    
                </el-row>
                <el-row>
                    <el-col :span='8'>
                        <el-button
                                @click='app.controls.app.clearField(app.controls.table.add);app.controls.table.dialog.add=true'
                                type='new' icon='plus' :disabled='!app.controls.app.Perm.write'>新增
                        </el-button>
                    </el-col>
                </el-row>
            </template>
           <el-table-column label='操作'>
                <template scope='inner'>
                    <el-button type='danger' size='mini' @click='app.controls.table.doDelete(inner.row)'
                               :disabled='!app.controls.app.Perm.write'>删除
                    </el-button>
                </template>
            </el-table-column>
            <el-table-column prop='name' label='名称'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='title' type='edit'
                                  @saved='app.controls.table.doUpdate(inner.row)'
                                  @discarded='app.controls.table.refresh()'
                                  :item-disabled='!app.controls.app.Perm.write'></control-item>
                </template>
            </el-table-column>
            <el-table-column prop='countryId' label='所在国家'>
                <template scope='inner'>
                    <el-button style='margin-right: 5px' size='mini' type='primary' plain
                               @click='app.controls.table.makeEdit(inner.row)' :disabled='!app.controls.app.Perm.write'>
                        编辑
                        <%--app.controls.table.makeEdit(inner.row)--%>
                    </el-button>
                    {{inner.row.countryID}}
                    {{app.controls.countryTable[inner.row.countryId] || `(${inner.row.countryId ? inner.row.countryId : "暂无"})`}}
                </template>
            </el-table-column>
            <el-table-column prop='name' label='区域编码'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='code' type='edit'
                                  @saved='app.controls.table.doUpdate(inner.row)'
                                  @discarded='app.controls.table.refresh()'
                                  :item-disabled='!app.controls.app.Perm.write'></control-item>
                </template>
            </el-table-column>
            <el-table-column prop='currencyCode' label='币种编码'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='currency' type='edit'
                                  @saved='app.controls.table.doUpdate(inner.row)'
                                  @discarded='app.controls.table.refresh()'
                                  :item-disabled='!app.controls.app.Perm.write'></control-item>
                </template>
            </el-table-column>
            <el-table-column prop='name' label='排序'>
                    <template scope='inner'>
                        <control-item :app='app' :pattern="/\d{1,10}/" validate-error="排序是1~10位数字" :local='inner.row'
                                      datakey='sort' type='edit' @saved='app.controls.table.doUpdate(inner.row)'
                                      @discarded='app.controls.table.refresh()'
                                      :item-disabled='!app.controls.app.Perm.write'></control-item>
                    </template>
            </el-table-column>

      </control-table>

      <dialog-create 
          :visible='app.controls.table.dialog.add' 
          @update:visible='app.controls.table.dialog.add=arguments[0]'
          :title='"添加"' 
          :app='app.controls.app'
          :controls='app.controls'
          @ok='app.controls.table.doAdd()'
      >
      <template scope='app'>
            <el-card>
                    <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='title' label='名称'></control-item></el-col></el-row>
                <el-row>
                    <el-col>
                        <control-item :app='app.controls.app' type='select' :list='app.controls.table.countryList'
                                      @select-change="app.controls.table.findParentList(app.controls.table.add.countryId)"
                                      :local='app.controls.table.add'
                                      datakey='countryId' label='国家地区'></control-item>
                    </el-col>
                </el-row>
                    <el-row><el-col><control-item :app='app.controls.app' type='select' :list='app.controls.table.parentList'  :local='app.controls.table.add' datakey='parentId' label='上级区域'></control-item></el-col></el-row>
                    <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='code' label='区域编码'></control-item></el-col></el-row>
                    <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='currency' label='币种编码'></control-item></el-col></el-row>
                    <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='sort' label='排序' :pattern="/^\d{1,10}$/" validate-error="排序是1~10位数字"></control-item></el-col></el-row>
            </el-card>
      </template>
    </dialog-create>

    <dialog-create
                    :visible='app.controls.table.dialog.edit'
                    @update:visible='app.controls.table.dialog.edit=arguments[0]'
                    :title='"编辑"'
                    :app='app.controls.app'
                    :controls='app.controls'
                    @ok='app.controls.table.doEdit()'
                    >
<template scope='app'>
      <el-card>
              <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.edit' datakey='title' label='名称'></control-item></el-col></el-row>
          <el-row>
              <el-col>
                  <control-item :app='app.controls.app' type='select' :list='app.controls.table.countryList'
                                @select-change="app.controls.table.findParentList(app.controls.table.edit.countryId)"
                                :local='app.controls.table.edit' datakey='countryId' label='国家地区'></control-item>
              </el-col>
          </el-row>
              <el-row><el-col><control-item :app='app.controls.app' type='select' :list='app.controls.table.parentList'  :local='app.controls.table.edit' datakey='parentId' label='上级区域'></control-item></el-col></el-row>
              <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.edit' datakey='code' label='区域编码'></control-item></el-col></el-row>
              <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.edit' datakey='currency' label='币种编码'></control-item></el-col></el-row>
          <el-row>
              <el-col>
                  <control-item :app='app.controls.app' type='input' :local='app.controls.table.edit' datakey='sort'
                                label='排序' :pattern="/^\d{1,10}$/"
                                validate-error="排序是1~10位整数"></control-item>
              </el-col>
          </el-row>
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
        var c = window.layoutData.controls;
        c.table = {
            data: [],
            page: 1,
            pageSize: 20,
            total: 0,
            countryList: [],
            parentList: [],
            search: {
                start: 0,
            },
            searchParams: {
                title: "",
            },
            add: {
                title: "",
                langCode: "",
                countryId: undefined,
                parentId: undefined,
                currency: "",
                sort: "",
            },
            edit: {
                title: "",
                langCode: "",
                countryId: undefined,
                parentId: undefined,
                currency: "",
                id: 0,
                sort: "",
            },
            dialog:{
                add: false,
                history: false,
                edit: false,
            },
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                app.ajax.zone.find(this.search).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            },
            doAdd(){
                var reg = /^\d{1,10}$/;
                if (app.controls.table.add.sort) {
                    var r = app.controls.table.add.sort.match(reg);
                    if (r == null) {
                        return;
                    }
                    app.ajax.zone.add(app.pluck(this.add, ['id', 'parentId', 'code', 'title', 'currency', 'countryId', 'sort'])).then(_ => {
                        this.get();
                        this.dialog.add = false;
                    })
                } else {
                    app.$message({
                        message: '请填写完整信息！',
                        type: 'warning'
                    })
                }

            },
            doEdit(){
                app.ajax.zone.update(app.pluck(this.edit, ['id', 'parentId', 'code', 'title', 'currency', 'countryId', 'sort'])).then(_ => {
                    this.get();
                    this.dialog.edit = false;
                })
            },
            makeEdit(row){
//                this.app.controls.table.refresh();
                app.controls.table.get();
                console.log(row);
                Object.assign(this.edit, row);
                this.dialog.edit = true;
            },
            doSearch(){
                Object.assign(this.search, this.searchParams);
                this.get();
            },
            doDelete(row){
                app.ajax.zone.delete(row).then( _ => this.get(), _ => this.get() );
            },
            doUpdate(row){
                app.ajax.zone.update(app.pluck(row, ['id', 'parentId', 'code', 'title', 'currency', 'countryId', 'sort'])).then( _ => this.get(), _ => this.get() );
            },
            findParentList(countryId){
                console.log("======>" + countryId);
                app.controls.app.ajax.zone.findParents({countryId: countryId}).then(x => x.data.item).then(x => c.table.parentList = [{
                    key: "无",
                    value: 0
                }].concat(x.map(y => ({key: y.title, value: y.id}))));
            }
        };
        $(function(){
            app.service['common/pagination/init'](c.table, _ => c.table.get());
            app.controls.countryTable = {};
            app.controls.app.ajax.country.find().then(x => x.data.item).then(x => c.table.countryList = x.map(y => ({key: y.name, value: y.id}))).then(_ => c.table.countryList.forEach(x => app.controls.countryTable[x.value] = x.key)).then(_ =>   c.table.get());            
            
            app.controls.app.ajax.zone.top({parentId: 0}).then(x => x.data.item).then(x => c.table.parentList = [{key: "无", value: 0}].concat(x.map(y => ({key: y.title, value: y.id}))))
          
        })
    </script>
</js>