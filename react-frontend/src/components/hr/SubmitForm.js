import React from "react";
import NavigationBar from '../NavigationBar';
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
        startDate: getDefaultStartDate(),
        endDate: getDefaultEndDate(),
        reason: ''
    });
    const [options, setOptions] = useState([
        // {label: 'Paid Leave', value: 1},
        // {label: 'Unpaid Leave', value: 2},
        // {label: 'Resignation', value: 3}
    ]);
    const [disableDates, setDisableDates] = useState(false);

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

    function handleInputChange(e) {
        if (e.target) {
            const name = e.target.name;
            const value = e.target.value;
            if (name === 'startDate') {
                var startDate = new Date(value);
                var endDate = new Date(inputs.endDate);
                var tomorrow = new Date();
                tomorrow.setDate(tomorrow.getDate() + 1);
                if (startDate < tomorrow) {
                    error("Invalid Start date");
                    return;
                }
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
        else {
            if (e.label === 'Resignation') {
                setDisableDates(true);
            }
            else if (disableDates) {
                setDisableDates(false);
            }
            setInputs(prevState => ({...prevState, 'type': e.value, 'typeName': e.label}));
        }
    }

    function handleSubmit() {
        if (inputs.typeName === 'Resignation') {
            setInputs(prevState => ({...prevState, 'startDate': '', 'endDate': ''}));
        }
        console.log(inputs);
        fetch(process.env.REACT_APP_API_URI + "/hr/submit-form" + "?token=" + auth.token,{
            method:"POST",
            body: JSON.stringify({
                'type': inputs.type,
                'ownerId': auth.id,
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
        <div class="d-flex justify-content-end mb-2 px-5">
            <Button onClick={() => navigate("/hr/my-forms")}><i class="bi bi-file-earmark-text"></i> My Forms</Button>
        </div>
        <div class="card text-dark bg-light" style={{marginLeft: '20%', marginRight: '20%'}}>
        <form class="w-75 mt-3 mx-auto">
            <div class="row my-2">
                <div class="col-12">
                    <label className="my-2">Type of Form</label>
                    <Select class="form-select" name="type" value={{label: inputs.typeName, value: inputs.type}} onChange={handleInputChange}
                    options={options}/>
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
        </form>
        <div class="row my-5 d-flex justify-content-center">
            <Button style={{width: '200px', height: '50px'}} onClick={handleSubmit}>Submit</Button>
        </div>
        </div>
    </div>

</div>
    )
}

export default SubmitForm;