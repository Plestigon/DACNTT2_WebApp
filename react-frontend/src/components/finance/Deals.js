import React from "react";
import { useEffect, useState } from 'react';
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import {success, error, loading, dismiss} from "../../utils/Notify";
import DeleteConfirmModal from "../../utils/DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";
import { useParams } from "react-router-dom";
import { dateFormat } from "../../utils/DateHelper";

function Deals() {
	const auth = useAuthentication();
	const params = useParams();
	const [deals, setDeals] = useState([]);
	const [associateName, setAssociateName] = useState('');

	const [showNewModal, setShowNewModal] = useState(false);
	const [showDeleteModal, setShowDeleteModal] = useState(false);
	const [deleteTarget, setDeleteTarget] = useState({
		id: 0,
		name: ''
	});

	useEffect(() => {
		fetchDeals();
		fetchAssociate();
	}, [])

	function fetchDeals() {
		const toastId = loading("Loading deals...");
		fetch(process.env.REACT_APP_API_URI + "/finance/associates/" + params.id + "/deals" + "?token=" + auth.token, {
			method: "GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
		})
		.then(result => result.json())
		.then((result) => {
			dismiss(toastId);
			if (result.statusCode === 200) {
				setDeals(result.data);
				// console.log(result.data);
			}
			else {
				error("Load deals failed");
			}
		})
		.catch(e => {
			console.log("ERROR_fetchDeals: " + e);
			dismiss(toastId);
			error("Load deals failed");
		})
	}

	function fetchAssociate() {
		fetch(process.env.REACT_APP_API_URI + "/finance/associates/" + params.id + "?token=" + auth.token, {
			method: "GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
		})
		.then(result => result.json())
		.then((result) => {
			console.log(result);
			if (result.statusCode === 200) {
				setAssociateName(result.data.name);
			}
		})
		.catch(e => {
			console.log("ERROR_fetchAssociate: " + e);
		})
	}

	function deleteBtnClick(e, id, name) {

	}

	function deleteDeal() {

	}

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				{/* <NewProjectModal show={showNewModal} onHide={() => setShowNewModal(false)} reload={fetchProjectData} /> */}
				<div class="row mb-2 px-5" style={{fontWeight: 'bold', fontSize: '26px'}}>Viewing deals of associate "{associateName}"</div>
				<div class="row mb-2 px-5">
					<Button class="btn btn-primary" onClick={() => setShowNewModal(true)}>
						<i class="bi bi-plus-circle me-2"></i>Create New Deal
					</Button>
				</div>
				<div class="card table-card table-responsive" style={{height: 'calc(90vh - 120px)'}}>
					<table class="table-clickable table table-hover table-collapsed" id="project-table" style={{ width: '100%' }}>
						<thead class="table-primary">
							<tr>
								<th scope="col">Title</th>
								<th scope="col">Stage</th>
								<th scope="col">Contact</th>
								<th scope="col">Value</th>
								<th scope="col">Create Date</th>
								<th scope="col">Close Date</th>
								<th scope="col" style={{ width: '200px' }}></th>
								<th scope="col" style={{ width: '50px' }}></th>
							</tr>
						</thead>
						<tbody>
							{deals.map(x => (
								<tr key={x.id}>
									<td>{x.title}</td>
									<td>{x.stageName}</td>
									<td>{x.contactName}</td>
									<td>{x.dealValue}</td>
									<td>{dateFormat(x.createDate)}</td>
									<td>{x.closeDate ? dateFormat(x.closeDate) : '-/-'}</td>
									<td><button type="button" class="btn btn-info" onClick={() => window.open('/finance/deals/' + x.id + '/stages', '_blank').focus()}>See Details</button></td>
									<td><button type="button" class="btn btn-danger bi bi-trash delete-prj-btn"
										onClick={(e) => deleteBtnClick(e, x.id, x.name)}></button></td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
			</div>
			<DeleteConfirmModal show={showDeleteModal} onHide={() => { setShowDeleteModal(false); setDeleteTarget({ 'id': 0, 'name': '' }) }}
				message={"Delete project \"" + deleteTarget.name + "\"?"} delete={deleteDeal} />
		</div>
	);
}
export default Deals;