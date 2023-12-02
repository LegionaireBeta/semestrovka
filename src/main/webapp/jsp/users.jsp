<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: VAHABI
  Date: 02/12/2023
  Time: 01:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-Avb2QiuDEEvB4bZJYdft2mNjVShBftLdPG8FJ0V7irTLQ8Uo0qcPxh4Plq7G5tGm0rU+1SPhVotteLpBERwTkw==" crossorigin="anonymous" referrerpolicy="no-referrer" />

    <title>Settings</title>

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


        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        .trash{
            cursor: pointer;
        }
        .trash .fa-trash{
            color: #326c9b;
            transition: .5s;
        }

        .trash .fa-trash:hover{
            color: black;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
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
            <a href="#">${user} <i class="fa-solid fa-crown"></i></a>
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
            <a href="#">${user}</a>
            <a href="#"><img src="/files/3135715.png"></a>
        </div>
    </c:forEach>
</div>
<%
    }
%>

<%
    String role1 = (String) session.getAttribute("currentUsername");
    if("admin".equals(role1)){
%>

<table>
    <tr>
        <th>ID</th>
        <th>USERNAME</th>
        <th>NAME</th>
        <th>SURNAME</th>
        <th>AGE</th>
        <th>GENDER</th>
        <th>COUNTRY</th>
        <th>CITY</th>
        <th>DELETE</th>

    </tr>
    <c:forEach items="${usersForJsp}" var="user">
        <tr>
            <td>${user.idOfUser}</td>
            <td>${user.usernameOfUser}</td>
            <td>${user.firstNameOfUser}</td>
            <td>${user.surnameOfUser}</td>
            <td>${user.ageOfUser}</td>
            <td>${user.genderOfUser}</td>
            <td>${user.countryOfUser}</td>
            <td>${user.cityOfUser}</td>
            <td class="trash"><a href="/deleteUser?idOfUser=${user.idOfUser}&usernameOfUser=${user.usernameOfUser}"><i class="fa-solid fa-trash"></i></a></td>
        </tr>
    </c:forEach>
</table>

<%
    }else{
%>

<h1>YOU ARE NOT ADMIN</h1>

<% } %>
</body>
</html>
