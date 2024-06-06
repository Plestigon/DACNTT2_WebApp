import React, { useState, useContext, createContext } from "react";
import { createSearchParams, useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext();

function Authentication({ children }) {
	const [user, setUser] = useState(null);
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
            if (result.statusCode === 200) {
				setToken(result.data);
				localStorage.setItem("token", result.data);
				navigate("/");
				return;
            }
        })
        .catch (e => {
            console.log("ERROR_login: " + e);
        })
	}

	function logOut() {
		setUser(null);
		setToken('');
		localStorage.removeItem("token");
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

	return <AuthContext.Provider value={{user, token, login, logOut, isExpired, checkToken}}>{children}</AuthContext.Provider>;
};

export function useAuthentication() {
	return useContext(AuthContext);
};

export default Authentication;