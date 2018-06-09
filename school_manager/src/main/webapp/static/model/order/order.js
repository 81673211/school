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


           var model=vm_orderManage;
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
 					title:'订单详情',
 					shadeClose: true,
 					maxmin: false,
 					content: getCtx() + detailUrl + '?id='+id
 				})
         	};
         	
        });
    });
});

