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
    <div class="left"><h1>Person edit</h1></div>
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

<%--@elvariable id="tempPersonMap" type="java.util.List"--%>
<c:forEach items="${tempPersonMap}" var="persons">
    <form method=post action="saveEditedPersons" modelAttribute="persons">
        ID:<input name="id" type="text" value=${persons.id} readonly><br>
        NAME:<input name="name" type="text" value=${persons.name}><br>
        FULL_NAME:<input name="fullName" type="text" value=${persons.fullName}><br>
        PHONE_NUMBER:<input name="phoneNumber" type="text" value=${persons.phoneNumber}><br>
        ADMIN:<input name="admin" type="checkbox" ${persons.admin  ? "checked":""}><br>
        MANAGER_ID:<input name="manager" type="text" value=${persons.manager.id !=null ? persons.manager.id:0} readonly>
        <input name="selectManager" type="submit" value="Select manager"><br>

        <input name="save" type="submit" value="Save">
        <input name="saveClose" type="submit" value="Save&Close">
        <input name="close" type="submit" value="Close">

    </form>
</c:forEach>

</body>
</html>
