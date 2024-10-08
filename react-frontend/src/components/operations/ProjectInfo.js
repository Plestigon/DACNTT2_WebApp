import React, { useCallback } from "react";
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Select from 'react-select';
import { handleDateTime, dateTimeFormat } from "../../utils/DateHelper";
import "react-datepicker/dist/react-datepicker.css";
import { Button } from "react-bootstrap";
import AddMemberModal from "./AddMemberModal";
import SideBar from "../SideBar";
import TopBar from "../TopBar";
import Notify, {success} from "../../utils/Notify";
import NewTaskModal from "./NewTaskModal";
import { useAuthentication } from "../system/Authentication";
  
const ProjectInfo = () => {
    const auth = useAuthentication();
    const params = useParams();
    const [options, setOptions] = useState([]);
    const [data, setData] = useState({
        name: '',
        owner: 0,
        ownerName: '',
        dueDate: '',
        status: -1,
        statusName: '',
        description: '',
        memberIds: []
    });
    const [prjUpdates, setPrjUpdates] = useState([]);
    const [members, setMembers] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [inputDisabled, setInputDisabled] = useState(true);
    const [addMemberModalShow, setAddMemberModalShow] = useState(false);
    const [newTaskModalShow, setNewTaskModalShow] = useState(false);
    const [comment, setComment] = useState('');

    useEffect(() => {
        document.title = 'Project Info - TDTU EMS';
    }, []);

    const loadProjectData = useCallback(() => {
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/" + params.id + "?token=" + auth.token,{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            // console.log(result);
            var formattedDate = handleDateTime(result.dueDate);
            setData({
                name: result.name,
                owner: result.ownerId,
                ownerName: result.ownerName,
                dueDate: formattedDate,
                status: result.status,
                statusName: result.statusName,
                description: result.description,
                memberIds: result.memberIds
            })
        })
        .catch (e => {
            console.log("ERROR_loadProjectData: " + e);
        })

        // Get status options
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/statuses",{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            setOptions(result);
        })
        .catch (e => {
            console.log("ERROR_loadProjectData_statuses: " + e);
        })
    }, [params])
    
    const loadProjectUpdates = useCallback(() => {
        //Get project updates
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/" + params.id + "/updates?token=" + auth.token,{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            // console.log(result);
            if (result.statusCode === 200) {
                setPrjUpdates(result.data);
            }
        })
        .catch (e => {
            console.log("ERROR_loadProjectData_updates: " + e);
        })
    }, [])

    useEffect(() => {
        loadProjectData();
        loadProjectUpdates();
    }, [params, loadProjectData, loadProjectUpdates])

    const loadMembers = useCallback(() => {
        //Get member list
        if (data.memberIds != null && data.memberIds.length > 0) {
            var query = "";
            if (data.memberIds.length > 0) {
                query = "&ids=" + data.memberIds.join(",");
            }
            fetch(process.env.REACT_APP_API_URI + "/operations/projects/members" + "?token=" + auth.token + query,{
                method:"GET",
                headers: { "ngrok-skip-browser-warning" : "true" }
            })
            .then(result=>result.json())
            .then((result)=>{
                console.log(result);
                if (result.statusCode === 200) {
                    setMembers(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadMembers: " + e);
            })
        }
    }, [data.memberIds]);

    useEffect(() => {
        loadMembers();
    }, [data.memberIds, loadMembers])

    const loadTasks = useCallback(() => {
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/" + params.id + "/tasks" + "?token=" + auth.token,{
            method:"GET",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            //console.log(result);
            if (result.statusCode === 200) {
                setTasks(result.data);
            }
            else {
                console.log(result.message);
            }
        })
        .catch (e => {
            console.log("ERROR_loadTasks: " + e);
        })
    }, [params.id])

    useEffect(() => {
        loadTasks();
    }, [loadTasks])

    function handleInputChange(e) {
        if (e.target) {
            const name = e.target.name;
            const value = e.target.value;
            setData(prevState => ({...prevState, [name]: value}));
            // console.log(data);
        }
        else {
            //Status select
            if (e.value !== data.status) {
                setData(prevState => ({...prevState, 'status': e.value, 'statusName': e.label}));
                handleUpdateStatus(e.value);
            }
        }
    }

    function handleUpdateStatus(status) {
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/" + params.id + "/update?status=" + status + "&token=" + auth.token,{
            method:"POST",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("Project status updated");
                loadProjectData();
                loadProjectUpdates();
            }
        })
        .catch (e => {
            console.log("ERROR_updateStatus: " + e);
        })
    }

    function handleEditClick() {
        setInputDisabled(false);
    }

    function handleCancelEditClick() {
        setInputDisabled(true);
        loadProjectData();
    }

    function handleEditSubmit() {
        // console.log(data);
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/edit" + "?token=" + auth.token,{
            method:"POST",
            body: JSON.stringify({
                'id': params.id,
                'name': data.name,
                'dueDate': data.dueDate,
                'description': data.description
            }),
            headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "ngrok-skip-browser-warning" : "true" 
            }
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                success("Project updated");
                window.location.reload();
            }
        })
        .catch (e => {
            console.log("ERROR_handleEditSubmit: " + e);
        })
    }

    function handleRemoveMember(memberId) {
        // console.log("remove " + memberId + " from " + params.id);
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/" + params.id + "/member?memberId=" + memberId + "&token=" + auth.token,{
            method:"DELETE",
            headers: { "ngrok-skip-browser-warning" : "true" }
        })
        .then(result=>result.json())
        .then((result)=>{
            // console.log(result);
            if (result.statusCode === 200) {
                success("Member removed");
                loadProjectData();
            }
        })
        .catch (e => {
            console.log("handleRemoveMember: " + e);
        })
    }

    function handleProjectUpdateSubmit() {
        fetch(process.env.REACT_APP_API_URI + "/operations/projects/" + params.id + "/updates" + "?token=" + auth.token,{
            method:"POST",
            body: JSON.stringify({
                'writerId': auth.id,
                'comment': comment
            }),
            headers: { 
                "Content-type": "application/json; charset=UTF-8",
                "ngrok-skip-browser-warning" : "true" 
            }
        })
        .then(result=>result.json())
        .then((result)=>{
            // console.log(result);
            if (result.statusCode === 200) {
                setComment('');
                loadProjectUpdates();
            }
        })
        .catch (e => {
            console.log("ERROR_handleEditSubmit: " + e);
        })
    }

    return (
<div>
<SideBar/>
<TopBar/>
    <div class="content mt-5">
        <div class="row w-100">
            <Button onClick={handleEditClick} className="ms-auto" style={{width: '50px', height: '50px', marginRight: '10%', padding: '0px'}}
            data-toggle="tooltip" data-placement="bottom" title="Edit project"><i class="bi bi-pencil-square"></i></Button>
        </div>
        <Container>
            <Row style={{marginLeft: '10%', marginRight: '10%'}}>
                <label>
                    Project Name: <input name="name" class="form-control" value={data.name} onChange={handleInputChange} disabled={inputDisabled} />
                </label>
            </Row>
            <br></br>
            <Row style={{marginLeft: '10%', marginRight: '10%'}}>
                <Col>
                    <label class="w-100">
                    Owner:<input name="owner" class="form-control" value={data.ownerName} disabled/>
                    </label>
                </Col>
                <Col>
                Status:<Select options={options} value={{label: data.statusName, value: data.status}} onChange={handleInputChange}/>
                </Col>
                <Col>
                Due Date:
                <input type="datetime-local" class="form-control" name="dueDate" value={data.dueDate} 
                onChange={(e) => handleInputChange(e)}  disabled={inputDisabled}/>
                </Col>
            </Row>
            <Row className="my-4 p-2" style={{marginLeft: '10%', marginRight: '10%'}}>
                Description:
                <textarea class="form-control" name="description" rows={4} value={data.description} onChange={handleInputChange} disabled={inputDisabled} />
            </Row>
        </Container>
        <div class="row w-100">
            <Button onClick={handleEditSubmit} className="btn-warning ms-auto me-3" style={{width: '150px', height: '50px', display: inputDisabled ? "none" : "block"}} >Submit</Button>
            <Button onClick={handleCancelEditClick} className="btn-secondary me-5" style={{width: '150px', height: '50px', display: inputDisabled ? "none" : "block"}} >Cancel Editing</Button>
        </div>

        <div class="row mt-2 mb-5" style={{marginLeft: '5%', marginRight: '5%'}}>
            <div class="col-6">
                <p class="h5"><i class="bi bi-card-text"></i> Updates</p>
                <hr/>
                <div class="row">
                    <div class="col-2">Comment</div>
                    <div class="col-10">
                        <textarea class="form-control" name="comment" value={comment} rows={2} cols={10} onChange={(e) => setComment(e.target.value)}/>
                    </div>
                </div>
                <div class="row w-100">
                    <Button className="ms-auto mt-2" style={{width: '150px'}} onClick={handleProjectUpdateSubmit}>Submit</Button>
                </div>
                <hr/>
                {prjUpdates.map(p => 
                    <div class="row d-flex justify-content-center my-1" key={p.id}>
                        <div class="card mx-3 p-2" style={{width: '90%'}}>
                            <div class="d-flex">
                                <div class="fw-bold">{p.writerName}</div>
                                <div class="ms-auto fst-italic"><i class="bi bi-clock"></i> {dateTimeFormat(p.createTime)}</div>
                            </div>
                            <hr style={{marginTop: '1px', marginBottom: '3px'}}/>
                            <div class="d-flex">
                                <div class="mx-1">{p.comment}</div>
                            </div>
                        </div>
                    </div>
                )}
            </div>
            <div class="col-6 px-5">
                <div class="h5">
                    <i class="bi bi-person"></i> Members
                    <Button className="btn-primary ms-3" onClick={() => setAddMemberModalShow(true)} title="Add Member"><i class="bi bi-person-plus"></i></Button>
                    <AddMemberModal show={addMemberModalShow} onHide={() => setAddMemberModalShow(false)}
                    projectId={params.id} members={members} reload={loadProjectData}/>
                </div>
                <hr/>
                {members.length > 0 ? (members.map(m => 
                    <div class="row my-1" key={m.id}>
                        <div class="card hover-lightgray p-2" style={{width: '90%', cursor:"default"}}>
                            <div class="d-flex align-items-center">
                                <div class="">
                                    {m.employeeName} ({m.employeeEmail}) - {m.roleName}
                                </div>
                                <div class="ms-auto">
                                    <Button className="btn btn-danger" onClick={() => handleRemoveMember(m.id, m.name)}><i class="bi bi-trash"></i></Button>
                                </div>
                            </div>
                        </div>
                    </div>
                )) : ""}

                <div class="h5 mt-5">
                    <i class="bi bi-list-task"></i> Tasks
                    <Button className="btn-primary ms-3" onClick={() => setNewTaskModalShow(true)} title="New Task"><i class="bi bi-list-task"></i>+</Button>
                    <NewTaskModal show={newTaskModalShow} onHide={() => setNewTaskModalShow(false)}
                    projectId={params.id} members={members} reload={loadTasks}/>
                </div>
                <hr/>
                {tasks.length > 0 ? (tasks.map(t => 
                    <div class="row my-1" key={t.id}>
                        <div class="card hover-lightgray p-2" style={{width: '90%', cursor:"pointer"}} title="See Task's details"
                        onClick={() => {
                            var win = window.open('/operations/tasks/' + t.id, '_blank');
                            win.focus();
                        }}>
                            <div class="d-flex">
                                <div class="">
                                    [{t.name}] {t.assigneeName}
                                </div>
                                <div class="ms-auto">
                                    <div class={"card status-card project-status-" + t.state} style={{width: '100px'}}>{t.stateName}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                )) : ""}
            </div>
        </div>
    </div>
</div>
);
};
 
export default ProjectInfo;