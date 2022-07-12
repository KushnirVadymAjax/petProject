<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Welcome</title>
    <link href="css/style.css"
          rel="stylesheet">
</head>

<body>

<div>
    <div class="left"><h1>Tasks browse</h1></div>
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

<br>

<form method=get action="searchTask">
    <input type="hidden" name="action" value="Search">

    Search by <select id="searchField" name="searchField">
    <option value="ID">Id</option>
</select>: <input type="text" name="searchString">
</form>

<form method=get action="addTask">
    <input type="submit" value="Add new">
</form>
<br>
<table id="table">
    <tr>
        <th>ID</th>
        <th>DATE</th>
        <th>POINT_ID</th>
    </tr>

    <%--@elvariable id="tempTasksMap" type="java.util.List"--%>
    <c:forEach items="${tempTasksMap}" var="task">

        <tr>
            <td>${task.id}</td>
            <td><fmt:formatDate value="${task.date}" pattern="yyyy-MM-dd"/></td>
            <td>${task.point}</td>
            <td>
                <a href="editTask?taskID=${task.id}">Edit</a>
            </td>
            <td>
                <a href="deleteTask?taskID=${task.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>

</html>
