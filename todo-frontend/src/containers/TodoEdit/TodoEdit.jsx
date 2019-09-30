import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import TodoView from '../TodoView/TodoView';
import { performEditItem } from '../../actions';

const TodoEdit = ({ todoIndex, todoItem, editItem }) => {
  const [editEnabled, setEditEnabled] = useState(false);
  const enableEdit = () => {
    setEditEnabled(true);
  };

  return (
    <div>
      <button type="button" onClick={() => enableEdit()}>Edit</button>
      {editEnabled && (
        <TodoView
          readOnly={false}
          setVisible={setEditEnabled}
          onSubmit={editItem}
          todoIndex={todoIndex}
          todoId={todoItem.id}
          name={todoItem.name}
          description={todoItem.description}
          dueDate={todoItem.dueDate}
        />
      )}
    </div>
  );
};

TodoEdit.propTypes = {
  todoIndex: PropTypes.number.isRequired,
  todoItem: PropTypes.shape({
    id: PropTypes.number,
    name: PropTypes.string,
    description: PropTypes.string,
    status: PropTypes.string,
    dueDate: PropTypes.string,
  }).isRequired,
  editItem: PropTypes.func.isRequired,
};

const mapDispatchToProps = {
  editItem: performEditItem,
};

export default connect(
  null,
  mapDispatchToProps,
)(TodoEdit);
