<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
     <template scope='app' slot='controls'>
        <H1>职位列表</H1>
    </template>
    <template scope='app'>
    <el-row>
            <el-col style='width: 600px;'>
                <control-table :app='app' tablekey='jobtable' v-loading='app.controls.jobtable.loading' style='width: 600px;'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='12' style='padding: 5px 15px;'>
                        <control-item type='inputAsync'
                                      @autocomplete='app.local.searchClick()'
                                      :complete-array-url-func='text=>window.app.ajax("/admin/job/getNameBySearch?search="+text).then(result => result.data.item.map(x => ({value: x})))'
                                      :local='app.local.searchParams'
                                      datakey='name'
                                      label='职位'>
                        </control-item>
                    </el-col>
                    <el-col :span='12' style='padding: 5px 15px;'>
                        <el-button @click='app.local.searchClick()' type='primary' icon='search'>搜索</el-button>
                    </el-col>
                </template>
                <el-table-column prop='职位授权' v-popover:popoveredit label='职位授权' width='100'>
                    <template scope='inner'><div>
                        <!--
                         <el-popover trigger="click" placement='right' @show='app.controls.jobtable.preparePopup(self, inner.$index, inner.row)'>
                            <el-tree :data='item'
                                      v-if='item'
                                      v-for='item in app.controls.jobtable.menus'
                                      :ref="'tree-' + inner.$index"
                                      node-key='id'
                                      :check-strictly='true'
                                      :props="{label: 'name', children: 'children'}"
                                      :default-expand-all='true'
                                      show-checkbox
                                      :render-content='app.controls.jobtable.renderNode'
                                      @check-change='app.controls.jobtable.checkChange.apply(app.controls.jobtable, arguments)'>
                            </el-tree>

                        </div></el-popover>
                    -->
                        <el-button type='primary' size='mini' @click='app.controls.jobtable.dialogOpenAuth(inner.row); app.controls.jobtable.preparePopup(app.controls.app.$refs.tree, inner.row)' slot='reference'>职位授权</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='数据权限' v-popover:popoveredit label='数据权限' width='100'>
                    <template scope='inner'><div>
                        <el-button type='primary' size='mini' @click='app.controls.jobtable.dialogOpenDataAuth(inner.row); app.controls.jobtable.preparePopup(app.controls.app.$refs.tree, inner.row)' slot='reference'>数据权限</el-button>
                    </div>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='id' width='100'></el-table-column>
                <el-table-column prop='name' label='职位' width='250'></el-table-column>
                <el-table-column prop='remark' label='描述' width='100'></el-table-column>
                <!--
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status  == '1' ? '开' : '关'}}</template>
                </el-table-column> 
                -->

                    <!--职位授权列表<BR>
                    <span v-if='app.local.editingName'>职位: {{app.local.editingName}}<BR>-->
            </control-table>
        </el-col>
            <el-col style='width: 15px;'>&nbsp;</el-col>
            <el-col :style='`width: auto; min-width:200px;  font-size: 12px; line-height: 12px; color: #222;`'  v-if='item.length' v-for='item in app.controls.jobtable.menus' >
                <el-card :style='`background: #F6F6F6;`'>
                    <H3 slot='header'><em>{{app.controls.jobtable.editingName}}</em>的职位菜单授权</H3>
                <div :style='`height: \${window.innerHeight - 200}px; overflow-y: auto; background: #F6F6F6;`'>
                <el-tree :data='item'
                    node-key='id'
                    id='jobTree'
                    :check-strictly='true'
                    :props="{label: 'name', children: 'children'}"
                    :default-expand-all='true'
                    style='background: #F6F6F6;'
                    show-checkbox
                    :render-content='app.controls.jobtable.renderNode'
                    @check-change='app.controls.jobtable.checkChange.apply(app.controls.jobtable, arguments)'>
                </el-tree>
            </div>
                </el-card>N
            </el-col>


        <el-col style='width: 15px;'>&nbsp;</el-col>
        <el-col :style='`width: auto; min-width:200px;  font-size: 12px; line-height: 12px; color: #222;`'  v-if ='app.controls.jobtable.showJobAuthority'>
            <el-card :style='`background: #F6F6F6;`'>
                <H3 slot='header'><em>{{app.controls.jobtable.editingName}}</em>的数据权限授权</H3>
                <br :style='`height: 200px; overflow-y: auto; background: #F6F6F6;`'>
                <el-radio-group v-model="app.controls.jobtable.radio">
                    <el-radio @click.native.prevent.stop="app.controls.jobtable.getRadio(0)" :label="0" >全公司</el-radio>
                    <el-radio @click.native.prevent.stop="app.controls.jobtable.getRadio(1)" :label="1" >本部门</el-radio>
                    <el-radio @click.native.prevent.stop="app.controls.jobtable.getRadio(2)" :label="2" >个人</el-radio>
                </el-radio-group>
                </div>
            </el-card>
        </el-col>



            <el-col style='width: 15px;'>&nbsp;</el-col>
            <!--<el-dialog v-model='app.controls.jobtable.dialog.element' :modal-append-to-body='true' :lock-scroll='true'>-->
            <el-col :style='`width: 400px; height: \${window.innerHeight - 100}px; position: relative; font-size: 12px; line-height: 12px;`' v-if='app.controls.jobtable.dialog.element'>
                <el-card :style='`height: 150px; left: 50%; top: 50%; transform: translate(-50%, -50%); overflow-y: auto; background: #F6F6F6; position: relative`'>
                    <H3><em>{{app.controls.jobtable.editingName}}</em> 在 <em>{{app.controls.jobtable.localOpenElementData.data.name}}</em> 页面的元素授权</H3>
                    <el-select type='select' v-model='app.controls.jobtable.has' multiple @change='app.controls.jobtable.changeElement' style='width: 100%;'>
                        <el-option v-for='item in app.controls.jobtable.elementList' :key='item.name' :label='item.name' :value='item.id'>

                        </el-option>
                    </el-select>
                </el-card>
            </el-col>
            <!--</el-dialog>-->
        </template>
