window.initAttr = function(){

    app.service["attr/init"] = function(con){
        if(!con.initList){
            con.initList = true;
            app.service['common/pagination/init'](con, 'attr/list');
            var prefix = "/product/base/attribute";
            con.list = {
                query:  prefix + "/find",
                queryForCate: prefix + "/findList",
                get:    prefix + "/get",
                delete: prefix + "/delete",
                add:    prefix + "/add",
                update: prefix + "/update",
                bind:   '/product/base/attribute/bindingCategory',
                unbind: '/product/base/attribute/unBindingCategory'
            };
            app.service['attr/list'](con);
        }
        con.refresh = function(){
            app.service['attr/list'](con);
        };

        con.addClick = function(){
            app.$prompt("请输入新建的属性名").then(function(x){
                if(x.action == 'confirm'){
                    app.service['attr/add'](con, x.value);
                }
            });
        }
        con.addClick2 = function(){
            app.$prompt("请输入新建的属性名").then(function(x){
                if(x.action == 'confirm'){
                    var product = window.app.controls.product_table.selected;
                    app.ajax.productAttr.add({
                        productId: product.id,
                        categoryId: product.categoryId,
                        title: x.value,
                    }).then(
                        _ => con.refresh(),
                        _ => con.refresh()
                    )
                }
            });
        }
        con.bindProductAttr = function(row, isBind){
            
            
            var product = window.app.controls.product_table.selected;
            app.ajax.product[isBind ? 'bind' : 'unbind']({
                productId: product.id,
                attributeId: row.id,
                bindIs: isBind,
            }).then(
                _ => window.app.controls.product_table.refresh(),
                _ => window.app.controls.product_table.refresh()
            )
        
            
        }

        con.addLangClick = function(row){
            con.add = {
                langCode: "",
                name: "",
            }
            con.add.attributeId = row.id;
        }
        con.editLangClick = function(row, item){
            con.edit = app.pluck(item, ["langCode", "name", "id"]);
            con.edit.attributeId = row.id;
        }
        con.doAddAttrLang = function(id){
            con.add.attributeId = id;
            app.ajax.attrLang.add(con.add).then(function(){
                con.refresh();
                app.dismissPopover();
            })
        }
        con.doEditAttrLang = function(){
            app.ajax.attrLang.update(con.edit).then(function(){
                con.refresh();
                app.dismissPopover();
            })
        }
        con.doDeleteAttrLang = function(){
            app.ajax.attrLang.delete(con.edit).then(function(){
                con.refresh();
                app.dismissPopover();
            })
        }

        con.addValueLangClick = function(row){
            con.add = {
                langCode: "",
                name: "",
            }
            con.add.attributeValueId = row.id;
            con.add.attributeId      = window.app.controls.attrValueTable.search.attributeId;
        }
        con.editValueLangClick = function(row, item){
            con.edit = app.pluck(item, ["langCode", "name", "id"]);
            con.edit.attributeValueId = row.id;
            con.edit.attributeId      = window.app.controls.attrValueTable.search.attributeId;
        }
        con.doAddAttrValueLang = function(id){
            app.ajax.attrValueLang.add(con.add).then(function(){
                window.app.controls.attrValueTable.refresh();
                app.dismissPopover();
            })
        }
        con.doEditAttrValueLang = function(){
            app.ajax.attrValueLang.update(con.edit).then(function(){
                window.app.controls.attrValueTable.refresh();
                app.dismissPopover();
            })
        }
        con.doDeleteAttrValueLang = function(row){
            app.ajax.attrValueLang.delete(con.edit).then(function(){
                window.app.controls.attrValueTable.refresh();
                app.dismissPopover();
            })
        }
        con.delClick = function(row){
            app.$confirm("要删除属性" + app.formatNameId(row) + "吗?").then(function(x){
                app.service['attr/delete'](con, row.id);
            }).then(_ => (window.app.controls.attrValueTable.search.attributeId == row.id) ? (window.app.controls.attrValueTable.search.attributeId = 0) : 1);
        }
        con.delClick2 = function(row){
           
            app.ajax.productAttr.delete({
                 attributeId: row.id,
                 title: row.title,
                 id: row.id,
            }).then(_ => con.refresh(), _ => con.refresh());
           
        }
        con.updateTitle = function(row, oldvalue){
            app.service['attr/updateTitle'](con, row, oldvalue);
        }
        con.doBind = function(row, bind){
            var cate = window.app.controls.category.nodeid;
            var attr = row.id;
            app.service['attr/bindCategory'](con, cate, attr, bind);
        }
        con.editProperty = function(row){
            window.app.controls.attrValueTable.search.attributeId = row.id;
            window.app.controls.attrValueTable.refresh();
            app.controls.attrtable.attrPanel.tabName = "attrValueList";
            con.attrName = row.title;
            con.currentEdit = row;
        }
    };

    

    app.service['attr/list'] = function(con){
        con.loading = true;
        if(window.app.controls.product_table && window.app.controls.product_table.selected){
            var product = window.app.controls.product_table.selected;
            var ids     = {};
            window.app.controls.product_table.selected.attributeList.forEach(function(item){
                ids[item.id] = item;
                item.bindIs = true;
            });
            let parameters = Object.assign({}, con.search, {limit: con.pageSize});
            parameters.categoryId = product.categoryId;
            app.ajax.productAttr.find(parameters).then(x => {
                x.data.item.forEach(function(item){
                    if(!ids[item.id]){
                        ids[item.id] = item;
                        item.bindIs = false;
                    }
                });
                con.attrList = Object.keys(ids).map(x => ids[x]);
                con.total    = con.attrList.length;
                con.loading = false;
            })
            return;
        }

        
        let parameters = Object.assign({}, con.search, {limit: con.pageSize});
        var hasid = window.app.controls.category;
        if(hasid){
            parameters.categoryId = hasid.nodeid;
        }

        app.ajax(hasid ? con.list.queryForCate : con.list.query, {method: 'post', data: app.postform(parameters)}).then(x => {
            con.attrList = x.data.item;
            con.total = x.data.total;
        }).then(_ => con.loading = false);
    };

    app.service['attr/add'] = function(con, val){
        app.ajax(con.list.add, {method: 'post', data: app.postform({title: val})}).then(_ => {
            con.refresh();
        });
    };

    app.service['attr/delete'] = function(con, id){
        app.ajax(con.list.delete, {method: 'post', data: app.postform({attributeId: id})}).then(_ => {
            con.refresh();
        });
    };

    app.service['attr/updateTitle'] = function(con, row, old){
        app.ajax(con.list.update, {method: 'post', data: app.postform({id: row.id, title: row.title})}).then(
            function(){
                con.refresh();
            },
            function(){
                row.title = old;
                con.refresh();
            }
        )
    };

    app.service['attr/bindCategory'] = function(con, cid, aid, bind){
        console.log(arguments);
        app.ajax(bind ? con.list.unbind : con.list.bind, {method: 'post', data: app.postform({categoryId: cid, attributeId: aid, bindIs: !bind})}).then(
            function(){
                con.refresh();
            },
            function(){
                con.refresh();
            }
        )
    };

    app.service["attrvalue/init"] = function(con){
        if(!con.initList){
            con.initList = true;
            app.service['common/pagination/init'](con, 'attrvalue/list');
            var prefix = "/product/base/attributeValue";
            con.list = {
                query:  prefix + "/findByAttributeId",
                get:    prefix + "/get",
                delete: prefix + "/delete",
                add:    prefix + "/add",
                update: prefix + "/update",
            };
            app.service['attrvalue/list'](con);
        }
        con.refresh = function(){
            app.service['attrvalue/list'](con);
        };
        con.doAdd = function(){
            if(con.search.attributeId){
                app.$prompt("请输入新建的属性值标题").then(function(x){
                    if(x.action == 'confirm'){
                        app.service['attrvalue/add'](con, con.search.attributeId, x.value);
                    }
                })
            }
        };
        con.delClick = function(row){
            if(con.search.attributeId){
                app.$confirm("确实要删除属性值"+app.formatNameId(row)+"吗?").then(function(){
                    app.service['attrvalue/delete'](con, con.search.attributeId, row.id);
                })
            }
        }

        con.updateTitle = function(row, oldvalue){
              if(con.search.attributeId){
                app.service['attrvalue/updateTitle'](con, con.search.attributeId, row, oldvalue);
              }
        }

    };

    app.service['attrvalue/list'] = function(con){
        if(con.search.attributeId){
            con.loading = true;
            let parameters = Object.assign({}, con.search, {limit: con.pageSize});
            app.ajax(con.list.query, {method: 'post', data: app.postform(parameters)}).then(x => {
                con.data = x.data.item;
                con.total = x.data.total;
            }).then(_ => con.loading = false);
        }
    };

    app.service['attrvalue/add'] = function(con, aid, title){
        app.ajax(con.list.add, {method: 'post', data: app.postform({attributeId: aid, title: title})}).then(function(){
            con.refresh();
        }).catch(function(){
            con.refresh();
        })
    };

    app.service['attrvalue/delete'] = function(con, aid, id){
        app.ajax(con.list.delete, {method: 'post', data: app.postform({attributeId: aid, id: id, attributeValueId: id})}).then(function(x){
            con.refresh();
        });
    }

    app.service['attrvalue/updateTitle'] = function(con, attrid, row, old){
        app.ajax(con.list.update, {method: 'post', data: app.postform({attributeId: attrid, attributeValueId: row.id, id: row.id, title: row.title})}).then(
            function(){
                con.refresh();
            },
            function(){
                row.title = old;
                con.refresh();
            }
        )
    };
}

