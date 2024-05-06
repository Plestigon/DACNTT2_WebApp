import React from "react";
import { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';

function AddMemberModal(props) {
    const [data, setData] = useState({
        memberId: 0,
        name: 'Select new member'
    });
    const [options, setOptions] = useState([]);

    useEffect(() => {
        function loadMembersToAdd() {
            if (props.members.length > 0) {
                let memberIds = props.members.map(m => m.id);
                let query = '?ids=' + memberIds.join(',');
                fetch("http://localhost:8080/operations/employees/to-add" + query,{
                    method:"GET"
                })
                .then(result=>result.json())
                .then((result)=>{
                    let newMem = [];
                    result.forEach(m => {
                        newMem.push({label: m.name + " (" + m.email + ")", value: m.id});
                    });
                    setOptions(newMem);
                })
                .catch (e => {
                    console.log("ERROR_loadMembers: " + e);
                })
            }
        }
        loadMembersToAdd();
    }, [props])

    function handleInputChange(e) {
        //setData(prevState => ({...prevState, 'memberId': e.value, 'name': e.label}));
        if (e.value > 0) {
            console.log("add " + e.value + " to " + props.projectid);
            fetch("http://localhost:8080/operations/project/" + props.projectid + "/member?memberId=" + e.value,{
                method:"POST"
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    console.log("New member added successfully!");
                    props.reload();
                    props.close();
                }
            })
            .catch (e => {
                console.log("ERROR_addMember: " + e);
            })
        }
    }

    return (
        <Modal {...props} size="md" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Select member to add</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div class="row my-1 d-flex justify-content-center">
                    <Select name="memberId" value={{label: data.name, value: data.memberId}} onChange={handleInputChange} options={options}/>
                </div>
            </Modal.Body>
        </Modal>
    )
}

export default AddMemberModal;