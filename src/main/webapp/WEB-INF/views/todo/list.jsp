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
<%--<script type="application/javascript" src="/resources/scripts/todo-components.js"></script>--%>
<script type="application/javascript">

    const snakeToCamel = str =>
        str.toLowerCase().replace(/([-_][a-z])/g, group =>
            group
                .toUpperCase()
                .replace('-', '')
                .replace('_', '')
        );

    Redux.components = [];
    Redux.componentId = null;
    Redux.registerWebComponent = function(component) {
        Redux.components.push(component);
        return Redux.components.length - 1;
    }
    Redux.queryComponent = function (action) {
        return Redux.components[action.componentId];
    }
    Redux.queryActionHandler = function (component, action) {
        const handler = eval("component." + snakeToCamel(action.type));
        return handler ? handler.bind(component) : null;
    }

    class WebComponent extends HTMLElement {
        componentId = null;
    }

    const ATTRIBUTE_ON_CHANGE = "ATTRIBUTE_ON_CHANGE";
    const FINISHED_ON_CLICK = "FINISHED_ON_CLICK";
    const TITLE_CHECKED_ON_CLICK = "TITLE_CHECKED_ON_CLICK";
    const WRITER_CHECKED_ON_CLICK = "WRITER_CHECKED_ON_CLICK";

    class TodoSearchComponent extends HTMLElement {
        #elements = [];

        static get observedAttributes() {
            return ['action', 'finished', 'titleChecked', 'writerChecked', 'keyword', 'startDt', 'finishDt']
        }

        adoptedCallback() {
            // called when the element is moved to a new document
            // (happens in document.adoptNode, very rarely used)
        }

        attributeChangedCallback(name, oldValue, newValue) {
            store.dispatch({
                componentId: this.componentId,
                type: ATTRIBUTE_ON_CHANGE,
                data: {
                    name: name,
                    oldValue: oldValue,
                    newValue: newValue,
                }
            });
        }

        attributeOnChange(action) {
            if (this.#elements["form"] !== undefined) {
                switch (action.data.name) {
                    case "action":
                        this.#elements["form"].action = this.getAttribute("action");
                        break;
                    case "finished":
                        this.#elements["finished"].checked = this.getAttribute("finished") == "true";
                        break;
                    case "titleChecked":
                        this.#elements["titleChecked"].checked = this.getAttribute("titleChecked") == "true";
                        break;
                    case "writerChecked":
                        this.#elements["writerChecked"].checked = this.getAttribute("writerChecked") == "true";
                        break;
                    case "keyword":
                        this.#elements["keyword"].value = this.getAttribute("keyword");
                        break;
                    case "startDt":
                        if (this.getAttribute("startDt")) {
                            this.#elements["startDt"].value = this.getAttribute("startDt");
                        }
                        break;
                    case "finishDt":
                        if (this.getAttribute("finishDt")) {
                            this.#elements["finishDt"].value = this.getAttribute("finishDt");
                        }
                        break;
                }
            }
        }

        finishedOnClick(action) {

        }

        titleCheckedOnClick(action) {

        }

        writerCheckedOnClick(action) {

        }

        #bind() {
            console.log("#bind");
            this.#elements['form'] = this.shadowRoot.querySelector("form");
            this.#elements['finished'] = this.shadowRoot.querySelector("input[name='finished']");
            this.#elements['titleChecked'] = this.shadowRoot.querySelector("input[name='types'][value='t']");
            this.#elements['writerChecked'] = this.shadowRoot.querySelector("input[name='types'][value='w']");
            this.#elements['keyword'] = this.shadowRoot.querySelector("input[name='keyword']");
            this.#elements['startDt'] = this.shadowRoot.querySelector("input[name='startDt']");
            this.#elements['finishDt'] = this.shadowRoot.querySelector("input[name='finishDt']");

            this.#elements["finished"].addEventListener("click", function (e) {
                store.dispatch({
                    componentId: this.componentId,
                    type: FINISHED_ON_CLICK,
                    data: {
                        titleChecked: this.#elements['finished'].checked,
                    }
                });
            }.bind(this), false);
            this.#elements["titleChecked"].addEventListener("click", function (e) {
                store.dispatch({
                    componentId: this.componentId,
                    type: TITLE_CHECKED_ON_CLICK,
                    data: {
                        titleChecked: this.#elements['titleChecked'].checked,
                    }
                });
            }.bind(this), false);
            this.#elements["writerChecked"].addEventListener("click", function (e) {
                store.dispatch({
                    componentId: this.componentId,
                    type: WRITER_CHECKED_ON_CLICK,
                    data: {
                        titleChecked: this.#elements['writerChecked'].checked,
                    }
                });
            }.bind(this), false);
        }

        connectedCallback() {
            console.log("connectedCallback");
            this.#bind();
            this.render();
        }

        constructor() {
            super();
            this.attachShadow({ mode: 'open'});

            // Define the template
            const template = document.createElement("template");
            template.innerHTML = `
                <link href="/resources/bootstrap.min.css" rel="stylesheet">
                <form action="" method="get">
                    <div class="mb-3">
                        <input type="checkbox" name="finished"> 완료여부
                    </div>
                    <div class="mb-3">
                        <input type="checkbox" name="types" value="t"> 제목
                        <input type="checkbox" name="types" value="w"> 작성자
                        <input type="text" name="keyword" class="form-control" value="">
                    </div>
                    <div class="input-group mb-3 dueDtDiv">
                        <input type="date" name="startDt" class="form-control">
                        <input type="date" name="finishDt" class="form-control">
                    </div>
                    <div class="input-group mb-3">
                        <div class="float-end">
                            <button class="btn btn-primary" type="submit">검색</button>
                            <button class="btn btn-info" type="reset">초기화</button>
                        </div>
                    </div>
                </form>
                `;
            this.shadowRoot.appendChild(template.content.cloneNode(true));
        }

        disconnectedCallback() {
            // browser calls this method when the element is removed from the document
            // (can be called many times if an element is repeatedly added/removed)
        }

        getState() {
            return this.#elements["form"] === undefined ? {} : {
                action: this.#elements["form"].action,
                finished: this.#elements["finished"].checked,
                titleChecked: this.#elements["title-checked"].checked,
                writerChecked: this.#elements["writer-checked"].checked,
                keyword: this.#elements["keyword"].value,
                startDate: this.#elements["start-date"].value,
                finishDate: this.#elements["finish-date"].value
            };
        }

        render() {
            var state = store.getState();

            this.#elements["form"].action = state.action;
            this.#elements["finished"].checked = state.finished;
            this.#elements["titleChecked"].checked = state.titleChecked;
            this.#elements["writerChecked"].checked = state.writerChecked;
            this.#elements["keyword"].value = state.keyword;
            this.#elements["startDt"].value = state.startDt;
            this.#elements["finishDt"].value = state.finishDt;
        }
    }

    customElements.define("todo-search-component", TodoSearchComponent);

    function reducer(state, action) {
        console.log(state, action);
        if (state === undefined) {
            return {
                action: "${searchComponent["action"]}",
                finished: ${searchComponent["finished"]},
                titleChecked: ${searchComponent["titleChecked"]},
                writerChecked: ${searchComponent["writerChecked"]},
                keyword: "${searchComponent["keyword"]}",
                startDt: "${searchComponent['startDt']}",
                finishDt: "${searchComponent['finishDt']}"
            };
        }

        let component = Redux.queryComponent(action);
        let actionHandler = Redux.queryActionHandler(component, action);
        let newState;
        if (actionHandler) {
            actionHandler(action);
            newState = Object.assign({}, state, component.getState());

            for(let i = 0; i < Redux.components.length; ++i) {
                if (i === action.componentId) {
                    continue;
                }
                component = Redux.components[i];
                actionHandler = Redux.queryActionHandler(component, action);
                if (actionHandler) {
                    actionHandler(action);
                    newState = Object.assign(newState, component.getState());
                }
            }
        }
        return newState;
    }

    var store = Redux.createStore(
        reducer,
        window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
    );

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
            const todoSearchComponent = document.querySelector("todo-search-component");

            store.subscribe(todoSearchComponent.render.bind(todoSearchComponent));

            // error 출력
            <c:if test="${error != ''}">
            alert("${error}");
            </c:if>
        }, false)
    </script>
</body>
</html>
