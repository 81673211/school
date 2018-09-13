requirejs(['requirejs.config'], function () {
    requirejs(["jquery","Hui","layer"], function (jquery,Hui,layer) {
    	$(function(){
//    		messageAlert();
//    		function messageAlert (){
//    			$.ajax({
//    				url:'/casemessage/getUnReadMessageSum.do',
//    				type:'post',
//    				dataType:'json',
//    				success:function(result){
//    					if(result.ok){
//    						if(result.data > 0){
//    							$('.msg-tips').css('display', 'inline-block');
//    							$('#msg-tips').text(result.data);
//    						}else{
//    							$('.msg-tips').css('display', 'none');
//    							$('#msg-tips').text('');
//    						}
//    					}else{
//    						$('.msg-tips').css('display', 'none');
//    						$('#msg-tips').text('');
//    					}
//    				},
//    				error:function(){
//    					layer.msg('网络错误，链接已断开，请检查您的网络是否正常', {icon:5, time:1000});
//    				}
//    			})
//    		}
//    		window.setInterval(messageAlert, 3000);
    		
    		var model = vm_main;
    		init();
    		// 注释掉H-ui.admin.js中调用的折叠，在此处重新调用，避免动态加载菜单无效和双重调用后引起的缩回现象
    		$.Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",1,"click");
    		function init(){
    			//初始化左侧菜单
    			if(model.menus!=null){
    				var menu = '';
    				$.each(model.menus, function(n1, v1){
    					if(v1.menuList.length>0){
    						menu += '<dl>';
    						menu += '<dt>';
    						menu += '<i class="'+v1.icon+'"></i> '+v1.resourceName+'<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>';
    						if(v1.resourceName=='消息管理')
    							menu += '<span class="msg-tips" style="display:none"></span>';
    						menu += '</dt>';
    			            menu += '<dd><ul>';
    			            $.each(v1.menuList, function(n2, v2){
    			            	menu += '<li><a _href="'+getCtx()+v2.resourceUrl+'" data-title="'+v2.resourceName+'" href="javascript:void(0)">'+v2.resourceName;
    			            	if(v2.resourceName=='我的消息')
    			            		menu += '<span class="badge badge-danger radius" id="msg-tips"></span>';
    			            	menu += '</a></li>';
    			            })
    			            menu += '</ul></dd></dl>';
    					}else{
    						menu += '<dl><ul>';
    						menu += '<li><a _href="'+getCtx()+v1.resourceUrl+'" data-title="'+v1.resourceName+'" href="javascript:void(0)" style="font-weight: normal"><i class="'+v1.icon+'"></i> '+v1.resourceName+'</a></li>';
    			            menu += '</ul></dl>';
    					}
    				})
    				$('#leftMenus').empty().append(menu);
    			}
    		}
    		model.alterPwd = function(){
    			layer.open({
    				type: 1,
                    title:"修改密码",
                    area: ['320px', '220px'],
                    btn: ['确定','取消'],
                    content: $('#alterPwd'),
                    yes: function(index, layero){
                        var oldpwd = $('#alterPwd input[name="oldPwd"]').val()||'';
                        var newpwd = $('#alterPwd input[name="newPwd"]').val()||'';
                        var repwd = $('#alterPwd input[name="rePwd"]').val()||'';
                        if(oldpwd==''){
                        	layer.tips('原始密码不能为空', $('#alterPwd input[name="oldPwd"]'));
                        	return false;
                        }
                        if(newpwd==''){
                        	layer.tips('新密码不能为空', $('#alterPwd input[name="newPwd"]'));
                        	return false;
                        }
                        if(repwd!=newpwd){
                        	layer.tips('两次密码输入不一致', $('#alterPwd input[name="rePwd"]'));
                        	return false;
                        }
                        var json = {'oldPassword':oldpwd, 'newPassword':newpwd};
                        $.ajax({
                        	url:getCtx()+'/managers/user/doUpdateUserPwd',
                        	type:'post',
                        	dataType:'json',
                        	data:json,
                        	success:function(result){
                        		if(result.ok){
                        			layer.msg('更改成功,请重新登陆系统', {icon:1, time:1000}, function(){
                        				window.location.href = getCtx()+"/managers/user/logout.htm";
                        			});
                        		}else{
                        			layer.msg(result.msg, {icon:5, time:1000});
                        		}
                        	},
                        	error:function(){
                        		layer.msg('网络错误', {icon:5, time:1000});
                        	}
                        })
                    },
                    end : function(){
                    	$('#alterPwd input').val('');
                    }
    			})
    		}
    	})
    });
});

