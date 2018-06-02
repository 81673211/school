$(function(){
    //名
    var model = vm_tablecase;
    //详情
    model.detail = function(id){
        window.location.href =  'caseDetail.html?id=' + id;
    }
    //分享
    model.share = function(id){
        layer.open({
            type: 2,
            title:"分享",
            area: ['400px', '320px'],
            btn: ['确定','取消'],
            content: 'case-share.html?id=' + id,
            yes: function(index, layero){
                var body = layer.getChildFrame('body', index);
                if(body.find("input:checked").val()){
                    $.ajax({
                        data: {"caseid": id, "shareId":body.find("input:checked").val() },
                        type: 'post',
                        dataType: 'josn',
                        url:"../share",
                        success: function (json) {
                            if(json.code == "0"){
                                layer.close(index);
                                layer.msg("已分享",{icon: 6,time:1000});
                            } else {
                                //操作失败
                            }
                        }
                    });
                }else{
                    layer.msg("您未选择分享",{icon:3,time:1000})
                }

            }
        });
    };
    //移交，分案
    model.tansfer = function(id,tansName){

        layer.open({
            type:2,
            title:$("#tansferT").text(),
            area:["400px","400px;"],
            btn:["移交","取消"],
            content:["case-transfer.html?id" + id,"no"],
            yes:function(index,layero){
                var body = layer.getChildFrame('body',index);
                $.ajax({
                    data:{"caseid":id, "tansName":body.find("#selectName").val()},
                    type:"post",
                    dataType:"json",
                    url:"../tansfer",
                    success:function(json){
                        if(json.code=="1"){
                            layer.close(index);
                            layer.msg("移交成功",{icon:1,time:1000});
                        }else{
                            //fail
                        }
                    }
                })
            }
        })
    }
})
