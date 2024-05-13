import React from 'react';
import {NotificationContainer, NotificationManager} from 'react-notifications';
import 'react-notifications/lib/notifications.css';

export function info(msg) {
    NotificationManager.info(msg);
}
export function success(msg) {
    NotificationManager.success(msg);
}
export function error(msg) {
    NotificationManager.error(msg, '', 10000);
}
export function warning(msg) {
    NotificationManager.warning(msg, '', 10000);
}
function Notify() {
    return (
        <NotificationContainer/>
    )
}
  
export default Notify;