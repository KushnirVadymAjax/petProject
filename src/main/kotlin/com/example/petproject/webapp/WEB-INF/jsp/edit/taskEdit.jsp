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
    <div class="left"><h1>Task edit</h1></div>
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

<%--@elvariable id="tempTask" type="java.util.List"--%>
<c:forEach items="${tempTask}" var="task">
    <form method=post action="saveEditedTask" modelAttribute="task">
        ID:<input name="id" type="text" value=${task.id} readonly><br>
        DATE:<input name="date" type="date" value=${task.date}><br>
        POINT:<input name="point" type="text" value=${task.point !=null ? task.point:0} readonly>
        <input name="selectPoint" type="submit" value="Select point"><br>

        <br>
        <input name="saveAndAddPosition" type="submit" value="Add new position">
        <br>

        <table id="positionTable">
            <tr>
                <th>ID</th>
                <th>DESCRIPTION</th>
                <th>COMMENT</th>
            </tr>
            <c:forEach items="${task.positions}" var="taskPositions">

                <tr>
                    <td>${taskPositions.id}</td>
                    <td>${taskPositions.description}</td>
                    <td>${taskPositions.comment}</td>
                    <td>
                        <a href="editPosition?positionID=${taskPositions.id}&taskID=${task.id}">Edit</a>
                    </td>
                    <td>
                        <a href="deletePosition?positionID=${taskPositions.id}&taskID=${task.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>

        </table>

        <br>
        <input name="saveAndAddConsumables" type="submit" value="Add new consumables">
        <br>

        <table id="consumablesTable">
            <tr>

                <th>ID</th>
                <th>NAME</th>
                <th>DESCRIPTION</th>
                <th>COMMENT</th>
            </tr>
            <c:forEach items="${task.consumables}" var="taskConsumables">
                <tr>
                    <td>${taskConsumables.id}</td>
                    <td>${taskConsumables.name}</td>
                    <td>${taskConsumables.description}</td>
                    <td>${taskConsumables.comment}</td>
                    <td>
                        <a href="editConsumables?consumablesID=${taskConsumables.id}&taskID=${task.id}">Edit</a>
                    </td>
                    <td>
                        <a href="deleteConsumables?consumablesID=${taskConsumables.id}&taskID=${task.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <input name="selectPerson" type="submit" value="Select person">
        <br>
        <table id="personsTable">
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>FULL_NAME</th>
                <th>PHONE_NUMBER</th>
                <th>ADMIN</th>
                <th>MANAGER_ID</th>
            </tr>

                <%--@elvariable id="tempPersonMap" type="java.util.List"--%>
            <c:forEach items="${task.persons}" var="taskPerson">

                <tr>
                    <td>${taskPerson.id}</td>
                    <td>${taskPerson.name}</td>
                    <td>${taskPerson.fullName}</td>
                    <td>${taskPerson.phoneNumber}</td>
                    <td>${taskPerson.admin}</td>
                    <td>${taskPerson.manager.id}</td>
                    <td>
                        <a href="editPersonFromTask?personID=${taskPerson.id}&taskID=${task.id}">Edit</a>
                    </td>
                    <td>
                        <a href="deletePersonFromTask?personID=${person.id}&taskID=${task.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <input name="save" type="submit" value="Save">
        <input name="saveClose" type="submit" value="Save&Close">
        <input name="close" type="submit" value="Close">
    </form>
</c:forEach>

</body>
</html>
