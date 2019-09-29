import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { deleteItem, fetchTodos, markItemAsDone } from '../../actions';
import TodoItem from '../../components/TodoItem/TodoItem';
import TodoCheck from '../../components/TodoCheck/TodoCheck';
import TodoDelete from '../../components/TodoDelete/TodoDelete';

const TodoList = ({
  todoList, getList, setItemDone, removeItem,
}) => {
  useEffect(() => {
    getList();
  }, []);

  let render = '';
  if (todoList && todoList.length > 0) {
    render = todoList.map((todo, index) => (
      <li key={`${todo.id}`}>
        <TodoItem todoItem={todo} />
        <TodoCheck todoIndex={index} todoId={todo.id} isChecked={todo.status === 'Done'} onCheck={setItemDone} />
        <TodoDelete todoIndex={index} onDelete={removeItem} todoId={todo.id} />
      </li>
    ));
  } else {
    render = <div>No items added yet!</div>;
  }

  return (
    <ul>
      {render}
    </ul>
  );
};

TodoList.propTypes = {
  todoList: PropTypes.arrayOf(PropTypes.shape),
  getList: PropTypes.func.isRequired,
  setItemDone: PropTypes.func.isRequired,
  removeItem: PropTypes.func.isRequired,
};

TodoList.defaultProps = {
  todoList: [],
};

const mapStateToProps = (state) => ({
  todoList: state.todoList,
  loading: state.loading,
});

const mapDispatchToProps = {
  getList: fetchTodos,
  setItemDone: markItemAsDone,
  removeItem: deleteItem,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(TodoList);
