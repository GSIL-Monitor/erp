<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>地区与客服</H1>
  </template>

  <template scope='app'>
      <control-table tablekey='table' :app='app' style='width: 1000px;' :loading='app.controls.table.loading'>
            <template scope='app' slot='controls'>
                <el-row>
                    <el-col :span='6'>
                        <%--<control-item type='input' label='用户' :app='app.controls.app'--%>
                                      <%--:local='app.controls.table.searchParams' datakey='lastName'></control-item>--%>
                        <control-item type='selectAsync'
                                      :complete-array-url-func="url => window.app.ajax('/admin/user/userAutoComplement?condition=' + url).then(result => result.data.item)"
                                      :complete-props = "{value: 'id', label: 'lastname'}"
                                      :local='app.controls.table.searchParams'
                                      datakey='userId'
                                      label='用户'></control-item>
                    </el-col>

                    <el-col :span='6'>
                        <control-item type='select'
                                      :list='window.app.common.zoneList'
                                      :local='app.controls.table.searchParams'
                                      datakey='zoneId'
                                      label='地区'>
                        </control-item>
                    </el-col>

                    <el-col :span='6'>
                        <control-item   :list="[{key: '有效', value: true}, {key: '无效', value: false}]"
                                type='select' label='是否有效' :app='app.controls.app'
                                      :local='app.controls.table.searchParams' datakey='useStatus'></control-item>
                    </el-col>
                    <el-col :span='6'><el-button @click='app.controls.table.doSearch()' type='primary' icon='search'>搜索</el-button></el-col>
                    
                </el-row>
                <el-row>
                    <el-col :span='8'><el-button @click='app.controls.app.clearField(app.controls.table.add); app.controls.table.dialog.add=true' type='new' icon='plus'  :disabled='!app.controls.app.Perm.write'>新增</el-button></el-col>
                </el-row>
            </template>
            <el-table-column label='操作' >
                <template scope='inner'>
                    <el-button size='mini' type='danger' @click='app.controls.table.delete(inner.row)'
                               v-if='!inner.row.useStatus' :disabled='!app.controls.app.Perm.write'>删除
                    </el-button>
                    <el-button size='mini' type='danger' @click='app.controls.table.disable(inner.row)'
                               v-if='inner.row.useStatus' :disabled='!app.controls.app.Perm.write'>关闭
                    </el-button>
                    <el-button size='mini' type='success' @click='app.controls.table.enable(inner.row)'
                               v-if='!inner.row.useStatus' :disabled='!app.controls.app.Perm.write'>开启
                    </el-button>
                </template>
            </el-table-column>
            <el-table-column label='用户' prop='userName'></el-table-column>
            <el-table-column label='地区' prop='zoneName'></el-table-column>
            <el-table-column label='是否有效'>
                <template scope='inner'>
                    {{inner.row.useStatus ? '有效' : "无效"}}
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
                            <control-item type='selectAsync'
                                          :complete-array-url-func="url => window.app.ajax('/admin/user/userAutoComplement?condition=' + url).then(result => result.data.item)"
                                          :complete-props = "{value: 'id', label: 'lastname'}"
                                          :local='app.controls.table.add'
                                          datakey='userId'
                                          label='用户'></control-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <control-item type='select'
                                          :list='window.app.common.zoneList'
                                          :local='app.controls.table.add'
                                          datakey='zoneId'
                                          label='地区'>
                            </control-item>
                        </el-col>
                    </el-row>
                    <%--<el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='langCode' label='语言编码'></control-item></el-col></el-row>--%>
                    <%--<el-row><el-col><control-item :app='app.controls.app' type='input' :local='app.controls.table.add' datakey='sort' label='排序'></control-item></el-col></el-row>--%>
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
        window.layoutData.common.zoneList = [];
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
                title: "",
            },
            add: {
                userId: "",
                zoneId: "",
            },
            dialog:{
                add: false,
                history: false,
            },
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                this.search.start = (this.page - 1) * this.pageSize ;
                app.ajax.userZone.find(this.search).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            },
            doAdd(){

                app.ajax.userZone.add(this.add).then(_ => {
                    this.get();
                    this.dialog.add = false;
                })

            },
            doSearch(){
                Object.assign(this.search, this.searchParams);
                this.get();
            },
            disable(row){
                app.ajax.userZone.disable({id: row.id}).then( _ => this.get(), _ => this.get() );
            },
            enable(row){
                app.ajax.userZone.enable({id: row.id}).then( _ => this.get(), _ => this.get() );
            },
            delete(row){
                app.ajax.userZone.delete({id: row.id}).then( _ => this.get(), _ => this.get() );
            },
            doUpdate(row){
                app.ajax.lang.update(row).then( _ => this.get(), _ => this.get());
            }
        };
        $(function(){
            app.service['common/pagination/init'](c.table, _ => c.table.get());
            c.table.get();
            window.app.ajax('/product/base/zone/find').then((msg) => {
                window.app.common.zoneList = msg.data.item.map(({title, id}) => ({value: id, key: title}));
            });
        })
    </script>
</js>