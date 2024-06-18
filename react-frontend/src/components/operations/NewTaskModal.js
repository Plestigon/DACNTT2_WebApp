import React, { useCallback, Component } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import { success } from "../../utils/Notify";
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { getDefaultDueDate } from "../../utils/DateHelper";
import { useAuthentication } from "../system/Authentication";

function NewTaskModal(props) {
    const auth = useAuthentication();
    const [inputs, setInputs] = useState({
        name: '',
        assignee: 0,
        assigneeName: '',
        priority: 0,
        priorityName: '',
        dueDate: getDefaultDueDate(),
        description: ''
    });
    const [assigneeOptions, setAssigneeOptions] = useState([]);
    const [priorityOptions, setPriorityOptions] = useState([]);

    const editorConfiguration = {
        toolbar: [ 'bold', 'italic' ]
    };

    useEffect(() => {
        function loadAssignees() {
            var a = [];
            props.members.forEach(e => {
                a.push({label: e.employeeName, value: e.employeeId})
            });
            setAssigneeOptions(a);
        }
        loadAssignees();
    }, [props.members])

    useEffect(() => {
        function loadPriorities() {
            fetch(process.env.REACT_APP_API_URI + "/operations/tasks/priorities",{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    setPriorityOptions(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadPriorities: " + e);
            })
        }
        loadPriorities();
    }, [])

    function handleInputChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        setInputs(prevState => ({...prevState, [name]: value}));
    }

    function handleAssigneeChange(e) {
        setInputs(prevState => ({...prevState, 'assignee': e.value, 'assigneeName': e.label}));
    }

    function handlePriorityChange(e) {
        setInputs(prevState => ({...prevState, 'priority': e.value, 'priorityName': e.label}));
    }

    function handleDescriptionChange(data) {
        setInputs(prevState => ({...prevState, 'description': data}));
    }

    function handleSubmit() {
        console.log(inputs);
        fetch(process.env.REACT_APP_API_URI + "/operations/tasks" + "?token=" + auth.token,{
            method:"POST",
            body: JSON.stringify({
                name: inputs.name,
                projectId: props.projectId,
                assigneeId: inputs.assignee,
                priority: inputs.priority,
                dueDate: inputs.dueDate,
                description: inputs.description
            }),
            headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "ngrok-skip-browser-warning" : "true" 
            }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("New task added successfully");
                props.onHide();
                props.reload();
            }
        })
        .catch (e => {
            console.log("ERROR_handleSubmitTask: " + e);
        })
    }

    return (
        <Modal show={props.show} onHide={props.onHide} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Create New Task</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body style={{height: '500px', overflow: 'scroll'}}>
            <form class="mx-3 h-100">
                <div class="row my-2">
                    <div class="col-12">
                        <label>Task name</label>
                        <input type="text" class="form-control" name="name" placeholder="New task's title" onChange={handleInputChange} required/> 
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-6">
                        <label>Assgined To</label>
                        <Select class="form-select" name="assignee" value={{label: inputs.assigneeName, value: inputs.assignee}}
                        options={assigneeOptions} onChange={handleAssigneeChange}/>
                    </div>
                    <div class="col-6">
                        <label>Priority</label>
                        <Select class="form-select" name="priority" value={{label: inputs.priorityName, value: inputs.priority}}
                        options={priorityOptions} onChange={handlePriorityChange}/>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-6">
                        <label>Due Date</label>
                        <input type="datetime-local" class="form-control" name="dueDate" value={inputs.dueDate} onChange={handleInputChange}/>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-12">
                        <label>Task description</label>
                        <CKEditor
                            editor={ ClassicEditor }
                            data={inputs.description} 
                            onReady={ editor => {
                                //console.log( 'Editor is ready to use!', editor );
                            } }
                            onChange={ ( event, editor ) => {
                                const data =  editor.getData();
                                //console.log( {event, editor, data} );
                                handleDescriptionChange(data);
                            } }
                            onBlur={ ( event, editor ) => {
                                //console.log( 'Blur.', editor );
                            } }
                            onFocus={ ( event, editor ) => {
                                //console.log( 'Focus.', editor );
                            } }
                        />
                    </div>
                </div>
            </form>
             </Modal.Body>
             <Modal.Footer className="d-flex justify-content-center">
                 <Button className="btn-primary w-25" onClick={handleSubmit}>Submit</Button>
                 <Button className="btn-secondary w-25" onClick={props.onHide}>Cancel</Button>
             </Modal.Footer>
         </Modal>
    );
}

export default NewTaskModal