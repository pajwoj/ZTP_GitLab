import {BrowserRouter, Route, Routes} from "react-router-dom";
import Menu from "./components/Menu";
import Home from "./components/Home";
import Login from "./components/Login";
import Register from "./components/Register";
import Logout from "./components/Logout"

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Menu/>}>
                    <Route index element={<Home/>}/>
                    <Route path="login" element={<Login/>}/>
                    <Route path="register" element={<Register/>}/>
                    <Route path="logout" element={<Logout/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}