import React, { useCallback, useEffect, useState } from "react";
import '../css/home.css';
import "../css/charts.css";
import { useSearchParams } from "react-router-dom";
import TopBar from "./TopBar";
import SideBar from "./SideBar";
import { Card, CardBody, CardTitle } from "react-bootstrap";
import { PieChart } from '@mui/x-charts/PieChart';
import { LineChart } from '@mui/x-charts/LineChart';
import { info } from "../utils/Notify";
import { useAuthentication } from "./system/Authentication";

function Home() {
	const auth = useAuthentication();
	const [searchParams] = useSearchParams();
	const [projectData, setProjectData] = useState([]);
	const [employeeRoleData, setEmployeeRoleData] = useState([]);
	const [dealData, setDealData] = useState([]);

	useEffect(() => {
		document.title = 'Home - TDTU EMS';
	});

	useEffect(() => {
		if (searchParams.get("loggedIn") === "true") {
			info("Welcome, " + auth.name);
		}
	}, [searchParams])

	const fetchProjectChartData = useCallback(() => {
		fetch(process.env.REACT_APP_API_URI + "/operations/projects/chart-data?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				//console.log(result.data);
				if (result.statusCode === 200) {
					setProjectData(result.data);
				}
				else {
					console.log(result.message);
				}
			})
			.catch(e => {
				console.log("ERROR_fetchProjectChartData: " + e);
			})
	}, [auth.token])

	const fetchEmployeeRoleData = useCallback(() => {
		fetch(process.env.REACT_APP_API_URI + "/hr/employees/chart-data?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				console.log(result.data);
				if (result.statusCode === 200) {
					setEmployeeRoleData(result.data);
				}
				else {
					console.log(result.message);
				}
			})
			.catch(e => {
				console.log("ERROR_fetchEmployeeRoleData: " + e);
			})
	}, [auth.token])

	const fetchDealData = useCallback(() => {
		fetch(process.env.REACT_APP_API_URI + "/finance/deals/chart-data?token=" + auth.token, {
			method: "GET",
			headers: { "ngrok-skip-browser-warning": "true" }
		})
			.then(result => result.json())
			.then((result) => {
				console.log(result.data);
				if (result.statusCode === 200) {
					setDealData(result.data);
				}
				else {
					console.log(result.message);
				}
			})
			.catch(e => {
				console.log("ERROR_fetchDealData: " + e);
			})
	}, [auth.token])

	useEffect(() => {
		fetchProjectChartData();
		fetchEmployeeRoleData();
		fetchDealData();
	}, [fetchProjectChartData, fetchEmployeeRoleData, fetchDealData])

	// return (
	// <div>
	//     <NavigationBar/>
	//     <h1 style={{textAlign:'center', fontSize: '60px'}}>Your go-to work platform</h1>
	//     <h1 style={{textAlign:'center', fontSize: '40px'}}>Run all your work on one platform </h1>

	//     {/*Cards*/}
	//     <div class="container">
	//         <div class="card-group d-flex justify-content-center">
	//             <div class="flip-card" style={{maxWidth: '18rem'}}>
	//                 <div class="flip-card-inner">
	//                     <div class="flip-card-front bg-success">
	//                         <img src={require('../assets/Stocks.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
	//                         <h3>Finance</h3>
	//                     </div>
	//                     <div class="flip-card-back bg-success">
	//                             <h5 class="card-title">Finance</h5>
	//                             <p class="card-text">Manage the company's finance.</p>
	//                         <a href="/finance/associates" class="btn btn-primary stretched-link bg-success border-0">Go to finance</a>
	//                     </div>
	//                 </div>
	//             </div>
	//             <div class="flip-card" style={{maxWidth: '18rem'}}>
	//                 <div class="flip-card-inner">
	//                     <div class="flip-card-front bg-info">
	//                         <img src={require('../assets/Cog.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
	//                         <h3>Operations</h3>
	//                     </div>
	//                     <div class="flip-card-back bg-info">
	//                         <h5 class="card-title">Operations</h5>
	//                         <p class="card-text">Manage the company's Operations.</p>
	//                         <a href="/operations/projects" class="btn btn-primary stretched-link bg-info border-0">Go to Operations</a>
	//                     </div>
	//                 </div>
	//             </div>
	//             <div class="flip-card" style={{maxWidth: '18rem'}}>
	//                 <div class="flip-card-inner">
	//                     <div class="flip-card-front bg-danger">
	//                         <img src={require('../assets/HR.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
	//                         <h3>Human Resources</h3>
	//                     </div>
	//                     <div class="flip-card-back bg-danger">
	//                         <h5 class="card-title">Human Resources</h5>
	//                         <p class="card-text">Manage the company's human resources.</p>
	//                         <a href="/hr/summary" class="btn btn-primary stretched-link bg-danger border-0">Go to Human Resources</a>
	//                     </div>
	//                 </div>
	//             </div>
	//             {/* <div class="flip-card" style={{maxWidth: '18rem'}}>
	//                 <div class="flip-card-inner">
	//                     <div class="flip-card-front bg-warning">
	//                         <img src={require('../assets/Calendar.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
	//                         <h3>Schedule</h3>
	//                     </div>
	//                     <div class="flip-card-back bg-warning">
	//                         <h5 class="card-title">Schedule</h5>
	//                         <p class="card-text">Manage the company's schedule.</p>
	//                         <a href="#" class="btn btn-primary stretched-link bg-warning border-0">Go to Schedule</a>
	//                     </div>
	//                 </div>
	//             </div> */}
	//         </div>
	//     </div>
	// </div>
	// );

	return (
		<div>
			<SideBar />
			<TopBar />
			<div class="content container">
				<div className="row">
					{/* Projects */}
					<div className="col-6 chart-container">
						<Card className="chart mx-2 my-2">
							<CardTitle className="mt-3">
								<div>Projects</div>
								<a href="/operations/projects">See all</a>
							</CardTitle>
							<CardBody>
								<PieChart
									series={[
										{
											data: projectData,
											innerRadius: 30,
											outerRadius: 100,
											paddingAngle: 5,
											cornerRadius: 5,
											startAngle: 0,
											endAngle: 360,
											cx: 150,
											cy: 100,
										}
									]}
								/>
							</CardBody>
						</Card>
					</div>
					{/* Employees */}
					<div className="col-6 chart-container">
						<Card className="chart mx-2 my-2">
							<CardTitle className="mt-3 text-center">
								<div>Employees</div>
								<a href="/hr/employees">See all</a>
							</CardTitle>
							<CardBody>
								<PieChart
									series={[
										{
											data: employeeRoleData,
											innerRadius: 30,
											outerRadius: 100,
											paddingAngle: 5,
											cornerRadius: 5,
											startAngle: 0,
											endAngle: 360,
											cx: 150,
											cy: 100,
										}
									]}
								/>
							</CardBody>
						</Card>
					</div>
				</div>
				<div className="row mt-3">
					{/* Deals */}
					<div className="col-6 chart-container">
						<Card className="chart mx-2 my-2">
							<CardTitle className="mt-3 text-center">
								<div>Deals</div>
								<a href="/finance/deals">See all</a></CardTitle>
							<CardBody style={{height: "90%"}}>
								<LineChart
									xAxis={[{ curve: "linear", data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12] }]}
									series={[
										{
											data: dealData
										},
									]}
									width={550}
									height={250}
									margin={{ left: 90, right: 10, top: 10, bottom: 40 }}
								/>
							</CardBody>
						</Card>
					</div>
				</div>
			</div>
		</div>
	);
};

export default Home;