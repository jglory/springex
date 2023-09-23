const COMPONENT_ON_LOAD = "COMPONENT_ON_LOAD";  // 컴포넌트 로드
const ATTRIBUTE_ON_CHANGE = "ATTRIBUTE_ON_CHANGE"; // 컴포넌트 속성값 변경됨

class WebComponent extends HTMLElement {
    #loaded = false;

    getComponentState() {
        let state = [];
        state[this.componentId] = this.hasComponentLoaded() && this._getComponentState ? this._getComponentState() : {};
        return state;
    }

    constructor() {
        super();
        this.componentId = Redux.registerComponent(this);
        this.attachShadow({ mode: 'open'});
    }

    connectedCallback() {
        this.#loaded = true;
        Redux.dispatch(this, COMPONENT_ON_LOAD);
    }

    hasComponentLoaded() {
        return this.#loaded;
    }

    disconnectedCallback() {
        // browser calls this method when the element is removed from the document
        // (can be called many times if an element is repeatedly added/removed)
    }

    attributeChangedCallback(name, oldValue, newValue) {
        // 컴포넌트가 로딩 완료가 되지 않았다면 처리도 의미가 없어 보인다. 분기 처리.
        if (this.hasComponentLoaded() === false) {
            return;
        }

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

    #attributeNameToOnChangeHandler(name) {
        const handler = eval("this." + name + "AttributeOnChange");
        return handler ? handler.bind(this) : undefined;
    }

    attributeOnChange(action) {
        const handler = this.#attributeNameToOnChangeHandler(action.data.name);
        if (handler) {
            handler(action);
        }
    }

    adoptedCallback() {
        // called when the element is moved to a new document
        // (happens in document.adoptNode, very rarely used)
    }
}