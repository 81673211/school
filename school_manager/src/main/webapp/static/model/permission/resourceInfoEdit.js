/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2) {
		$(function() {
			var model = vm_resourceInfoEdit;
			$("#resourceInfoForm").validate({
            	rules: {
            		resName:{
            			required:true,
            			remote: getCtx() + "/permission/resourceInfo/checkResNameIsNotExist?resId="+$("#id").val()
            		},
            		resUrl:{
                        required:true,
                        remote: getCtx() + "/permission/resourceInfo/checkResUrlIsNotExist?resId="+$("#id").val()
                    },
                    parentResId:{
                        required:true,
                        digits:true
                    },
                    menuId:{
                    	required:true,
                    	digits:true
                    }
                },
                messages:{
                	resName:{
                		required:'请填写资源名称',
                		remote:'资源名称已存在'
                	},
                	resUrl:{
                		required:'请填写资源地址',
                		remote:'资源地址已存在'
                	},
                	parentResId:{
                		required:'请选择父级资源',
                		digits:'请填写数字'
                	},
                	menuId:{
                		required:'请选择状态',
                		digits:'请填写数字'
                	}
                }
            });
			model.methods.validAndSubmit = function(callback) {
				if ($("#resourceInfoForm").valid()) {
					$("#resourceInfoForm").ajaxSubmit(function(json){
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
