import {BrowserRouter, Route, Routes} from "react-router-dom";
import Menu from "./components/Menu";
import Home from "./components/Home";
import Register from "./components/Register";
import Books from "./components/Books";
import React from "react";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Menu/>}>
                    <Route index element={<Home/>}/>
                    <Route path="register" element={<Register/>}/>
                    <Route path="books" element={<Books/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}