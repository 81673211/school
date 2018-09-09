//require配置文件
requirejs.config({
    baseUrl: '../../../'+getCtx(),
    urlArgs: "rand=" + (new Date()).getTime(),
    packages: [
        {
            name: 'echarts',
            location: 'static/lib/echarts/3',
            main: 'echarts'
        },
        {
            name: 'zrender',
            location: 'static/lib/zrender/src', // zrender与echarts在同一级目录
            main: 'zrender'
        }
    ],
    paths: {
        'jquery': getCtx()+'/static/lib/jquery/1.9.1/jquery.min',
        'Hui': getCtx()+'/static/h-ui/js/H-ui',
        'Huiadmin': getCtx()+'/static/h-ui.admin/js/H-ui.admin',
        'jqueryUi': getCtx()+'/static/lib/jquery/1.9.1/jquery-ui.min',
        'avalon':getCtx()+'/static/lib/avalon/2.0/avalon',
        'validation': getCtx()+'/static/lib/jquery.validation/1.14.0/jquery.validate',
        'methods': getCtx()+'/static/lib/jquery.validation/1.14.0/validate-methods',
        'messages_zhs': getCtx()+'/static/lib/jquery.validation/1.14.0/messages_zh',
        'form':getCtx()+'/static/lib/jquery.validation/1.14.0/jquery.form',
        'zeo': getCtx()+'/static/lib/jquery.validation/1.14.0/zeo',
        'layer': getCtx()+'/static/lib/layer/2.4/layer',
        'dataTables':getCtx()+'/static/lib/datatables/1.10.0/jquery.dataTables',
        'icheck':getCtx()+'/static/lib/icheck/jquery.icheck.min',
        'WdatePicker':getCtx()+'/static/lib/My97DatePicker/WdatePicker',
        'Autocom':getCtx()+'/static/lib/jquery.autocomplete/jquery.autocomplete',
        'ajaxFileUpload':getCtx()+'/static/lib/ajaxfileupload/ajaxfileupload',
        'ztree_core':getCtx()+'/static/lib/zTree/v3/js/jquery.ztree.core-3.5.min',
        'ztree_excheck':getCtx()+'/static/lib/zTree/v3/js/jquery.ztree.excheck-3.5.min',
        'select2':getCtx()+'/static/lib/select2/select2.full',
        'ueditor':getCtx()+'/static/lib/ueditor/1.4.3/ueditor.all',
        'lightbox2':getCtx()+'/static/lib/lightbox2/2.8.1/js/lightbox',
        'qrcode':getCtx()+'/static/lib/jquery.qrcode/jquery.qrcode',
        'utf':getCtx()+'/static/lib/jquery.qrcode/utf'
    },
    waitSeconds: 10,
    map: {
        '*': {
            'css': getCtx()+'/static/lib/requirejs/css/css.js' //require 调用css插件
        }
    },
    shim: {
        layer: {
            deps: ['css!'+getCtx()+'/static/lib/layer/2.4/skin/layer.css', 'jquery'],
            exports: 'layer'
        },
        jqueryUi: ['css!'+getCtx()+'/static/h-ui.admin/css/common/smartadmin-production.css', 'jquery'],
        Hui:['jquery'],
        Huiadmin:['jquery','Hui'],
        dataTables:['jquery'],
        icheck:['jquery'],
        validation:['jquery'],
        methods:['validation'],
        messages_zhs:['validation'],
        Autocom:['jquery'],
        form:['jquery'],
        ajaxFileUpload:['jquery'],
        ztree_core:['css!'+getCtx()+'/static/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css','jquery'],
        ztree_excheck:['css!'+getCtx()+'/static/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css','jquery','ztree_core'],
        select2:['css!'+getCtx()+'/static/lib/select2/select2.min.css', 'jquery'],
        ueditor: {
            deps: [getCtx()+'/static/lib/ueditor/1.4.3/third-party/zeroclipboard/ZeroClipboard.js', getCtx()+'/static/lib/ueditor/1.4.3/ueditor.config.js'],
            exports: 'UE',
            init:function(ZeroClipboard){
                //导出到全局变量，供ueditor使用
                window.ZeroClipboard = ZeroClipboard;
            }
        },
        lightbox2:['css!'+getCtx()+'/static/lib/lightbox2/2.8.1/css/lightbox.css', 'jquery']
    }

});

//__proto__
require(('__proto__' in {} ? ['jquery'] : ['ie']));
// 为了可以预览，直接在这里requirejs，而不是通过include
requirejs(["layer","Hui","Huiadmin"], function (layer,Hui,Huiadmin) {
    //avalon.require = requirejs
  //  Hui.require = requirejs
    // 重写模板加载器，改为用text插件加载
//require([ 'ZeroClipboard',  'ueditor'], function (ZeroClipboard) {  
//     // 初始化ZeroClipboard，此处一定要有，不然会报错  
//     window['ZeroClipboard'] = ZeroClipboard;  
//});  
});