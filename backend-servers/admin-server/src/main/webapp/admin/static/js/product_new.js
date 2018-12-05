window.initProductNew = function(){
    app.service['productNewAd/init'] = function(con){
        if(!con.initList){
            con.initList = true;
            app.service['common/pagination/init'](con, 'productNewAd/list');
            
            var prefix = "/product/product/productNew";
            var prefixCountry = prefix + "Country";
            con.list = {
                adlist: prefix + "/" + con.url,
                get: prefix + "/get",
                update: prefix + "/update",
                delete: prefix + "/delete",
                country:{
                    query: app.ajaxFunc(function(){
                        return {
                            url: prefixCountry + "/findList",
                            method: 'post',
                        }
                    }),
                    delete: app.ajaxFunc(function(id){
                        return {
                            url: prefixCountry + "/delete",
                            data: app.postform({id: id}),
                            method: 'post',
                        }
                    }),
                    add: app.ajaxFunc(function(obj){
                        return {
                            url: prefixCountry + "/add",
                            data: app.postform(obj),
                            method: 'post',
                        }
                    }),
                },
            };
            
            con.seledit = function(row){
                var attrDesc = row.attributeDesc;
                if (attrDesc != null) {
                    attrDesc = attrDesc.replace(/&amp;/g, '&');
                    attrDesc = attrDesc.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&nbsp;/, '');
                }
                Object.assign(app.controls.newtable.edit, row);
                app.controls.newtable.edit.mainImageUrl = app.controls.newtable.edit.mainImageUrl;
                app.clearField(con.edit.validate);
                window.nativePub("validate");
                app.controls.newtable.dialog.edit=true;
                var f = setInterval(function () {
                    if ($("#attrDesc")[0]) {
                        clearInterval(f);
                        $("#attrDesc").html(attrDesc);
                    }
                }, 50);
            }
            con.doEdit = function(){
                var must = ['title', 'innerName', 'classifyEnum', 'sourceEnum', 'sourceUrl', 'mainImageUrl', 'topCategoryId', 'customEnum', 'goalEnum'];
                var ok = true;
                must.forEach(function(key){
                    if(con.edit[key] === '' || con.edit[key] === undefined){
                        ok = false;
                        con.edit.validate[key] = '必填';
                    }else{
                        con.edit.validate[key] = '';
                    }
                })
                window.nativePub('validate');
                if(!ok) {
                    app.$message("所有项目必填");
                    return;
                }

                var data = app.pluck(app.controls.newtable.edit, ['id', 'title', 'innerName', 'classifyEnum', 'sourceEnum', 'sourceUrl', 'topCategoryId', 'mainImageUrl', 'memo', 'state', 'customEnum', 'goalEnum', 'spu', 'attributeDesc']);
                app.ajax(con.list.update, {method: 'post', data: app.postform(data)}).then(function () {
                    app.controls.newtable.dialog.edit=false;
                    con.refresh();
                });
            }
            con.delete = function(row){ 
                app.$confirm("确实要删除" + app.formatNameId(row) + "吗?").then(function () {
                    app.ajax(con.list.delete, {method: 'post', data: app.postform(row)}).then(function(){
                        con.refresh()
                    });
                });
            }

            con.addClick = function(){
                con.dialog.add=true;
                app.clearField(con.add);
                $("#attr_desc").html('');
                window.nativePub("validate");
            }

            con.validateSpu = function (spu) {
                console.log("spu:" + spu);
                if (spu != null && $.trim(spu) != '') {
                    var param = {spu: spu};
                    app.ajax('/product/product/productNew/findProductNewBySpu', {
                        method: 'post',
                        params: (param)
                    }).then(result => {
                        if (result.data.item != null) {
                            var attrDesc = result.data.item.attributeDesc;
                            if (attrDesc != null) {
                                attrDesc = attrDesc.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;nbsp;/g, " ").replace(/&nbsp;/g, '');
                            }
                            delete result.data.item.productNewZones;
                            delete result.data.item.checkerId;
                            delete result.data.item.checker;
                            Object.assign(app.controls.newtable.add, result.data.item);
                        app.controls.newtable.add.innerName = result.data.item.innerName;
                            app.controls.newtable.add.topCategoryId = result.data.item.category.id;
                            $("#attr_desc").html(attrDesc);
                        } else {
                            app.$message({
                                message: 'spu不存在'
                            });
                        }
                    });
                }
            };

            con.doAdd = function(){
                //防止重复提交，当点击完确定按钮后，将按钮变成不可点击样式，动作完成之后恢复
                app.controls.app.clickShow.clickFlag = true;

                var must = ['title', 'innerName', 'classifyEnum', 'sourceEnum', 'sourceUrl', 'mainImageUrl', 'topCategoryId'];
                var ok = true;
                must.forEach(function(key){
                    if(con.add[key] === '' || con.add[key] === undefined){
                        ok = false;
                        con.add.validate[key] = '必填';
                    }else{
                        con.add.validate[key] = '';
                    }
                })
                window.nativePub('validate');
                if(!ok) {
                    app.$message("所有项目必填");
                    app.controls.app.clickShow.clickFlag = false;
                    return;
                }
                var ret = Object.assign({}, con.add);
                delete ret.validate;
                app.ajax("/product/product/productNew/add", {method: 'post', data: app.postform(ret)}).then(_ => {
                    con.dialog.add = false;
                    app.controls.app.clickShow.clickFlag = false;
                    app.service['productNewAd/list'](con);
                }, function () {
                    app.controls.app.clickShow.clickFlag = false;
                });
            }

            con.showAttributeDesc = function (attributeDesc) {
                attributeDesc = attributeDesc.replace(/&amp;/g, '&');
                attributeDesc = attributeDesc.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;nbsp;/g, " ").replace(/&nbsp;/g, '');
                app.controls.attributeDescData.attribute_desc = attributeDesc;
                con.dialog.attributeDesc = true;
            };
            con.selcate = function(id){
                app.ajax("/product/product/productNew/get", {method: 'get', params: ({id: id})}).then(data => {
                    con.cate = data.data.item;
                    
                    var opt = {url: '/tree', data: 'no=', method: 'post'};
                    app.controls.app.ajax(opt.url, opt).then(x => x.data.item.children).then(x => {
                        console.log(con.cate.topCategoryId);
                        var list;
                        if(con.cate.topCategoryId){
                            list = x.filter(y => y.id == con.cate.topCategoryId);
                        }else{
                            list = x;
                        }
                        con.catelist = list;
                        if(con.cate.categoryId) {
                            con.cate.categoryIds = app.findCategoryPath(con.catelist, con.cate.categoryId);
                            console.log(con.cate.categoryIds, con.cate.categoryId);
                        }
                        con.cate.categoryId = "";
                        con.dialog.category = true;
                    });
                                        
                });
            }

            con.savecate = function(id, categoryId){
                //防止重复提交，当点击完确定按钮后，将按钮变成不可点击样式，动作完成之后恢复
                app.controls.app.clickShow.clickFlag = true;
                let a = app.pluck(con.cate, ['id', 'categoryId']);
                if (a.categoryId) {
                    /*a.categoryId = a.categoryIds.slice(-1)[0];
                    delete a.categoryIds;*/
                    app.ajax("/product/product/productNew/updateCategory", {
                        method: 'post',
                        data: app.postform(a)
                    }).then(data => {
                        con.dialog.category = false;
                        app.controls.app.clickShow.clickFlag = false;
                        con.refresh();
                    }, function () {
                        app.controls.app.clickShow.clickFlag = false;
                    });
                } else {
                    app.$message({
                        message: '请设置分类',
                        type: 'error'
                    });
                    app.controls.app.clickShow.clickFlag = false;
                }

            }

            con.regionedit = function(id){
                app.ajax("/product/productNewZone/get", {method: 'get', params: ({productNewId: id})}).then(data => {
                    con.refresh();
                    app.controls.newregion.data = data.data.item;
                    app.controls.newregion.total = data.total;
                    app.controls.newregion.productNewId = id;
                    con.dialog.region = true;
                    
                });
            }

            app.controls.newregion.pagesizeChange = function(){
                
                var inner = app.controls.newregion;
                if(!inner.loading){
                    inner.loading = true;
                    inner.search.start = 0;
                    inner.pageSize = val;
                }
                con.regionedit(inner.productNewIdwaitCheck);
                
            }

            app.controls.newregion.getRegion = function(){
                return app.ajax.zone.findStintZone().then(x => x.data.item).then(x => x.map(y => ({
                    key: y.title,
                    value: y.id,
                })));
            }
        
            con.ACTION_HALF_TABLE = {
                "submit": "waitCheck",
                "checkOk": "checkOk",
                "requestApprove": "waitApprove",
                "duplicate": "duplication",
            };
            con.action = function(opt){
                app.$prompt("备注:").then(function(x){
                    if(x.action == 'confirm'){
                            var actionParameters = {
                                event: opt.type,
                                state: opt.row.stateEnum,
                                memo: x.value,
                                id: opt.id,
                                spu: opt.spu,
                                productZoneId: opt.productZoneId,
                            }
                            app.ajax("/product/product/productNew/processEvent", {method: 'post', data: app.postform(actionParameters)}).then(function(){
                                con.refresh();
                            }).catch(function(){
                                con.refresh();
                            });
                    }
                });
            };
            con.refresh = function(){
                app.service['productNewAd/list'](con);
            }

            app.service['common/pagination/init'](con, 'productNewAd/list');
            con.searchClick = function(){
                let con = app.controls.newtable;
                Object.assign(con.search, con.searchParams);
                if(con.searchParams.createAt[0] && con.searchParams.createAt[1]){
                    con.search.minCreateAt = app.util.formatDate(con.searchParams.createAt[0]);
                    var day = con.searchParams.createAt[1];
                    day = new Date(Date.parse(day) + 86400e3);
                    day = new Date(day.getFullYear(), day.getMonth(), day.getDate());
                    con.search.maxCreateAt = app.util.formatDate(day);
                }else{
                    delete con.search.minCreateAt;
                    delete con.search.maxCreateAt;
                }
                delete con.search.createAt;
                if(con.search.checkerId == undefined || !con.search.checkerId){
                    delete con.search.checkerId;
                }
                con.search.start = 0;
                con.refresh();
            }
            con.searchClick();
        }
    }
    
     app.service['productNewAd/list'] = function(con){ 
        console.log(con, con.search);
        let parameters = Object.assign({}, con.search, {limit: con.pageSize});
        con.loading = true;
        if(parameters.checkerId && !parameters.checkerId.toString().match(/^\d+$/)){
            parameters.checkerId = window.user_id;
        }
        if(parameters.creatorId && !parameters.creatorId.toString().match(/^\d+$/)){
            parameters.creatorId = window.user_id;
        }
        app.ajax(con.list.adlist, {method: 'post', data: app.postform(parameters)}).then(x => {
            con.data = x.data.item;
            var total = con.total = x.data.total;
            con.loading = false;
        });
    };
};