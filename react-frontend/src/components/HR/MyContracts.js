import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import { dateFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import Notify, {success, loading, dismiss} from "../../utils/Notify";
 
function MyContracts() {

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
            </tbody>
            </table>
        </div>
    </div>
    );
};
 
export default MyContracts;