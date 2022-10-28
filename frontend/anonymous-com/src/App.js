import logo from './logo.svg';
import './App.css';
import Quiz from "./Quiz";
import LoginPage from "./LoginPage"
import {BrowserRouter as Router, Redirect, Route, Routes, Navigate} from "react-router-dom";
import HomePage from "./HomePage";
import React, {useEffect, useState} from "react";
import RegisterPage from "./RegisterPage";
import Navigation from "./Navigation";
import Profile from "./Profile";
import {isLoggedIn} from "./Utils";
import Subjects from "./Subjects";
import Forum from "./Forum";
import NewThreadForm from "./NewThreadForm";
import SubjectThreadList from "./SubjectThreadList";
import Thread from "./Thread";
import QuizForm from "./QuizForm";
import InviteLink from "./InviteLink";

const SignInWrapper = ({ children, currentUser }) => {
    return !currentUser() ? <Navigate to="/login" replace /> : children;
};

function App() {
    const [loggedIn,setLoggedIn] = useState(isLoggedIn())
    const [count ,setCount] = useState(0)
    async function setLoggedInParent(cbLoggedIn){
        setLoggedIn(cbLoggedIn)
    }

    useEffect(() => {
        setCount(count+1)
        
    }, [loggedIn])
  return (



        <Router>
            <Navigation key={count} loggedIn={loggedIn} setLoggedInParent={setLoggedInParent} />
            <div className="vh-100">
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/login" element={<LoginPage setLoggedInParent={setLoggedInParent}/>}/>
                <Route path="/links/:inviteLink" element={<InviteLink/>}/>
                <Route path="/register" element={<RegisterPage/>}/>
                <Route path="/subjects/:subjectId/quizzes/new" element={<QuizForm/>}/>
                <Route path="/subjects/:subjectId/quizzes/:quizId" element={<Quiz/>}/>
                <Route path="/subjects/:subjectId/*" element={<Subjects/>}/>
                <Route path="/subjects/" element={<Subjects/>}/>
                <Route path="/forum/" element={<Forum/>}/>
                <Route path="forum/:id" element=<SubjectThreadList/>/>
                <Route path="/forum/:subjectId/threads/:threadId" element={<Thread/>}/>
                <Route
                    path='/profile'
                    element={<SignInWrapper currentUser={isLoggedIn}>
                        <Profile />
                    </SignInWrapper>}
                />
            </Routes>
            </div>

            </Router>

  );
}

export default App;
