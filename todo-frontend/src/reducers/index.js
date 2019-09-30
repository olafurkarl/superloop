import update from 'immutability-helper';
import {
  FETCH_TODO_LIST,
  MARK_ITEM_DONE,
  RECEIVE_TODO_LIST,
  RECEIVED_ERROR,
  REMOVE_ITEM,
  ADD_ITEM,
  EDIT_ITEM,
  DISMISS_ERROR,
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

  if (action.type === REMOVE_ITEM) {
    return update(state, {
      todoList: {
        $splice: [[action.index, 1]],
      },
    });
  }

  if (action.type === RECEIVED_ERROR) {
    const { defaultMessage } = action.error.response.data.errors[0];
    return update(state, {
      error: {
        $set: defaultMessage,
      },
    });
  }

  if (action.type === DISMISS_ERROR) {
    return update(state, {
      error: {
        $set: '',
      },
    });
  }

  if (action.type === ADD_ITEM) {
    const newItem = action.todoItem;
    newItem.id = action.id;
    return update(state, {
      todoList: {
        $push: [newItem],
      },
    });
  }

  if (action.type === EDIT_ITEM) {
    return update(state, {
      todoList: {
        [action.todoIndex]: {
          $set: action.todoItem,
        },
      },
    });
  }

  return state;
};

export default reducer;
