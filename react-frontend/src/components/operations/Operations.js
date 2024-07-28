import React, { useCallback } from "react";
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
	const [page, setPage] = useState(1);
	const [pages, setPages] = useState([]);
	const [search, setSearch] = useState('');
	const [status, setStatus] = useState(0);
	const [statuses, setStatuses] = useState([]);
	const [totalCount, setTotalCount] = useState(0);

	useEffect(() => {
		document.title = 'All Projects - TDTU EMS';
	}, []);

	const fetchProjectData = useCallback(() => {
		const toastId = loading("Loading projects...");
		let uri = process.env.REACT_APP_API_URI + "/operations/projects";
		uri += "?page=" + page;
		uri += "&search=" + search;
		uri += "&status=" + (status === 0 ? '' : status);
		uri += "&token=" + auth.token;
		fetch(uri, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				dismiss(toastId);
				if (result.statusCode === 200) {
					setProjects(result.data);
					setTotalCount(result.totalCount);
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
	}, [auth.token, page, search, status])

	useEffect(() => {
		fetchProjectData();
	}, [fetchProjectData])

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

	const fetchStatusCount = useCallback(() => {
		fetch(process.env.REACT_APP_API_URI + "/operations/projects/status-count?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				if (result.statusCode === 200) {
					setStatuses(result.data);
				}
			})
			.catch(e => {
				console.log("ERROR_getStatusCount: " + e);
			})
	}, [])

	useEffect(() => {
		fetchStatusCount();
	}, [fetchStatusCount])


	function projectDetails(id) {
		var win = window.open('/operations/projects/' + id, '_blank');
		win.focus();
	}

	function handleSearch(e) {
		if (e.type === 'keydown') {
			if (e.key === 'Enter') {
				fetchProjectData();
			}
			return;
		}
		fetchProjectData();
	}

	const handleChangeStatus = (value) => {
		setStatus(value);
		setPage(1);
	}

	const generatePages = useCallback(() => {
		var list = [];
		for (let i = 1; i <= Math.ceil(totalCount / 10); i++) {
			list.push(i);
		}
		setPages(list);
		//console.log(list);
	}, [totalCount])

	useEffect(() => {
		generatePages();
	}, [generatePages])

	const handlePageChange = (doChange, value) => {
		if (!doChange) return;
		if (page + value < 1) return;
		if (page + value > Math.ceil(totalCount / 10)) return;
		setPage(page + value);
	}

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				<NewProjectModal show={newPrjModalShow} onHide={() => setNewPrjModalShow(false)} reload={fetchProjectData} />
				<div class="d-flex row mb-2 px-5">
					<ul class="nav nav-tabs w-auto">
						{/* <li class="nav-item">
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
						</li> */}
						{statuses.map(x => (
							x.status === status ?
								(<li class="nav-item">
									<button class="nav-link active">{x.statusName}<div class={`tab-status-${x.status}`}>{x.count}</div></button>
								</li>)
								:
								(<li class="nav-item">
									<button class="nav-link" onClick={() => handleChangeStatus(x.status)}>{x.statusName}<div class={`tab-status-${x.status}`}>{x.count}</div></button>
								</li>)
						))}
					</ul>
				</div>
				<div class="d-flex row mb-2 px-5 align-items-center">
					<input type="text" class="form-control" style={{ width: '400px', height: '35px' }} placeholder="Search project"
						value={search} onChange={e => setSearch(e.target.value)} onKeyDown={handleSearch} />
					<button type="button" class="btn btn-primary btn-search" onClick={handleSearch}><i class="bi bi-search"></i></button>
					<button class="btn btn-primary ms-auto me-3" style={{ width: '150px', height: '35px' }} onClick={() => setNewPrjModalShow(true)}>
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
								<th scope="col" style={{ width: '20%' }}>Description</th>
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
					<div class="page-nums">
						<div className={page <= 1 ? "disabled" : ""} onClick={() => handlePageChange(page <= 1 ? false : true, -1)}>{`< Previous`}</div>
						{pages.map((x) => (
							<div key={x} class={x === page ? "active" : ""} onClick={() => setPage(x)}>{x}</div>
						))}
						<div className={page >= Math.ceil(totalCount / 10) ? "disabled" : ""} onClick={() => handlePageChange(page >= Math.ceil(totalCount / 10) ? false : true, 1)}>{`Next >`}</div>
						{/* <div class="active">1</div><div>2</div><div>3</div> */}
					</div>
					<div>Showing items {totalCount > 0 ? (page - 1) * 10 + 1 : 0} ~ {Math.min(page * 10, totalCount)} / {totalCount}</div>
				</div>
			</div>
			<DeleteConfirmModal show={showDeleteModal} onHide={() => { setShowDeleteModal(false); setDeleteTarget({ 'id': 0, 'name': '' }) }}
				message={"Delete project \"" + deleteTarget.name + "\"?"} delete={deleteProject} />
		</div>
	);
};

export default Operations;