<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: VAHABI
  Date: 02/12/2023
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Informations</title>
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

        .informations{
            display: flex;
            flex-direction: column;
            margin-top: 1rem;
            margin-left: 1rem;
            padding: 1rem;
            width: 500px;
            background-color: #eaeaea;
            border-radius: 10px;
        }
        .informations div{
            display: flex;
            flex-direction: row;
            font-size: 3rem;
        }
        .informations .fontId{
            font-size: 5rem;
        }
        .informations div h3{
            margin-left: 1rem;
            opacity: .7;
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
<c:forEach items="${userForJsp}" var="user">
    <div class="informations">
        <h4 class="fontId">ID : ${user.idOfUser}</h4>
        <div><h4>Username : </h4><h3>${user.usernameOfUser}</h3></div>
        <div><h4>First Name : </h4><h3>${user.firstNameOfUser}</h3></div>
        <div><h4>Surname : </h4><h3>${user.surnameOfUser}</h3></div>
        <div><h4>Birth : </h4><h3>${user.ageOfUser}</h3></div>
        <div><h4>Gender : </h4><h3>${user.genderOfUser}</h3></div>
        <div><h4>Country : </h4><h3>${user.countryOfUser}</h3></div>
        <div><h4>City : </h4><h3>${user.cityOfUser}</h3></div>
    </div>
</c:forEach>

</body>
</html>
