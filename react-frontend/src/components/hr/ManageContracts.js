import React, { useCallback } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import { dateFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import { Button } from "react-bootstrap";
import { error, loading, dismiss, success } from "../../utils/Notify";
import { useAuthentication } from "../system/Authentication";
import { useNavigate } from "react-router-dom";
import Pagination from "../../utils/Pagination";

function ManageContracts() {
    const auth = useAuthentication();
    const navigate = useNavigate();

    const [contracts, setContracts] = useState([]);
    const [page, setPage] = useState(1);
    const [totalCount, setTotalCount] = useState(0);

    useEffect(() => {
        document.title = 'Manage Contracts - TDTU EMS';
    }, []);

    const loadContractData = useCallback(() => {
        const toastId = loading("Loading contracts...");
        fetch(process.env.REACT_APP_API_URI + "/hr/contracts/" + auth.id + "/manage?page=" + page + "&token=" + auth.token, {
            method: "GET",
            headers: { "ngrok-skip-browser-warning": "true" }
        })
            .then(result => result.json())
            .then((result) => {
                dismiss(toastId);
                if (result.statusCode === 200) {
                    setContracts(result.data);
                    setTotalCount(result.totalCount);
                }
                else {
                    error("Load contracts failed");
                }
            })
            .catch(e => {
                console.log("ERROR_loadContractData: " + e);
                error("Load contracts failed");
            })
    }, [auth.token, auth.id, page]);

    useEffect(() => {
        loadContractData();
    }, [loadContractData])

    function updateContract(id, value) {
        fetch(process.env.REACT_APP_API_URI + "/hr/contracts/" + id + "?value=" + value + "&token=" + auth.token, {
            method: "PUT",
            headers: { "ngrok-skip-browser-warning": "true" }
        })
            .then(result => result.json())
            .then((result) => {
                if (result.statusCode === 200) {
                    success(result.data);
                    loadContractData();
                }
                else {
                    error(result.message);
                }
            })
            .catch(e => {
                console.log("ERROR_updateContract: " + e);
            })
    }

    return (
        <div>
            <SideBar />
            <TopBar />
            <div class="content container">
                <div class="card table-card table-responsive mt-5">
                    <table class="table-clickable table table-hover table-collapsed" id="project-table" style={{ width: '100%' }}>
                        <thead class="table-primary">
                            <tr>
                                <th scope="col">Contract Code</th>
                                <th scope="col">Contract Type</th>
                                <th scope="col">Owner</th>
                                <th scope="col">Department</th>
                                <th scope="col">Effective Date</th>
                                <th scope="col">End Date</th>
                                <th scope="col" style={{ width: '180px' }}>Status</th>
                                <th scope="col" style={{ width: '100px' }}></th>
                            </tr>
                        </thead>
                        <tbody>
                            {contracts.map(c => (
                                <tr key={c.id}>
                                    <td>{c.code}</td>
                                    <td>{c.typeName}</td>
                                    <td>{c.ownerName}</td>
                                    <td>{c.departmentName}</td>
                                    <td>{dateFormat(c.timeStart)}</td>
                                    <td>{dateFormat(c.timeEnd)}</td>
                                    <td><div class={"card status-card contract-status-" + c.status}>{c.statusName}</div></td>
                                    <td class="d-flex">
                                        <button type="button" class="btn btn-success h1 mx-1" style={{ padding: "0", width: "40px", height: "40px" }}
                                            onClick={() => updateContract(c.id, true)} title="Approve"
                                            disabled={c.status === 1 ? false : true}><i class="bi bi-check"></i></button>
                                        <button type="button" class="btn btn-danger h1 mx-1" style={{ padding: "0", width: "40px", height: "40px" }}
                                            onClick={() => updateContract(c.id, false)} title={c.status === 1 ? "Reject" : "End"}
                                            disabled={c.status === 1 || c.status === 2 ? false : true}><i class="bi bi-x"></i></button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <Pagination page={page} setPage={setPage} totalCount={totalCount} />
            </div>
        </div>
    );
};

export default ManageContracts;