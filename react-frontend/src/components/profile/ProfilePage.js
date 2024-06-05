import React from 'react';
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
import NavigationBar from '../NavigationBar';
import Notify from '../../utils/Notify';

export default function ProfilePage() {
  return (
    <div>
        <Notify/>
        <SideBar/>
        <TopBar/>
    <section>
      <MDBContainer className="py-5">
<<<<<<< Updated upstream
        <MDBRow style={{marginLeft: '10%'}}>
          <MDBCol lg=""  >
            <Card className="mb-7">
=======
        <MDBRow>
          <MDBCol lg="4">
            <Card className="mb-9">
>>>>>>> Stashed changes
              <Card className="text-center">
                <Card
                  src=""
                  alt="Avatar goes here"
                  className="rounded-circle"
                  class="shadow-none"
                  style={{ width: '150px' }}
                  fluid> Avatar goes here</Card>
                <p className="text-muted mb-1" >Role Goes here</p>
              </Card>
            </Card>

          </MDBCol>
          <MDBCol lg="8">
            <Card className="mb-4">
              <Card>
                <MDBRow>
                  <MDBCol sm="3">
                    <Card class="shadow-none">Full Name</Card>
                  </MDBCol>
                  <MDBCol sm="9">
<<<<<<< Updated upstream
                    <Card className="text-muted" class="shadow-none text-muted">Johnathan Doe</Card>
=======
                    <Card className="text-muted" class="shadow-none">Johnathan Doe</Card>
>>>>>>> Stashed changes
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <Card class="shadow-none">Email</Card>
                  </MDBCol>
                  <MDBCol sm="9">
                    <Card className="text-muted" class="shadow-none">example@example.com</Card>
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <Card class="shadow-none">Phone</Card>
                  </MDBCol>
                  <MDBCol sm="9">
                    <Card className="text-muted" class="shadow-none">(097) 234-5678</Card>
                  </MDBCol>
                </MDBRow>
                <hr/>
                <MDBRow>
                  <MDBCol sm="3">
                    <Card class="shadow-none">Joined Date</Card>
                  </MDBCol>
                  <MDBCol sm="9">
                    <Card className="text-muted" class="shadow-none">Insert date here</Card>
                  </MDBCol>
                </MDBRow>

                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <Card class="shadow-none">Address</Card>
                  </MDBCol>
                  <MDBCol sm="9">
                    <Card className="text-muted" class="shadow-none">Bay Area, San Francisco, CA</Card>
                  </MDBCol>
                </MDBRow>
              </Card>
            </Card>

            <MDBRow>
              <MDBCol md="6">
              </MDBCol>

              <MDBCol md="6">
              </MDBCol>
            </MDBRow>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </section>
    </div>
  );
}