const FINISHED_ON_CLICK = "FINISHED_ON_CLICK";
const TITLE_CHECKED_ON_CLICK = "TITLE_CHECKED_ON_CLICK";
const WRITER_CHECKED_ON_CLICK = "WRITER_CHECKED_ON_CLICK";

class TodoSearchComponent extends WebComponent {
    #elements = [];

    static get observedAttributes() {
        return ['action', 'finished', 'titleChecked', 'writerChecked', 'keyword', 'startDt', 'finishDt']
    }

    constructor() {
        super();

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

    #bind() {
        this.#elements['form'] = this.shadowRoot.querySelector("form");
        this.#elements['finished'] = this.shadowRoot.querySelector("input[name='finished']");
        this.#elements['titleChecked'] = this.shadowRoot.querySelector("input[name='types'][value='t']");
        this.#elements['writerChecked'] = this.shadowRoot.querySelector("input[name='types'][value='w']");
        this.#elements['keyword'] = this.shadowRoot.querySelector("input[name='keyword']");
        this.#elements['startDt'] = this.shadowRoot.querySelector("input[name='startDt']");
        this.#elements['finishDt'] = this.shadowRoot.querySelector("input[name='finishDt']");

        this.#elements["finished"].addEventListener("click", function (e) {
            Redux.dispatch(
                this,
                FINISHED_ON_CLICK,
                {
                    titleChecked: this.#elements['finished'].checked,
                }
            );
        }.bind(this), false);
        this.#elements["titleChecked"].addEventListener("click", function (e) {
            Redux.dispatch(
                this,
                TITLE_CHECKED_ON_CLICK,
                {
                    titleChecked: this.#elements['titleChecked'].checked,
                }
            );
        }.bind(this), false);
        this.#elements["writerChecked"].addEventListener("click", function (e) {
            Redux.dispatch(
                this,
                WRITER_CHECKED_ON_CLICK,
                {
                    titleChecked: this.#elements['writerChecked'].checked,
                }
            );
        }.bind(this), false);
    }

    componentOnLoad(action) {
        this.#bind();

        this.#elements["form"].action = this.getAttribute("action");
        this.#elements["finished"].checked = this.getAttribute("finished") === "true";
        this.#elements["titleChecked"].checked = this.getAttribute("titleChecked") === "true";
        this.#elements["writerChecked"].checked = this.getAttribute("writerChecked") === "true";
        this.#elements["keyword"].value = this.getAttribute("keyword");
        this.#elements["startDt"].value = this.getAttribute("startDt");
        this.#elements["finishDt"].value = this.getAttribute("finishDt");
    }

    _getComponentState() {
        return {
            action: this.#elements["form"].action,
            finished: this.#elements["finished"].checked,
            titleChecked: this.#elements["titleChecked"].checked,
            writerChecked: this.#elements["writerChecked"].checked,
            keyword: this.#elements["keyword"].value,
            startDt: this.#elements["startDt"].value,
            finishDt: this.#elements["finishDt"].value
        };
    }

    actionAttributeOnChange(action) {
        this.#elements["form"].action = this.getAttribute("action");
    }

    finishedAttributeOnChange(action) {
        this.#elements["finished"].checked = this.getAttribute("finished") === "true";
    }

    titleCheckedAttributeOnChange(action) {
        this.#elements["titleChecked"].checked = this.getAttribute("titleChecked") === "true";
    }

    writerCheckedAttributeOnChange(action) {
        this.#elements["writerChecked"].checked = this.getAttribute("writerChecked") === "true";
    }

    keywordAttributeOnChange(action) {
        this.#elements["keyword"].value = this.getAttribute("keyword");
    }

    startDtAttributeOnChange(action) {
        if (this.getAttribute("startDt")) {
            this.#elements["startDt"].value = this.getAttribute("startDt");
        }
    }

    finishDtAttributeOnChange(action) {
        if (this.getAttribute("finishDt")) {
            this.#elements["finishDt"].value = this.getAttribute("finishDt");
        }
    }

    finishedOnClick(action) {

    }

    titleCheckedOnClick(action) {

    }

    writerCheckedOnClick(action) {

    }

    render() {
        var state = Redux.queryComponentState(this);

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