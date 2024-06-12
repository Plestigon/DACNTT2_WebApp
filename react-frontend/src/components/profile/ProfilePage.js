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
          <SideBar/>
          <TopBar/>
      <section class="content">
      <MDBContainer className="py-5">
        <div class="row align-items-center center">
          <div class="col"></div>
          <div class="col col-lg-4 center" >
            <Card className="mb-5">
              <Card className="text-center">
                <Card
                  src=""
                  alt="Avatar goes here"
                  className="rounded-circle"
                  style={{ width: '150px', align: "center" }}
                  fluid> Avatar goes here</Card>
                <p className=" mb-1" >Role Goes here</p>
              </Card>
            </Card>
          </div>
          <div class="col col-lg-6 center">
            <Card>
              <Card>
                <div class="row align-items-center">
                  <MDBCol sm="3">
                    <div >Full Name</div>
                  </MDBCol>
                  <MDBCol sm="9">
                    <div  >Johnathan Doe</div>
                  </MDBCol>
                </div>
                
                <hr />
                <div class="row align-items-center">
                  <MDBCol sm="3">
                    <div >Email</div>
                  </MDBCol>
                  <MDBCol sm="9">
                    <div >example@example.com</div>
                  </MDBCol>
                </div>

                <hr />
                <div class="row align-items-center">
                  <MDBCol sm="3">
                    <div >Phone</div>
                  </MDBCol>
                  <MDBCol sm="9">
                    <div >(097) 234-5678</div>
                  </MDBCol>
                </div>

                <hr/>
                <div class="row align-items-center">
                  <MDBCol sm="3">
                    <div >Joined Date</div>
                  </MDBCol>
                  <MDBCol sm="9">
                    <div  >Insert date here</div>
                  </MDBCol>
                </div>

                <hr />
                <div class="row align-items-center">
                  <MDBCol sm="3">
                    <div >Address</div>
                  </MDBCol>
                  <MDBCol sm="9">
                    <div >Bay Area, San Francisco, CA</div>
                  </MDBCol>
                </div>
              </Card>
            </Card>
          </div>
        </div>
      </MDBContainer>
    </section>
    </div>
  );
}