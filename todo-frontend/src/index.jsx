import React from 'react';

import { render } from 'react-dom';
import thunk from 'redux-thunk';
import { applyMiddleware, createStore } from 'redux';
import { Provider } from 'react-redux';

import reducer from './reducers';

import './index.css';

import App from './components/App/App';

const store = createStore(
  reducer,
  applyMiddleware(thunk),
);

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root'),
);
