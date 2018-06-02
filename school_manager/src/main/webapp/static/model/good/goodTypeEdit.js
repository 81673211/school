/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2) {
		$(function() {
			var model = vm_goodTypeEdit;
			$("#goodTypeForm").validate({
            	rules: {
            		typeName:{
            			required:true
            		},
            		pid:{
                        digits:true
                    },
                    sort:{
                        required:true,
                        digits:true
                    },
                    status:{
                    	required:true
                    }
                },
                messages:{
                	typeName:{
                		required:'请填写类型名称'
                	},
                	pid:{
                		digits:'请填写数字'
                	},
                	sort:{
                		required:'请填写节点排序',
                		digits:'请填写数字'
                	},
                	status:{
                		required:'请选择状态'
                	}
                }
            });
			model.methods.validAndSubmit = function(callback) {
				if ($("#goodTypeForm").valid()) {
					$("#goodTypeForm").ajaxSubmit(function(json){
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
