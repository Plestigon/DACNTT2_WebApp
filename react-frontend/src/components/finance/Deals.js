import React, { useCallback } from "react";
import { useEffect, useState } from 'react';
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import { success, error, loading, dismiss } from "../../utils/Notify";
import DeleteConfirmModal from "../../utils/DeleteConfirmModal";
import { Button } from "react-bootstrap";
import { useAuthentication } from "../system/Authentication";
import { createSearchParams, useNavigate, useParams, useSearchParams } from "react-router-dom";
import { dateFormat } from "../../utils/DateHelper";
import NewDealModal from "./NewDealModal";
import Select from 'react-select';
import Pagination from "../../utils/Pagination";

function Deals() {
	const auth = useAuthentication();
	const navigate = useNavigate();
	const [searchParams] = useSearchParams();
	var filter = searchParams.get("associate");
	const [deals, setDeals] = useState([]);
	const [associate, setAssociate] = useState({});

	const [showNewModal, setShowNewModal] = useState(false);
	const [showDeleteModal, setShowDeleteModal] = useState(false);
	const [deleteTarget, setDeleteTarget] = useState({
		id: 0,
		name: ''
	});
	const [associateOptions, setAssociateOptions] = useState([]);
	const [associateId, setAssociateId] = useState(0);
	const [associateName, setAssociateName] = useState('- Filter by associate -');
	const [page, setPage] = useState(1);
	const [totalCount, setTotalCount] = useState(0);

	useEffect(() => {
		document.title = 'Deals - TDTU EMS';
	}, []);

	useEffect(() => {
		if (filter === null || filter === 0) {
			setAssociateId(0);
			setAssociateName('- Filter by associate -');
		}
	}, [filter])

	useEffect(() => {
		fetchAssociate();
	}, [])

	const fetchDeals = useCallback(() => {
		const toastId = loading("Loading deals...");
		fetch(process.env.REACT_APP_API_URI + "/finance/deals?associate=" + (filter !== null && filter !== "0" ? filter : "") 
				+ "&page=" + page + "&token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				dismiss(toastId);
				if (result.statusCode === 200) {
					setDeals(result.data);
					setTotalCount(result.totalCount);
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
	}, [auth.token, page, filter]);

	useEffect(() => {
		fetchDeals();
	}, [fetchDeals])

	function fetchAssociate() {
		if (searchParams.get("associate") === null) return;
		fetch(process.env.REACT_APP_API_URI + "/finance/associates/" + filter + "?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				// console.log(result);
				if (result.statusCode === 200) {
					setAssociate(result.data);
				}
			})
			.catch(e => {
				console.log("ERROR_fetchAssociate: " + e);
			})
	}

	useEffect(() => {
		function fetchAssociates() {
			fetch(process.env.REACT_APP_API_URI + "/finance/associates?token=" + auth.token, {
				method: "GET",
				headers: { "ngrok-skip-browser-warning": "true" }
			})
				.then(result => result.json())
				.then((result) => {
					// console.log(result);
					if (result.statusCode === 200) {
						var data = [];
						if (filter !== null && filter !== "0") {
							data.push({ label: '- All -', value: 0})
						}
						result.data.forEach(o => {
							if (o.id === parseInt(filter)) {
								setAssociateId(o.id);
								setAssociateName(o.name);
							}
							else {
								data.push({ label: o.name, value: o.id });
							}
						});
						setAssociateOptions(data);
					}
				})
				.catch(e => {
					console.log("ERROR_fetchAssociates: " + e);
				})
		}
		fetchAssociates();
	}, []);

	function handleAssociateChange(e) {
		navigate({
			pathname: "/finance/deals",
			search: createSearchParams({ associate: e.value }).toString()
		});
		window.location.reload();
	}

	// function deleteBtnClick(e, id, name) {

	// }

	// function deleteDeal() {

	// }

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				<NewDealModal show={showNewModal} onHide={() => setShowNewModal(false)} reload={fetchDeals} associate={associate} token={auth.token} />
				<div class="row mb-2 px-5" style={{ fontWeight: 'bold', fontSize: '26px' }}>
					{filter !== null && filter !== "0" ? <div>Viewing deals of associate "{associate.name}"</div> : "Viewing all deals"}
				</div>
				<div class="row d-flex mb-2 px-5">
					<div class="col-6">
						<Select class="form-select" value={{ label: associateName, value: associateId }} options={associateOptions} onChange={handleAssociateChange} />
					</div>
					<div class="col-6">
						<Button className="btn btn-primary w-100" onClick={() => setShowNewModal(true)}>
							<i class="bi bi-plus-circle me-2"></i>Create New Deal
						</Button>
					</div>
				</div>
				<div class="card table-card table-responsive" style={{ height: 'calc(100vh - 205px)' }}>
					<table class="table-clickable table table-hover table-collapsed" id="project-table" style={{ width: '100%' }}>
						<thead class="table-primary" style={{zIndex: "0"}}>
							<tr>
								<th scope="col">Title</th>
								<th scope="col">Stage</th>
								<th scope="col">Contact</th>
								<th scope="col">Value</th>
								<th scope="col">Create Date</th>
								<th scope="col">Close Date</th>
								<th scope="col" style={{ width: '200px' }}></th>
								{/* <th scope="col" style={{ width: '50px' }}></th> */}
							</tr>
						</thead>
						<tbody>
							{deals.map(x => (
								<tr key={x.id}>
									<td>{x.title}</td>
									<td>{x.stageName}</td>
									<td>{x.contactName}</td>
									<td>{x.dealValue.toLocaleString()} VNĐ</td>
									<td>{dateFormat(x.createDate)}</td>
									<td>{x.closeDate ? dateFormat(x.closeDate) : '-/-'}</td>
									<td><button type="button" class="btn btn-info" onClick={() => window.open('/finance/deals/' + x.id + '/stages', '_blank').focus()}>See Details</button></td>
									{/* <td><button type="button" class="btn btn-danger bi bi-trash delete-prj-btn"
										onClick={(e) => deleteBtnClick(e, x.id, x.name)}></button></td> */}
								</tr>
							))}
						</tbody>
					</table>
				</div>
				<Pagination page={page} setPage={setPage} totalCount={totalCount} />
			</div>
			{/* <DeleteConfirmModal show={showDeleteModal} onHide={() => { setShowDeleteModal(false); setDeleteTarget({ 'id': 0, 'name': '' }) }}
				message={"Delete project \"" + deleteTarget.name + "\"?"} delete={deleteDeal} /> */}
		</div>
	);
}
export default Deals;