export function getDefaultDueDate() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = today.getDate(); if (day < 10) {day = "0" + day};
    var result = `${year}-${month}-${day}T23:59`;
    return (result);
}

export function handleDate(input) {
    var date = new Date(input);
    var year = date.getFullYear();
    var month = date.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = date.getDate(); if (day < 10) {day = "0" + day};
    var hour = date.getHours(); if (hour < 10) {hour = "0" + hour};
    var minute = date.getMinutes(); if (minute < 10) {minute = "0" + minute};
    var result = `${year}-${month}-${day}T${hour}:${minute}`;
    return (result);
}