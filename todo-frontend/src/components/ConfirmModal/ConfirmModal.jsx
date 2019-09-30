import React from 'react';
import PropTypes from 'prop-types';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

const ConfirmModal = ({
  onConfirm, onCancel, title, visible, setVisible, message,
}) => (
  <Modal show={visible} onHide={() => setVisible(false)}>
    <Modal.Header closeButton>
      <Modal.Title>{title}</Modal.Title>
    </Modal.Header>
    <Modal.Body>
      {message}
    </Modal.Body>
    <Modal.Footer>
      <Button
        variant="primary"
        onClick={onConfirm}
      >
        Confirm
      </Button>
      <Button
        variant="secondary"
        onClick={onCancel}
      >
        Cancel
      </Button>
    </Modal.Footer>
  </Modal>
);

ConfirmModal.propTypes = {
  onConfirm: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
  title: PropTypes.string.isRequired,
  visible: PropTypes.bool.isRequired,
  setVisible: PropTypes.func.isRequired,
  message: PropTypes.string.isRequired,
};

export default ConfirmModal;
