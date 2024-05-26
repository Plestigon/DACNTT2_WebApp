import React, { useEffect, useState } from "react";
import '../../css/home.css';
import "bootstrap-icons/font/bootstrap-icons.css";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import '../../css/utils.css';
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
import { Button, Card } from "react-bootstrap";
import 'react-horizontal-scrolling-menu/dist/styles.css';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css"; 
import styled from "@emotion/styled/macro";

function MyProjects() {
    const [projects, setProjects] = useState([]);
    const employeeId = 1;

    var settings = {
    infinite: false,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 3,
    initialSlide: 0,
    vertical: false,
    responsive: [
      {
        breakpoint: 1200,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2,
          infinite: false
        }
      },
      {
        breakpoint: 780,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
          infinite: false
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

    useEffect(() => {
        function loadProjects() {
            const toastId = loading("Loading project data...");
            fetch("http://localhost:8080/operations/projects?employeeId=" + employeeId,{
                method:"GET"
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    dismiss(toastId);
                    setProjects(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadProjects: " + e);
                dismiss(toastId);
                error("Load project data failed");
            })
        }
        loadProjects();
    }, [])

    return (
        
    <div>

        <Notify/>
        <SideBar/>
        <TopBar/>

        <div className="slider-container" style={{marginLeft:"225px", marginRight:"50px", marginTop:"25px"}}>
            <Slider {...settings}>
            {
                projects.length > 0 && (
                    projects.map(p=>(
                        <div class="card" key={p.id}>
                            <div class="card-body" style={{minHeight:"220px"}}>
                                <h5 class="card-title">{p.name}</h5>
                                <p class="card-text" style={{textAlign:"right"}}>{p.statusName}</p>
                                <p class="card-text">You have X tasks in progress</p>
                                <p class="card-text">You have X tasks not started</p>
                                <p class="card-hover-text">Role Title</p>
                                <p class="card-hover-text">You have X tasks in progress</p>
                                <p class="card-hover-text">You have X tasks not started</p>
                                <Button class="btn btn-primary">Check Project 1 tasks</Button>
                            </div>
                        </div>
                    ))
                )
            }
            {(() => {
                const placeholders = [];
                if (projects.length === 0) {
                    placeholders.push(
                        <div class="card">
                            <div class="card-body" style={{minHeight:"220px"}}>
                                <h5 class="card-title">You haven't joined any project.</h5>
                            </div>
                        </div>
                    );
                }
                for (let i = placeholders.length; i <= 3-projects.length; i++) {
                    placeholders.push(<div class="card"/>);
                }
                return placeholders;
            })()}
            {/* <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Project 2</h5>
                    <p class="card-text" style={{textAlign:"right"}}>Status goes here</p>
                    <p class="card-text">You have X tasks in progress</p>
                    <p class="card-text">You have X tasks not started</p>
                    <p class="card-hover-text">Role Title</p>
                    <p class="card-hover-text">You have X tasks in progress</p>
                    <p class="card-hover-text">You have X tasks not started</p>
                    <Button class="btn btn-primary">Check Project 2 tasks</Button>
                </div>
            </div>
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Project 3</h5>
                    <p class="card-text" style={{textAlign:"right"}}>Status goes here</p>
                    <p class="card-text">You have X tasks in progress</p>
                    <p class="card-text">You have X tasks not started</p>
                    <p class="card-hover-text">Role Title</p>
                    <p class="card-hover-text">You have X tasks in progress</p>
                    <p class="card-hover-text">You have X tasks not started</p>
                    <Button class="btn btn-primary">Check Project 3 tasks</Button>
                </div>
            </div> */}
            {/* <div class="card" style="width: 18rem;">
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
            </div> */}

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