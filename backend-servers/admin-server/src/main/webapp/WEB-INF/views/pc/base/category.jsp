<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <body>
     <template scope='app' slot='controls'>
         <H1>品类列表</H1>
     </template>
     <template scope='app'>
            <el-row style='width: 1000px;'>
                <el-col :span='5'>
                   
                    <el-tree style='margin-top: 15px;' 
                        :current-node-key='app.controls.category.nodeid' 
                        :data='[app.controls.category.tree]' 
                        :default-expanded-keys='[app.controls.category.node ? app.controls.category.node.id : 0]'  
                        :highlight-current='true' 
                        :props="{label: 'name', children: 'children'}" 
                        @node-click='app.controls.category.beginEdit.apply(app.controls.category, arguments)'
                        data-id='category-tree' 
                        node-key='id' >

                    </el-tree>
                </el-col>
                <dialog-create :visible='app.controls.category.dialog.new' @update:visible='app.controls.category.dialog.new=arguments[0]' @ok='app.controls.category.saveNew()'>
                    <el-row style='padding: 5px 15px;'>
                    <control-item type='input' label='名称' :local='app.controls.category.new' datakey='name'></control-item>
                    </el-row>
                    <el-row style='padding: 5px 15px;'>
                    <control-item type='selectcascade'  :list='[app.controls.category.tree2]' :local='app.controls.category.new' datakey='parentId' label='上级品类'></control-item>
                </el-row>
                </dialog-create>
                <el-col :span='1'>
                    &nbsp;
                </el-col>
                <el-col :span='18' v-if='app.controls.category.node'>
                    <div style='height: 51px'>&nbsp;</div>
                    <control-table :app='app' :filter='app.controls.category.filter.bind(app.controls.category)'
                                   :filterkey='app.controls.category.search.keyword' tablekey='category'
                                   v-if='!(app.controls.category.node && (app.controls.category.node.leaf && app.controls.category.node.id != 0))'
                                   empty-text='请从左边选择一个节点' v-loading='app.controls.category.loading'>
                        <template slot='controls' :app='app' v-if='app.controls.category.node'>
                            <el-card>
                            <el-row>
                                <el-row>
                                    <control-item :app='app' :local='app.controls.category.node' datakey='name'
                                                  type='edit'
                                                  @saved='app.controls.category.update(app.controls.category.node, arguments[0])'
                                                  :item-disabled='!app.controls.app.Perm.write'></control-item>
                                    <el-button icon='plus' size='mini' type='new' @click='app.controls.category.new = {name: "", parentId: app.controls.category.newPath}; app.controls.category.dialog.new = true'
                                        :disabled='!app.controls.app.Perm.write'>新增子类
                                    </el-button>
                                </el-row>
                            </el-row>
                            <div style='background: #EEE; height: 2px; margin: 10px -20px;'>&nbsp;</div>
                            <el-row style='font-size: 12px; width: 500px; color: #AAA; line-height: 21px;'>
                            品类ID:{{app.controls.category.node.id}}<br>品类编码:{{app.controls.category.node.no}}<br>        
                            上级ID:{{app.controls.category.node.parentId}}</span>
                            </el-row>
                        </el-card>
                        <div style='height: 15px'>&nbsp;</div>
                        <el-row>
                            <el-col :span=6 style='padding: 5px 15px;'><control-item :app='app' :local='app.controls.category.search' datakey='keyword' type='input' label='关键字'></control-item></el-col>
                            <el-col :span=6 style='padding: 5px 15px;'><el-button type='primary' icon='search' @click='app.controls.category.fuzzySearch()'>模糊搜索</el-button></el-col>
                        </el-row>
                        </template>
                        <el-table-column label='操作' width='180'>
                            <template scope='inner'>
                                <el-button type='danger' size='mini' @click='app.controls.category.delete(inner.row)'
                                           :disabled='!app.controls.app.Perm.write'>删除
                                </el-button>
                                <el-popover trigger='click'>
                                    <el-button style='margin-left: 10px;' slot='reference' type='success' size='mini'
                                               @click='window.app.controls.category.moveTo=[]'
                                               :disabled='!app.controls.app.Perm.move'>移动
                                    </el-button>
                                        <el-cascader v-model='window.app.controls.category.moveTo' clearable change-on-select expand-trigger='hover' :options='[app.controls.category.partTree[inner.$index]]' :props="{label: 'name', children: 'children', value:'id'}" ></el-cascader>
                                        <el-button style='margin-left: 10px;' type='success' size='mini' @click='app.controls.category.moveNode(inner.row, app.controls.category.moveTo.slice(-1)[0])'>确定</el-button>
                                </el-popover>
                            </template>
                        </el-table-column>
                        <el-table-column prop='parentId' label='基本信息' width='280'>
                            <template scope='inner'>
                                <control-item :app='app' :local='inner.row' datakey='name' type='edit'
                                              style="display: inline-block"
                                              @saved='app.controls.category.update(inner.row, arguments[0])'
                                              :item-disabled='!app.controls.app.Perm.write'></control-item>
                                <span>(id:{{inner.row.id}})</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop='createAt' label='创建人/创建时间' width='170'>
                            <template scope='inner'>
                                {{inner.row.creator}}<br>
                                {{inner.row.createAt}}<br>
                            </template> 
                        </el-table-column>
                        <el-table-column prop='leaf' label='是否末级' width='100'><template scope='inner'>{{inner.row.leaf ? '是' : '否'}}</template></el-table-column>
                        
                        
                    </control-table>
                        <div v-if='app.controls.category.node && (app.controls.category.node.leaf && app.controls.category.node.id != 0)'>
                                <%@ include file="/WEB-INF/views/partial/product_attr.jsp" %>
                         </div>
                        
                </el-col>
            </el-row>
    </template>
