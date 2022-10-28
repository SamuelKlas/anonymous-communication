import React, {useEffect, useState} from 'react';
import {getCookie} from "./Utils";
import "./App.css"
import Loader from "./Loader";
import {Button} from "react-bootstrap";
import {useNavigate, useParams} from "react-router-dom";
import NewThreadForm from "./NewThreadForm";
import ThreadListItem from "./ThreadListItem";

function SubjectThreadList(props) {
    const [threadData,setThreadData] = useState(null)
    const [loaded,setLoaded] = useState(false)
    const { id } = useParams();
    const navigate = useNavigate()
    const getThreadData = async () => {
        let url = "http://localhost:8080/subjects/" + id +"/forum/threads"
        const requestOptions = {
            method: 'GET',
            headers: {
                Authorization: "Bearer " + getCookie('token')
            },

        };

        let response = await fetch(url, requestOptions);

        let data = await response.json();
        setThreadData(data)
        setLoaded(true)
        


    }

    const oddOrEven = (i) => {
        return i % 2 == 0 ? "bg-even" : "bg-odd"
    }

    const handleCallBack = (idx) => {
        navigate("/forum/" + id + "/threads/" + threadData[idx].id)
    }


    const redirect = (e) =>{

    }



    useEffect(() => {
        getThreadData()
    }, [])

    if(loaded === false)return ""
    return (
        <div className="p-5">
            <h2 className="pb-3">Threads</h2>
            <NewThreadForm subjectId = {id}/>
            {
                threadData.map((thread,i) =>
                    <ThreadListItem thread = {thread} idx = {i} handleCallBack = {handleCallBack}/>
                )

            }

        </div>
    );
}

export default SubjectThreadList;