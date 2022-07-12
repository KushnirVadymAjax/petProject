<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Log in with your account</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
        }

        table#alter tr:nth-child(even) {
            background-color: #eee;
        }

        table#alter tr:nth-child(odd) {
            background-color: #fff;
        }

        table#alter th {
            color: white;
            background-color: gray;
        }

        input[type=text], input[type=hidden], input[type=date], table, input[type=password] {
            font-size: 1rem;
            font-family: sans-serif;
            margin: 5px;
        }

        a:link, a:visited, button {
            background-color: #2ae133;
            color: #000000;
            padding: 8px 25px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            margin: 4px 2px;
            border: 1px solid black;
        }

        input[type=button], input[type=submit], input[type=reset] {
            background-color: #2ae133;
            border: 1px solid black;
            color: #000000;
            padding: 8px 25px;
            text-decoration: none;
            margin: 4px 2px;
            cursor: pointer;
            display: inline-block;
            text-align: center;
        }

        .left, .right {
            display: inline-block;
            width: 100%;
            height: 100%;
        }
    </style>
</head>

<body>
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
</sec:authorize>
<div>
    <form method="POST" action="/login">
        <h2>Login</h2>
        <div>
            <input name="username" type="text" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" placeholder="Password"/>
            <button type="submit">Login</button>
            <h4><a href="/registration">Register</a></h4>
        </div>
    </form>
</div>

</body>
</html>