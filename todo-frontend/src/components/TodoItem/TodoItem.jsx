import React from 'react';
import PropTypes from 'prop-types';

const TodoItem = ({ todoItem }) => (
  <div>
    <div>{`Name: ${todoItem.name}`}</div>
    <div>{todoItem.description !== '' && `Description: ${todoItem.description}`}</div>
    <div>{`Status: ${todoItem.status}`}</div>
    <div>{`Due date: ${new Date(todoItem.dueDate).toLocaleDateString()}`}</div>
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
