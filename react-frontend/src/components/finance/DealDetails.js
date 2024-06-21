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

	useEffect(() => {
		fetchDeal();
		fetchDealStageDetails();
	}, [])

	function fetchDeal() {
		fetch(process.env.REACT_APP_API_URI + "/finance/deals/" + params.id + "?token=" + auth.token, {
			method: "GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
		})
		.then(result => result.json())
		.then((result) => {
			if (result.statusCode === 200) {
				setDeal(result.data);
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
            headers: { "ngrok-skip-browser-warning" : "true" }
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
								headers: { "ngrok-skip-browser-warning" : "true" }
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

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				<div class="card ms-5 me-5 mt-3" style={{padding: "50px 10%"}}>
					<div class="row">
						<div class="col-8">
							Title: <div class="form-control inline-block">{deal.title}</div>
						</div>
						<div class="col-4">
							Stage: <div class={"card deal-stage deal-stage-" + deal.stage}>{deal.stageName}</div>
						</div>
					</div>
					<div class="row">
						<div class="col-6">
							Associate: <div class="form-control inline-block">{deal.associateName}</div>
						</div>
						<div class="col-6">
							Contact: <div class="form-control inline-block">{deal.contactName}</div>
						</div>
					</div>
					<div class="row">
						<div class="col-4">
							Deal value: <div class="form-control inline-block">{deal.dealValue}</div>
						</div>
						<div class="col-4">
							Create date: <div class="form-control inline-block">{dateFormat(deal.createDate)}</div>
						</div>
						<div class="col-4">
							Close date: <div class="form-control inline-block">{deal.closeDate ? dateFormat(deal.closeDate) : "-/-"}</div>
						</div>
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
							<div class="col-6">{x.closeDate ? dateFormat(x.closeDate) : "-/-"}</div>
						</div>
						<div class="mt-3 d-flex justify-content-between">
							<p>Notes:</p>
							<button class="btn btn-primary" style={{paddingTop:"0", paddingBottom:"0", height:"30px"}} onClick={() => updateNotes(x.id)}>Update</button>
						</div>
						<textarea class="form-control" rows={3} value={x.notes ? x.notes : ""} onChange={(e) => handleNotesChange(e, x.id)}/>
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