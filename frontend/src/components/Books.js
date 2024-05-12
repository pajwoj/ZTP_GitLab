import {useEffect, useState} from "react";
import {Col, Container, Form, FormControl, FormGroup, Row} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.css';
import React  from 'react';

function Books() {
    const [books, setBooks] = useState([]);
    const [filtered, setFiltered] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const booksPerPage = 6;

    useEffect(() => {
        fetch("/api/books/all", {
            method: "get",
        })
            .then(r => {
                r.json().then(r => {
                    setBooks(r);
                    setFiltered(r);
                });
            });
    }, []);

    const indexOfLastBook = currentPage * booksPerPage;
    const indexOfFirstBook = indexOfLastBook - booksPerPage;
    const currentBooks = filtered.slice(indexOfFirstBook, indexOfLastBook);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <>
        <Container>
            <Row className="p-3">
                <Col></Col>
                <Col className="align-items-center w-25">
                    {books && <Filter books={books} setFiltered={setFiltered}/>}
                </Col>
                <Col></Col>
            </Row>
            <hr/>
            <Row className="text-center d-flex justify-content-center align-items-center" id="books">
                {currentBooks.map((c, index) => (
                    <Col key={index}
                         className="col-4 p-4 m-2 bg-warning border border-secondary rounded text-center align-items-center">
                        ISBN: <b>{c.isbn}</b> <br/>
                        Name: <b>{c.name}</b> <br/>
                        Author: <b>{c.author}</b>
                    </Col>
                ))}
            </Row>
            <hr/>
            <Pagination
                booksPerPage={booksPerPage}
                totalBooks={filtered.length}
                currentPage={currentPage}
                paginate={paginate}
            />
        </Container>
    </>
    );
}

function Filter({books, setFiltered}) {
    const [filter, setFilter] = useState('');

    const onFilterInput = ({target: {value}}) => {
        setFilter(value);

        const filteredBooks = books.filter(book =>
            book.name.toLowerCase().includes(value.toLowerCase())
        );

        setFiltered(filteredBooks);
    };

    return (
        <>
            <Row id="filter">
                <Col>
                    <Form.Group controlId="filterGroup">
                        <Form.Label className="text-center align-items-center" style={{width: "100%"}}>filter by
                            name</Form.Label>
                        <Form.Control value={filter} onChange={onFilterInput} type="text"/>
                    </Form.Group>
                </Col>
            </Row>
        </>
    );
}

function Pagination({booksPerPage, totalBooks, currentPage, paginate}) {
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalBooks / booksPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <Row id="pagination">
            <Col className="p-4 text-center">
                {pageNumbers.map(number => (
                    <button
                        key={number}
                        onClick={() => paginate(number)}
                        className={number === currentPage ? 'active' : ''}>
                        {number}
                    </button>
                ))}
            </Col>
        </Row>
    );
}

export default Books;