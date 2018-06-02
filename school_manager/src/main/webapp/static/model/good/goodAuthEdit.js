/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2', 'ueditor' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2) {
		$(function() {
			var ue = UE.getEditor('container',{readonly:true});
			var model = vm_goodAuthEdit;
			
			$('#labels').select2({  
                placeholder: "请选择标签",  
                tags:true,  
                createTag:function (decorated, params) {  
                    return null;  
                },  
                width:'400px'
            });
			$("#labels").val(model.data.goodLabels).trigger("change");
			
			$("#goodAuthForm").validate({
            	rules: {
            		noPassReason:{
                    	required:true
                    }
                    
                },
                messages:{
                	noPassReason:{
                		required:'请填写意见'
                	}
                }
            });
			model.methods.validAndSubmit = function(status,callback) {
				// 给status赋值
				console.log(status);
				if(status == 4){
					str = "通过";
                }else{
                	str = "不通过";
                }
				$($("#goodAuthForm input[name='status']")[0]).val(status);
				
				if ($("#goodAuthForm").valid()) {
					layer.confirm('确认'+str+'吗？',function(){
						$("#goodAuthForm").ajaxSubmit(function(json){
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
					});
				}
			};
			
			
		});

	});
});
