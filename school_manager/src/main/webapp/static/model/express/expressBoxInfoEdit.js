/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2) {
		$(function() {
			var model = vm_expressBoxInfoEdit;
			$("#expressBoxInfoForm").validate({
            	rules: {
                    expressCode:{
                    	required:true
                    },
                    boxGroup:{
                    	required:true
                    },
                    boxNo:{
                    	required:true
                    },
                    status:{
                    	required:true
                    }
                },
                messages:{
                	expressCode:{
                    	required:'请填写快递单号'
                    },
                    boxGroup:{
                    	required:'请填写快递柜组名'
                    },
                    boxNo:{
                    	required:'请填写快递柜编号'
                    },
                    status:{
                    	required:'请选择状态'
                    }
                }
            });
			model.methods.validAndSubmit = function(callback) {
				if ($("#expressBoxInfoForm").valid()) {
					$("#expressBoxInfoForm").ajaxSubmit(function(json){
						if(json.ok){
                			layer.msg(json.msg, {
          						icon : 1,
          						time : 2000
          					}, function() {
          						if(typeof callback == "function"){
          							callback();
          						}
          						var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
          		                parent.layer.close(index); //再执行关闭
          					});
                		 } else {
                			 layer.msg(json.msg, {
                				 icon : 2,
                				 time : 2000
                			 });
                		 }
					});
				}
			};
		});

	});
});
