import React, {useState} from 'react';
import {Button, ButtonGroup} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

function SubjectListItem(props) {
    const [visible,setVisible] = useState(false)
    const navigate = useNavigate()
    const handleClick = () =>{
        if(!visible) {
            props.callback(props.index, "overview")
        }
        setVisible(!visible)

    }

    const callback = (e) =>{
        props.callback(props.index,e.target.name)
    }
    const redirectToForum = () =>{
        navigate("/forum/" + props.data.id)
    }
    return (
        <div>
            <Button className="w-100 subjectButton" variant="dark" onClick={handleClick}>{props.data.code}</Button>
            {visible && <Button className="w-100" variant="secondary" name="overview" onClick={callback} >Overview</Button>}
            {visible && <Button className="w-100" variant="secondary" name="forum" onClick={redirectToForum}>Forum</Button>}
            {visible && <Button className="w-100" variant="secondary" name="quizzes" onClick={callback}>Quizzes</Button>}
            {visible && <Button className="w-100" variant="secondary" name="chat" onClick={callback}>Chat</Button>}
            {visible && <Button className="w-100" variant="secondary" name="settings" onClick={callback}>Settings</Button>}
        </div>

    );
}

export default SubjectListItem;