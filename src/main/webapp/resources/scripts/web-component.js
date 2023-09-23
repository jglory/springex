const COMPONENT_ON_LOAD = "COMPONENT_ON_LOAD";  // 컴포넌트 로드

class WebComponent extends HTMLElement {
    _getComponentState() {
        throw "WebComponent._getComponentState 를 구현하여 주십시오.";
    }

    getComponentState() {
        let state = [];
        state[this.componentId] = this._getComponentState();
        return state;
    }

    constructor() {
        super();
        this.componentId = Redux.registerComponent(this);
    }
}