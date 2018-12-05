<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
<template scope='app' slot='controls'>
    <H1>产品详情</H1>
</template>
<template scope='app'>
    <el-row>
        <el-col :span='7' style='padding: 15px;'>
            <el-card v-for='item in window.app.controls.products'>
                <el-row style='font-size: 12px; line-height: 24px;'>
                    <el-col :span='24'>
                        <img :src='window.uploadPrefix + "/" + item.mainImageUrl' style='max-width: 100%;'>
                    </el-col>
                    <div style='height: 20px'>&nbsp;</div>
                    <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>基本信息</div>
                    <el-col :span='24'>
                        产品ID：{{item.id}}<br> SPU: {{item.spu}}<br>内部名: {{item.innerName}}<br>
                        <%--<control-item style='display: inline-block;' :app='app' :local='item'--%>
                        <%--datakey='innerName' type='edit'--%>
                        <%--:item-disabled='!app.controls.app.Perm.write'--%>
                        <%--@saved='app.controls.c.updateTitle(item, arguments[0])'></control-item>--%>
                        <br> 标题: {{item.title}} <br>
                        <!--标题语言包: <a href='javascript:void(0)' @click='app.con trols.edit.lang = !app.controls.edit.lang'>{{app.controls.edit.lang ? '收缩' : '展开'}}</a>-->
                        <el-collapse v-model="app.controls.edit.lang">
                            <el-collapse-item title="标题语言包" name="1">

                                                    <span v-show='app.controls.edit.lang' slot='title'>
                                                            标题语言包
                                                            <div style='margin: auto 0;'>
                                                                <el-button type='new' size='mini'
                                                                           @click='window.app.controls.c.doNewLang(); window.event.stopPropagation();'
                                                                           style='float: right; transform: translateY(-20px);'
                                                                           :disabled='!app.controls.app.Perm.write'>新增</el-button>
                                                            </div>
                                                    </span>
                                <el-row v-for='item in item.productLangList'>
                                    {{item.langName}}: {{item.name}}
                                    <i class='el-icon-close' style='cursor: pointer; transform: scale(0.6);'
                                       @click='window.app.controls.c.delete(item)'></i>
                                    <i class='el-icon-edit' style='cursor: pointer; transform: scale(0.6);'
                                       @click='window.app.controls.c.edit(item)'></i>
                                </el-row>
                                <span v-if='!item.productLangList || !item.productLangList.length'>暂无，点击新增按钮添加</span>

                            </el-collapse-item>
                        </el-collapse>

                        <br>品类: <span v-html="item.name || '暂无'"></span><br> 特性:
                        <el-tag v-if='item.classifyEnumName'>{{item.classifyEnumName}}</el-tag>
                        <el-tag v-if="item.customEnum!=null && item.customEnum!='normal'">
                            {{item.customEnumName}}
                        </el-tag>
                        <span v-if='!item.classifyEnumName'>无</span><br>
                        <span v-if='item.sourceUrl && item.purchaseUrl'>
                            来源: {{item.sourceEnumName}} <a :href='window.app.normalizeURL(item.sourceUrl)'
                                                           target="_blank">来源链接</a>
                            <%--<a :href='window.app.normalizeURL(item.purchaseUrl)'
                               target="_blank">采购链接</a>--%></span><br> 采购价: {{item.purchasePrice}}<br>
                        创建人: {{item.creator}}<br>
                        产品状态: {{item.stateName}}
                        <el-button type="primary" v-if="item.stateEnum=='waitFill'"
                                   :disabled='!app.controls.app.Perm.write'
                                   @click="window.app.controls.c.dialogVisible = true">提交
                        </el-button>

                        <el-dialog title="提示"
                                   :visible.sync="window.app.controls.c.dialogVisible"
                                   size="tiny">
                            <span>产品填充完成，确定要提交吗？</span>
                            <span slot="footer" class="dialog-footer">
                                            <el-button
                                                    @click="window.app.controls.c.dialogVisible = false">取 消</el-button>
                                                <el-button type="primary" @click="window.app.controls.c.submit(item)"
                                                           window.app.controls.c.load="false">确 定</el-button>
                                        </span>
                            <%--v-loading.fullscreen.lock="fullscreenLoading"--%>
                        </el-dialog>


                        <br>
                        总库存: {{item.totalStock}}<br>
                        备注: {{item.memo}}<br>
                    </el-col>
                </el-row>
                <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>产品区域</div>
                <el-row style='font-size: 12px; line-height: 24px;'>
                                <span v-for='r in item.productZoneList || []' style='white-space: nowrap;'>{{r.departmentName}}
                                    <span style='font-weight: bold; color: #cc1da6; font-size: 12px;'>{{r.creator}}</span>
                                    <span slot='label' style='font-weight: bold; font-size: 12px;'>{{r.zoneName}}</span>{{r.zoneState}}
                                库存:{{r.stock}} 状态:<span style='font-weight: bold; color: #1c2ccc; font-size: 12px;'>{{r.stateName}}</span> 最后下单: {{r.lastOrderAt || '暂无'}}<br></span>
                </el-row>
                <el-row>
                    属性描述: <span class="attr-desc"
                                v-html="window.app.formatHtml(item.attributeDesc)"></span>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span='6' style='padding: 15px;'>

            <el-card v-for='item in window.app.controls.products'>
                <el-button type='primary' @click='app.controls.dialog.sku = true;'>查看SKU
                </el-button>
                <div style='height: 15px;'>&nbsp;</div>
                <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>新增属性</div>
                <el-row>
                    <el-col :span='24' style='padding: 15px 0px;'>
                        <div @keyup.enter='app.controls.c.addAttr()'>
                            <control-item type='input' :local='app.controls.edit' label='属性名' datakey='attr'
                                          @icon-click='app.controls.c.addAttr()'
                                          :icon='!app.controls.app.Perm.attr_write? "" : "plus"'></control-item>
                        </div>
                    </el-col>
                </el-row>
                <div style='height: 20px'>&nbsp;</div>
                <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>已绑定属性</div>
                <el-row style='padding: 15px 5px; border: 2px dashed grey;'>
                    <div style='display: flex; flex-flow: row wrap;'>
                        <draggable v-model='item.attributeList'
                                   @start='drag=true;app.controls.c.startDrag("product.attr", arguments[0])'
                                   @end='drag=false;app.controls.c.endDrag("product.attr", arguments[0])'>
                            <el-button v-for='b in item.attributeList' class='a-p' :data-title='b.title' :data-id='b.id'
                                       size='mini' style='margin-bottom: 5px;'
                                       :disabled="!app.controls.app.Perm.attr_write"
                                       @click.native.stop.capture='app.controls.c.doClick($event, _ => app.controls.c.getAttrValue(b.id), _ => {window.alert(1);app.controls.c.doDrag("product.attr.product.attr", b.id)})'>
                                {{b.title}}
                            </el-button>
                        </draggable>
                    </div>
                </el-row>
                <div style='height: 20px'>&nbsp;</div>
                <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>未绑定属性</div>
                <el-row style='padding: 15px 5px;  border: 2px dashed grey;'>
                    <div style='display: flex; flex-flow: row wrap;'>
                        <draggable v-model='app.controls.cateAttrList'
                                   @start='drag=true;app.controls.c.startDrag("category.attr", arguments[0]);'
                                   @end='drag=false;app.controls.c.endDrag("category.attr", arguments[0])'>
                            <el-button v-for='b in app.controls.cateAttrList' :disabled="!app.controls.app.Perm.attr_write" :data-title='b.title' class='a-c'
                                       :data-id='b.id' size='mini' style='margin-bottom: 5px;'>
                                {{b.title}}
                            </el-button>
                        </draggable>
                    </div>
                </el-row>

            </el-card>
            <el-card v-for='item in [app.controls.attrValue]' v-if='app.controls.attrValue'>
                <H3><em>{{window.app.controls.attrTitle}}</em>的属性值列表</H3>
                <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>新增属性值</div>
                <el-row>
                    <el-col :span='24' style='padding: 15px 0px;'>
                        <div @keyup.enter='app.controls.c.addAttrValue()'>
                            <control-item type='input' :local='app.controls.edit' label='属性值名称'
                                          datakey='attrValue'
                                          @icon-click='app.controls.c.addAttrValue()'
                                          :icon='!app.controls.app.Perm.attr_write? "" : "plus"'></control-item>
                        </div>
                    </el-col>
                </el-row>
                <div style='height: 20px'>&nbsp;</div>
                <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>已绑定属性值</div>
                <el-row style='padding: 15px 5px; border: 2px dashed grey;'>
                    <div style='display: flex; flex-flow: row wrap;'>
                        <draggable v-model='item'
                                   @start='drag=true;app.controls.c.startDrag("product.attrValue", arguments[0])'
                                   @end='drag=false;app.controls.c.endDrag("product.attrValue", arguments[0])'>
                            <el-button v-for='b in item' :data-id='b.id' :data-title='b.title'
                                       :disabled="!app.controls.app.Perm.attr_write"
                                       class='v-p' size='mini' style='margin-bottom: 5px;' v-if='b.bindIs'>
                                {{b.title}}
                            </el-button>
                        </draggable>
                    </div>
                </el-row>
                <div style='height: 20px'>&nbsp;</div>
                <div style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>未绑定属性值</div>
                <el-row style='padding: 15px 5px;  border: 2px dashed grey;'>
                    <div style='display: flex; flex-flow: row wrap;'>
                        <draggable v-model='item'
                                   @start='drag=true;app.controls.c.startDrag("category.attrValue", arguments[0]);'
                                   @end='drag=false;app.controls.c.endDrag("category.attrValue", arguments[0])'>
                            <el-button v-for='b in item' :disabled="!app.controls.app.Perm.attr_write" :data-id='b.id' class='v-c' :data-title='b.title' size='mini'
                                       style='margin-bottom: 5px;' v-if='!b.bindIs'>
                                {{b.title}}
                            </el-button>
                        </draggable>
                    </div>
                </el-row>

            </el-card>
        </el-col>
        <el-col :span='6' style='padding:15px;'>
            <el-card v-if='app.controls.langPack.type'>
                <div v-if='app.controls.langPack.type == "attr"'
                     style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>属性:
                    {{app.controls.langPack.title}}的标题语言包
                </div>
                <div v-if='app.controls.langPack.type == "attrValue"'
                     style='background: #EEE; text-align: center; font-size: 14px; font-weight: bold;'>属性值:
                    {{app.controls.langPack.title}}的标题语言包
                </div>
                <div style='height: 15px;'>&nbsp;</div>
                <el-button type='new' icon='plus' @click='app.controls.langPack.newValue()'
                           :disabled='!app.controls.app.Perm.write'>新增
                </el-button>
                <div style='height: 15px;'>&nbsp;</div>
                <control-table :app='app' tablekey='langPack'>
                    <el-table-column label='操作'>
                        <template scope='inner'>
                            <el-button type='primary' plain size='mini'
                                       @click='app.controls.langPack.doEdit(inner.row)'
                                       :disabled='!app.controls.app.Perm.write'>编辑
                            </el-button>
                        </template>
                    </el-table-column>
                    <el-table-column prop='langName' label='语言'>
                        <template scope='inner'>
                            {{inner.row.langName}}({{inner.row.langCode}})
                        </template>
                    </el-table-column>
                    <el-table-column prop='name' label='名称'></el-table-column>
                </control-table>
            </el-card>
        </el-col>


        <dialog-create :visible='window.app.controls.dialog.lang'
                       @update:visible='window.app.controls.dialog.lang=arguments[0]'
                       @ok='app.controls.edit.langCallback'>
            <el-row style='margin: 5px;'>
                <control-item type='select' label='语言' :local='app.controls.lang' datakey='langCode'
                              :list='app.controls.langs'></control-item>
            </el-row>
            <el-row style='margin: 5px;'>
                <control-item type='input' label='标题' :local='app.controls.lang' datakey='name'></control-item>
            </el-row>
        </dialog-create>

        <el-dialog v-model='app.controls.dialog.sku' @open='app.controls.sku.get()'>

            <el-table :data='app.controls.sku.view' class='main-table' style='border-top: none;'
                      height='450' v-loading='app.controls.dialog.sku.loading'>
                <el-table-column prop='' label='属性值组合'>
                    <template scope='inner'>
                        {{inner.row.attributeValues.map(x => x.title).join(",")}}
                    </template>
                </el-table-column>
                <el-table-column prop='' label='SKU'>
                    <template scope='inner'>
                        {{inner.row.sku ? inner.row.sku :'未生成'}}
                        <el-button type='primary' size='mini' v-if='!inner.row.sku'
                                   :disabled="!app.controls.app.Perm.sku_write"
                                   @click="app.controls.sku.createSku(inner.row.attributeValues)">
                            生成SKU
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-pagination
                    :current_page.sync='app.controls.sku.page'
                    :page-size='app.controls.sku.limit'
                    :total='app.controls.sku.total'
                    @size-change='app.controls.sku.pagesizeChange(arguments[0])'
                    @current-change='app.controls.sku.pageChange(arguments[0])'
                    layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
        </el-dialog>
    </el-row>
