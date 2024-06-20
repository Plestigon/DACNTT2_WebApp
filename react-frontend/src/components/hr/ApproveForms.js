import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import { dateTimeFormat, dateFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
import "../../css/utils.css"
import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useAuthentication } from "../system/Authentication";
 
function ApproveForms() {
    const auth = useAuthentication();
    const [forms, setForms] = useState([]);
    const navigate = useNavigate();

    function loadFormData() {
        const toastId = loading("Loading forms...");
        fetch(process.env.REACT_APP_API_URI + "/hr/forms/" + auth.id + "/approve?approverId=" + auth.id + "&token=" + auth.token,{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            if (result.statusCode === 200) {
                setForms(result.data);
            }
            else {
                error("Load forms failed");
            }
        })
        .catch (e => {
            console.log("ERROR_loadFormData: " + e);
            error("Load forms failed");
        })
    }

    useEffect(()=>{
        loadFormData();
    }, [])

    function approveForm(id, value) {
        fetch(process.env.REACT_APP_API_URI + "/hr/forms/" + id + "?approve=" + value + "&token=" + auth.token,{
            method:"PUT",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success(result.data);
                loadFormData();
            }
            else {
                error(result.message);
            }
        })
        .catch (e => {
            console.log("ERROR_approveForm: " + e);
        })
    }

    return (
    <div>
        <SideBar/>
        <TopBar/>
        <div class="content container">
            {/* <div class="row mb-2 px-5">
                <Button onClick={() => navigate("/hr/submit-form")}><i class ="bi bi-pen"></i> Write a form</Button>
            </div> */}
            <div class="card table-card table-responsive">
                <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{width:'100%'}}>
                <thead class="table-primary">
                    <tr>
                        <th scope="col">Form Type</th>
                        <th scope="col">Submitter</th>
                        <th scope="col">SubmitterEmail</th>
                        {/* <th scope="col">Create Date</th> */}
                        <th scope="col">Start Date</th>
                        <th scope="col">End Date</th>
                        <th scope="col">Reason</th>
                        {/* <th scope="col" style={{width:'100px'}}>Status</th> */}
                        <th scope="col" style={{width:'100px'}}></th>
                    </tr>
                </thead>
                <tbody>
                    {forms.map(f=>(
                        <tr key={f.id}>
                            <td>{f.typeName}</td>
                            <td>{f.ownerName}</td>
                            <td>{f.ownerEmail}</td>
                            {/* <td>{dateTimeFormat(f.createDate)}</td> */}
                            <td>{dateFormat(f.startDate)}</td>
                            <td>{dateFormat(f.endDate)}</td>
                            <td>{f.reason}</td>
                            {/* <td><div class={"card status-card form-status-" + f.status}>{f.statusName}</div></td> */}
                            <td class="d-flex">
                                <button type="button" class="btn btn-success" onClick={() => approveForm(f.id, true)}><i class="bi bi-check"></i></button>
                                <button type="button" class="btn btn-danger" onClick={() => approveForm(f.id, false)}><i class="bi bi-x"></i></button>
                            </td>
                        </tr>
                    ))}
                </tbody>
                </table>
            </div>
        </div>
    </div>
    );
};
 
export default ApproveForms;