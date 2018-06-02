requirejs(['requirejs.config'], function () {
    requirejs(["jquery","Hui","layer",'dataTables','icheck','form','validation','lightbox2'], function (jquery,Hui,layer,dataTables,icheck,validation,lightbox2) {
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


           var model=vm_goodInfoManage;
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
 					area: ['1024px', '768px'],
 					title:'商品详情',
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
                    title:"新增商品",
                    area: ['1024px', '768px'],
                    btn: ['确定','取消'],
					content : getCtx() + editUrl,
					yes : function(index, layero) {
						// 调用iframe层的表单提交方法
                        var iframeWin = window[layero.find('iframe')[0]['name']];
                        iframeWin.vm_goodInfoEdit.methods.validAndSubmit(function(){
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
                    title:"修改商品",
                    area: ['1024px', '768px'],
                    btn: ['确定','取消'],
					content : getCtx() + editUrl + '?id=' + id,
					yes : function(index, layero) {
						// 调用iframe层的表单提交方法
                        var iframeWin = window[layero.find('iframe')[0]['name']];
                        iframeWin.vm_goodInfoEdit.methods.validAndSubmit(function(){
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
            
            /*
             * 上架/下架
             */
            model.methods.upAndDown=function(id,status,upAndDownUrl){
            	var opt = "";
            	if(status == 1){
            		// 商品处于上架状态，执行下架操作
            		opt = "下架";
            	}else{
            		// 商品处于下架状态，执行上架操作
            		opt = "上架";
            	}
            	layer.confirm('确定要'+opt+'？', {
                    btn: ['确定','取消'] // 按钮
                },function(){
                	$.ajax({
                		data:{"id":id},
                		type:"post",
                		url:getCtx() + upAndDownUrl,
                		success:function(json){
                			if(json.ok){
                				layer.msg(opt+'成功', {icon: 1,time:1000},function(){
                					queryVal();
                				});
                			}else if(json.ok==false && json.msg!=null){
                				layer.msg(json.msg, {icon: 2,time:1000});
                			}else{
                				layer.msg(opt+'失败', {icon: 2,time:1000});
                			}
                		}
                	});
                });
            };
           
            
            // 全选/取消
    		$("#selectAll").on("click",function(){
    			if($("#selectAll").prop("checked")){
    				$("input[name='checkBox']").prop("checked",true);
    			}else{
    				$("input[name='checkBox']").prop("checked",false);
    			}
    		});
    		
    		var checkBoxs = $("input[name='checkBox']");
    		checkBoxs.change(function(){
    			if(checkBoxs.filter(":checked").size() == checkBoxs.size()){
    				$("#selectAll").prop("checked",true);
    			}else{
    				$("#selectAll").prop("checked",false);
    			}
    		});
            
            /*
             * 批量上架
             */
            model.methods.upAndDownAll=function(isUpAll,upAndDownAllUrl){
            	var opt = "";
            	if(isUpAll == 1){
            		opt = "批量上架";
            	}else{
            		opt = "批量下架";
            	}
            	
            	var length = $("input[name='checkBox']:checked").length;
            	if(length < 1){
            		layer.msg("没有选中要"+opt+"的产品！", {
            			icon : 2,
            			time : 1000
            		});
            		return ;
            	}
            	var ids = "";
            	$("input[name='checkBox']:checked").each(function(i, dom){
            		var idVal = $(this).val();
            		if(i == length - 1){
            			ids += idVal;
            		}else{
            			ids += idVal + ",";
            		}
            	});
            	
            	layer.confirm('确定要'+opt+'吗？', {
                    btn: ['确定','取消'] // 按钮
                },function(){
                	$.ajax({
                		data:{"ids":ids,"isUpAll":isUpAll},
                		type:"post",
                		url:getCtx() + upAndDownAllUrl,
                		success:function(json){
                			if(json.ok){
                				layer.msg(opt + '成功', {icon: 1,time:1000},function(){
                					queryVal();
                				});
                			}else if(json.ok==false && json.msg!=null){
                				layer.msg(json.msg, {icon: 2,time:1000});
                			}else{
                				layer.msg(opt + '失败', {icon: 2,time:1000});
                			}
                		}
                	});
                });
            };
            
            /**
             * 导出数据
             */
            model.methods.exportData=function(formId,exportDataUrl){
    			var total = $("#"+formId+" tr").length - 1;
    			if(total>0){

    				var url = getCtx()+exportDataUrl+"?total="+total+"&";

    				if(formId !=null && document.getElementById(formId) !=null && $("#"+formId).serialize() !=""){
    					url = url + $("#"+formId).serialize();
    				}
    				console.log(url);
    				var a = document.createElement("a");
    			    a.setAttribute("href", url);
    			    a.setAttribute("target", "iframeXls");
    			    a.setAttribute("id", "openwin");
    			    document.body.appendChild(a);
    			    layer.msg("已发送数据导出请求,请稍后...", {
    					icon : 7,
    					time : 1000
    				});
    			    a.click();

    			}else{
    				layer.msg("当前没有数据可以导出...", {
    					icon : 7,
    					time : 3000
    				});
    			}
            };
            
        });
    });
});

