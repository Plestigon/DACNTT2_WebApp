import React from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export function info(msg) {
    toast.info(msg, {
        position: "bottom-right",
        theme: "colored",
        autoClose: 2000
    });
}
export function success(msg) {
    toast.success(msg, {
        position: "bottom-right",
        theme: "colored",
        autoClose: 2000
    });
}
export function error(msg) {
    toast.error(msg, {
        position: "bottom-right",
        theme: "colored",
        autoClose: false
    });
}
export function warning(msg) {
    toast.warning(msg, {
        position: "bottom-right",
        theme: "colored",
        autoClose: 2000
    });
}
export function loading(msg) {
    return toast.loading(msg);
}
export function doneLoading(toastId, msg, type) {
    toast.update(toastId, {
        render: msg,
        type: type,
        isLoading: false
    });
}
export function dismiss(toastId) {
    toast.dismiss(toastId);
}

function Notify() {
    return (
        <ToastContainer/>
    )
}
  
export default Notify;