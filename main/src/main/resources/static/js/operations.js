//const tableObj = document.getElementById("table");
//const cellCount = tableObj.rows[0].cells.length;
//
//function addRow(){
//    const rowLength = tableObj.rows.length;
//    const newRow = tableObj.insertRow(rowLength);
//
//    for(let i =0; i<cellCount; i++){
//        const newCell = newRow.insertCell(i);
//        newRow.cells[0].innerHTML = rowLength;
//    }
//}
var rowCount = -1;


$(document).ready(function() {
    init();
});

function init() {
    rowCount = $("#project-table tbody tr").length;
    console.log("row count: " + rowCount);
    //New project button
    $("#new-prj-btn").on("click", function() {
        //rowCount = rowCount+1;
        //addRow(rowCount);
        addProjectButtonOnClick();
    });
    console.log("row count:s " + rowCount);
    //Delete button
    $(".del-prj-btn").on("click", function() {
        rowCount = rowCount-1;
        deleteProjectButtonOnClick();
    });
    console.log("row count:d " + rowCount);
    //Load data
    loadTableData();
    initNewProjectForm();
    //Clickable table
    $(".table-clickable tbody tr").on("click", function() {
        tableRowOnClick();
    });
}

function initNewProjectForm() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = today.getDate(); if (day < 10) {day = "0" + day};
    var defaultDueDate = `${year}-${month}-${day}T23:59`;
    $("#newPrjDueDate").val(defaultDueDate);
    console.log("date: " + $("#newPrjDueDate").val());
    $("#newPrjBtnSubmit").on("click", function() {
        pushNewProject();
    });
    $("#newPrjBtnCancel").on("click", function() {
        $("#newPrjForm").hide();
    });
    //$("#newPrjOwner").
}

function loadTableData() {
    var projects = [];
    $.ajax({
        type: "GET",
        dataType:"json",
        contentType : "application/json",
        url: "http://localhost:8079/operations/projects",
        success: function (data) {
            projects = data;
        },
        error: function (error) {
            console.log(error);
        },
        async: false
    });
    console.log(projects);
    projects.forEach(project => loadTableRow(project));
}

function loadTableRow(project) {
    console.log("load row: ");
    var dueDate = new Date(project.dueDate).toLocaleString("en-GB");
    var status = (project.status == 1 ? "open" : "closed");
    $("#project-table tbody").append(`
        <tr>
            <td>${project.name}</td>
            <td>${project.ownerId}</td>
            <td>${status}</td>
            <td>${dueDate}</td>
            <td>${project.description}</td>
            <td>
                <button type="button" class="btn btn-primary del-prj-btn">
                    Delete project
                </button>
            </td>
        </tr>
    `);
        $(".del-prj-btn").on("click", function() {
            $(this).parent().parent().remove();
            //deleteProjectButtonOnClick();
        });
}

function pushNewProject() {
    console.log("Push");
}

function addRow(rowCount) {
    console.log("addrow" + rowCount);
    $("#project-table tbody").append(`
            <tr>
                <td>Project ${rowCount}</td>
                <td>Owner ${rowCount}</td>
                <td>Status ${rowCount}</td>
                <td>Due date ${rowCount}</td>
                <td>People ${rowCount}</td>
                <td>
                    <button type="button" class="btn btn-primary del-prj-btn">
                        Delete project
                    </button>
                </td>
            </tr>
    `);
    $(".del-prj-btn").on("click", function() {
        console.log("deleterow" + rowCount);
        $(this).parent().parent().remove();
        //deleteProjectButtonOnClick();
    });
}

function deleteProjectButtonOnClick() {
    console.log("deleterow" + rowCount);
    var ind = $(this).parent().parent().index();
    $("#project-table tbody tr:eq(" + ind + ")").remove();
}


//Clickable table
function tableRowOnClick() {
//    const tableRows = document.querySelectorAll(".table-clickable tbody tr");
//    for (const tableRow of tableRows) {
//        tableRow.addEventListener("click", function () {
//            window.location.href = this.dataset.href;
//        });
//    }
    console.log(window.location.href);
}

function addProjectButtonOnClick() {
    console.log(document.getElementById('newPrjForm').style.display);
    console.log(document.getElementById('newPrjForm').style.display == "none");
    if (document.getElementById('newPrjForm').style.display == "none") {
        document.getElementById('newPrjForm').style.display = 'block';
    }
}
