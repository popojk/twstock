import Form from 'react-bootstrap/Form';
import { Row, Col } from 'react-bootstrap';

function Selector(props) {


    return (
        <Row className="selector">
            <Col sm={12} md={6} lg={4} className="selectorItem">
                <Form.Select aria-label="Default select example">
                    <option>Open this select menu</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </Form.Select>
                <Form.Select aria-label="Default select example">
                    <option>Open this select menu</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                </Form.Select>
            </Col>
        </Row>
    )
}

export default Selector
