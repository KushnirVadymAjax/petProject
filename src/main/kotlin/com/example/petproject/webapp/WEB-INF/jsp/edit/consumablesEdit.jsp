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
<h1>Consumables</h1>
<div>
    <div class="left"><h1>Consumables edit</h1></div>
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

<%--@elvariable id="tempConsumables" type="java.util.List"--%>
<c:forEach items="${tempConsumables}" var="consumables">
    <form method=post action="saveEditedConsumables?taskID=${taskID}" modelAttribute="consumables">
        ID:<input name="id" type="text" value=${consumables.id} readonly><br>
        NAME:<input name="name" type="text" value=${consumables.name}><br>
        DESCRIPTION:<input name="description" type="text" value=${consumables.description}><br>
        COMMENT:<input name="comment" type="text" value=${consumables.comment}><br>

        <input name="saveClose" type="submit" value="Save&Close">
        <input name="close" type="submit" value="Close">

    </form>
</c:forEach>

</body>
</html>
