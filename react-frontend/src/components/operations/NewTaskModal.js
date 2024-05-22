import React, { useCallback, Component } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import { getDefaultDueDate } from "../../utils/DateHelper";
import { success } from "../../utils/Notify";
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { render } from "@testing-library/react";

function NewTaskModal(props) {
    const [inputs, setInputs] = useState({
        name: '',
        owner: 0,
        ownerName: '',
        dueDate: getDefaultDueDate(),
        description: ''
    });
    const [options, setOptions] = useState([]);
    const [value, setValue] = useState('');

    const editorConfiguration = {
        toolbar: [ 'bold', 'italic' ]
    };

    function handleSelectPrio(event){
        setValue(event.target.value)
    }

    const taskpriority =[
        {label: "1 - Very High", value: 1},
        {label: "2 - High", value: 2},
        {label: "3 - Medium", value: 3},
        {label: "4 - Low", value: 4},

    ]
    // const loadOwners = useCallback(() => {
    //     fetch("http://localhost:8080/operations/employees",{
    //         method:"GET"
    //     })
    //     .then(result=>result.json())
    //     .then((result)=>{
    //         if (result.statusCode === 200) {
    //             var data = [];
    //             result.data.forEach(o => {
    //                 data.push({label: o.name, value: o.id});
    //             });
    //             setOptions(data);
    //             handleInputChange({label: "Select project's owner", value: 0});
    //         }
    //     })
    //     .catch (e => {
    //         console.log("ERROR_handleSubmitProject: " + e);
    //     })
    // }, [])
    
    // useEffect(()=>{
    //     loadOwners();
    // }, [loadOwners])

    // function handleInputChange(e) {
    //     if (e.target) {
    //         const name = e.target.name;
    //         const value = e.target.value;
    //         setInputs(prevState => ({...prevState, [name]: value}));
    //     }
    //     else {
    //         setInputs(prevState => ({...prevState, 'owner': e.value, 'ownerName': e.label}));
    //     }
    // }

    // function handleSelectChange(json) {
    //     setInputs(prevState => ({...prevState, }))
    // }

    // function handleSubmitProject(e) {
    //     e.preventDefault();
    //     // console.log(inputs);
    //     fetch("http://localhost:8080/operations/project",{
    //         method:"POST",
    //         body: JSON.stringify({
    //             'name': inputs.name,
    //             'ownerId': inputs.owner,
    //             'memberIds': [],
    //             'dueDate': inputs.dueDate,
    //             'description': inputs.description
    //         }),
    //         headers: { "Content-type": "application/json; charset=UTF-8" }
    //     })
    //     .then((result)=>{
    //         if (result.ok) {
    //             success("New project added successfully!");
    //             props.onHide();
    //             props.reload();
    //         }   
    //         console.log(result);
    //     })
    //     .catch (e => {
    //         console.log("ERROR_handleSubmitProject: " + e);
    //     })
    // }
    return (
        <Modal show={props.show} onHide={props.onHide} reload={props.reload} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Create New Task</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
            <form class="newPrjForm mx-3">
                <div class="row my-2">
                    <div class="col-12">
                        <label htmlFor="newPrjName">Task name</label>
                        <input type="text" class="form-control" id="newPrjName" name="name"  placeholder="New project's name" required/> 
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-6">
                        <label htmlFor="newPrjOwner">Assgined To</label>
                        <Select class="form-select" id="newPrjOwner" name="owner" 
                        options={options}/>
                    </div>
                    <div class="col-6">
                        <label htmlFor="newPrjDueDate">Priority</label>
                        <select type="select" class="form-select" id="TaskPriority" onChange={handleSelectPrio} >
                        {taskpriority.map(option => (
                            <option value={option.value}>{option.label}</option>
                        ))}/</select>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-12">
                        <label htmlFor="newTaskDescription">Task description</label>
                        <CKEditor
                            editor={ ClassicEditor }
                            data={inputs.description} 
                            onReady={ editor => {
                                console.log( 'Editor is ready to use!', editor );
                            } }
                            onChange={ ( event, editor ) => {
                                const data =  editor.getData();
                                console.log( {event, editor, data} );
                            } }
                            onBlur={ ( event, editor ) => {
                                console.log( 'Blur.', editor );
                            } }
                            onFocus={ ( event, editor ) => {
                                console.log( 'Focus.', editor );
                            } }
                        />
                    </div>
                </div>
            </form>
             </Modal.Body>
             <Modal.Footer className="d-flex justify-content-center">
                 <Button className="btn-primary w-25" >Submit</Button>
                 <Button className="btn-secondary w-25" onClick={props.onHide}>Cancel</Button>
             </Modal.Footer>
         </Modal>
    );
}

export default NewTaskModal