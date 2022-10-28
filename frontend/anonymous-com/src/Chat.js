
import React, {Component, useEffect, useRef, useState} from 'react';
import SockJsClient from 'react-stomp';
import './App.css';
import {Button, Form} from "react-bootstrap";
import {getFromLocalStorage, makeRestCall} from "./Utils";

function Chat(props) {
    let clientRef = useRef(null)
    let [loaded,setLoaded] = useState(false)
    let [messages,setMessages] = useState([])
    let [currMessage,setCurrMessage] = useState('')
    let [name, setName] = useState(
        JSON.parse(localStorage.getItem('user'))["displayName"]
    )

    let getMessageData = async () => {
        let data = await makeRestCall("/chat/"+ props.chatId +"/messages")
        setMessages(data)
        setLoaded(true)
        console.log(getFromLocalStorage("displayName"))

    }
    let sendMessage = () => {

        
        clientRef.current.sendMessage('/app/subject/' + props.subjectId + '/chat/' + props.chatId, JSON.stringify({
            poster: name,
            content: currMessage
        }));
        setCurrMessage('')
    };

    let handleMessageChange = (e) =>{
        setCurrMessage(e.target.value)
    }

    useEffect(async () => {
        await getMessageData()
    }, [])

        if(loaded === false) return ''
        return (
            <div className="border border-dark mb-5 w-75">
                <div>
                    {messages.map((msg,i)=>
                    msg.poster.includes(getFromLocalStorage("displayName"))=== true

                        ?
                        <div className="chatBoxRight beforeRight">
                            <p className="m-0">{msg.poster.includes("Student") ? "You" : <b>You</b>} :</p>
                            <p className="m-0">{msg.content}</p>
                        </div>

                        :
                        <div className="chatBoxLeft beforeLeft">
                            <p className="m-0">{msg.poster.includes("Student") ? msg.poster : <b>{msg.poster}</b>} :</p>
                            <p className="m-0">{msg.content}</p>
                        </div>
                    )}

                </div>
                <div className="align-center">
                    <Form.Control type="text"
                                  name="content"
                                  placeholder=''
                                  value={currMessage}
                                  onChange={event => handleMessageChange(event)}/>
                    <Button variant="dark" className="subjectButton"
                            onClick={sendMessage}>Send</Button>
                </div>


                <SockJsClient url='http://localhost:8080/websocket-chat/'
                              topics={['/topic/subject/' + props.subjectId + '/chat/' + props.chatId]}
                              onConnect={() => {
                                  
                              }}
                              onDisconnect={() => {
                                  
                              }}
                              onMessage={(msg) => {
                                  setMessages([...messages, msg])
                              }}
                              ref={(client) => {
                                  clientRef.current = client
                              }}/>
            </div>
        )
}

export default Chat;