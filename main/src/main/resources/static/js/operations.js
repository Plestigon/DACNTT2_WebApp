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
    console.log("ready!");
    init();
});

function init() {
    rowCount = $("#project-table tbody tr").length;
    console.log("row count: " + rowCount);
    $("#new-prj-btn").on("click", function() {
        rowCount = rowCount+1;
        addRow(rowCount);
    });
    console.log("row count:s " + rowCount);
    $(".del-prj-btn").on("click", function() {
        rowCount = rowCount-1;
        deletePrj();
    });
    console.log("row count:d " + rowCount);
    loadData();
}

function loadData() {
    console.log("start load data");
    var projects = [];
    $.ajax({
        type: "GET",
        dataType:"json",
        contentType : "application/json",
        url: "http://localhost:8079/operations/projects",
        success: function (data) {
            console.log("data: ", data);
            projects = data;
        },
        error: function (error) {
            console.log(error);
        },
        async: false
    });
    console.log("end load data");
    console.log(projects);
    projects.forEach(project => loadRow(project));
}

function loadRow(project) {
    console.log("load row: ");
    $("#project-table tbody").append(`
        <tr>
            <td>${project.name}</td>
            <td>${project.ownerId}</td>
            <td>${project.status}</td>
            <td>${project.dueDate}</td>
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
            //deletePrj();
        });
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
        //deletePrj();
    });
}

function deletePrj() {
    console.log("deleterow" + rowCount);
    var ind = $(this).parent().parent().index();
    $("#project-table tbody tr:eq(" + ind + ")").remove();
}