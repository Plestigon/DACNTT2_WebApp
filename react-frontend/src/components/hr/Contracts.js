import React, { useCallback } from "react";
import Select from 'react-select'
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Select from 'react-select';
import { getDefaultDueDate } from "../../utils/DateHelper";
import { success } from "../../utils/Notify";

function NewContractssModal(props) {
    const Types = [
        { value: 'Fixed', label: 'Fixed-term labor contract' },
        { value: 'Indefinite', label: 'Indefinite labor contract' },
        { value: 'Seasonal', label: 'Seasonal labor contract' }
      ]
    return (
        <Modal show={props.show} onHide={props.onHide} reload={props.reload} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title class="w-100" id="contained-modal-title-vcenter">
                    <p class="h4 text-center">Create New Contracts</p>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
            <form class="mx-3">
                <div class="row my-2">
                    <div class="col-12">
                        <label htmlFor="newPrjName">Laborer's name</label>
                        <input type="text" class="form-control"  placeholder="Laborer's name" required/>
                    </div>
                </div>
                <div class="row my-2">
                    <div class="col-6">
                        <label>Contract Type</label>
                        <Select Types={options}/>
                    </div>
                    <div class="col-6">
                        <label htmlFor="newPrjDueDate">Effective Date</label>
                        <input type="datetime-local" class="form-control"  value={inputs.dueDate} />
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

export default NewContractssModal