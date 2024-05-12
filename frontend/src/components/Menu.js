import {Outlet, Link} from "react-router-dom";
import {useState} from "react";
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import React  from 'react';
import 'bootstrap/dist/css/bootstrap.css';

function Menu() {
    const [loggedIn, setLoggedIn] = useState('');

    function Login() {
        const [email, setEmail] = useState('');
        const [password, setPassword] = useState('');

        const onEmailInput = ({target: {value}}) => setEmail(value);
        const onPasswordInput = ({target: {value}}) => setPassword(value);

        function handleSubmit(e) {
            e.preventDefault();

            fetch("/api/users/login?email=" + email + "&password=" + password, {
                method: "post",
            })
                .then(r => {
                    if (r.status === 200) {
                        r.text().then(r => {
                            alert(r);
                            setLoggedIn("Currently logged in as " + email);
                        });
                    } else if (r.status === 400) {
                        r.text().then(r => {
                            alert(r);
                        });
                    } else if (r.status === 401) {
                        r.text().then(r => {
                            alert(r);
                        });
                    } else {
                        alert("BACKEND SERVER OFF");
                    }
                });
        }

        if (loggedIn.length === 0) {
            return (<>
                <Form onSubmit={handleSubmit}>
                    <Row className="text-center p-3 align-items-center bg-success" id="loginForm">
                        <Col>
                            <Form.Group controlId="emailGroup">
                                <Form.Label>e-mail</Form.Label>
                                <Form.Control value={email} onChange={onEmailInput} type="email"
                                              placeholder="email@gmail.com"/>
                            </Form.Group>
                        </Col>

                        <Col>
                            <Form.Group controlId="passwordGroup">
                                <Form.Label>password</Form.Label>
                                <Form.Control value={password} onChange={onPasswordInput} type="password"/>
                            </Form.Group>
                        </Col>

                        <Col>
                            <Button variant="danger" size="lg" type="submit">
                                submit
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </>);
        } else return (<>
            <Col className="text-center p-3 align-items-center fs-1" id="loggedIn">
                {loggedIn}
            </Col>
        </>);

    }

    function Logout() {
        function handleLogout(e) {
            e.preventDefault();

            fetch("/api/users/logout", {
                method: "post",
            })
                .then(r => {
                    r.text().then(r => {
                        alert(r);
                        setLoggedIn("");
                    });
                });
        }

        return (<>
            <a href="/" onClick={handleLogout}>Logout</a>
        </>);

    }

    return (<>
        <Container>
            <Row className="p-3 bg-danger text-center fs-2 align-items-center" id="menu">
                <Col>
                    <Link to="/">Home</Link>
                </Col>
                <Col>
                    <Logout/>
                </Col>
                <Col>
                    <Link to="/register">Register</Link>
                </Col>
                <Col>
                    <Link to="/books">Books</Link>
                </Col>
            </Row>
                <Login/>
        </Container>

        <hr/>
        <Outlet/>
    </>);
}

export default Menu;
