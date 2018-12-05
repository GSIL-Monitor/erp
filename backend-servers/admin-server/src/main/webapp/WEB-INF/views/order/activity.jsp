<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>活动列表</title>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<template scope='app' slot='controls'>
    <H1>职位列表</H1>
</template>
<template scope='app'>
    <el-row>
        <el-col style='width: 600px;'>
            <control-table :app='app' tablekey='jobtable' v-loading='app.controls.jobtable.loading' style='width: 600px;'>
                <template scope='app' slot='controls'>
                    <el-row>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <control-item type='inputAsync'
                                          @autocomplete='app.local.searchClick()'
                                          :complete-array-url-func='text=>window.app.ajax("/admin/job/getNameBySearch?search="+text).then(result => result.data.item.map(x => ({value: x})))'
                                          :local='app.local.searchParams'
                                          datakey='name'
                                          label='职位'>
                            </control-item>
                        </el-col>
                        <el-col :span='12' style='padding: 5px 15px;'>
                            <el-button @click='app.local.searchClick()' type='primary' icon='search'>搜索</el-button>
                        </el-col>
                    </el-row>
                </template>

                <el-table-column prop='职位授权' v-popover:popoveredit label='职位授权' width='100'>
                    <template scope='inner'>
                        <div>
                        <el-button type='primary' size='mini' @click='app.controls.jobtable.dialogOpenAuth(inner.row); app.controls.jobtable.preparePopup(app.controls.app.$refs.tree, inner.row)' slot='reference'>职位授权</el-button>
                    </div>
                    </template>
                </el-table-column>
                <el-table-column prop='id' label='id' width='100'></el-table-column>
                <el-table-column prop='name' label='职位' width='250'></el-table-column>
                <el-table-column prop='remark' label='描述' width='100'></el-table-column>
            </control-table>
        </el-col>
        <el-col style='width: 15px;'>&nbsp;</el-col>
        <el-col :style='`width: auto; min-width:200px;  font-size: 12px; line-height: 12px; color: #222;`'  v-if='item.length' v-for='item in app.controls.jobtable.menus' >
            <el-card :style='`background: #F6F6F6;`'>
                <H3 slot='header'><em>{{app.controls.jobtable.editingName}}</em>的职位菜单授权</H3>
                <div :style='`height: \${window.innerHeight - 200}px; overflow-y: auto; background: #F6F6F6;`'>
                    <el-tree :data='item'
                             node-key='id'
                             id='jobTree'
                             :check-strictly='true'
                             :props="{label: 'name', children: 'children'}"
                             :default-expand-all='true'
                             style='background: #F6F6F6;'
                             show-checkbox
                             :render-content='app.controls.jobtable.renderNode'
                             @check-change='app.controls.jobtable.checkChange.apply(app.controls.jobtable, arguments)'>
                    </el-tree>
                </div>
            </el-card>
        </el-col>
        <el-col style='width: 15px;'>&nbsp;</el-col>
        <el-col :style='`width: 400px; height: \${window.innerHeight - 100}px; position: relative; font-size: 12px; line-height: 12px;`' v-if='app.controls.jobtable.dialog.element'>
            <el-card :style='`height: 150px; left: 50%; top: 50%; transform: translate(-50%, -50%); overflow-y: auto; background: #F6F6F6; position: relative`'>
                <H3><em>{{app.controls.jobtable.editingName}}</em> 在 <em>{{app.controls.jobtable.localOpenElementData.data.name}}</em> 页面的元素授权</H3>
                <el-select type='select' v-model='app.controls.jobtable.has' multiple @change='app.controls.jobtable.changeElement' style='width: 100%;'>
                    <el-option v-for='item in app.controls.jobtable.elementList' :key='item.name' :label='item.name' :value='item.id'>

                    </el-option>
                </el-select>
            </el-card>
        </el-col>
    </el-row>
</template>
</body>
<js>

</js>