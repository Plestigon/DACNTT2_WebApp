import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import AdbIcon from '@mui/icons-material/Adb';
import { Link, useNavigate } from "react-router-dom";
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { useAuthentication } from './system/Authentication';
import NewPassword from './profile/NewPassword';
import Notify from '../utils/Notify';


const pages = ['Products', 'Pricing', 'Blog'];
const settings = ['Profile', 'Account', 'Dashboard'];

function NavigationBar() {
  const auth = useAuthentication();
  const navigate = useNavigate();
  const [newPassModalShow, setNewPassModalShow] = useState(false);

  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  function handleLogOutClick() {
    auth.logOut();
  }

  // const redirect = (path) => {
  //   navigate(path);
  // }

  return (
    <Navbar bg="primary" data-bs-theme="dark" position="static">
      
      <Notify/>

      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
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

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >
              {pages.map((page) => (
                <MenuItem key={page} onClick={handleCloseNavMenu}>
                  <Typography textAlign="center">{page}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <AdbIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            LOGO
          </Typography>
          <Nav className="me-auto">
            {/* {pages.map((page) => (
              <Button
                key={page}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                {page}
              </Button>
            ))} */}
            {/* <Button
                key="Home"
                onClick={() => redirect("/prod")}
                sx={{ my: 2, color: 'white', display: 'block' }}
            >
                Home
            </Button> */}
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

            <Nav.Link as={Link} to="/operations/projects"><i class="bi bi-journal-check"></i> Operations</Nav.Link>

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

          {/* Profile */}
          <div class="me-2">
            <div class="" style={{fontSize: 15}}>{auth.name} {auth.email ? <span class="fst-italic" style={{fontSize: 12}}>({auth.email})</span> : ''}</div>
            <div class="fw-bold text-end" style={{fontSize: 10}}>{auth.role}</div>
          </div>
          
          <Box sx={{ flexGrow: 0 }}>
            <Tooltip title="Logged in">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar alt="User" src={require('../assets/profile-user.png')} />
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {/* {settings.map((setting) => (
                <MenuItem key={setting} onClick={handleCloseUserMenu}>
                  <Typography textAlign="center">{setting}</Typography>
                </MenuItem>
              ))} */}
              <MenuItem onClick={() => navigate("/profile")}>
                <Typography textAlign="center">Profile</Typography>
              </MenuItem>
              <MenuItem onClick={() => {setNewPassModalShow(true);handleCloseUserMenu()}}>
                <Typography textAlign="center">Change Password</Typography>
              </MenuItem>
              <MenuItem onClick={handleLogOutClick}>
                <div class="fw-bold">
                  <i class="fa fa-sign-out me-2" aria-hidden="true" style={{color:'#dc3545'}}></i><span class="text-danger">Log Out</span>
                </div>
              </MenuItem>
            </Menu>
          </Box>
          {/* End Profile */}
          <NewPassword show={newPassModalShow} onHide={() => setNewPassModalShow(false)} userId={auth.id} token={auth.token} />
          
        </Toolbar>
      </Container>
    </Navbar>
  );
}
export default NavigationBar;
