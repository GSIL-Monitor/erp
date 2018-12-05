<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <body>
            <template scope='app' slot='controls'>
                <H1>菜单列表</H1>
            </template>
            <template scope='app'>
                <el-col style='width: 1000px;'>
                    <el-col :span='8'>
                        <el-tree style='font-size: 12px;' :props='{label: "name", children: "children"}' :v-loading='true' :current-node-key='app.controls.menus.nodeid'
                            :data="app.controls.menus.menu.children" :default-expanded-keys="app.controls.menus.nodeids" :highlight-current='true'
                            @node-click='app.controls.menus.clicked.apply(app.controls.menus, arguments)' node-key='id' style='width: 300px;'
                            :default-expand-all='true' v-loading='app.controls.menus.loading'>
                        </el-tree>
                    </el-col>

                    <el-col :span='16'>
                        <el-card v-if='app.controls.menus.selected'>
                            <H3>{{app.controls.menus.selected.name}}</H3>
                            <el-row>
                                <el-col :span="7">
                                    <control-item :local='app.controls.menus.selected' datakey='no' label='编码' type='readonly'></control-item>
                                </el-col>

                                <el-col :span="8">
                                    <control-item :local='app.controls.menus.selected' datakey='name' label='名称' type='input'></control-item>
                                </el-col>
                                <el-col :span="9">
                                    <control-item :local='app.controls.menus.selected' datakey='parentId' label='父节点' type='select' :tree='app.controls.menus.partTree'
                                        :treetolist='true' :treelistprops="{'key': 'name', 'value': 'id', 'children': 'children'}"></control-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="8">
                                    <control-item :local='app.controls.menus.selected' datakey='icon' label='图标' type='select' :itemclass="'material-icons'"
                                        :list='app.controls.menus.icons'></control-item>
                                </el-col>
                                <el-col :span="10">
                                    <control-item :local='app.controls.menus.selected' datakey='keyword' label='关键词' type='input'></control-item>
                                </el-col>

                                <el-col :span="6">
                                    <control-item :local='app.controls.menus.selected' datakey='sort' label='排序' type='input'></control-item>
                                </el-col>
                            </el-row>
                            <el-row>

                                <el-col :span="24">
                                    <control-item :local='app.controls.menus.selected' datakey='url' label='地址' type='input'></control-item>
                                </el-col>
                            </el-row>


                            <el-row>
                                <el-col :span='24'>
                                    <control-item :local='app.controls.menus.selected' datakey='remark' label='备注' type='textarea'></control-item>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="4">
                                    <el-button icon='plus' type='new' @click='app.controls.menus.doAdd()'>新增</el-button>
                                </el-col>
                                <el-col :span="4">
                                    <el-button icon='plus' type='danger' @click='app.controls.menus.delete()'>删除</el-button>
                                </el-col>
                                <el-col :span="4">&nbsp;</el-col>
                                <el-col :span="4">&nbsp;</el-col>
                                <el-col :span="4">&nbsp;</el-col>
                                <el-col :span="4">
                                    <el-button icon='arrow-up' type='primary' @click='app.controls.menus.save()'>保存</el-button>

                                </el-col>

                            </el-row>
                        </el-card>
                        <el-card v-if='app.controls.menus.selected && (!app.controls.menus.selected.children || !app.controls.menus.selected.children.length)' style='position: relative;'>
                            <H3>元素绑定</H3>
                            <%--<div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>新增权限</div>
                            <el-row>
                                <el-col :span='24' style='padding: 15px 0px;'>
                                    <div>
                                    <el-row>
                                        <el-col :span='10' style='padding: 15px 5px;'><control-item type='input' :local='app.controls.menus.element' label='权限名' datakey='name'></control-item></el-col>
                                        <el-col :span='10' style='padding: 15px 5px;'><control-item type='input' :local='app.controls.menus.element' label='权限关键字' datakey='keyword'></control-item></el-col>
                                        <el-col :span='4' style='text-align: right; padding: 15px 5px;'><el-button icon='arrow-up' type='new' icon='plus' @click='app.controls.menus.addElement()'>新增保存</el-button></el-col>
                                    </el-row>
                                    </div>
                                </el-col>
                            </el-row>--%>
                            <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>
                                调整元素绑定
                            </div>
                            <div style='position: relative;'>
                                <el-select type='select' v-model='app.controls.menus.has' multiple @change='app.controls.menus.changeElement' style='width: 100%;'>
                                    <el-option v-for='item in app.controls.menus.elementList' :key='item.name' :label='item.name' :value='item.id'>
                                        
                                    </el-option>
                                </el-select>
                            </div>
                        </el-card>
                    </el-col>

                </el-col>

                <dialog-create title='新增菜单' :visible='app.controls.menus.dialog.new' @update:visible='app.controls.menus.dialog.new=arguments[0]'
                @cancel='app.controls.menus.dialog.new = false;'
                @ok='app.controls.menus.addMenu().then(_ => (app.controls.menus.dialog.new=false, window.app.service["menu/list"](app.controls.menus)));'>
                        <el-card :title='app.controls.menus.new.name' v-if='app.controls.menus.new'>
                                <el-row>
                                    <el-col :span="12">
                                        <control-item :local='app.controls.menus.new' datakey='name' label='名称' type='input'></control-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <control-item :local='app.controls.menus.new' datakey='parentId' label='父节点' type='select' :tree='app.controls.menus.menu'
                                            :treetolist='true' :treelistprops="{'key': 'name', 'value': 'id', 'children': 'children'}"></control-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="8">
                                        <control-item :local='app.controls.menus.new' datakey='icon' label='图标' type='select' :itemclass="'material-icons'"
                                            :list='app.controls.menus.icons'></control-item>
                                    </el-col>
                                    <el-col :span="10">
                                        <control-item :local='app.controls.menus.new' datakey='keyword' label='关键词' type='input'></control-item>
                                    </el-col>
    
                                    <el-col :span="6">
                                        <control-item :local='app.controls.menus.new' datakey='sort' label='排序' type='input'></control-item>
                                    </el-col>
                                </el-row>
                                <el-row>
    
                                    <el-col :span="24">
                                        <control-item :local='app.controls.menus.new' datakey='url' label='地址' type='input'></control-item>
                                    </el-col>
                                </el-row>
    
    
                                <el-row>
                                    <el-col :span='24'>
                                        <control-item :local='app.controls.menus.selected' datakey='remark' label='备注' type='textarea'></control-item>
                                    </el-col>
                                </el-row>
                            </el-card>
                </dialog-create>
            </template>
        </body>
        <js>
            <style>
                .el-col {
                    padding: 5px 15px;
                }
            </style>
            <script>
                window.layoutData.controls.menus = { menu: [], selected: undefined, loading: false, nodeid: -1, new: undefined, dialog: {new: false}, has: [], elementList: [], element: {keyword: '', name: ''} };
                $(function(){
                       

                app.service['menu/init'] = function(con){
                    if(!con.initList){
                        con.initList = true;
                        
                        

                        var prefix = "/admin/menu";
                        con.list = {
                            query: prefix + "/query",
                            get: prefix+"/get",
                            delete: prefix + "/delete",
                            save:    prefix + "/save",
                            update: prefix + "/update",
                        }   
                        app.service['common/pagination/init'](con, 'menu/list');
                        app.service['menu/list'](con);

                        con.get = function(){
                            return app.service['menu/list'](con);
                        }
                        con.refresh = function(){
                            con.get().then(function(){
                                traverseTree(con.menu, function(recur, node){
                                    if(node.id == con.nodeid){
                                        con.clicked(node);
                                    }
                                    if(node.children){
                                        node.children.forEach(recur);
                                    }
                                });
                            });
                        };
                        con.clicked = function(data){
                            con.selected = Object.assign({}, data);
                            con.nodeid = data.id;
                            con.partTree = app.copyTreeWithout(con.menu, x => x.id != data.id);
                            con.refreshNode = _ => con.clicked(data);
                            Promise.all([
                                app.ajax.element.list(),
                                app.ajax.element.find({menuKeyword: data.keyword})
                            ]).then(([a, b]) => {
                            con.has = b.data.item.map(x => x.id);
                            con.elementList = a.data.item;
                            con.lastList = con.has.slice();
                            });
                        }
                        
                        con.render = function(h, {node, data, store}){
                            return h("span",
                                [
                                    h("span", data.name + "(ID:" + data.id + ")"),
                                    h("span", {attrs: {"style": "float: right; padding-top: 8px; padding-right: 8px; vertical-align: middle;"}}, [
                                        h("el-button", {attrs: {'size': 'mini'}, "on":  {click(){ con.add(data) }} }, "新增"),
                                        h("el-button", {attrs: {'size': 'mini'}, "on": {click(){ con.delete(data) }} }, "删除"),
                                        h("el-button", {attrs: {'size': 'mini'}, "on": {click(){ con.move(data) }} }, "移动"),
                                    ])
                                ]
                            )
                        };
                        con.delete = function(){
                            app.service["menu/delete"](con);
                        }
                        con.add = function(data){
                            app.service["menu/add"](con, data);
                        }
                        con.save = function(){
                            app.service["menu/update"](con);
                        }
                    };

                    app.service["menu/update"] = function(con){
                        let a = Object.assign({}, con.selected);
                        delete a.children;
                        app.ajax(con.list.update, {data: app.postform(a), method: 'post'}).then(function(){
                            con.refresh(con.selected);
                        });
                    }

                    app.service["menu/add"] = function(con, data){
                            var template = {
                                    name: "新菜单",
                                    keyword: "admin.newmenu" + +new Date(),
                                    url: "#",
                                    parentId: con.selected.id,
                                };
                                app.ajax(con.list.save, {data: app.postform(template)}).then(function(result){
                                    con.get().then(function(){
                                        template.id = result.data.item;
                                        con.clicked(template);
                                        con.refresh();
                                    })
                                });
                            }
                    }
                    app.service["menu/delete"] = function(con){
                        app.$confirm("确实要删除菜单[" + (con.selected.name) + "](id: " + (con.selected.id) + ")吗?", "提示").then(function(){
                            app.ajax(con.list.delete, {data: app.postform({id: con.selected.id}), method: 'post'}).then(function(){
                                con.refresh({id: -1});
                            });
                        })
                    }

                    app.service['menu/list'] = function(con){
                        con.loading = true
                        return app.dajax("获取菜单列表", con.list.query).then(x =>con.menu = x.data.item).then(_ => con.loading = false).then(_ => {
                            app.controls.menus.nodeids = [];
                            var dfs = (node) => {
                                app.controls.menus.nodeids.push(node.id);
                                node.children && node.children.length && node.children.forEach(dfs);
                            }  
                            dfs(app.controls.menus.menu);
                        });
                    }
                });

                $(function () {
                    var icons = app.iconNames;
                    app.controls.menus.icons = icons.map(x => ({ key: x, value: x }));
                    app.controls.menus.doAdd = function(row){
                        app.controls.menus.new = {
                            name: "",
                            parentId: app.controls.menus.selected.id,
                            keyword: "admin.newmenu" + +new Date(),
                            url: "#",
                        };
                        app.controls.menus.dialog.new = true;
                    };
                    app.controls.menus.addMenu = function(){
                        return app.ajax.menu.save(this.new);
                    }
                    app.controls.menus.addElement = function(){
                        app.ajax.element.add({
                            keyword: app.controls.menus.element.keyword,
                            name: app.controls.menus.element.name,
                            menuId: app.controls.menus.nodeid,
                            menuKeyword: app.controls.menus.selected.keyword,
                        }).then(
                            _ => app.controls.menus.refreshNode(),
                            _ => app.controls.menus.refreshNode(),
                        )
                    }
                    app.controls.menus.changeElement = function(val){
                        var con = app.controls.menus;
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
                        console.log("====>" + id);
                        console.log("===>" + app.controls.menus.selected.keyword);

                        if(id < 0){
                            let el = con.elementList.find(x => x.id == -id);
                            app.ajax.element.delete({
                                elementId: -id,
                                name: el.name,
                                id: -id,
                                keyword: el.keyword,
                                menuId: con.nodeid,
                                menuKeyword: app.controls.menus.selected.keyword
                            }).then(
                                _ => app.controls.menus.refreshNode(),
                                _ => app.controls.menus.refreshNode(),
                            )
                        }else if(id > 0){
                            let el = con.elementList.find(x => x.id == id);
                            app.ajax.element.add({
                                elementId: id,
                                name: el.name,
                                keyword: el.keyword,
                                menuId: con.nodeid,
                                menuKeyword: app.controls.menus.selected.keyword
                            }).then(
                                _ => app.controls.menus.refreshNode(),
                                _ => app.controls.menus.refreshNode(),
                            )
                        }
                    }
                    app.service['menu/init'](app.controls.menus);
                });

            </script>
        </js>