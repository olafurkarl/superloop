import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import PropTypes from 'prop-types';
import ConfirmModal from '../../components/ConfirmModal/ConfirmModal';

const TodoDelete = ({ todoIndex, todoId, onDelete }) => {
  const [modalShowing, setShowModal] = useState(false);
  return (
    <div>
      <ConfirmModal
        onConfirm={() => onDelete(todoId, todoIndex)}
        title="Delete TODO"
        message="Are you sure you want to delete this TODO?"
        onCancel={() => setShowModal(false)}
        setVisible={setShowModal}
        visible={modalShowing}
      />
      <Button variant="danger" size="sm" className="m-1" onClick={() => setShowModal(true)}>Delete</Button>
    </div>
  );
};

TodoDelete.propTypes = {
  todoId: PropTypes.number.isRequired,
  todoIndex: PropTypes.number.isRequired,
  onDelete: PropTypes.func.isRequired,
};

export default TodoDelete;
