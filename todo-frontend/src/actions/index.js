import axios from 'axios';

export const FETCH_TODO_LIST = 'FETCH_TODO_LIST';
export const RECEIVE_TODO_LIST = 'RECEIVE_TODO_LIST';
export const RECEIVED_ERROR = 'RECEIVED_ERROR';
export const MARK_ITEM_DONE = 'MARK_ITEM_DONE';
export const REMOVE_ITEM = 'REMOVE_ITEM';
export const ADD_ITEM = 'ADD_ITEM';
export const EDIT_ITEM = 'EDIT_ITEM';
export const DISMISS_ERROR = 'DISMISS_ERROR';

export const receivedError = (error) => ({
  type: RECEIVED_ERROR,
  error,
});

export const dismissError = () => ({
  type: DISMISS_ERROR,
});

export const receivedTodoList = (json) => ({
  type: RECEIVE_TODO_LIST,
  json,
});

export function clearError() {
  return (dispatch) => dispatch(dismissError());
}

export const requestTodoList = () => ({
  type: FETCH_TODO_LIST,
});

export function fetchTodos() {
  return (dispatch) => {
    dispatch(requestTodoList());
    return axios.get('api/v1/getList')
      .then(
        (response) => response.data,
        (error) => dispatch(receivedError(error)),
      )
      .then((json) => {
        dispatch(receivedTodoList(json));
      });
  };
}

export const finishItem = (index) => ({
  type: MARK_ITEM_DONE,
  index,
});

export function markItemAsDone(id, index) {
  return (dispatch) => axios.post(`api/v1/markItemAsDone?itemId=${id}`)
    .then(
      (response) => {
        if (response.status === 200) {
          dispatch(finishItem(index));
        } else {
          receivedError(response.message);
        }
      },
      (error) => (receivedError(error)),
    );
}

export const removeItem = (index) => ({
  type: REMOVE_ITEM,
  index,
});

export function deleteItem(id, index) {
  return (dispatch) => axios.post(`api/v1/deleteItem?itemId=${id}`)
    .then(
      (response) => {
        if (response.status === 200) {
          dispatch(removeItem(index));
        } else {
          receivedError(response.message);
        }
      },
      (error) => dispatch(receivedError(error)),
    );
}

export const itemAdded = (todoItem, id) => ({
  type: ADD_ITEM,
  todoItem,
  id,
});

export function addItem(todoItem) {
  return (dispatch) => axios.post('api/v1/addItem', todoItem)
    .then(
      (response) => {
        if (response.status === 200) {
          dispatch(itemAdded(todoItem, response.data));
        } else {
          receivedError(response);
        }
      },
      (error) => dispatch(receivedError(error)),
    );
}

export const dispatchEdit = (todoItem, todoIndex) => ({
  type: EDIT_ITEM,
  todoItem,
  todoIndex,
});

export function performEditItem(todoItem, todoIndex) {
  return (dispatch) => axios.post('api/v1/editItem', todoItem)
    .then(
      (response) => {
        if (response.status === 200) {
          dispatch(dispatchEdit(todoItem, todoIndex));
        } else {
          receivedError(response);
        }
      },
      (error) => dispatch(receivedError(error)),
    );
}
