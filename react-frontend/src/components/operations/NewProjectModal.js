import React, { useCallback } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import { getDefaultDueDate } from "../../utils/DateHelper";

function NewProjectModal(props) {
    const [inputs, setInputs] = useState({
        name: '',
        owner: 0,
        ownerName: '',
        dueDate: getDefaultDueDate(),
        desc: ''
    });
    const [options, setOptions] = useState([]);

    const loadOwners = useCallback(() => {
        fetch("http://localhost:8080/operations/employees",{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            var data = [];
            result.forEach(o => {
                data.push({value: o.id, label: o.name});
            });
            setOptions(data);
            handleInputChange({label: "Select project's owner", value: 0});
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
        console.log(inputs);
        fetch("http://localhost:8080/operations/project",{
            method:"POST",
            body: JSON.stringify({
                'name': inputs.name,
                'ownerId': inputs.owner,
                'memberIds': [],
                'dueDate': inputs.dueDate,
                'description': inputs.desc
            }),
            headers: { "Content-type": "application/json; charset=UTF-8" },
        })
        .then((result)=>{
            if (result.ok) {
                alert("New project added successfully!");
                window.location.reload(false);
            }
            console.log(result);
        })
        .catch (e => {
            console.log("ERROR_handleSubmitProject: " + e);
        })
    }

    return (
        <Modal {...props} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Create New Project</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
            <form class="newPrjForm mx-3">
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
                        <textarea type="text" class="form-control" rows='3' id="newPrjDescription" name="desc" value={inputs.desc} onChange={handleInputChange}
                        placeholder="New project's description" required/>
                    </div>
                </div>
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