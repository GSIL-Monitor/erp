Object.getMethod = function(a, b){
    return a[b].bind(a);
}
$(function(){
    
     if(!document.title){
         document.title = $(".topcontrols .controls H1").text();
     }
     var copyTreeWithout = function(t, f){
        if(!f(t)){
            return undefined;
        }else{
            var children = (t.children || []).map(x => copyTreeWithout(x, f)).filter(x => x);
            return Object.assign({}, t, {children: children && children.length == 0 ? undefined : children});
        }
    };

    var formatNameId = function(obj, key){
        var name = obj['title'] || obj['name'];
        return ["[", name, "](id: ", obj.id, ")"].join("");
    }

    var traverseTree = function(tree, f){
       f(tree => traverseTree(tree, f), tree);
    };

    Object.assign(app, {
        copyTreeWithout,
        formatNameId,
        traverseTree,
    });

    app.countryfunc = (url) => window.app.controls.app.ajax(url).then(x => x.data.item.map(y => ({key: y.zoneName, value: y.zoneCode})));
    app.enum = function(name){
        var url = "/commons/enums?enumCls=" + name + "&enumPkg=com.stosz.product.ext.enums";
        return app.ajax(url, {method: 'get'}).then(x => x.data.item.map(function(obj){
            return {key: obj.getDisplay, value: obj.name};
        }));
    }

    app.enum2 = function(name){
        var url = "/commons/enums?enumCls=" + name;
        return app.ajax(url, {method: 'get'}).then(x => x.data.item.map(function(obj){
            return {key: obj.displayName, value: obj.name};
        }));
    }

    app.dismissPopover = function(){
        setTimeout(_ => document.dispatchEvent(new MouseEvent("click", {clientX: 0, clientY: 0})), 200);
    }

    app.enum3 = function(name){
        var url = "/commons/enums?enumCls=" + name;
        return app.ajax(url, {method: 'get'}).then(x => x.data.item.map(function(obj){
            return {key: obj.getDisplay, value: obj.name};
        }));
    }

    app.ajaxTo = function(prefix, opt1){
        return function(url, opt){
            return app.ajax(prefix + "/" + url, Object.assign({}, opt1, opt));
        }
    };

    app.findCategoryPath = function(node, id){
        try{
            var dfs = function(node, arr){
                if(node.id == id){
                    throw arr.concat([node.id]);
                }else{
                    var newarr = arr.concat([node.id]);
                    if(node.children && node.children.length){
                        node.children.forEach(function(item){
                            dfs(item, newarr);
                        });
                    }
                }
            };
            node.forEach(node => dfs(node, []));
        }catch(ret){
            return ret;
        }
    },

    app.pluck = function(obj, args){
        var ret = {};
        args.forEach(function(key){
            ret[key] = obj[key];
        })
        return ret;
    };

    app.remove = function(obj, args){
        obj = Object.assign({}, obj);
        args.forEach(function(key){
            delete obj[key];
        })
        return obj;
    };

    app.normalizeURL = function(url){
        if(!url){
            return 'about:blank';
        }
        url = url.replace(/&amp;/g, "&");
        if(url.toString().match(/^https?:/))return url;
        return 'http://' + url.toString();
    }

    app.formatHtml = function (html) {
        if (html != null) {
            html = html.replace(/&amp;/g, "&");
            html = html.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;nbsp;/g, " ").replace(/&nbsp;/g, '');
        }
        return html;
    };

    app.clearField = function(obj){
        Object.keys(obj).forEach(function(key){
           if(({}).toString.call(obj[key]) !== '[object Object]') obj[key] = undefined;
           else app.clearField(obj[key]);
        });
        return obj;
    }
    app.ajaxFunc = function(f){
       return function(){
           var obj = f.apply(null, arguments);
           return app.ajax(obj.url, obj);
       }
    };

    app.ajax2 = function(opt){
        return app.ajax(opt.url, opt);
    }


    app.service  = function(){};

    app.requests = function(args){
        return Promise.all(args.map(function(x){
            return app.ajax(x[0]).then(x[1] || function(x){return x;});
        }));
    };

    app.getList = function(url, f, opt){
        return app.ajax(url, opt).then(x => x.data.item.map(f));
    };

    app.getTree = function(url){
        return app.ajax(url);
    }

    app.countryListFunc = function(){
        return app.getList('/product/country/findList', (x) => ({value: x.value, key: x.name})).then(x => app.countrylist = x);
    };

    app.firstCategoryListFunc = function(){
        return app.ajax("/findAllFirstLevel",  {
            //data: app.postform({'no': 0}),
            method: 'post',
        }).then(result => result.data.item || [])
        .then(list => list.map((x) => ({value: x.id, key: x.name, parentId: x.parentId, label: x.name, children: x.children})))
        .then(x => app.common.firstCategoryList = x);
    }

    // app.departmentZoneRelListFunc = function () {
    //     return app.ajax("/admin/department/zone",{
    //         method: 'post'
    //     }).then(result => {
    //        let data =  result.data.item || [];
    //        console.log(data,88)
    //     }).then(list => list.map((x) => ({value: x.id, key: x.name, parentId: x.parentId, label: x.name, children: x.children})))
    //         .then(x => app.common.firstCategoryList = x);
    // }

    app.firstCategoryListAll = function () {
        return app.ajax("/product/product/product/getWaitDivideCategory", {
            method: 'post',
        }).then(result => result.data.item || [])
            .then(list => list.map((x) => ({
                value: x.id,
                key: x.name,
                parentId: x.parentId,
                label: x.name,
                children: x.children
            })))
            .then(x => app.common.firstCategoryList = x);
    }


    app.firstSelfCategoryListFunc = function(){
        var page = app.controls.page, data = '';
        if(page == 'adList') data = app.postform({userType: 'advertis'});
        if(page == 'checkList') data = app.postform({userType: 'checker'});
        return app.ajax("/findSelfFirstLevel",  {
            //data: app.postform({'no': 0}),
            method: 'post',
            data: data,
            
        }).then(result => result.data.item || [])
        .then(list => list.map((x) => ({value: x.id, key: x.name, parentId: x.parentId, label: x.name, children: x.children})))
        .then(x => app.common.firstCategoryList = x);
    }
    app.util = {};
    app.util.pad = function(text, n, fill){
        var left = Array(n + 1).join(fill);
        return (left + text).slice(-n);
    }
    
    app.util.formatDate = function(date){
        var pad = app.util.pad;
        return [pad(date.getFullYear(), 4, 0),
                '-',
                pad(date.getMonth() + 1, 2, 0),
                '-',
                pad(date.getDate(), 2, 0),
                /*
                ' ',
                pad(date.getHours(), 2, 0),
                ':',
                pad(date.getMinutes(), 2, 0),
                ':',
                pad(date.getSeconds(), 2, 0),
                */
              ].join("");
    }
    

    app.departmentListFunc = function(){
        return app.ajax("/admin/department/list").then(x =>{
            app.common.departmentList = x.data.item.children || [];
            console.log(app.common.departmentList)
        } );
    };


    app.service['department/list'] = function(con){
        window.layoutData.controls.departmentLoading = true;
        return app.ajax('/admin/department/list').then(x => con.splice(0, 1, x.data.item.children)).then(_ => window.layoutData.controls.departmentLoading = false);
    };
    app.service['common/pagination/init'] = function(con, query){
        var fn = query;
        if(typeof query !== 'function'){
            fn = function(){
                return app.service[query](con);
            }
        }
        con.pageChange = function(val){
            if(!con.loading){
                con.loading = true;
                con.page = val;
                con.search.start = con.pageSize * (val - 1);
                con.search.limit = con.pageSize;
                fn();
            }
        };

        //搜索 功能
        con.searchClick = function(){

            //查找商品categoryid
            if (con.searchParams.categoryIds && con.searchParams.categoryIds.length) {
                con.searchParams.categoryId = con.searchParams.categoryIds.slice(-1)[0];
                delete con.searchParams.categoryIds;
            } else {
                // delete con.searchParams.categoryId;
            }

            /*
            // 查找部门id
            if (con.searchParams.id && con.searchParams.id.length) {
                con.searchParams.id = con.searchParams.id.slice(-1)[0];
            } else {
                // delete con.searchParams.categoryId;
            }
            */

            con.search = Object.assign({}, con.searchParams);
            if (con.search.checkerId && !con.search.checkerId.toString().match(/^\d+$/)) {
                con.search.checkerId = window.user_id;
            }
            con.search.start = 0;
            con.search.limit = con.pageSize;

            // if (con.searchParams.departmentId && con.searchParams.departmentId.length) {
            //     con.searchParams.departmentId = con.searchParams.departmentId.slice(-1)[0];
            //     // con.searchParams.departmentId = con.searchParams.departmentId[con.searchParams.departmentId.length-1];
            //     // delete con.searchParams.departmentId;
            // } else {
            //     // delete con.searchParams.categoryId;
            // }
            if (con.search.departmentIds && con.searchParams.departmentIds.length) {
                con.search.departmentId = con.searchParams.departmentIds[con.searchParams.departmentId.length-1];
                // delete con.searchParams.departmentId;
            } else {
                // delete con.searchParams.categoryId;
            }
            fn();
        }
        con.pagesizeChange = function(val){
                if(!con.loading){
                con.loading = true;
                con.search.start = 0;
                con.pageSize = val;
                fn();
            }
        }
    }

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

 

    app.service['product/init'] = function(con){
        if(!con.initList){
            con.initList = true;
            app.service['common/pagination/init'](con, 'product/list');
            var prefix = '/product/product/product';
            con.list = {
                search: app.ajaxFunc(function(obj){
                    return {
                        url: prefix + "/find",
                        method: 'post',
                        data: app.postform(app.pluck(obj, ['userId', 'categoryId', 'usable', 'departmentNo']))
                    }
                })
            }
        }
    };

    app.service['product/list'] = function(con){
        con.list.query().then(x => con.data = x.data.item);
    }

   
 


    app.service['beginCheck/init'] = function(con, query){
        if(!con.initList){
            con.initList = true;
            app.service['common/pagination/init'](con, query);
            var prefix = "/product/product/product";
            con.list = {
                query:  prefix + "/find",
                get:    prefix + "/get",
                delete: prefix + "/delete",
                add:    prefix + "/add",
                update: prefix + "/update",
            };
            con.refresh = function(){
                if(typeof query != 'function') query = app.service[query];
                query(con);
            }
            con.refresh();
        }
    };
    
 


    Object.keys(app.ajax).forEach(function(key){
        var s = app.ajax[key]
        if(s.delete){
            var f = s.delete
            s.delete = function(row){
                return app.$confirm("确实要删除" + formatNameId(row) + "吗?").then(_ => f(row))
            }
        }
    });




    app.util.formatRange = function(a, b, c, d){
        if(a[b] && a[b][0] && a[b][1]){
            var data = a[b];
            delete a[b];
            a[c] = app.util.formatDate(data[0]);
            var date = new Date(+data[1] + 86400e3);
            a[d] = app.util.formatDate(new Date(date.getFullYear(), date.getMonth(), date.getDate()));
            return true;
        }else{
            delete a[c];
            delete a[d];
        }
        if (b in a) delete a[b];
        return false;
    }

    app.flatten = function*(tree){
      if(({}).toString.call(tree) == "[object Array]"){
          for(let x of tree){
              yield *app.flatten(x);
          }          
          return;
      }
      if(tree){
           yield tree;
           if(tree.children && tree.children.forEach){
               for(let x of tree.children){
                   yield *app.flatten(x);
               }
           }
      }
    }

    /*
    app.service['beginCheck/list'] = function(con){
        let parameters = Object.assign({}, con.search, {limit: con.pageSize});
        if(parameters.ccategoryIds && parameters.ccategoryIds.length){
            parameters.categoryId = parameters.categoryIds.slice(-1)[0];
            delete parameters.categoryIds;
        }
        con.loading = true;
        app.ajax(con.list.query, {method: 'post', data: app.postform(parameters)}).then(x => {
            con.data = x.data.item;
            var total = con.total = x.data.total;
            con.loading = false;
        });        
    }*/

    
    /*
    if($(".container > .tablecontrol")){
        $(".container").css('padding', '0px');
    }
    */
  

});


