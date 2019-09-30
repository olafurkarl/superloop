import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Button from 'react-bootstrap/Button';
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
      <Button
        onClick={handleClick}
      >
        Add new TODO
      </Button>
      {addItemVisible
      && (
        <div>
          <TodoView
            readOnly={false}
            onSubmit={submitItem}
            setVisible={setIsAddItemVisible}
            visible={addItemVisible}
            title="Add TODO Item"
          />
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
