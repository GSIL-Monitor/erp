<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<dialog-create :title='"属性描述"' :visible='app.controls.newtable.dialog.attributeDesc'
               @update:visible='val => window.app.controls.newtable.dialog.attributeDesc = val'
               @ok='app.controls.newtable.dialog.attributeDesc=false'
               :controls='window.app.controls' size="tiny">
    <control-item class="attr-desc" v-html="app.controls.attributeDescData.attribute_desc">
    </control-item>
</dialog-create>


<dialog-create :title='"细化分类"' :visible='app.controls.newtable.dialog.category' :controls='window.app.controls'
               @update:visible='val => window.app.controls.newtable.dialog.category = val'
               @ok='app.controls.newtable.savecate()'>
    <el-card>
        <el-row>
            <el-col style='padding: 5px 15px;'>
                <control-item :local='app.controls.newtable.cate' datakey='id' label='新品ID'
                              type='readonly'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col style='padding: 5px 15px;'>
                <control-item :local='app.controls.newtable.cate' datakey='title' label='名称'
                              type='readonly'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col style='padding: 5px 15px;'>
                <control-item :local='app.controls.newtable.cate' datakey='topCategoryName' label='参考品类'
                              type='readonly'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <%--<el-col style='padding: 5px 15px;'>--%>
            <%--<control-item :local='app.controls.newtable.cate'--%>
            <%--:list='app.controls.newtable.catelist'--%>
            <%--datakey='categoryIds' label='设置分类' type='selectcascade'--%>
            <%--select-leaf--%>
            <%--></control-item>--%>
            <%--</el-col>--%>
            <el-col style='padding: 5px 15px;'>
                <control-item type='selectAsync'
                              :complete-array-url-func="url => window.app.ajax('/leafSearch?name=' + url).then(result => result.data.item)"
                              :complete-props = "{value: 'id', label: 'name'}"
                              :local='app.controls.newtable.cate'
                              datakey='categoryId'
                              label='设置分类'>
                     </control-item>
            </el-col>
            <%--<el-col style='padding: 5px 15px;'>--%>
            <%--<control-item type='selectAsync' --%>
            <%--:complete-array-url-func="url => window.app.ajax('/leafSearch?no='+app.controls.newtable.cate.topCategoryNo+'&name=' + url).then(result => result.data.item)"--%>
            <%--:complete-props = "{value: 'id', label: 'name'}"--%>
            <%--:local='app.controls.newtable.cate' --%>
            <%--datakey='categoryId'--%>
            <%--label='设置分类'>--%>
            <%--</control-item>--%>
            <%--</el-col>--%>
        </el-row>
    </el-card>
</dialog-create>

