// 如果是删除所有参数，则直接根据?来分割成数组再取第一个
// var operation_url = location.href;
// location.href = operation_url.split('?')[0];


// 图片404全局处理
document.addEventListener("error", function(e){
    var elem = e.target;
    if(elem.tagName.toLowerCase() === 'img'){
        // 如果引发error事件的元素是img元素，就进行处理
        //elem.style.display="none";      //隐藏该图片
        elem.src = "images/img404.png";  //或者替换为其他图片
    }
}, true /*指定事件处理函数在捕获阶段执行*/);

// 全局图片放大功能
document.addEventListener("click", function(e){
    var elem = e.target;
    if($(elem).hasClass("clickamplify_img")){
        $("#amplify_img_mask").show();
        $("#amplify_img").attr("src",$(elem).attr("src"));
    }
}, true /*指定事件处理函数在捕获阶段执行*/);

$("#amplify_img_mask").click(function(){
    $(this).hide();
});


var admin_host, current_view,toogle_status = true,currentUserInfo;

function tooglesidebar(that){
        toogle_status = !toogle_status;
        if (toogle_status) {
                $(that).text("隐藏侧边栏");
                $("#sidebar_wrap").show();
                $("#view_container").css("left","200px");
        }else{
                $(that).text("显示侧边栏");
                $("#sidebar_wrap").hide();
                $("#view_container").css("left","0px");
        }
}


function state_response(res,elem) {
    switch (res.code) {
        case "LOGIN":
            //    var back = "/front";
            // back = encodeURI(back);
            // //console.log(back);
            // location.href= res.item+"?backUrl="+back;
            location.href = res.item;
            break;
        case "NOTICE":
            layer.msg(res.desc, {icon: 3});
            break;
        case "FAIL":
            if(elem != undefined){
                $(elem.elem).removeClass("layui-disabled");
                $(elem.elem).attr("disabled", false);
            }
            layer.msg(res.desc, {icon: 3});
            break;
        default:

    }
}

function get_each_nav_list(idx) {
    $.ajax({
        url: "/admin/subMenu?keyword=" + idx,
        dataType: 'json',
        type: 'GET',
        success: function (res) {
            console.log("请求二级菜单", res);
            state_response(res);
            var _navlist = res.item;
            //console.log("数据源",_navlist);
            $('#left-navwrap').html(template('left-navtpl', _navlist));
            //JavaScript代码区域
            layui.use('element', function () {
                var element = layui.element;
                element.init();
            });
            load_firstbtnpage();
        }
    });
}

// $(function(){  
//     // 设置jQuery Ajax全局的参数  
//     $.ajaxSetup({  
//         error: function(jqXHR, textStatus, errorThrown){  
//             switch (jqXHR.status){  
//                 case(500):  
//                     alert("服务器系统内部错误");  
//                     break;  
//                 case(401):  
// 			        var _location = xhr.getResponseHeader("Location");
// 			        if(_location) {
// 			            location.assign(_location);
// 			        }				   
//                     break;  
//                 case(403):  
//                     alert("无权限执行此操作");  
//                     break;  
//                 case(408):  
//                     alert("请求超时");  
//                     break;  
//                 default:  
//                     alert("未知错误");  
//             }  
//         } 
//     });  
// });  


$.ajax({
    url: "/admin/currentUser",
    dataType: 'json',
    type: 'POST',
    success: function (res) {
        console.log("请求用户信息", res);
        state_response(res);
        if (res.code === 'OK') currentUserInfo = res.item;
        console.log("当前用户信息", currentUserInfo);
        $("#user-info").html(res.item.deptName + "&nbsp;/&nbsp;" + res.item.lastName);
    }
});

