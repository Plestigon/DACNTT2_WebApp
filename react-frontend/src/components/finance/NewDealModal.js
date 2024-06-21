import React, { useEffect } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Select from 'react-select';
import Modal from 'react-bootstrap/Modal';
import { success } from "../../utils/Notify";

function NewDealModal(props) {
	const [inputs, setInputs] = useState({
		title: '',
		contact: 0,
		contactName: '- Select contact -',
		dealValue: 0
	});

	const [options, setOptions] = useState([]);
	const [showErr, setShowErr] = useState(false);

	useEffect(() => {
		if (props.associate.contacts) {
			var data = [];
			props.associate.contacts.forEach(o => {
				data.push({label: o.name, value: o.id});
			});
			setOptions(data);
		}
	}, [props.associate.contacts])

	function handleInputChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		setInputs(prevState => ({ ...prevState, [name]: value }));
	}

	function handleDealValueChange(e) {
		var value = e.target.value;
		value = value.replaceAll(',', '');
		value = value.replaceAll('.', '');
		value = value.replaceAll(/\s/g, '');
		value = value.replace(/^0+/, '');
		if (inputs.dealValue === '0' && value.slice(-1) === '0') {
			value = value.slice(0, -1);
		}
		if (value === '') {
			value = '0';
		}
		if (isNaN(value) || isNaN(parseFloat(value))) {
			return;
		}
		setInputs(prevState => ({ ...prevState, 'dealValue': value.replace(/\B(?=(\d{3})+(?!\d))/g, ",") }));
	}

	function handleContactChange(e) {
		setInputs(prevState => ({ ...prevState, 'contact': e.value, 'contactName': e.label}));
		setShowErr(false);
	}

	function handleSubmit() {
		if (inputs.contact === 0 || inputs.title === '') {
			setShowErr(true);
		}
		// console.log(inputs);
		fetch(process.env.REACT_APP_API_URI + "/finance/deals?token=" + props.token, {
			method: "POST",
			body: JSON.stringify({
				'title': inputs.title,
				'associate': props.associate.id,
				'contact': inputs.contact,
				'dealValue': inputs.dealValue.replaceAll(',', '')
			}),
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
					<p class="h4 text-center">Create New Deal for Associate "{props.associate.name}"</p>
				</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<form class="mx-3">
					<div class="row my-2">
						<div class="col-12">
							<label>Title:</label>
							<input type="text" class="form-control" name="title" value={inputs.name} onChange={handleInputChange}
								placeholder="Deal title" required />
						</div>
					</div>
					<div class="row my-2">
						<div class="col-6">
							<label>Associate:</label>
							<input type="text" class="form-control" value={props.associate.name} onChange={handleInputChange} disabled={true} />
						</div>
						<div class="col-6">
							<label>Contact:</label>
							<Select class="form-select" name="contact" options={options} value={{ label: inputs.contactName, value: inputs.contact }} onChange={handleContactChange} />
						</div>
					</div>
					<div class="row my-2">
						<div class="col-6">
							<label>Deal value:</label>
							<div class="d-flex">
								<input type="text" class="form-control" style={{ width: "85%", textAlign: "end" }} name="dealValue" value={inputs.dealValue} onChange={handleDealValueChange} />
								<span style={{ marginLeft: "10px", marginTop: "5px" }}>VNĐ</span></div>
						</div>
						<div class="col-6">
							{showErr ? <span class="text-danger">Please fill in title and select contact</span> : ""}
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

export default NewDealModal