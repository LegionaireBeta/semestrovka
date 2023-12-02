<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: VAHABI
  Date: 27/11/2023
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>

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

        .mainContainer{
            width: 70%;
        }

        .articles{

            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 1.5rem;
            margin-left: 8rem;
            width: 80%;
        }
        .articles .article{
            position: relative;
            display: flex;
            flex-direction: column;
            align-items: start;
            margin-bottom: 2rem;
            border-bottom: 1px solid black;
            border-left: 1px solid black;
            border-right: 1px solid black;
            padding: 1rem;
        }

        .articles .article .topOfArticle{
            display: flex;
            flex-direction: row;
            margin-bottom: .75rem;
        }

        .articles .article .topOfArticle .profilePhoto{
            margin-right: .5rem;
            width: 50px;
        }


        .articles .article .topOfArticle .nameOfUser{
            margin-top: .8rem;
            margin-left: 1rem;
            font-size: 1.75rem;

        }
        .articles .article .articleOfUser{
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            margin-bottom: 1rem;
        }

        .articles .article .articleOfUser .titleOfArticle{
            margin-bottom: .75rem;
            font-size: 2rem;
        }
        .articles .article .articleOfUser .textOfArticle{
            padding: 0 .75rem;
            font-size: 1.2rem;
            font-family: sans-serif;
        }

        .articles .article .bottomOfArticle{
            display: flex;
            flex-direction: row;

            padding: 0 .75rem;
        }
        .articles .article .bottomOfArticle .dateOfArticle{
            margin-right: 1rem;
            opacity: .7;
        }
        .articles .article .bottomOfArticle .tagOfArticle{
            margin-left: 0.5rem;
            text-decoration: none;
        }

        .articles .article .bottomOfArticle a i{
            position: absolute;
            bottom: .5rem;
            right: 1rem;
            margin-bottom: 0.5rem;
            margin-right: 0.5rem;
            padding: .5rem;
            color: black;
            color: black;
            border: 1px solid black;
            border-radius: 100%;
            font-size: 1.25rem;
        }
        .articles .article .bottomOfArticle a i:hover{
            border: 1px solid #326c9b;
            color: #326c9b;
        }

        .articles .article .bottomOfArticle a i.fa-trash{
            position: absolute;
            right: 5rem;
        }


        .main{
            display: flex;
            flex-direction: row;
        }

        .sections{

            display: flex;
            flex-direction: column;
            margin-top: 1rem;
            width: 300px;

        }
        .sections .section{
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
        .sections .section:hover{
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
    <div class="main">
        <div class="mainContainer">
            <div class="articles">
                <c:forEach items="${articlesForJsp}" var="article">
                    <div class="article" id="article-${article.articleId}">
                        <div class="topOfArticle">
                            <img class="profilePhoto" src="/files/3135715.png">
                            <h3 class="nameOfUser">${article.username}</h3>
                        </div>
                        <div class="articleOfUser">
                            <h2 class="titleOfArticle">${article.title}</h2>
                            <p class="textOfArticle">${article.text}</p>
                        </div>
                        <div class="bottomOfArticle">
                            <p class="dateOfArticle">${article.date}</p>
                            <a href="#" class="tagOfArticle">#${article.tag}</a>
                            <%
                                if("admin".equals(role)){
                            %>
                            <a href="/deleteArticle?articleId=${article.articleId}"><i class="fa-solid fa-trash"></i></a>
                            <a href="/savedArticles?articleId=${article.articleId}"><i class="fa-solid fa-plus"></i></a>
                            <%
                                }else{
                            %>
                            <a href="/savedArticles?articleId=${article.articleId}"><i class="fa-solid fa-plus"></i></a>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="sections">
            <c:forEach items="${titlesForJsp}" var="article">
            <a class="section" href="#article-${article.articleId}">${article.title}</a>
            </c:forEach>
        </div>
    </div>
</body>
</html>
