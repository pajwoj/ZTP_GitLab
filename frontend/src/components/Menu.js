import { Outlet, Link } from "react-router-dom";
import {useEffect, useState} from "react";

const Menu = () => {
    const [user, setUser] = useState("");

    useEffect(() => {
        fetch("api/users/current", {
            method: "get",
        }).then(r => {
            r.text().then(r => {
                setUser(r)
            })
        })

        console.log(user);
    })

    return (
        <>
            <nav>
                <ul>
                    <li>
                        <Link to="/">Home</Link>
                    </li>
                    <li>
                        <Link to="/login">Login</Link>
                    </li>
                    <li>
                        <Link to="/register">Register</Link>
                    </li>
                    <li>
                        <Link to="/logout">Logout</Link>
                    </li>
                </ul>
            </nav>

            <div>Current user: {user}</div>

            <Outlet/>
        </>
    )
};

export default Menu;
