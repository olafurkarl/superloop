import React from 'react';
import PropTypes from 'prop-types';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSquare, faCheckSquare } from '@fortawesome/free-solid-svg-icons';

const TodoCheck = ({
  todoIndex, todoId, isChecked, onCheck,
}) => (
  <div
    role="button"
    tabIndex={todoIndex}
    onClick={() => !isChecked && onCheck(todoId, todoIndex)} // Should not be able to uncheck
    onKeyDown={(e) => e.key === 'Enter' && onCheck(todoId, todoIndex)} // Listen only to Enter key for keyboard input
  >
    <FontAwesomeIcon icon={isChecked ? faCheckSquare : faSquare} size="lg" />
  </div>

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