$.ajax({
    url: "/admin/topMenu",
    dataType: 'json',
    type: 'POST',
    success: function (res) {
        console.log("请求顶级菜单", res);
        state_response(res);
        $.each(res.item, function (i, n) {
            switch (n.keyword) {
                case "admin":
                    admin_host = n.httpPrefix;
                    break;
                case "product":
                    product_host = n.httpPrefix;
                    break;
                case "orders":
                    orders_host = n.httpPrefix;
                    break;
                case "purchase":
                    purchase_host = n.httpPrefix;
                    break;
                case "crm":
                    crm_host = n.httpPrefix;
                    break;
                case "product_img":
                    product_img_host = n.httpPrefix;
                default:

            }
        });
        var topmenu = res;
        $('#top-navwrap').append(template('top-navtpl', topmenu));
        get_each_nav_list($('#top-navwrap').find(".top-navbtn").eq(0).attr("data-link"));
        $(".top-navbtn").click(function () {
            var _linkto = $(this).attr("data-link");
            //console.log(_linkto);
            get_each_nav_list(_linkto);
        });
        $("body").on('click', '.link_btn', function () {
            var _url = "view/" + $(this).attr("data-url") + ".html?ran=" + Math.random();
            var _id = $(this).attr("data-id");
            //console.log(_url);
            loadfirst_orclick(_url, _id);
        })
    }
});

function load_firstbtnpage() {
    var _url = "view/" + $("#left-navwrap").find(".link_btn").eq(0).attr("data-url") + ".html?ran=" + Math.random();
    var _id = $("#left-navwrap").find(".link_btn").eq(0).attr("data-id");
    console.log("第一个按钮", _url);
    loadfirst_orclick(_url, _id);
}

function loadfirst_orclick(_url, _id) {
    current_view = _url;
    $.ajax({
        url: "/admin/element/findElementPermission?menuId=" + _id,
        dataType: 'json',
        type: 'GET',
        success: function (res) {
            console.log("请求视图操作权限", res);
            state_response(res);
            $.each(res.item, function (i, n) {
                window[n.keyword] = n.checked;
                console.log(n.keyword + "值是" + window[n.keyword]);
            });
            $("#view_container").load(_url);
        }
    });
}


//config的设置是全局的
layui.config({
    base: 'lib/treeselect/' //假设这是test.js所在的目录
}).extend({ //设定模块别名
    test: 'treeselect' //如果test.js是在根目录，也可以不用设定别名
    , test1: '' //相对于上述base目录的子目录
});


// 自动完成组件start
// function auto_val(name_item,display_item) {
//     $('[name='+name_item+']').next(".auto_matchinput").val(display_item);
//     $.ajax({
//         type: 'GET',
//         url: "/admin/user/userAutoComplement?search=" + display_item,
//         dataType: 'json',
//         success: function (res) {
//             $("[name="+name"]").val();
//         }
//     });
// }

function input_match(that) {
    console.log($(that).val());
    $(that).removeClass("inserted");
    $(that).siblings(".auto_matchwrap").show();
    $.ajax({
        type: 'GET',
        url: "/admin/user/userAutoComplement?search=" + $(that).val(),
        dataType: 'json',
        success: function (res) {
            console.log("职位模糊查询", res);
            state_response(res);
            $(that).siblings(".auto_matchwrap").html(template('position-nametpl', res));
        }
    });
    $(that).siblings(".auto_complateval").val("");
}


// 自动完成组件start
function input_match2(that,id) {
    debugger
    console.log($(that).val());
    $("#"+id).show();
    $.ajax({
        type: 'GET',
        url: "/admin/user/userAutoComplement?search=" + $(that).val(),
        dataType: 'json',
        success: function (res) {
            console.log("职位模糊查询", res);
            state_response(res);
            $("#"+id).html(template('position-nametpl', res));
        }
    });
    $(that).siblings(".auto_complateval").val("");
}


function blur_event(that) {
    if (!$(that).hasClass("inserted")) {
            $(that).val("");
    }
}



function insert_val(that, event) {
    $(that).parent(".auto_matchwrap").siblings(".auto_matchinput").val($(that).text()).addClass("inserted");
    $(that).parent(".auto_matchwrap").siblings(".auto_complateval").val($(that).attr("data-id"));
    $(".auto_matchwrap").hide();
    event.stopPropagation();
}

$("body").click(function () {
    $(".auto_matchwrap").hide();
});
// 自动完成组件end

// 树形下拉组件返回id start
function tree_formDept(selectId,returnedParam) {
    layui.use('form', function () {
        var form = layui.form;
        $.ajax({
            type: 'GET',
            url: '/admin/department/list',
            dataType: 'json',
            success: function (res) {
                console.log("查询部门树", res);
                state_response(res);
                res.returnedParam = returnedParam;
                $('#'+selectId).append(template('select_deptTree_tpl', res));
                form.render('select'); //刷新select选择框渲染
            }
        });
    });
}


