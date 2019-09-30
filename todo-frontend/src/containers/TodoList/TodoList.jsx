import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
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
          <Card key={`${todo.id}`}>
            <Card.Header>
              <Container>
                <Row>
                  <Col>
                    <TodoCheck
                      todoIndex={index}
                      todoId={todo.id}
                      isChecked={todo.status === 'Done'}
                      onCheck={setItemDone}
                    />
                  </Col>
                  <Col sm={7} className="m-1">
                    {todo.name}
                  </Col>
                  <Col>
                    <Accordion.Toggle as={Button} variant="info" eventKey={todo.id}>
                      Details
                    </Accordion.Toggle>
                  </Col>
                  <Col>
                    <TodoDelete todoIndex={index} onDelete={removeItem} todoId={todo.id} />
                  </Col>
                </Row>
              </Container>
            </Card.Header>
            <Accordion.Collapse eventKey={todo.id}>
              <div className="m-4">
                <TodoItem todoItem={todo} />
                <TodoEdit todoIndex={index} todoItem={todo} />
              </div>
            </Accordion.Collapse>
          </Card>
        );
      }
      return null;
    });
  }
  if (!containsItem) {
    render = <div>No items in this list yet!</div>;
  }

  return (
    <Accordion>
      {render}
    </Accordion>
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
