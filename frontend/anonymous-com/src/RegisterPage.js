import React, {useState} from 'react';
import Navigation from "./Navigation";
import {Button, Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {isLoggedIn} from "./Utils";

function RegisterPage(props) {

    const[username,setUsername] = useState('')
    const[firstName,setFirstName] = useState('')
    const[lastName,setLastName] = useState('')
    const[password,setPassword] = useState('')
    const[error,setError] = useState(false)
    const[passwordShown,setPasswordShown] = useState(false)
    const navigate = useNavigate();
    const methodMap = {
        username: setUsername,
        password:setPassword,
        firstName:setFirstName,
        lastName:setLastName,
        error:setError,
        passwordShown:setPasswordShown
    };

    const handleInputChange = (event) => {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        methodMap[name](value);
        setError(false)
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        let body = {
            username: username,
            password: password,
            firstName:firstName,
            lastName:lastName
        }


        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(body)
        };

        let response = await fetch('http://localhost:8080/register', requestOptions)
        if (response.status === 401) {
            setError(true)

        }
        if (response.status === 409) {
            setError(true)
            let data = await response.json()
            
        }
        if (response.status === 201) {
            let data = await response.json()
            
            setError(false)
            navigate("/")
        }




    }

    const handleCheckBox = () =>{
        setPasswordShown(!passwordShown)
    }
    return (

<div>
            <div className="d-flex justify-content-center align-items-center text-start align-middle h-100 p-5 ">
                <Form onSubmit={handleSubmit} className="rounded p-5 p-sm-3 shadow-sm m-auto col-md-3 fw-bold border border-dark">
                    <h1 className="shadow-sm text-center rounded mt-5 fw-bold">Sign Up</h1>
                    <Form.Group className="mb-3" controlId="formBasicUsername">
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="text"
                                      name="username"
                                      value={username}
                                      onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicFirstName">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control type="text"
                                      name="firstName"
                                      value={firstName}
                                      onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicLastName">
                        <Form.Label>Last Name</Form.Label>
                        <Form.Control type="text"
                                      name="lastName"
                                      value={lastName}
                                      onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control type={passwordShown ? "text" : "password"}
                                      value={password}
                                      name="password"
                                      onChange={handleInputChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicCheckBox">
                        <Form.Check type="checkbox"
                                    label="Show password"
                                    onChange={handleCheckBox} />
                        <Form.Text className={error ? "text-danger" : "text-white"}>Incorrect username or password</Form.Text>
                    </Form.Group>
                    <Button className="subjectButton" variant="dark btn-block w-100" type="submit">Register</Button>

                </Form>

            </div>
</div>
    );
}

export default RegisterPage;