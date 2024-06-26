import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { success } from "../../utils/Notify";

function NewAssociateModal(props) {
	const [inputs, setInputs] = useState({
		name: '',
		domain: '',
		description: ''
	});
	const [validateError, setValidateError] = useState('');

	function handleInputChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		setInputs(prevState => ({ ...prevState, [name]: value }));
	}

	function handleSubmit() {
		// console.log(inputs);
		if (inputs.name === '') {
			setValidateError("Associate name required"); return;
		}
		// if (inputs.domain === '') {
		// 	setValidateError("Associate domain required"); return;
		// }
		// if (!/[a-zA-Z0-9][a-zA-Z0-9-]{1,61}[a-zA-Z0-9](?:\.[a-zA-Z]{2,})+/.test(inputs.domain)) {
		// 	setValidateError("Invalid domain");
		// 	return;
		// }
		setValidateError('');
		fetch(process.env.REACT_APP_API_URI + "/finance/associates?token=" + props.token, {
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
				success("New associate added successfully!");
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
					<p class="h4 text-center">Create New Associate</p>
				</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<form class="mx-3">
					<div class="row my-2">
						<div class="col-12">
							Name: <input type="text" class="form-control" name="name" value={inputs.name} onChange={handleInputChange}
								placeholder="Associate name" required />
						</div>
					</div>
					<div class="row my-2">
						<div class="col-12">
							Domain: <input type="text" class="form-control" name="domain" value={inputs.domain} onChange={handleInputChange}
								placeholder="Associate domain. Ex: tdtu.edu.vn" required />
						</div>
					</div>
					<div class="row my-2">
						<div class="col-12">
							Description: <textarea rows={3} class="form-control" name="description" value={inputs.description} onChange={handleInputChange}
								placeholder="Description" required />
						</div>
					</div>
					<span class="text-danger">{validateError}</span>
				</form>
			</Modal.Body>
			<Modal.Footer className="d-flex justify-content-center">
				<Button className="btn-primary w-25" onClick={handleSubmit}>Submit</Button>
				<Button className="btn-secondary w-25" onClick={props.onHide}>Cancel</Button>
			</Modal.Footer>
		</Modal>
	);
}

export default NewAssociateModal