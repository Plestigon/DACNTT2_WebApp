import React from "react";
import NavigationBar from '../NavigationBar';
import { useEffect, useState } from 'react';
import SideBar from "../SideBar";
import TopBar from "../TopBar";
import Select from 'react-select';
import { Button } from "react-bootstrap";
import Notify, { info, error } from "../../utils/Notify";
import { getDefaultStartDate, getDefaultEndDate } from "../../utils/DateHelper";

function SubmitForm() {
    // var defaultStartDate = new Date();
    // defaultStartDate.setDate(defaultStartDate.getDate() + 1);
    // var defaultEndDate = new Date();
    // defaultEndDate.setDate(defaultEndDate.getDate() + 2);
    // console.log(defaultStartDate);
    // console.log(defaultEndDate);
    const [inputs, setInputs] = useState({
        type: 1,
        typeName: 'Annual Leave',
        createDate: '',
        startDate: getDefaultStartDate(),
        endDate: getDefaultEndDate(),
        reason:''
    });
    const [options, setOptions] = useState([
        // {label: 'Paid Leave', value: 1},
        // {label: 'Unpaid Leave', value: 2},
        // {label: 'Resignation', value: 3}
    ]);

    useEffect(() => {
        function loadFormTypes() {
            fetch("http://localhost:8080/hr/submit-form/types",{
                method:"GET"
            })
            .then(result=>result.json())
            .then((result)=>{
                setOptions(result);
            })
            .catch (e => {
                console.log("ERROR_loadProjectData_statuses: " + e);
            })
        }
        loadFormTypes();
    }, []);

    function handleInputChange(e) {
        if (e.target) {
            const name = e.target.name;
            const value = e.target.value;
            if (name === 'endDate') {
                var startDate = new Date(inputs.startDate);
                var endDate = new Date(value);
                if (endDate <= startDate) {
                    error("End date must be greater than Start date!");
                    return;
                }
            }
            setInputs(prevState => ({...prevState, [name]: value}));
            info(value);
        }
        else {
            setInputs(prevState => ({...prevState, 'type': e.value, 'typeName': e.label}));
        }
    }

    return (
<div>
    <Notify/>
    <SideBar/>
    <TopBar/>
    <div class="content container pt-3 px-3">
        <div class="card text-dark bg-light mt-5" style={{marginLeft: '20%', marginRight: '20%'}}>
        <form class="newPrjForm w-75 mt-3 mx-auto">
            <div class="row my-2">
                <div class="col-12">
                    <label className="my-2">Type of Form</label>
                    <Select class="form-select" name="type" value={{label: inputs.typeName, value: inputs.type}} onChange={handleInputChange}
                    options={options}/>
                </div>
            </div>
            <div class="row my-2">
                <div class="col-6">
                    <label className="my-2">Start Date</label>
                    <input type="date" class="form-control" id="" name="startDate" value={inputs.startDate} onChange={handleInputChange}
                    placeholder="New project's name" required/>
                </div>
                <div class="col-6">
                    <label className="my-2">End Date</label>
                    <input type="date" class="form-control" id="" name="endDate" value={inputs.endDate} onChange={handleInputChange}
                    placeholder="Reasonings" required/>
                </div>
            </div>
            
            <div class="row my-2">
                <div class="col-12">
                    <label htmlFor="newPrjDescription" className="my-2">Reason</label>
                    <textarea type="text" class="form-control" rows='3' id="newPrjDescription" name="description" value={inputs.description} onChange={handleInputChange}
                    placeholder="New project's description" required/>
                </div>
            </div>
        </form>
        <div class="row my-5 d-flex justify-content-center">
            <Button style={{width: '200px', height: '50px'}}>Submit</Button>
        </div>
        </div>
    </div>

</div>
    )
}

export default SubmitForm;