import React from 'react';
import {oddOrEven} from "./Utils";

function ThreadListItem(props) {

    const callBack = () =>{
        props.handleCallBack(props.idx)
    }
    return (
        <div role="button" className={"border border-dark " + oddOrEven(props.idx)} onClick={callBack} >
            <h5 className="text">{props.thread.title}</h5>
            <p className="text-muted m-0"><small>{"posted by: " + props.thread.posterDisplayName}</small></p>

        </div>
    );
}

export default ThreadListItem;