const ATTRIBUTE_ON_CHANGE = "ATTRIBUTE_ON_CHANGE";
const FINISHED_ON_CLICK = "FINISHED_ON_CLICK";
const TITLE_CHECKED_ON_CLICK = "TITLE_CHECKED_ON_CLICK";
const WRITER_CHECKED_ON_CLICK = "WRITER_CHECKED_ON_CLICK";

class TodoSearchComponent extends WebComponent {
    #elements = [];

    static get observedAttributes() {
        return ['action', 'finished', 'titleChecked', 'writerChecked', 'keyword', 'startDt', 'finishDt']
    }

    adoptedCallback() {
        // called when the element is moved to a new document
        // (happens in document.adoptNode, very rarely used)
    }

    attributeChangedCallback(name, oldValue, newValue) {
        Redux.store.dispatch({
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
                    this.#elements["finished"].checked = this.getAttribute("finished") === "true";
                    break;
                case "titleChecked":
                    this.#elements["titleChecked"].checked = this.getAttribute("titleChecked") === "true";
                    break;
                case "writerChecked":
                    this.#elements["writerChecked"].checked = this.getAttribute("writerChecked") === "true";
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
        this.#elements['form'] = this.shadowRoot.querySelector("form");
        this.#elements['finished'] = this.shadowRoot.querySelector("input[name='finished']");
        this.#elements['titleChecked'] = this.shadowRoot.querySelector("input[name='types'][value='t']");
        this.#elements['writerChecked'] = this.shadowRoot.querySelector("input[name='types'][value='w']");
        this.#elements['keyword'] = this.shadowRoot.querySelector("input[name='keyword']");
        this.#elements['startDt'] = this.shadowRoot.querySelector("input[name='startDt']");
        this.#elements['finishDt'] = this.shadowRoot.querySelector("input[name='finishDt']");

        this.#elements["finished"].addEventListener("click", function (e) {
            Redux.store.dispatch({
                componentId: this.componentId,
                type: FINISHED_ON_CLICK,
                data: {
                    titleChecked: this.#elements['finished'].checked,
                }
            });
        }.bind(this), false);
        this.#elements["titleChecked"].addEventListener("click", function (e) {
            Redux.store.dispatch({
                componentId: this.componentId,
                type: TITLE_CHECKED_ON_CLICK,
                data: {
                    titleChecked: this.#elements['titleChecked'].checked,
                }
            });
        }.bind(this), false);
        this.#elements["writerChecked"].addEventListener("click", function (e) {
            Redux.store.dispatch({
                componentId: this.componentId,
                type: WRITER_CHECKED_ON_CLICK,
                data: {
                    titleChecked: this.#elements['writerChecked'].checked,
                }
            });
        }.bind(this), false);
    }

    connectedCallback() {
        this.#bind();
        Redux.store.dispatch({
            componentId: this.componentId,
            type: COMPONENT_ON_LOAD
        })
    }

    componentOnLoad(action) {
        this.#elements["form"].action = this.getAttribute("action");
        this.#elements["finished"].checked = this.getAttribute("finished") === "true";
        this.#elements["titleChecked"].checked = this.getAttribute("titleChecked") === "true";
        this.#elements["writerChecked"].checked = this.getAttribute("writerChecked") === "true";
        this.#elements["keyword"].value = this.getAttribute("keyword");
        this.#elements["startDt"].value = this.getAttribute("startDt");
        this.#elements["finishDt"].value = this.getAttribute("finishDt");
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
                <form action="" method="get">
                    <div class="mb-3">
                        <input type="checkbox" name="finished"> 완료여부
                    </div>
                    <div class="mb-3">
                        <input type="checkbox" name="types" value="t"> 제목
                        <input type="checkbox" name="types" value="w"> 작성자
                        <input type="text" name="keyword" class="form-control" value="">
                    </div>
                    <div class="input-group mb-3 dueDateDiv">
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

    _getComponentState() {
        return this.#elements["form"] === undefined ? {} : {
            action: this.#elements["form"].action,
            finished: this.#elements["finished"].checked,
            titleChecked: this.#elements["titleChecked"].checked,
            writerChecked: this.#elements["writerChecked"].checked,
            keyword: this.#elements["keyword"].value,
            startDt: this.#elements["startDt"].value,
            finishDt: this.#elements["finishDt"].value
        };
    }

    render() {
        var state = Redux.queryComponentState(Redux.store, this);

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