window.showFSM = function(id, kind){
    app.controls.fsm.searchParams.objectId = id;
    app.controls.fsm.searchParams.fsmName  = kind;
    app.controls.fsm.searchClick();
    app.controls.fsm.dialog = true;
};
window.initFSM = function(){
    $(function(){
        app.service['common/pagination/init'](app.controls.fsm, _ => app.controls.fsm.get());
    });
};
initFSM(); 
$(function(){
    app.EventNames = {};
    app.StateNames = {};
    app.product_EventNames = {};
    app.product_StateNames = {};
    initAttr();
    initProductNew();
    app.enum("ProductNewEvent").then(x => {
        x.forEach(item => app.EventNames[item.value] = item.key);
    })
    app.enum("ProductNewState").then(x => {
        x.forEach(item => app.StateNames[item.value] = item.key);
    })

    app.enum("ProductEvent").then(x => {
        x.forEach(item => app.product_EventNames[item.value] = item.key);
    })
    app.enum("ProductState").then(x => {
        x.forEach(item => app.product_StateNames[item.value] = item.key);
    })

    $(document).on("submit", ".attrform", function(e){
        e.stopPropagation();
        e.preventDefault();
    });
  
    var fn = function(){ 
        if(!app){
            top.removeEventListener('resize', fn);
        }
        app.win.width  = top.innerWidth;
        app.win.height = top.innerHeight;
    };
    top.addEventListener('resize', fn);
    app.win.width  = top.innerWidth;
    app.win.height = top.innerHeight;
})