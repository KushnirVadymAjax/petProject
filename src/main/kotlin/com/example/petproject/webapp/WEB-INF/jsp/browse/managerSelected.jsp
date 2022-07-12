<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div class="left"><h1>Manager selected</h1></div>
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

<form method=get action="searchPerson">
    <input type="hidden" name="action" value="Search">

    Search by <select id="searchField" name="searchField">
    <option value="ID">Id</option>
    <option value="NAME">Name</option>
</select>: <input type="text" name="searchString">
</form>

<form method=get action="addPerson">
    <input type="submit" value="Add new">
</form>
<br>
<table id="table">
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>FULL_NAME</th>
        <th>PHONE_NUMBER</th>
        <th>ADMIN</th>
        <th>MANAGER_ID</th>
    </tr>

    <%--@elvariable id="tempPersonMap" type="java.util.List"--%>
    <c:forEach items="${tempPersonMap}" var="person">
        <tr>
            <td>${person.id}</td>
            <td>${person.name}</td>
            <td>${person.fullName}</td>
            <td>${person.phoneNumber}</td>
            <td>${person.admin}</td>
            <td>${person.manager.id}</td>
            <td>
                <a href="selectManager?managerID=${person.id}&personID=${personID}">Select</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>

</html>
