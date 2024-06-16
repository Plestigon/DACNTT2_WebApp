import React from "react";
import { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import Button from 'react-bootstrap/Button';
import Notify, {success} from "../../utils/Notify";

function AssignContactModal(props) {
    const [data, setData] = useState({
        id: 0,
        name: "Select contact"
    });
    const [options, setOptions] = useState([]);

    useEffect(() => {
        function loadContactsToAdd() {
            fetch(process.env.REACT_APP_API_URI + "/finance/contacts?token=" + props.token,{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    let newContacts = [];
                    result.data.forEach(c => {
                        if (!props.contacts.includes(c.id)) {
                            newContacts.push({label: c.name + " (" + c.role + ")", value: c.id});
                        }
                    });
                    setOptions(newContacts);
                }
                else {
                    console.log(result.message);
                }
            })
            .catch (e => {
                console.log("ERROR_loadContactsToAdd: " + e);
            })
        }
        loadContactsToAdd();
    }, [props])

    function handleContactSelect(e) {
        setData(prevState => ({...prevState, 'id': e.value, 'name': e.label}));
    }

    function handleSubmit() {
        if (data.id === 0) return;
        console.log("add " + data.id + " to " + props.associateId);
        fetch(process.env.REACT_APP_API_URI + "/finance/associates/" + props.associateId + "/contacts?contactId=" + data.id + "&token=" + props.token,{
            method:"PUT",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("Contact added");
                setData({
                    id: 0,
                    name: 'Select contact'
                });
                props.reload();
                props.onHide();
            }
            else {
                console.log(result.message);
            }
        })
        .catch (e => {
            console.log("ERROR_handleSubmit: " + e);
        })
    }

    return (
        <Modal show={props.show} onHide={props.onHide} size="md" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Add Contact to this Associate</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div class="my-1 mx-5">
                    <Select name="id" value={{label: data.name, value: data.id}} onChange={handleContactSelect} options={options}/>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <div className="w-100 d-flex justify-content-center">
                    <Button className="w-75" onClick={handleSubmit}>Add</Button>
                </div>
            </Modal.Footer>
        </Modal>
    )
}

export default AssignContactModal;