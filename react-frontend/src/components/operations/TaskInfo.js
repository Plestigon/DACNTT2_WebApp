import React, { useCallback } from "react";
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Select from 'react-select';
import { dateTimeFormat, handleDate, getDaysSince, getHoursSince, getDaysUntil, getHoursUntil } from "../../utils/DateHelper";
import "react-datepicker/dist/react-datepicker.css";
import { Button } from "react-bootstrap";
import SideBar from "../SideBar";
import TopBar from "../TopBar";
import Notify, {success, error} from "../../utils/Notify";
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import "../../css/editor.css";
  
const TaskInfo = () => {
    const params = useParams();
    const [stateOptions, setStateOptions] = useState([]);
    const [assigneeOptions, setAssigneeOptions] = useState([]);
    const [priorityOptions, setPriorityOptions] = useState([])
    const [data, setData] = useState({
        id: 0,
        name: '',
        projectId: 0,
        projectName: '',
        assignee: 0,
        assigneeName: '',
        createDate: '',
        updateDate: '',
        dueDate: '',
        priority: 0,
        priorityName: '',
        state: 0,
        stateName: '',
        description: '',
    });
    const [inputDisabled, setInputDisabled] = useState(true);

    const loadTaskData = useCallback(() => {
        fetch("http://localhost:8080/operations/task/" + params.id,{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode===200) {
                console.log(result.data);
                setData({
                    id: result.data.id,
                    name: result.data.name,
                    projectId: result.data.projectId,
                    projectName: result.data.projectName,
                    assignee: result.data.assigneeId,
                    assigneeName: result.data.assigneeName,
                    createDate: result.data.createDate,
                    updateDate: result.data.updateDate,
                    dueDate: handleDate(result.data.dueDate),
                    priority: result.data.priority,
                    priorityName: result.data.priorityName,
                    state: result.data.state,
                    stateName: result.data.stateName,
                    description: result.data.description,
                })
            }
        })
        .catch (e => {
            console.log("ERROR_loadTaskData: " + e);
        })
    }, [params.id])

    useEffect(() => {
        loadTaskData();
    }, [loadTaskData])

    useEffect(() => {
        function loadStates() {
            fetch("http://localhost:8080/operations/tasks/states",{
                method:"GET"
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    setStateOptions(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadStates: " + e);
            })
        }
        loadStates();
        
        function loadPriorities() {
            fetch("http://localhost:8080/operations/tasks/priorities",{
                method:"GET"
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    setPriorityOptions(result.data);
                }
            })
            .catch (e => {
                console.log("ERROR_loadPriorities: " + e);
            })
        }
        loadPriorities();
    }, [])

    const loadAssignees = useCallback(() => {
        //console.log("load " + data.id);
        if (data.id <= 0) return;
        fetch("http://localhost:8080/operations/task/" + data.id + "/members",{
            method:"GET"
        })
        .then(result=>result.json())
        .then((result)=>{
            if (result.statusCode === 200) {
                var a = [];
                result.data.forEach(e => {
                    if (e.employeeId !== data.assignee) {
                        a.push({label: e.employeeName, value: e.employeeId})
                    }
                });
                setAssigneeOptions(a);
            }
        })
        .catch (e => {
            console.log("ERROR_loadMembers: " + e);
        })
    }, [data.id])

    useEffect(() => {
        loadAssignees();
    }, [loadAssignees])

    function handleInputChange(e) {
        const name = e.target.name;
        const value = e.target.value;
        if (name === 'dueDate') {
            var newDate = new Date(value);
            var today = new Date();
            if (newDate < today) {
                error("Invalid due date");
                return;
            }
        }
        setData(prevState => ({...prevState, [name]: value}));
        // console.log(data);
    }

    function handleDescriptionChange(data) {
        setData(prevState => ({...prevState, 'description': data}));
        //console.log(assigneeOptions);
    }

    function handleStateChange(e) {
        if (e.value !== data.state) {
            setData(prevState => ({...prevState, 'state': e.value, 'stateName': e.label}));
            fetch("http://localhost:8080/operations/task/" + params.id + "/state?newValue=" + e.value,{
                method:"POST"
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    success("Task state updated");
                    loadTaskData();
                }
            })
            .catch (e => {
                console.log("ERROR_handleStateChange: " + e);
            })
        }
    }

    function handlePriorityChange(e) {
        if (e.value !== data.priority) {
            setData(prevState => ({...prevState, 'priority': e.value, 'priorityName': e.label}));
            fetch("http://localhost:8080/operations/task/" + params.id + "/priority?newValue=" + e.value,{
                method:"POST"
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    success("Priority level updated");
                    loadTaskData();
                }
            })
            .catch (e => {
                console.log("ERROR_handlePriorityChange: " + e);
            })
        }
    }

    function handleAssigning(e) {
        if (e.value !== data.assignee) {
            setData(prevState => ({...prevState, 'assignee': e.value, assigneeName: e.label}));
            fetch("http://localhost:8080/operations/task/" + params.id + "/assign?assigneeId=" + e.value,{
                method:"POST"
            })
            .then(result=>result.json())
            .then((result)=>{
                if (result.statusCode === 200) {
                    success("Task assigned to " + e.label);
                    loadTaskData();
                }
            })
            .catch (e => {
                console.log("ERROR_handleAssigning: " + e);
            })
        }
    }

    function handleEditClick() {
        setInputDisabled(false);
    }

    function handleCancelEditClick() {
        setInputDisabled(true);
        loadTaskData();
    }

    function handleEditSubmit() {
        // console.log(data);
        fetch("http://localhost:8080/operations/project/edit",{
            method:"POST",
            body: JSON.stringify({
                'id': params.id,
                'name': data.name,
                'dueDate': data.dueDate,
                'description': data.description
            }),
            headers: { "Content-type": "application/json; charset=UTF-8" }
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

    return (
<div>
<Notify/>
<SideBar/>
<TopBar/>
    <div class="content mt-5">
        <div class="row w-100">
            <Button onClick={handleEditClick} className="ms-auto" style={{width: '50px', height: '50px', marginRight: '10%'}}
            data-toggle="tooltip" data-placement="bottom" title="Edit task"><i class="bi bi-pencil-square"></i></Button>
        </div>
        <Container>
            <Row style={{marginLeft: '10%', marginRight: '10%'}}>
                <div class="col-6">
                    <label className="w-100">
                        Task Name: <input name="name" class="form-control" value={data.name} onChange={handleInputChange} disabled={inputDisabled} />
                    </label>
                </div>
                <div class="col-6">
                    <label className="w-100">
                        Parent Project: <div class="form-control link-primary hover-lightgray" title="See Project's details" style={{cursor:"pointer"}}
                        onClick={() => {
                            var win = window.open('/operations/project/' + data.projectId, '_blank');
                            win.focus();
                        }}>{data.projectName}</div>
                    </label>
                </div>
            </Row>
            <br></br>
            <Row style={{marginLeft: '10%', marginRight: '10%'}}>
                <Col>
                Asigned To:
                <Select options={assigneeOptions} value={{label: data.assigneeName, value: data.assignee}} onChange={handleAssigning}/>
                </Col>
                <Col>
                State:
                <Select options={stateOptions} value={{label: data.stateName, value: data.state}} onChange={handleStateChange}/>
                </Col>
                <Col>
                Priority:
                <Select options={priorityOptions} value={{label: data.priorityName, value: data.priority}} onChange={handlePriorityChange}/>
                </Col>
            </Row>
            <Row style={{marginLeft: '10%', marginRight: '10%', marginTop:"20px"}}>
                <Col>
                Due Date:
                <input type="datetime-local" class="form-control" name="dueDate" title={getDaysUntil(data.dueDate) > 0 ? (getDaysUntil(data.dueDate) + " days left") : (getHoursUntil(data.dueDate) + " hours left")}
                value={data.dueDate} onChange={handleInputChange} disabled={inputDisabled}/>
                </Col>
                <Col>
                Last Update:
                <div class="form-control">{dateTimeFormat(data.updateDate) + " "}
                ({getDaysSince(data.updateDate) > 0 ? (getDaysSince(data.updateDate) + " days ago") : (getHoursSince(data.updateDate) + " hours ago")})</div>
                </Col>
                <Col>
                Create Date:
                <div class="form-control">{dateTimeFormat(data.createDate)}</div>
                </Col>
            </Row>
            <Row className="my-4 p-2 task-info" style={{marginLeft: '10%', marginRight: '10%'}}>
                <label className="w-100">Description:</label>
                <CKEditor
                    editor={ ClassicEditor }
                    data={data.description} 
                    onReady={ editor => {
                        //console.log( 'Editor is ready to use!', editor );
                    } }
                    onChange={ ( event, editor ) => {
                        const data =  editor.getData();
                        //console.log( {event, editor, data} );
                        handleDescriptionChange(data);
                    } }
                    disabled={inputDisabled}
                />
            </Row>
        </Container>
        <div class="row w-100">
            <Button onClick={handleEditSubmit} className="btn-warning ms-auto me-3" style={{width: '150px', height: '50px', display: inputDisabled ? "none" : "block"}} >Submit</Button>
            <Button onClick={handleCancelEditClick} className="btn-secondary me-5" style={{width: '150px', height: '50px', display: inputDisabled ? "none" : "block"}} >Cancel Editing</Button>
        </div>

        <div class="row mt-2 mb-5" style={{marginLeft: '5%', marginRight: '5%'}}>
        <div class="col-6">
            <p class="h5"><i class="bi bi-card-text"></i> Discussions</p>
            <hr/>
            <div class="row">
                <div class="col-2">Comment</div>
                <div class="col-10">
                    <textarea class="form-control" rows={2} cols={10}/>
                </div>
            </div>
            <div class="row w-100">
                <Button className="ms-auto mt-2" style={{width: '150px'}}>Submit</Button>
            </div>
            <hr/>
            {/* {prjUpdates.map(p => 
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
            )} */}
        </div>
    </div>
    
    
    </div>
</div>
);
};
 
export default TaskInfo;