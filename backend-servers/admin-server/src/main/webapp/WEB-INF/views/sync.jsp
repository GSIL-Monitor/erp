<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
    <template scope='app'>
        <div>
            <template v-for='(item, index) in app.controls.buttons'>
                <el-button :type='item.type' style='margin-right: 15px; margin-bottom: 15px;' @click='doSync(item)'
                           :loading='item.loading' :icon="item.icon">{{item.name}}
                </el-button>
                <div style='height: 15px' v-if='index % 4 == 3'>&nbsp;</div>
            </template>
        </div>

        <div>
            <template>
                产品ID:<input text="text" id="product"/>
                <button type="button" id="productOn">产品同步(可批量:使用,分割)</button>
            </template>
            <template>
                产品ID:<input text="text" id="productSKu"/>
                <button type="button" id="productSKuOn">产品SKU同步(可批量:使用,分割)</button>
            </template>
        </div>
        <div>
            <template>
                产品内部名:<input text="text" id="innername"/>
            </template>
            <template>
                产品ID:<input text="text" id="productId"/>
            </template>
            <button type="button" id="updateById">内部名修改</button>
        </div>

        <div>
            <template>
                开始时间:<input text="text" id="startTime"/>
                结束时间:<input text="text" id="endTime"/>
                <button type="button" id="checkEmail">排重邮件重发</button>
            </template>
            <template>
                <button type="button" id="warningEmail">预警邮件重发</button>
            </template>
            <template>
                <button type="button" id="disappearedEmail">消档邮件重发</button>
            </template>
            <template>
                <button type="button" id="waitoffsaleEmail">待下架下架重发</button>
            </template>
        </div>

    </template>
</body>
<js>
    <script>
    var names = `
    ① OA用户导入
    ① OA职位导入
    ① OA部门导入
    ① 国家信息同步
    ① 区域信息同步
    ① 品类信息同步
    ① 属性信息同步
    ① 属性值信息同步
    ① 产品SKU同步
    ② 老erp用户绑定
    ② 老erp部门绑定
    ③ 产品信息同步
    ③ 排重产品同步
    ④ 产品区域同步
    ⑤ 产品部门区域域名同步
    ⑥ 产品区域库存同步
    ⑦ 产品区域最后下单时间同步
    `.trim().split("\n").map(x => x.trim());
    var urls=`
    /admin/oaSync/pullAllUser
    /admin/oaSync/pullAllJob
    /admin/oaSync/pullAllDepartment
    /pc/sync/country
    /pc/sync/zone
    /pc/sync/category
    /pc/sync/attribute
    /pc/sync/attributeValue
    /pc/sync/productSku
    /admin/oldErpSync/pullAllUser
    /admin/oldErpSync/pullAllDepartment
    /pc/sync/product
    /pc/sync/checkProductSync
    /pc/sync/productZone
    /pc/sync/productZoneDomain
    /pc/sync/stock
    /pc/sync/lastOrderAt
    `.trim().split("\n").map(x => x.trim());

    window.layoutData.controls.buttons = [];
    console.log(window.layoutData.controls);
    names.forEach((x, i) => {
        window.layoutData.controls.buttons[i] = {
            url: urls[i],
            name: x,
            loading: false,
            icon: "",
            type: 'primary',
        }
    })
    function doSync(item){
        item.loading = true;
        item.type    = 'primary';
        item.icon    = '';
        app.ajax2({url: item.url}).then(_ => {item.type='success'; item.icon='check'; item.loading = false}, _ => {item.loading = false; item.icon='close'; item.type='danger'});
    }

    $(function () {
        //产品同步
        $("#productOn").on("click", function () {
            var param = $("#product").val();
            $.ajax({
                method: 'POST',
                url: '/product/sync/againPushProduct',
                data: {
                    productIds: param
                },
                success: function (result) {
                    alert("完成,自行检查!")
                }
            });
        })
        //产品sku同步
        $("#productSKuOn").on("click", function () {
            var param = $("#productSKu").val();
            $.ajax({
                method: 'POST',
                url: '/product/sync/againPushProductSku',
                data: {
                    productIds: param
                },
                success: function (result) {
                    alert("完成,自行检查!")
                }
            });
        })
        //内部名修改
        $("#updateById").on("click", function () {
            var name = $("#innername").val();
            var id = $("#productId").val();
            $.ajax({
                method: 'POST',
                url: '/product/product/product/updateById',
                data: {
                    innerName: name,
                    id: id
                },
                success: function (result) {
                    alert("内部名修改完成,自行检查!")
                }
            });
        })
        //排重邮件重发
        $("#checkEmail").on("click", function () {
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            $.ajax({
                method: 'POST',
                url: '/product/email/again/checkEmail',
                data: {
                    startTime: startTime,
                    endTime: endTime
                },
                success: function (result) {
                    alert("排重邮件重发完成,自行检查!")
                }
            });
        })
        //预警邮件重发
        $("#warningEmail").on("click", function () {
            $.ajax({
                method: 'POST',
                url: '/product/email/again/warningEmail',
                success: function (result) {
                    alert("预警邮件重发完成,自行检查!")
                }
            });
        })
        //消档邮件重发
        $("#disappearedEmail").on("click", function () {
            $.ajax({
                method: 'POST',
                url: '/product/email/again/disappearedEmail',
                success: function (result) {
                    alert("消档邮件重发完成,自行检查!")
                }
            });
        })
        //待下架下架成功
        $("#waitoffsaleEmail").on("click", function () {
            $.ajax({
                method: 'POST',
                url: '/product/email/again/waitoffsaleEmail',
                success: function (result) {
                    alert("待下架下架重发完成,自行检查!")
                }
            });
        })
    })

    </script>
</js>   