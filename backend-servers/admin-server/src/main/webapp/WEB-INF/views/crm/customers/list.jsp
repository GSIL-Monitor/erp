<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<body>
<template slot='controls' scope='app'>
    <H1>订单列表</H1>
</template>

<template scope='app'>

    <control-table tablekey='constomers' :app='app' style='width: 100%;' :loading='app.controls.constomers.loading'>
        <template scope='app' slot='controls'>
            <el-row style="display: flex;flex-wrap: wrap;">
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='select' label='客户等级' :app='app.controls.app'
                                  :list="app.controls.constomers.enumList.customerCreditEnum"
                                  :local='app.controls.constomers.searchParams' datakey='levelEnum'></control-item>
                </el-col>
                <%--<el-col style="padding: 0 20px 20px 0; width: 250px;">--%>
                    <%--<control-item type='select' label='地区' :list="app.controls.constomers.countryList"--%>
                                  <%--:app='app.controls.app'--%>
                                  <%--:local='app.controls.constomers.searchParams' datakey='zoneCode'></control-item>--%>
                <%--</el-col>--%>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='input' label='签收次数' :app='app.controls.app'
                                  :local='app.controls.constomers.searchParams' datakey='acceptQty'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 250px;">
                    <control-item type='input' label='拒收次数' :app='app.controls.app'
                                  :local='app.controls.constomers.searchParams' datakey='rejectQty'></control-item>
                </el-col>
                <%--<el-col style="padding: 0 20px 20px 0; width: 250px;">--%>
                    <%--<control-item type='select' label='验证码类型' :app='app.controls.app'--%>
                                  <%--:list="app.controls.constomers.enumList.codeTypeEnum"--%>
                                  <%--:local='app.controls.constomers.searchParams' datakey='codeType'></control-item>--%>
                <%--</el-col>--%>

                <el-col style="padding: 0 20px 20px 0; width: 200px;">
                    <control-item type='input' label='关键字' :app='app.controls.app'
                                  :local='app.controls.constomers.searchParams' datakey='keyWord'></control-item>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 400px;" id="setDate">
                    <el-date-picker v-model="app.controls.constomers.show_time" type="datetimerange"
                                    range-separator="-"
                                    placeholder="选择日期" format="yyyy-MM-dd" editable="true"
                                    clearable="false"
                                    @change="app.controls.constomers.getDate(app.controls.constomers.show_time)">
                    </el-date-picker>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 100px;">
                    <el-button type="primary" @click="app.controls.constomers.doSearch()">搜索</el-button>
                </el-col>
                <el-col style="padding: 0 20px 20px 0; width: 100px;">
                    <el-button type="primary"
                               @click="app.controls.app.clearField(app.controls.constomers.dialogData);app.controls.constomers.dialog.dialogVisible=true;app.controls.constomers.dialog.status=1">
                        添加
                    </el-button>
                </el-col>
            </el-row>
        </template>
        <el-table-column
                label="客户姓名"
                width="130">
            <template scope="data">
                {{data.row.firstName}}{{data.row.lastName}}
            </template>
        </el-table-column>
        <el-table-column
                prop="telphone"
                label="电话"
                width="120">
        </el-table-column>
        <el-table-column
                prop="email"
                label="邮箱">
        </el-table-column>
        <%--<el-table-column label="地区">--%>
            <%--<template scope="data">--%>
                <%--{{data.row.province}} {{data.row.city}} {{data.row.area}}--%>
            <%--</template>--%>
        <%--</el-table-column>--%>
        <el-table-column label="地址">
            <template scope="data">
                {{data.row.address}}
            </template>
        </el-table-column>
        <el-table-column
                prop="customerCreditEnum"
                label="客户等级">
        </el-table-column>
        <el-table-column
                prop="acceptQty"
                label="签收次数">
        </el-table-column>
        <el-table-column
                prop="rejectQty"
                label="拒签次数">
        </el-table-column>
        <%--<el-table-column--%>
                <%--prop="codeTypeEnum"--%>
                <%--label="验证码类型">--%>
        <%--</el-table-column>--%>
        </el-table-column>
        <el-table-column
                prop="state"
                label="状态">
            <template scope="data">
                {{data.row.state == 1 ? "已启用": "已停用"}}
            </template>
        </el-table-column>
        <el-table-column
                prop="createAt"
                label="客户添加时间">
        </el-table-column>
        <el-table-column
                prop="memo"
                label="备注">
        </el-table-column>
        <el-table-column
                label="操作">
            <template scope="data">
                <i class="el-icon-edit" style="color: #3399FF; cursor: pointer;"
                   @click="app.controls.constomers.showEditPanel(data.row.telphone)">编辑</i>
                <i class="el-icon-view" style="color: #993399; cursor: pointer;"
                   @click="app.controls.constomers.showDetail(data.row.id)">详情</i>
                <i class="el-icon-delete" style="color: #FF0000; cursor: pointer;"
                   @click="app.controls.constomers.enable(data.row.id)"> {{data.row.state == 1 ? "停用": "启用"}}</i>
            </template>
        </el-table-column>

    </control-table>

    <!-- :before-close="handleClose" -->
    <el-dialog title="新增/编辑/详情" :visible.sync="app.controls.constomers.dialog.dialogVisible" width="30%">
        <control-item type='input' label='客户姓' :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='firstName'></control-item>
        <control-item type='input' label='客户名' :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='lastName'></control-item>

        <control-item v-if="app.controls.constomers.dialog.status != 1" type='readonly' label='客户手机'
                      :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='telphone'></control-item>
        <control-item v-if="app.controls.constomers.dialog.status == 1" type='input' label='客户手机'
                      :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='telphone'></control-item>

        <control-item type='input' label='客户邮箱' :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='email'></control-item>

        <%--<control-item v-if="app.controls.constomers.dialog.status != 1" type='readonly' label='地区'--%>
                      <%--:app='app.controls.app' :local='app.controls.constomers.dialogData'--%>
                      <%--datakey='zoneCode'></control-item>--%>
        <control-item type='input' label='地址'
                      :list="app.controls.constomers.countryList" :app='app.controls.app'
                      :local='app.controls.constomers.dialogData'
                      datakey='address'></control-item>

        <control-item type='select' label='客户等级' :app='app.controls.app'
                      :list="app.controls.constomers.enumList.customerCreditEnum"
                      :local='app.controls.constomers.dialogData'
                      datakey='levelEnum'></control-item>

        <control-item v-if="app.controls.constomers.dialog.status != 1" type='readonly' label='拒收次数'
                      :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='rejectQty'></control-item>
        <control-item v-if="app.controls.constomers.dialog.status != 1" type='readonly' label='签收次数'
                      :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='acceptQty'></control-item>
        <%--<control-item type='select' label='验证码类型' :app='app.controls.app'--%>
                      <%--:list="app.controls.constomers.enumList.codeTypeEnum" :local='app.controls.constomers.dialogData'--%>
                      <%--datakey='codeType'></control-item>--%>
        <control-item type='select' label='状态' :app='app.controls.app'
                      :list="[{key: '启用', value: 1}, {key: '停用', value: 0}]" :local='app.controls.constomers.dialogData'
                      datakey='state'></control-item>
        <control-item v-if="app.controls.constomers.dialog.status != 1" type='readonly' label='客户添加时间'
                      :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='createAt'></control-item>
        <control-item type='input' label='备注' :app='app.controls.app' :local='app.controls.constomers.dialogData'
                      datakey='memo'></control-item>

        <span slot="footer" class="dialog-footer" v-if="app.controls.constomers.dialog.status">
            <el-button type="primary" @click="app.controls.constomers.doSave()">保 存</el-button>
            <el-button @click="app.controls.constomers.dialog.dialogVisible = false">取 消</el-button>
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
        c.constomers = {
            data: [],
            getcodetype: function (x) {
                console.log(x);
            },
            page: 1,
            pageSize: 20,
            total: 0,
            search: {
                start: 0,
            },
            searchParams: {
                title: "",
                constomeLevel: '',
                countryList: '',
                acceptQty: '',
                rejectQty: '',
                codeTypeVal: '',
                keyword: '',
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
            dialogData: [],
            showDetail(x){
                window.top.app.open('/crm/customers/detailsPage?id=' + x + "&iframe=" + x);
            },
            showEditPanel(x){
                Object.assign(this.search, this.searchParams, {telphone: x,});
                app.ajax.crm.find(this.search).then(x => {
                    this.dialogData = x.data.item[0];
                    this.total = x.data.total;
                    this.loading = false;
                    this.dialog.dialogVisible = true;
                    this.dialog.status = 2;
                })
            },
            departmentList: [],
            moreData: '',
            countryList: [],
            show_time: ['',''],
            getDate(x){
                function getNowFormatDate(xx) {
                    var Year = 0;
                    var Month = 0;
                    var Day = 0;
                    var CurrentDate = "";
                    Year = xx.getFullYear();//ie火狐下都可以
                    Month = xx.getMonth() + 1;
                    Day = xx.getDate();
                    CurrentDate += Year + "-";
                    if (Month >= 10) {
                        CurrentDate += Month + "-";
                    } else {
                        CurrentDate += "0" + Month + "-";
                    }
                    if (Day >= 10) {
                        CurrentDate += Day;
                    } else {
                        CurrentDate += "0" + Day;
                    }
                    return CurrentDate;
                }

                this.show_time[0] = getNowFormatDate(x[0]);
                this.show_time[1] = getNowFormatDate(x[1]);
                Object.assign(this.search, {minCreateAt: this.show_time[0], maxCreateAt: this.show_time[1]});
            },
            dialog: {
                add: false,
                history: false,
                dialogVisible: false,
                status: 0, // dialog 出现的状态 0 查看详情 1 添加 2 编辑
            },
            enumList: {
                customerCreditEnum: [],
                codeTypeEnum: []
            },
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                this.search.start = (this.page - 1) * this.pageSize;
                app.ajax.crm.find(this.search).then(x => {
                    console.log(x);
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            },
            getEnumList(){
                app.ajax.crm.enumList().then(x => {
                    function changeObject(arr, index) {
                        for (var i = 0; i < arr.length; i++) {
                            index.push({key: arr[i].getDisplay, value: arr[i].ordinal})
                        }
                    }

                    changeObject(x.data.item.customerCreditEnum, this.enumList.customerCreditEnum);
                    changeObject(x.data.item.codeTypeEnum, this.enumList.codeTypeEnum);
                })
            },
            doSave(){
                if (this.dialog.status == 1) { // 新增
                    console.log(this.dialogData, 1);
                    app.ajax.crm.add(this.dialogData).then(x => {
                        _ => this.get(), _ => this.get()
                    })
                } else if (this.dialog.status == 2) { // 编辑

                    app.ajax.crm.update(this.dialogData).then((x) => {
                        app.clearField(this.search);
                        this.get();
                        this.dialog.dialogVisible = false;
                        this.$notify({
                            title: '成功',
                            message: x.data.desc,
                            type: 'success'
                        });
                    }).catch(() => {

                    });
                }
                this.dialog.status = 1;
                this.dialog.dialogVisible = true;
            },
            delete(x){
                app.ajax.crm.delete({customerId: x}).then((x) => {
                    this.get();
                    this.$notify({
                        title: '成功',
                        message: x.data.desc,
                        type: 'success'
                    });
                })
            },
            enable(x){
                app.ajax.crm.enable({customerId: x}).then((x) => {
                    this.get();
                this.$notify({
                    title: '成功',
                    message: x.data.desc,
                    type: 'success'
                });
                })
            },
            showWatchPanel(x){
                Object.assign(this.search, this.searchParams, {telphone: x,});
                app.ajax.crm.find(this.search).then(x => {
                    this.dialogData = x.data.item[0];
                    this.total = x.data.total;
                    this.loading = false;
                    this.dialog.dialogVisible = true;
                    this.dialog.status = 0;
                })
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
            app.service['common/pagination/init'](c.constomers, _ => c.constomers.get());
            c.constomers.get();
            c.constomers.getEnumList();
            app.departmentListFunc();
            window.app.ajax('/product/base/zone/find').then((msg) => {
                app.controls.constomers.countryList = msg.data.item.map(({title, code}) => ({value: code, key: title}));
            })
//            app.controls.app.Perm.write=true;
            $('#setDate input').on('focus', function () { // 清空已选择日期
                c.constomers.show_time[0] = '';
                c.constomers.show_time[1] = '';
                c.constomers.search.minCreateAt = '';
                c.constomers.search.maxCreateAt = '';
                $(this).val('');
            })
        })
    </script>
</js>