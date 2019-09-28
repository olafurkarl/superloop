import axios from 'axios';

export const FETCH_TODO_LIST = 'FETCH_TODO_LIST';
export const RECEIVE_TODO_LIST = 'RECEIVE_TODO_LIST';
export const RECEIVED_ERROR = 'RECEIVED_ERROR';
export const MARK_ITEM_DONE = 'MARK_ITEM_DONE';
export const DELETE_ITEM = 'DELETE_ITEM';

export const receivedError = (error) => ({
  type: RECEIVED_ERROR,
  error,
});

export const receivedTodoList = (json) => ({
  type: RECEIVE_TODO_LIST,
  json,
});

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
      (error) => dispatch(receivedError(error)),
    );
}
