import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import { dateTimeFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
import DeleteConfirmModal from "../operations/DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";
import AddEmpModal from "./AddEmpModal";
 
function EmpList() {
    const auth = useAuthentication();
    const[employees, setEmployees] = useState([]);
    const[addEmpModalShow, setAddEmpModalShow] = useState(false);
    const[showDeleteModal, setShowDeleteModal] = useState(false);
    const[deleteTarget, setDeleteTarget] = useState({
        id: 0,
        name: ''
    });

    useEffect(()=>{
        fetchEmployees();
    }, [])

    function fetchEmployees() {
        const toastId = loading("Loading employee data...");
        fetch("http://localhost:8080/hr/employees?token=" + auth.token,{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            if (result.statusCode === 200) {
                setEmployees(result.data);
            }
            else {
                error("Load employee data failed");
            }
        })
        .catch (e => {
            console.log("ERROR_fetchEmployees: " + e);
        })
    }
    
    function deleteBtnClick(e, id, name) {
        e.stopPropagation();
        setDeleteTarget({'id': id, 'name': name});
        setShowDeleteModal(true);
    }

    function deleteEmployee() {
        setShowDeleteModal(false);
        // if (deleteTarget.id === null || deleteTarget.id <= 0) {return;}
        // fetch("http://localhost:8080/operations/project?id=" + deleteTarget.id, {
        //     method:"DELETE"
        // })
        // .then((response) => {
        //     console.log(response);
        //     if (response.ok) {
        //         success("Project deleted");
        //         fetchEmployees();
        //     }
        // })
        // .catch(e => {
        //     console.log("ERROR_deleteProject: " + e);
        // })
        // setDeleteTarget({'id': 0, 'name': ''});
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
            <AddEmpModal show={addEmpModalShow} onHide={() => setAddEmpModalShow(false)} reload={fetchEmployees}/>
            <div class="row mb-2 px-5">
                <Button class="btn btn-primary" onClick={() => setAddEmpModalShow(true)}>
                    <i class="bi bi-plus-circle me-2"></i>Add New Employee
                </Button>
            </div>
            <div class="card table-card table-responsive">
                <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{width:'100%'}}>
                <thead class="table-primary">
                    <tr>
                        <th scope="col" style={{width:'50px'}}>ID</th>
                        <th scope="col" style={{width:'30%'}}>Full Name</th>
                        <th scope="col" style={{width:'10%'}}>Department</th>
                        <th scope="col">Role</th>
                        <th scope="col">Join date</th>
                        <th scope="col" style={{width:'50px'}}></th>
                    </tr>
                </thead>
                <tbody>

                    {employees.map(em=>(
                        // <tr key={e.id} title="See Project's details" style={{cursor:"pointer"}} onClick={() => projectDetails(e.id)}>
                        <tr key={em.id}>
                            <td>{em.id}</td>
                            <td>{em.name}</td>
                            <td>{em.departmentName}</td>
                            <td>{em.roleDetail}</td>
                            <td>{dateTimeFormat(em.joinDate)}</td>
                            <td><button type="button" class="btn btn-danger bi bi-trash"
                                onClick={(e) => deleteBtnClick(e, em.id, em.name)}></button></td>
                        </tr>
                    ))}
                </tbody>
                </table>
            </div>
        </div>
        <DeleteConfirmModal show={showDeleteModal} onHide={() => {setShowDeleteModal(false); setDeleteTarget({'id': 0, 'name': ''})}} 
        message={"Delete employee \"" + deleteTarget.name + "\"?"} delete={deleteEmployee}/>
    </div>
    );
};
 
export default EmpList;