<dialog-create :title='"新品添加"' :local='app.controls.newtable.add' :visible='app.controls.newtable.dialog.add'
               :controls='window.app.controls'
               @update:visible='val => window.app.controls.newtable.dialog.add = val'
               @ok='app.controls.newtable.doAdd()'>
    <template scope='app'>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <control-item type='input' :local='app.local' datakey='spu' label='spu'
                              @blur="app.controls.newtable.validateSpu(app.controls.newtable.add.spu)"></control-item>
            </el-col>
        </el-row>
        <el-row >

            <el-col :span='23' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <%--<control-item type='select'  :listfunc='app.controls.app.firstSelfCategoryListFunc' listkey='1' :local='app.local' datakey='topCategoryId'--%>
                <%--label='一级分类'>--%>
                <control-item :type='app.local.spu ? "readonly" : "select"'
                              :listfunc='app.controls.app.firstSelfCategoryListFunc' listkey='1' :local='app.local'
                              datakey='topCategoryId'
                              label='一级分类'>
                </control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <control-item :type='app.local.spu ? "readonly" : "input"' :local='app.local' datakey='title'
                              label='产品标题'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <control-item :type='app.local.spu ? "readonly" : "input"' :local='app.local' datakey='innerName'
                              label='内部名'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='ClassifyEnum' :local='app.local'
                              datakey='classifyEnum'
                              label='产品特性'></control-item>
            </el-col>
        </el-row>
        <el-row >
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='CustomEnum' :local='app.local'
                              datakey='customEnum'
                              label='自定义类别'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='GoalEnum' :local='app.local'
                              datakey='goalEnum'
                              label='上架目标' ref="goalEnum"></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='SourceEnum' :local='app.local'
                              datakey='sourceEnum'
                              label='来源'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:27px;left: -10px" >*</span>
                <control-item type='input' :local='app.local' datakey='sourceUrl' label='来源url'></control-item>
            </el-col>
        </el-row>
        <%--<el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <control-item type='input' :local='app.local' datakey='purchaseUrl' label='采购url' ></control-item>
            </el-col>
        </el-row>--%>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:50px;left: -10px" >*</span>
                <control-item type='upload' :local='app.local' datakey='mainImageUrl'
                              :url='"/commons/upload?type=productNew"' label='图片'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:39px;left: -10px" >*</span>
                <control-item type="textarea" :local="app.local" datakey="attributeDesc" label="属性描述" writeplaceholder="颜色:红色、白色、黑色&#10;尺码:L、XL、XXL "></control-item>
                <%--<label>属性描述</label>--%>
                <%--<el-button @click='window.open("/static/product_new_attribute_desc.xlsx")'--%>
                           <%--style="background: #1d90e6;color: #fff;">点击下载excel模板--%>
                <%--</el-button>--%>
            </el-col>
            <%--<el-col>--%>
                <%--<div contentEditable="true" class="excelDiv attr-desc" id="attr_desc" style="min-height: 60px;" placeholder="颜色"></div>--%>
            <%--</el-col>--%>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <control-item type='textarea' :local='app.local' datakey='memo' label='备注'></control-item>
            </el-col>
        </el-row>
    </template>
</dialog-create>

<dialog-create :title='"新品编辑"' :local='app.controls.newtable.edit' :visible='app.controls.newtable.dialog.edit'
               :controls='window.app.controls'
               @update:visible='val => window.app.controls.newtable.dialog.edit = val'
               @ok='app.controls.newtable.doEdit()'>
    <template scope='app'>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <%--<control-item type='select' :listfunc='app.controls.app.firstSelfCategoryListFunc' listkey='1' :local='app.local' datakey='topCategoryId' --%>
                <%--label='一级分类'></control-item>--%>
                <control-item :type='app.local.spu ? "readonly" : "select"'
                              :listfunc='app.controls.app.firstSelfCategoryListFunc' listkey='1' :local='app.local'
                              datakey='topCategoryId'
                              label='一级分类'>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <control-item type='input' :local='app.local' :type='app.local.spu ? "readonly" : "input"' datakey='title' label='产品标题'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <control-item type='input' :local='app.local' :type='app.local.spu ? "readonly" : "input"               ' datakey='innerName' label='内部名'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='ClassifyEnum' :local='app.local'
                              datakey='classifyEnum'
                              label='产品特性'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='CustomEnum' :local='app.local'
                              datakey='customEnum'
                              label='自定义类别'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='GoalEnum' :local='app.local'
                              datakey='goalEnum'
                              label='上架目标'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <control-item type='select' :listfunc='app.controls.app.enum3' listkey='SourceEnum' :local='app.local'
                              datakey='sourceEnum'
                              label='来源'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <control-item type='input' :local='app.local' datakey='spu' label='spu'
                              :item-disabled='app.controls.newtable.readonly'></control-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:29px;left: -10px" >*</span>
                <control-item type='input' :local='app.local' datakey='sourceUrl' label='来源url' ></control-item>
            </el-col>
        </el-row>
        <%--<el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <control-item type='input' :local='app.local' datakey='purchaseUrl' label='采购url'></control-item>
            </el-col>
        </el-row>--%>
        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:102px;left: -10px" >*</span>
                <control-item type='upload' :local='app.local' datakey='mainImageUrl'
                              :url='"/commons/upload?type=productNew"' label='图片'></control-item>
            </el-col>
        </el-row>

        <el-row>

            <el-col :span='24' style='padding: 5px 15px;'>
                <span style="color: red;position:relative;top:39px;left: -10px" >*</span>
                <control-item type="textarea" :local="app.local" datakey="attributeDesc" label="属性描述" writeplaceholder="颜色:红色、白色、黑色&#10;尺码:L、XL、XXL "></control-item>
                <%--<label>属性描述</label>--%>
                <%--<el-button @click='window.open("/static/product_new_attribute_desc.xlsx")'--%>
                           <%--style="background: #1d90e6;color: #fff;">点击下载excel模板--%>
                <%--</el-button>--%>
            </el-col>
            <%--<el-col>--%>
                <%--<div contentEditable="true" class="excelDiv attr-desc" id="attrDesc" style="min-height: 60px;"></div>--%>
            <%--</el-col>--%>
        </el-row>

        <el-row>
            <el-col :span='24' style='padding: 5px 15px;'>
                <control-item type='textarea' :local='app.local' datakey='memo' label='备注'></control-item>
            </el-col>
        </el-row>
    </template>
