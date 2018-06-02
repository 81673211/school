requirejs(['requirejs.config'], function () {
    requirejs(["jquery","Hui","layer",'dataTables','icheck','form','validation'], function (jquery,Hui,layer,dataTables,icheck,validation) {
        $(function(){
            $.fn.serializeObject = function () {
                var o = {};
                var a = this.serializeArray();
                $.each(a, function () {
                    if (o[this.name] !== undefined) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                        o[this.name] = this.value || '';
                    }
                });
                return o;
            };


           var model=vm_goodBrandManage;
           /*
            * 顶部 搜索
            */
            function queryVal(){
            	$('#searchForm').submit();
            } 
           
             model.methods.query=function(){
                 queryVal();
             };
            
           /*
            * 查看 
            */
             model.methods.detail = function(id,detailUrl){
         		layer.open({
 					type: 2,
 					closeBtn: 1,
 					fix:true,
 					area: ['450px', '280px'],
 					title:'商品品牌详情',
 					shadeClose: true,
 					maxmin: false,
 					content: getCtx() + detailUrl + '?id='+id
 				})
         	};
         	
         	/**
         	 * 新增
         	 */
            model.methods.add=function(editUrl){
            	layer.open({
					type: 2,
                    title:"新增商品品牌",
                    area: ['460px', '400px'],
                    btn: ['确定','取消'],
					content : [getCtx() + editUrl,'no'],
					yes : function(index, layero) {
						// 调用iframe层的表单提交方法
                        var iframeWin = window[layero.find('iframe')[0]['name']];
                        iframeWin.vm_goodBrandEdit.methods.validAndSubmit(function(){
                        	queryVal();
                        });
					},
					btn2 : function(){}
				});
            };
         	
         	/**
         	 * 编辑
         	 */
            model.methods.edit = function(id,editUrl) {
				layer.open({
					type: 2,
                    title:"修改商品品牌",
                    area: ['460px', '400px'],
                    btn: ['确定','取消'],
					content : [getCtx() + editUrl + '?id=' + id,'no'],
					yes : function(index, layero) {
						// 调用iframe层的表单提交方法
                        var iframeWin = window[layero.find('iframe')[0]['name']];
                        iframeWin.vm_goodBrandEdit.methods.validAndSubmit(function(){
                        	queryVal();
                        });
					},
					btn2 : function(){}
				});
			}
            
            /*
             * 删除 
             */
            model.methods.del=function(id,delUrl){
                layer.confirm('确定要删除？', {
                    btn: ['删除','取消'] // 按钮
                }, function(){
                    $.ajax({
                        data:{"id":id},
                        type:"post",
                        url:getCtx() + delUrl,
                        success:function(json){
                        	if(json.ok){
                        		layer.msg('删除成功', {icon: 1,time:1000},function(){
                        			queryVal();
                        		});
                        	}else if(json.ok==false && json.msg!=null){
                        		layer.msg(json.msg, {icon: 2,time:1000});
                        	}else{
                        		layer.msg('删除失败', {icon: 2,time:1000});
                        	}
                        }
                    })
                });
            };
                
        });
    });
});

