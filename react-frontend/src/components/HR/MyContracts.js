import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import { dateFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
 
function MyContracts() {
    const [contracts, setContracts] = useState([]);

    useEffect(()=>{
        loadContractData();
    }, [])

    function loadContractData() {
        const toastId = loading("Loading contract data...");
        fetch("http://localhost:8080/hr/contracts/1",{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            if (result.statusCode === 200) {
                setContracts(result.data);
            }
            else {
                error("Load contract data failed");
            }
        })
        .catch (e => {
            console.log("ERROR_loadContractData: " + e);
        })
    }

    return (
    <div>
        <SideBar/>
        <TopBar/>
        <div class="content container">
            
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