$(document).ready(function() {
    init();
});

function init() {
    rowCount = $("#project-table tbody tr").length;
    //New project button
    $("#newPrjBtn").on("click", function() {
        $("#newPrjFormContainer").show();
    });
    //Load data
    loadTableData();
    //Clickable table
    $(".table-clickable tbody tr").on("click", function() {
        tableRowOnClick();
    });
    initNewProjectForm();
}

function initNewProjectForm() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = today.getDate(); if (day < 10) {day = "0" + day};
    var defaultDueDate = `${year}-${month}-${day}T23:59`;
    $("#newPrjDueDate").val(defaultDueDate);
    console.log("date: " + $("#newPrjDueDate").val());
    //New project submit button
    $("#newPrjBtnSubmit").on("click", function() {
        pushNewProject();
    });
    //New project cancel button
    $("#newPrjBtnCancel").on("click", function() {
        $("#newPrjFormContainer").hide();
    });
    //Init owner select list
    var employees = [];
    $.ajax({
        type: "GET",
        dataType:"json",
        contentType : "application/json",
        url: "http://localhost:8080/operations/employees",
        success: function (data) {
            employees = data;
        },
        error: function (error) {
            console.log(error);
        },
        async: false
    });
    console.log(employees);
    $("#newPrjOwner").empty();
    employees.forEach(function (owner) {
        $("#newPrjOwner").append(`
            <option value="${owner.id}">${owner.name}</option>
        `);
    });
    $("#newPrjOwner").val(employees[0].id);
    //Form submit
    $("#newPrjForm").submit(function(event) {
        event.preventDefault();
        pushNewProject();
    })
}

function loadTableData() {
    var projects = [];
    $.ajax({
        type: "GET",
        dataType:"json",
        contentType : "application/json",
        url: "http://localhost:8080/operations/projects",
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
    //Delete button
    $(".delete-prj-btn").on("click", function() {
        var id = $(this).data("project-id");
        deleteProjectButtonOnClick(id);
    });
}

function loadTableRow(project) {
    console.log("load row: ");
    var dueDate = new Date(project.dueDate).toLocaleString("en-GB");
    var status = (project.status == 1 ? "open" : "closed");
    //Table row format
    $("#project-table tbody").append(`
        <tr>
            <td>${project.name}</td>
            <td>${project.ownerName}</td>
            <td>${status}</td>
            <td>${dueDate}</td>
            <td>${project.description}</td>
            <td>
                <button type="button" class="btn btn-primary bi bi-trash delete-prj-btn" data-project-id="${project.id}"
                style="float: right">
                </button>
            </td>
        </tr>
    `);
}

function pushNewProject() {
    var projectDto = {
        name: $("#newPrjName").val(),
        description: $("#newPrjDescription").val(),
        ownerId: $("#newPrjOwner").val(),
        dueDate: $("#newPrjDueDate").val()
    };
    console.log(JSON.stringify(projectDto));
    $.ajax({
        type: "POST",
        contentType : "application/json",
        url: "http://localhost:8080/operations/project",
        data: JSON.stringify(projectDto),
        success: function (result) {
            console.log(result);
            alert("New project created successfully!");
            location.reload();
        },
        error: function (error) {
            console.log(error);
        },
        async: false
    });
}

function deleteProjectButtonOnClick(id) {
    if (confirm("Are you sure you want to delete project with id \"" + id + "\"?")) {
        $.ajax({
            type: "DELETE",
            contentType : "application/json",
            url: "http://localhost:8080/operations/project?id=" + id,
            success: function (result) {
                console.log(result);
                alert("Project deleted successfully!");
                location.reload();
            },
            error: function (error) {
                console.log(error);
            },
            async: false
        });
    }
}


//Clickable table
function tableRowOnClick() {
//    const tableRows = document.querySelectorAll(".table-clickable tbody tr");
//    for (const tableRow of tableRows) {
//        tableRow.addEventListener("click", function () {
//            window.location.href = this.dataset.href;
//        });
//    }
//    console.log(window.location.href);
}

// Set add project button to active on click
