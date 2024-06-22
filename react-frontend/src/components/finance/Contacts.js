import React from "react";
import { useEffect, useState } from 'react';
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import { success, error, loading, dismiss } from "../../utils/Notify";
import DeleteConfirmModal from "../../utils/DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";
import NewContactModal from "./NewContactModal";

function Contacts() {
	const auth = useAuthentication();
	const [contacts, setContacts] = useState([]);

	const [showNewModal, setShowNewModal] = useState(false);
	const [showDeleteModal, setShowDeleteModal] = useState(false);
	const [deleteTarget, setDeleteTarget] = useState({
		id: 0,
		name: ''
	});

	useEffect(() => {
		fetchContacts();
	}, [])

	function fetchContacts() {
		const toastId = loading("Loading contacts...");
		fetch(process.env.REACT_APP_API_URI + "/finance/contacts?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				dismiss(toastId);
				if (result.statusCode === 200) {
					setContacts(result.data);
					console.log(result.data);
				}
				else {
					error("Load contacts failed");
				}
			})
			.catch(e => {
				console.log("ERROR_fetchContacts: " + e);
				dismiss(toastId);
				error("Load contacts failed");
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
				<NewContactModal show={showNewModal} onHide={() => setShowNewModal(false)} reload={fetchContacts} token={auth.token} />
				<div class="row mb-2 px-5">
					<Button class="btn btn-primary" onClick={() => setShowNewModal(true)}>
						<i class="bi bi-plus-circle me-2"></i>Add New Contact
					</Button>
				</div>
				<div class="card table-card table-responsive">
					<table class="table-clickable table table-hover table-collapsed" style={{ width: '100%' }}>
						<thead class="table-primary">
							<tr>
								<th scope="col">Name</th>
								<th scope="col">Role</th>
								<th scope="col">Email</th>
								<th scope="col">Phone number</th>
								{/* <th scope="col" style={{ width: '50px' }}></th> */}
							</tr>
						</thead>
						<tbody>
							{contacts.map(x => (
								<tr key={x.id}>
									<td>{x.name}</td>
									<td>{x.role}</td>
									<td>{x.email}</td>
									<td>{x.phoneNumber}</td>
									{/* <td><button class="btn btn-danger bi bi-trash delete-prj-btn"
										onClick={(e) => deleteBtnClick(e, x.id, x.name)}></button></td> */}
								</tr>
							))}
						</tbody>
					</table>
				</div>
			</div>
			<DeleteConfirmModal show={showDeleteModal} onHide={() => { setShowDeleteModal(false); setDeleteTarget({ 'id': 0, 'name': '' }) }}
				message={"Delete contact \"" + deleteTarget.name + "\"?"} delete={deleteAssociate} />
		</div>
	);
}
export default Contacts;