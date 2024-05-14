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

function SideBar() {
    const [isHRMenuOpen, setHRMenuOpen] = useState(false);
    const [isFinanceMenuOpen, setFinanceMenuOpen] = useState(false);
    
    const toggleHRMenu = () => {
        setHRMenuOpen(!isHRMenuOpen);
    };
    
    const toggleFinanceMenu = () => {
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

        <Nav.Link as={Link} to="/operations"><i class="bi bi-journal-check"></i> Operations</Nav.Link>


        <div className="nav-dropdown-container">
            <div className="nav-dropdown-title" onClick={toggleHRMenu}>
            <i className="bi bi-people "></i> Human Resources <i className={isHRMenuOpen ? "bi bi-caret-up-fill" : "bi bi-caret-down-fill"}></i>
            </div>
        </div>
        <div className={`nav-dropdown-content ${isHRMenuOpen ? 'active' : ''}`}>
            <Nav.Link as={Link} to="/hr/submit-form">Submit Form</Nav.Link>
            <Nav.Link as={Link} to="/hr/summary">HR Summary</Nav.Link>
            <Nav.Link as={Link} to="/hr/contracts">My Contracts</Nav.Link>
        </div>

        <div className="nav-dropdown-container">
            <div className="nav-dropdown-title" onClick={toggleFinanceMenu}>
            <i className="bi bi-coin"></i> Finance <i className={isFinanceMenuOpen ? "bi bi-caret-up-fill" : "bi bi-caret-down-fill"}></i>
            </div>
        </div>
        <div className={`nav-dropdown-content ${isFinanceMenuOpen ? 'active' : ''}`}>
            <Nav.Link as={Link} to="/hr/submit-form">Accounts</Nav.Link>
            <Nav.Link as={Link} to="/hr/submit-form">Contacts</Nav.Link>
            <Nav.Link as={Link} to="/hr/submit-form">Deals</Nav.Link>
            <Nav.Link as={Link} to="/hr/submit-form">Leads</Nav.Link>
        </div>

    </Nav>

    </div>

</div>
    )
}

export default SideBar