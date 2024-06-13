import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import NavigationBar from '../NavigationBar';
import { useEffect, useState } from 'react';
import NewProjectModal from "./NewProjectModal";
import { useNavigate } from 'react-router-dom';
 
// function Operations() {
//     const[projects,setProjects] = useState([]);
//     const[newPrjModalShow, setNewPrjModalShow] = useState(false);
//     const navigate = useNavigate();

//     useEffect(()=>{
//         fetchProjectData();
//     }, [])

//     function fetchProjectData() {
//         fetch(process.env.REACT_APP_API_URI + "/operations/projects",{
//             method:"GET"
//         })
//         .then(result=>result.json())
//         .then((result)=>{
//             setProjects(result);
//         })
//         .catch (e => {
//             console.log("ERROR_fetchProjectData: " + e);
//         })
//     }
    
//     function deleteProject(id) {
//         try {
//             if (window.confirm("Are you sure you want to delete project with id \"" + id + "\"?")) {
//                 fetch(process.env.REACT_APP_API_URI + "/operations/project?id=" + id, {
//                     method:"DELETE"
//                 })
//                 .then((response) => {
//                     if (response.ok) {
//                         alert("Deleted successfully!");
//                         fetchProjectData();
//                     }
//                     else {
//                         console.log(response);
//                     }
//                 })
//             }
//         }
//         catch(error) {
//             console.log(error);
//         }
//     }

//     function projectDetails(id) {
//         navigate('/operations/project/' + id);
//     }

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
                    <th scope="col">Deal</th>
                    <th scope="col">Stage</th>
                    <th scope="col">Status</th>
                    <th scope="col">Deal Value</th>
                    <th scope="col">Contacts</th>
                    <th scope="col">Close Date</th>
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