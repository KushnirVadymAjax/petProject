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
    <div class="left"><h1>Position edit</h1></div>
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

<%--@elvariable id="tempPosition" type="java.util.List"--%>
<c:forEach items="${tempPosition}" var="position">
    <form method=post action="saveEditedPosition?taskID=${taskID}" modelAttribute="position">
        ID:<input name="id" type="text" value=${position.id} readonly><br>
        DESCRIPTION:<input name="description" type="text" value=${position.description}><br>
        COMMENT:<input name="comment" type="text" value=${position.comment}><br>

        <input name="saveClose" type="submit" value="Save&Close">
        <input name="close" type="submit" value="Close">

    </form>
</c:forEach>

</body>
</html>
