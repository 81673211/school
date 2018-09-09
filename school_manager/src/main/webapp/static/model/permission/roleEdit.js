/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2', 'ztree_core', 'ztree_excheck' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2, ztree_core, ztree_excheck) {
		$(function() {
			var model = vm_roleEdit;
			$("#roleForm").validate({
            	rules: {
            		roleName:{
            			required:true,
            			remote: getCtx() + "/permission/role/checkIsNotExist?roleId="+$("#id").val()
            		}
                },
                messages:{
                	roleName:{
                		required:'请填写角色名称',
                		remote:'角色名称已存在'
                	}
                }
            });
			model.methods.validAndSubmit = function(callback) {
				if ($("#roleForm").valid()) {
					if(!getLeftChecked()){
                        return false;
                    }
					$("#roleForm").ajaxSubmit(function(json){
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
			var resIds = [];
			
			function getLeftChecked(){
			    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			    var nodes = zTree.getCheckedNodes(true);
			    for(var i=0; i<nodes.length; i++) {
			        if(nodes[i].isParent==false&&nodes[i].checked==true){
			            resIds.push(nodes[i].resId);
			        }
			    }
			    if(resIds.length==0){
			       alert('请至少选择一个资源权限授予角色');
			       return false;
			    }else{
			        $('#resIds').val(resIds.toString());
			        return true;
			    }
			}
			
			var setting = {
		        check: {
		            enable: true,
		            chkStyle: "checkbox",
		            chkboxType: { "Y": "ps", "N": "ps" }
		        },
				data: {
					simpleData: {
						enable: true
					}
				}
			};

			var zNodes = model.zNodes;
			
			$(document).ready(function(){
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			});
		});

	});
});
