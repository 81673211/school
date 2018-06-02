/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
	            // , 'qrcode', 'utf' 二维码加上这两个
			'methods', 'zeo', 'form', 'Hui', 'select2', 'ueditor', 'lightbox2'], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2,lightbox2) {
		$(function() {
			var ue = UE.getEditor('container',{readonly:true});
			var model = vm_goodInfoDetail;
			
			$('#labels').select2({  
                placeholder: "请选择标签",  
                tags:true,  
                createTag:function (decorated, params) {  
                    return null;  
                },  
                width:'400px'
            });
            
            $("#labels").val(model.data.goodLabels).trigger("change");
//            $("#qrCode").qrcode({
//                render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
//                text : "http://www.sohu.com/",    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
//                width : "200",               //二维码的宽度
//                height : "200",              //二维码的高度
//                background : "#ffffff",       //二维码的后景色
//                foreground : "#000000",        //二维码的前景色
//                src: getCtx()+'/static/image/qrcode_logo.jpg'       //二维码中间的图片
//            });
		});
	});
});
