import React, {useState} from 'react';
import {Button, Form} from "react-bootstrap";
import {makeRestCall} from "./Utils";
import {useParams} from "react-router-dom";

function PostForm(props) {
    const [content,setContent] = useState("")
    const {subjectId,threadId} = useParams()
    const handleInputChange = (event) => {
        const target = event.target;
        const value = target.value;
        setContent(value)
        
    }

    const postThread = async () => {
        let body = {
            content:content,
        }
        if(props.operation === "new") {
            let data = await makeRestCall("/subjects/" + subjectId + "/forum/threads/" + threadId + "/" + props.postId,
                'POST', body)
            
        }
        if(props.operation === "edit") {
            let data = await makeRestCall("/subjects/" + subjectId + "/forum/posts/" + props.postId,
                'PUT', body)
            
        }


        props.callback()

    }

    return (
        <div className="w-50">
            <Form>
                <Form.Group >
                    <Form.Control as="textarea"
                                  name="content"
                                  value={content}
                                  rows={3}
                                  onChange={handleInputChange}

                    />
                </Form.Group>
            </Form>
            <Button className="subjectButton" variant="dark" type="submit" size="sm" onClick={postThread}>{props.operation === "new" ? "Post" : "Edit"}</Button>
        </div>
    );
}

export default PostForm;