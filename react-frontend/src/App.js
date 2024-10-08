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
import MyProjects from './components/operations/MyProjects';
import TaskInfo from './components/operations/TaskInfo';
import ProfilePage from './components/profile/ProfilePage';
import Login from './components/system/Login';
import PrivateRoute from './components/system/PrivateRoute';
import Authentication from './components/system/Authentication';
import EmpList from './components/hr/EmpList';
import Associates from './components/finance/Associates';
import Deals from './components/finance/Deals';
import DealDetails from './components/finance/DealDetails';
import Contacts from './components/finance/Contacts';
import NewContract from './components/hr/NewContract';
import ApproveForms from './components/hr/ApproveForms';
import Forbidden from './components/system/Forbidden';
import ManageContracts from './components/hr/ManageContracts';

function App() {

  return (
    <>
      <BrowserRouter>
        <Authentication>
          <Routes>
            {/* === Not secured */}

            {/* <Route path="/" element={<Home/>}/>

            <Route path="/operations/projects" element={<Operations/>}/>
            <Route path="/operations/projects/:id" element={<ProjectInfo/>} />
            <Route path="/operations/my-projects" element={<MyProjects/>}/>
            <Route path="/operations/tasks/:id" element={<TaskInfo/>}/>
            <Route path="/profile" element={<ProfilePage/>}/>

            <Route path="/hr/submit-form" element={<SubmitForm/>}/>
            <Route path="/hr/my-forms" element={<MyForms/>}/>
            <Route path="/hr/summary" element={<HRSummary/>}/>
            <Route path="/hr/contracts" element={<MyContracts/>}/>
            <Route path="/hr/approve-contracts" element={<ManageContracts/>}/>
            <Route path="/hr/new-contract" element={<NewContract/>}/>
            <Route path="/hr/employees" element={<EmpList/>}/>
            
            <Route path="/finance/associates" element={<Associates/>}/>
            <Route path="/finance/associates/:id/deals" element={<Deals/>}/>
            <Route path="/finance/deals/:id/stages" element={<DealDetails/>}/>
            <Route path="/finance/contacts/" element={<Contacts/>}/> */}

            {/* === Secured */}
            
            <Route path="/" element={<PrivateRoute><Home/></PrivateRoute>}/>
            
            <Route path="/operations/projects" element={<PrivateRoute><Operations/></PrivateRoute>}/>
            <Route path="/operations/projects/:id" element={<PrivateRoute><ProjectInfo/></PrivateRoute>} />
            <Route path="/operations/my-projects" element={<PrivateRoute><MyProjects/></PrivateRoute>}/>
            <Route path="/operations/tasks/:id" element={<PrivateRoute><TaskInfo/></PrivateRoute>}/>
            <Route path="/profile" element={<PrivateRoute><ProfilePage/></PrivateRoute>}/>

            <Route path="/hr/submit-form" element={<PrivateRoute><SubmitForm/></PrivateRoute>}/>
            <Route path="/hr/my-forms" element={<PrivateRoute><MyForms/></PrivateRoute>}/>
            <Route path="/hr/approve-forms" element={<PrivateRoute><ApproveForms/></PrivateRoute>}/>
            <Route path="/hr/summary" element={<PrivateRoute><HRSummary/></PrivateRoute>}/>
            <Route path="/hr/contracts" element={<PrivateRoute><MyContracts/></PrivateRoute>}/>
            <Route path="/hr/approve-contracts" element={<PrivateRoute><ManageContracts/></PrivateRoute>}/>
            <Route path="/hr/new-contract" element={<PrivateRoute><NewContract/></PrivateRoute>}/>
            <Route path="/hr/employees" element={<PrivateRoute><EmpList/></PrivateRoute>}/>
            
            <Route path="/finance/associates" element={<PrivateRoute><Associates/></PrivateRoute>}/>
            <Route path="/finance/deals" element={<PrivateRoute><Deals/></PrivateRoute>}/>
            <Route path="/finance/deals/:id/stages" element={<PrivateRoute><DealDetails/></PrivateRoute>}/>
            <Route path="/finance/contacts/" element={<PrivateRoute><Contacts/></PrivateRoute>}/>
            
            {/* === Always public */}

            <Route path="/login" element={<Login/>}/>
            <Route path="/forbidden" element={<Forbidden/>}/>
          </Routes>
        </Authentication>
      </BrowserRouter>
    </>
  );
}

export default App;
