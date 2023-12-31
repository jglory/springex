<%--
  Created by IntelliJ IDEA.
  User: joyongmoon
  Date: 2023/09/15
  Time: 5:20 PM
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
        <div class="card-header">Featured</div>
        <div class="card-body">

          <div class="input-group mb-3">
            <span class="input-group-text">TNO</span>
            <input type="text" name="tno" class="form-control" value="<c:out value="${todoDto.tno}"></c:out>" readonly>
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text">Title</span>
            <input type="text" name="title" class="form-control" value="<c:out value="${todoDto.title}"></c:out>" readonly>
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text">DueDate</span>
            <input type="date" name="dueDt" class="form-control" value="<c:out value="${todoDto.dueDt}"></c:out>" readonly>
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text">Writer</span>
            <input type="text" name="writer" class="form-control" value="<c:out value="${todoDto.writer}"></c:out>" readonly>
          </div>
          <div class="form-check">
            <label class="form-check-label">
              Finished &nbsp;
            </label>
            <input class="form-check-input" type="checkbox" name="finished" class="form-control" ${todoDto.finished ? "checked" : ""}>
          </div>

          <div class="my-4">
            <div class="float-end">
              <button type="button" class="btn btn-danger">삭제</button>
              <button type="button" class="btn btn-primary">수정</button>
              <button type="button" class="btn btn-secondary">목록</button>
            </div>
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

    document.querySelector(".btn-danger").addEventListener("click", function (e) {
      e.preventDefault();
      e.stopPropagation();

      self.location = "/todo/remove?tno=${todoDto.tno}&page=${pageRequestDto.page}&size=${pageRequestDto.size}&finished=${queryString.finished}&types=${queryString.titleChecked ? "t" : ""}&types=${queryString.writerChecked ? "w" : ""}&keyword=${queryString.keyword}&startDt=${queryString.startDt}&finishDt=${queryString.finishDt}";
    }, false);

    document.querySelector(".btn-primary").addEventListener("click", function (e) {
      self.location = "/todo/modify?tno=${todoDto.tno}&page=${pageRequestDto.page}&size=${pageRequestDto.size}&finished=${queryString.finished}&types=${queryString.titleChecked ? "t" : ""}&types=${queryString.writerChecked ? "w" : ""}&keyword=${queryString.keyword}&startDt=${queryString.startDt}&finishDt=${queryString.finishDt}";
    }, false);

    document.querySelector(".btn-secondary").addEventListener("click", function (e) {
      self.location = "/todo/list?page=${pageRequestDto.page}&size=${pageRequestDto.size}&finished=${queryString.finished}&types=${queryString.titleChecked ? "t" : ""}&types=${queryString.writerChecked ? "w" : ""}&keyword=${queryString.keyword}&startDt=${queryString.startDt}&finishDt=${queryString.finishDt}";
    }, false)

  </script>
</body>
</html>
