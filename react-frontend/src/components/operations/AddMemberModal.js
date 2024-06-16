import React from "react";
import { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import Button from 'react-bootstrap/Button';
import Notify, {success} from "../../utils/Notify";

function AddMemberModal(props) {
    const [data, setData] = useState({
        memberId: 0,
        name: 'Select new member',
        role: 0,
        roleName: ''
    });
    const [options, setOptions] = useState([]);
    const [roleOptions, setRoleOptions] = useState([]);

    useEffect(() => {
        function loadProjectRoles() {
            fetch(process.env.REACT_APP_API_URI + "/operations/project/roles",{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    setRoleOptions(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadProjectRoles: " + e);
            })
        }

        function loadMembersToAdd() {
            let query = "";
            if (props.members.length > 0) {
                let memberIds = props.members.map(m => m.employeeId);
                query = '?ids=' + memberIds.join(',');
            }
            fetch(process.env.REACT_APP_API_URI + "/operations/employees/to-add" + query,{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    let newMem = [];
                    result.data.forEach(m => {
                        newMem.push({label: m.name + " (" + m.email + ")", value: m.id});
                    });
                    setOptions(newMem);
                }
            })
            .catch (e => {
                console.log("ERROR_loadMembersToAdd: " + e);
            })
        }
        loadProjectRoles();
        loadMembersToAdd();
    }, [props])

    function handleMemberSelect(e) {
        setData(prevState => ({...prevState, 'memberId': e.value, 'name': e.label}));
    }

    function handleRoleSelect(e) {
        setData(prevState => ({...prevState, 'role': e.value, 'roleName': e.label}));
    }

    function handleSubmit() {
        if (data.memberId === 0) return;
        fetch(process.env.REACT_APP_API_URI + "/operations/project/" + props.projectId + "/member?memberId=" + data.memberId + "&role=" + data.role,{
            method:"POST",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("Member added");
                setData({
                    memberId: 0,
                    name: 'Select new member',
                    role: 0,
                    roleName: ''
                });
                props.reload();
                props.onHide();
            }
        })
        .catch (e => {
            console.log("ERROR_addMember: " + e);
        })
    }

    return (
        <Modal show={props.show} onHide={props.onHide} size="md" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Add member to Project</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div class="row my-1 d-flex justify-content-center">
                    <div class="col-8">
                        <label class="form-label">Member:</label>
                        <Select name="memberId" value={{label: data.name, value: data.memberId}} onChange={handleMemberSelect} options={options}/>
                    </div>
                    <div class="col-4">
                        <label class="form-label">Role:</label>
                        <Select name="role" value={{label: data.roleName, value: data.role}} onChange={handleRoleSelect} options={roleOptions}/>
                    </div>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <div className="w-100 d-flex justify-content-center">
                    <Button className="w-75" onClick={handleSubmit}>Submit</Button>
                </div>
            </Modal.Footer>
        </Modal>
    )
}

export default AddMemberModal;