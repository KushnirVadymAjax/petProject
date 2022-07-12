<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Welcome</title>
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

        input[type=text], input[type=hidden], input[type=date], table {
            font-size: 1rem;
            font-family: sans-serif;
            margin: 5px;
        }

        a:link, a:visited {
            background-color: #2ae133;
            color: #000000;
            padding: 8px 25px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            border: 1px solid black;
        }

        input[type=button], input[type=submit], input[type=reset] {
            background-color: #2ae133;
            border: 1px solid black;
            color: #000000;
            padding: 8px 32px;
            text-decoration: none;
            margin: 4px 2px;
            cursor: pointer;
        }

        .left, .right {
            display: inline-block;
            width: 100%;
            margin-right: -100%;
        }

        .right {
            text-align: right;
        }

        .left a, .right a {
            position: relative;
        }
    </style>
</head>
<body>

<div>
    <div class="left"><h1>Welcome</h1></div>
    <div class="right">Welcome, ${username} <a href="logoutApp">Logout</a></div>
</div>

<nav>
    <a href="/">Home</a>
    <a href="/tasksBrowse">Tasks</a>
    <a href="/pointsBrowse">Points</a>
    <a href="/personsBrowse">Persons</a>
    ${isAdmin}
    <div class="# start-home"></div>
</nav>

</body>

</html>