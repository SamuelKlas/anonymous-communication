import React, {useEffect, useReducer, useState} from 'react';
import {Button, ButtonGroup, Col, Container, DropdownButton, ListGroup, Nav, NavDropdown, Row} from "react-bootstrap";
import Navigation from "./Navigation";
import {Link, Route, Routes, useNavigate, useParams} from "react-router-dom";
import {getCookie, getFromLocalStorage, isLoggedIn, makeRestCall} from "./Utils";
import SubjectListItem from "./SubjectListItem";
import SubjectQuizList from "./SubjectQuizList";
import SubjectOverview from "./SubjectOverview";
import SubjectChatList from "./SubjectChatList";
import SubjectSettings from "./SubjectSettings";

function Subjects(props) {
    const [loaded, setLoaded] = useState(false)
    const [userData, setUserData] = useState(null)
    const [activeIdx, setActiveIdx] = useState(0)
    const navigate = useNavigate()
    const {subjectId} = useParams()

    const getUserData = async () => {
        let userId = getFromLocalStorage("id")
        let subjectsUrl = "/users/" + userId + "/subjects"
        let data = await makeRestCall(subjectsUrl,'GET')
        setUserData(data)
        setLoaded(true)

    }
    const findActiveId = (subjectId) => {
        for (let i = 0; i < userData.length; i++) {
            if (userData[i].id == subjectId) {
                setActiveIdx(i)
            }
        }
    }


    const handleChildCallBack = (subjectIdx,activePanel) =>{
        findActiveId(subjectIdx)
        navigate("/subjects/" + subjectIdx + "/" + activePanel)

    }


    useEffect(async () => {
        await getUserData()
        findActiveId(subjectId)
    }, [])

    useEffect(async () => {
        findActiveId(subjectId)
    }, [userData,subjectId])



    if(loaded === false || userData.size === 0)return ""
    return (

    <div className="d-flex text-start align-middle  vh-100">

        <Container className="h-100">
            <Row className="justify-content-center">
                <h1 className="text-center">Subjects</h1>
            </Row>
            <Row fluid className="h-100">
                <Col xs={12} md={3}>
                        {loaded ? userData.map((item) =>
                                <SubjectListItem key={item.id} data={item} index = {item.id} callback={handleChildCallBack}/>
                        ) : ""}

                </Col>
                <Col xs={{ order: 'first' }} md ={{ order: 'last',span:8 }} className="border border-dark">
                    <h1>{subjectId !== undefined ? userData[activeIdx].title : ''}</h1>
                    <Routes>
                        <Route path="overview" element={<SubjectOverview/>}/>
                        <Route path="quizzes" element={<SubjectQuizList/>}/>
                        <Route path="chat" element={<SubjectChatList/>}/>
                        <Route path="settings" element={<SubjectSettings/>}/>
                    </Routes>

                </Col>
            </Row>


        </Container>
    </div>



    );
}

export default Subjects;
