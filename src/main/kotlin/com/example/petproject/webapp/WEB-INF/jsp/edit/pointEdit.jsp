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
    <div class="left"><h1>Point edit</h1></div>
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

<%--@elvariable id="tempPoint" type="java.util.List"--%>
<c:forEach items="${tempPoint}" var="point">
    <form method=post action="saveEditedPoint" modelAttribute="point">
        ID:<input name="id" type="text" value=${point.id} readonly><br>
        NAME:<input name="name" type="text" value=${point.name}><br>
        ADRESS:<input name="adress" type="text" value=${point.adress}><br>
        LATITUDE:<input name="latitude" type="text" value=${point.latitude}><br>
        LONGITUDE:<input name="longitude" type="text" value=${point.longitude}><br>
        CONTACT_PERSON:<input name="contactPerson" type="text" value=${point.contactPerson}><br>
        CONTACT_NUMBER:<input name="contactNumber" type="text" value=${point.contactNumber}><br>
        COMMENT:<input name="comment" type="text" value=${point.comment}><br>

        <input name="save" type="submit" value="Save">
        <input name="saveClose" type="submit" value="Save&Close">
        <input name="close" type="submit" value="Close">

    </form>
</c:forEach>

</body>
</html>