</template>

</body>
<js>
    <style>
        .el-col {
            padding: 5px 15px;
        }
    </style>
    <script>
        var args = qs.parse(window.location.search, {ignoreQueryPrefix: true});
        window.layoutData.id = args.id;
        window.layoutData.controls = {
            sku: {
                data: [],
                view: [],
                page: 1,
                limit: 20,
                total: 0,
                makeView() {
                    this.view = this.data.slice(this.page * this.limit - this.limit, this.page * this.limit);
                },
                pageChange(val) {
                    this.page = val;
                    this.makeView();
                },
                pagesizeChange(val) {
                    this.limit = val;
                    this.makeView();
                },
                get () {
                    this.loading = true;
                    app.ajax.product.skulist({
                        productId: args.id,
                        start: this.page * this.limit - this.limit,
                        limit: this.limit
                    }).then(x => {
                            this.loading = false;
                            this.data = x.data.item;
                            this.total = this.data.length;
                            this.page = 1;
                            this.makeView();
                        },
                        _ =>
                            this.loading = false
                    )
                    ;
                },
                createSku(attrs) {
                    var arry = new Array();
                    if (attrs.length > 0) {
                        var obj = {
                            productId: args.id,
                        };
                        var attributeValueIds = new Array();
                        for (var i = 0; i < attrs.length; i++) {
                            if (attrs[i].id != null) {
                                attributeValueIds.push(attrs[i].id);
                            }
                        }
                        obj.attributeValueIds = attributeValueIds;
                        arry.push(obj);
                    }
                    console.log("生成sku的产品id:" + args.id + "attr:" + arry);
                    app.ajax("/product/product/product/productSkuGenerate", {
                        method: 'post',
                        params: ({attrInfo: JSON.stringify(arry)})
                    }).then(function () {
                        app.controls.dialog.sku = false;
                    }).catch(function () {

                    });
                }
            },
            attrId: 0,
            attrTitle: "",
            edit: {lang: [], langCallback: _ => _, attr: '', attrValue: ''
            },
            dialog: {
                lang: false, sku: false,
            }
            ,
            lang: {
                productId: args.id, langCode: "", name: ""
            }
            ,
            langs: [],
            cateAttrList: [],
            attrValue: undefined,
            langPack: {
                type: "",
                data: [],
                id: 0,
                title: '',
                get()
                {
                    if (this.type == 'attr') {
                        app.ajax.attrLang.find({
                            attributeId: this.id
                        }).then(x => this.data = x.data.item
                        )
                    } else if (this.type == 'attrValue') {
                        app.ajax.attrValueLang.find({
                            attributeValueId: this.id
                        }).then(x => this.data = x.data.item
                        )
                    }
                }
                ,
                newValue()
                {
                    app.clearField(app.controls.lang);
                    app.controls.dialog.lang = true;
                    app.controls.edit.langCallback = function () {
                        var a = _ => {
                                app.controls.dialog.lang = false;
                                this.get()
                            }
                            ,
                            b = _ => {
                                this.get()
                            }
                        ;
                        if (this.type == 'attr') {
                            app.ajax.attrLang.add(Object.assign({attributeId: this.id}, app.controls.lang)).then(a, b);
                        } else {
                            app.ajax.attrValueLang.add(Object.assign({attributeValueId: this.id}, app.controls.lang)).then(a, b);
                        }
                    }.bind(this);
                }
                ,
                doEdit(row)
                {
                    Object.assign(app.controls.lang, row)
                    app.controls.dialog.lang = true;
                    app.controls.edit.langCallback = function () {
                        var a = _ => {
                                app.controls.dialog.lang = false;
                                this.get()
                            }
                            ,
                            b = _ => {
                                this.get()
                            }
                        ;
                        if (this.type == 'attr') {
                            app.ajax.attrLang.update(Object.assign({attributeId: this.id}, app.controls.lang)).then(a, b);
                        } else {
                            app.ajax.attrValueLang.update(Object.assign({attributeValueId: this.id}, app.controls.lang)).then(a, b);
                        }
                    }.bind(this);
                }
            }
        }
        ;
        window.layoutData.controls.products = undefined;
        window.layoutData.controls.p = {
            get () {
                app.ajax.product.detail({id: app.id}).then(x => {
                    app.controls.products = [x.data.item];
//                            top.app.childInit(args.iframe, { title: '产品详情' });
                    top.app.childInit(args.iframe, {title: x.data.item.title});
                    let ids = {};
                    (x.data.item.attributeList || []).forEach(x => ids[x.id] = 1, x.toolShowing = false
                    )
                    ;
                    app.ajax.productAttr.find({categoryId: x.data.item.categoryId}).then(r => {
                        app.controls.cateAttrList = r.data.item.filter(x => !ids[x.id]
                        )
                        ;
                        app.controls.cateAttrList.forEach(x => x.toolShowing = false
                        )
                        ;
                    })
                    ;
                })
                ;
            }
        };
        window.layoutData.controls.c = {
            dialogVisible: false,
            load: false,
            get () {
                return app.ajax.productLang.find({productId: args.id}).then(r => {
                    app.controls.products[0].productLangList = r.data.item;
                })
                    ;
            },
            updateTitle(product) {
                app.ajax.product.updateInnerName({
                    id: product.id,
                    innerName: product.innerName
                }).then(_ => app.controls.p.get(), _ => app.controls.p.get());
            },
            addAttr() {
                if (app.controls.app.Perm.attr_write) {
                    app.ajax.productAttr.add({
                        productId: args.id,
                        categoryId: app.controls.products[0].categoryId,
                        title: app.controls.edit.attr
                    }).then(_ => app.controls.p.get()
                    )
                    ;
                }

            },
            doNewLang() {

                Object.assign(app.controls.lang, {
                    productId: args.id,
                    langCode: "",
                    name: ""
                });
                app.controls.dialog.lang = true;
                app.controls.edit.langCallback = function () {
                    app.ajax.productLang.add(app.controls.lang).then(_ => {
                        app.controls.c.get().then(_ => app.controls.dialog.lang = false
                        )
                        ;
                    })
                    ;
                }
            },
            delete(item) {
                app.ajax.productLang.delete(item).then(_ => app.controls.c.get()
                )
                ;
            },
            edit(item) {

                Object.assign(app.controls.lang, {
                    productId: args.id,
                    id: item.id,
                    langCode: item.langCode,
                    name: item.name
                });
                app.controls.dialog.lang = true;
                app.controls.edit.langCallback = function () {
                    app.ajax.productLang.update(app.controls.lang).then(_ => {
                        app.controls.c.get().then(_ => app.controls.dialog.lang = false
                        )
                        ;
                    })
                    ;
                };
            },
            doDrag(allkind, id, title) {
                if (!app.Perm.attr_write) {
                    return;
                }
                switch (allkind) {
                    case 'category.attr.category.attr': {
                        app.ajax.product.bind({
                            productId: args.id,
                            categoryId: app.controls.products[0].categoryId,
                            attributeId: id,
                            bindIs: true,
                        }).then(_ => app.controls.p.get(), _ =>
                            app.controls.p.get()
                        )
                        break;
                    }
                    case 'product.attr.product.attr': {
                        app.ajax.product.unbind({
                            productId: args.id,
                            categoryId: app.controls.products[0].categoryId,
                            attributeId: id,
                            bindIs: false,
                        }).then(_ => app.controls.p.get(), _ =>app.controls.p.get()
                        )
                        break;
                    }
                    case 'category.attrValue.category.attrValue': {
                        let a = _ =>
                            new Promise((ac, re) => ac(app.controls.p.get())
                            ).then(_ => app.controls.c.getAttrValue(app.controls.attrId, app.controls.attrTitle)
                            )
                        ;
                        app.ajax.product.bindValue({
                            productId: args.id,
                            categoryId: app.controls.products[0].categoryId,
                            attributeId: app.controls.attrId,
                            attributeValueId: id,
                            bindIs: true,
                        }).then(a, a);
                        break;
                    }
                    case 'product.attrValue.product.attrValue': {
                        let a = _ =>
                            new Promise((ac, re) => ac(app.controls.p.get())
                            ).then(_ => app.controls.c.getAttrValue(app.controls.attrId, app.controls.attrTitle)
                            )
                        ;
                        app.ajax.product.unbindValue({
                            productId: args.id,
                            categoryId: app.controls.products[0].categoryId,
                            attributeId: app.controls.attrId,
                            attributeValueId: id,
                            bindIs: false,
                        }).then(a, a);
                        break;
                    }
                }
            },
            endDrag(kind, e) {
                let id = app.controls.edit.dragItem;
                this.doDrag(app.controls.edit.dragFrom + "." + kind, id);
            },
            startDrag(kind, e) {
                let id = e.item.dataset.id;
                console.log(id);
                setTimeout(function () {
                    app.controls.edit.dragFrom = kind;
                    app.controls.edit.dragItem = id;
                }, 0);
            },
            getAttrValue(id, title) {
                app.controls.attrId = id;
                app.controls.attrTitle = title;
                app.ajax.product.getAttrValue({attributeId: id, productId: args.id}).then(result => {
                    app.controls.attrValue = result.data.item;
                })
                ;
            },
            addAttrValue() {
                if (app.controls.app.Perm.attr_write) {
                    let a = _ =>
                        new Promise((ac, re) => ac(app.controls.p.get())
                        ).then(_ => app.controls.c.getAttrValue(app.controls.attrId, app.controls.attrTitle)
                        )
                    ;
                    app.ajax.product.addAttrValue({
                        title: app.controls.edit.attrValue,
                        productId: args.id,
                        attributeId: app.controls.attrId
                    }).then(
                        a,
                        a);
                }

            },
            doClick(e, l, r) {
                console.log(e);
            },
            submit(product) {
                this.dialogVisible = false;
                this.load = true;
                console.log(22222)
                app.controls.app.ajax.product.processEvent({id: product.id}).then(x => app.controls.p.get()
                )
                ;
                setTimeout(() => {
                        this.load = false;
                        console.log(1111)
                    },
                    3000
                )
                ;
            }
        };
        $(function () {
            app.controls.app.ajax.lang.find().then(x => x.data.item
            ).
            then(x => x.map(x => ({key: x.name, value: x.langCode})
            )).
            then(x => app.controls.langs = x
            )
            ;
            app.controls.p.get();
            $(document).on('mouseup', '.a-p', function (e) {
                e.stopPropagation();
                e.preventDefault();
                var id = $(e.target).closest('.a-p')[0].getAttribute("data-id"),
                    title = $(e.target).closest('.a-p')[0].getAttribute("data-title");
                if (e.button == 2) {
                    app.controls.c.doDrag("product.attr.product.attr", id);

                } else if (e.button == 0) {
                    app.controls.c.getAttrValue(id, title);
                    app.controls.langPack.id = id;
                    app.controls.langPack.title = title;
                    app.controls.langPack.type = 'attr';
                    app.controls.langPack.get();
                }
            });
            $(document).on('mouseup', '.a-c', function (e) {
                e.stopPropagation();
                e.preventDefault();
                var id = $(e.target).closest('.a-c')[0].getAttribute("data-id"),
                    title = $(e.target).closest('.a-c')[0].getAttribute("data-title");
                if (e.button == 2) {
                    app.controls.c.doDrag("category.attr.category.attr", id);
                } else if (e.button == 0) {
                    //app.controls.c.getAttrValue(id);
                    app.controls.attrValue = undefined;
                    app.controls.langPack.id = id;
                    app.controls.langPack.title = title;
                    app.controls.langPack.type = 'attr';
                    app.controls.langPack.get();
                }
            });
            $(document).on('mouseup', '.v-p', function (e) {
                e.stopPropagation();
                e.preventDefault();
                var id = $(e.target).closest('.v-p')[0].getAttribute("data-id"),
                    title = $(e.target).closest('.v-p')[0].getAttribute("data-title");
                if (e.button == 2) {
                    app.controls.c.doDrag("product.attrValue.product.attrValue", id);
                } else if (e.button == 0) {
                    app.controls.langPack.id = id;
                    app.controls.langPack.title = title;
                    app.controls.langPack.type = 'attrValue';
                    app.controls.langPack.get();
                }
            });
            $(document).on('mouseup', '.v-c', function (e) {
                e.stopPropagation();
                e.preventDefault();
                var id = $(e.target).closest('.v-c')[0].getAttribute("data-id"),
                    title = $(e.target).closest('.v-c')[0].getAttribute("data-title");

                if (e.button == 2) {
                    app.controls.c.doDrag("category.attrValue.category.attrValue", id);
                } else if (e.button == 0) {
                    app.controls.langPack.id = id;
                    app.controls.langPack.title = title;
                    app.controls.langPack.type = 'attrValue';
                    app.controls.langPack.get();
                }
            });
            $(document).on('contextmenu', '.a-p,.a-c,.v-p,.v-c', function (e) {
                e.stopPropagation();
                e.preventDefault();

            });
        });
    </script>
</js>
