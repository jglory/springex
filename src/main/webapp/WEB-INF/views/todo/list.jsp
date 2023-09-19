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
    <title>일상다반사</title>
    <link href="/resources/bootstrap.min.css" rel="stylesheet">
</head>
<script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/redux/4.2.1/redux.js"></script>
<script type="application/javascript" src="/resources/scripts/util.js"></script>
<script type="application/javascript" src="/resources/scripts/redux.js"></script>
<script type="application/javascript" src="/resources/scripts/web-component.js"></script>
<script type="application/javascript" src="/resources/scripts/todo-search-component.js"></script>
<body>
<div class="container-fluid">
    <!-- HEADER -->
    <div class="row">
        <div class="col">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <span class="navbar-brand"><a href="#"><img src="/resources/green-tea.png" width="20" height="20"></a> <a href="#">日常茶槃思</a></span>
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
                <div class="card-header">Featured</div>
                <div class="card-body">
                    <h5 class="card-title">Special title treatment</h5>
                    <todo-search action="${searchComponent['action']}", page-size="${searchComponent['page-size']}", finished="${searchComponent['finished']}", titleChecked="${searchComponent['titleChecked']}", writerChecked="${searchComponent['writerChecked']}", keyword="${searchComponent['keyword']}", startDt="${searchComponent['startDt']}", finishDt="${searchComponent['finishDt']}"></todo-search>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Tno</th>
                            <th scope="col">Title</th>
                            <th scope="col">Writer</th>
                            <th scope="col">DueDate</th>
                            <th scope="col">Finished</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageDto.dtoList}" var="item">
                            <tr>
                                <th scope="row"><c:out value="${item.tno}"/></th>
                                <td><a href="/todo/read?tno=${item.tno}&page=${pageDto.page}&size=${pageDto.size}" class="text-docoration-none"><c:out value="${item.title}"/></a></td>
                                <td><c:out value="${item.writer}"/></td>
                                <td><c:out value="${item.dueDt}"/></td>
                                <td><c:out value="${item.finished}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="float-end">
                        <ul class="pagination flex-wrap">
                            <c:if test="${pageDto.prev}">
                                <li class="page-item">
                                    <a class="page-link" data-num="${pageDto.start - 1}">이전</a>
                                </li>
                            </c:if>

                            <c:forEach begin="${pageDto.start}" end="${pageDto.end}" var="pageNo">
                                <li class="page-item"><a class="page-link${pageDto.page == pageNo ? " active" : ""}" data-num="${pageNo}">${pageNo}</a></li>
                            </c:forEach>

                            <c:if test="${pageDto.next}">
                                <li class="page-item">
                                    <a class="page-link" data-num="${pageDto.end + 1}">다음</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- FOOTER -->
    <div class="row fixed-bottom" style="z-index: -100">
        <footer class="py-1 my-1">
            <p class="text-center text-muted">Footer</p>
        </footer>
    </div>
</div>

    <script src="/resources/bootstrap.bundle.min.js"></script>
    <script type="application/javascript">
        document.querySelector(".pagination").addEventListener("click", function (e) {
            e.preventDefault();
            e.stopPropagation();

            if (e.target.tagName !== "A") {
                return;
            }

            self.location = "/todo/list?page=" + e.target.getAttribute("data-num");
        }, false);

        addEventListener("load", function (e) {
            Redux.subscribeAllComponentsToStore();

            // error 출력
            <c:if test="${error != ''}">
            alert("${error}");
            </c:if>
        }, false)
    </script>
</body>
</html>
