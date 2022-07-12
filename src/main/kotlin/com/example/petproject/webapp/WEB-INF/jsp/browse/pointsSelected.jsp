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
    <div class="left"><h1>Points selected</h1></div>
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

<form method=get action="searchPoint">
    <input type="hidden" name="action" value="Search">

    Search by <select id="searchField" name="searchField">
    <option value="ID">Id</option>
    <option value="NAME">Name</option>
</select>: <input type="text" name="searchString">
</form>

<form method=get action="addPoint">
    <input type="submit" value="Add new">
</form>
<br>
<table id="table">
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>ADRESS</th>
        <th>LATITUDE</th>
        <th>LONGITUDE</th>
        <th>CONTACT_PERSON</th>
        <th>CONTACT_NUMBER</th>
        <th>COMMENT</th>
    </tr>

    <%--@elvariable id="tempPointsMap" type="java.util.List"--%>
    <c:forEach items="${tempPointsMap}" var="point">

        <tr>
            <td>${point.id}</td>
            <td>${point.name}</td>
            <td>${point.adress}</td>
            <td>${point.latitude}</td>
            <td>${point.longitude}</td>
            <td>${point.contactPerson}</td>
            <td>${point.contactNumber}</td>
            <td>${point.comment}</td>
            <td>
                <a href="selectPoint?pointID=${point.id}&taskID=${taskID}">Select</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>

</html>
