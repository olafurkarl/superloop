import React from 'react';
import PropTypes from 'prop-types';
import TodoView from '../../containers/TodoView/TodoView';

const TodoItem = ({ todoItem }) => (
  <div>
    <div>{todoItem.name}</div>
    <TodoView
      name={todoItem.name}
      description={todoItem.description}
      status={todoItem.status}
      dueDate={new Date(todoItem.dueDate)}
    />
  </div>
);

TodoItem.propTypes = {
  todoItem: PropTypes.shape({
    name: PropTypes.string,
    description: PropTypes.string,
    status: PropTypes.string,
    dueDate: PropTypes.string,
  }).isRequired,
};

export default TodoItem;
