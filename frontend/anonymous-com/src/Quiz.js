import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {makeRestCall} from "./Utils";
import {Button, Form} from "react-bootstrap";
import Chat from "./Chat";

function Quiz(props) {
    const [quizData,setQuizData] = useState(null)
    const [loaded,setLoaded] = useState(false)
    const {subjectId,quizId} = useParams()
    const [quizEntryData,setQuizEntryData] = useState(null)

    const getQuizData = async () => {
        let data = await makeRestCall("/quizzes/" + quizId)
        setQuizData(data)
        setQuizEntryData(data.questions.map(quiz=>
            ({
                    answerIdx:''
                }
            )
        ))
        setLoaded(true)
        
    }
    const submit = async () => {
        let body = {
            answers: quizEntryData
        }
        let response = await makeRestCall("/quizzes/" + quizId + "/quizEntries", 'POST', body)
        
    }

    const handleCheckBox = (questionIdx,choiceIdx) =>{
        let data = [...quizEntryData]
        
        data[questionIdx].answerIdx = choiceIdx
        setQuizEntryData(data)
        
    }

    useEffect(async () => {
        await getQuizData()
    }, [])
    useEffect(async () => {
        
    }, [quizData])

    if(loaded === false)return ""
    return (
        <div className="p-5 m-5">
            <h2>{quizData.title}</h2>
            <Form>
                {quizData.questions.map((question,questionIdx) =>
                    <Form.Group>
                        <Form.Label className="d-inline-block">{question.content + "   " + "points: " + question.points}</Form.Label>
                        {question.choices.map((choice,choiceIdx) =>
                            <Form.Group>
                                <Form.Check
                                    inline
                                    name="correct"
                                    type='checkbox'
                                    checked={quizEntryData[questionIdx].answerIdx === choiceIdx}
                                    onChange={() => handleCheckBox(questionIdx,choiceIdx)}
                                />
                                <Form.Label className="d-inline-block">{choice.content}</Form.Label>
                            </Form.Group>

                        )}


                    </Form.Group>

                )}
                <Button variant="dark" className="subjectButton" onClick={submit}>Submit Entry</Button>
            </Form>
        </div>

    );
}

export default Quiz;