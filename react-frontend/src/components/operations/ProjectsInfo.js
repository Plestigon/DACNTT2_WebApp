import React, { useCallback } from "react";
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import NavigationBar from '../NavigationBar'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Select from 'react-select';
import { handleDate } from "../../utils/DateHelper";
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
        desc: ''
    });

    const loadProjectData = useCallback(() => {
        fetch("http://localhost:8080/operations/project?id=" + params.id,{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            console.log(result);
            var formattedDate = handleDate(result.dueDate);
            setInputs({
                name: result.name,
                owner: result.ownerId,
                ownerName: result.ownerName,
                dueDate: formattedDate,
                status: result.status,
                statusName: result.statusName,
                desc: result.description
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
    }, [params])

    useEffect(() => {
        loadProjectData();
    }, [params, loadProjectData]);

    function handleInputChange(e) {
        if (e.target) {
            const name = e.target.name;
            const value = e.target.value;
            setInputs(prevState => ({...prevState, [name]: value}));
        }
        else {
            setInputs(prevState => ({...prevState, 'status': e.value, 'statusName': e.label}));
            updateStatus(e.value);
        }
    }

    function updateStatus(status) {
        fetch("http://localhost:8080/operations/project/" + params.id + "/update?status=" + status,{
            method:"POST"
        })
        .then((result)=>{
            if (result.ok) {
                alert("Project status updated successfully!");
            }
        })
        .catch (e => {
            console.log("ERROR_updateStatus: " + e);
        })
    }

    return (
<div>
<NavigationBar/>
    <div class="mt-5">
        <div class="row d-flex justify-content-end pe-5">
            <Button className="" style={{width: '50px', height: '50px'}}><i class="bi bi-pencil-square"></i></Button>
        </div>
        <Container>
        <Row>
        <label>
            Project Name: <input name="ProjName" class="form-control" defaultValue="Project Name" disabled />
        </label>
        </Row>
        <br></br>

            <Row>
                <Col>
                    <label>
                    Owner:<input name="Owner" class="form-control" defaultValue="Project Owner" disabled></input>
                    </label>
                </Col>
                <Col>
                Status:<Select options={options} value={{label: inputs.statusName, value: inputs.status}} onChange={handleInputChange}/>
                </Col>
                <Col>
                Due Date:
                <input type="datetime-local" class="form-control" name="dueDate" value={inputs.dueDate} 
                onChange={(e) => handleInputChange(e)} disabled/>
                </Col>
            </Row>
            <div class="my-4 mx-1">
            <Row>
                Description:
                <textarea class="form-control" name="descriptions" rows={4} cols={40} value={inputs.desc} disabled />
            </Row>
            </div>
        </Container>
    </div>
</div>
);
};
 
export default ProjectInfo;