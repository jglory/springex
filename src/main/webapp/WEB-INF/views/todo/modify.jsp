<%--
  Created by IntelliJ IDEA.
  User: joyongmoon
  Date: 2023/09/15
  Time: 5:41 PM
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
<body>
<div class="container-fluid">
  <!-- HEADER -->
  <div class="row">
    <div class="col">
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <span class="navbar-brand"><a href=" href="#"><img src="/resources/green-tea.png" width="20" height="20"> 日常茶槃思</a></span>
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
          <form action="/todo/modify" method="post">
            <input type="hidden" name="page" value="${pageRequestDto.page}">
            <input type="hidden" name="size" value="${pageRequestDto.size}">
          <div class="input-group mb-3">
            <span class="input-group-text">TNO</span>
            <input type="text" name="tno" class="form-control" value="<c:out value="${dto.tno}"></c:out>" readonly>
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text">Title</span>
            <input type="text" name="title" class="form-control" value="<c:out value="${dto.title}"></c:out>">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text">DueDate</span>
            <input type="date" name="dueDate" class="form-control" value="<c:out value="${dto.dueDate}"></c:out>">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text">Writer</span>
            <input type="text" name="writer" class="form-control" value="<c:out value="${dto.writer}"></c:out>" readonly>
          </div>
          <div class="form-check">
            <label class="form-check-label">
              Finished &nbsp;
            </label>
            <input class="form-check-input" type="checkbox" name="finished" class="form-control" ${dto.finished ? "checked" : ""}>
          </div>

          <div class="my-4">
            <div class="float-end">
              <button type="button" class="btn btn-primary">완료</button>
              <button type="button" class="btn btn-secondary">취소</button>
            </div>
          </div>
          </form>
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


  <script src="/resources/bootstrap.bundle.min.js"></script>
  <script type="application/javascript">

    document.querySelector(".btn-primary").addEventListener("click", function (e) {
      document.querySelector("form").submit();
    }, false);

    document.querySelector(".btn-secondary").addEventListener("click", function (e) {
      self.location = "/todo/read?tno=${dto.tno}&page=${pageRequestDto.page}&size=${pageRequestDto.size}"
    }, false)

  </script>
</body>
</html>