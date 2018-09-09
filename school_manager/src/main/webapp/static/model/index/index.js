requirejs(['requirejs.config'], function () {
    requirejs(["respond","jquery","Hui"], function (respond,jquery,Hui) {
        $(function(){
            $.Huifold("#Huifold1 .item h4","#Huifold1 .item .info","fast",1,"click");
        })
    });
});

