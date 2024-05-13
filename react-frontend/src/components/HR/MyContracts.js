import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import NewProjectModal from "./NewProjectModal";
import { dateFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import Notify, {success, loading, dismiss} from "../../utils/Notify";
 
function Contracts() {

    function contractsDetails(id) {
        // navigate('/operations/project/' + id);
        var win = window.open('/operations/project/' + id, '_blank');
        win.focus();
    }

    return (
    <div>
        <Notify/>
        <SideBar/>
        <TopBar/>
        <div class="content container pt-3 px-4">
            <NewProjectModal show={newPrjModalShow} onHide={() => setNewPrjModalShow(false)}/>
            <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{width:'100%'}}>
            <thead class="table-primary">
                <tr>
                    <th scope="col">Contract ID</th>
                    <th scope="col">Contract Type</th>
                    <th scope="col">Department</th>
                    <th scope="col">Effective Date</th>
                    <th scope="col" style={{width:'30%'}}>Status</th>
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
 
export default Contracts;