import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {getCookie, makeRestCall} from "./Utils";
import Post from "./Post";
import {Button} from "react-bootstrap";
import PostForm from "./PostForm";

function Thread(props) {
    const [userData,setUserData] = useState(null)
    const [posting,setPosting] = useState(false)
    const [threadData,setThreadData] = useState(null)
    const [loaded,setLoaded] = useState(false)
    const { subjectId, threadId } = useParams();
    const navigate = useNavigate()
    const getThreadData = async () => {
        setLoaded(false)
        let data = await makeRestCall("/subjects/" + subjectId +"/forum/threads/" + threadId,'GET')
        setThreadData(data)
        
        setLoaded(true)



    }

    const handleClick = () =>{
        setPosting(!posting)
    }

    const callback = async () => {
        setLoaded(false)
        await getThreadData()
        setLoaded(true)
    }


    useEffect(() => {
        const loggedInUser = localStorage.getItem("user");
        if (loggedInUser) {
            const foundUser = JSON.parse(loggedInUser);
            setUserData(foundUser);
        }
    }, []);

    useEffect(async () => {
        await getThreadData()
    }, [])

    if(threadData === null || loaded == false)return ""
    return (
        <div className="mx-3 mb-3">

            <h2 className="mb-3">{threadData.title}</h2>
            {threadData.posts.length > 0 &&
                threadData.posts.filter(post=> post.replyToId === 0).map(post => <Post userId = {userData.id} callback={callback} nesting={0} data={post}/>)
            }
            <div className="border border-dark w-50"><h6>New post</h6>
                <PostForm callback = {callback} operation = "new" postId = ""></PostForm>
            </div>

        </div>
    );
}

export default Thread;