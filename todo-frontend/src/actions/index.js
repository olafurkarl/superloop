import axios from 'axios';

export const FETCH_TODO_LIST = 'FETCH_TODO_LIST';
export const RECEIVE_TODO_LIST = 'RECEIVE_TODO_LIST';

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
        (error) => console.log('An error occurred.', error),
      )
      .then((json) => {
        dispatch(receivedTodoList(json));
      });
  };
}
