import React, { useEffect } from "react";
import '../css/home.css';
import { Link, useSearchParams } from "react-router-dom";
import NavigationBar from "./NavigationBar";
import { info } from "../utils/Notify";
import { useAuthentication } from "./system/Authentication";
 
function Home() {
	const auth = useAuthentication();
	const [searchParams] = useSearchParams();

    useEffect(()=>{
		if (searchParams.get("loggedIn") === "true") {
			info("Welcome, " + auth.name);
		}
    }, [searchParams])

    return (
    <div>
        <NavigationBar/>
        <h1 style={{textAlign:'center', fontSize: '60px'}}>Your go-to work platform</h1>
        <h1 style={{textAlign:'center', fontSize: '40px'}}>Run all your work on one platform </h1>

        {/*Cards*/}
        <div class="container">
            <div class="card-group">
                <div class="flip-card" style={{maxWidth: '18rem'}}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-success">
                            <img src={require('../assets/Stocks.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
                            <h3>Finance</h3>
                        </div>
                        <div class="flip-card-back bg-success">
                                <h5 class="card-title">Finance</h5>
                                <p class="card-text">Manage the company's finance.</p>
                            <a href="/finance/associates" class="btn btn-primary stretched-link bg-success border-0">Go to finance</a>
                        </div>
                    </div>
                </div>
                <div class="flip-card" style={{maxWidth: '18rem'}}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-info">
                            <img src={require('../assets/Cog.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
                            <h3>Operations</h3>
                        </div>
                        <div class="flip-card-back bg-info">
                            <h5 class="card-title">Operations</h5>
                            <p class="card-text">Manage the company's Operations.</p>
                            <a href="/operations/projects" class="btn btn-primary stretched-link bg-info border-0">Go to Operations</a>
                        </div>
                    </div>
                </div>
                <div class="flip-card" style={{maxWidth: '18rem'}}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-danger">
                            <img src={require('../assets/HR.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
                            <h3>Human Resources</h3>
                        </div>
                        <div class="flip-card-back bg-danger">
                            <h5 class="card-title">Human Resources</h5>
                            <p class="card-text">Manage the company's human resources.</p>
                            <a href="/hr/summary" class="btn btn-primary stretched-link bg-danger border-0">Go to Human Resources</a>
                        </div>
                    </div>
                </div>
                <div class="flip-card" style={{maxWidth: '18rem'}}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-warning">
                            <img src={require('../assets/Calendar.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
                            <h3>Schedule</h3>
                        </div>
                        <div class="flip-card-back bg-warning">
                            <h5 class="card-title">Schedule</h5>
                            <p class="card-text">Manage the company's schedule.</p>
                            <a href="#" class="btn btn-primary stretched-link bg-warning border-0">Go to Schedule</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    );
};
 
export default Home;