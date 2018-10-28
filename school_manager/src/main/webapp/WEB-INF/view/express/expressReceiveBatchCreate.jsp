 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@include file="/WEB-INF/view/common/common.jsp" %>
    <title>批量导入收件</title>
</head>

<body>
    <form method="post" enctype="multipart/form-data" action="/express/expressReceive/batchCreate.do">
        <tr>
            <td>数据文件：</td>
            <td><input type="file" name="file"/></td>
        </tr>
        <tr>
            <td>邮箱地址：</td>
            <td><input type="text" name="email"/></td>
        </tr>

        <tr>
            <td>
                <input type="submit" value="导入" />
            </td>
        </tr>
    </form>

    <br/><br/><br/>
    <p class="color:red">
        * 请务必填写邮箱地址，用于接收录入结果，<br/>
        * 数据文件请将快递公司的名称改成对应的我们系统的快递公司编号，然后直接上传从快递员助手导出的文件，<br>
        * 对应关系：<br>
            顺丰 - SFSY<br>
            EMS - EMS<br>
            圆通速递 - YTSD<br>
            申通快递 - STKD<br>
            韵达快递 - YDKD<br>
            中通快递 - ZTKD<br>
            百世汇通 - BSHT<br>
            天天快递 - TTKD<br>
            中国邮政 - POST<br>
            京东快递 - JDKD<br>
            优速快递 - YSKD<br>
        如：数据是优速快递的，那么从快递员助手导出的文件的第一列是优速快递，将之改为对应的YSKD即可，其它的数据请不要作任何修改
    </p>
</body>
</html>