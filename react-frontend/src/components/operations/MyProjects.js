import React, { useRef } from "react";
import '../../css/home.css';
import { Link } from "react-router-dom";
import "bootstrap-icons/font/bootstrap-icons.css";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import '../../css/utils.css';
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
import DeleteConfirmModal from "./DeleteConfirmModal";
import { Button, Card } from "react-bootstrap";
import 'react-horizontal-scrolling-menu/dist/styles.css';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css"; 
import styled from "@emotion/styled/macro";



  
function MyProjects() {

        var settings = {
          infinite: false,
          speed: 500,
          slidesToShow: 3,
          slidesToScroll: 3,
          initialSlide: 0,
          vertical: false,
          responsive: [
            {
              breakpoint: 1024,
              settings: {
                slidesToShow: 3,
                slidesToScroll: 3,
                infinite: false,
                dots: true
              }
            },
            {
              breakpoint: 600,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 2,
                initialSlide: 2
              }
            },
            {
              breakpoint: 480,
              settings: {
                slidesToShow: 1,
                slidesToScroll: 1
              }
            }
          ]
        };
        const Background = styled.div({
            backgroundSize: "cover",
            backgroundRepeat: "no-repeat",
            color: "#FFF",
            position: "relative",
            width: "500px",
            height: "350px",
            cursor: "pointer",
            backgroundImage: "url(/bg.jpg)",
          });
    return (
        
    <div>

        <Notify/>
        <SideBar/>
        <TopBar/>

        <div className="slider-container" style={{marginLeft:"225px",marginRight:"50px"}}>
            <Slider {...settings}>
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Project 1</h5>
                    <p class="card-text">You have X tasks in progress</p>
                    <p class="card-text">You have X tasks not started</p>
                    <p class="card-text" style={{textAlign:"right"}}>Status goes here</p>
                    <p class="card-hover-text">Role Title</p>
                    <p class="card-hover-text">You have X tasks in progress</p>
                    <p class="card-hover-text">You have X tasks not started</p>
                    <a href="#" class="btn stretched-link border-0" style={{textAlign:"center"}}>Check Project 1 tasks</a>
                </div>
            </div>
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Project 2</h5>
                    <p class="card-text">You have X tasks in progress</p>
                    <p class="card-text">You have X tasks not started</p>
                    <p class="card-text" style={{textAlign:"right"}}>Status goes here</p>
                    <p class="card-hover-text">Role Title</p>
                    <p class="card-hover-text">You have X tasks in progress</p>
                    <p class="card-hover-text">You have X tasks not started</p>
                    <a href="#" class="btn stretched-link border-0" style={{textAlign:"center"}}>Check Project 2 tasks</a>
                </div>
            </div>
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Project 3</h5>
                    <p class="card-text">You have X tasks in progress</p>
                    <p class="card-text">You have X tasks not started</p>
                    <p class="card-text" style={{textAlign:"right"}}>Status goes here</p>
                    <p class="card-hover-text">Role Title</p>
                    <p class="card-hover-text">You have X tasks in progress</p>
                    <p class="card-hover-text">You have X tasks not started</p>
                    <a href="#" class="btn stretched-link border-0" style={{textAlign:"center"}}>Check Project 3 tasks</a>
                </div>
            </div>
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Project 4</h5>
                    <p class="card-text">You have X tasks in progress</p>
                    <p class="card-text">You have X tasks not started</p>
                    <p class="card-text" style={{textAlign:"right"}}>Status goes here</p>
                    <p class="card-hover-text">Role Title</p>
                    <p class="card-hover-text">You have X tasks in progress</p>
                    <p class="card-hover-text">You have X tasks not started</p>
                    <a href="#" class="btn stretched-link border-0" style={{textAlign:"center"}}>Check Project 4 tasks</a>
                </div>
            </div>

            </Slider>

            <hr ></hr>

            <div class="row mx-md-n5">
                <div class="col px-md-2" href="#">Selected project's Name hyperlink that goes to Projinfo</div>
                <div class="col px-md-1">
                    <table className="table-clickable table table-hover table-collapsed" style={{width:'100%'}}>
                    <thead class="table-primary">
                    <tr>
                        <th scope="col">Task Name</th>
                        <th scope="col">Asigned to</th>
                        <th scope="col">Priority</th>
                        <th scope="col">Last Updated</th>
                        <th scope="col">State</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>@mdo</td>
                        <td>@mdo</td>
                    </tr>
                    </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>

    );
};
 
export default MyProjects;