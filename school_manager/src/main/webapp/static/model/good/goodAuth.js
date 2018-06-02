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


           var model=vm_goodAuthManage;
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
            
           /*
            * 审核 
            */
             model.methods.auth = function(id,goodAuthUrl){
            	 layer.open({
 					type: 2,
                    title:"审核商品",
                    area: ['1024px', '768px'],
                    btn: ['通过','不通过','取消'],
 					content : getCtx() + goodAuthUrl + '?id=' + id,
 					yes : function(index, layero) {
 						 // 调用iframe层的表单提交方法
                         var iframeWin = window[layero.find('iframe')[0]['name']];
                         iframeWin.vm_goodAuthEdit.methods.validAndSubmit(4,function(){
                         	queryVal();
                         });
 					},
 					btn2 : function(index, layero){
 						// 调用iframe层的表单提交方法
                        var iframeWin = window[layero.find('iframe')[0]['name']];
                        iframeWin.vm_goodAuthEdit.methods.validAndSubmit(5,function(){
                        	queryVal();
                        });
                        return false;
 					}
 				});
         	};
            
        });
    });
});

