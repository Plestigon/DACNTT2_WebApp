import React from "react";
import { useEffect, useState } from 'react';
import SideBar from "../SideBar";
import TopBar from "../TopBar";
import Select from 'react-select';
import { Button } from "react-bootstrap";
import Notify, { info, error, success } from "../../utils/Notify";
import { getDefaultStartDate, handleDate, getDefaultContractExpireDate } from "../../utils/DateHelper";
import { useNavigate } from "react-router-dom";
import { useAuthentication } from "../system/Authentication";

function NewContract(props) {
	const auth = useAuthentication();
	const navigate = useNavigate();

	const [inputs, setInputs] = useState({
		type: 0,
		typeName: '- Select Contract Type',
		ownerId: 0,
		ownerName: '- Select Laborer -',
		department: 0,
		departmentName: '- Select Department -',
		timeStart: getDefaultStartDate(),
		timeEnd: getDefaultContractExpireDate()
	});
	const [typeOptions, setTypeOptions] = useState([])
	const [employeeOptions, setEmployeeOptions] = useState([])
	const [departmentOptions, setDepartmentOptions] = useState([])

	const [showValidate, setShowValidate] = useState(false);

	useEffect(() => {
		document.title = 'New Contract - TDTU EMS';
	}, []);
	
	useEffect(() => {
		if (auth.roleList?.length > 0 && !auth.checkRole(auth.role, "Human Resources")) {
			navigate("/forbidden");
		}
	}, [auth.roleList])

	useEffect(() => {
		function loadContractTypes() {
			fetch(process.env.REACT_APP_API_URI + "/hr/contracts/types", {
				method: "GET",
				headers: { "ngrok-skip-browser-warning": "true" }
			})
				.then(result => result.json())
				.then((result) => {
					setTypeOptions(result);
				})
				.catch(e => {
					console.log("ERROR_loadContractTypes: " + e);
				})
		}
		loadContractTypes();
	}, []);

	useEffect(() => {
		function loadLaborers() {
			fetch(process.env.REACT_APP_API_URI + "/hr/employees?token=" + auth.token, {
				method: "GET",
				headers: { "ngrok-skip-browser-warning": "true" }
			})
				.then(result => result.json())
				.then((result) => {
					if (result.statusCode === 200) {
						let emps = [];
						result.data.forEach(e => {
							emps.push({ label: e.name + " (" + e.email + ")", value: e.id });
						});
						setEmployeeOptions(emps);
					}
					else {
						console.log(result.message);
					}
				})
				.catch(e => {
					console.log("ERROR_loadLaborers: " + e);
				})
		}
		loadLaborers();
	}, []);

	useEffect(() => {
		function loadDepartments() {
			fetch(process.env.REACT_APP_API_URI + "/hr/employees/departments", {
				method: "GET",
				headers: { "ngrok-skip-browser-warning": "true" }
			})
				.then(result => result.json())
				.then((result) => {
					if (result.statusCode === 200) {
						setDepartmentOptions(result.data);
					}
				})
				.catch(e => {
					console.log("ERROR_loadDepartments: " + e);
				})
		}
		loadDepartments();
	}, [])

	function handleInputChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		if (name === 'timeStart') {
			var startDate = new Date(value);
			var endDate = new Date(inputs.timeEnd);
			var tomorrow = new Date();
			tomorrow.setDate(tomorrow.getDate() + 1);
			if (endDate <= startDate) {
				endDate = new Date(startDate);
				endDate.setDate(endDate.getDate() + 1);
				setInputs(prevState => ({ ...prevState, 'endDate': handleDate(endDate) }));
			}
		}
		if (name === 'timeEnd') {
			var startDate = new Date(inputs.timeStart);
			var endDate = new Date(value);
			if (endDate <= startDate) {
				error("Expire date must be after Effective date");
				return;
			}
		}
		setInputs(prevState => ({ ...prevState, [name]: value }));
	}

	function handleTypeChange(e) {
		setInputs(prevState => ({ ...prevState, 'type': e.value, 'typeName': e.label }));
		setShowValidate(false);
	}

	function handleOwnerChange(e) {
		setInputs(prevState => ({ ...prevState, 'ownerId': e.value, 'ownerName': e.label }));
		setShowValidate(false);
	}

	function handleDepartmentChange(e) {
		setInputs(prevState => ({ ...prevState, 'department': e.value, 'departmentName': e.label }));
		setShowValidate(false);
	}

	function handleSubmit() {
		// console.log(inputs);
		if (inputs.type === 0 || inputs.ownerId === 0 || inputs.department === 0) {
			setShowValidate(true);
			return;
		}
		fetch(process.env.REACT_APP_API_URI + "/hr/contracts" + "?token=" + auth.token, {
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
					success("Contract created successfully!");
					setInputs({
						type: 0,
						typeName: '- Select Contract Type',
						ownerId: 0,
						ownerName: '- Select Laborer -',
						department: 0,
						departmentName: '- Select Department -',
						timeStart: getDefaultStartDate(),
						timeEnd: getDefaultContractExpireDate()
					});
				}
				else {
					error(result.message);
				}
			})
			.catch(e => {
				console.log("ERROR_handleSubmit: " + e);
			})
	}

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container pt-3 px-3">
				<div class="d-flex justify-content-start mb-2 px-5">
					<Button onClick={() => navigate("/hr/contracts")}><i class="bi bi-file-earmark-text"></i> Contracts</Button>
				</div>
				<div class="card text-dark bg-light" style={{ marginLeft: '20%', marginRight: '20%' }}>
					<form class="w-75 mt-3 mx-auto">
						<div class="row my-2">
							<div class="col-6">
								<label>Contract Type</label>
								<Select class="form-select" name="type" options={typeOptions} value={{ label: inputs.typeName, value: inputs.type }} onChange={handleTypeChange} />
							</div>
							<div class="col-6">
								<label>Department</label>
								<Select class="form-select" name="department" options={departmentOptions} value={{ label: inputs.departmentName, value: inputs.department }} onChange={handleDepartmentChange} />
							</div>
						</div>
						<div class="row my-2">
							<div class="col-12">
								<label>Laborer</label>
								<Select class="form-select" name="ownerId" options={employeeOptions} value={{ label: inputs.ownerName, value: inputs.ownerId }} onChange={handleOwnerChange} />
							</div>
						</div>
						<div class="row my-2">
							<div class="col-6">
								<label>Effective Date</label>
								<input type="date" class="form-control" name="timeStart" value={inputs.timeStart} onChange={handleInputChange} />
							</div>
							<div class="col-6">
								<label>Expire Date</label>
								<input type="date" class="form-control" name="timeEnd" value={inputs.timeEnd} onChange={handleInputChange} />
							</div>
						</div>
						{showValidate ? <span class="text-danger" >Please select all required fields</span> : ""}
					</form>
					<div class="row mt-3 mb-5 d-flex justify-content-center">
						<Button style={{ width: '200px', height: '50px' }} onClick={handleSubmit}>Submit</Button>
					</div>
				</div>
			</div>
		</div>
	);
}

export default NewContract