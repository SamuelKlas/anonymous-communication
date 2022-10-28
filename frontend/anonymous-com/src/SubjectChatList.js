import React, {useEffect, useRef, useState} from 'react';
import {makeRestCall} from "./Utils";
import {useParams} from "react-router-dom";
import {Button} from "react-bootstrap";
import chat from "./Chat";
import Chat from "./Chat";

function SubjectChatList(props) {
    let [loaded,setLoaded] = useState(false)
    let [chatData,setChatData] = useState(null)
    let {subjectId} = useParams()

    let getChatData = async () => {
        let data = await makeRestCall("/subjects/" + subjectId + "/chat")
        setChatData(data)
        setLoaded(true)
        
    }

    let createNewChat = async () => {
        let data = await makeRestCall("/subjects/" + subjectId + "/chat",'POST')
        
        
        setChatData([...chatData, data])
    }

    useEffect(async () => {
        await getChatData()
    }, [])

    useEffect(async () => {
        await getChatData()
    }, [subjectId])

    if(loaded === false)return ''
    return (
        <div>
            <h2>Conversations</h2>
            {chatData.map(chat =>
                <Chat subjectId = {subjectId} chatId = {chat.id}/>
            )}
            <Button variant="dark" className="subjectButton" name="chat" onClick={createNewChat}>New Chat</Button>
        </div>
    );
}

export default SubjectChatList;