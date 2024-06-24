import React, { useEffect, useState } from 'react';
import { Button, Card } from "react-bootstrap";
import {
  MDBCol,
  MDBContainer,
  MDBRow,
  MDBCardBody,
  MDBCardImage,
} from 'mdb-react-ui-kit';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import "@fortawesome/fontawesome-free/css/all.min.css";
import TopBar from '../TopBar';
import SideBar from '../SideBar';
import { useAuthentication } from '../system/Authentication';
import { dateFormat } from '../../utils/DateHelper';
import NewPassword from './NewPassword';

export default function ProfilePage() {
  const auth = useAuthentication();
  const [user, setUser] = useState({});
  const [newPassModalShow, setNewPassModalShow] = useState(false);

  useEffect(() => {
    document.title = 'Profile - TDTU EMS';
  }, []);

  useEffect(() => {
    function fetchUser() {
      fetch(process.env.REACT_APP_API_URI + "/auth/user?email=" + auth.email + "&token=" + auth.token, {
        method: "GET",
        headers: { "ngrok-skip-browser-warning": "true" }
      })
        .then(result => result.json())
        .then((result) => {
          // console.log(result.data);
          if (result.statusCode === 200) {
            setUser(result.data);
          }
          else {
            console.log(result.message);
          }
        })
        .catch(e => {
          console.log("ERROR_fetchUser: " + e);
        })
    }
    fetchUser();
  }, [])

  return (
    <div>
      <SideBar />
      <TopBar />
      <div class="content">
        <MDBContainer className="pt-5 pb-2">
          <div class="row d-flex justify-content-center">
            <div class="col col-lg-4 center">
              <Card className="mb-5" style={{ height: "300px" }}>
                <Card className="text-center d-flex justify-content-center" style={{ height: "300px" }}>
                  <div class="my-3">
                    <img src={require("../../assets/profile-user.png")} alt="Avatar goes here" className="rounded-circle"
                      style={{ width: '150px', align: "center" }} />
                  </div>
                  <p className="mb-1 h3">{user.name}</p>
                </Card>
              </Card>
            </div>
            <div class="col col-lg-6 center">
              <Card style={{ minHeight: "300px" }}>
                <Card style={{ minHeight: "300px" }}>
                  <div class="row align-items-center" style={{ height: "52px" }}>
                    <MDBCol sm="3" style={{ textAlign: "end" }}>
                      <div >ID</div>
                    </MDBCol>
                    <MDBCol sm="9">
                      <div  >{user.id}</div>
                    </MDBCol>
                  </div>

                  <hr style={{ marginTop: "5px", marginBottom: "5px" }} />
                  <div class="row align-items-center" style={{ height: "52px" }}>
                    <MDBCol sm="3" style={{ textAlign: "end" }}>
                      <div >Email</div>
                    </MDBCol>
                    <MDBCol sm="9">
                      <div >{user.email}</div>
                    </MDBCol>
                  </div>

                  <hr style={{ marginTop: "5px", marginBottom: "5px" }} />
                  <div class="row align-items-center" style={{ height: "52px" }}>
                    <MDBCol sm="3" style={{ textAlign: "end" }}>
                      <div >Department</div>
                    </MDBCol>
                    <MDBCol sm="9">
                      <div >{user.departmentName}</div>
                    </MDBCol>
                  </div>

                  <hr style={{ marginTop: "5px", marginBottom: "5px" }} />
                  <div class="row align-items-center" style={{ height: "52px" }}>
                    <MDBCol sm="3" style={{ textAlign: "end" }}>
                      <div >Role</div>
                    </MDBCol>
                    <MDBCol sm="9">
                      <div  >{user.roleName}</div>
                    </MDBCol>
                  </div>

                  <hr style={{ marginTop: "5px", marginBottom: "5px" }} />
                  <div class="row align-items-center" style={{ minHeight: "52px" }}>
                    <MDBCol sm="3" style={{ textAlign: "end" }}>
                      <div >Join Date</div>
                    </MDBCol>
                    <MDBCol sm="9">
                      <div >{dateFormat(user.joinDate)}</div>
                    </MDBCol>
                  </div>
                </Card>
              </Card>
            </div>
          </div>
        </MDBContainer>
        <div class="row d-flex justify-content-end me-5">
          <button class="btn btn-secondary" style={{width:"150px", height:"50px", fontSize:"12px", paddingLeft:"0", paddingRight:"0"}}
          onClick={() => setNewPassModalShow(true)}>Change Password</button>
          <button class="btn btn-danger ms-4" style={{width:"150px", height:"50px", fontSize:"14px"}}
          onClick={() => auth.logOut()}><i class="bi bi-box-arrow-right me-2"></i>Log Out</button>
        </div>
        <NewPassword show={newPassModalShow} onHide={() => setNewPassModalShow(false)} userId={auth.id} token={auth.token} />
      </div>
    </div>
  );
}