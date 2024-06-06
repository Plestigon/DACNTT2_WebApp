import React, {useState} from 'react';
import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route, Navigate, useNavigate, } from "react-router-dom";
import Home from './components/Home';
import Operations from './components/operations/Operations';
import ProjectInfo from './components/operations/ProjectInfo';
import SubmitForm from './components/hr/SubmitForm';
import HRSummary from './components/hr/HRSummary';
import MyContracts from './components/hr/MyContracts';
import MyForms from './components/hr/MyForms';
import NewTaskModal from './components/operations/NewTaskModal';
import MyProjects from './components/operations/MyProjects';
import TaskInfo from './components/operations/TaskInfo';
import ProfilePage from './components/profile/ProfilePage';
import Login from './components/system/Login';
import PrivateRoute from './components/system/PrivateRoute';
import Authentication from './components/system/Authentication';
import EmpList from './components/hr/EmpList';

function App() {

  return (
    <>
      <BrowserRouter>
        <Authentication>
          <Routes>
            {/* <Route path="/" element={<Home/>}/> */}
            <Route path="/" element={<PrivateRoute><Home/></PrivateRoute>}/>
            
            {/* <Route path="/operations/projects" element={<Operations/>}/> */}
            <Route path="/operations/projects" element={<PrivateRoute><Operations/></PrivateRoute>}/>
            <Route path="/operations/project/:id" element={<ProjectInfo/>} />
            <Route path="/operations/my-projects" element={<MyProjects/>}/>
            <Route path="/operations/task/:id" element={<TaskInfo/>}/>
            <Route path="/profile" element={<ProfilePage/>}/>


            <Route path="/hr/submit-form" element={<SubmitForm/>}/>
            <Route path="/hr/my-forms" element={<MyForms/>}/>
            <Route path="/hr/summary" element={<HRSummary/>}/>
            <Route path="/hr/contracts" element={<MyContracts/>}/>
            <Route path="/hr/employees" element={<PrivateRoute><EmpList/></PrivateRoute>}/>
            
            <Route path="/login" element={<Login/>}/>
          </Routes>
        </Authentication>
      </BrowserRouter>
    </>
  );
}

export default App;
