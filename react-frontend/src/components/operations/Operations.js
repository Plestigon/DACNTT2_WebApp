import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import NewProjectModal from "./NewProjectModal";
import { dateTimeFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
import DeleteConfirmModal from "./DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";


 function Operations() {
    const auth = useAuthentication();
    const[projects, setProjects] = useState([]);
    const[newPrjModalShow, setNewPrjModalShow] = useState(false);
    const[showDeleteModal, setShowDeleteModal] = useState(false);
    const[deleteTarget, setDeleteTarget] = useState({
        id: 0,
        name: ''
    });

    useEffect(()=>{
        fetchProjectData();
    }, [])

    function fetchProjectData() {
        const toastId = loading("Loading project data...");
        fetch("http://localhost:8080/operations/projects" + "?token=" + auth.token,{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            if (result.statusCode === 200) {
                setProjects(result.data);
            }
            else {
                error("Load project data failed");
            }
        })
        .catch (e => {
            console.log("ERROR_fetchProjectData: " + e);
            dismiss(toastId);
            error("Load project data failed");
        })
    }
    
    function deleteBtnClick(e, id, name) {
        e.stopPropagation();
        setDeleteTarget({'id': id, 'name': name});
        setShowDeleteModal(true);
    }

    function deleteProject() {
        setShowDeleteModal(false);
        if (deleteTarget.id === null || deleteTarget.id <= 0) {return;}
        fetch("http://localhost:8080/operations/project?id=" + deleteTarget.id, {
            method:"DELETE"
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("Project deleted");
                fetchProjectData();
            }
        })
        .catch(e => {
            console.log("ERROR_deleteProject: " + e);
        })
        setDeleteTarget({'id': 0, 'name': ''});
    }

    function projectDetails(id) {
        // navigate('/operations/project/' + id);
        var win = window.open('/operations/project/' + id, '_blank');
        win.focus();
    }

    return (
    <div>
        <Notify/>
        <SideBar/>
        <TopBar/>
        <div class="content container">
            <NewProjectModal show={newPrjModalShow} onHide={() => setNewPrjModalShow(false)} reload={fetchProjectData}/>
            <div class="row mb-2 px-5">
                <Button class="btn btn-primary" id="newPrjBtn" onClick={() => setNewPrjModalShow(true)}>
                    <i class="bi bi-plus-circle me-2"></i>Create New Project
                </Button>
            </div>
            <div class="card table-card table-responsive">
                <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{width:'100%'}}>
                <thead class="table-primary">
                    <tr>
                        <th scope="col">Project</th>
                        <th scope="col">Owner</th>
                        <th scope="col">Status</th>
                        <th scope="col">Create date</th>
                        <th scope="col">Due date</th>
                        <th scope="col">Description</th>
                        <th scope="col" style={{width:'50px'}}></th>
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
                        <tr key={p.id} title="See Project's details" style={{cursor:"pointer"}} onClick={() => projectDetails(p.id)}>
                            <td>{p.name}</td>
                            <td>{p.ownerName}</td>
                            <td><div class={"card status-card project-status-" + p.status}>{p.statusName}</div></td>
                            <td>{dateTimeFormat(p.createDate)}</td>
                            <td>{dateTimeFormat(p.dueDate)}</td>
                            <td>{p.description}</td>
                            <td><button type="button" class="btn btn-danger bi bi-trash delete-prj-btn"
                                onClick={(e) => deleteBtnClick(e, p.id, p.name)}></button></td>
                        </tr>
                    ))}
                </tbody>
                </table>
            </div>
        </div>
        <DeleteConfirmModal show={showDeleteModal} onHide={() => {setShowDeleteModal(false); setDeleteTarget({'id': 0, 'name': ''})}} 
        message={"Delete project \"" + deleteTarget.name + "\"?"} delete={deleteProject}/>
    </div>
    );
};
 
export default Operations;