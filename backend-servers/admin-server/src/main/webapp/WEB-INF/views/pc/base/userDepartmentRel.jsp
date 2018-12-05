<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>数据权限</H1>
  </template>

  <template scope='app'>
     <el-row>
     <el-col :span='14'>
      <control-table tablekey='table' :app='app' v-loading='app.controls.table.loading'>
            <template slot='title'>数据权限</template>
            <template scope='app' slot='controls'>
                  <el-row>
                        <el-col :span='6' style='padding: 5px 15px;'>
                                <control-item type='selectAsync' 
                                    :complete-array-url-func="url => window.app.ajax('/admin/user/userAutoComplement?condition=' + url).then(result => result.data.item)"
                                    :complete-props = "{value: 'id', label: 'lastname'}"
                                    :local='app.local.searchParams' 
                                    datakey='userId'
                                    label='用户'></control-item>
                        </el-col>
                        <el-col :span='6'>
                                <control-item type='selectcascade' :treetolist='true'
                                :treelistprops='{key: "name", value: "id", children: "children"}'
                                :treeprops='{label: "name", children: "children", value: "no"}' :tree='app.controls.app.common.departmentList' :local='app.local.searchParams' datakey='departmentNo' label='部门'></control-item>
                        </el-col>
                        <el-col :span='6'>
                                <control-item type='select' :list = "[{key: '有效', value: true}, {key: '无效', value: false}]" :local='app.local.searchParams' datakey='usable' label='是否有效'></control-item>
                        </el-col>

                        <el-col :span='6'><el-button @click='app.controls.table.searchClick()' type='primary' icon='search' :loading='app.controls.table.loading'>搜索</el-button></el-col>
                </el-row>
                <el-row>
                    <el-col :span='8'><el-button @click='app.controls.table.add = {}; app.controls.table.dialog.add=true' type='new' icon='plus'>新增</el-button></el-col>
                </el-row>
            </template>
            <el-table-column label='操作' width='180'>
                    <template scope='inner'>
                            <el-button @click='inner.row.name="id为" + inner.row.id + "的数据";app.controls.app.ajax.userDept.delete(inner.row).then(_ => app.controls.table.get())' size='mini' type='danger'>删除</el-button>
                    </template>
                </el-table-column>
            <el-table-column label='是否有效' width='180'>
                <template scope='inner'>
                    <el-checkbox v-model='inner.row.usable' @change='app.controls.app.ajax.userDept.update(app.controls.app.pluck(inner.row, ["id", "usable"])).then(_ => app.controls.table.get(), _ => app.controls.table.get())'>{{inner.row.usable ? "有效" : "无效"}}</el-checkbox>
                </template>
            </el-table-column>
            <!--
            <el-table-column prop='id' label='id' width='50'>

            </el-table-column>
        -->
            <el-table-column prop='userId' label='用户'>
                <template scope='inner'>
                    {{inner.row.username}}({{inner.row.userId}})
                </template>
            </el-table-column>
            <el-table-column prop='departmentName' label='部门'>
                    <template scope='inner'>
                            {{inner.row.departmentName}}({{inner.row.departmentNo}})
                        </template>
            </el-table-column>
      </control-table>
    </el-col>
    </el-row>
      <dialog-create 
          :visible='app.controls.table.dialog.add' 
          @update:visible='app.controls.table.dialog.add=arguments[0]'
          :title='"添加"' 
          :app='app'
          :controls='app.controls'
          @ok = 'app.controls.table.doAdd()'
      >
        <el-card>
                <el-row style='padding: 5px 15px;'>
                        <control-item type='selectAsync' 
                            :complete-array-url-func="url => window.app.ajax('/admin/user/userAutoComplement?condition=' + url).then(result => result.data.item)"
                            :complete-props = "{value: 'id', label: 'lastname'}"
                            :local='app.controls.table.add' 
                            datakey='userId'
                            label='用户'></control-item>
                </el-row>
                <el-row style='padding: 5px 15px;'>
                        <control-item type='selectcascade' :treetolist='true'
                        :treelistprops='{key: "name", value: "id", children: "children"}'
                        :treeprops='{label: "name", children: "children", value: "id"}' :tree='app.controls.app.common.departmentList' :local='app.controls.table.add' datakey='departmentId' label='部门'></control-item>
                </el-row>
        </el-card>
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
            window.layoutData.common.departmentList = [];
            window.layoutData.common.firstCategoryList = [];
        
            var c = window.layoutData.controls;
            c.table = {
                data: [],
                userlist: {},
                page: 1,
                pageSize: 20,
                total: 0,
                loading: false,
                search: {
                    start: 0,
                },
                searchParams: {
                    userId: '',
                    departmentNo: [],
                    usable: '',
                },
                add: {
                    userId: '',
                    departmentId: '',
                    departmentNo: '',
                    departmentName: '',
                },
                dialog:{
                    add: false,
                    history: false,
                },
                get(){
                    let parameters = Object.assign(this.search);
                    this.loading = true;
                    if(parameters.departmentNo && parameters.departmentNo.length){
                        parameters.departmentNo = parameters.departmentNo.slice(-1)[0];
                    }
                    app.ajax.userDept.find(parameters).then(x => {
                        this.data  = x.data.item;
                        this.total = x.data.total;
                        this.loading = false;
                    }, _ => this.loading = false);
                },
                doAdd(){
                    if(this.add.departmentId&&this.add.departmentId.length){
                        this.add.departmentId = this.add.departmentId.slice(-1)[0];
                        let el = Array.from(app.flatten(app.controls.app.common.departmentList)).filter(x => x.id == this.add.departmentId)[0];
                        this.add.departmentNo   = el.no;
                        this.add.departmentName = el.name;
                    }
                    app.ajax.userDept.add(this.add).then(x => {
                        this.get();
                        this.dialog.add = false;
                    }, _ => this.get());
                }
                
            };
            $(function(){
                
                app.departmentListFunc();
                app.service['common/pagination/init'](c.table, _ => c.table.get());
                c.table.get();
            });

    </script>
</js>
