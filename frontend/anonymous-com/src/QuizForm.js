import React, {useState} from 'react';
import {Button, Col, Form, Row} from "react-bootstrap";
import {makeRestCall} from "./Utils";
import {useNavigate, useParams} from "react-router-dom";

function QuizForm(props) {
    const {subjectId} = useParams()
    const [title, setTitle] = useState("")
    let navigate = useNavigate()
    const [inputFields, setInputFields] = useState([
        {
            content: '',
            points: '',
            choices: [
                {
                    content: '',
                    correct: false
                }
            ]
        }
    ])

    const handleFormChange = (index, event) => {
        let data = [...inputFields];
        data[index][event.target.name] = event.target.value;
        setInputFields(data);
    }

    const handleFormChoiceChange = (index, choiceIndex, event) => {
        let data = [...inputFields];
        data[index].choices[choiceIndex]['content'] = event.target.value;
        setInputFields(data);
    }
    const handleFormCorrectChange = (index, choiceIndex, event) => {
        let data = [...inputFields];
        data[index].choices[choiceIndex]["correct"] = !data[index].choices[choiceIndex]["correct"];
        for (let i = 0; i < data[index].choices.length; i++) {
            if (i !== choiceIndex) {
                data[index].choices[i].correct = false
            }
        }
        setInputFields(data);
    }

    const addQuestion = () => {
        let newField = {
            content: '', points: '',
            choices: [
                {
                    content: '',
                    correct: false
                }
            ]
        }

        setInputFields([...inputFields, newField])
    }

    const addChoice = (index) => {
        let newChoice = {
            content: '',
            correct: false
        }

        let newInputFields = [...inputFields]
        newInputFields[index].choices = [...inputFields[index].choices, newChoice]
        setInputFields(newInputFields)
    }

    const removeQuestion = (index) => {
        let data = [...inputFields];
        data.splice(index, 1)
        setInputFields(data)
    }

    const removeChoice = (index, choiceIndex) => {
        let data = [...inputFields];
        data[index].choices.splice(choiceIndex, 1)
        setInputFields(data)
    }

    const submit = async (e) => {
        e.preventDefault();
        let data = {
            title: title,
            questions: inputFields
        }
        

        let response = await makeRestCall("/subjects/" + subjectId + "/quizzes", 'POST', data)
        
        navigate("/subjects/"+subjectId+"/quizzes")
    }

    return (
        <Row>
            <Col xs={12} sm={6} className="m-auto">
                {/*<h2 className="text-center">Create quiz</h2>*/}
                <Form onSubmit={submit}>
                    <div className="border border-dark lightGrey mb-3">
                    <h6>Title</h6>
                    <Form.Control className="mb-3"
                                type="text"
                                  name="title"
                                  placeholder='Set quiz title'
                                  value={title}
                                  onChange={event => setTitle(event.target.value)}/>
                    </div>
                    {inputFields.map((input, index) => {
                        return (
                            <div className="lightGrey border border-dark mb-3">
                                <h6>Question {index + 1}</h6>
                                <Form.Group className="m-3">
                                    <Form.Control
                                        className="border border-dark mb-2"
                                        type="text"
                                        name="content"
                                        placeholder='Question'
                                        value={input.content}
                                        onChange={event => handleFormChange(index, event)}/>
                                    <Form.Control className="lightGrey"
                                        type="text"
                                                  name="points"
                                                  placeholder='Points'
                                                  value={input.points}
                                                  onChange={event => handleFormChange(index, event)}/>
                                    <Button className="subjectButton" variant="dark"
                                            onClick={() => removeQuestion(index)}>Remove</Button>
                                    {input.choices.map((choice, choiceIndex) =>
                                        <Form.Group className="mediumGrey border border-dark">
                                            <h6>Choice {choiceIndex + 1}</h6>
                                            <Form.Control type="text"
                                                          name="content"
                                                          placeholder='Choice content'
                                                          value={choice.content}
                                                          onChange={event => handleFormChoiceChange(index, choiceIndex, event)}/>
                                            <Form.Check
                                                inline
                                                label="correct"
                                                name="correct"
                                                type='checkbox'
                                                checked={inputFields[index].choices[choiceIndex].correct}
                                                onChange={event => handleFormCorrectChange(index, choiceIndex, event)}
                                            />
                                            <Button className="subjectButton" variant="dark"
                                                    onClick={() => removeChoice(index, choiceIndex)}>Remove
                                                Choice</Button>
                                        </Form.Group>
                                    )}
                                    <Button className="subjectButton" variant="dark" onClick={() => addChoice(index)}>Add
                                        Choice</Button>
                                </Form.Group>
                            </div>
                        )
                    })}
                    <Button className="subjectButton" variant="dark" onClick={addQuestion}>Add Question</Button>
                    <Button className="subjectButton" variant="dark" onClick={submit}>Submit</Button>
                </Form>
            </Col>
        </Row>
    );
}

export default QuizForm;