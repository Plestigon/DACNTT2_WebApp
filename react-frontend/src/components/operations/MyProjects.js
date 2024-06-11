import React, { useEffect, useState } from "react";
import '../../css/home.css';
import "bootstrap-icons/font/bootstrap-icons.css";
import TopBar from "../TopBar";
import SideBar from "../SideBar";
import '../../css/sidebar.css';
import '../../css/utils.css';
import Notify, {success, error, loading, dismiss} from "../../utils/Notify";
import { getDaysUntil, getHoursUntil, getDaysSince, getHoursSince, getTimeUntil, getTimeSince } from "../../utils/DateHelper";
import { Button, Card } from "react-bootstrap";
import 'react-horizontal-scrolling-menu/dist/styles.css';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css"; 
import styled from "@emotion/styled/macro";

function MyProjects() {
    const [projects, setProjects] = useState([]);
    const [tasks, setTasks] = useState([]);
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
            fetch("http://localhost:8080/operations/my-projects?employeeId=" + employeeId,{
                method:"GET"
            })
            .then(result=>result.json())
            .then((result)=>{
                dismiss(toastId);
                if (result.statusCode === 200) {
                    // console.log(result.data);
                    setProjects(result.data);
                }
                else {
                    error("Load projects failed");
                }
            })
            .catch (e => {
                console.log("ERROR_loadProjects: " + e);
            })
        }
        loadProjects();
    }, [])

    function loadTasks(projectId) {
        const toastId = loading("Loading task data...");
        fetch("http://localhost:8080/operations/my-project/" + projectId + "/tasks?employeeId=" + employeeId,{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            if (result.statusCode === 200) {
                // console.log(result.data);
                setTasks(result.data);
            }
            else {
                error("Load task data failed");
            }
        })
        .catch (e => {
            console.log("ERROR_loadTasks: " + e);
        })
    }

    return (
        
    <div>

        <SideBar/>
        <TopBar/>

        <div class="content container">
        <div className="slider-container" style={{marginLeft:"25px", marginRight:"25px", marginTop:"10px", maxHeight:"50vh"}}>
            <Slider {...settings}>
            {
                projects.length > 0 && (
                    projects.map(p=>(
                        <div class="card" key={p.id}>
                            <div class="card-body" style={{minHeight:"220px"}}>
                                <h5 class="card-title text-nowrap d-inline-block text-truncate" style={{maxWidth:"100%"}}>{p.name}</h5>
                                <p class={"card status-card project-status-" + p.status}>{p.statusName}</p>
                                <p class="card-text text-end">You have <span class="fw-bold">{p.tasksInProgress}</span> task{p.tasksInProgress > 1 ? "s" : ""} in progress</p>
                                <p class="card-text text-end">You have <span class="fw-bold">{p.tasksNotStarted}</span> task{p.tasksNotStarted > 1 ? "s" : ""} not started</p>
                                <p class="card-hover-text">Role: <span class="fw-bold">{p.memberInfo.roleName}</span></p>
                                <p class="card-hover-text">Nearest deadline in <span class="fw-bold">{p.nearestDueDate != null ? getDaysUntil(p.nearestDueDate) : "-/-"}</span> days <span class="fw-bold">{p.nearestDueDate != null ? getHoursUntil(p.nearestDueDate) : "-/-"}</span> hours</p>
                                <div class="row mx-2"><Button class="btn btn-primary w-100" onClick={() => loadTasks(p.id)}>Show assigned tasks</Button></div>
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
                                <h5 class="card-title text-nowrap d-inline-block text-truncate" style={{maxWidth:"100%"}}>You haven't joined any project.</h5>
                            </div>
                        </div>
                    );
                }
                for (let i = placeholders.length; i < 3-projects.length; i++) {
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

            </div>
            <hr ></hr>

            {/* <div class="col px-md-2" href="#">Selected project's Name hyperlink that goes to Projinfo</div> */}
            <div class="card table-card table-responsive" style={{height:'48vh'}}>
                <table className="table-clickable table table-hover table-collapsed">
                <thead class="table-primary">
                <tr>
                    <th scope="col">Task Name</th>
                    <th scope="col">Asigned To</th>
                    <th scope="col" style={{width:'15%'}}>Priority</th>
                    <th scope="col" style={{width:'15%'}}>Last Updated</th>
                    <th scope="col" style={{width:'15%'}}>Due Date</th>
                    <th scope="col" style={{width:'15%'}}>State</th>
                </tr>
                </thead>
                <tbody>
                {
                    tasks.length > 0 && (
                        tasks.map(t => (
                            <tr key={t.id} title="See task details" style={{cursor:"pointer"}} onClick={() => {
                                var win = window.open('/operations/task/' + t.id, '_blank');
                                win.focus();
                            }}>
                            <td>{t.name}</td>
                            <td>{t.assigneeName}</td>
                            <td><div class={"card status-card priority-" + t.priority}>{t.priorityName}</div></td>
                            <td>{getTimeSince(t.updateDate)}</td>
                            <td>{getTimeUntil(t.dueDate)}</td>
                            <td><div class={"card status-card task-state-" + t.state}>{t.stateName}</div></td>
                            </tr>
                        ))
                    )
                }
                </tbody>
                </table>
            </div>


    </div>
    </div>

    );
};
 
export default MyProjects;