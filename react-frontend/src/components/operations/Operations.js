import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import NavigationBar from '../NavigationBar';
import { useEffect, useState } from 'react';
import NewProjectModal from "./NewProjectModal";
import { Link, useNavigate } from 'react-router-dom';
import { dateFormat } from "../../utils/DateHelper";
 
function Operations() {
    const[projects,setProjects] = useState([]);
    const[newPrjModalShow, setNewPrjModalShow] = useState(false);
    const navigate = useNavigate();

    useEffect(()=>{
        fetchProjectData();
    }, [])

    function fetchProjectData() {
        fetch("http://localhost:8080/operations/projects",{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            setProjects(result);
        })
        .catch (e => {
            console.log("ERROR_fetchProjectData: " + e);
        })
    }
    
    function deleteProject(e, id, name) {
        e.stopPropagation();
        try {
            if (window.confirm("Are you sure you want to delete project \"" + name + "\"?")) {
                fetch("http://localhost:8080/operations/project?id=" + id, {
                    method:"DELETE"
                })
                .then((response) => {
                    if (response.ok) {
                        alert("Deleted successfully!");
                        fetchProjectData();
                    }
                    else {
                        console.log(response);
                    }
                })
            }
        }
        catch(error) {
            console.log(error);
        }
    }

    function projectDetails(id) {
        // navigate('/operations/project/' + id);
        var win = window.open('/operations/project/' + id, '_blank');
        win.focus();
    }

    return (
    <div>
        <NavigationBar/>
        <div class="container pt-3">
            <button type="button" class="btn btn-outline-primary my-2" id="newPrjBtn" onClick={() => setNewPrjModalShow(true)}>
                <i class="bi bi-plus-circle me-2"></i>Create New Project
            </button>
            <NewProjectModal show={newPrjModalShow} onHide={() => setNewPrjModalShow(false)}/>
            <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{width:'100%'}}>
            <thead class="table-primary">
                <tr>
                    <th scope="col">Project</th>
                    <th scope="col">Owner</th>
                    <th scope="col">Status</th>
                    <th scope="col">Due date</th>
                    <th scope="col" style={{width:'30%'}}>Description</th>
                    <th scope="col" style={{width:'10%'}}></th>
                </tr>
            </thead>
            <tbody>
                {/* <tr>
                    <td>Project 1</td>
                    <td>Owner 1</td>
                    <td>Status 1</td>
                    <td>Due date 1</td>
                    <td>Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 </td>
                    <td>
                        <button type="button" class="btn btn-primary bi bi-trash delete-prj-btn" style={{float: 'right'}}>
                        </button>
                    </td>
                </tr> */}
                {projects.map(p=>(
                    <tr key={p.id} onClick={() => projectDetails(p.id)}>
                        <td>
                            {/* <Link to={'/operations/project/' + p.id} className="text-decoration-none" 
                        style={{display: 'inline-block', height: '100%', width: '100%'}}> */}
                            {p.name}
                            {/* </Link> */}
                            </td>
                        <td>{p.ownerName}</td>
                        <td>{p.statusName}</td>
                        <td>{dateFormat(p.dueDate)}</td>
                        <td>{p.description}</td>
                        <td><button type="button" class="btn btn-primary bi bi-trash delete-prj-btn"
                            onClick={(e) => deleteProject(e, p.id, p.name)}></button></td>
                    </tr>
                ))}
            </tbody>
            </table>
        </div>
    </div>
    );
};
 
export default Operations;