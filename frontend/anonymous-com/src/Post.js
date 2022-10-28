import React, {useEffect, useState} from 'react';
import PostForm from "./PostForm";
import {Button} from "react-bootstrap";
import {makeRestCall} from "./Utils";
import {useParams} from "react-router-dom";

const ArrowUp = () => {
    return <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-arrow-up"
                viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M8 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L7.5 2.707V14.5a.5.5 0 0 0 .5.5z"/>
    </svg>
}
const ArrowDown = () => {
    return <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                className="bi bi-arrow-down" viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M8 1a.5.5 0 0 1 .5.5v11.793l3.146-3.147a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 .708-.708L7.5 13.293V1.5A.5.5 0 0 1 8 1z"/>
    </svg>
}

function Post(props) {
    const [collapsed, setCollapsed] = useState(false)
    const [replying, setReplying] = useState(false)
    const [editing, setEditing] = useState(false)
    const {subjectId, threadId} = useParams()
    const [own, setOwn] = useState(false)
    const handleCollapseClick = () => {
        setCollapsed(!collapsed)
    }

    const handleReplyClick = () => {
        setReplying(!replying)
    }

    const handleEditClick = () => {
        setEditing(!editing)
    }

    const handleDeleteClick = async () => {
        let data = await makeRestCall("/subjects/" + subjectId + "/forum/posts/" + props.data.id,
            'DELETE')
        
        props.callback()
    }

    useEffect(() => {
        setOwn(props.userId === props.data.userId)
    }, []);


    return (
        <div>
            <div style={{marginInlineStart: props.nesting + "rem"}} className="overflow-auto border border-dark">
                <p>{props.data.content}</p>
                <p className="text-muted m-0"><small>{"posted by: " + props.data.posterDisplayName}</small></p>
                <Button variant="dark" className="subjectButton" size="sm" onClick={handleCollapseClick}>
                    {collapsed === false ? <ArrowUp/> : <ArrowDown/>}
                </Button>
                <Button variant="dark" className="subjectButton" size="sm" onClick={handleReplyClick}>Reply</Button>
                {own &&
                    <Button variant="dark" className="subjectButton" size="sm" onClick={handleEditClick}>Edit</Button>}
                {own && <Button variant="dark" className="subjectButton" size="sm"
                                onClick={handleDeleteClick}>Delete</Button>}
                {replying && <PostForm operation="new" callback={props.callback} postId={props.data.id}></PostForm>}
                {editing && <PostForm operation="edit" callback={props.callback} postId={props.data.id}></PostForm>}
            </div>
            {!collapsed && props.data.replies.map(reply => <Post userId={props.userId} callback={props.callback}
                                                                 nesting={props.nesting + 1} data={reply}></Post>)}
        </div>
    );
}

export default Post;