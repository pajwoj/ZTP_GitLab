import {useState} from "react";
import {Button, Col, Form, Row} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.css';
import React  from 'react';

function Register() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const onEmailInput = ({target: {value}}) => setEmail(value);
    const onPasswordInput = ({target: {value}}) => setPassword(value);
    const onConfirmPasswordInput = ({target: {value}}) => setConfirmPassword(value);

    function handleSubmit(e) {
        e.preventDefault();

        if (password !== confirmPassword) {
            alert("The passwords are not the same! Try again.");
        } else {
            fetch("/api/users/register?email=" + email + "&password=" + password,
                {
                    method: "post",
                })
                .then(r => {
                    r.text().then(r => {
                        alert(r);
                    });
                });
        }
    }

    return (
        <>
            <Form onSubmit={handleSubmit}>
                <Row className="text-center p-3 align-items-center bg-success" id="registerForm">
                    <Col>
                        <Form.Group controlId="emailGroup">
                            <Form.Label>e-mail</Form.Label>
                            <Form.Control value={email} onChange={onEmailInput} type="email" placeholder="email@gmail.com"/>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="passwordGroup">
                            <Form.Label>password</Form.Label>
                            <Form.Control value={password} onChange={onPasswordInput} type="password"/>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Form.Group controlId="confirmPasswordGroup">
                            <Form.Label>confirm password</Form.Label>
                            <Form.Control value={confirmPassword} onChange={onConfirmPasswordInput} type="password"/>
                        </Form.Group>
                    </Col>

                    <Col>
                        <Button variant="danger" size="lg" type="submit">
                            submit
                        </Button>
                    </Col>
                </Row>
            </Form>
        </>
    );
}

export default Register;
