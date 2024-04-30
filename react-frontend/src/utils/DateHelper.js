export function getDefaultDueDate() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = today.getDate(); if (day < 10) {day = "0" + day};
    var result = `${year}-${month}-${day}T23:59`;
    return (result);
}