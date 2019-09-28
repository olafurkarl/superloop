import update from 'immutability-helper';
import {
  FETCH_TODO_LIST, MARK_ITEM_DONE, RECEIVE_TODO_LIST, RECEIVED_ERROR,
} from '../actions';

const initialState = {
  todoList: [],
  error: '',
  loading: false,
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

  if (action.type === MARK_ITEM_DONE) {
    return update(state, {
      todoList: {
        [action.index]: {
          status: { $set: 'Done' },
        },
      },
    });
  }

  if (action.type === RECEIVED_ERROR) {
    return update(state, {
      error: {
        $set: action.error,
      },
    });
  }

  return state;
};

export default reducer;
