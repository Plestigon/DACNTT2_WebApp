import React, { useCallback } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import { getDefaultDueDate } from "../../utils/DateHelper";
import { success, error } from "../../utils/Notify";
import { useAuthentication } from "../system/Authentication";

function NewProjectModal(props) {
    const auth = useAuthentication();
    const [inputs, setInputs] = useState({
        name: '',
        owner: 0,
        ownerName: '- Select project owner -',
        dueDate: getDefaultDueDate(),
        description: ''
    });
    const [options, setOptions] = useState([]);
    const [validateError, setValidateError] = useState('');

    const loadOwners = useCallback(() => {
        fetch(process.env.REACT_APP_API_URI + "/operations/employees" + "?token=" + auth.token,{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                var data = [];
                result.data.forEach(o => {
                    data.push({label: o.name, value: o.id});
                });
                setOptions(data);
                handleInputChange({label: "- Select project's owner -", value: 0});
            }
        })
        .catch (e => {
            console.log("ERROR_handleSubmitProject: " + e);
        })
    }, [])
    
    useEffect(()=>{
        loadOwners();
    }, [loadOwners])

    function handleInputChange(e) {
        if (e.target) {
            const name = e.target.name;
            const value = e.target.value;
            if (name === 'dueDate') {
                var newDate = new Date(value);
                var today = new Date();
                if (newDate < today) {
                    error("Invalid due date");
                    return;
                }
            }
            setInputs(prevState => ({...prevState, [name]: value}));
        }
        else {
            setInputs(prevState => ({...prevState, 'owner': e.value, 'ownerName': e.label}));
        }
    }

    // function handleSelectChange(json) {
    //     setInputs(prevState => ({...prevState, }))
    // }

    function handleSubmitProject(e) {
        e.preventDefault();
        // console.log(inputs);
        if (inputs.name === '') {
            setValidateError('Project name is required'); return;
        }
        if (inputs.owner === 0) {
            setValidateError('Please select project owner'); return;
        }
        setValidateError('');
        fetch(process.env.REACT_APP_API_URI + "/operations/projects" + "?token=" + auth.token,{
            method:"POST",
            body: JSON.stringify({
                'name': inputs.name,
                'ownerId': inputs.owner,
                'memberIds': [],
                'dueDate': inputs.dueDate,
                'description': inputs.description
            }),
            headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "ngrok-skip-browser-warning" : "true" 
            }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("New project added successfully!");
                setInputs({
                    name: '',
                    owner: 0,
                    ownerName: '- Select project owner -',
                    dueDate: getDefaultDueDate(),
                    description: ''
                })
                props.onHide();
                props.reload();
            }   
            // console.log(result);
        })
        .catch (e => {
            console.log("ERROR_handleSubmitProject: " + e);
        })
    }

    return (
        <Modal show={props.show} onHide={props.onHide} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Create New Project</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
            <form class="mx-3">
                <div class="row my-2">
                    <div class="col-12">
                        <label htmlFor="newPrjName">Project name</label>
                        <input type="text" class="form-control" id="newPrjName" name="name" value={inputs.name} onChange={handleInputChange}
                        placeholder="New project's name" required/>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-6">
                        <label htmlFor="newPrjOwner">Owner</label>
                        <Select class="form-select" id="newPrjOwner" name="owner" value={{label: inputs.ownerName, value: inputs.owner}} onChange={handleInputChange}
                        options={options}/>
                    </div>
                    <div class="col-6">
                        <label htmlFor="newPrjDueDate">Due date</label>
                        <input type="datetime-local" class="form-control" id="newPrjDueDate" name="dueDate" value={inputs.dueDate} onChange={handleInputChange}/>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-12">
                        <label htmlFor="newPrjDescription">Project description</label>
                        <textarea type="text" class="form-control" rows='3' id="newPrjDescription" name="description" value={inputs.description} onChange={handleInputChange}
                        placeholder="New project's description" required/>
                    </div>
                </div>
                <span class="text-danger">{validateError}</span>
            </form>
            </Modal.Body>
            <Modal.Footer className="d-flex justify-content-center">
                <Button className="btn-primary w-25" onClick={handleSubmitProject}>Submit</Button>
                <Button className="btn-secondary w-25" onClick={props.onHide}>Cancel</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default NewProjectModal