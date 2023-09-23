Redux.components = [];
Redux.componentId = null;
Redux.registerComponent = function(component) {
    Redux.components.push(component);
    return Redux.components.length - 1;
}
Redux.queryComponent = function (action) {
    return Redux.components.find((element, index, arr) => element === action.component);
}
Redux.queryComponentState = function (component) {
    return Redux.store.getState()[component.componentId];
}
Redux.queryComponentActionHandler = function (component, action) {
    const handler = eval("component." + snakeToCamel(action.type));
    return handler ? handler.bind(component) : null;
}
Redux.reducer = function (state, action) {
    if (action.hasOwnProperty("component") === false) {
        return;
    }

    let component = Redux.queryComponent(action);
    let actionHandler = Redux.queryComponentActionHandler(component, action);
    let newState;
    if (actionHandler) {
        actionHandler(action);
        newState = Object.assign({}, state, component.getComponentState());

        if (action.type !== COMPONENT_ON_LOAD) {
            for(let i = 0; i < Redux.components.length; ++i) {
                component = Redux.components[i];
                if (component === action.component) {
                    continue;
                }

                actionHandler = Redux.queryComponentActionHandler(component, action);
                if (actionHandler) {
                    actionHandler(action);
                    newState = Object.assign(newState, component.getComponentState());
                }
            }
        }
    }
    return newState;
}
Redux.store = Redux.createStore(
    Redux.reducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
Redux.dispatch = function (component, type, data) {
    Redux.store.dispatch({
        component: component,
        type: type,
        data: data ?? {}
    });
}
Redux.subscribeAll = function () {
    let component = null;
    for (let i =0; i < Redux.components.length; ++i) {
        component = Redux.components[i];
        Redux.store.subscribe(component.render.bind(component));
    }
}