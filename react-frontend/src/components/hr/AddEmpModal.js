import React from "react";
import { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import Button from 'react-bootstrap/Button';
import Notify, {error, success} from "../../utils/Notify";
import { useAuthentication } from "../system/Authentication";

function AddEmpModal(props) {
    const auth = useAuthentication();
    const [inputs, setInputs] = useState({
        name: '',
        email: '',
        department: 0,
        departmentName: '',
        role: 0,
        roleName: '',
        password: ''
    });
    const [depOptions, setDepOptions] = useState([]);
    const [roleOptions, setRoleOptions] = useState([]);

    useEffect(() => {
        function loadEmployeeRoles() {
            fetch(process.env.REACT_APP_API_URI + "/hr/employees/roles",{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                setRoleOptions(result);
            })
            .catch (e => {
                console.log("ERROR_loadEmployeeRoles: " + e);
            })
        }
        loadEmployeeRoles();
    }, [])

    useEffect(() => {
        function loadDepartments() {
            fetch(process.env.REACT_APP_API_URI + "/hr/employees/departments",{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    setDepOptions(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadDepartments: " + e);
            })
        }
        loadDepartments();
    }, [])

    function handleInputChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        setInputs(prevState => ({...prevState, [name]: value}));
    }

    function handleDepSelect(e) {
        setInputs(prevState => ({...prevState, 'department': e.value, 'departmentName': e.label}));
    }

    function handleRoleSelect(e) {
        setInputs(prevState => ({...prevState, 'role': e.value, 'roleName': e.label}));
    }

    function handleSubmit() {
        fetch(process.env.REACT_APP_API_URI + "/hr/employees?token=" + auth.token,{
            method:"POST",
            body: JSON.stringify({
                'name': inputs.name,
                'email': inputs.email,
                'departmentId': inputs.department,
                'departmentName': inputs.departmentName,
                'role': inputs.role,
                'roleDetail': inputs.roleName,
                'password': inputs.password
            }),
            headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "ngrok-skip-browser-warning" : "true" 
            }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("Employee added");
                props.reload();
                props.onHide();
            }
            else {
                error("Some errors occurred");
            }
        })
        .catch (e => {
            console.log("ERROR_addEmployee: " + e);
        })
    }

    return (
        <Modal show={props.show} onHide={props.onHide} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Add Employee</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
            <form class="mx-3">
                <div class="row my-3 d-flex justify-content-center">
                    <div class="col-6">
                        <label class="form-label">Name:</label>
                        <input type="text" class="form-control" name="name" onChange={handleInputChange}/>
                    </div>
                    <div class="col-6">
                        <label class="form-label">Email:</label>
                        <input type="text" class="form-control" name="email" onChange={handleInputChange}/>
                    </div>
                </div>
                <div class="row my-3 d-flex justify-content-center">
                    <div class="col-6">
                        <label class="form-label">Department:</label>
                        <Select name="department" value={{label: inputs.departmentName, value: inputs.department}} onChange={handleDepSelect} options={depOptions}/>
                    </div>
                    <div class="col-6">
                        <label class="form-label">Role:</label>
                        <Select name="role" value={{label: inputs.roleName, value: inputs.role}} onChange={handleRoleSelect} options={roleOptions}/>
                    </div>
                </div>
                <div class="row my-3 d-flex">
                    <div class="col-6">
                        <label class="form-label">Password:</label>
                        <input type="text" class="form-control" name="password" onChange={handleInputChange}/>
                    </div>
                </div>
            </form>
            </Modal.Body>
            <Modal.Footer>
                <div className="w-100 d-flex justify-content-center">
                    <Button className="w-75 btn btn-lg" onClick={handleSubmit}>Submit</Button>
                </div>
            </Modal.Footer>
        </Modal>
    )
}

export default AddEmpModal;