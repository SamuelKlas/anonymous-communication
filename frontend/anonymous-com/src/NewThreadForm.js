import React, {useState} from 'react';
import {Button, Form} from "react-bootstrap";
import {makeRestCall} from "./Utils";
import {useNavigate} from "react-router-dom";

function NewThreadForm(props) {
    const [title,setTitle] = useState("")
    const [content,setContent] = useState("")
    const [error,setError] = useState(false)
    const navigate = useNavigate()
    const methodMap = {
        title:setTitle,
        content:setContent
    }
    const handleInputChange = (event) => {
        setError(false)
        const target = event.target;
        const value = target.value;
        const name = target.name;
        methodMap[name](value);
        
        
    }

    const postThread = async () => {
        if(title === "" || content === ""){
            setError(true)
            return
        }
        let threadBody = {
            title:title
        }
        let postBody = {
            content:content,
        }
        let threadData = await makeRestCall("/subjects/" + props.subjectId + "/forum/threads",'POST',threadBody)
        
        
        let postData = await makeRestCall("/subjects/" + props.subjectId + "/forum/threads/" + threadData.id,
            'POST',postBody)
        
        navigate("/forum/" + props.subjectId + "/threads/" + threadData.id)

    }
    return (
        <div className="w-50 border border-dark lightGrey mb-3">
            <Form className="mx-2">
                <Form.Group  >
                    <h6>New Thread</h6>
                    <Form.Control type="email"
                                  name="title"
                                  value={title}
                                  placeholder="Thread title"
                                  onChange={handleInputChange}

                    />
                </Form.Group>
                <Form.Group >
                    <Form.Control as="textarea"
                                  name="content"
                                  value={content}
                                  rows={3}
                                  placeholder="First post content"
                                  onChange={handleInputChange}

                    />
                </Form.Group>
                { error && <Form.Label variant ="warning">You have provided an empty title or text content</Form.Label>}
            </Form>
            <Button type="submit" variant="dark" className="subjectButton" onClick={postThread}>New Thread</Button>
        </div>
    );
}

export default NewThreadForm;