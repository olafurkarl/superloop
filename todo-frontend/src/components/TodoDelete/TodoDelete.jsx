import React from 'react';
import PropTypes from 'prop-types';

const TodoDelete = ({ todoIndex, todoId, onDelete }) => (
  <button type="button" onClick={() => onDelete(todoId, todoIndex)}>Delete</button>
);

TodoDelete.propTypes = {
  todoId: PropTypes.number.isRequired,
  todoIndex: PropTypes.number.isRequired,
  onDelete: PropTypes.func.isRequired,
};

export default TodoDelete;