// 树形下拉组件返回id end
// 树形下拉组件返回部门名称 end


/**
*    仓库下拉数据获取
* @param selectIds  select框的id 命名需唯一，加各自模块前缀区分
* @param data   null默认20条  Integer zoneId, Integer type,Integer page ,Integer limit
*/
function store_wmsId_option(selectId, data,callback) {

    console.log('selectId:' + selectId);
    console.log('data:' + data);

    layui.use('form', function () {
        var form = layui.form;
        $.ajax({
            type: 'POST',
            url: "/store/findList",
            dataType: 'json',
            data: data,
            success: function (res) {
                console.log("请求-仓库下拉框数据", res);
                state_response(res);
                $('#' + selectId).append(template('wmsId-option-tpl', res));
                if(callback)
                    callback();
                form.render('select'); //刷新select选择框渲染
            }
        });

    });
}



/**
 *    物流方式下拉数据获取
 * @param selectIds  select框的id 命名需唯一，加各自模块前缀区分
 * @param callback  回调函数
 */
function tms_shippingWayId_option(selectId,callback) {
    console.log('selectId:' + selectId);

    layui.use('form', function () {
        var form = layui.form;
        $.ajax({
            type: 'POST',
            url: "/tms/base/shippingway/query",
            data: {
                limit:100000,
                shippingWayName:null
            },
            dataType: 'json',
            success: function (res) {
                console.log("物流方式下拉框数据", res);
                state_response(res);
                $("#"+ selectId).append(template('option-shippingWayId-tpl', res));
                if(callback)
                    callback();
                form.render();
            }
        });

    });
}

/**
 *  区域下拉菜单获取
 * @param selectId  select框的id 命名需唯一，加各自模块前缀区分
 * @param outParam  出参，code，id 二选一，不填默认id
 */
function zone_select(selectId,outParam,callback) {

    console.log('selectId:'+ selectId);
    console.log('outParam'+outParam);
    layui.use('form', function () {
        var form = layui.form;
        $.ajax({
            type: 'GET',
            url: '/product/base/zone/findAll',
            dataType: 'json',
            success: function (res) {
                console.log("查询区域", res);
                state_response(res);
                res.outParam = outParam;
                $('#'+selectId).append(template('select_zone_tpl', res));
                if (callback)
                    callback();
                form.render('select'); //刷新select选择框渲染
            }

        });
    });

}

//下拉多选组件start

var multiselectmodal_index;
function start_multiselect(url,that,display_item,val_item){
        $.get('view/component/multiselectmodal.html', function (str) {
            multiselectmodal_index = layer.open({
                type: 1
                ,title: '多选操作'
                , content: str //注意，如果str是object，那么需要字符拼接。
                , maxmin: true
                , area: ['60%',"60%"]
                ,success: function(layero, index){
                    //console.log(layero, index);
                    multiselect_modalrender(url,that,display_item,val_item);
                }
            });
        });
}


//下拉多选组件end

//树形下拉选择组件start
var treeselectmodal_index;
function start_treeselect(url,that,display_item,val_item){
        $.get('view/component/treeselect_modal.html', function (str) {
            treeselectmodal_index = layer.open({
                type: 1
                ,title: '树形选择'
                , content: str //注意，如果str是object，那么需要字符拼接。
                , maxmin: true
                , area: ['60%',"60%"]
                ,success: function(layero, index){
                    //console.log(layero, index);
                    treeselect_modalrender(url,that,display_item,val_item);
                }
            });
        });
}

function empty_inputfunc(that){
        $(that).siblings(".component_input").val("").attr("data-id","");
}

//树形下拉选择组件end

//树形多选组件start
var treemultiselectmodal_index;
function start_treemultiselect(url,that,add_api,del_api){
        $.get('view/component/treemultiselectmodal.html', function (str) {
            treemultiselectmodal_index = layer.open({
                type: 1
                ,title: '树形多选'
                , content: str //注意，如果str是object，那么需要字符拼接。
                , maxmin: true
                , area: ['40%',"60%"]
                ,success: function(layero, index){
                    //console.log(layero, index);
                    treemultiselect_modalrender(url,that,add_api,del_api);
                }
            });
        });
}


