import React, { useCallback, Component } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import { success } from "../../utils/Notify";
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

function NewPassword(props) {
    return (
        <Modal show={props.show} onHide={props.onHide} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Change Your Password</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body style={{height: '500px', overflow: 'scroll'}}>
            <form class="newPrjForm mx-3 h-100">
                <div class="row my-2">
                    <div class="col-12">
                        <label>Enter your old password</label>
                        <input type="password" class="form-control" name="oldpassword" placeholder="Your old password" required/> 
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-6">
                        <label>Enter your new password</label>
                        <input type="password" class="form-control" name="newpassword" placeholder="Your new password" required/> 
                    </div>
                    <div class="col-6">
                        <label>Verify your old password</label>
                        <input type="password" class="form-control" name="verifypassword" placeholder="Your new password" required/> 
                    </div>
                </div>

            </form>
             </Modal.Body>
             <Modal.Footer className="d-flex justify-content-center">
                 <Button className="btn-primary w-25" onClick={handleSubmit}>Change Password</Button>
                 <Button className="btn-secondary w-25" onClick={props.onHide}>Cancel</Button>
             </Modal.Footer>
         </Modal>
    );
}

export default NewPassword