import React, { useState } from 'react';
import PropTypes from 'prop-types';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const TodoView = ({
  name, description, dueDate, readOnly, onSubmit,
}) => {
  const [nameText, setNameText] = useState(name);
  const [descriptionText, setDescriptionText] = useState(description);
  const [selectedDate, setSelectedDate] = useState(dueDate);

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
      name: nameText,
      description: descriptionText,
      status: 'Pending',
      dueDate: selectedDate.toJSON(),
    };
    onSubmit(todoItem);
  };

  return (
    <div>
      <input
        type="text"
        placeholder={nameText}
        readOnly={readOnly}
        onChange={!readOnly ? onNameChange : undefined}
      />
      <input
        type="text"
        placeholder={descriptionText}
        readOnly={readOnly}
        onChange={!readOnly ? onDescriptionChange : undefined}
      />
      <DatePicker
        selected={selectedDate}
        onChange={!readOnly ? onDateChange : undefined}
      />
      {!readOnly && <button type="button" onClick={onSubmitButtonClick}>Save</button>}
    </div>
  );
};

TodoView.propTypes = {
  name: PropTypes.string,
  description: PropTypes.string,
  dueDate: PropTypes.instanceOf(Date),
  readOnly: PropTypes.bool,
  onSubmit: PropTypes.func,
};

TodoView.defaultProps = {
  name: 'New item',
  description: '',
  dueDate: new Date(),
  readOnly: true,
  onSubmit: () => {},
};

export default TodoView;
