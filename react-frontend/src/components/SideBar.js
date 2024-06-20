import React from "react";
import { useEffect, useState } from 'react';
import '../css/sidebar.css';
import AdbIcon from '@mui/icons-material/Adb';
import { Link } from "react-router-dom";
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Typography from '@mui/material/Typography';
import SubMenu from "./SubMenu";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/js/dist/dropdown'
import 'bootstrap/js/dist/collapse'
import { useAuthentication } from "./system/Authentication";

function SideBar() {
    const auth = useAuthentication();
    const [isOpMenuOpen, setOpMenuOpen] = useState(false);
    const [isHRMenuOpen, setHRMenuOpen] = useState(false);
    const [isFinanceMenuOpen, setFinanceMenuOpen] = useState(false);
    
    function toggleOpMenu() {
        if (!isOpMenuOpen) {
            setHRMenuOpen(false);
            setFinanceMenuOpen(false);
        }
        setOpMenuOpen(!isOpMenuOpen);
    }

    function toggleHRMenu() {
        if (!isHRMenuOpen) {
            setOpMenuOpen(false);
            setFinanceMenuOpen(false);
        }
        setHRMenuOpen(!isHRMenuOpen);
    };
    
    function toggleFinanceMenu() {
        if (!isFinanceMenuOpen) {
            setOpMenuOpen(false);
            setHRMenuOpen(false);
        }
        setFinanceMenuOpen(!isFinanceMenuOpen);
    };
    
    return(
<div>
    <div class="sidebar bg-primary">
    <Nav className="me-auto">
        <div class="my-5 w-100 sidebar-top" style={{height: '100px'}}>
            <div class="row w-100 mx-auto text-center">
                <i class="bi bi-amazon h1"></i>
            </div>
            <div class="row w-100 mx-auto my-auto text-center">
                <Typography variant="h6" noWrap component="a"
                sx={{
                    mr: 2,
                    display: { xs: 'none', md: 'flex' },
                    fontFamily: 'monospace',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: 'inherit',
                    textDecoration: 'none',
                }}
                >
                LOGO
                </Typography>
            </div>
        </div>
        {/* <div class="w-100 mb-2" style={{borderBottom: '1px solid white'}}/> */}
        <Nav.Link as={Link} to="/"><i class="bi bi-house"></i> Home</Nav.Link>


        <div className="nav-dropdown-container">
            <div className="nav-dropdown-title" onClick={toggleOpMenu}>
            <i className="bi bi-journal-check"></i> Operations<i className={isOpMenuOpen ? "bi bi-caret-up-fill" : "bi bi-caret-down-fill"}></i>
            </div>
        </div>
        <div className={`nav-dropdown-content ${isOpMenuOpen ? 'active' : ''}`}>
            <Nav.Link as={Link} to="/operations/my-projects"><i class="bi bi-journal-check"></i> My Projects</Nav.Link>
            {auth.checkRole(auth.role, "Team Lead")
            ?<Nav.Link as={Link} to="/operations/projects"><i class="bi bi-journal-check"></i> All Projects</Nav.Link>
            :""}
        </div>


        <div className="nav-dropdown-container">
            <div className="nav-dropdown-title" onClick={toggleHRMenu}>
            <i className="bi bi-people "></i> Human Resources<i className={isHRMenuOpen ? "bi bi-caret-up-fill" : "bi bi-caret-down-fill"}></i>
            </div>
        </div>
        <div className={`nav-dropdown-content ${isHRMenuOpen ? 'active' : ''}`}>
            <Nav.Link as={Link} to="/hr/summary"><i class ="bi bi-building"></i> HR Summary</Nav.Link>
            <Nav.Link as={Link} to="/hr/my-forms"><i class="bi bi-file-earmark-text"></i> My Forms</Nav.Link>
            {auth.checkRole(auth.role, "Human Resources")
                ? <Nav.Link as={Link} to="/hr/approve-forms"><i class="bi bi-file-earmark-text"></i> Approve Forms</Nav.Link>
                : ""}
            <Nav.Link as={Link} to="/hr/submit-form"><i class ="bi bi-pen"></i> Submit Form</Nav.Link>
            <Nav.Link as={Link} to="/hr/contracts"><i class ="bi bi-newspaper"></i> My Contracts</Nav.Link>
            <Nav.Link as={Link} to="/hr/new-contract"><i class ="bi bi-newspaper"></i> Create Contract</Nav.Link>
            <Nav.Link as={Link} to="/hr/employees"><i class ="bi bi-person"></i> Employees</Nav.Link>
        
        </div>

        <div className="nav-dropdown-container">
            <div className="nav-dropdown-title" onClick={toggleFinanceMenu}>
            <i class="bi bi-graph-up-arrow"></i> Finance <i className={isFinanceMenuOpen ? "bi bi-caret-up-fill" : "bi bi-caret-down-fill"}></i>
            </div>
        </div>
        <div className={`nav-dropdown-content ${isFinanceMenuOpen ? 'active' : ''}`}>
            <Nav.Link as={Link} to="/finance/associates"><i class ="bi bi-wallet"></i> Associates</Nav.Link>
            <Nav.Link as={Link} to="/finance/contacts"><i class ="bi bi-person-bounding-box"></i> Contacts</Nav.Link>
            {/* <Nav.Link as={Link} to="/finance/deals"><i class ="bi bi-hand-thumbs-up"></i> Deals</Nav.Link> */}
        </div>

    </Nav>

    </div>

</div>
    )
}

export default SideBar