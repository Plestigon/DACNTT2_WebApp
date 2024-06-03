import React, { useState, useContext, createContext } from "react";
import { createSearchParams, useNavigate } from "react-router-dom";

const AuthContext = createContext();

function Authentication({ children }) {
	const [user, setUser] = useState(null);
	const [token, setToken] = useState(localStorage.getItem("token") || '');
	const navigate = useNavigate();

	function login(email, password) {
		if (email === 'aaa@gmail.com' && password === 'pass') {
			setUser({name: "minh", email: "aaa"});
			setToken("andlwids");
			localStorage.setItem("token", "andlwids");
			navigate("/");
			return;
		}
		else {
			console.log("login failed");
		}
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

	return <AuthContext.Provider value={{user, token, login, logOut: logOut}}>{children}</AuthContext.Provider>;
};

export function useAuthentication() {
	return useContext(AuthContext);
};

export default Authentication;