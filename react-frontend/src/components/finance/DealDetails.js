import React from "react";
import { useEffect, useState } from 'react';
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import { success, error, loading, dismiss } from "../../utils/Notify";
import { Button } from "react-bootstrap";
import Select from 'react-select';
import { useAuthentication } from "../system/Authentication";
import { useParams } from "react-router-dom";
import { dateFormat } from "../../utils/DateHelper";

function DealDetails() {
	const auth = useAuthentication();
	const params = useParams();
	const [deal, setDeal] = useState({
		// id: 0,
		// title: '',
		// stage: 0,
		// stageName: '',
		// associateName: '',
		// contact: 0,
		// contactName: '',
		// dealValue: 0,
		// createDate: '',
		// closeDate: ''
	});
	const [dealStageDetails, setDealStageDetails] = useState([]);

	const [showNewModal, setShowNewModal] = useState(false);
	const [disableEdit, setDisableEdit] = useState(true);

	useEffect(() => {
		fetchDeal();
		fetchDealStageDetails();
	}, [])

	function fetchDeal() {
		fetch(process.env.REACT_APP_API_URI + "/finance/deals/" + params.id + "?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				if (result.statusCode === 200) {
					setDeal(result.data);
					let dealVal = "" + result.data.dealValue;
					setDeal(prevState => ({ ...prevState, 'dealValue': dealVal.replace(/\B(?=(\d{3})+(?!\d))/g, ",") }));
					// console.log(result.data);
				}
			})
			.catch(e => {
				console.log("ERROR_fetchDeal: " + e);
			})
	}

	function fetchDealStageDetails() {
		const toastId = loading("Loading deal stages...");
		fetch(process.env.REACT_APP_API_URI + "/finance/deals/" + params.id + "/stages" + "?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				dismiss(toastId);
				if (result.statusCode === 200) {
					setDealStageDetails(result.data);
					// console.log(result.data);
				}
				else {
					error("Load deal stages failed");
				}
			})
			.catch(e => {
				console.log("ERROR_fetchDealStageDetails: " + e);
				dismiss(toastId);
				error("Load deal stages failed");
			})
	}

	function handleInputChange(e) {
		const name = e.target.name;
		const value = e.target.value;
		setDeal(prevState => ({ ...prevState, [name]: value }));
	}

	function handleDealValueChange(e) {
		var value = e.target.value;
		value = value.replaceAll(',', '');
		value = value.replaceAll('.', '');
		value = value.replaceAll(/\s/g, '');
		value = value.replace(/^0+/, '');
		if (deal.dealValue === '0' && value.slice(-1) === '0') {
			value = value.slice(0, -1);
		}
		if (value === '') {
			value = '0';
		}
		if (isNaN(value) || isNaN(parseFloat(value))) {
			return;
		}
		setDeal(prevState => ({ ...prevState, 'dealValue': value.replace(/\B(?=(\d{3})+(?!\d))/g, ",") }));
	}

	function handleSubmitEdit() {
		fetch(process.env.REACT_APP_API_URI + "/finance/deals?token=" + auth.token, {
			method: "PUT",
			body: JSON.stringify({
				'id': deal.id,
				'title': deal.title,
				'dealValue': deal.dealValue.replaceAll(',', '')
			}),
			headers: {
				"Content-type": "application/json; charset=UTF-8",
				"ngrok-skip-browser-warning": "true"
			}
		})
		.then(result => result.json())
		.then((result) => {
			if (result.statusCode === 200) {
				success("Updated");
				setDisableEdit(true);
				fetchDeal();
			}
			else {
				console.log(result.message);
			}
		})
		.catch(e => {
			console.log("ERROR_handleSubmit: " + e);
		})
	}

	function handleNotesChange(e, id) {
		var data = [...dealStageDetails];
		data = data.map(x => {
			if (x.id === id) {
				x.notes = e.target.value;
			}
			return x;
		})
		setDealStageDetails(data);
	}

	function updateNotes(id) {
		dealStageDetails.forEach(x => {
			if (x.id === id) {
				fetch(process.env.REACT_APP_API_URI + "/finance/deals/" + id + "/notes?value=" + x.notes + "&token=" + auth.token, {
					method: "PUT",
					headers: { "ngrok-skip-browser-warning": "true" }
				})
					.then(result => result.json())
					.then((result) => {
						if (result.statusCode === 200) {
							success("Notes updated");
						}
						else {
							console.log(result.message);
						}
					})
					.catch(e => {
						console.log("ERROR_fetchDeal: " + e);
					})
				return;
			}
		})
	}

	function updateStage(stage) {
		// console.log(deal.id + " " + stage);
		fetch(process.env.REACT_APP_API_URI + "/finance/deals/" + deal.id + "/stage?value=" + stage + "&token=" + auth.token, {
			method: "PUT",
			headers: {
				"Content-type": "application/json; charset=UTF-8",
				"ngrok-skip-browser-warning": "true"
			}
		})
		.then(result => result.json())
		.then((result) => {
			if (result.statusCode === 200) {
				success("Stage Updated");
				setDisableEdit(true);
				fetchDeal();
				fetchDealStageDetails();
			}
			else {
				console.log(result.message);
			}
		})
		.catch(e => {
			console.log("ERROR_updateStage: " + e);
		})
	}

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				<div class="card ms-5 me-5" style={{ paddingLeft: "10%", paddingRight: "10%", paddingTop: "10px", paddingBottom: "20px" }}>
					<Button className="ms-auto" style={{ width: '50px', height: '50px', marginTop: "0", padding: '0px' }}
						title="Edit deal" onClick={() => setDisableEdit(false)}><i class="bi bi-pencil-square"></i></Button>
					<div class="row">
						<div class="col-8">
							Title: <input type="text" class="form-control inline-block" name="title" value={deal.title} onChange={handleInputChange} disabled={disableEdit} />
						</div>
						<div class="col-4">
							Stage: <div class={"card deal-stage deal-stage-" + deal.stage}>{deal.stageName}</div>
						</div>
					</div>
					<div class="row">
						<div class="col-6">
							Associate: <input type="text" class="form-control inline-block" value={deal.associateName} disabled />
						</div>
						<div class="col-6">
							Contact: <div class="form-control inline-block">{deal.contactName}</div>
						</div>
					</div>
					<div class="row">
						<div class="col-3">
							Deal value: <input type="text" class="form-control inline-block" value={deal.dealValue} onChange={handleDealValueChange} disabled={disableEdit} />
						</div>
						<div class="col-1" style={{marginTop:"30px"}}>VNĐ</div>
						<div class="col-4">
							Create date: <div class="form-control inline-block">{dateFormat(deal.createDate)}</div>
						</div>
						<div class="col-4">
							Close date: <div class="form-control inline-block">{deal.closeDate ? dateFormat(deal.closeDate) : "-/-"}</div>
						</div>
					</div>
					<div class="row d-flex mt-3 justify-content-end">
						<button class="btn btn-primary mx-1" style={{width:"200px"}} onClick={handleSubmitEdit} hidden={disableEdit}>Submit Edit</button>
						<button class="btn btn-secondary mx-1" style={{width:"200px"}} onClick={() => {setDisableEdit(true); fetchDeal();}} hidden={disableEdit}>Cancel</button>
					</div>
				</div>

				<div class="row mt-3 ms-1">
					{dealStageDetails.map(x => (
						<div class="card deal-stage-display" key={x.id}>
							<div class={"card deal-stage deal-stage-" + x.stage}>{x.stageName}</div>
							<div class="row mt-3">
								<div class="col-6 text-end">Start date:</div>
								<div class="col-6">{x.startDate ? dateFormat(x.startDate) : "-/-"}</div>
							</div>
							<div class="row mt-3">
								<div class="col-6 text-end">End date:</div>
								<div class="col-6">{x.endDate ? dateFormat(x.endDate) : "-/-"}</div>
							</div>
							<div class="mt-3 d-flex justify-content-between">
								<p>Notes:</p>
								<button class="btn btn-primary" style={{ paddingTop: "0", paddingBottom: "0", height: "30px" }} onClick={() => updateNotes(x.id)} disabled={!disableEdit}>Update</button>
							</div>
							<textarea class="form-control" rows={3} value={x.notes ? x.notes : ""} onChange={(e) => handleNotesChange(e, x.id)} />
							<button class="btn btn-primary mt-2" onClick={() => updateStage(x.stage)}>Go to this stage</button>
						</div>
					))}
					{/* <div class="card deal-stage-display">
						<div class="card deal-stage deal-stage-2">Proposal</div>
					</div>
					<div class="card deal-stage-display">
						<div class="card deal-stage deal-stage-3">Negotiate</div>
					</div>
					<div class="card deal-stage-display">
						<div class="card deal-stage deal-stage-4">Ended</div>
					</div> */}
				</div>
			</div>
		</div>
	);
}
export default DealDetails;