export function getDefaultStartDate() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = today.getDate(); if (day < 10) {day = "0" + day};
    var result = `${year}-${month}-${day+1}`;
    return (result);
}

export function getDefaultEndDate() {
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = today.getDate(); if (day < 10) {day = "0" + day};
    var result = `${year}-${month}-${day+2}`;
    return (result);
}

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

export function dateFormat(input) {
    var date = new Date(input);
    var year = date.getFullYear();
    var month = date.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = date.getDate(); if (day < 10) {day = "0" + day};
    var result = `${day}/${month}/${year}`;
    return (result);
}

export function dateTimeFormat(input) {
    var date = new Date(input);
    var year = date.getFullYear();
    var month = date.getMonth()+1; if (month < 10) {month = "0" + month};
    var day = date.getDate(); if (day < 10) {day = "0" + day};
    var hour = date.getHours(); if (hour < 10) {hour = "0" + hour};
    var minute = date.getMinutes(); if (minute < 10) {minute = "0" + minute};
    var result = `${day}/${month}/${year} ${hour}:${minute}`;
    return (result);
}

export function getDaysUntil(input) {
    var date = new Date(input);
    var today = new Date();
    var diff = date - today;
    var days = Math.floor(diff / 1000 / 60 / 60 / 24);
    return days;
}

export function getHoursUntil(input) {
    var date = new Date(input);
    var today = new Date();
    var diff = date - today;
    var days = Math.floor(diff / 1000 / 60 / 60 / 24);
    if (diff >= 0) {
        diff -= days * 1000 * 60 * 60 * 24;
    }
    else {
        diff += days * 1000 * 60 * 60 * 24;
    }
    var hours = Math.floor(diff / 1000 / 60 / 60);
    return hours;
}

export function getDaysSince(input) {
    var date = new Date(input);
    var today = new Date();
    var diff = today - date;
    var days = Math.floor(diff / 1000 / 60 / 60 / 24);
    return days;
}

export function getHoursSince(input) {
    var date = new Date(input);
    var today = new Date();
    var diff = today - date;
    var days = Math.floor(diff / 1000 / 60 / 60 / 24);
    if (diff >= 0) {
        diff -= days * 1000 * 60 * 60 * 24;
    }
    else {
        diff += days * 1000 * 60 * 60 * 24;
    }
    var hours = Math.floor(diff / 1000 / 60 / 60);
    return hours;
}

export function getTimeUntil(input) {
    var date = new Date(input);
    var today = new Date();
    var diff = date - today;
    var days;
    var hours;
    if (diff >= 0) {
        days = Math.floor(diff / 1000 / 60 / 60 / 24);
        if (days >= 1) {
            return (<div> {days} day{days > 1 ? "s" : ""}</div>);
        }
        else {
            diff -= days * 1000 * 60 * 60 * 24;
            hours = Math.floor(diff / 1000 / 60 / 60);
            return (<div> {hours} hours{hours > 1 ? "s" : ""}</div>);
        }
    }
    else {
        days = Math.floor(diff / 1000 / 60 / 60 / 24);
        if (days <= -1) {
            return (<div class='text-danger'> {-days} day{days < 1 ? "s" : ""} overdue</div>);
        }
        else {
            diff += days * 1000 * 60 * 60 * 24;
            hours = Math.floor(diff / 1000 / 60 / 60);
            return (<div class='text-danger'> {-hours} hour{hours < 1 ? "s" : ""} overdue</div>);
        }
    }
}

export function getTimeSince(input) {
    var date = new Date(input);
    var today = new Date();
    var diff = today - date;
    var days;
    var hours;
    if (diff >= 0) {
        days = Math.floor(diff / 1000 / 60 / 60 / 24);
        if (days >= 1) {
            return (<div> {days} day{days > 1 ? "s" : ""} ago</div>);
        }
        else {
            diff -= days * 1000 * 60 * 60 * 24;
            hours = Math.floor(diff / 1000 / 60 / 60);
            return (<div> {hours} hours{hours > 1 ? "s" : ""} ago</div>);
        }
    }
    else {
        days = Math.floor(diff / 1000 / 60 / 60 / 24);
        if (days <= -1) {
            return (<div class='text-danger'>Invalid date</div>);
        }
        else {
            diff += days * 1000 * 60 * 60 * 24;
            hours = Math.floor(diff / 1000 / 60 / 60);
            return (<div class='text-danger'>Invalid date</div>);
        }
    }
}