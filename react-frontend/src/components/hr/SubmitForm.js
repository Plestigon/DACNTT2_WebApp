import React from "react";
import NavigationBar from '../NavigationBar';
import { useEffect, useState } from 'react';
import SideBar from "../SideBar";
import TopBar from "../TopBar";
import Select from 'react-select';
import { Button } from "react-bootstrap";

function SubmitForm() {
    const [inputs, setInputs] = useState({
        name: '',
        owner: 0,
        ownerName: '',
        dueDate: '',
        description: ''
    });
    const [options, setOptions] = useState([
        {label: 'Paid Leave', value: 1},
        {label: 'Unpaid Leave', value: 2},
        {label: 'Resignation', value: 3}
    ]);

    function handleInputChange(e) {
        if (e.target) {
            const name = e.target.name;
            const value = e.target.value;
            setInputs(prevState => ({...prevState, [name]: value}));
        }
        else {
            setInputs(prevState => ({...prevState, 'owner': e.value, 'ownerName': e.label}));
        }
    }

    return (
<div>
    <SideBar/>
    <TopBar/>
    <div class="content container pt-3 px-3">
        <div class="card text-dark bg-light mx-5 my-5">
        <form class="newPrjForm w-75 mt-3 mx-auto">
            <div class="row my-2">
                <div class="col-12">
                    <label htmlFor="newPrjOwner" className="my-2">Type of Form</label>
                    <Select class="form-select" id="newPrjOwner" name="owner" value={{label: inputs.ownerName, value: inputs.owner}} onChange={handleInputChange}
                    options={options}/>
                </div>
            </div>
            <div class="row my-2">
                <div class="col-6">
                    <label htmlFor="newPrjName" className="my-2">Start Date</label>
                    <input type="datetime-local" class="form-control" id="" name="" value="" onChange={handleInputChange}
                    placeholder="New project's name" required/>
                </div>
                <div class="col-6">
                    <label htmlFor="newPrjName" className="my-2">End Date</label>
                    <input type="datetime-local" class="form-control" id="" name="" value="" onChange={handleInputChange}
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