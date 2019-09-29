import React from 'react';
import './App.css';
import TodoList from '../../containers/TodoList/TodoList';
import TodoAdd from '../../containers/TodoAdd/TodoAdd';

function App() {
  return (
    <div className="App">
      <TodoAdd />
      <TodoList />
    </div>
  );
}

export default App;
