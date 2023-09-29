<%--
  Created by IntelliJ IDEA.
  User: joyongmoon
  Date: 2023/09/13
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>할일 관리</title>
    <link href="/resources/bootstrap.min.css" rel="stylesheet">
</head>
<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/redux/4.2.1/redux.js"></script>
<script type="application/javascript" src="/resources/scripts/util.js"></script>
<script type="application/javascript" src="/resources/scripts/redux.js"></script>
<script type="application/javascript" src="/resources/scripts/web-component.js"></script>
<script type="application/javascript" src="/resources/scripts/todo-search-component.js"></script>
<script type="application/javascript" src="/resources/scripts/todo-page-navigator-component.js"></script>
<body>
<div class="container-fluid">
    <!-- HEADER -->
    <div class="row">
        <div class="col">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <span class="navbar-brand"><a href="#"><img src="/resources/green-tea.png" width="20" height="20"></a> <a href="#">할일 관리</a></span>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                        <div class="navbar-nav">
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                            <a class="nav-link" href="#">Features</a>
                            <a class="nav-link" href="#">Pricing</a>
                            <a class="nav-link" href="#">Disabled</a>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    </div>
    <!-- CONTENT -->
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-header">할 일</div>
                <div class="card-body">
                    <h5 class="card-title"></h5>
                    <todo-search-component action="${searchComponent['action']}", page-size="${searchComponent['page-size']}", finished="${searchComponent['finished']}", titleChecked="${searchComponent['titleChecked']}", writerChecked="${searchComponent['writerChecked']}", keyword="${searchComponent['keyword']}", startDt="${searchComponent['startDt']}", finishDt="${searchComponent['finishDt']}"></todo-search-component><br>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">번호</th>
                            <th scope="col">할일</th>
                            <th scope="col">작성자</th>
                            <th scope="col">일정</th>
                            <th scope="col">완료 여부</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageDto.items}" var="item">
                            <tr>
                                <th scope="row"><c:out value="${item.tno}"/></th>
                                <td><a href="#" onclick="event.preventDefault();self.location = encodeURI('/todo/read?tno=${item.tno}&page=${pageDto.page}&size=${pageDto.size}&finished=${searchComponent.finished ? "true" : "false"}&types=${searchComponent.titleChecked ? "t" : ""}&types=${searchComponent.writerChecked ? "w" : ""}&keyword=${searchComponent.keyword}&startDt=${searchComponent.startDt}&finishDt=${searchComponent.finishDt}');" class="text-docoration-none"><c:out value="${item.title}"/></a></td>
                                <td><c:out value="${item.writer}"/></td>
                                <td><c:out value="${item.dueDt}"/></td>
                                <td><c:out value="${item.finished ? '완료' : '진행중'}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <button class="btn btn-primary" type="button" onclick="self.location = encodeURI('/todo/register?tno=${item.tno}&page=${pageDto.page}&size=${pageDto.size}&finished=${searchComponent.finished ? "true" : "false"}&types=${searchComponent.titleChecked ? "t" : ""}&types=${searchComponent.writerChecked ? "w" : ""}&keyword=${searchComponent.keyword}&startDt=${searchComponent.startDt}&finishDt=${searchComponent.finishDt}');">등록</button>
                    <todo-page-navigator-component start="${pageNavigatorComponent.start}" end="${pageNavigatorComponent.end}" current="${pageNavigatorComponent.current}" count="${pageNavigatorComponent.count}" url="${pageNavigatorComponent.url}"></todo-page-navigator-component>
                </div>
            </div>
        </div>
    </div>
    <!-- FOOTER -->
    <div class="row fixed-bottom" style="z-index: -100">
<%--        <footer class="py-1 my-1">--%>
<%--            <p class="text-center text-muted">Footer</p>--%>
<%--        </footer>--%>
    </div>
</div>

    <script src="/resources/bootstrap.bundle.min.js"></script>
    <script type="application/javascript">
        addEventListener("load", function (e) {
            Redux.subscribeAll();

            <c:if test="${not empty error}">
            // error 출력
            alert("${error}");
            </c:if>
        }, false)
    </script>
</body>
</html>
