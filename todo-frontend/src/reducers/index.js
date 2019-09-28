import update from 'immutability-helper';
import { FETCH_TODO_LIST, RECEIVE_TODO_LIST } from '../actions';

const initialState = {
  todoList: [],
};

const reducer = (state = initialState, action) => {
  if (action.type === FETCH_TODO_LIST) {
    return update(state, {
      loading: {
        $set: true,
      },
    });
  }

  if (action.type === RECEIVE_TODO_LIST) {
    return update(state, {
      todoList: {
        $set: action.json,
      },
    });
  }

  return state;
};

export default reducer;
