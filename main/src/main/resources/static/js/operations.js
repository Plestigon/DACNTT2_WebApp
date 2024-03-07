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
$(document).ready(function() {
    console.log("ready!");
    init();
});

function init() {
    var rowCount = $("#project-table tbody tr").length;
    console.log("row count: " + rowCount);
    $("#new-prj-btn").on("click", function() {
        addRow();
    });
    loadData();
}

function loadData() {
    $.ajax({
        type: "GET",
        dataType:"json",
        contentType : "application/json",
        url: "projects/get",
        success: function (data, status, xhr) {
            console.log("data: ", data);
        }
    });
}

function addRow() {
    console.log("addrow");
    $("#project-table tbody").append(`
            <tr>
                <td>Project</td>
                <td>Owner</td>
                <td>Status</td>
                <td>Due date</td>
                <td>People</td>
            </tr>
    `);
}