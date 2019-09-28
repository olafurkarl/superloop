import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { fetchTodos } from '../../actions';

const TodoList = ({ todoList, getList }) => {
  useEffect(() => {
    getList();
  });

  let list = '';
  if (todoList) {
    list = todoList.map((todo) => (
      <div key={`${todo.id}`} className="row">
        <div>{todo.name}</div>
      </div>
    ));
  }

  return (
    <div>
      {list}
    </div>
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
