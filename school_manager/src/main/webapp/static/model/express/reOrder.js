/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2) {
		$(function() {
			var model = vm_reOrder;
			$("#reOrderForm").validate({
            	rules: {
                    refundAmt:{
                    	required:true
                    }
                },
                messages:{
                	refundAmt:{
                		required:'请填写补单金额'
                	}
                }
            });
			model.methods.validAndSubmit = function(callback) {
				if ($("#reOrderForm").valid()) {
					$("#reOrderForm").ajaxSubmit(function(json){
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
