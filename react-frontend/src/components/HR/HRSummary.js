import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import React from "react";
// import '.../css/home.css'
import SideBar from '../SideBar';
import { Link } from "react-router-dom";
import Nav from 'react-bootstrap/Nav';
import TopBar from "../TopBar";



function HRSummary() {
  return (
    <div>
        <SideBar/>
        <TopBar/>
            <h3 align="center" style={{marginBottom: '125px'}}> Summmary of Human Resources Information</h3>
        <Row className="justify-content-center">
            <div class="flip-card" style={{maxWidth: '18rem', boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)' }}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-success">
<<<<<<< Updated upstream
                            <img src={require('../../assets/Team.png')} class="card-img-top" alt="..." style={{width: '10rem'}}/>
=======
                            <img src={require('../../assets/Teams.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
>>>>>>> Stashed changes
                            <h3>My Team</h3>
                        </div>
                        <div class="flip-card-back ">
                            <a href="#" class="btn btn-primary stretched-link border-0">Go to my team</a>
                        </div>
                    </div>
                </div>
                <div class="flip-card" style={{maxWidth: '18rem', boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)' }}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-info">
<<<<<<< Updated upstream
                        <img src={require('../../assets/Form.png')} class="card-img-top" alt="..." style={{width: '10rem'}}/>
                            <h3>My Forms</h3>
                        </div>
                        <div class="flip-card-back bg-info">
                            <h3>Manage my x forms</h3>
                            <a href="#" class="btn btn-primary stretched-link bg-info border-0">Go to my forms</a>
=======
                            <img src={require('../../assets/Forms.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
                            <h3>My Forms</h3>
                        </div>
                        <div class="flip-card-back ">
                            <a href="#" class="btn btn-primary stretched-link border-0">Go to my forms</a>
>>>>>>> Stashed changes
                        </div>
                    </div>
                </div>
                <div class="flip-card" style={{maxWidth: '18rem', boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.1)' }}>
                    <div class="flip-card-inner">
<<<<<<< Updated upstream
                        <div class="flip-card-front bg-info">
                        <img src={require('../../assets/Contracts.png')} class="card-img-top" alt="..." style={{width: '10rem'}}/>
                            <h3>My Contracts</h3>
                        </div>
                        <div class="flip-card-back bg-info">
                            <h3>Manage my X contracts</h3>
                            <a href="#" class="btn btn-primary stretched-link bg-info border-0">Go to my contracts</a>
=======
                        <div class="flip-card-front bg-success">
                            <img src={require('../../assets/Contracts.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/>
                            <h3>My Contracts</h3>
                        </div>
                        <div class="flip-card-back ">
                            <a href="#" class="btn btn-primary stretched-link border-0">Go to my contracts</a>
>>>>>>> Stashed changes
                        </div>
                    </div>
                </div>
        </Row>
    </div>
  );
}

export default HRSummary;