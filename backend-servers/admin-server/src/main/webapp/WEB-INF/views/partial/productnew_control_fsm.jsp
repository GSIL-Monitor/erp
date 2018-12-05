<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- this file is auto-generated via src/main/frontend/fsm.js
from fsm.xml
no need to edit this directly -->
<el-row v-if='inner.row.state == "start"'>
    <el-row>
        <el-button size='mini' type='text' :actionkey='{type: "create", id: inner.row.id, row: inner.row}'
                   actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                   :disabled='!app.controls.app.Perm.write'>创建
        </el-button>
    </el-row>
</el-row>
<el-row v-if='inner.row.state == "draft"'>
    <template v-if="app.controls.page == 'adList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "submit", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                       :disabled='!app.controls.app.Perm["product_new.submit"]'>提交
            </el-button>
        </el-row>
</template></el-row>
<el-row v-if='inner.row.state == "waitCheck"'>
    <template v-if="app.controls.page == 'checkList'">
        <%-- <el-row>
             <el-button size='mini' type='text' :actionkey='{type: "checkOk", id: inner.row.id, row: inner.row}'
                        actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                        :disabled='!app.controls.app.Perm["product_new.checkOk"]'>排查通过
             </el-button>
         </el-row>--%>
    </template>
    <template v-if="app.controls.page == 'checkList'">
        <%--<el-row><el-button size='mini' type='text' :actionkey='{type: "warn", id: inner.row.id, row: inner.row}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">预警</el-button></el-row>--%>
    </template>
    <template v-if="app.controls.page == 'checkList'">
        <%--<el-row><el-button size='mini' type='text' :actionkey='{type: "duplicate", id: inner.row.id, row: inner.row}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">重复</el-button></el-row>--%>
    </template>
    <template v-if="app.controls.page == 'checkList'">
        <%--<el-row><el-button size='mini' type='text' :actionkey='{type: "decline", id: inner.row.id, row: inner.row}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">拒绝</el-button></el-row>--%>
</template>
    <template v-if="app.controls.page == 'checkList' || app.controls.page == 'adList'">
        <%--<el-row>
            <el-button size='mini' type='text' :actionkey='{type: "backToCreated", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                       :disabled='!app.controls.app.Perm["product_new.backToDraft"]'>回到草稿
            </el-button>
        </el-row>--%>
</template></el-row>
<%--<el-row v-if='inner.row.state == "checkOk"'>
    <el-row><el-button size='mini' type='text' :actionkey='{type: "orderCancel", id: inner.row.id, row: inner.row}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">销档</el-button></el-row>
    <el-row><el-button size='mini' type='text' :actionkey='{type: "archive", id: inner.row.id, row: inner.row}' actionfunc="Object.getMethod(app.controls.newtable, 'action')">建档</el-button></el-row>
</el-row>--%>
<el-row v-if='inner.row.state == "waitApprove"'>
    <template v-if="app.controls.page == 'approveList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "checkOk", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                       :disabled='!app.controls.app.Perm["product_new.checkOk"]'>排查通过
            </el-button>
        </el-row>
    </template>
    <template v-if="app.controls.page == 'approveList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "decline", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')">拒绝
            </el-button>
        </el-row>
    </template>
</el-row>

<el-row v-if='inner.row.state == "checkWarn"'>
    <template v-if="app.controls.page == 'approveList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "checkOk", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                       :disabled='!app.controls.app.Perm["product_new.checkOk"]'>排查通过
            </el-button>
        </el-row>
    </template>
    <template v-if="app.controls.page == 'approveList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "giveUp", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                       :disabled='!app.controls.app.Perm.give_up'>放弃
            </el-button>
        </el-row>
    </template>
</el-row>

<el-row v-if='inner.row.state == "duplication"'>
    <template v-if="app.controls.page == 'adList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "giveUp", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                       :disabled='!app.controls.app.Perm.give_up'>放弃
            </el-button>
        </el-row>
</template>
    <template v-if="app.controls.page == 'adList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "requestApprove", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')"
                       :disabled='!app.controls.app.Perm["product_new.requestApprove"]'>申请审批
            </el-button>
        </el-row>
    </template>
</el-row>
<el-row v-if='inner.row.state == "waitCheck"'>
    <template v-if="app.controls.page == 'checkList'">
        <el-row>
            <el-button size='mini' type='text' :actionkey='{type: "goback", id: inner.row.id, row: inner.row}'
                       actionfunc="Object.getMethod(app.controls.newtable, 'action')">返回到细化分类
            </el-button>
        </el-row>
    </template>
</el-row>