</dialog-create>


<dialog-create :visible='window.app.controls.newtable.dialog.region' v-if='window.app.controls.newtable.dialog.region'
               :controls='window.app.controls' @update:visible='val => window.app.controls.newtable.dialog.region = val'
               @ok='window.app.controls.newtable.dialog.region=false'
>
    <template scope='app'>
        <el-card>
            <el-row>
                <el-col :span='24' style='padding: 5px 15px;'>
                    <control-table class='main-table' :app='app' :local='app.local' tablekey='newregion' :height='150'
                                   :sub='true'>
                        <template scope='app' slot='controls'>
                            <el-row>
                                <el-col style='padding: 5px 15px;' :span='12'>
                                    <control-item type='select'
                                                  :listfunc='window.app.controls.newregion.getRegion'
                                                  :local='window.app.controls.newregion'
                                                  datakey='zoneId'
                                                  :props="{key: 'key', value: 'value'}"
                                                  @select-change='window.app.controls.newregion.countryName = arguments[0]'
                                                  label='来源'></control-item>
                                </el-col>
                                <el-col style='padding: 5px 15px;' :span='8'>
                                    <el-button icon='plus' type='primary'
                                               :actionkey='{data:
                                                        window.app.postform({productNewId: window.app.controls.newregion.productNewId,
                                                        zoneId:  window.app.controls.newregion.zoneId}),
                                                        method: "post",
                                                        url: "/pc/productNewZone/add"
                                                    }'
                                               :actionfunc='(opt) => !window.app.controls.newregion.zoneId ? $message("请填写区域") : window.app.ajax2(opt).then(_ => app.controls.newtable.regionedit(window.app.controls.newregion.productNewId))'>
                                        添加
                                    </el-button>
                                </el-col>
                            </el-row>
                            <el-row>

                            </el-row>
                        </template>
                        <template scope='app'>

                            <el-table-column label='操作'>
                                <template scope='inner'>
                                    <el-button icon='close' type='primary' size='small'
                                               :actionkey='{data: window.app.postform({id: inner.row.id}),
                                                method: "post",
                                                url: "/pc/productNewZone/delete"
                                            }'
                                               :actionfunc="(opt) => window.app.ajax2(opt).then(_ => app.controls.newtable.regionedit(window.app.controls.newregion.productNewId))"

                                    >删除
                                    </el-button>
                                </template>
                            </el-table-column>
                            <el-table-column label='新品id' prop='productNewId'></el-table-column>
                            <el-table-column label='区域编码' prop='zoneCode'></el-table-column>
                            <el-table-column label='区域名称' prop='zoneName'>

                            </el-table-column>
                        </template>
                    </control-table>
                </el-col>
            </el-row>
        </el-card>
    </template>
</dialog-create>





<%@ include file="/WEB-INF/views/partial/fsm_page.jsp" %>


