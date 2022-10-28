import React, {useEffect, useState} from 'react';
import {makeRestCall, oddOrEven} from "./Utils";
import {useNavigate, useParams} from "react-router-dom";
import {Button} from "react-bootstrap";
import Chat from "./Chat";

function SubjectQuizList(props) {
    const [loaded,setLoaded] = useState(false)
    const [quizData,setQuizData] = useState(null)
    const {subjectId} = useParams()
    const navigate = useNavigate()
    const getQuizData = async () => {
        let data = await makeRestCall("/subjects/" + subjectId + "/quizzes")
        setQuizData(data)
        
        setLoaded(true)
    }

    const redirectToNew = () =>{
        navigate("/subjects/" + subjectId + "/quizzes/new")
    }

    const redirect = (idx) =>{
        navigate("/subjects/" + subjectId + "/quizzes/" + quizData[idx].id)
    }

    useEffect(async () => {
        await getQuizData()
    }, [subjectId])
    if(loaded === false)return ""
    return (
        <div>
            <h2>Quizzes</h2>
            {quizData.map((quiz,idx) =>
                <div role="button" className={"border border-dark p-0 m-0 " + oddOrEven(idx)} onClick={ () =>redirect(idx)}>
                    <h5 className="text">{quiz.title}</h5>
                    <p>Questions: {quizData[idx].questions.length}</p>
                    <p>Points: {quizData[idx].quizStatistics.maxPoints}</p>
                </div>

            )}
            <Button onClick={redirectToNew} variant="dark" className="subjectButton">New Quiz</Button>
        </div>
    );
}

export default SubjectQuizList