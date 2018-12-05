<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<body>
<template slot='controls' scope='app'>
    <H1>订单列表</H1>
</template>

<template scope='app'>

    <control-table tablekey='table' :app='app' style='width: 100%;' :loading='app.controls.table.loading'>
        <template scope='app' slot='controls'>
            <%--<el-row>--%>
            <%--<el-col :span='8'>--%>
            <%--<control-item type='input' label='名称' :app='app.controls.app'--%>
            <%--:local='app.controls.table.searchParams' datakey='name'></control-item>--%>
            <%--</el-col>--%>
            <%--<el-col :span='8'>--%>
            <%--<el-button @click='app.controls.table.doSearch()' type='primary' icon='search'>搜索</el-button>--%>
            <%--</el-col>--%>

            <%--</el-row>--%>
            <%--<el-row>--%>
            <%--<el-col :span='8'>--%>
            <%--<el-button--%>
            <%--@click='app.controls.app.clearField(app.controls.table.add); app.controls.table.dialog.add=true'--%>
            <%--type='new' icon='plus' :disabled='!app.controls.app.Perm.write'>新增--%>
            <%--</el-button>--%>
            <%--</el-col>--%>
            <%--</el-row>--%>
            <el-row>
                <el-col style="padding: 0 20px 20px 0; width: 200px;">
                    <control-item type='input' label='关键词' :app='app.controls.app'
                                  :local='app.controls.table.searchParams' datakey='keyword'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 200px;">
                    <control-item type='input' label='订单号' :app='app.controls.app'
                                  :local='app.controls.table.searchParams' datakey='order_id'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 200px;">
                    <control-item type='input' label='运单号' :app='app.controls.app'
                                  :local='app.controls.table.searchParams' datakey='send_id'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='select' label='手机验证码' :list="app.controls.table.operatEnum.telCodeState"
                                  :app='app.controls.app'
                                  :local='app.controls.table.operatEnum.telCodeState'
                                  datakey='mobile_verify'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='select' label='支付方式' :list="app.controls.table.operatEnum.payMethodEnum"
                                  :app='app.controls.app'
                                  :local='app.controls.table.operatEnum.payMethodEnum'
                                  datakey='pay_method'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='select' label='状态' :list="app.controls.table.operatEnum.orderStateEnum"
                                  :app='app.controls.app'
                                  :local='app.controls.table.operatEnum.orderStateEnum' datakey='status'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='select' label='地区' :list="app.controls.table.countryList"
                                  :app='app.controls.app'
                                  :local='app.controls.table.searchParams' datakey='area'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='selectcascade'
                                  :treetolist='true'
                                  :treelistprops='{key: "name", value: "id", children: "children"}'
                                  :treeprops='{label: "name", children: "children", value: "id"}'
                                  :tree='window.app.common.departmentList'
                                  :local='app.controls.table.searchParams'
                                  datakey='departmentId'
                                  label='部门'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 370px;">
                    <%--<control-item type='select' label='展示时间' :app='app.controls.app'--%>
                    <%--:local='app.controls.table.searchParams' datakey='show_time'></control-item>--%>
                    <el-date-picker v-model="app.controls.table.show_time" type="datetimerange" range-separator="-"
                                    placeholder="选择日期" time-arrow-control="true" editable="false"
                                    @change="app.controls.table.getDate(app.controls.table.show_time)">
                    </el-date-picker>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='select' label='更多条件' :app='app.controls.app'
                                  :local='app.controls.table.searchParams' datakey='more_condition'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='input' label='输入条件' :app='app.controls.app'
                                  :local='app.controls.table.searchParams' datakey='input_condition'></control-item>
                </el-col>
                <el-col style="width: 70px;padding: 0;margin: 0;">
                    <el-button type="primary">搜索</el-button>
                </el-col>
                <el-col style="width: 70px;padding: 0;margin: 0;">
                    <el-button type="success">导出</el-button>
                </el-col>
                <el-col style="width: 70px;padding: 0;margin: 0;">
                    <el-button type="warning" @click="app.controls.table.dialog.watch_detail = true">重置</el-button>
                </el-col>
            </el-row>
            <el-row style="display: flex;align-items:center;justify-content:flex-start">
                <el-col :span='9'>
                    说明：字体红色，重复订单、背景红色，黑名单 | 待审数量：890 | 今天： 41 | 今天前：<span
                        style="color: red">849</span>
                </el-col>
                <el-col :span='9'>
                    <control-item type='select' label='选择订单类型' :app='app.controls.app'
                                  :list="app.controls.table.operatEnum.operationEnum"
                                  :local='app.controls.table.operatEnum.operationEnum'
                                  datakey='choose_condition'></control-item>
                </el-col>
                <el-col :span="1">
                    <el-button type="primary">搜索</el-button>
                </el-col>
            </el-row>
        </template>

        <%--<el-table-column type='selection' width='50'></el-table-column>--%>

        <%--<el-table-column label='操作'>--%>
        <%--<template scope='inner'>--%>
        <%--<el-button type='danger' size='mini' @click='app.controls.table.doDelete(inner.row)'--%>
        <%--:disabled='!app.controls.app.Perm.write'>删除--%>
        <%--</el-button>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column prop='name' label='名称'  width='150'>--%>
        <%--<template scope='inner'>--%>
        <%--<control-item :app='app' :local='inner.row' datakey='name' type='edit'--%>
        <%--@saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()'--%>
        <%--:item-disabled='!app.controls.app.Perm.write'></control-item>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column prop='langCode' label='语言编码'  width='150'>--%>
        <%--<template scope='inner'>--%>
        <%--<control-item :app='app' :local='inner.row' datakey='langCode' type='edit'--%>
        <%--@saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()'--%>
        <%--:item-disabled='!app.controls.app.Perm.write'></control-item>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column prop='sort' label='排序'>--%>
        <%--<template scope='inner'>--%>
        <%--<control-item :app='app' :local='inner.row' datakey='sort' type='edit'--%>
        <%--@saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()'--%>
        <%--:item-disabled='!app.controls.app.Perm.write'></control-item>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <!-------------- -->

        <%--<el-table-column label="订单号" width='150'>--%>
        <%--<template scope="inner">--%>
        <%--<el-row>--%>
        <%--<el-col style="padding: 0;width: 100%;">--%>
        <%--<p style="margin: 0;padding: 0;">订单号:{{inner.row.id}}</p>--%>
        <%--<p style="margin: 0;padding: 0;">国家: 中国</p>--%>
        <%--<p style="margin: 0;padding: 0;">ip:<span style="color: blue;">{{inner.row.ip}}</span></p>--%>
        <%--<p style="margin: 0;padding: 0;">当天购买: {{inner.row.dayPurchaseCount}}</p>--%>
        <%--<!-- 1重复订单 2黑名单 -->--%>
        <%--<p v-if="inner.row.orderWarningType == 2" style="margin: 0;padding: 0;color: red;">--%>
        <%--{{inner.row.email}}</p>--%>
        <%--<p v-if="inner.row.orderWarningType == 1" style="margin: 0;padding: 0;color: red;">--%>
        <%--{{inner.row.telphone}} | SKU:465</p>--%>
        <%--</el-col>--%>
        <%--</el-row>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column label="域名" width='150'>--%>
        <%--<template scope="inner">--%>
        <%--<el-row>--%>
        <%--<el-col style="padding: 0;width: 100%;">--%>
        <%--<p style="margin: 0;padding: 0;">香港</p>--%>
        <%--<p style="margin: 0;padding: 0;">{{inner.row.domain}}</p>--%>
        <%--<p style="margin: 0;padding: 0;">陈小静</p>--%>
        <%--</el-col>--%>
        <%--</el-row>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column label="订单状态" width='100' prop="orderStatusEnum">--%>
        <%--<!--111-->--%>
        <%--&lt;%&ndash;<template scope='inner'>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<control-item :app='app' :local='inner.row' datakey='sort' type='edit'&ndash;%&gt;--%>
        <%--&lt;%&ndash;@saved='app.controls.table.doUpdate(inner.row)' @discard='app.controls.table.refresh()'&ndash;%&gt;--%>
        <%--&lt;%&ndash;:item-disabled='app.controls.app.Perm.write'></control-item>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</template>&ndash;%&gt;--%>
        <%--<!--111-->--%>
        <%--</el-table-column>--%>
        <%--<el-table-column label="支付方式" width='100' prop="paymentMethod">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="姓名邮箱" width='150'>--%>
        <%--<template scope="inner">--%>
        <%--<el-row>--%>
        <%--<el-col style="padding: 0;width: 100%;">--%>
        <%--<p style="margin: 0;padding: 0;">姓名</p>--%>
        <%--<p style="margin: 0;padding: 0;">{{inner.row.email}}</p>--%>
        <%--</el-col>--%>
        <%--</el-row>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column label="电话" width='150' prop="telphone">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="验证码" width='150' prop="getCode">--%>
        <%--<template scope="inner">--%>
        <%--<el-row>--%>
        <%--<el-col style="padding: 0;width: 100%;">--%>
        <%--<p style="margin: 0;padding: 0;color: red;" v-if="inner.row.getCode != inner.row.inputCode">--%>
        <%--验证失败</p>--%>
        <%--<p style="margin: 0;padding: 0;">原：{{inner.row.getCode}}</p>--%>
        <%--<p style="margin: 0;padding: 0;">验：{{inner.row.inputCode}}</p>--%>
        <%--</el-col>--%>
        <%--</el-row>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column label="总价" width='100' prop="orderAmount">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="购买数量" width='100' prop="goodsQty">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="产品名" width='100' prop="title">--%>
        <%--<template scope="inner">--%>
        <%--<el-row>--%>
        <%--<el-col style="padding: 0;width: 100%;">--%>
        <%--<p style="margin: 0;padding: 0;color: blue;"></p>--%>
        <%--<p style="margin: 0;padding: 0;">{{inner.row.title}}</p>--%>
        <%--<p style="margin: 0;padding: 0;">{{inner.row.attrs}}</p>--%>
        <%--</el-col>--%>
        <%--</el-row>--%>
        <%--</template>--%>
        <%--</el-table-column>--%>
        <%--<el-table-column label="送货地址" width='100' prop="address">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="邮编" width='100' prop="zipcode">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="留言" width='100' prop="customerMeassage">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="备注" width='100' prop="memo">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label="展示时间" width='100' prop="showTime">--%>

        <%--</el-table-column>--%>
        <%--<el-table-column label='操作' width="200">--%>
        <%--<!-- 111-->--%>
        <%--&lt;%&ndash;<el-button type='danger' size='mini' @click='app.controls.table.doDelete(inner.row)'&ndash;%&gt;--%>
        <%--&lt;%&ndash;:disabled='!app.controls.app.Perm.write'>删除&ndash;%&gt;--%>
        <%--&lt;%&ndash;</el-button>&ndash;%&gt;--%>
        <%--<!-- 11 -->--%>
        <%--<template scope="inner">--%>
        <%--<control-item type='select-nolabel' :list="app.controls.table.operatEnum.operationEnum"--%>
        <%--:app='app.controls.app'--%>
        <%--:local='inner.row'--%>
        <%--datakey='display'></control-item>--%>
        <%--<control-item type='textarea-nolabel' :disabled="true" :local="inner.row"--%>
        <%--datakey="sort">--%>
        <%--</control-item>--%>
        <%--<div>--%>
        <%--<el-button type="primary" size="mini">主要按钮</el-button>--%>
        <%--<el-select style="width: 100px;" placeholder="更多" v-model="app.controls.table.moreData" placeholder="请选择" @change="app.controls.table.getData(app.controls.table.moreData)">--%>
        <%--<el-option--%>
        <%--v-for="item in app.controls.table.defaultMore"--%>
        <%--:key="item.value"--%>
        <%--:label="item.label"--%>
        <%--:value="item.value"--%>
        <%-->--%>
        <%--</el-option>--%>
        <%--</el-select>--%>
        <%--</div>--%>
        <%--</template>--%>
        <%--<!--- 11-->--%>
        <%--&lt;%&ndash;<el-button type='primary' size='mini' @click='app.controls.table.doUpdate(inner.row)'&ndash;%&gt;--%>
        <%--&lt;%&ndash;:disabled='!app.controls.app.Perm.write'>子类别&ndash;%&gt;--%>
        <%--&lt;%&ndash;</el-button>&ndash;%&gt;--%>
        <%--<!----111-->--%>
        <%--&lt;%&ndash;</el-table-column>&ndash;%&gt;--%>
    </control-table>
    <dialog-create
            :visible='app.controls.table.dialog.add'
            @update:visible='app.controls.table.dialog.add=arguments[0]'
            :title='"添加"'
            :app='app.controls.app'
            :controls='app.controls'
            @ok='app.controls.table.doAdd()'
    >
        <template scope='app'>
            <el-card>
                <el-row>
                    <el-col>
                        <control-item :app='app.controls.app' type='input' :local='app.controls.table.add'
                                      datakey='name' label='名称'></control-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <control-item :app='app.controls.app' type='input' :local='app.controls.table.add'
                                      datakey='langCode' label='语言编码'></control-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <control-item :app='app.controls.app' type='input' :local='app.controls.table.add'
                                      datakey='sort' label='排序'></control-item>
                    </el-col>
                </el-row>
            </el-card>
        </template>
    </dialog-create>
    <!--:before-close="app.controls.table.handleClose" -->
    <el-dialog title="提示" custom-class="el-dialog--large" :visible.sync="app.controls.table.dialog.watch_detail"
               width="30%">
        <span slot="title">订单详情</span>
        <template>
            <div style="display: flex;">
                <div :class="1==1?'a':'b'" style="padding: 10px;margin-right: 10px;background: #eee;display: flex;flex-direction: column;justify-content: center;align-items: center;">
                    <span>
                        无效
                    </span>
                    <span>
                        恶意下单
                    </span>
                </div>
                <div style="display: flex; flex-direction: column;justify-content: space-around;">
                    <div>
                        <span>订单465465465465465</span>
                        <span style="color: red;">$1008</span>
                    </div>
                    <div>
                        <span>3部1营2组（袁昭明）</span>
                        <span>张三</span>
                    </div>
                </div>
            </div>
            <div style="margin-top: 10px;">
                <el-tabs type="border-card">
                    <el-tab-pane>
                        <span slot="label">摘要</span>
                        <div>
                            <h3 style="margin: 0;">客户信息</h3>
                            <template>
                                <el-table
                                        :data="app.controls.table.tableData"
                                        style="width: 100%">
                                    <el-table-column
                                            prop="date"
                                            label="日期"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="name"
                                            label="姓名"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="address"
                                            label="地址">
                                    </el-table-column>
                                </el-table>
                            </template>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="库存">暂无数据</el-tab-pane>
                    <el-tab-pane label="物流">暂无数据</el-tab-pane>
                    <el-tab-pane label="退换货">暂无数据</el-tab-pane>
                    <el-tab-pane label="相关内部订单">暂无数据</el-tab-pane>
                </el-tabs>
            </div>
        </template>
        <span slot="footer" class="dialog-footer">
            <%--<el-button @click="dialogVisible = false">取 消</el-button>--%>
            <%--<el-button type="primary" @click="dialogVisible = false">确 定</el-button>--%>
        </span>
    </el-dialog>