//树形多选组件end


// 多语言操作组件start
var multi_lang_operation_modalidx;
function multi_lang_operation(a,b,c,d,e,f){
        $.get('view/component/multi_lang_operation.html', function (str) {
            multi_lang_operation_modalidx = layer.open({
                type: 1
                ,title: '多语言'
                , content: str //注意，如果str是object，那么需要字符拼接。
                , maxmin: true
                , area: ['25%',"40%"]
                ,success: function(layero, index){
                    //console.log(layero, index);
                    multi_lang_operation_modalrender(a,b,c,d,e,f);
                }
            });
        });
}
// 多语言操作组件end

//产品中心多语言
function product_lang_fun(language_api, add_api, dele_api, update_api, obj_data_id, obj_data){
    $.get('view/component/product_language.html', function (str) {
        multi_lang_operation_modalidx = layer.open({
            type: 1
            ,title: '多语言'
            , content: str //注意，如果str是object，那么需要字符拼接。
            , maxmin: true
            , area: ['25%',"40%"]
            ,success: function(layero, index){
                product_language_modalrender(a,b,c,d,e,f);
            }
        });
    });
}


//artTemplate模板内部取外部变量
template.defaults.imports.$window = window;

//防止用户重复提交
function preventRepeat(elem) {
    $(elem.elem).addClass("layui-disabled");
    $(elem.elem).attr("disabled", true);
}


// 状态及历史表格组件start
function  loadFsmHistory(table,tableId,url,param) {
    table.render({
        elem: '#'+tableId,
        method: 'post',
        url:  url,
        where: param,
        page: true,
        even:true,
        limit: 10
        , request: {
            pageName: 'page' //页码的参数名称，默认：page
            ,limitName: 'limit' //每页数据量的参数名，默认：limit
        } ,
        response: {
            statusName: 'code' //数据状态的字段名称，默认：code
            , statusCode: 'OK' //成功的状态码，默认：0
            , msgName: 'desc' //状态信息的字段名称，默认：msg
            , countName: 'total' //数据总数的字段名称，默认：count
            , dataName: 'item' //数据列表的字段名称，默认：data
        },cols: [[ //表头
            {field: 'srcStateDisplay', title: '原始状态',width:"15%"}
            ,{field: 'dstStateDisplay', title: '目标状态',width:"15%"}
            ,{field: 'optUid', title: '用户',width:"15%"}
            , {title: '详细',templet: '#memoTpl'}
            ,{field: 'createAt', title: '时间',width:"20%"}
        ]]
    });
}

// 产品中心状态机及历史表格组件
function productCenter_fsmHistory(id, title, fsm){
    layui.layer.open({
        type: 1,
        title: "[<span class='product-span-font'>"+ title +"</span>]的历史记录",
        content: template('product_fsmHistory', new Object()),
        area: ['80%', '60%'],
        success: function (obj) {
             layui.table.render({
                elem: '#product_fsmHistory_tableId',
                url: "/product/fsmHistory/find?fsmName=" + fsm + "&objectId=" + id,
                even: true,     //开启隔行背景
                page: false,    //关闭分页
                cols: [[ //表头
                    {field: 'optUid', title: '操作人' },
                    {field: '', title: '源状态', templet:'#productCenter_srcStateTpl'},
                    {field: '', title: '事件' , templet:'#productCenter_eventTpl'},
                    {field: '', title: '目标状态', templet:'#productCenter_dstStateTpl' },
                    {field: 'createAt', title: '时间' },
                    {field: 'memo', title: '备注' }
                ]],
                response: {
                    statusName: 'code', //数据状态的字段名称，默认：code
                    statusCode: 'OK', //成功的状态码，默认：0
                    msgName: 'desc', //状态信息的字段名称，默认：msg
                    countName: 'total', //数据总数的字段名称，默认：count
                    dataName: 'item' //数据列表的字段名称，默认：data
                }
            });
        }
    });
}
layui.use('form', function(){
        var form = layui.form;
        form.verify({
            codeCheck:function(value, item){ //value：表单的值、item：表单的DOM对象
                if(!new RegExp("^\\w+$").test(value)){
                    return "只能为数字英文与下划线";
                }
            },
            positiveNumber:[
                /^[0-9]*[1-9][0-9]*$/,
                "只能为正整数"
            ]
        });
});

