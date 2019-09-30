import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import TodoAdd from '../TodoAdd/TodoAdd';
import TodoList from '../TodoList/TodoList';

function TodoContainer() {
  const [currentStatus, changeStatus] = useState('Pending');
  return (
    <div>
      <Container className="justify-content-md-center">
        <Row className="m-1">
          <Col className="text-center">
            <Button
              onClick={() => changeStatus('Pending')}
              block
              active={currentStatus === 'Pending'}
            >
              Pending
            </Button>
          </Col>
          <Col className="text-center">
            <Button
              onClick={() => changeStatus('Done')}
              block
              active={currentStatus === 'Done'}
            >
              Done
            </Button>
          </Col>
          <Col className="text-center">
            <Button
              onClick={() => changeStatus('All')}
              block
              active={currentStatus === 'All'}
            >
              All
            </Button>
          </Col>
        </Row>
        <Row className="m-1">
          <Col className="text-center">
            <TodoAdd />
          </Col>
        </Row>
        <Row className="m-1">
          <Col className="text-center">
            <TodoList statusShowing={currentStatus} />
          </Col>
        </Row>
      </Container>
    </div>
  );
}


export default TodoContainer;