</template>

</body>
<js>
    <style>
        .el-col {
            padding: 5px 15px;
        }
    </style>
    <script>
        // window.layoutData = {controls: {}};
        window.layoutData.common.departmentList = [];
        var c = window.layoutData.controls;
        c.table = {
            data: [],
            page: 1,
            pageSize: 20,
            total: 0,
            search: {
                start: 0,
            },
            searchParams: {
                title: "",
            },
            add: {
                title: "",
                langCode: "",
            },
            dep_add: {
                departmentNo: 0,
                departmentId: 0,
                zoneId: 0,
                id: 0
            },
            departmentList: [],
            moreData: '',
            countryList: [],
            show_time: [
                new Date((new Date()).getFullYear(), (new Date()).getMonth() - 1, 1, 0, 0),
                new Date((new Date()).getFullYear(), (new Date()).getMonth(), 1, 0, 0)
            ],
            getDate(x){
                console.log(x);
            },
            dialog: {
                add: false,
                history: false,
                watch_detail: false,
                edit_detail: false,
                cancel_order: false,
                return_order: false,
                change_order: false,
                again_order: false,
            },
            operatEnum: {
                operationEnum: [],
                orderStateEnum: [],
                otherConditionEnum: [],
                payMethodEnum: [],
                telCodeState: [],
            },
            xxxxx(x){
                console.log(x);
            },
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }],
            handleClose: function (d) {
                this.$confirm('确认关闭？').then(_ => {
                    d();
                }).catch(_ => {
                });

            },
            defaultMore: [{
                value: '1',
                label: '查看详情'
            }, {
                value: '2',
                label: '编辑订单'
            }, {
                value: '3',
                label: '取消订单'
            }, {
                value: '4',
                label: '退货申请'
            }, {
                value: '5',
                label: '换货申请'
            }, {
                value: '6',
                label: '补发'
            }, {
                value: '7',
                label: '撤回'
            }],
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                this.search.start = (this.page - 1) * this.pageSize;
//                app.ajax.order.find(this.search).then(x => {
//                    this.data = x.data.item;
//                    this.total = x.data.total;
//                    this.loading = false;
//                    for (var i = 0; i < x.data.item.length; i++) {
//                        var item = x.data.item;
//                    }
//                })
            },
            getEnumList(){
                app.ajax.ordercommon.enumList(this.search).then(x => {
                    function changeObject(arr, index) {
                        for (var i = 0; i < arr.length; i++) {
                            index.push({key: arr[i].getDisplay, value: arr[i].name})
                        }
                    }

                    changeObject(x.data.item.operationEnum, this.operatEnum.operationEnum);
                    changeObject(x.data.item.orderStateEnum, this.operatEnum.orderStateEnum);
                    changeObject(x.data.item.otherConditionEnum, this.operatEnum.otherConditionEnum);
                    changeObject(x.data.item.payMethodEnum, this.operatEnum.payMethodEnum);
                    changeObject(x.data.item.telCodeState, this.operatEnum.telCodeState);
                });
            },
            doAdd(){
                let re = /^[0-9]+$/;
                if (re.test(this.add.sort)) {
                    app.ajax.lang.add(this.add).then(_ => {
                        this.get();
                        this.dialog.add = false;
                    })
                } else {
                    app.$message({
                        message: '排序请输入正整数',
                        type: 'warning'
                    })
                }

            },
            doSearch(){
                Object.assign(this.search, this.searchParams);
                this.get();
            },
            doDelete(row){
                app.ajax.lang.delete(Object.assign({languageId: row.id}, row)).then(_ => this.get(), _ => this.get());
            },
            doUpdate(row){
                app.ajax.lang.update(row).then(_ => this.get(), _ => this.get());
            },
            getData(x){
                console.log(x);
            }
        };
        $(function () {
            app.service['common/pagination/init'](c.table, _ => c.table.get());
            c.table.get();
            c.table.getEnumList();
            app.departmentListFunc();
            window.app.ajax('/product/base/zone/find').then((msg) => {
                app.controls.table.countryList = msg.data.item.map(({title, id}) => ({value: id, key: title}));
            })
//            app.controls.app.Perm.write=true;
            setTimeout(function () {
                $("tr").each(function (i, e) {
                    if (i != 0) {
                        $(e).on('mousemove', function () {
                            $(this).addClass('row-hover');
                        }).on('mouseout', function () {
                            $(this).removeClass('row-hover');
                        });
                    }
                });
            }, 2000);
        })
    </script>
</js>