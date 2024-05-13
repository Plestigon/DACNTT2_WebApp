import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import React from "react";
// import '../css/home.css';
import SideBar from '../SideBar';
import { Link } from "react-router-dom";
import Nav from 'react-bootstrap/Nav';
import TopBar from "../TopBar";



function HRSummary() {
  return (
    <div>
        <SideBar/>
        <TopBar/>
            <h3 align="center"> Summmary of Human Resources Information</h3>
        <div class="content container pt-3 px-4">
            <div class="flip-card" style={{maxWidth: '18rem'}}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-success">
                            {/* <img src={require('../assets/Stocks.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/> */}
                            <h3>My Team</h3>
                        </div>
                        <div class="flip-card-back bg-success">
                            <a href="#" class="btn btn-primary stretched-link bg-success border-0">Go to my team</a>
                        </div>
                    </div>
                </div>
                <div class="flip-card" style={{maxWidth: '18rem'}}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-info">
                            {/* <img src={require('../assets/Cog.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/> */}
                            <h3>My Forms</h3>
                        </div>
                        <div class="flip-card-back bg-info">
                            <a href="#" class="btn btn-primary stretched-link bg-info border-0">Go to my forms</a>
                        </div>
                    </div>
                </div>
                <div class="flip-card" style={{maxWidth: '18rem'}}>
                    <div class="flip-card-inner">
                        <div class="flip-card-front bg-info">
                            {/* <img src={require('../assets/Cog.png')} class="card-img-top" alt="..." style={{width: '12rem'}}/> */}
                            <h3>My Contracts</h3>
                        </div>
                        <div class="flip-card-back bg-info">
                            <a href="#" class="btn btn-primary stretched-link bg-info border-0">Go to my contracts</a>
                        </div>
                    </div>
                </div>
    </div>
    </div>
  );
}

export default HRSummary;