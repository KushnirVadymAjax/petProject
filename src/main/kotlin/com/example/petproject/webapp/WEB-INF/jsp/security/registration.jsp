<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Register new user</title>
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
<div>
    <%--@elvariable id="userForm" type=""--%>
    <form:form method="POST" modelAttribute="userForm">
        <h2>Register new user</h2>
        <div>
            <form:input type="text" path="username" placeholder="Username" autofocus="true"></form:input>
            <form:errors path="username"></form:errors> ${usernameError}
        </div>
        <div>
            <form:input type="password" path="password" placeholder="Password"></form:input>
        </div>
        <div>
            <form:input type="password" path="passwordConfirm" placeholder="Confirm your password"></form:input>
            <form:errors path="password"></form:errors> ${passwordError}
        </div>
        <div class="box">
            <div class="left">
                <button type="submit">Register</button>
            </div>
            <div class="right"><a href="/login">Home</a></div>
        </div>
    </form:form>

</div>
</body>
</html>