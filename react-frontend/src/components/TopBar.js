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
import "../css/topbar.css";
import { Icon } from '@mui/material';
import { useAuthentication } from './system/Authentication';
import NewPassword from './profile/NewPassword';
import Notify from '../utils/Notify';


const pages = ['Products', 'Pricing', 'Blog'];
const settings = ['Profile', 'Change Password', 'Dashboard'];

function TopBar() {
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


  return (
    <Navbar data-bs-theme="dark" position="static" className="topbar" >
      
      <Notify/>

      <Container maxWidth="xl">
        <div class="d-flex justify-content-end">
        <Toolbar disableGutters>
          <IconButton style={{marginRight:"10px"}}><i class="fa-solid fa-bell"></i></IconButton>

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
        </div>
      </Container>
    </Navbar>
  );
}
export default TopBar;
