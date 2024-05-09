import React from "react";
import { useEffect, useState } from 'react';
import '../css/sidebar.css';
import AdbIcon from '@mui/icons-material/Adb';
import { Link } from "react-router-dom";
import Nav from 'react-bootstrap/Nav';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Typography from '@mui/material/Typography';
import SubMenu from "./SubMenu";


function SideBar() {
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

        <NavDropdown title={<span><i class="bi bi-people"></i> Human Resources</span>} id="basic-nav-dropdown">
            <NavDropdown.Item as={Link} to="/submit-form">Submit Form</NavDropdown.Item>
            <NavDropdown.Item href="#action/3.2">
            Another action
            </NavDropdown.Item>
            <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
            <NavDropdown.Divider />
            <NavDropdown.Item href="#action/3.4">
            Separated link
            </NavDropdown.Item>
        </NavDropdown>

        <Nav.Link as={Link} to="/operations"><i class="bi bi-journal-check"></i> Operations</Nav.Link>

        <NavDropdown title={<span><i class="bi bi-coin"></i> Finances</span>} id="finances">
            <NavDropdown.Item href="#action/3.1">Activities</NavDropdown.Item>
            <NavDropdown.Item href="#action/3.2">
            Contacts
            </NavDropdown.Item>
            <NavDropdown.Item href="#action/3.3">Accounts</NavDropdown.Item>
            <NavDropdown.Item href="#action/3.4">Contacts</NavDropdown.Item>
            <NavDropdown.Item href="#action/3.5">Deals</NavDropdown.Item>
            <NavDropdown.Item href="#action/3.6">Leads</NavDropdown.Item>
        </NavDropdown>

    </Nav>

    </div>

</div>
    )
}

export default SideBar