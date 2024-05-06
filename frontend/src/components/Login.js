import {useState} from "react";

const Login = () => {
    return (
        <>
            <Form/>
        </>
    );
};

function Form() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    function handleSubmit(e) {
        e.preventDefault()

        fetch("/api/users/login?email=" + email + "&password=" + password,
            {
                method: "post",
            })
            .then(r => {
                r.text().then(r => {
                    alert(r);
                })
            })
    }

    return (
        <>
            <form method="post" onSubmit={handleSubmit}>
                <label>
                    email
                    <input
                        value={email}
                        placeholder={"a@wp.pl"}
                        onChange={e => setEmail(e.target.value)}
                    />
                </label>

                <label>
                    password
                    <input
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                </label>

                <button type={"submit"}>Submit</button>
            </form>
        </>
    )
}

export default Login;
