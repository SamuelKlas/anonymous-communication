import  'bootstrap/dist/css/bootstrap.min.css'
import {Form, Button} from "react-bootstrap"
import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import {BrowserRouter as Router, Switch, Route,Routes} from "react-router-dom";
import HomePage from "./HomePage";
import Navigation from "./Navigation";
import {setCookie, getCookie, deleteCookie, isLoggedIn, makeRestCall} from "./Utils";


function LoginPage (props) {

    const[username,setUsername] = useState('')
    const[password,setPassword] = useState('')
    const[error,setError] = useState(false)
    const[passwordShown,setPasswordShown] = useState(false)
    const navigate = useNavigate();
    const methodMap = { username: setUsername,
        password:setPassword,
        error:setError,
        passwordShown:setPasswordShown
    };

    const handleCheckBox = () =>{
        setPasswordShown(!passwordShown)
    }

    const handleInputChange = (event) => {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        methodMap[name](value);
        setError(false)
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        let details = {username: username, password: password}

        const formBody = Object.keys(details)
            .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(details[key]))
            .join('&');

        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
            body: formBody
        };

        let response = await fetch('http://localhost:8080/login', requestOptions)
        if (response.status === 401) {
            setError(true)
        }
        else if (response.status === 200) {
            let data = await response.json()
            setError(false)
            setCookie('token', data.access_token, {secure: true, 'max-age': 10*3600});
            let userData = await makeRestCall("/profile",'GET')
            localStorage.setItem('user',JSON.stringify(userData))
            
            props.setLoggedInParent(true)
            navigate("/")
    }
    }

        return (
            <div>
            <div className="d-flex justify-content-center align-items-center text-start align-middle h-100 p-5 ">
                <Form onSubmit={handleSubmit} className="rounded p-5 p-sm-3 shadow-sm m-auto col-md-3 border border-dark">
                    <h1 className="shadow-sm text-center rounded mt-5 fw-bold">Login</h1>
                    <Form.Group className="mb-3" controlId="formBasicUsername">
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="text"
                                      placeholder="Enter Email"
                                      name="username"
                                      value={username}
                                      onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type={passwordShown ? "text" : "password"}
                                      value={password}
                                      name="password"
                                      placeholder="Enter password"
                                      onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicCheckBox">
                        <Form.Check type="checkbox"
                                    label="Show password"
                                    onChange={handleCheckBox} />
                        <Form.Text className={error ? "text-danger" : "text-white"}>Incorrect email or password</Form.Text>
                    </Form.Group>
                    <Button className="subjectButton" variant="dark w-100" type="submit">Login</Button>

                </Form>

            </div>
            </div>
        );

}

export default LoginPage;