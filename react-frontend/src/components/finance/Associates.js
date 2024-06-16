import React from "react";
import { useEffect, useState } from 'react';
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import { success, error, loading, dismiss } from "../../utils/Notify";
import DeleteConfirmModal from "../../utils/DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";
import AssignContactModal from "./AssignContactModal.js";
import NewAssociateModal from "./NewAssociateModal.js";

function Associates() {
	const auth = useAuthentication();
	const [associates, setAssociates] = useState([]);

	const [showNewModal, setShowNewModal] = useState(false);
	const [showAddContactModal, setShowAddContactModal] = useState(false);
	const [showDeleteModal, setShowDeleteModal] = useState(false);
	const [deleteTarget, setDeleteTarget] = useState({
		id: 0,
		name: ''
	});
	const [addContactTarget, setAddContactTarget] = useState({
		id: 0,
		contacts: []
	});

	useEffect(() => {
		fetchAssociates();
	}, [])

	function fetchAssociates() {
		const toastId = loading("Loading associates...");
		fetch(process.env.REACT_APP_API_URI + "/finance/associates?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				dismiss(toastId);
				if (result.statusCode === 200) {
					setAssociates(result.data);
					console.log(result.data);
				}
				else {
					error("Load associates failed");
				}
			})
			.catch(e => {
				console.log("ERROR_fetchAssociates: " + e);
				dismiss(toastId);
				error("Load associates failed");
			})
	}

	function deleteBtnClick(e, id, name) {

	}

	function deleteAssociate() {

	}

	function addContactBtnClick(id) {
		let contactIds = [];
		associates.forEach(a => {
			if (a.id === id) {
				contactIds = a.contacts.map(c => c.id);
			}
		})
		setAddContactTarget({ 'id': id, 'contacts': contactIds });
		setShowAddContactModal(true);
	}

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				{/* <NewProjectModal show={showNewModal} onHide={() => setShowNewModal(false)} reload={fetchProjectData} /> */}
				<NewAssociateModal show={showNewModal} onHide={() => setShowNewModal(false)} reload={fetchAssociates} token={auth.token}  />
				<div class="row mb-2 px-5">
					<Button class="btn btn-primary" onClick={() => setShowNewModal(true)}>
						<i class="bi bi-plus-circle me-2"></i>Add New Associate
					</Button>
				</div>
				<div class="card table-card table-responsive">
					<table class="table-clickable table table-hover table-collapsed" style={{ width: '100%' }}>
						<thead class="table-primary">
							<tr>
								<th scope="col">Associate</th>
								<th scope="col">Domain</th>
								<th scope="col">Description</th>
								<th scope="col" style={{ width: '360px' }}>Contacts (hover for info)</th>
								<th scope="col" style={{ width: '150px' }}></th>
								<th scope="col" style={{ width: '50px' }}></th>
							</tr>
						</thead>
						<tbody>
							{associates.map(x => (
								<tr key={x.id}>
									<td>{x.name}</td>
									<td>{x.domain}</td>
									<td>{x.description}</td>
									<td class="align-middle" style={{ padding: '10px' }}>
										<div class="d-flex justify-content-between">
											<div>
												{x.contacts.map(y => (
													<div class="card contacts-card text-truncate"
														title={"Name: " + y.name + "\nRole: " + y.role + "\nEmail: " + y.email + "\nPhone number: " + y.phoneNumber}>
														{y.name}
													</div>
												))}
											</div>
											<i class="bi bi-plus-circle h3" title="Add contact" style={{ cursor: "pointer" }} onClick={() => addContactBtnClick(x.id)}></i>
										</div>
									</td>
									{/* <td style={{padding:'10px'}}><button class="btn btn-primary">+ Contact</button></td> */}
									<td style={{ padding: '10px' }}><button class="btn btn-info" onClick={() => window.open('/finance/associates/' + x.id + '/deals', '_blank').focus()}>Go to Deals</button></td>
									<td><button class="btn btn-danger bi bi-trash delete-prj-btn"
										onClick={(e) => deleteBtnClick(e, x.id, x.name)}></button></td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
			</div>
			<DeleteConfirmModal show={showDeleteModal} onHide={() => { setShowDeleteModal(false); setDeleteTarget({ 'id': 0, 'name': '' }) }}
				message={"Delete project \"" + deleteTarget.name + "\"?"} delete={deleteAssociate} />
			<AssignContactModal show={showAddContactModal} onHide={() => setShowAddContactModal(false)}
				associateId={addContactTarget.id} contacts={addContactTarget.contacts} reload={fetchAssociates} token={auth.token} />
		</div>
	);
}
export default Associates;