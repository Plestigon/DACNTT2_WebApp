import React from "react";
import { useEffect, useState } from 'react';
import SideBar from "../SideBar";
import TopBar from "../TopBar";
import Select from 'react-select';
import { Button } from "react-bootstrap";
import Notify, { info, error } from "../../utils/Notify";
import { getDefaultStartDate, getDefaultEndDate, handleDate } from "../../utils/DateHelper";
import { useNavigate } from "react-router-dom";
import { useAuthentication } from "../system/Authentication";

function SubmitForm() {
    const auth = useAuthentication();
    const navigate = useNavigate();

    const [inputs, setInputs] = useState({
        type: 0,
        typeName: '',
        approver: 0,
        approverName: '- Select approver -',
        startDate: getDefaultStartDate(),
        endDate: getDefaultEndDate(),
        reason: ''
    });
    const [options, setOptions] = useState([
        // {label: 'Paid Leave', value: 1},
        // {label: 'Unpaid Leave', value: 2},
        // {label: 'Resignation', value: 3}
    ]);
    const [approvers, setApprovers] = useState([]);
    const [disableDates, setDisableDates] = useState(false);
    const [showErr, setShowErr] = useState(false);

    useEffect(() => {
        function loadFormTypes() {
            fetch(process.env.REACT_APP_API_URI + "/hr/submit-form/types",{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                setOptions(result);
                setInputs(prevState => ({...prevState, type: result[0].value, typeName: result[0].label}))
            })
            .catch (e => {
                console.log("ERROR_loadFormTypes: " + e);
            })
        }
        loadFormTypes();
    }, []);

    useEffect(() => {
        function loadApprovers() {
            fetch(process.env.REACT_APP_API_URI + "/hr/forms/approvers?userId=" + auth.id + "&token=" + auth.token,{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                console.log(result);
                if (result.statusCode === 200) {
                    setApprovers(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadFormTypes: " + e);
            })
        }
        loadApprovers();
    }, []);

    function handleInputChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        if (name === 'startDate') {
            var startDate = new Date(value);
            var endDate = new Date(inputs.endDate);
            var tomorrow = new Date();
            tomorrow.setDate(tomorrow.getDate() + 1);
            if (endDate <= startDate) {
                endDate = new Date(startDate);
                endDate.setDate(endDate.getDate() + 1);
                setInputs(prevState => ({...prevState, 'endDate': handleDate(endDate)}));
            }
        }
        if (name === 'endDate') {
            var startDate = new Date(inputs.startDate);
            var endDate = new Date(value);
            if (endDate <= startDate) {
                error("End date must be after Start date");
                return;
            }
        }
        setInputs(prevState => ({...prevState, [name]: value}));
    }

    function handleFormTypeChange(e) {
        if (e.label === 'Resignation') {
            setDisableDates(true);
        }
        else if (disableDates) {
            setDisableDates(false);
        }
        setInputs(prevState => ({...prevState, 'type': e.value, 'typeName': e.label}));
    }

    function handleApproverChange(e) {
        setShowErr(false);
        setInputs(prevState => ({...prevState, 'approver': e.value, 'approverName': e.label}));
    }

    function handleSubmit() {
        if (inputs.approver === 0) {
            setShowErr(true);
            return;
        }
        if (inputs.typeName === 'Resignation') {
            setInputs(prevState => ({...prevState, 'startDate': '', 'endDate': ''}));
        }
        // console.log(inputs);
        fetch(process.env.REACT_APP_API_URI + "/hr/submit-form" + "?token=" + auth.token,{
            method:"POST",
            body: JSON.stringify({
                'type': inputs.type,
                'ownerId': auth.id,
                'approverId': inputs.approver,
                'startDate': inputs.startDate,
                'endDate': inputs.endDate,
                'reason': inputs.reason
            }),
            headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "ngrok-skip-browser-warning" : "true"
            }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                navigate("/hr/my-forms?submitted=true");
            }
        })
        .catch (e => {
            console.log("ERROR_handleSubmitForm: " + e);
        })
    }

    return (
<div>
    <SideBar/>
    <TopBar/>
    <div class="content container pt-3 px-3">
        <div class="d-flex justify-content-start mb-2 px-5">
            <Button onClick={() => navigate("/hr/my-forms")}><i class="bi bi-file-earmark-text"></i> My Forms</Button>
        </div>
        <div class="card text-dark bg-light" style={{marginLeft: '20%', marginRight: '20%'}}>
        <form class="w-75 mt-3 mx-auto">
            <div class="row my-2">
                <div class="col-12">
                    <label className="my-2">Type of Form</label>
                    <Select class="form-select" name="type" value={{label: inputs.typeName, value: inputs.type}} onChange={handleFormTypeChange}
                    options={options}/>
                </div>
            </div>
            <div class="row my-2">
                <div class="col-12">
                    <label className="my-2">Approver</label>
                    <Select class="form-select" name="approver" value={{label: inputs.approverName, value: inputs.approver}} onChange={handleApproverChange}
                    options={approvers}/>
                </div>
            </div>
            <div class="row my-2" hidden={disableDates}>
                <div class="col-6">
                    <label className="my-2">Start Date</label>
                    <input type="date" class="form-control" id="" name="startDate" value={inputs.startDate} onChange={handleInputChange}
                    placeholder="--/--/----" required/>
                </div>
                <div class="col-6">
                    <label className="my-2">End Date</label>
                    <input type="date" class="form-control" id="" name="endDate" value={inputs.endDate} onChange={handleInputChange}
                    placeholder="--/--/----" required/>
                </div>
            </div>
            
            <div class="row my-2">
                <div class="col-12">
                    <label htmlFor="newPrjDescription" className="my-2">Reason</label>
                    <textarea type="text" class="form-control" rows='3' name="reason" value={inputs.description} onChange={handleInputChange}
                    placeholder="Reason" required/>
                </div>
            </div>
            {showErr ? <span class="text-danger">Please select approver</span> : ""}
        </form>
        <div class="row mt-4 mb-4 d-flex justify-content-center">
            <button class="btn btn-primary" style={{width:"70%", height:"45px"}} onClick={handleSubmit}>Submit</button>
        </div>
        </div>
    </div>

</div>
    )
}

export default SubmitForm;