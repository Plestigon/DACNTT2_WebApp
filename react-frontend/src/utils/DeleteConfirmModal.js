import React from "react";
import { useEffect, useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

function DeleteConfirmModal(props) {
    return (
        <Modal show={props.show} onHide={props.onHide}>
            <Modal.Header closeButton>
                <Modal.Title>{props.message}</Modal.Title>
            </Modal.Header>
            <Modal.Body><div className="alert alert-danger text-center">This cannot be undone!</div></Modal.Body>
            <Modal.Footer>
                <Button variant="default" onClick={props.onHide}>
                Cancel
                </Button>
                <Button variant="danger" onClick={props.delete}>
                Delete
                </Button>
            </Modal.Footer>
        </Modal>
    )
}

export default DeleteConfirmModal;