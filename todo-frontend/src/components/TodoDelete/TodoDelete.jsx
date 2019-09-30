import React from 'react';
import Button from 'react-bootstrap/Button';
import PropTypes from 'prop-types';

const TodoDelete = ({ todoIndex, todoId, onDelete }) => (
  <Button variant="danger" onClick={() => onDelete(todoId, todoIndex)}>Delete</Button>
);

TodoDelete.propTypes = {
  todoId: PropTypes.number.isRequired,
  todoIndex: PropTypes.number.isRequired,
  onDelete: PropTypes.func.isRequired,
};

export default TodoDelete;
