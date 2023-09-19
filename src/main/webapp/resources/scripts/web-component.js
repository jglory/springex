class WebComponent extends HTMLElement {
    componentId = null;

    _getComponentState() {
        throw "WebComponent._getComponentState 를 구현하여 주십시오.";
    }

    getComponentState() {
        let state = [];
        state[this.componentId] = this._getComponentState();
        return state;
    }
}