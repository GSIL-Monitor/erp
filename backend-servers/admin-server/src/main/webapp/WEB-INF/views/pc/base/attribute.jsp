<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<body>
     <template scope='app' slot='controls'>
         <H1>属性列表</H1>
     </template>
	 <%@ include file="/WEB-INF/views/partial/product_attr.jsp" %>
</body>
<js>
    <script>
        var C = window.layoutData.controls;
        C.attrtable = {
            attrList: [],
            attrValueList: [],
            attrPanel: {
                tabName: 'attrList',
            },
            attrName: "",
            loading: false,
            searchParams: {
                title: '',
                status: null,
                bindIs: null,
            },
            add: {

            },
            edit: {

            },
            search: {},
            pageSize: 20,
            page: 1,
            total: 1,
        };
        C.attrValueTable = {
            data: [],
            search: {},
            searchParams: {
                title: '',
                status: null,
            },
            pageSize: 20,
            page: 1,
            total: 1,
        };
        $(function(){
            app.service['attr/init'](window.layoutData.controls.attrtable);
            app.service["attrvalue/init"](app.controls.attrValueTable);
            app.controls.app.ajax.lang.find().then(x => x.data.item).then(x => x.map(x => ({key: x.name, value: x.langCode}))).then(x => app.controls.attrtable.langs = x);
        });
    </script>
</js>