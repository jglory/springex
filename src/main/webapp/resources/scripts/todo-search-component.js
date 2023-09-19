const ATTRIBUTE_ON_CHANGE = "ATTRIBUTE_ON_CHANGE";
const FINISHED_ON_CLICK = "FINISHED_ON_CLICK";
const TITLE_CHECKED_ON_CLICK = "TITLE_CHECKED_ON_CLICK";
const WRITER_CHECKED_ON_CLICK = "WRITER_CHECKED_ON_CLICK";

class TodoSearchComponent extends WebComponent {
    #elements = [];

    static get observedAttributes() {
        return ['action', 'finished', 'title-checked', 'writer-checked', 'keyword', 'start-date', 'finish-date']
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
                    this.#elements["finished"].checked = this.getAttribute("finished") == "true";
                    break;
                case "title-checked":
                    this.#elements["title-checked"].checked = this.getAttribute("title-checked") == "true";
                    break;
                case "writer-checked":
                    this.#elements["writer-checked"].checked = this.getAttribute("writer-checked") == "true";
                    break;
                case "keyword":
                    this.#elements["keyword"].value = this.getAttribute("keyword");
                    break;
                case "start-date":
                    if (this.getAttribute("start-date")) {
                        this.#elements["start-date"].value = this.getAttribute("start-date");
                    }
                    break;
                case "finish-date":
                    if (this.getAttribute("finish-date")) {
                        this.#elements["finish-date"].value = this.getAttribute("finish-date");
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
        this.#elements['title-checked'] = this.shadowRoot.querySelector("input[name='types'][value='t']");
        this.#elements['writer-checked'] = this.shadowRoot.querySelector("input[name='types'][value='w']");
        this.#elements['keyword'] = this.shadowRoot.querySelector("input[name='keyword']");
        this.#elements['start-date'] = this.shadowRoot.querySelector("input[name='from']");
        this.#elements['finish-date'] = this.shadowRoot.querySelector("input[name='to']");

        this.#elements["finished"].addEventListener("click", function (e) {
            Redux.store.dispatch({
                componentId: this.componentId,
                type: FINISHED_ON_CLICK,
                data: {
                    titleChecked: this.#elements['finished'].checked,
                }
            });
        }.bind(this), false);
        this.#elements["title-checked"].addEventListener("click", function (e) {
            Redux.store.dispatch({
                componentId: this.componentId,
                type: TITLE_CHECKED_ON_CLICK,
                data: {
                    titleChecked: this.#elements['title-checked'].checked,
                }
            });
        }.bind(this), false);
        this.#elements["writer-checked"].addEventListener("click", function (e) {
            Redux.store.dispatch({
                componentId: this.componentId,
                type: WRITER_CHECKED_ON_CLICK,
                data: {
                    titleChecked: this.#elements['writer-checked'].checked,
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
        this.#elements["title-checked"].checked = this.getAttribute("title-checked") === "true";
        this.#elements["writer-checked"].checked = this.getAttribute("writer-checked") === "true";
        this.#elements["keyword"].value = this.getAttribute("keyword");
        this.#elements["start-date"].value = this.getAttribute("start-date");
        this.#elements["finish-date"].value = this.getAttribute("finish-date");
    }

    constructor() {
        super();

        this.componentId = Redux.registerWebComponent(this);

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
                        <input type="date" name="from" class="form-control">
                        <input type="date" name="to" class="form-control">
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
            titleChecked: this.#elements["title-checked"].checked,
            writerChecked: this.#elements["writer-checked"].checked,
            keyword: this.#elements["keyword"].value,
            startDate: this.#elements["start-date"].value,
            finishDate: this.#elements["finish-date"].value
        };
    }

    render() {
        var state = Redux.queryComponentState(Redux.store, this);

        this.#elements["form"].action = state.action;
        this.#elements["finished"].checked = state.finished;
        this.#elements["title-checked"].checked = state.titleChecked;
        this.#elements["writer-checked"].checked = state.writerChecked;
        this.#elements["keyword"].value = state.keyword;
        this.#elements["start-date"].value = state.startDate;
        this.#elements["finish-date"].value = state.finishDate;
    }
}

customElements.define("todo-search", TodoSearchComponent);