import * as React from "react";
import '../css/home.css';
import { Link } from "react-router-dom";
import ResponsiveAppBar from './AppBar'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Select from 'react-select'
import DatePicker from "react-datepicker";
import { useState } from "react";
import "react-datepicker/dist/react-datepicker.css";



  
const options = [
    { label: "In Progress", value: 1 },
    { label: "Stopped", value: 2 },
    { label: "Cancelled", value: 3 },
    { label: "Finished", value: 4 },
    { label: "Not Started", value: 5 }
  ];
  
const style = {
      control: base => ({
        ...base,
        border: 0,
        // This line disable the blue border
        boxShadow: "none"
      })
};
  
const OperationsInfo = () => {
    const [startDate, setStartDate] = useState(new Date());
    return (
<div>
<ResponsiveAppBar/>

    <Container>
    <Row>
    <label>
        Project Name: <input name="ProjName" defaultValue="Project Name" />
    </label>
    </Row>
    <br></br>

        <Row>
            <Col>
                <label>
                Owner:<input name="Owner" defaultValue="Project Owner"></input>
                </label>
            </Col>
            <Col>
            Status:<Select options={options} styles={style} />
            </Col>
            <Col>
            Due Date:
            <DatePicker selected={startDate} onChange={(date) => setStartDate(date)} />
            </Col>
        </Row>
        <Row>
            Description:
            <textarea name="descriptions" rows={4} cols={40} />
        </Row>
    </Container>
</div>
);
};
 
export default OperationsInfo;