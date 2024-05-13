import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route, Navigate, } from "react-router-dom";
import Home from './components/Home';
import Operations from './components/operations/Operations';
import ProjectInfo from './components/operations/ProjectInfo';
import SubmitForm from './components/hr/SubmitForm';
import HRSummary from './components/hr/HRSummary';
import MyContracts from './components/hr/MyContracts';

function App() {
  return (
    // <div className="App">
    //   <header className="App-header">
    //     <img src={logo} className="App-logo" alt="logo" />
    //     <p>
    //       Edit <code>src/App.js</code> and save to reload.
    //     </p>
    //     <a
    //       className="App-link"
    //       href="https://reactjs.org"
    //       target="_blank"
    //       rel="noopener noreferrer"
    //     >
    //       Learn React
    //     </a>
    //   </header>
    // </div>
    <>
      <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/operations" element={<Operations/>}/>
        <Route path="/operations/project/:id" element={<ProjectInfo/>} />
        <Route path="/submit-form" element={<SubmitForm/>}/>
        <Route path="/hr/summary" element={<HRSummary/>}/>
        <Route path="/hr/contracts" element={<MyContracts/>}/>
      </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
