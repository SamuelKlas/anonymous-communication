import React, {useEffect, useState} from 'react';
import Loader from "./Loader";
import 'draft-js/dist/Draft.css';
import {makeRestCall} from "./Utils";
import {useParams} from "react-router-dom";
function SubjectOverview(props) {
    const [loaded,setLoaded] = useState(false)
    const [subjectData,setSubjectData] = useState(null)
    const {subjectId} = useParams()
    const getSubjectData = async () => {
        let data = await makeRestCall("/subjects/" + subjectId)
        setSubjectData(data)
        
        setLoaded(true)
    }
    useEffect(async () => {
        await getSubjectData()
    }, [subjectId])

    if(loaded === false)return <Loader/>
    return (
        <div>
            <div className="lightGrey border border-dark mb-2">
                <h2>Information</h2>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
            </div>
            <div className="lightGrey border border-dark ">
                <h6>Teachers</h6>
                <ul>
                    {subjectData.teachers.map(teacher =>
                        <li>{teacher.displayName}</li>
                    )}
                </ul>
            </div>
        </div>
    );
}

export default SubjectOverview;