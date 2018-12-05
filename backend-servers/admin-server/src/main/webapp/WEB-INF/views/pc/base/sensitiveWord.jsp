<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>敏感词列表</H1>
  </template>

  <template scope='app'>
      <control-table tablekey='table' :app='app' style='width: 1000px;'>
            <template scope='app' slot='controls'>
                <el-row>
                    <el-col :span='8'><control-item type='input' label='关键词' :app='app.controls.app' :local='app.controls.table.searchParams' datakey='name'></control-item></el-col>
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
            <el-table-column label='id' prop='id'>
                
            </el-table-column>
            <el-table-column prop='name' label='名称'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='name' type='edit'
                                  @saved='app.controls.table.doUpdate(inner.row)'
                                  @discard='app.controls.table.refresh()'
                                  :item-disabled='!app.controls.app.Perm.write'></control-item>
                </template>
            </el-table-column>
            <el-table-column label='创建时间' prop='createAt'>
                    
            </el-table-column>
        
            <!--<el-table-column prop='currencyCode' label='币种编码'>
                <template scope='inner'>
                    <control-item :app='app' :local='inner.row' datakey='currencyCode' type='edit' @saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()'></control-item>
                </template>
            </el-table-column>-->
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
        var key = 'sensitiveWord';
        c.table = {
            data: [],
            page: 1,
            pageSize: 20,
            total: 0,
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
                this.search.start = (this.page - 1) * this.pageSize ;
                app.ajax[key].find(this.search).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            },
            doAdd(){
                app.ajax[key].add(this.add).then(_ => {
                    this.get();
                    this.dialog.add = false;
                })
            },
            doSearch(){
                Object.assign(this.search, this.searchParams);
                this.get();
            },
            doDelete(row){
                app.ajax[key].delete(row).then( _ => this.get(), _ => this.get() );
            },
            doUpdate(row){
                app.ajax[key].update(row).then( _ => this.get(), _ => this.get() );
            }
        };
        $(function(){
            app.service['common/pagination/init'](c.table, c.table.get.bind(c.table));
            c.table.get();
        })
    </script>
</js>