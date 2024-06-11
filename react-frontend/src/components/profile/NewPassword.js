import React, { useCallback, Component } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import { error, success, warning } from "../../utils/Notify";
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

function NewPassword(props) {
    const [oldPass, setOldPass] = useState('');
    const [newPass, setNewPass] = useState('');
    const [confirmPass, setConfirmPass] = useState('');

    const [inputErr, setInputErr] = useState(false);
    const [wrongPassErr, setWrongPassErr] = useState(false);
    const [newPassErr, setNewPassErr] = useState(false);
    const [confirmPassErr, setConfirmPassErr] = useState(false);

    function resetErr() {
        setInputErr(false);
        setWrongPassErr(false);
        setNewPassErr(false);
        setConfirmPassErr(false);
    }

    function handleClose() {
        resetErr();
        props.onHide();
    }

    function handleSubmit() {
        resetErr();
        if (oldPass === '' || newPass === '' || confirmPass === '') {
            setInputErr(true);
            return;
        }
        if (oldPass === newPass) {
            setNewPassErr(true);
            return;
        }
        if (newPass !== confirmPass) {
            setConfirmPassErr(true);
            return;
        }
        
        fetch("http://localhost:8080/auth/change-password" + "?token=" + props.token,{
            method:"PUT",
            body: JSON.stringify({
                'userId': props.userId,
                'oldPass': oldPass,
                'newPass': newPass
            }),
            headers: { "Content-type": "application/json; charset=UTF-8" }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                props.onHide();
                success("Password changed");
            }
            else if (result.statusCode === 400) {
                setWrongPassErr(true);
            }
            else {
                error(result.message);
            }
        })
        .catch (e => {
            console.log("ERROR_handleSubmit: " + e);
        })
    }

    return (
        <Modal show={props.show} onHide={handleClose} size="md" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Change Your Password</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
            <form class="mx-3 h-100">
                <div class="row my-2">
                    <label>Enter old password</label>
                    <input type="password" class="form-control" name="oldPass" onChange={(e) => setOldPass(e.target.value)} required/>
                    {wrongPassErr ? <span class="text-danger">Incorrect password</span> : ''}
                </div>
                <div class="row my-2">
                    <label>Enter new password</label>
                    <input type="password" class="form-control" name="newPass" onChange={(e) => setNewPass(e.target.value)} required/> 
                    {newPassErr ? <span class="text-danger">New password must be different from old password</span> : ''}
                </div>
                <div class="row my-2">
                    <label>Confirm new password</label>
                    <input type="password" class="form-control" name="confirmPass" onChange={(e) => setConfirmPass(e.target.value)} required/>
                    {confirmPassErr ? <span class="text-danger">Password confirmation does not match</span> : ''}
                </div>
                {inputErr ? <span class="text-danger">Please fill all fields</span> : ''}

            </form>
             </Modal.Body>
             <Modal.Footer className="d-flex justify-content-center">
                 <Button className="btn-primary" style={{width:"180px"}} onClick={handleSubmit}>Confirm</Button>
                 <Button className="btn-secondary" style={{width:"180px"}} onClick={handleClose}>Cancel</Button>
             </Modal.Footer>
         </Modal>
    );
}

export default NewPassword