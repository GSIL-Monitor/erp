部门管理
<el-tree :v-loading='true' :data="item" v-for='item in app.controls.departmentData' style='width: 300px;' :props='{label: "name", children: "children"}'></el-tree>
供应商列表
            <control-table :app='app' style='width: 1200px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.searchParams' datakey='field' label='供应商链接'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span='12' style='padding: 5px 15px; '>
                         <el-button type='primary' icon='plus' style='margin-top: 2px;'>新增</el-button>
                    </el-col>
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>     
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='ID'></el-table-column>
                <el-table-column prop='field' label='供应商名称' ></el-table-column>
                <el-table-column prop='field' label='供应商连接' ></el-table-column>
                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                
                
                
            </control-table>
             采购列表
            <control-table :app='app' style='width: 1500px;' tablekey='newtable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :local='app.local.searchParams' datakey='field' label='部门'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :local='app.local.searchParams' datakey='field' label='仓库'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='select' :local='app.local.searchParams' datakey='field' label='状态'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.searchParams' datakey='field' label='SPU'></control-item>
                    </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span='6' style='padding: 5px 15px; '>
                            <control-item type='daterange' :local='app.local.searchParams' datakey='field' label='创建日期'></control-item>
                        </el-col>
                        <el-col :span='6' style='padding: 5px 15px; '>
                            <control-item type='daterange' :local='app.local.searchParams' datakey='field' label='采购日期'></control-item>
                        </el-col>
                        <el-col :span='6' style='padding: 5px 15px; '>
                            <control-item type='input' :local='app.local.searchParams' datakey='field' label='采购单号'></control-item>
                        </el-col>
                         <el-col :span='6' style='padding: 5px 15px;'>
                            <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <el-button type='primary' icon='plus' style='margin-top: 2px;'>新增</el-button>
                            <el-button type='success' icon='check' style='margin-top: 2px;'>提交</el-button>
                            <el-button type='danger' icon='delete' style='margin-top: 2px;'>删除</el-button>
                        </el-col>                    
                    </el-row>
                    
                </template>
                <el-table-column type='selection'></el-table-column>
                
                <el-table-column label='操作'  min-width='150'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='ID'></el-table-column>
                <el-table-column prop='field' label='采购单号' ></el-table-column>
                <el-table-column prop='field' label='采购时间' ></el-table-column>
                <el-table-column prop='field' label='仓库' ></el-table-column>
                <el-table-column prop='field' label='供应商' ></el-table-column>
                <el-table-column prop='field' label='产品名称' ></el-table-column>
                <el-table-column prop='field' label='SPU' ></el-table-column>

                <el-table-column prop='field' label='采购单价' ></el-table-column>
                <el-table-column prop='field' label='采购数量' ></el-table-column>
                <el-table-column prop='field' label='采购金额' ></el-table-column>
                <el-table-column prop='field' label='总金额' ></el-table-column>
                <el-table-column prop='field' label='运费' ></el-table-column>
                <el-table-column prop='field' label='采购渠道' ></el-table-column>

                <el-table-column prop='status' label='状态'>
                    
                </el-table-column> 
                <el-table-column prop='field' label='创建人' ></el-table-column>
   
            </control-table>
            国家列表
            <control-table :app='app' style='width: 1200px;' tablekey='regiontable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.searchParams' datakey='country' label='国家'></control-item>
                    </el-col>
                    <el-col :span=3>&nbsp;</el-col>
                    <el-col :span='6'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                        <el-button type='primary' @click='app.local.dialogShow=true' icon='search' style='margin-top: 2px;'>新增</el-button>
                    </el-col>
                    </el-row>
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='ID' label='id'></el-table-column>
                <el-table-column prop='country' label='国家' min-width='100'></el-table-column>
                <el-table-column prop='remark' label='备注'></el-table-column>
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status ? '开' : '关'}}</template>
                </el-table-column> 
            </control-table>
            <dialog-create :title='"新增国家"' :app='app' :local='app.controls.regiontable.add' 
                :visible='app.controls.regiontable.dialogShow'
                @update:visible='val => window.app.controls.regiontable.dialogShow = val'>
                <template scope='app'>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='country' label='国家'></control-item>
                        </el-col>    
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='select' :local='app.local' datakey='status' label='状态'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='symbol' label='备注'></control-item>
                        </el-col>    
                    </el-row>
                    
                </template>
            </dialog-create>
            币种列表
             <control-table :app='app' style='width: 1200px;' tablekey='currencytable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.searchParams' datakey='code' label='币种'></control-item>
                        
                    </el-col>
                    <el-col :span=3>&nbsp;</el-col>
                    <el-col :span='6'>
                        <el-button type='primary' icon='search' style='margin-top: 2px;'>搜索</el-button>
                        <el-button type='primary' @click='app.controls.currencytable.dialogShow=true' icon='search' style='margin-top: 2px;'>新增</el-button>
                    </el-col>
                    </el-row>
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column label='操作'  min-width='200'>
                    <template scope='inner'>
                        <el-button size='mini'>查看</el-button>
                        <el-button size='mini'>删除</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='id'></el-table-column>
                <el-table-column prop='name' label='币种' min-width='100'></el-table-column>
                <el-table-column prop='code' label='代码'></el-table-column>
                <el-table-column prop='symbol' label='符号'></el-table-column>
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status ? '开' : '关'}}</template>
                </el-table-column> 
            </control-table>
            <dialog-create :title='"新增币种"' :app='app' :local='app.controls.currencytable.add' 
                :visible='app.controls.currencytable.dialogShow'
                @update:visible='val => window.app.controls.currencytable.dialogShow = val'>
                <template scope='app'>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='code' label='币种'></control-item>
                        </el-col>    
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='name' label='英文缩写'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='symbol' label='符号'></control-item>
                        </el-col>    
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='input' :local='app.local' datakey='ratio' label='汇率'></control-item>
                        </el-col>    
                    </el-row>
                    <el-row>
                        <el-col :span='24' style='padding: 5px 15px;'>
                            <control-item type='select' :local='app.local' datakey='status' label='状态'></control-item>
                        </el-col>    
                    </el-row>
                </template>
            </dialog-create>
             用户管理
            <control-table :app='app' style='width: 600px;' tablekey='usertable'>
                <template scope='app' slot='controls'>
                    <el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.searchParams' datakey='lastname' label='姓名'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='loginid' label='账号'></control-item>
                    </el-col> 
                     <el-col :span='6' style='padding: 5px 15px;'>
                        <control-item type='input' :local='app.local.searchParams' datakey='departmentId' label='部门'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='input' :local='app.local.searchParams' datakey='jobId' label='职位'></control-item>
                    </el-col>
                    </el-row><el-row>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <control-item type='select' :local='app.local.searchParams' datakey='status' label='状态' :list='app.local.options'></control-item>
                    </el-col>
                    <el-col :span='6' style='padding: 5px 15px;'>
                    <el-button @click='alert(JSON.stringify(app.local.search))'>搜索</el-button>
                    </el-col>
                </template>
                <el-table-column type='selection'></el-table-column>
                <el-table-column prop='lastname' label='姓名'></el-table-column>
                <el-table-column prop='loginid' label='账号'></el-table-column>
                <el-table-column prop='departmentId' label='部门'></el-table-column>
                <el-table-column prop='status' label='启用'>
                    <template scope='inner'>{{inner.row.status ? '开' : '关'}}</template>
                </el-table-column> 
            </control-table>
            