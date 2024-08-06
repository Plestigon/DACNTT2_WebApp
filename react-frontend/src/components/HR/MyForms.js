import React, { useCallback } from "react";
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
import Pagination from "../../utils/Pagination";
 
function MyForms() {
    const auth = useAuthentication();
    const [forms, setForms] = useState([]);
    const [page, setPage] = useState(1);
	const [totalCount, setTotalCount] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
		document.title = 'My Forms - TDTU EMS';
	}, []);

    useEffect(() => {
        const query = new URLSearchParams(window.location.search);
        const submitted = query.get("submitted");
        if (submitted) {
            success("Form submitted successfully!");
        }
    }, []);

    const loadFormData = useCallback(() => {
        const toastId = loading("Loading forms...");
        fetch(process.env.REACT_APP_API_URI + "/hr/forms/" + auth.id + "?page=" + page + "&token=" + auth.token,{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            console.log(result);
            if (result.statusCode === 200) {
                setForms(result.data);
                setTotalCount(result.totalCount);
            }
            else {
                error("Load forms failed");
            }
        })
        .catch (e => {
            console.log("ERROR_loadFormData: " + e);
            error("Load forms failed");
        })
    }, [auth.token, page]);

    useEffect(()=>{
        loadFormData();
    }, [loadFormData])

    return (
    <div>
        <SideBar/>
        <TopBar/>
        <div class="content container">
            <div class="row mb-2 px-5">
                <Button onClick={() => navigate("/hr/submit-form")}><i class ="bi bi-pen"></i> Write a form</Button>
            </div>
            <div class="card table-card table-responsive">
                <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{width:'100%'}}>
                <thead class="table-primary">
                    <tr>
                        <th scope="col">Form Type</th>
                        <th scope="col">Create Date</th>
                        <th scope="col">Start Date</th>
                        <th scope="col">End Date</th>
                        <th scope="col">Reason</th>
                        <th scope="col" style={{width:'200px'}}>Status</th>
                        <th scope="col">Notes</th>
                    </tr>
                </thead>
                <tbody>
                    {forms.map(f=>(
                        <tr key={f.id}>
                            <td>{f.typeName}</td>
                            <td>{dateTimeFormat(f.createDate)}</td>
                            <td>{dateFormat(f.startDate)}</td>
                            <td>{dateFormat(f.endDate)}</td>
                            <td>{f.reason}</td>
                            <td><div class={"card status-card form-status-" + f.status}>{f.statusName}</div></td>
                            <td>{f.notes}</td>
                        </tr>
                    ))}
                </tbody>
                </table>
            </div>
            <Pagination 
                page = {page}
                setPage = {setPage}
                totalCount = {totalCount}
            />
        </div>
    </div>
    );
};
 
export default MyForms;