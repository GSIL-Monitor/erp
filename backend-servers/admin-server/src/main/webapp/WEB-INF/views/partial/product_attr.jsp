<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <el-row>
        <div v-if='app.controls.category && app.controls.category.node' style='padding: 20px; border-radius: 4px 4px 0px 0px; border: 1px solid #d1dbe5; border-bottom: none;'>
        <el-card>
                            <el-row>
                                <el-row>
                                    <control-item :app='app' :local='app.controls.category.node' datakey='name'
                                                  type='edit'
                                                  @saved='app.controls.category.update(app.controls.category.node, arguments[0])'
                                                  :item-disabled='!app.controls.app.Perm.attr_write'></control-item>
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
            </div>
        <el-tabs type="border-card" class='product_attr' v-model='app.controls.attrtable.attrPanel.tabName'
                 :style='`width:\${app.win?app.win.width-250:750}px`'>
            <el-tab-pane label='属性列表' name='attrList' v-loading='app.controls.attrtable.loading'>
                 
                <el-card style='border-bottom: none; box-shadow: 0 0 0 transparent; background: #F6F6F6; border-radius: 4px 4px 0px 0px; border-color:#dfe6ec;'>
                    <el-form class='attrform'>
                        <el-row>
                            <el-col :span='8'>
                                <el-form-item label='属性名称'>
                                    <el-row>
                                        <el-col :span='12'>
                                            <el-input v-model='app.controls.attrtable.searchParams.title'></el-input>
                                        </el-col>
                                    </el-row>
                                </el-form-item>
                            </el-col>
                            <el-col :span='6'>
                                <el-form-item label='是否绑定' v-if='app.controls.category'>
                                    <el-row>
                                        <el-col :span='12'>
                                            <el-select v-model='app.controls.attrtable.searchParams.bindIs'>
                                                <el-option :value='null' label='全部'></el-option>
                                                <el-option :value='true' label='已绑定'></el-option>
                                                <el-option :value='false' label='未绑定'></el-option>
                                            </el-select>
                                        </el-col>
                                    </el-row>
                                </el-form-item>
                            </el-col>
                            <el-col :span='3'>
                                <el-button type='primary' icon='search' @click='app.controls.attrtable.searchClick()'>
                                    搜索
                                </el-button>
                            </el-col>
                            <el-col :span='3'>
                                <el-button type='new' icon='plus' @click='app.controls.attrtable.addClick()'
                                           v-if='app.controls.attrtable.tag != "product"'
                                           :disabled='!app.controls.app.Perm.attr_write'>
                                    新增属性
                                </el-button>
                                <el-button type='new' icon='plus' @click='app.controls.attrtable.addClick2()'
                                           v-if='app.controls.attrtable.tag == "product"'
                                           :disabled='!app.controls.app.Perm.attr_write'>
                                        新增属性
                                    </el-button>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>


                <el-table :data='app.controls.attrtable.attrList' class='main-table' style='border-top: none;'
                          height='450'>
                    <el-table-column label='操作' width='200'>
                        <template scope='scope'>
                            <el-button type='danger' size='mini' @click='app.controls.attrtable.delClick(scope.row)'
                                       v-if='app.controls.attrtable.tag != "product"'
                                       :disabled='!app.controls.app.Perm.attr_write'>删除
                            </el-button>
                            <el-button type='danger' size='mini' @click='app.controls.attrtable.delClick2(scope.row)'
                                       v-if='app.controls.attrtable.tag == "product"'
                                       :disabled='!app.controls.app.Perm.attr_write'>删除
                            </el-button>
                            <el-button type='success' size='mini' @click='app.controls.attrtable.bindProductAttr(scope.row, true)' v-if='app.controls.attrtable.tag == "product" && !scope.row.bindIs'>绑定</el-button>
                            <el-button type='success' size='mini' @click='app.controls.attrtable.bindProductAttr(scope.row, false)' v-if='app.controls.attrtable.tag == "product" && scope.row.bindIs'>解绑</el-button>
                            <!--
                            <el-button v-if='app.controls.category && app.controls.category.nodeid' :type='scope.row.bindIs ? "success" : "info"' size='mini' @click='app.controls.attrtable.doBind(scope.row, !!(scope.row.bindIs))'>
                               <span v-if='scope.row.bindIs'>
                                   解绑<i class='el-icon-circle-close' style='margin-left: 5px;'></i>
                                </span>
                                <span v-if='!scope.row.bindIs'>
                                   绑定<i class='el-icon-circle-check' style='margin-left: 5px;'></i>
                                </span> 
                            </el-button>
                            -->
                            <el-button type='primary' size='mini' @click='app.controls.attrtable.editProperty(scope.row)'>属性值<i class='el-icon-caret-right'></i></el-button>
                        </template>
                    </el-table-column>
                    <el-table-column prop='id' label='ID' width='70'></el-table-column>
                    <el-table-column prop='title' label='属性名称' width='140'>
                        <template scope='inner'>
                            <control-item type="edit" :local='inner.row' datakey='title' label='标题'
                                          @saved='app.controls.attrtable.updateTitle(inner.row, arguments[0])'
                                          :item-disabled='!app.controls.app.Perm.attr_write'></control-item>
                        </template scope='inner'>
                    </el-table-column>
                    <el-table-column prop='createAt' label='创建时间' width='90'>
                        <template scope='inner'>
                            <span class='breakspace'>{{inner.row.creator}}</span>
                            <span class='breakspace'>{{inner.row.createAt}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop='bindIs' label='绑定' width='120' v-if='app.controls.category && app.controls.category.node'>
                        <template scope='scope'>
                            <!--<el-switch v-model='scope.row.bindIs' on-text='已offshoffsale off-text='未绑定' width='70' class='small-text'></el-switch>-->
                            <!--{{scope.row.bindIs ? "已绑定" : "未绑定"}}-->
                            <el-checkbox v-model='scope.row.bindIs'
                                         @change='app.controls.attrtable.doBind(scope.row, !scope.row.bindIs)'
                                         style='font-size:12px;' :disabled='!app.controls.app.Perm.attr_write'>
                                {{scope.row.bindIs ? "已绑定" : "未绑定"}}
                            </el-checkbox>
                        </template>
                    </el-table-column>
                    <el-table-column label='属性名语言包' width='200'>
                        <template scope='scope'>
                            <!--
                            <div style='display:table-cell; width: 200px;'>
                                <el-table class='main-table' :data='scope.row.attrLangTable || []' style='margin: 5px; border-radius: 2px; max-width: 200px;'>
                                    <component is='el-table-column' v-for='(item,index) in scope.row.attributeLangs' :label='item.language_code2' :prop='item.language_code2' :key='item.language_code2'>
                                        <template scope='inner'>
                                            <el-input size='mini' :value='scope.row.attributeLangs[index].name' @input='scope.row.attributeLangs[index].name=arguments[0]'></el-input>
                                        </template>
                                    </component>
                                    <el-table-column>
                                        <template scope='inner'>
                                            <el-button type='primary' size='mini' icon='plus' @click='app.controls.addAttr(inner.row, scope.row)'></el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </div>
                            <div style='width: 100px; display: table-cell; vertical-align: bottom;'><el-button size='mini' type='primary' style='margin-bottom: 5px;'>新增</el-button></div>
                            -->
                        <el-row class='langs'>
                            
                            <el-popover trigger='click'  @show='app.controls.attrtable.addLangClick(scope.row)'>
                                <el-button slot='reference' icon='plus' type='new' size='mini'
                                           style='margin-bottom: 5px;' :disabled='!app.controls.app.Perm.attr_write'>新增
                                </el-button>
                                <el-card style='width: 500px; border-bottom: none; box-shadow: 0 0 0 transparent; background: #F6F6F6; border-radius: 4px 4px 0px 0px; border-color:#dfe6ec; width: 500px;'>
                                    <el-row style='margin: 5px;'><control-item type='select' label='语言' :local='app.controls.attrtable.add' datakey='langCode' :list='app.controls.attrtable.langs'></control-item></el-row>
                                    <el-row style='margin: 5px;'><control-item type='input' label='属性名称'  :local='app.controls.attrtable.add' datakey='name'></control-item></el-row>
                                    <el-row style='margin: 5px;'><el-button type='primary' size='mini' @click='app.controls.attrtable.doAddAttrLang(scope.row.id)'>确认</el-button> </el-row>
                                </el-card>
                            </el-popover>
                           
                            <div :span='18' class='lang-grid'>
                                <el-popover trigger='click' @show='app.controls.attrtable.editLangClick(scope.row, item)' v-for='item in scope.row.attributeLangs'>
                                    <el-card style='border-bottom: none; box-shadow: 0 0 0 transparent; background: #F6F6F6; border-radius: 4px 4px 0px 0px; border-color:#dfe6ec; width: 500px;'>
                                        <el-row style='margin: 5px;'><control-item type='select' label='语言' :local='app.controls.attrtable.edit' datakey='langCode' :list='app.controls.attrtable.langs'></control-item></el-row>
                                        <el-row style='margin: 5px;'><control-item type='input' label='属性名称'  :local='app.controls.attrtable.edit' datakey='name'></control-item></el-row>
                                        <el-row style='margin: 5px;'>
                                            <el-button type='primary' size='mini'
                                                       @click='app.controls.attrtable.doEditAttrLang(scope.row.id)'
                                                       :disabled='!app.controls.app.Perm.attr_write'>保存
                                            </el-button>
                                            <el-button type='danger' size='mini'
                                                       @click='app.controls.attrtable.doDeleteAttrLang(scope.row)'
                                                       :disabled='!app.controls.app.Perm.attr_write'>删除
                                            </el-button>
                                        </el-row>    
                                    </el-card>
                                    <el-tag style='cursor: pointer;' slot='reference' size=mini >{{item.langName}}: {{item.name}}</el-button>
                                </el-popover>
                            </div>
                        </el-row>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination class='end'  v-if='app.controls.attrtable.tag != "product"' @size-change="app.controls.attrtable.pagesizeChange(arguments[0])" @current-change="app.controls.attrtable.pageChange(arguments[0])"
                    :current-page.sync="app.controls.attrtable.page" :page-size="app.controls.attrtable.pageSize" layout="total, sizes, prev, pager, next, jumper"
                    :total="app.controls.attrtable.total">
                </el-pagination>
            </el-tab-pane>
            <el-tab-pane :label='app.controls.attrtable.attrName ? "[" + app.controls.attrtable.attrName + "]属性值列表" : "属性值列表"' name='attrValueList'
                v-if='window.app.controls.attrValueTable.search.attributeId'>
                <el-card style='border-bottom: none; box-shadow: 0 0 0 transparent; background: #F6F6F6; border-radius: 4px 4px 0px 0px; border-color:#dfe6ec;'>
                    <el-form>
                        <el-row>
                            <el-col :span='12'>

                            </el-col>
                            <el-col :span='6'>

                            </el-col>
                            <el-col :span='3'>

                            </el-col>
                            <el-col :span='3'>
                                <el-button type='primary' icon='plus' @click='app.controls.attrValueTable.doAdd()'
                                           :disabled='!app.controls.app.Perm.attr_write'>
                                    新增属性值
                                </el-button>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
                <el-table :data='app.controls.attrValueTable.data' class='main-table' style='border-top: none;' height='450'>
                    <el-table-column label='操作' width='100'>
                        <template scope='inner'>
                            <el-button type='danger' size='mini'
                                       @click='app.controls.attrValueTable.delClick(inner.row)'
                                       :disabled='!app.controls.app.Perm.attr_write'>删除
                            </el-button>
                        </template>
                    </el-table-column>
                    <el-table-column prop='id' label='属性值id' width='90'></el-table-column>
                    <el-table-column prop='title' label='属性值名称' width='150'>
                        <template scope='inner'>
                            <control-item type="edit" :local='inner.row' datakey='title' label='标题'
                                          @saved='app.controls.attrValueTable.updateTitle(inner.row, arguments[0])'
                                          :item-disabled='!app.controls.app.Perm.attr_write'></control-item>
                        </template>
                    </el-table-column>
                    <el-table-column prop='createAt' label='创建时间' width='100'>
                        <template scope='inner'>
                            <span class='breakspace'>{{inner.row.createAt}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label='属性值语言包' width='340'>

                        <template scope='scope'>
                            <!--
                            <el-button size='mini' type='primary'>新增</el-button>
                            <el-table class='main-table' :data='scope.row.attrLangTable || []' style='margin: 5px; border-radius: 2px;'>
                                <component is='el-table-column' v-for='item in scope.row.attributeLangs' :label='item.language_code2' :prop='item.language_code2' :key='item.language_code2'>
                                    
                                </component>
                            </el-table>
                            -->
                      <el-row class='langs'>                          
                          
                            <el-popover style='width: 500px;' trigger='click' @show='app.controls.attrtable.addValueLangClick(scope.row)'>
                                <el-button slot='reference' icon='plus' type='new' size='mini'
                                           style='margin-bottom: 5px;' :disabled='!app.controls.app.Perm.attr_write'>新增
                                </el-button>
                                <el-card style='border-bottom: none; box-shadow: 0 0 0 transparent; background: #F6F6F6; border-radius: 4px 4px 0px 0px; border-color:#dfe6ec; width: 500px;'>
                                    <el-row style='margin: 5px;'><control-item type='select' label='语言' :local='app.controls.attrtable.add' datakey='langCode' :list='app.controls.attrtable.langs'></control-item></el-row>
                                    <el-row style='margin: 5px;'><control-item type='input' label='属性名称'  :local='app.controls.attrtable.add' datakey='name'></control-item></el-row>
                                    <el-row style='margin: 5px;'><el-button type='primary' size='mini' @click='app.controls.attrtable.doAddAttrValueLang(scope.row.id)'>确认</el-button> </el-row>
                                </el-card>

                            </el-popover>
                          <br>
                            <div :span='18' class='lang-grid'>
                                <el-popover trigger='click' @show='app.controls.attrtable.editValueLangClick(scope.row, item)' v-for='item in scope.row.attributeValueLangs'>
                                    <el-card style='border-bottom: none; box-shadow: 0 0 0 transparent; background: #F6F6F6; border-radius: 4px 4px 0px 0px; border-color:#dfe6ec; width: 500px;'>
                                        <el-row style='margin: 5px;'><control-item type='select' label='语言' :local='app.controls.attrtable.edit' datakey='langCode' :list='app.controls.attrtable.langs'></control-item></el-row>
                                        <el-row style='margin: 5px;'><control-item type='input' label='属性名称'  :local='app.controls.attrtable.edit' datakey='name'></control-item></el-row>
                                        <el-row style='margin: 5px;'>
                                            <el-button type='primary' size='mini' @click='app.controls.attrtable.doEditAttrValueLang(scope.row.id)'>保存</el-button>
                                            <el-button type='danger' size='mini' @click='app.controls.attrtable.doDeleteAttrValueLang(scope.row)'>删除</el-button>
                                        </el-row>    
                                    </el-card>
                                    <el-tag style='cursor: pointer;' slot='reference' size=mini >{{item.langName}}: {{item.name}}</el-button>
                                </el-popover>
                            </div>
                        </el-row>
                        </template>


                    </el-table-column>
                </el-table>
                <el-pagination class='end' @size-change="app.controls.attrValueTable.pagesizeChange(arguments[0])" @current-change="app.controls.attrValueTable.pageChange(arguments[0])"
                    :current-page.sync="app.controls.attrValueTable.page" :page-size="app.controls.attrValueTable.pageSize" layout="total, sizes, prev, pager, next, jumper"
                    :total="app.controls.attrValueTable.total">
                </el-pagination>
            </el-tab-pane>
        </el-tabs>
    </el-row>