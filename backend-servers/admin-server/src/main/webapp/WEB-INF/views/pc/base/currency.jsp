<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
  <template slot='controls' scope='app'>
      <H1>币种管理</H1>
  </template>

  <template scope='app'>
     <el-row>
     <el-col :span='14'>
      <control-table tablekey='table' :app='app' v-loading='app.controls.table.loading'>
            <template slot='title'>币种列表</template>
            <template scope='app' slot='controls'>
                  <el-row>
                      <el-col :span='16'>
                          <control-item type='input' label='名称' :app='app.controls.app'
                                        :local='app.controls.table.searchParams' datakey='name'></control-item>
                      </el-col>
                    <el-col :span='8'><el-button @click='app.controls.table.doSearch()' type='primary' icon='search'>搜索</el-button></el-col>
                    
                </el-row>
                <el-row>
                    <el-col :span='8'>
                        <el-button
                                @click='app.controls.app.clearField(app.controls.table.add);app.controls.table.dialog.add=true'
                                type='new' icon='plus' :disabled='!app.controls.app.Perm.write'>新增
                        </el-button>
                    </el-col>
                </el-row>
            </template>
            <el-table-column label='操作' width='80'>
                <template scope='inner'>
                    <el-button type='danger'  size='mini' @click='app.controls.table.doDelete(inner.row)' v-if='!inner.row.usable'>删除</el-button>
                    <el-button type='success'  size='mini' @click='app.controls.hist.doShowHistory(inner.row)'>历史</el-button>
                    <el-button :type='!inner.row.usable ? "success" : "info"'  size='mini' @click='app.controls.table.setUsable(inner.row)' v-if='!inner.row.usable'>{{!inner.row.usable ? '启用' : ''}}</el-button>
                </template>
            </el-table-column>
            <el-table-column prop='id' label='id' width='50'>


            </el-table-column>
            <el-table-column prop='name' label='名称'>
                <template scope='inner'>
                    <control-item type='edit' :local='inner.row' datakey='name' @saved='app.controls.table.doUpdate(inner.row)' v-if='!inner.row.usable'></control-item>
                    <span v-if='inner.row.usable' style='margin-left: 15px;'>{{inner.row.name}}</span>
                </template>
            </el-table-column>
            <el-table-column prop='currencyCode' label='编码' width='80'>
                <template scope='inner'>
                    <control-item type='edit' :local='inner.row' datakey='currencyCode' @saved='app.controls.table.doUpdate(inner.row)' v-if='!inner.row.usable'></control-item>
                    <span v-if='inner.row.usable' style='margin-left: 15px;'>{{inner.row.currencyCode}}</span>
                </template>
            </el-table-column>
            <el-table-column prop='symbol' label='符号' width='80' >
                <template scope='inner'>
                    <control-item type='edit' :local='inner.row'    datakey='symbol' @saved='app.controls.table.doUpdate(inner.row)' v-if='!inner.row.usable'></control-item>
                    <span v-if='inner.row.usable' style='margin-left: 15px;font-family:Arial;'>{{inner.row.symbol}}</span>
                </template>
            </el-table-column>

          <el-table-column prop='symbolLeft' label='左符号' width='80' >
              <template scope='inner'>
                  <control-item type='edit' :local='inner.row'    datakey='symbolLeft' @saved='app.controls.table.doUpdate(inner.row)' v-if='!inner.row.usable'></control-item>
                  <span v-if='inner.row.usable' style='margin-left: 15px;font-family:Arial;'>{{inner.row.symbolLeft}}</span>
              </template>
          </el-table-column>


          <el-table-column prop='symbolRight' label='右符号' width='140' >
              <template scope='inner'>
                  <control-item type='edit' :local='inner.row'    datakey='symbolRight' @saved='app.controls.table.doUpdate(inner.row)' v-if='!inner.row.usable'    :item-disabled='!app.controls.app.Perm.write' ></control-item>
                  <span v-if='inner.row.usable' style='margin-left: 15px;font-family:Arial;'>{{inner.row.symbolRight}}</span>
              </template>
          </el-table-column>



            <el-table-column prop='rateCny' label='到人民币汇率' width='110'>
                <template scope='inner'>
                    <control-item type='edit' :local='inner.row' datakey='rateCny'
                                  :item-disabled='!app.controls.app.Perm.write'
                                  @saved='app.controls.table.doUpdate(inner.row)'
                                  :pattern="/^([1-9]\d{1,3}|\d)(\.\d{1,6})?$/"
                                  validate-error="最多4位整数6位小数"></control-item>
                    <!--<span v-if='inner.row.usable' style='margin-left: 15px;'>{{inner.row.rateCny}}</span>-->
                </template>
            </el-table-column>
            <el-table-column prop='usable' label='状态' width='100'>
                <template scope='inner'>
                    {{inner.row.usable ? '启用' : '禁用'}}
                </template>
            </el-table-column>
      </control-table>
    </el-col>
    <el-col :span='10'>
        <control-table tablekey='hist' :app='app' :loading='app.controls.hist.loading'>
             <template slot='title'>汇率历史</template>
                    <template scope='app' slot='controls'>
                        <el-row>
                            <el-col :span='24'><control-item type='select' label='货币代码' :app='app.controls.app' :local='app.controls.hist.searchParams' datakey='name' :listfunc='() => Promise.resolve(app.controls.table.data.map(x => ({value: x.name, key: x.name})))' listkey=''></control-item></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span='24'><control-item type='daterange' label='记录时间' :app='app.controls.app' :local='app.controls.hist.searchParams' datakey='updateAt'></control-item></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span='24'><el-button @click='app.controls.hist.doSearch()' type='primary' icon='search'>搜索</el-button></el-col>
                        </el-row>
                    </template>
                    <el-table-column prop='name' label='名称' width='100'></el-table-column>
                    <el-table-column prop='currencyCode' label='货币编码' width='120'></el-table-column>
                    <el-table-column prop='rateCny' label='到人民币汇率' width='120' ></el-table-column>
                    <el-table-column prop='createAt' label='更新时间' class-name='breakspace'></el-table-column>
            </control-table>
    </el-col>
    </el-row>
      <dialog-create 
          :visible='app.controls.table.dialog.add' 
          @update:visible='app.controls.table.dialog.add=arguments[0]'
          :title='"添加"' 
          :app='app'
          :controls='app.controls'
          @ok = 'app.controls.table.doAdd()'
      >
        <el-card>
                <el-row><el-col><control-item type='input' :local='app.controls.table.add' datakey='name' label='名称'></control-item></el-col></el-row>
                <el-row><el-col><control-item type='input' :local='app.controls.table.add' datakey='currencyCode' label='货币编码'></control-item></el-col></el-row>
                <el-row><el-col><control-item type='input' :local='app.controls.table.add' datakey='symbol' label='币种符号'></control-item></el-col></el-row>
                <el-row><el-col><control-item type='input' :local='app.controls.table.add' datakey='symbolLeft' label='左货币符号'></control-item></el-col></el-row>
                <el-row><el-col><control-item type='input' :local='app.controls.table.add' datakey='symbolRight' label='右货币符号'></control-item></el-col></el-row>
                <el-row><el-col><control-item type='input' :local='app.controls.table.add' datakey='rateCny' label='到人民币汇率' :pattern="/^([1-9]\d{1,3}|\d)(\.\d{1,6})?$/" validate-error="最多4位整数6位小数"></control-item></el-col></el-row>
        </el-card>
    </dialog-create>
    
  </template>

