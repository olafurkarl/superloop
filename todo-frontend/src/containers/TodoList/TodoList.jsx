import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import ListGroup from 'react-bootstrap/ListGroup';
import { connect } from 'react-redux';
import {
  deleteItem,
  fetchTodos,
  markItemAsDone,
} from '../../actions';
import TodoItem from '../../components/TodoItem/TodoItem';
import TodoCheck from '../../components/TodoCheck/TodoCheck';
import TodoDelete from '../../components/TodoDelete/TodoDelete';
import TodoEdit from '../TodoEdit/TodoEdit';

const TodoList = ({
  todoList, getList, setItemDone, removeItem, statusShowing,
}) => {
  useEffect(() => {
    getList();
  }, [getList]);

  let containsItem = false;
  let render = null;
  if (todoList && todoList.length > 0) {
    render = todoList.map((todo, index) => {
      if (todo.status === statusShowing || statusShowing === 'All') {
        containsItem = true;
        return (
          <ListGroup.Item key={`${todo.id}`}>
            <TodoItem todoItem={todo} />
            <TodoCheck todoIndex={index} todoId={todo.id} isChecked={todo.status === 'Done'} onCheck={setItemDone} />
            <TodoDelete todoIndex={index} onDelete={removeItem} todoId={todo.id} />
            <TodoEdit todoIndex={index} todoItem={todo} />
          </ListGroup.Item>
        );
      }
      return null;
    });
  }
  if (!containsItem) {
    render = <div>No items in this list yet!</div>;
  }

  return (
    <ListGroup>
      {render}
    </ListGroup>
  );
};

TodoList.propTypes = {
  todoList: PropTypes.arrayOf(PropTypes.shape),
  getList: PropTypes.func.isRequired,
  setItemDone: PropTypes.func.isRequired,
  removeItem: PropTypes.func.isRequired,
  statusShowing: PropTypes.string.isRequired,
};

TodoList.defaultProps = {
  todoList: [],
};

const mapStateToProps = (state) => ({
  todoList: state.todoList,
});

const mapDispatchToProps = {
  getList: fetchTodos,
  setItemDone: markItemAsDone,
  removeItem: deleteItem,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(TodoList);
