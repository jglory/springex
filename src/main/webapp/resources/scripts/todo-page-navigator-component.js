class TodoPageNavigatorComponent extends WebComponent {
    #elements = [];

    static get observedAttributes() {
        return ['start', 'end', 'current', 'count', 'url']
    }

    constructor() {
        super();

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

    #bind() {

    }

    componentOnLoad(action) {
        this.#bind();

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
                <li class="page-item"><a class="page-link` + (i === current ? " active" : "") + `" href="` + url.replace("###", i) + `">${i}</a></li>
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
    }

    render() {
        var state = Redux.queryComponentState(this);
    }
}

customElements.define("todo-page-navigator-component", TodoPageNavigatorComponent);