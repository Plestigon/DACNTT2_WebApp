import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import { dateFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import { Button } from "react-bootstrap";
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
import { useAuthentication } from "../system/Authentication";
import { useNavigate } from "react-router-dom";
 
function MyContracts() {
    const auth = useAuthentication();
	const navigate = useNavigate();

    const [contracts, setContracts] = useState([]);

    useEffect(() => {
		document.title = 'My Contracts - TDTU EMS';
	}, []);

    useEffect(()=>{
        loadContractData();
    }, [])

    function loadContractData() {
        const toastId = loading("Loading contracts...");
        fetch(process.env.REACT_APP_API_URI + "/hr/contracts/" + auth.id + "?token=" + auth.token,{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            if (result.statusCode === 200) {
                setContracts(result.data);
            }
            else {
                error("Load contracts failed");
            }
        })
        .catch (e => {
            console.log("ERROR_loadContractData: " + e);
            error("Load contracts failed");
        })
    }

    return (
    <div>
        <SideBar/>
        <TopBar/>
        <div class="content container">
            <div class="row mb-2 px-5">
                <Button onClick={() => navigate("/hr/new-contract")}><i class="bi bi-file-earmark-text"></i> Create New Contract</Button>
            </div>
            <div class="card table-card table-responsive">
                <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{width:'100%'}}>
                <thead class="table-primary">
                    <tr>
                        <th scope="col">Contract Code</th>
                        <th scope="col">Contract Type</th>
                        <th scope="col">Department</th>
                        <th scope="col">Effective Date</th>
                        <th scope="col">End Date</th>
                        <th scope="col" style={{width:'180px'}}>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {contracts.map(c=>(
                        <tr key={c.id}>
                            <td>{c.code}</td>
                            <td>{c.typeName}</td>
                            <td>{c.departmentName}</td>
                            <td>{dateFormat(c.timeStart)}</td>
                            <td>{dateFormat(c.timeEnd)}</td>
                            <td><div class={"card status-card contract-status-" + c.status}>{c.statusName}</div></td>
                        </tr>
                    ))}
                </tbody>
                </table>
            </div>
        </div>
    </div>
    );
};
 
export default MyContracts;