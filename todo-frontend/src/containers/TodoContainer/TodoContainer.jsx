import React, { useState } from 'react';
import TodoList from '../TodoList/TodoList';
import TodoAdd from '../TodoAdd/TodoAdd';

function TodoContainer() {
  const [currentStatus, changeStatus] = useState('Pending');
  return (
    <div>
      <button type="button" onClick={() => changeStatus('Pending')}>Pending</button>
      <button type="button" onClick={() => changeStatus('Done')}>Done</button>
      <button type="button" onClick={() => changeStatus('All')}>All</button>
      <TodoAdd />
      <TodoList statusShowing={currentStatus} />
    </div>
  );
}


export default TodoContainer;
