<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<body>
<template slot='controls' scope='app'>
    <H1>黑名单导入</H1>
</template>

<template scope='app'>
    <el-row :gutter="20">
        <el-col :span="12">
            <el-card>
                <el-input
                        minlength = 2
                        min = 2
                        type="textarea"
                        :rows="20"
                        placeholder="请输入要导入的内容"
                        v-model = "app.controls.textarea"
                >
                </el-input>
            </el-card>
        </el-col>
        <el-col :span="10" style="margin-top: 200px">
            <el-button  type="primary" @click = "app.controls.blackList.importBlackList">提交</el-button>
        </el-col>
    </el-row>
</template>

</body>
<js>
    <script>
        window.layoutData.controls = {
            textarea: "",
            blackList: {
                importBlackList: function () {
                    console.log(app.controls.textarea);
                    app.ajax.blackList.import({"content":  app.controls.textarea}).then(x => {
//                        this.data = x.data.item;
//                        this.total = x.data.total;
//                        this.loading = false;
                    });
                }
            }
        };
        $(function(){
            this.loading = false;
        });
    </script>
</js>