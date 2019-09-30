import React, { useState } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Alert from 'react-bootstrap/Alert';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import TodoAdd from '../TodoAdd/TodoAdd';
import TodoList from '../TodoList/TodoList';
import { clearError } from '../../actions';

function TodoContainer({ error, dismissError }) {
  const [currentStatus, changeStatus] = useState('All');

  return (
    <div>
      {error !== ''
      && (
        <Alert
          variant="danger"
          dismissible
          onClose={() => dismissError()}
        >
          {error}
        </Alert>
      )}
      <Container className="justify-content-md-center" fluid>
        <Row className="m-1">
          <Col className="text-center">
            <Button
              variant="secondary"
              onClick={() => changeStatus('Pending')}
              block
              active={currentStatus === 'Pending'}
            >
              Pending
            </Button>
          </Col>
          <Col className="text-center">
            <Button
              variant="secondary"
              onClick={() => changeStatus('Done')}
              block
              active={currentStatus === 'Done'}
            >
              Done
            </Button>
          </Col>
          <Col className="text-center">
            <Button
              variant="secondary"
              onClick={() => changeStatus('All')}
              block
              active={currentStatus === 'All'}
            >
              All
            </Button>
          </Col>
        </Row>
        <Row className="m-3">
          <Col className="text-center">
            <TodoAdd />
          </Col>
        </Row>
        <Row className="m-xl-4">
          <Col className="text-center">
            <TodoList statusShowing={currentStatus} />
          </Col>
        </Row>
      </Container>
    </div>
  );
}

TodoContainer.propTypes = {
  error: PropTypes.string,
  dismissError: PropTypes.func.isRequired,
};

TodoContainer.defaultProps = {
  error: '',
};

const mapStateToProps = (state) => ({
  error: state.error,
});

const mapDispatchToProps = {
  dismissError: clearError,
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(TodoContainer);
