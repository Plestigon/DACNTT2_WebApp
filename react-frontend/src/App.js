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
import NewPassword from './components/profile/NewPassword';
import Associates from './components/finance/Associates';
import Deals from './components/finance/Deals';
import DealDetails from './components/finance/DealDetails';

function App() {

  return (
    <>
      <BrowserRouter>
        <Authentication>
          <Routes>
            {/* === Not secured */}

            {/* <Route path="/" element={<Home/>}/>

            <Route path="/operations/projects" element={<Operations/>}/>
            <Route path="/operations/project/:id" element={<ProjectInfo/>} />
            <Route path="/operations/my-projects" element={<MyProjects/>}/>
            <Route path="/operations/task/:id" element={<TaskInfo/>}/>
            <Route path="/profile" element={<ProfilePage/>}/>

            <Route path="/hr/submit-form" element={<SubmitForm/>}/>
            <Route path="/hr/my-forms" element={<MyForms/>}/>
            <Route path="/hr/summary" element={<HRSummary/>}/>
            <Route path="/hr/contracts" element={<MyContracts/>}/>
            <Route path="/hr/employees" element={<EmpList/>}/>
            
            <Route path="/finance/associates" element={<Associates/>}/> */}

            {/* === Secured */}
            
            <Route path="/" element={<PrivateRoute><Home/></PrivateRoute>}/>
            
            <Route path="/operations/projects" element={<PrivateRoute><Operations/></PrivateRoute>}/>
            <Route path="/operations/project/:id" element={<PrivateRoute><ProjectInfo/></PrivateRoute>} />
            <Route path="/operations/my-projects" element={<PrivateRoute><MyProjects/></PrivateRoute>}/>
            <Route path="/operations/task/:id" element={<PrivateRoute><TaskInfo/></PrivateRoute>}/>
            <Route path="/profile" element={<PrivateRoute><ProfilePage/></PrivateRoute>}/>

            <Route path="/hr/submit-form" element={<PrivateRoute><SubmitForm/></PrivateRoute>}/>
            <Route path="/hr/my-forms" element={<PrivateRoute><MyForms/></PrivateRoute>}/>
            <Route path="/hr/summary" element={<PrivateRoute><HRSummary/></PrivateRoute>}/>
            <Route path="/hr/contracts" element={<PrivateRoute><MyContracts/></PrivateRoute>}/>
            <Route path="/hr/employees" element={<PrivateRoute><EmpList/></PrivateRoute>}/>
            
            <Route path="/finance/associates" element={<PrivateRoute><Associates/></PrivateRoute>}/>
            <Route path="/finance/associates/:id/deals" element={<PrivateRoute><Deals/></PrivateRoute>}/>
            <Route path="/finance/deals/:id/stages" element={<PrivateRoute><DealDetails/></PrivateRoute>}/>
            
            <Route path="/login" element={<Login/>}/>
          </Routes>
        </Authentication>
      </BrowserRouter>
    </>
  );
}

export default App;
