<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>黑名单列表</H1>
  </template>

  <template scope='app'>
      <control-table tablekey='table' :app='app' style='width: 100%;' :loading='app.controls.table.loading'>
            <template scope='app' slot='controls'>
                <el-row>`
                    <el-col :span='6'>
                        <control-item type='select'
                                      :list="[{key: 'email', value: 'email'}, {key: 'phone', value: 'phone'},{key: 'ip', value: 'ip'},{key: 'address', value: 'address'}]"
                                      :local='app.local.searchParams' datakey='blackTypeKey' label='匹配类型'></control-item>
                    </el-col>
                    <el-col :span='6'>
                        <control-item type='input' label='内容' :app='app.controls.app'
                                      :local='app.controls.table.searchParams' datakey='content'></control-item>
                    </el-col>
                    <el-col :span='6'>
                        <control-item type='select'
                                      :list='app.controls.table.creatorList'
                                      :local='app.local.searchParams' datakey='creatorId' label='操作人'></control-item>
                    </el-col>
                    <el-col :span='6'><el-button @click='app.controls.table.doSearch()' type='primary' icon='search'>搜索</el-button></el-col>
                </el-row>
                <el-row>
                    <%--app.controls.table.dialog.add=true'--%>
                        <%--:disabled='!app.controls.app.Perm.write'--%>
                    <el-col :span='8'><el-button @click='app.controls.table.importBlackList()'  type='new' icon='plus' >导入黑名单</el-button></el-col>
                </el-row>
                <el-row>
                    <%--app.controls.table.dialog.add=true'--%>
                    <%--:disabled='!app.controls.app.Perm.write'--%>
                    <el-col :span='8'><el-button @click='app.controls.table.export()'  type='new' icon='plus' >导出</el-button></el-col>
                </el-row>
            </template>
            <el-table-column label='操作' >
                <template scope='inner'>
                    <%--:disabled='!app.controls.app.Perm.write'--%>
                    <el-button type='danger' size='mini' @click='app.controls.table.delete(inner.row)' >删除</el-button>
                </template>
            </el-table-column>
            <el-table-column prop='name' label='ID'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='id' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
                </template>
            </el-table-column>
            <el-table-column prop='langCode' label='匹配类型'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='blackTypeStateName' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
                </template>
            </el-table-column>
            <el-table-column prop='sort' label='内容'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='content' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
                </template>
            </el-table-column>
          <el-table-column prop='sort' label='导入日期'>
              <template scope='inner'>
                  <control-item :app='app' :local='inner.row' datakey='createAt' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
              </template>
          </el-table-column>
          <el-table-column prop='sort' label='添加人'>
              <template scope='inner'>
                  <control-item :app='app' :local='inner.row' datakey='creator' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
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
                    <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='name' label='名称'></control-item></el-col></el-row>
                    <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='langCode' label='语言编码'></control-item></el-col></el-row>
                    <el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='sort' label='排序'></control-item></el-col></el-row>
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
            creatorList: [],
            search: {
                start: 0,
            },
            searchParams: {
                title: "",
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
                app.ajax.blackList.find(this.search).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                });
//                app.ajax.blackList.creators()
//                    .then( x =>{
//                        this.creatorList = x.data.item;
////                        console.log(this.creatorList);
//                    });


            },
            export(){
                this.loading = true;
                this.search.limit = null;
                this.search.start = null ;
                app.ajax.blackList.preExport(this.search).then(x => {
                    window.location.href='/order/black_list/export';
                });
            },
            doAdd(){
                let re = /^[0-9]+$/;
                if (re.test(this.add.sort)) {
                    app.ajax.lang.add(this.add).then(_ => {
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
            delete(row){
                app.ajax.blackList.delete(Object.assign({id: row.id}, row)).then( _ => this.get(), _ => this.get() );
            },
            doUpdate(row){
                app.ajax.lang.update(row).then( _ => this.get(), _ => this.get());
            },
            importBlackList(){
                window.top.app.open('/order/black_list/import_index?&iframe=1');
            }
        };
        $(function(){
            app.service['common/pagination/init'](c.table, _ => c.table.get());
            c.table.get();

            app.ajax.blackList.creators()
                .then( x => x = x.data.item || [])
                .then(list => list.map((x) => ({value: x.creatorId, key: x.creator})))
                .then( list=>{app.controls.table.creatorList = list});
        })
    </script>
</js>