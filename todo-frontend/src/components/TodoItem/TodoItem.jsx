import React from 'react';
import PropTypes from 'prop-types';

const TodoItem = ({ name, description, status }) => (
  <div>
    <div>{name}</div>
    <div>{description}</div>
    <div>{status}</div>
  </div>
);

TodoItem.propTypes = {
  name: PropTypes.string.isRequired,
  description: PropTypes.string,
  status: PropTypes.string,
};

TodoItem.defaultProps = {
  description: '',
  status: 'Pending',
};

export default TodoItem;
