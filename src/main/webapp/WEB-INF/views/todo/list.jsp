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
<script type="application/javascript">

    class TodoPageNavigatorComponent extends WebComponent {
        #loaded = false;
        #elements = [];

        static get observedAttributes() {
            return ['start', 'end', 'current', 'count', 'url']
        }

        constructor() {
            super();

            // 모든 WebComponent 는 반드시 Redux 에 등록해야 함.
            this.componentId = Redux.registerComponent(this);

            this.attachShadow({ mode: 'open'});

            // Define the template
            const template = document.createElement("template");
            template.innerHTML = `
                <link href="/resources/bootstrap.min.css" rel="stylesheet">
                <div class="float-end">
                    <ul class="pagination flex-wrap">

                    </ul>
                </div>
                `;
            this.shadowRoot.appendChild(template.content.cloneNode(true));
        }

        connectedCallback() {
            this.#bind();
            Redux.dispatch(this, COMPONENT_ON_LOAD);
        }

        #bind() {

        }

        componentOnLoad(action) {
            let prev = "";
            let next = "";
            let pages = "";

            const start = parseInt(this.getAttribute("start"));
            const end = parseInt(this.getAttribute("end"));
            const current = parseInt(this.getAttribute("current"));
            const count = parseInt(this.getAttribute("count"));
            const url = this.getAttribute("url");

            if (start > 1) {
                prev = `
                <li class="page-item">
                    <a class="page-link" data-num="\${start - 1}">이전</a>
                </li>
                `;
            }
            if (end < count) {
                next = `
                <li class="page-item">
                    <a class="page-link" data-num="\${end + 1}">다음</a>
                </li>
                `;
            }

            for (let i = start; i <= end; ++i) {
                pages += `
                <li class="page-item"><a class="page-link` + (i === current ? " active" : "") + `" href="` + url.replace("###", i) + `">\${i}</a></li>
                `;
            }

            this.shadowRoot.innerHTML = `
                <link href="/resources/bootstrap.min.css" rel="stylesheet">
                <div class="float-end">
                    <ul class="pagination flex-wrap">
                `
                + prev
                + pages
                + next
                + `
                    </ul>
                </div>
                `;
            this.#loaded = true;
        }

        adoptedCallback() {
            // called when the element is moved to a new document
            // (happens in document.adoptNode, very rarely used)
        }

        attributeChangedCallback(name, oldValue, newValue) {
            Redux.dispatch(
                this,
                ATTRIBUTE_ON_CHANGE,
                {
                    name: name,
                    oldValue: oldValue,
                    newValue: newValue,
                }
            );
        }

        attributeOnChange(action) {
            if (this.#loaded) {
                switch (action.data.name) {
                    case "start":
                        // this.#elements["start"].action = this.getAttribute("start");
                        break;
                    case "end":
                        // this.#elements["end"].checked = this.getAttribute("end") === "true";
                        break;
                    case "count":
                        // this.#elements["count"].checked = this.getAttribute("count") === "true";
                        break;
                    case "url":
                        // this.#elements["url"].checked = this.getAttribute("url") === "true";
                        break;
                }
            }
        }

        _getComponentState() {
            return this.#loaded ? {

            } : {};
        }

        render() {
            var state = Redux.queryComponentState(this);
        }
    }

    customElements.define("todo-page-navigator-component", TodoPageNavigatorComponent);
</script>
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
                    <todo-search-component action="${searchComponent['action']}", page-size="${searchComponent['page-size']}", finished="${searchComponent['finished']}", titleChecked="${searchComponent['titleChecked']}", writerChecked="${searchComponent['writerChecked']}", keyword="${searchComponent['keyword']}", startDt="${searchComponent['startDt']}", finishDt="${searchComponent['finishDt']}"></todo-search-component>
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
                        <c:forEach items="${pageDto.items}" var="item">
                            <tr>
                                <th scope="row"><c:out value="${item.tno}"/></th>
                                <td><a href="#" onclick="event.preventDefault();self.location = encodeURI('/todo/read?tno=${item.tno}&page=${pageDto.page}&size=${pageDto.size}&finished=${searchComponent.finished ? "true" : "false"}&types=${searchComponent.titleChecked ? "t" : ""}&types=${searchComponent.writerChecked ? "w" : ""}&keyword=${searchComponent.keyword}&startDt=${searchComponent.startDt}&finishDt=${searchComponent.finishDt}');" class="text-docoration-none"><c:out value="${item.title}"/></a></td>
                                <td><c:out value="${item.writer}"/></td>
                                <td><c:out value="${item.dueDt}"/></td>
                                <td><c:out value="${item.finished}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <todo-page-navigator-component start="${pageNavigatorComponent.start}" end="${pageNavigatorComponent.end}" current="${pageNavigatorComponent.current}" count="${pageNavigatorComponent.count}" url="${pageNavigatorComponent.url}"></todo-page-navigator-component>
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
