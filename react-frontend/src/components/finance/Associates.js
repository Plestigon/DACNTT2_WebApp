import React from "react";
import { useEffect, useState } from 'react';
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import {success, error, loading, dismiss} from "../../utils/Notify";
import DeleteConfirmModal from "../../utils/DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";

function Associates() {
	const auth = useAuthentication();
	const [associates, setAssociates] = useState([]);

	const [showNewModal, setShowNewModal] = useState(false);
	const [showDeleteModal, setShowDeleteModal] = useState(false);
	const [deleteTarget, setDeleteTarget] = useState({
		id: 0,
		name: ''
	});

	useEffect(() => {
		fetchAssociates();
	}, [])

	function fetchAssociates() {
		const toastId = loading("Loading associates data...");
		fetch("http://localhost:8080/finance/associates" + "?token=" + auth.token, {
			method: "GET"
		})
		.then(result => result.json())
		.then((result) => {
			dismiss(toastId);
			if (result.statusCode === 200) {
				setAssociates(result.data);
				// console.log(result.data);
			}
			else {
				error("Load associates data failed");
			}
		})
		.catch(e => {
			console.log("ERROR_fetchAssociates: " + e);
			dismiss(toastId);
			error("Load associates data failed");
		})
	}

	function deleteBtnClick(e, id, name) {

	}

	function deleteAssociate() {

	}

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				{/* <NewProjectModal show={showNewModal} onHide={() => setShowNewModal(false)} reload={fetchProjectData} /> */}
				<div class="row mb-2 px-5">
					<Button class="btn btn-primary" onClick={() => setShowNewModal(true)}>
						<i class="bi bi-plus-circle me-2"></i>Create New Associate
					</Button>
				</div>
				<div class="card table-card table-responsive">
					<table class="table-clickable table table-hover table-collapsed" id="project-table" style={{ width: '100%' }}>
						<thead class="table-primary">
							<tr>
								<th scope="col">Associate</th>
								<th scope="col">Domain</th>
								<th scope="col">Description</th>
								<th scope="col">Contacts</th>
								<th scope="col" style={{ width: '200px' }}></th>
								<th scope="col" style={{ width: '50px' }}></th>
							</tr>
						</thead>
						<tbody>
							{associates.map(x => (
								<tr key={x.id}>
									<td>{x.name}</td>
									<td>{x.domain}</td>
									<td>{x.description}</td>
									<td>{x.contacts.map(y => (
										<div class="card contacts-card text-truncate">{y.name}</div>
									))}
									</td>
									<td><button class="btn btn-info" onClick={() => window.open('/finance/associates/' + x.id + '/deals', '_blank').focus()}>Go to Deals</button></td>
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
		</div>
	);
}
export default Associates;