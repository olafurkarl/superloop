import React from 'react';
import PropTypes from 'prop-types';

const TodoCheck = ({
  todoIndex, todoId, isChecked, onCheck,
}) => (
  <input type="checkbox" onChange={() => onCheck(todoId, todoIndex)} checked={isChecked} />
);

TodoCheck.propTypes = {
  todoId: PropTypes.number.isRequired,
  todoIndex: PropTypes.number.isRequired,
  isChecked: PropTypes.bool,
  onCheck: PropTypes.func.isRequired,
};

TodoCheck.defaultProps = {
  isChecked: false,
};

export default TodoCheck;
