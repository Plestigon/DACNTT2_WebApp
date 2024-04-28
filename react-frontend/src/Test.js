import React, {useEffect, useState} from 'react';
// import {makeStyles} from '@material-ui/core/styles';
// import TextField from '@material-ui/core/TextField';
// import {Container, Paper, Button} from '@material-ui/core';

// const useStyles = makeStyles((theme)=>({
//     root: {
//         '& > *': {
//             margin: theme.spacing(1)
//         }
//     }
// }));

export default function GetData() {
    // const classes = useStyles();
    const[data,setdata]=useState([])
    useEffect(()=>{
        fetch("http://localhost:8080/operations/projects",{
            method:"GET"
        })
        .then(res=>res.json())
        .then((result)=>{
            setdata(result);
        })
    }, [])
    return (
        data.map(project=>(
            <p key={project.id}>Name: {project.name}</p>
        ))
    )
}