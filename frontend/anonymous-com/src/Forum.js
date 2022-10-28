import React, {useEffect, useState} from 'react';
import {makeRestCall, oddOrEven} from "./Utils";
import {Route, Routes, useNavigate} from "react-router-dom";
import SubjectThreadList from "./SubjectThreadList";


function Forum(props) {
    const [loaded,setLoaded] = useState(false)
    const [data,setData] = useState(null)
    const navigate = useNavigate()

    const fetchData = async () => {
        let data = await makeRestCall("/subjects", 'GET')
        
        setData(data)
        setLoaded(true)

    }
    const redirect = (e,idx) =>{
        let id = data[idx].id
        navigate("/forum/"+id)
    }
    useEffect(() => {
        fetchData()
    }, [])

    if(loaded == false)return ""
    return (
        <div className="p-5">
            <Routes>
                <Route path={"/:id"} element=<SubjectThreadList/>/>
            </Routes>

            <h2>Forum</h2>
                {data.map((d,i) =>
                    <div role="button" className={"p-0 " + oddOrEven(i)}  onClick={(e) => {
                        redirect(e, i);
                    }}>
                        <p className="m-0 p-2">{d.code +" : " + d.title}</p>
                    </div>
                )
                }

        </div>
    );
}

export default Forum;