</body>

<js>
	<script>
        window.layoutData = {controls: {}};
        var C = window.layoutData.controls;
        C.category = {
            tree: [],
            editingCategory: [],
            data: [],
            node: undefined,
            nodeid: 0,
            moveTo: [],
            dialog:{
                new: false,
            },
            new:{
                name: "",
                parentId: [0],
            }, 
            newPath: [],
            total: 1,
            page: 1,
            pageSize: 20,
            loading : false,
            search: {},
            searchParams: {},
            filter(row, index){
                return true;
                //if(!this.search.keyword) return true;
                //return row.name.indexOf(this.search.keyword) > -1;
            },
            saveNew(){
                let parameters = Object.assign({}, this.new);
                if(parameters.parentId && parameters.parentId.length){
                    parameters.parentId = parameters.parentId.slice(-1)[0];
                }else{
                    delete parameters.parentId;
                }
                if(parameters.parentId == -1) parameters.parentId = 0;
                app.ajax.category.add(parameters).then(function(data){
                    this.refresh(this.node);
                    this.dialog.new = false;
                }.bind(this));
            },
            pageChange(val){
                this.page = val;
                this.fuzzySearch();
            },
            pagesizeChange(val){
                this.pageSize = val;
                this.fuzzySearch();
            },
            fuzzySearch(){
                let parameters = {
                    start: this.page * this.pageSize - this.pageSize,
                    limit: this.pageSize,
                    name: this.search.keyword,
                    parentId: this.nodeid,
                };
                this.loading = true;
                app.ajax.category.fuzzy(parameters).then( x => {
                    this.loading = false;

                    this.data = x.data.item;
                    this.total = x.data.total;

                    
                    this.partTree = (this.data || []).map(data => app.copyTreeWithout(this.tree, function(x){
                        return x.id != this.nodeid;
                    })).map(x => (x.id = -1, x));
                    
                }, _ => this.loading = false );
            }
        };
        C.attrtable = {
            attrList: [],
            attrValueList: [],
            attrPanel: {
                tabName: 'attrList',
            },
            search: {},
            attrName: "",
            loading: false,
            searchParams: {
                title: '',
                status: null, 
                bindIs: null,
            },
            add: {

            },
            edit: {

            },
            pageSize: 20,
            page: 1,
            total: 1,
        };
        C.attrValueTable = {
            data: [],
            search: {},
            searchParams: {
                title: '',
                status: null,
            },
            pageSize: 20,
            page: 1,
            total: 1,
        };

        $(function(){
            app.service['category/tree'] = function(con){
                return app.ajax.category.tree({"no": ""}).then(x => {
                    con.tree = x.data.item;
                    con.tree2 = JSON.parse(JSON.stringify(x.data.item));
                    var dfs = function(tree, par){
                        if(tree) tree.parent = par;
                        if(tree && tree.children) tree.children.forEach(x => dfs(x, tree));
                    }
                    dfs(con.tree);
                    con.tree2.id = -1;
                });
            };

            
            app.service["category/attr"] = function(con, data){
                app.service['attr/list'](window.app.controls.attrtable);
                //app.ajax(con.list.getAttr, {data: app.postform({categoryId: data.id})}).then(x => app.controls.attrtable.attrList = x.data.item);
            };

            app.service['category/init'] = function(con){
                con.get = function(){
                    return app.service['category/tree'](con);
                };
                
                con.beginEdit = function(data){
                    con.node = data;
                    con.nodeid = data.id;
                    if(con.node){
                        var a = [con.node];
                        console.log(a);
                        while(a[0].id != 0){
                            var node = a[0];
                            a.splice(0, 0, node.parent);
                        }
                        a = a.map(x => x.id);
                        a[0] = -1;
                        con.newPath = a;
                    }
                    if(con.node.children){
                        con.total = con.node.children.length;
                    }
                    if(con.node.leaf){
                        app.service['category/attr'](con, data);
                    }
                    con.search.keyword = "";
                    
                    con.fuzzySearch()
                    
                }
                con.refresh = function(row){
                    con.get().then(function(){
                        app.traverseTree(con.tree, function(recur, node){
                            if(node.id == row.id){
                                con.beginEdit(node);
                            }
                            if(node.children){
                                node.children.forEach(recur);
                            }
                        });
                    });
                };
                con.addTop = function(){
                    app.service['category/add'](con, con.node);
                }
                con.delete = function(row){
                    app.service['category/delete'](con, row);
                }
                con.modify = function(row){
                    app.service['category/modify'](con, row);
                }
                con.moveNode = function(row, node_){
                    let node = node_;
                    if(node == -1) node = 0;
                    app.$confirm("要把类别" + app.formatNameId(row) + "及所有子类别移动到" + node + "下吗?").then(_ => app.service['category/modify'](con, row, node)).then(con.moveTo = []);
                }
                con.update = function(row, oldvalue){
                    app.service['category/update'](con, row, oldvalue);
                }
                app.service['category/tree'](con);
            };

        
            app.service['category/modify'] = function(con, row, node){
                app.ajax.category.move({currentCategoryId: row.id,targetCategoryId: node}).then(function(){
                    con.refresh(con.node);
                })
            }
            app.service['category/add'] = function(con, row){
                if(!row || row.id == undefined ){
                    app.$message("请先选择父节点");
                    return;
                }
                var newdata = {parentId: row.id, name: '新分类'};
                app.ajax.category.add(newdata).then(function(data){
                    con.refresh(con.node);
                });
            }
            app.service['category/delete'] = function(con, row){
                app.ajax.category.delete({id: row.id, name: row.name, parentId: row.parentId, no: row.no}).then(function(){
                    con.refresh(con.node);
                });
            }
            app.service['category/update'] = function(con, row, oldvalue){
                var row_ = Object.assign({}, row);
                delete row_.children;
                app.ajax.category.update({
                    id: row_.id, name: row_.name, parentId: row_.parentId, sortNo: row_.sortNo, usable: row_.usable,
                    no: row_.no, leaf: row_.leaf
                }).then(_ => con.refresh(con.node), function () {
                    con.refresh(con.node);
                })
            }
        })
        $(function(){
            app.service['category/init'](app.controls.category);
            app.service["attr/init"](app.controls.attrtable);
            app.service["attrvalue/init"](app.controls.attrValueTable);
            app.controls.app.ajax.lang.find().then(x => x.data.item).then(x => x.map(x => ({key: x.name, value: x.langCode}))).then(x => app.controls.attrtable.langs = x);
        }); 
        
	</script>
</js>