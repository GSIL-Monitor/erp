<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<dialog-create 
    :app='app'
    :visible='app.controls.fsm.dialog' 
    @update:visible='app.controls.fsm.dialog=arguments[0]'
    @ok='app.controls.fsm.dialog=false'>
        
        <control-table :app='app' tablekey='fsm' :loading='app.controls.fsm.loading' :sub='true'>
            <el-table-column width="180" label='时间' prop='createAt'></el-table-column>
            <el-table-column label='操作人' prop='optUid'></el-table-column>
            <el-table-column label='源状态' prop='srcState'>
                <template scope='inner'>
                    {{inner.row.srcStateDisplay || app.controls.app.StateNames[inner.row.srcState] || "(" + inner.row.srcState  + ")"}}
                </template>
            </el-table-column>
            <el-table-column label='事件'  prop='eventName'>
                    <template scope='inner'>
                        {{inner.row.eventNameDisplay || app.controls.app.EventNames[inner.row.eventName] || "(" + inner.row.eventName  + ")"}}
                    </template>
            </el-table-column>
            <el-table-column label='目标状态' prop='dstState'>
                <template scope='inner'>
                        {{inner.row.dstStateDisplay || app.controls.app.StateNames[inner.row.dstState] || "(" + inner.row.dstState  + ")"}}
                    </template>
            </el-table-column>
            <el-table-column label='备注' prop='memo'></el-table-column>
        </control-table>
</dialog-create>
