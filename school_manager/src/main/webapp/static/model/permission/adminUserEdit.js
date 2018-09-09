/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2) {
		$(function() {
			var model = vm_adminUserEdit;
			$("#adminUserForm").validate({
            	rules: {
            		loginName:{
            			required:true,
            			remote: getCtx() + "/permission/adminUser/checkIsNotExist?userId"+$("#id").val()
            		},
            		adminName:{
                        required:true
                    },
                    adminPhone:{
                        required:true,
                        isMobile:true
                    },
                    roleId:{
                    	required:true
                    },
                    status:{
                    	required:true
                    }
                },
                messages:{
                	loginName:{
                		required:'请填写登陆账号',
                		remote:'账号已经存在'
                	},
                	adminName:{
                		required:'请填写用户名称'
                	},
                	adminPhone:{
                		required:'请填写手机号',
                		isMobile:'手机号不正确'
                	},
                	roleId:{
                    	required:'请选择角色'
                    },
                	status:{
                		required:'请选择状态'
                	}
                }
            });
			model.methods.validAndSubmit = function(callback) {
				if ($("#adminUserForm").valid()) {
					$("#adminUserForm").ajaxSubmit(function(json){
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