</body>
<js>
    <style>
        .el-col{
            padding: 5px 15px;

        }
    </style>
    <script>
            var c = window.layoutData.controls;
            c.table = {
                data: [],
                page: 1,
                pageSize: 20,
                total: 0,
                loading: false,
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
                    this.search.start = (this.page - 1) * this.pageSize ;
                    this.loading = true;
                    app.ajax.currency.find(this.search).then(x => {
                        this.data = x.data.item;
                        this.total = x.data.total;
                        this.loading = false;
                        this.data.map(item => {
                            switch (item.symbol){
                                case "&yen;":
                                    symbolRightitem.symbol = "￥"
                                    break;
                                case "&amp;":
                                    item.symbol = "&"
                                    break;
                                case "&lt;":
                                    item.symbol = "<";
                                    break;
                                case "&gt;":
                                    item.symbol = ">"
                                default :
                                    item.symbol = item.symbol
                            }

                            switch (item.symbolLeft){
                                case "&yen;":
                                    item.symbolLeft = "￥"
                                    break;
                                case "&amp;":
                                    item.symbolLeft = "&"
                                    break;
                                case "&lt;":
                                    item.symbolLeft = "<";
                                    break;
                                case "&gt;":
                                    item.symbolLeft = ">"
                                default :
                                    item.symbolLeft = item.symbolLeft
                            }

                            switch (item.symbolRight){
                                case "&yen;":
                                    item.symbolRight = "￥"
                                    break;
                                case "&amp;":
                                    item.symbolRight = "&"
                                    break;
                                case "&lt;":
                                    item.symbolRight = "<";
                                    break;
                                case "&gt;":
                                    item.symbolRight = ">"
                                default :
                                    item.symbolRight = item.symbolRight
                            }





                        })
                    });
                },
                doAdd(){
                    app.ajax.currency.add(Object.assign({}, this.add, {usable: false})).then(_ => {
                        this.dialog.add = false;
                        this.get();
                    }, _ => this.get() );
                },
                doSearch(){
                    Object.assign(this.search, this.searchParams);
                    this.get();
                },
                doDelete(row){
                    console.log(row);
                   app.ajax.currency.delete(row).then( _ => this.get(), _ => this.get()  );
                },
                doUpdate(row){
                    app.ajax.currency.update(row).then( _ => this.get(), _ => this.get() );
                },
                setUsable(row){
                    var row_ = Object.assign(row);
                    row_.usable = !row_.usable;
                    app.ajax.currency.update(row).then( _ => this.get(), _ => this.get() );
                }
                
            };
            $(function(){
                app.service['common/pagination/init'](c.table, _ => c.table.get());
                c.table.get();
            });

             c.hist = {
                data: [],
                page: 1,
                pageSize: 20,
                total: 0,
                loading: false,
                search: {
                    start: 0,
                },
                searchParams: {
                    name: undefined,
                    updateAt: [],
                },
                add: {
                    title: "",
                    langCode: "",
                },
                dialog:{
                    add: false,
                    history: false,
                },
                doShowHistory(row){
                    this.searchParams.name = row.name;
                    this.searchParams.currencyCode = row.currencyCode;
                    this.searchParams.updateAt = [];
                    this.doSearch();
                },
               
                get(){
                    this.search.limit = this.pageSize;
                    this.search.start = (this.page - 1) * this.pageSize ;
                    this.loading = true;
                    app.ajax.currency.findHistory(this.search).then(x => {
                        this.data = x.data.item;
                        this.total = x.data.total;
                        this.loading = false;
                    })
                },
                doSearch(){
                    Object.assign(this.search, this.searchParams);
                    app.util.formatRange(this.search, 'updateAt', 'minCreateAt', 'maxCreateAt');
                    this.get();
                },
            };
            $(function(){
                app.service['common/pagination/init'](c.hist, _ => c.hist.get());
                c.hist.get();
                console.log( $("div"))
            })
    </script>
    <style>

    </style>
</js>