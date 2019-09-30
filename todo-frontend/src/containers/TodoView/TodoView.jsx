import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';
import 'react-datepicker/dist/react-datepicker.css';

const TodoView = ({
  name, description,
  status, dueDate,
  readOnly, onSubmit,
  setVisible, todoIndex,
  todoId, visible, title,
}) => {
  const [nameText, setNameText] = useState(name);
  const [descriptionText, setDescriptionText] = useState(description);
  const [selectedDate, setSelectedDate] = useState(new Date(dueDate));

  const onNameChange = (e) => {
    setNameText(e.target.value);
  };
  const onDescriptionChange = (e) => {
    setDescriptionText(e.target.value);
  };
  const onDateChange = (date) => {
    setSelectedDate(date);
  };

  const onSubmitButtonClick = () => {
    const todoItem = {
      id: todoId,
      name: nameText,
      description: descriptionText,
      status,
      dueDate: selectedDate.toJSON(),
    };
    onSubmit(todoItem, todoIndex);
    setVisible(false);
  };

  return (
    <Modal show={visible} onHide={() => setVisible(false)}>
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <Form>
          <Form.Group as={Row}>
            <Form.Label column sm={3}>
              Name
            </Form.Label>
            <Col sm={10}>
              <Form.Control
                type="text"
                placeholder="New item"
                value={nameText}
                onChange={onNameChange}
              />
            </Col>
          </Form.Group>

          <Form.Group as={Row}>
            <Form.Label column sm={3}>
              Description
            </Form.Label>
            <Col sm={10}>
              <Form.Control
                as="textarea"
                rows="3"
                placeholder="Optional description"
                value={descriptionText}
                onChange={onDescriptionChange}
              />
            </Col>
          </Form.Group>

          <Form.Group as={Row}>
            <Form.Label column sm={3}>
              Due date
            </Form.Label>
            <Col sm={10}>
              <DatePicker
                selected={selectedDate}
                onChange={!readOnly ? onDateChange : undefined}
              />
            </Col>
          </Form.Group>
        </Form>

      </Modal.Body>
      {!readOnly
      && (
        <Modal.Footer>
          <Button
            variant="primary"
            onClick={onSubmitButtonClick}
          >
            Save
          </Button>
          <Button
            variant="secondary"
            onClick={() => setVisible(false)}
          >
            Cancel
          </Button>
        </Modal.Footer>
      )}

    </Modal>
  );
};

TodoView.propTypes = {
  name: PropTypes.string,
  description: PropTypes.string,
  dueDate: PropTypes.string,
  status: PropTypes.string,
  readOnly: PropTypes.bool,
  onSubmit: PropTypes.func,
  setVisible: PropTypes.func,
  todoIndex: PropTypes.number,
  todoId: PropTypes.number,
  visible: PropTypes.bool.isRequired,
  title: PropTypes.string.isRequired,
};

TodoView.defaultProps = {
  name: 'New item',
  description: '',
  dueDate: new Date().toUTCString(),
  status: 'Pending',
  readOnly: true,
  onSubmit: () => {},
  setVisible: () => {},
  todoIndex: null,
  todoId: null,
};

export default TodoView;
