/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2) {
		$(function() {
			var model = vm_expressCompanyEdit;
			$("#expressCompanyForm").validate({
            	rules: {
            		name:{
            			required:true
            		},
            		code:{
                        required:true
                    },
                    phone:{
                        required:true,
                        isPhone:true
                    },
                    addr:{
                    	required:true
                    }
                },
                messages:{
                	name:{
                		required:'请填写名称'
                	},
                	code:{
                		required:'请填写编码'
                	},
                	phone:{
                		required:'请填写联系电话',
                		isPhone:'电话格式不正确'
                	},
                	addr:{
                		required:'请填写地址'
                	}
                }
            });
			model.methods.validAndSubmit = function(callback) {
				if ($("#expressCompanyForm").valid()) {
					$("#expressCompanyForm").ajaxSubmit(function(json){
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
