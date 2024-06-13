import React, { useState, useContext, createContext } from "react";
import { createSearchParams, useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { success, error, loading, dismiss } from "../../utils/Notify";

const AuthContext = createContext();

function Authentication({ children }) {
	const [id, setId] = useState(localStorage.getItem("id") || null);
	const [name, setName] = useState(localStorage.getItem("name") || null);
	const [email, setEmail] = useState(localStorage.getItem("email") || null);
	const [role, setRole] = useState(localStorage.getItem("role") || null);
	const [token, setToken] = useState(localStorage.getItem("token") || '');
	const navigate = useNavigate();

	function login(email, password) {
		// if (email === 'aaa@gmail.com' && password === 'pass') {
		// 	setUser({name: "minh", email: "aaa"});
		// 	setToken("andlwids");
		// 	localStorage.setItem("token", "andlwids");
		// 	navigate("/");
		// 	return;
		// }
		// else {
		// 	console.log("login failed");
		// }
        const toastId = loading("Processing...");
		fetch("http://localhost:8080/auth/login",{
            method:"POST",
            body: JSON.stringify({
                "email": email,
                "password": password
            }),
            headers: { "Content-type": "application/json; charset=UTF-8" }
        })
		.then(result=>result.json())
        .then((result)=>{
            dismiss(toastId);
            if (result.statusCode === 200) {
				setToken(result.data);
				localStorage.setItem("token", result.data);

				const userData = jwtDecode(result.data);
				setId(userData.id);
				localStorage.setItem("id", userData.id);
				setName(userData.name);
				localStorage.setItem("name", userData.name);
				setEmail(userData.sub);
				localStorage.setItem("email", userData.sub);
				setRole(userData.role);
				localStorage.setItem("role", userData.role);
				
				navigate("/?loggedIn=true");
            }
			else {
				error(result.message);
			}
        })
        .catch (e => {
            dismiss(toastId);
            console.log("ERROR_login: " + e);
			error(e);
        })
	}

	function logOut() {
		setToken('');
		setId(null);
		setName(null);
		setEmail(null);
		setRole(null);
		localStorage.removeItem("token");
		localStorage.removeItem("id");
		localStorage.removeItem("name");
		localStorage.removeItem("email");
		localStorage.removeItem("role");
		navigate({
			pathname: "/login",
			search: createSearchParams({loggedOut: true}).toString()
		});
	}

	function isExpired(token) {
		let decodedToken = jwtDecode(token);
		// console.log("Decoded Token", decodedToken);
		let currentDate = new Date();

		if (decodedToken.exp * 1000 < currentDate.getTime()) {
			//Expired
			return true
		}
		return false;
	}

	function checkToken() {
		let token = localStorage.getItem("token");
		if (token && !isExpired(token)) {
			return true;
		}
		navigate({
			pathname: "/login",
			search: createSearchParams({tokenNotFoundOrExpired: true}).toString()
		});
		return false;
	}

	return <AuthContext.Provider value={{id, name, email, role, token, login, logOut, isExpired, checkToken}}>{children}</AuthContext.Provider>;
};

export function useAuthentication() {
	return useContext(AuthContext);
};

export default Authentication;