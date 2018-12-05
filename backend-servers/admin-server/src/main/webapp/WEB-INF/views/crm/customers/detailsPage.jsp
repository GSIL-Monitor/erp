<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>语言列表</H1>
  </template>

  <template scope='app'>
      <el-row  :loading='app.controls.customerDetail.loading'>
          <el-col :span='18' style='padding: 15px;'>
              <el-card>
                  <br>客户编号：{{ window.layoutData.controls.customerDetail.data.id}}
                  <br>客户姓名：{{ window.layoutData.controls.customerDetail.data.firstName}}{{ window.layoutData.controls.customerDetail.data.lastName}}
                  <br>客户手机：{{ window.layoutData.controls.customerDetail.data.telphone}}
                  <br>客户邮箱：{{ window.layoutData.controls.customerDetail.data.email}}
                  <br>客户等级：{{ window.layoutData.controls.customerDetail.data.customerCreditEnum}}
                  <br>拒收次数：{{ window.layoutData.controls.customerDetail.data.rejectQty}}
                  <br>签收次数：{{ window.layoutData.controls.customerDetail.data.acceptQty}}
                  <br>添加时间：{{ window.layoutData.controls.customerDetail.data.createAt}}
                  <br>备    注：{{ window.layoutData.controls.customerDetail.data.memo}}
              </el-card>
          </el-col>
      </el-row>
      <el-row>
          <el-tabs v-model="window.layoutData.controls.tab.activeName" type="card" @tab-click="window.layoutData.controls.tab.click">
              <el-tab-pane label="客户订单信息" name="first" :span='18' style='padding: 15px;'>
                  <el-row>
                      <control-table tablekey='orderList' :app='app' :loading='app.controls.orderList.loading'>
                          <el-table-column
                                  prop="idOrder"
                                  label="ID"
                                  width="120">
                          </el-table-column>
                          <el-table-column
                                  label="客户姓名"
                                  width="130">
                              <template scope="data">
                                  {{data.row.firstName}}{{data.row.lastName}}
                              </template>
                          </el-table-column>
                          <el-table-column
                                  prop="tel"
                                  label="电话"
                                  width="120">
                          </el-table-column>
                          <el-table-column
                                  prop="email"
                                  label="邮箱">
                          </el-table-column>
                          <el-table-column label="地址">
                              <template scope="data">
                                  {{data.row.address}}
                              </template>
                          </el-table-column>
                          <el-table-column
                                  prop="statusName"
                                  label="订单状态">
                          </el-table-column>
                      </control-table>
                  </el-row>
              </el-tab-pane>


              <el-tab-pane label="客户修改记录" name="second">
                  <el-row>
                      <control-table tablekey='changeLogList' :app='app'  :loading='app.controls.changeLogList.loading'>
                          <el-table-column prop='name' label='ID'>
                              <template scope='inner'>
                                  <control-item :app='app' :local='inner.row' datakey='id' type='edit' @saved='app.controls.changeLogList.doUpdate(inner.row)' @discard='app.controls.changeLogList.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
                              </template>
                          </el-table-column>
                          <el-table-column prop='creator' label='修改人'>
                              <template scope='inner'>
                                  <control-item :app='app' :local='inner.row' datakey='creator' type='edit' @saved='app.controls.changeLogList.doUpdate(inner.row)' @discard='app.controls.changeLogList.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
                              </template>
                          </el-table-column>
                          <el-table-column prop='content' label='修改内容'>
                              <template scope='inner'>
                                  <control-item :app='app' :local='inner.row' datakey='content' type='edit' @saved='app.controls.changeLogList.doUpdate(inner.row)' @discard='app.controls.changeLogList.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
                              </template>
                          </el-table-column>
                          <el-table-column prop='createAt' label='修改时间'>
                              <template scope='inner'>
                                  <control-item :app='app' :local='inner.row' datakey='createAt' type='edit' @saved='app.controls.changeLogList.doUpdate(inner.row)' @discard='app.controls.changeLogList.refresh()' :item-disabled='!app.controls.app.Perm.write'></control-item>
                              </template>
                          </el-table-column>
                      </control-table>
                  </el-row>

              </el-tab-pane>
          </el-tabs>
      </el-row>



  </template>

</body>
<js>
    <script>
        var args = qs.parse(window.location.search, {ignoreQueryPrefix: true});
        window.layoutData.id = args.id;

        var c = window.layoutData.controls;

        c.customerDetail = {
            data:{},
            getDetail(){
                this.loading = true;
                app.ajax.crm.detail({"id": window.layoutData.id}).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    window.setTitle(this.data.firstName +this.data.lastName);
                    this.loading = false;
                });
            }
        };

        c.tab = {
            activeName:"first",
            click(){
//                alert("click is ok");
            }
        };


        c.orderList = {
            data: [],
            page: 1,
            pageSize: 100,
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
            dialog:{
                add: false,
                history: false,
            },
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                this.search.start = (this.page - 1) * this.pageSize ;
                app.ajax.crm.oldOrders({"id": window.layoutData.id}).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            }
        };

        c.changeLogList = {
            data: [],
            page: 1,
            pageSize: 100,
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
            dialog:{
                add: false,
                history: false,
            },
            get(){
                this.search.limit = this.pageSize;
                this.loading = true;
                this.search.start = (this.page - 1) * this.pageSize ;
                app.ajax.crm.udpateLog({"id": window.layoutData.id}).then(x => {
                    this.data = x.data.item;
                    this.total = x.data.total;
                    this.loading = false;
                })
            }
        };


        $(function(){
//            app.service['common/pagination/init'](c.orderList, _ => c.orderList.get());
            c.customerDetail.getDetail();
            c.orderList.get();
            c.changeLogList.get();
        })
    </script>
</js>