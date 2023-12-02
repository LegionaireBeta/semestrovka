<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: VAHABI
  Date: 29/11/2023
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-Avb2QiuDEEvB4bZJYdft2mNjVShBftLdPG8FJ0V7irTLQ8Uo0qcPxh4Plq7G5tGm0rU+1SPhVotteLpBERwTkw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        .topNav {
            display: flex;
            flex-direction: row;
            justify-content: space-around;
            background-color: #333;
            overflow: hidden;
        }



        .topNav a {
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
            height: 25px;
        }
        .topNav a img{
            width: 25px;
            height: 25px;
        }


        .topNav a:hover {
            background-color: #ddd;
            color: black;
        }


        .topNav a.active {
            background-color: #04AA6D;
            color: white;
        }

        body .main{
            display: flex;
            flex-direction: row;
            justify-content: space-around;

        }

        body form{
            margin-top: 1rem;
            margin-left: 1rem;
            padding: 1rem;
            width: 60%;

            background-color: #eaeaea;
            border-radius: 10px;
        }

        form .inputs{
            display: flex;
            flex-direction: column;
            margin-bottom: .75rem;
            width: 70%;
        }

        form .inputs label{
            margin-bottom: .75rem;
            font-size: 2rem;
            font-family: sans-serif;
        }
        form .inputs input{
            padding: .55rem;
            font-size: 1.75rem;
            border-radius: 10px;
        }
        form .button{
            width: 150px;
            height: 50px;
            background-color: #30ff31;
            border: 1px solid black;
            border-radius: 10px;
            font-size: 1.75rem;
            cursor: pointer;
            transition: .5ms;
        }
        form .button:hover{
            background-color: #6bff6c;
        }
        form .button:active{
            background-color: #6bff6c;
        }

        .main .sections{
            display: flex;
            flex-direction: column;
            margin-top: 1rem;
            width: 300px;

        }
        .main .sections .section{
            padding: .75rem;
            background-color: #f5f3f3;
            color: black;
            border: 1px solid black;
            border-radius: 10px;
            text-align: center;
            cursor: pointer;
            font-size: 1.5rem;
            text-decoration: none;
            transition: .5s;
        }
        .main .sections .section:hover{
            background-color: white;
        }
    </style>
</head>
<body>
<%
    String role = (String) session.getAttribute("currentUsername");
    if("admin".equals(role)){
%>
<div class="topNav">
    <div class="left">
        <a href="/home">Home</a>
        <a href="/myArticles">My Articles</a>
        <a href="/insertArticle">Post Article</a>
        <a href="/users">Users</a>
        <a href="/userInformationSettings">Setting</a>
    </div>
    <c:forEach items="${usernameForJsp}" var="user">
        <div class="right">
            <a href="/signOut">Sign Out</a>
            <a href="/accountInformations">${user} <i class="fa-solid fa-crown"></i></a>
            <a href="#"><img src="/files/3135715.png"></a>
        </div>
    </c:forEach>
</div>
<%
}else {
%>
<div class="topNav">
    <div class="left">
        <a href="/home">Home</a>
        <a href="/myArticles">My Articles</a>
        <a href="/insertArticle">Post Article</a>
        <a href="/userInformationSettings">Setting</a>
    </div>
    <c:forEach items="${usernameForJsp}" var="user">
        <div class="right">
            <a href="/signOut">Sign Out</a>
            <a href="/accountInformations">${user}</a>
            <a href="#"><img src="/files/3135715.png"></a>
        </div>
    </c:forEach>
</div>
<%
    }
%>
    <div class="main">
        <c:forEach items="${settingsForJsp}" var="result">
        <form action="/mySettings" method="post">
            <div class="inputs">
                <label>Username</label>
                <input name="username" value="${result.usernameOfUser}">
            </div>
            <div class="inputs">
                <label>Password</label>
                <input type="password" name="password">
            </div>
            <button type="submit" class="button">Save</button>
        </form>
        </c:forEach>
        <div class="sections">
            <a class="section" href="/userInformationSettings">Edit User Informations</a>
            <a class="section" href="/mySettings">Edit Username & Password</a>
        </div>
    </div>
</body>
</html>
