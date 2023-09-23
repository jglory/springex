Redux.components = [];
Redux.componentId = null;
Redux.registerComponent = function(component) {
    Redux.components.push(component);
    return Redux.components.length - 1;
}
Redux.queryComponent = function (action) {
    return Redux.components[action.componentId];
}
Redux.queryComponentState = function (component) {
    return Redux.store.getState()[component.componentId];
}
Redux.queryActionHandler = function (component, action) {
    const handler = eval("component." + snakeToCamel(action.type));
    return handler ? handler.bind(component) : null;
}
Redux.reducer = function (state, action) {
    if (action.hasOwnProperty("componentId") === false) {
        return;
    }

    let component = Redux.queryComponent(action);
    let actionHandler = Redux.queryActionHandler(component, action);
    let newState;
    if (actionHandler) {
        actionHandler(action);
        newState = Object.assign({}, state, component.getComponentState());

        for(let i = 0; i < Redux.components.length; ++i) {
            if (i === action.componentId) {
                continue;
            }
            component = Redux.components[i];
            actionHandler = Redux.queryActionHandler(component, action);
            if (actionHandler) {
                actionHandler(action);
                newState = Object.assign(newState, component.getComponentState());
            }
        }
    }
    return newState;
}
Redux.store = Redux.createStore(
    Redux.reducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
Redux.subscribeAllComponentsToStore = function () {
    let component = null;
    for (let i =0; i < Redux.components.length; ++i) {
        component = Redux.components[i];
        Redux.store.subscribe(component.render.bind(component));
    }
}