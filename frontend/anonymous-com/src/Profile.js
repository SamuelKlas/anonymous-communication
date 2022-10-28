import React, {useEffect, useState} from 'react';
import {getCookie, isLoggedIn, makeRestCall} from "./Utils";
import Navigation from "./Navigation";
import {Button} from "react-bootstrap";

function Profile(props) {
    let [userData,setUserData] = useState('')
    const getUserData = async () => {
        let url = "http://localhost:8080/profile"
        const requestOptions = {
            method: 'GET',
            headers: {
                Authorization: "Bearer " + getCookie('token')
            },

        };

        let response = await fetch(url,requestOptions);
        let data = await response.json();
        
        setUserData(data)
        return data

    }
    const resetDisplayName = async () => {
        let data = await makeRestCall("/reset", 'PUT')
        
        localStorage.setItem('user',JSON.stringify(data))
    }
    useEffect(() => {
        getUserData()
    }, [])

    return (
        <div className="d-flex justify-content-center align-items-center text-start align-middle h-100 p-5 ">
            <Button variant="dark" className="subjectButton" onClick={resetDisplayName}>Reset display name</Button>

        </div>
    );
}

export default Profile;