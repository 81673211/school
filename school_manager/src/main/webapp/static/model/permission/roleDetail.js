/**
 * Created by lx on 2016/10/9.
 */
requirejs([ 'requirejs.config' ], function() {
	requirejs([ "jquery", 'icheck', 'Autocom', 'WdatePicker', 'validation',
			'methods', 'zeo', 'form', 'Hui', 'select2', 'ztree_core', 'ztree_excheck' ], function(jquery,
			icheck, Autocom, WdatePicker, validation, methods, zeo, Hui,
			select2, ztree_core, ztree_excheck) {
		$(function() {
			var model = vm_roleDetail;
			
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