/**
 * 创建售后申请单
 * @param {*} id 订单流水号
 * @param {*} type 售后类型 reject：拒收， returned：退货
 * @param {*} source 页面来源 ordersquestion 问题件
 */
function createAfterSaleApply(id, type, source, iTitle) {
    rmaOrderId = id;
    rmaType =  type;
    rmaSource = source;
    rmaShowType = 'apply';


    if (!iTitle)
        iTitle = '新建退货单据';

    $.get('view/orders/manage/component/rma_comp.html', function (str) {
        rmaIndex = layer.open({
            type: 1
            , title: iTitle
            , content: str //注意，如果str是object，那么需要字符拼接。
            , maxmin: true
            , area: ['75%','85%']
            ,success: function(layero, index){
                layero.find('.layui-btn').css('text-align', 'right');
            }
        });
    });
}

/**
 * 上传图片 ctrl+v 上传截图 组件
 */
function parse_ctrlV(event) {
    console.log(event);
    var isChrome = false;
    if ( event.clipboardData || event.originalEvent ) {
        //not for ie11   某些chrome版本使用的是event.originalEvent
        var clipboardData = (event.clipboardData || event.originalEvent.clipboardData);
        console.log(clipboardData);
        if ( clipboardData.items ) {
            // for chrome
            var    items = clipboardData.items,
                len = items.length,
                blob = null;
            isChrome = true;
            if(len>1){
                return;
            }

            //items.length比较有意思，初步判断是根据mime类型来的，即有几种mime类型，长度就是几（待验证）
            //如果粘贴纯文本，那么len=1，如果粘贴网页图片，len=2, items[0].type = 'text/plain', items[1].type = 'image/*'
            //如果使用截图工具粘贴图片，len=1, items[0].type = 'image/png'
            //如果粘贴纯文本+HTML，len=2, items[0].type = 'text/plain', items[1].type = 'text/html'
            // console.log('len:' + len);
            // console.log(items[0]);
            // console.log(items[1]);
            // console.log( 'items[0] kind:', items[0].kind );
            // console.log( 'items[0] MIME type:', items[0].type );
            // console.log( 'items[1] kind:', items[1].kind );
            // console.log( 'items[1] MIME type:', items[1].type );

            //阻止默认行为即不让剪贴板内容在div中显示出来
            // event.preventDefault();
            //在items里找粘贴的image,据上面分析,需要循环
            for (var i = 0; i < len; i++) {
                if (items[i].type.indexOf("image") !== -1) {
                    // console.log(items[i]);
                    // console.log( typeof (items[i]));

                    //getAsFile()  此方法只是living standard  firefox ie11 并不支持
                    blob = items[i].getAsFile();
                }
            }
            if ( blob !== null ) {
                var reader = new FileReader();
                reader.onload = function (event) {
                    // event.target.result 即为图片的Base64编码字符串
                    var base64_str = event.target.result;
                    console.log("base64_str",event);
                    //可以在这里写上传逻辑 直接将base64编码的字符串上传（可以尝试传入blob对象，看看后台程序能否解析）
                    uploadImgFromPaste(blob, 'paste', isChrome);
                };
                reader.readAsDataURL(blob);
            }
        }
    }
}

function uploadImgFromPaste (file, type, isChrome) {
    var formData = new FormData();
    formData.append('file', file);
    formData.append('type', 'productNew');

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/product/commons/upload');
    xhr.onload = function () {
        if ( xhr.readyState === 4 ) {
            if ( xhr.status === 200 ) {
                var res = JSON.parse( xhr.responseText );
                if ( isChrome ) {
                    res.mainImageUrl = res.item;
                    res.productImgPrefix = window.product_img_host;
                    $('#new_add_imageUpload').html(template('newAdd_imageUpLoadTpl', res));
                }
            } else {
                console.log( xhr.statusText );
            }
        };
    };
    xhr.onerror = function (e) {
        console.log( xhr.statusText );
    }
    xhr.send(formData);
}