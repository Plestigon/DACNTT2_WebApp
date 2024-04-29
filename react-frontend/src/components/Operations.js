import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import ResponsiveAppBar from '../components/AppBar';
import {useEffect, useState} from 'react';
 
const Operations = () => {
    const[projects,setProjects]=useState([])

    const fetchProjectData = () => {
        fetch("http://localhost:8080/operations/projects",{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
        if (result)
            setProjects(result);
        })
    }

    useEffect(()=>{
        // fetch("http://localhost:8080/operations/projects",{
        //     method:"GET"
        // })
        // .then(result=>result.json())
        // .then((result)=>{
        //     if (result)
        //     setProjects(result);
        // })
        fetchProjectData();
    }, [])
    

    const deleteProject = (id) => {
        try {
            if (window.confirm("Are you sure you want to delete project with id \"" + id + "\"?")) {
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

    return (
    <div>
        <ResponsiveAppBar/>
        {/*New Project Form*/}
    {/* <div class="container justify-content-center my-1 overlay" id="newPrjFormContainer" style="display: none;">
        <div class="card shadow h-100 px-3 py-3 overlay" style="width: 80%; max-width: 500px; background-color: #EEEEEE;">
            <p class="h4 text-center">Create New Project</p>
            <form class="newPrjForm">
                <div class="row my-2">
                    <div class="col-12">
                        <label for="newPrjName">Project name</label>
                        <input type="text" class="form-control" id="newPrjName" placeholder="New project's name" required>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-12">
                        <label for="newPrjDescription">Project description</label>
                        <input type="text" class="form-control" id="newPrjDescription" placeholder="New project's description" required>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-6">
                        <label for="newPrjOwner">Owner</label>
                        <select class="form-select" id="newPrjOwner">
                            <option selected value="0">Open this select menu</option>
                            <option value="1">One</option>
                            <option value="2">Two</option>
                            <option value="3">Three</option>
                        </select>
                    </div>
                    <div class="col-6">
                        <label for="newPrjDueDate">Due date</label>
                        <input type="datetime-local" class="form-control" id="newPrjDueDate">
                    </div>
                </div>
                <div class="row my-3 mx-1">
                    <div class="col-6">
                        <button type="submit" class="btn btn-primary w-100" id="newPrjBtnSubmit">Submit</button>
                    </div>
                    <div class="col-6">
                        <button type="button" class="btn btn-secondary w-100" id="newPrjBtnCancel">Cancel</button>
                    </div>
                </div>
            </form>
        </div>
    </div> */}


        <div class="container pt-3">
            <button type="button" class="btn btn-outline-primary my-2" id="newPrjBtn"><i class="bi bi-plus-circle me-2"></i>Create New Project</button>
            
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
                    <tr key={p.id}>
                        <td>{p.name}</td>
                        <td>{p.ownerName}</td>
                        <td>{p.status}</td>
                        <td>{p.dueDate}</td>
                        <td>{p.description}</td>
                        <td><button type="button" class="btn btn-primary bi bi-trash delete-prj-btn"
                            onClick={() => deleteProject(p.id)}></button></td>
                    </tr>
                ))}
            </tbody>
            </table>
        </div>
    </div>
    );
};
 
export default Operations;