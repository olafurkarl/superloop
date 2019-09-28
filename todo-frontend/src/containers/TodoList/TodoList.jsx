import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { fetchTodos } from '../../actions';
import TodoItem from '../../components/TodoItem/TodoItem';

const TodoList = ({ todoList, getList }) => {
  useEffect(() => {
    getList();
  });

  let list = '';
  if (todoList) {
    list = todoList.map((todo) => (
      <li key={`${todo.id}`} className="row">
        <TodoItem name={todo.name} description={todo.description} status={todo.status} />
      </li>
    ));
  }

  return (
    <ul>
      {list}
    </ul>
  );
};

TodoList.propTypes = {
  todoList: PropTypes.arrayOf(PropTypes.shape),
  getList: PropTypes.func,
};

TodoList.defaultProps = {
  todoList: [],
  getList: () => {},
};

const mapStateToProps = (state) => ({
  todoList: state.todoList,
  loading: state.loading,
});

const mapDispatchToProps = {
  getList: fetchTodos,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(TodoList);
