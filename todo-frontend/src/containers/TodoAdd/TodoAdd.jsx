import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { addItem } from '../../actions';
import TodoView from '../TodoView/TodoView';

const TodoAdd = ({ submitItem }) => {
  const [addItemVisible, setIsAddItemVisible] = useState(false);

  const handleClick = () => {
    setIsAddItemVisible(true);
  };

  return (
    <div>
      <button type="button" onClick={handleClick}>Add new TODO</button>
      {addItemVisible
      && (
        <div>
          <TodoView readOnly={false} onSubmit={submitItem} setVisible={setIsAddItemVisible} />
        </div>
      )}
    </div>
  );
};

TodoAdd.propTypes = {
  submitItem: PropTypes.func.isRequired,
};

const mapDispatchToProps = {
  submitItem: addItem,
};

export default connect(
  null,
  mapDispatchToProps,
)(TodoAdd);
