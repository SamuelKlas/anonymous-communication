import React from "react";
import "./loader.css"
export default function Loader(){
    return <div className="spinner">
        <div className="rect1 bg-primary"></div>
        <div className="rect2 bg-primary"></div>
        <div className="rect3 bg-primary"></div>
        <div className="rect4 bg-primary"></div>
        <div className="rect5 bg-primary"></div>
    </div>
}