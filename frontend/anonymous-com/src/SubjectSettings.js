import React, {useEffect, useState} from 'react';
import {makeRestCall} from "./Utils";
import {useParams} from "react-router-dom";
import {Button, ButtonGroup, Col, Dropdown, Row} from "react-bootstrap";


let baseUrl = "https://dulcet-swan-61594d.netlify.app/"
function SubjectSettings(props) {

    return (
        <div>
            <h2>Settings</h2>
            <Links/>
        </div>
    );
}

const Links = (props) => {
    let [expirySeconds, setExpirySeconds] = useState(0)
    let [string, setString] = useState("")
    let [loaded, setLoaded] = useState(false)
    let [linkData, setLinkData] = useState([])
    let {subjectId} = useParams()

    let calculateDate = (dateMilliseconds) =>{
        let date = new Date(dateMilliseconds)
        return date.toDateString() + " " + date.toTimeString().split("(")[0]
    }
    const getLinkData = async () =>{
        setLoaded(false)
        let data = await makeRestCall("/subjects/" + subjectId + "/links",'GET')

        setLinkData(data)
        setLoaded(true)
    }

    const deleteLink = async(uuid) =>{
        let data = await makeRestCall("/links/" + uuid,'DELETE')
        let removedData = linkData.filter(link =>link.uuid !== uuid)
        setLinkData(removedData)

    }
    const createLink = async () => {
        let body = {
            expiresSecondsFromNow: expirySeconds
        }

        let data = await makeRestCall("/subjects/" + subjectId + "/links",'POST',body)
        setLinkData([...linkData, data])

    }
    useEffect(async () => {
        await getLinkData()
    }, [subjectId])

    return loaded && (
        <div className="border border-dark lightGrey">
            <h6>Invite Links</h6>
            {linkData.map(link =>
                <Row className="mediumGrey border-dark border m-2">
                    <Col>
                        <p>{baseUrl + "links/" + link.uuid}</p>
                    </Col>
                    <Col>
                        <p>{new Date(link.expiringAt) < new Date() ? "Expired" : "Expires " + calculateDate(link.expiringAt) }</p>

                    </Col>
                    <Col>
                        <Button variant="dark" className="subjectButton" onClick={() => deleteLink(link.uuid)}>X</Button>
                    </Col>

                </Row>
                    )}

            <Dropdown as={ButtonGroup}>
                <Button variant="dark" onClick={createLink}>Generate Link</Button>

                <Dropdown.Toggle split variant="dark" id="dropdown-split-basic" >Expiring in </Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item value="1 minute" onClick={() => setExpirySeconds(60)}>1 minute</Dropdown.Item>
                    <Dropdown.Item value="5 minutes" onClick={() => setExpirySeconds(5*60)}>5 minutes</Dropdown.Item>
                    <Dropdown.Item value="15 minutes" onClick={() => setExpirySeconds(15*60)}>15 minutes</Dropdown.Item>
                    <Dropdown.Item value="1 hour" onClick={() => setExpirySeconds(60*60)}>1 hour</Dropdown.Item>
                    <Dropdown.Item value="3 hours" onClick={() => setExpirySeconds(3*60*60)} >3 hours</Dropdown.Item>
                    <Dropdown.Item value="1 day" onClick={() => setExpirySeconds(24*60*60)}>1 day</Dropdown.Item>
                    <Dropdown.Item value="1 week" onClick={() => setExpirySeconds(7*24*60*60)}>1 week</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        </div>

    )
}

export default SubjectSettings;