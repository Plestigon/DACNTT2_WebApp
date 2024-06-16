import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { success } from "../../utils/Notify";

function NewContactModal(props) {
	const [inputs, setInputs] = useState({
		name: '',
		role: '',
		email: '',
		phoneNumber: ''
	});

	function handleInputChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		if (name === "phoneNumber" && isNaN(value)) return;
		setInputs(prevState => ({ ...prevState, [name]: value }));
	}

	function handleSubmit() {
		console.log(inputs);
		fetch(process.env.REACT_APP_API_URI + "/finance/contacts?token=" + props.token, {
			method: "POST",
			body: JSON.stringify(inputs),
			headers: {
				"Content-type": "application/json; charset=UTF-8",
				"ngrok-skip-browser-warning": "true"
			}
		})
		.then(result => result.json())
		.then((result) => {
			if (result.statusCode === 200) {
				success("New contact added successfully!");
				props.onHide();
				props.reload();
			}
			console.log(result);
		})
		.catch(e => {
			console.log("ERROR_handleSubmit: " + e);
		})
	}

	return (
		<Modal show={props.show} onHide={props.onHide} reload={props.reload} size="lg" aria-labelledby="contained-modal-title-vcenter" centered>
			<Modal.Header closeButton>
				<Modal.Title class="w-100" id="contained-modal-title-vcenter">
					<p class="h4 text-center">Create New Contact</p>
				</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<form class="mx-3">
					<div class="row my-2">
						<div class="col-12">
							Name: <input type="text" class="form-control" name="name" value={inputs.name} onChange={handleInputChange}
								placeholder="Contact's name" required />
						</div>
					</div>
					<div class="row my-2">
						<div class="col-12">
							Role: <input type="text" class="form-control" name="role" value={inputs.role} onChange={handleInputChange}
								placeholder="Contact's role" required />
						</div>
					</div>
					<div class="row my-2">
						<div class="col-6">
							Email: <input type="text" class="form-control" name="email" value={inputs.email} onChange={handleInputChange}
								placeholder="Contact's email" required />
						</div>
						<div class="col-6">
							Phone number: <input type="text" class="form-control" name="phoneNumber" value={inputs.phoneNumber} onChange={handleInputChange} />
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

export default NewContactModal