<%@ page import="com.jh.common.enmu.RequestType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="my" uri="mytaglib" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/UIFrameWork/common/common.jsp"/>
    <title>Title</title>
</head>
<body>
<ajaxBoot id="ajaxBoot">
<div class="container">
    <div class="panel panel-primary">
        <!-- Default panel contents -->
        <div class="panel-heading ">
            <my:showBtn serach="serach"></my:showBtn>
        </div>
        <div class="panel-body">
            <my:LoadForm requestType=""></my:LoadForm>
        </div>
            <my:LoadList btnType="checkbox"></my:LoadList>
            <div class="panel-footer"><my:PageInfo requestType="post"></my:PageInfo></div>
        </div>


    </div>
</ajaxBoot>
</body>
</html>
