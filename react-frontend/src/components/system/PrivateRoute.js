import { Navigate } from "react-router-dom";
import { useAuthentication } from "./Authentication";

function PrivateRoute({ children }) {
	const auth = useAuthentication();
		if (auth.token === "") {
			return <Navigate to="/login"/>;
		}
		if (auth.isExpired(auth.token)) {
			return <Navigate to="/login?tokenExpired=true"/>;
		}
	return children;
}

export default PrivateRoute;