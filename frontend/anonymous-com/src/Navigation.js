import React, {useEffect, useState} from 'react';
import {Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import {deleteCookie, getCookie, isLoggedIn} from "./Utils";

function Navigation(props) {
    const [userData,setUserData] = useState(JSON.parse(localStorage.getItem('user')))
    const [loggedIn,setLoggedIn] = useState(props.loggedIn)
    const navigate = useNavigate();
    const logout = () =>{
        setLoggedIn(false)
        props.setLoggedInParent(false)
        deleteCookie('token')
        localStorage.clear()
        setUserData(null)
        navigate("/")
    }
    const handleNavClick = (location) =>{
        setUserData(JSON.parse(localStorage.getItem("user")))
        navigate(location)
    }

    useEffect(() => {
        const loggedInUser = localStorage.getItem("user");
        if (loggedInUser) {
            const foundUser = JSON.parse(loggedInUser);
            setUserData(foundUser);
        }
    }, [loggedIn]);

    return (
        <Navbar bg="dark" variant="dark"  fixed="top">

                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/">Home</Nav.Link>
                        {loggedIn == false &&
                            <Nav.Link as={Link} to="/login">Login</Nav.Link>
                        }
                        {loggedIn == false &&
                            <Nav.Link onClick={() => handleNavClick("/register")}>Sign Up</Nav.Link>
                        }
                        {loggedIn == true &&
                            <Nav.Link onClick={() => handleNavClick("/subjects")}>Subjects</Nav.Link>
                        }
                        {loggedIn == true &&
                            <Nav.Link onClick={() => handleNavClick("/forum")}>Forum</Nav.Link>
                        }
                        {loggedIn == true &&
                        <NavDropdown title={userData.displayName} id="basic-nav-dropdown">
                            <NavDropdown.Item as={Link} to={isLoggedIn() ? "/profile" : "/login"}>Profile</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item onClick={logout}>Logout</NavDropdown.Item>
                        </NavDropdown>
                        }
                    </Nav>
                </Navbar.Collapse>
        </Navbar>
    );
}

export default Navigation;