</body>
<js>
    <script>
        var C = window.layoutData.controls;
        C.departmentData = [];
        C.jobtable = {
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
            showJobAuthority:false,
            radio:2,
            getRadio: function(x){
                if(app.controls.jobtable.radio == x){
                    return ;
                }
                app.controls.jobtable.radio = x;
                var param = {jobId: this.rolePrivId,authorityId: parseInt(x)};
                app.ajax('/admin/jobAuthorityRel/insert', {
                    method: 'post',
                    params: (param)
                }).then(_=>{

                });
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
                element: false,
            },
            renderNode(h, {node, data, store}){
                return h("span", null,
                    [
                        h("span", null, [node.label]),
                        h("span", null,
                            (data.children && data.children.length) ? [] : [
                                h("el-button", {attrs: {type: 'primary', size: 'mini', style: 'float: right; transform: translateY(8px); margin-left: 10px; margin-right: 10px;'}, on: {'click': function(){
                                    app.controls.jobtable.openElement({menuId: data.id, keyword: data.keyword, jobId: app.controls.jobtable.jobId, data: data});
                                }}}, "元素授权"),
                            ])
                    ]
                )
            },
            openElement(data){
                let con = app.controls.jobtable;
                con.localOpenElementRefresh = _ => con.openElement(data);
                con.localOpenElementData = data;
                Promise.all([
                    app.ajax.element.job({menu_id: data.menuId, menuKeyword: data.keyword, jobId: data.jobId}),
                    app.ajax.element.find({menu_id: data.menuId, menuKeyword: data.keyword})
                ])
                .then(([has, list]) => {
                    try{
                        con.has = has.data.item[0].elements.filter(x => x.checked).map(x => x.id);
                    }catch(e){
                        app.$message("请先为菜单绑定元素");
                        return;
                    }

                    con.elementList = list.data.item;
                    con.lastList = con.has.slice();
                    if(!con.dialog.element) con.dialog.element = true;
                });
            },
            changeElement(val){
                var con = app.controls.jobtable;
                var ids = {};
                var id;
                val.forEach(x => ids[x] = 1);
                try{
                    con.lastList.forEach(x => {
                        if(!ids[x]){
                            id = -x;
                            throw 'found';
                        }
                        delete ids[x];
                    });
                    id=Object.keys(ids)[0];
                }catch(e){

                }
                console.log(id);

                if(id < 0){
                    app.ajax.job.updateElement({
                        jobId:  con.localOpenElementData.jobId,
                        menuId: con.localOpenElementData.menuId,
                        menuKeyword: con.localOpenElementData.keyword,
                        elementId: -id,
                        isChecked: false
                    }).then(
                        _ => app.controls.jobtable.localOpenElementRefresh(),
                        _ => app.controls.jobtable.localOpenElementRefresh()
                    )
                }else if(id > 0){
                    app.ajax.job.updateElement({
                        jobId:  con.localOpenElementData.jobId,
                        menuId: con.localOpenElementData.menuId,
                        menuKeyword: con.localOpenElementData.keyword,
                        elementId: id,
                        isChecked: true
                    }).then(
                        _ => app.controls.jobtable.localOpenElementRefresh(),
                        _ => app.controls.jobtable.localOpenElementRefresh()
                    )
                }
            }
        };
        $(function(){

            app.service['job/init'] = function(con){
                if(!con.initList){
                    con.initList = true;
                    app.service['common/pagination/init'](con, 'job/list');
                    con.searchParams.complete = {
                        name: function(text, cb){
                            app.ajax.job.search({search: text}).then(result => cb(result.data.item.map(function(x){return {value: x}})));
                        }
                    }
                    con.checkChange = function (data, val, selected){

                        if(!val && selected){
                            val = true;
                            con.keys[data.id] = val;
                            return;
                        }
                        con.keys[data.id] = val;

                        app.service['job/menucheck'](con, data.name, data.id, val);
                    }

                    con.preparePopup = function(tree, row){
                    con.tree  = tree; //w.$refs['tree'] //w.$refs['tree-' + index][0];
                    con.jobId = row.id;
                    }

                    con.dialogOpenAuth = function(row){
                        this.rolePrivId = row.id;
                        this.editingName = row.name;
                        app.service['job/menu'](this);
                    }

                    con.dialogOpenDataAuth = function(row){
                        this.rolePrivId = row.id;
                        this.editingName = row.name;
                        app.service['job/jobAuthorityRel'](this);
                    }
                }
            };

            app.service['job/menucheck'] = function(con, name, id, val){
                if(con.updatingCheck) return false;
                app.ajax.job.update({jobId: con.rolePrivId, menuId: id, isChecked: val.toString()});
            };


            app.service['job/menu'] = function(con){
                con.loading = true;
                app.ajax.job.getPermission({jobId: con.rolePrivId}).then(x => {
                con.menus = [x.data.item.children || []];
                con.updatingCheck = true;
                Vue.nextTick(function(){
                        let keys = [];
                        var tree = $("#jobTree")[0].__vue__;
                        let dfs = function(obj){
                            tree.setChecked(obj.id, obj.checked, false);
                            if(obj.checked){
                                keys.push(obj.id);
                            }
                            if(obj.children){
                                obj.children.forEach(dfs);
                            }
                        };
                        dfs(x.data.item);
                        con.keys = {};
                        keys.forEach(function(x){con.keys[x] = true});
                        con.loading = false;
                        setTimeout(function(){
                            con.updatingCheck = false;
                        }, 500);
                    });
                });
            };

            app.service['job/jobAuthorityRel'] =  function(con) {
                con.loading = true;
                var param = {jobId: con.rolePrivId};
                app.ajax('/admin/jobAuthorityRel/findByJobId', {
                    method: 'post',
                    params: (param)
                }).then(result => {
                    console.log(result.data);
                    if(result.data.item[0] == null){
                        app.controls.jobtable.radio = 2;
                    }else{
                        var jobAuthority =  result.data.item[0].jobAuthorityRelEnum;
                        if("all" == jobAuthority){
                            app.controls.jobtable.radio = 0;
                        }else if("myDepartment" == jobAuthority){
                            app.controls.jobtable.radio = 1;
                        }else{
                            app.controls.jobtable.radio = 2;
                        }
                        console.log(result.data.item[0].jobAuthorityRelEnum.id);
                    }
                    console.log(result);
                    app.controls.jobtable.showJobAuthority = true;
                    con.loading = false;
            });
            }

            app.service['job/list'] = function(con){
                let parameters = {
                    name:    con.search.name,
                    remark:  con.search.remark,
                    status:  con.search.status,
                    start:   con.search.start,
                    limit:   con.pageSize,
                }
                con.loading = true;
                app.ajax.job.query(parameters).then(x => {
                    con.data = x.data.item;
                    var total = con.total = x.data.total;
                    con.loading = false;
                });
            };
            app.service['department/list'](app.controls.departmentData);
            app.service['job/init'](app.controls.jobtable);
            app.service['job/list'](app.controls.jobtable);
        });
    </script>
</js>

