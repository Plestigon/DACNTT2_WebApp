import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import React, { useEffect, useState } from "react";
// import '.../css/home.css'
import SideBar from '../SideBar';
import { Link } from "react-router-dom";
import Nav from 'react-bootstrap/Nav';
import TopBar from "../TopBar";
import { useAuthentication } from '../system/Authentication';

function HRSummary() {
	const auth = useAuthentication();
	const [data, setData] = useState({});

	useEffect(() => {
		function loadSummary() {
			fetch(process.env.REACT_APP_API_URI + "/hr/summary/" + auth.id + "?token=" + auth.token, {
				method: "GET",
				headers: { "ngrok-skip-browser-warning": "true" }
			})
				.then(result => result.json())
				.then((result) => {
					if (result.statusCode === 200) {
						setData(result.data);
					}
					else {
						console.log(result.message);
					}
				})
				.catch(e => {
					console.log("ERROR_loadSummary: " + e);
				})
		}
		loadSummary();
	}, [])

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content">
				<h3 align="center" style={{ marginBottom: '125px' }}> Summmary of Human Resources Information</h3>
				<Row className="justify-content-center">
					{auth.checkRole(auth.role, "Human Resources")
					?<div class="flip-card" style={{ maxWidth: '18rem', boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)' }}>
						<div class="flip-card-inner">
							<div class="flip-card-front bg-success">
								<img src={require('../../assets/Team.png')} class="card-img-top" alt="..." style={{ width: '10rem' }} />
								<h3>Employees</h3>
							</div>
							<div class="flip-card-back bg-info">
								<h3>Manage {data.employeeCount} employees</h3>
								<a href="/hr/employees" class="btn btn-primary stretched-link border-0">Go to employee list</a>
							</div>
						</div>
					</div>
					:""}
					<div class="flip-card" style={{ maxWidth: '18rem', boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)' }}>
						<div class="flip-card-inner">
							<div class="flip-card-front bg-info">
								<img src={require('../../assets/Form.png')} class="card-img-top" alt="..." style={{ width: '10rem' }} />
								<h3>My Forms</h3>
							</div>
							<div class="flip-card-back bg-info">
								<h3>Manage my {data.formCount} forms</h3>
								<a href="/hr/my-forms" class="btn btn-primary stretched-link bg-info border-0">Go to my forms</a>
							</div>
						</div>
					</div>
					<div class="flip-card" style={{ maxWidth: '18rem', boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)' }}>
						<div class="flip-card-inner">
							<div class="flip-card-front bg-info">
								<img src={require('../../assets/Form.png')} class="card-img-top" alt="..." style={{ width: '10rem' }} />
								<h3>Submit Form</h3>
							</div>
							<div class="flip-card-back">
								<a href="/hr/submit-form" class="btn btn-primary stretched-link bg-info border-0">Write A Form</a>
							</div>
						</div>
					</div>
					<div class="flip-card" style={{ maxWidth: '18rem', boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)' }}>
						<div class="flip-card-inner">
							<div class="flip-card-front bg-info">
								<img src={require('../../assets/Contracts.png')} class="card-img-top" alt="..." style={{ width: '10rem' }} />
								<h3>My Contracts</h3>
							</div>
							<div class="flip-card-back bg-info">
								<h3>Manage my {data.contractCount} contracts</h3>
								<a href="#" class="btn btn-primary stretched-link bg-info border-0">Go to my contracts</a>
							</div>
						</div>
					</div>
				</Row>
			</div>
		</div>
	);
}

export default HRSummary;