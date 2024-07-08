import React from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useEffect, useState } from 'react';
import NewProjectModal from "./NewProjectModal";
import { dateTimeFormat } from "../../utils/DateHelper";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import '../../css/table.css';
import '../../css/tabs.css';
import { success, error, loading, dismiss } from "../../utils/Notify";
import DeleteConfirmModal from "../../utils/DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";


function Operations() {
	const auth = useAuthentication();
	const [projects, setProjects] = useState([]);
	const [newPrjModalShow, setNewPrjModalShow] = useState(false);
	const [showDeleteModal, setShowDeleteModal] = useState(false);
	const [deleteTarget, setDeleteTarget] = useState({
		id: 0,
		name: ''
	});

	useEffect(() => {
		document.title = 'All Projects - TDTU EMS';
	}, []);

	useEffect(() => {
		fetchProjectData();
	}, [])

	function fetchProjectData() {
		const toastId = loading("Loading projects...");
		fetch(process.env.REACT_APP_API_URI + "/operations/projects" + "?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				dismiss(toastId);
				if (result.statusCode === 200) {
					setProjects(result.data);
				}
				else {
					error("Load projects failed");
					console.log(result.message);
				}
			})
			.catch(e => {
				console.log("ERROR_fetchProjectData: " + e);
				dismiss(toastId);
				error("Load projects failed");
			})
	}

	function deleteBtnClick(e, id, name) {
		e.stopPropagation();
		setDeleteTarget({ 'id': id, 'name': name });
		setShowDeleteModal(true);
	}

	function deleteProject() {
		setShowDeleteModal(false);
		if (deleteTarget.id === null || deleteTarget.id <= 0) { return; }
		fetch(process.env.REACT_APP_API_URI + "/operations/projects/" + deleteTarget.id + "?token=" + auth.token, {
			method: "DELETE",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				if (result.statusCode === 200) {
					success("Project deleted");
					fetchProjectData();
				}
			})
			.catch(e => {
				console.log("ERROR_deleteProject: " + e);
			})
		setDeleteTarget({ 'id': 0, 'name': '' });
	}

	function projectDetails(id) {
		var win = window.open('/operations/projects/' + id, '_blank');
		win.focus();
	}

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				<NewProjectModal show={newPrjModalShow} onHide={() => setNewPrjModalShow(false)} reload={fetchProjectData} />
				<div class="d-flex row mb-2 px-5">
					<ul class="nav nav-tabs w-auto">
						<li class="nav-item">
							<button class="nav-link active">Active<div>10</div></button>
						</li>
						<li class="nav-item">
							<button class="nav-link">Active<div>10</div></button>
						</li>
						<li class="nav-item">
							<button class="nav-link">Active<div>10</div></button>
						</li>
						<li class="nav-item">
							<button class="nav-link">Active<div>10</div></button>
						</li>
					</ul>
				</div>
				<div class="d-flex row mb-2 px-5 align-items-center">
					<input type="text" class="form-control" style={{width:'400px', height:'35px'}} placeholder="Search project"/>
					<button type="button" class="btn btn-primary btn-search"><i class="bi bi-search"></i></button>
					<button class="btn btn-primary ms-auto me-3" style={{width:'150px', height:'35px'}} onClick={() => setNewPrjModalShow(true)}>
						<i class="bi bi-plus-circle me-2"></i>New Project
					</button>
				</div>
				<div class="card table-card table-responsive">
					<table class="table-clickable table table-hover">
						<thead class="table-primary">
							<tr>
								<th scope="col">Project</th>
								<th scope="col">Owner</th>
								<th scope="col">Status</th>
								<th scope="col">Create date</th>
								<th scope="col">Due date</th>
								<th scope="col">Description</th>
								<th scope="col" style={{ width: '50px' }}></th>
							</tr>
						</thead>
						<tbody>
							{projects.map(p => (
								<tr key={p.id} title="See Project's details" style={{ cursor: "pointer" }} onClick={() => projectDetails(p.id)}>
									<td>{p.name}</td>
									<td>{p.ownerName}</td>
									<td><div class={"card status-card project-status-" + p.status}>{p.statusName}</div></td>
									<td>{dateTimeFormat(p.createDate)}</td>
									<td>{dateTimeFormat(p.dueDate)}</td>
									<td>{p.description}</td>
									<td><button type="button" class="btn btn-danger bi bi-trash delete-prj-btn"
										onClick={(e) => deleteBtnClick(e, p.id, p.name)}></button></td>
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<div class="d-flex row table-page">
					<div class="page-nums">Page <div class="active">1</div><div>2</div><div>3</div></div>
					<div>Showing 10 / 200 items</div>
				</div>
			</div>
			<DeleteConfirmModal show={showDeleteModal} onHide={() => { setShowDeleteModal(false); setDeleteTarget({ 'id': 0, 'name': '' }) }}
				message={"Delete project \"" + deleteTarget.name + "\"?"} delete={deleteProject} />
		</div>
	);
};

export default Operations;