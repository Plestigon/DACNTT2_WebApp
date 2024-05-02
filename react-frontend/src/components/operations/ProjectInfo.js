import React, { useCallback } from "react";
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import NavigationBar from '../NavigationBar'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Select from 'react-select';
import { handleDate, dateFormat } from "../../utils/DateHelper";
import "react-datepicker/dist/react-datepicker.css";
import { Button } from "react-bootstrap";
  
const ProjectInfo = () => {
    const params = useParams();
    const [options, setOptions] = useState([]);
    const [inputs, setInputs] = useState({
        name: '',
        owner: 0,
        ownerName: '',
        dueDate: '',
        status: -1,
        statusName: '',
        description: ''
    });
    const [prjUpdates, setPrjUpdates] = useState([]);
    const [inputDisabled, setInputDisabled] = useState(true);

    const loadProjectData = useCallback(() => {
        fetch("http://localhost:8080/operations/project?id=" + params.id,{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            // console.log(result);
            var formattedDate = handleDate(result.dueDate);
            setInputs({
                name: result.name,
                owner: result.ownerId,
                ownerName: result.ownerName,
                dueDate: formattedDate,
                status: result.status,
                statusName: result.statusName,
                description: result.description
            })
        })
        .catch (e => {
            console.log("ERROR_loadProjectData: " + e);
        })

        // Get status options
        fetch("http://localhost:8080/operations/project/statuses",{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            setOptions(result);
        })
        .catch (e => {
            console.log("ERROR_loadProjectData_statuses: " + e);
        })

        //Get project updates
        fetch("http://localhost:8080/operations/project/updates/" + params.id,{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            console.log(result);
            setPrjUpdates(result);
        })
        .catch (e => {
            console.log("ERROR_loadProjectData_updates: " + e);
        })
    }, [params])

    useEffect(() => {
        loadProjectData();
    }, [params, loadProjectData]);

    function handleInputChange(e) {
        if (e.target) {
            const name = e.target.name;
            const value = e.target.value;
            setInputs(prevState => ({...prevState, [name]: value}));
            console.log(inputs);
        }
        else {
            setInputs(prevState => ({...prevState, 'status': e.value, 'statusName': e.label}));
            handleUpdateStatus(e.value);
        }
    }

    function handleUpdateStatus(status) {
        fetch("http://localhost:8080/operations/project/" + params.id + "/update?status=" + status,{
            method:"POST"
        })
        .then((result)=>{
            if (result.ok) {
                alert("Project status updated successfully!");
                window.location.reload();
            }
        })
        .catch (e => {
            console.log("ERROR_updateStatus: " + e);
        })
    }

    function handleEditClick() {
        setInputDisabled(false);
    }

    function handleCancelEditClick() {
        window.location.reload(); 
    }

    function handleEditSubmit() {
        console.log(inputs);
        fetch("http://localhost:8080/operations/project/edit",{
            method:"POST",
            body: JSON.stringify({
                'id': params.id,
                'name': inputs.name,
                'dueDate': inputs.dueDate,
                'description': inputs.description
            }),
            headers: { "Content-type": "application/json; charset=UTF-8" }
        })
        .then((result)=>{
            console.log(result);
            if (result.ok) {
                alert("Project updated successfully!");
                window.location.reload();
            }
        })
        .catch (e => {
            console.log("ERROR_handleEditSubmit: " + e);
        })
    }

    return (
<div>
<NavigationBar/>
    <div class="mt-5">
        <div class="row d-flex justify-content-end pe-5">
            <Button onClick={handleEditClick} className="" style={{width: '50px', height: '50px'}}><i class="bi bi-pencil-square"></i></Button>
        </div>
        <Container>
        <Row>
        <label>
            Project Name: <input name="name" class="form-control" value={inputs.name} onChange={handleInputChange} disabled={inputDisabled} />
        </label>
        </Row>
        <br></br>

            <Row>
                <Col>
                    <label>
                    Owner:<input name="owner" class="form-control" value={inputs.ownerName} disabled/>
                    </label>
                </Col>
                <Col>
                Status:<Select options={options} value={{label: inputs.statusName, value: inputs.status}} onChange={handleInputChange}/>
                </Col>
                <Col>
                Due Date:
                <input type="datetime-local" class="form-control" name="dueDate" value={inputs.dueDate} 
                onChange={(e) => handleInputChange(e)}  disabled={inputDisabled}/>
                </Col>
            </Row>
            <div class="my-4 mx-1">
            <Row>
                Description:
                <textarea class="form-control" name="description" rows={4} cols={40} value={inputs.description} onChange={handleInputChange} disabled={inputDisabled} />
            </Row>
            </div>
        </Container>
        <div class="row d-flex justify-content-end pe-5">
            <Button onClick={handleEditSubmit} className="btn-warning me-3" style={{width: '150px', height: '50px', display: inputDisabled ? "none" : "block"}} >Submit</Button>
            <Button onClick={handleCancelEditClick} className="btn-secondary" style={{width: '150px', height: '50px', display: inputDisabled ? "none" : "block"}} >Cancel Editing</Button>
        </div>
    </div>
    
    <div class="mt-5">
    <Container>
        <Row>
            Updates
        </Row>
        {prjUpdates.map(p => 
            <Row>
                <div class="row my-1">
                    <div class="card w-50" style={{minWidth: '500px'}}>
                        <div class="row">
                            <div class="col-6">{p.writerName}</div>
                            <div class="col-6 text-end">@{dateFormat(p.createTime)}</div>
                        </div>
                        <hr/>
                        <div class="row">
                            <div style={{width: '80%'}}>{p.comment}</div>
                        </div>
                    </div>
                </div>
            </Row>
        )}
    </Container>
    </div>
</div>
);
};
 
export default ProjectInfo;