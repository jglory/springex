class TodoSearchComponent extends HTMLElement {
    static get observedAttributes() {
        return ['action', 'page-size', 'finished', 'title-checked', 'writer-checked', 'keyword', 'start-dt', 'finish-dt']
    }

    constructor() {
        super();
        this.attachShadow({ mode: 'open'});

        // Define the template
        const template = document.createElement("template");
        template.innerHTML = `
                <link href="/resources/bootstrap.min.css" rel="stylesheet">
                <form action="" method="get">
                    <input type="hidden" name="size" value="">
                    <div class="mb-3">
                        <input type="checkbox" name="finished"> 완료여부
                    </div>
                    <div class="mb-3">
                        <input type="checkbox" name="types" value="t"> 제목
                        <input type="checkbox" name="types" value="w"> 작성자
                        <input type="text" name="keyword" class="form-control" value="">
                    </div>
                    <div class="input-group mb-3 dueDtDiv">
                        <input type="date" name="start-dt" class="form-control">
                        <input type="date" name="finish-dt" class="form-control">
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

    connectedCallback() {
        let ref = this.shadowRoot.querySelector("form");
        ref.action = this.getAttribute("action");

        ref = this.shadowRoot.querySelector("input[name='size']");
        ref.value = this.getAttribute("page-size");

        ref = this.shadowRoot.querySelector("input[name='finished']");
        ref.checked = this.getAttribute("finished") == "true";

        ref = this.shadowRoot.querySelector("input[name='types'][value='t']");
        ref.checked = this.getAttribute("title-checked") == "true";

        ref = this.shadowRoot.querySelector("input[name='types'][value='w']");
        ref.checked = this.getAttribute("writer-checked") == "true";

        ref = this.shadowRoot.querySelector("input[name='keyword']");
        ref.value = this.getAttribute("keyword");

        ref = this.shadowRoot.querySelector("input[name='finish-dt']");
        if (this.getAttribute("start-dt")) {
            ref.value = this.getAttribute("start-dt");
        }

        ref = this.shadowRoot.querySelector("input[name='finish-dt']");
        if (this.getAttribute("finish-dt")) {
            ref.value = this.getAttribute("finish-dt");
        }
    }

    disconnectedCallback() {
        // browser calls this method when the element is removed from the document
        // (can be called many times if an element is repeatedly added/removed)
    }

    attributeChangedCallback(name, oldValue, newValue) {
        let ref = null;

        switch (name) {
            case "action":
                this.shadowRoot.querySelector("form").action = this.getAttribute("action");
                break;
            case "page-size":
                this.shadowRoot.querySelector("input[name='size']").value = this.getAttribute("page-size");
                break;
            case "finished":
                this.shadowRoot.querySelector("input[name='finished']").checked = this.getAttribute("finished") == "true";
                break;
            case "title-checked":
                this.shadowRoot.querySelector("input[name='types'][value='t']").checked = this.getAttribute("title-checked") == "true";
                break;
            case "writer-checked":
                this.shadowRoot.querySelector("input[name='types'][value='w']").checked = this.getAttribute("writer-checked") == "true";
                break;
            case "keyword":
                this.shadowRoot.querySelector("input[name='keyword']").value = this.getAttribute("keyword");
                break;
            case "start-dt":
                if (this.getAttribute("start-dt")) {
                    this.shadowRoot.querySelector("input[name='start-dt']").value = this.getAttribute("start-dt");
                }
                break;
            case "finish-dt":
                if (this.getAttribute("finish-dt")) {
                    this.shadowRoot.querySelector("input[name='finish-dt']").value = this.getAttribute("finish-dt");
                }
                break;
        }
    }

    adoptedCallback() {
        // called when the element is moved to a new document
        // (happens in document.adoptNode, very rarely used)
    }
}

customElements.define("todo-search-component", TodoSearchComponent);