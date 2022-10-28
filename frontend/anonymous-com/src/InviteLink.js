import React, {useEffect} from 'react';
import {makeRestCall} from "./Utils";
import {useNavigate, useParams} from "react-router-dom";

function InviteLink(props) {
    let {inviteLink} = useParams()
    let navigate = useNavigate()
    let handleInviteLink = async () => {
        let data = await makeRestCall("/links/" + inviteLink, 'PUT')
        
        navigate("/subjects")
    }

    useEffect(async () => {
        await handleInviteLink()
    }, [])
    return (
        <div></div>
    );
}

export default InviteLink;