import { Navigate } from "react-router-dom";
import { useAuthentication } from "./Authentication";

function PrivateRoute({ children }) {
	const auth = useAuthentication();
		if (auth.token === '') {
			return <Navigate to="/login" />;
		}
	return children;
}

export default PrivateRoute;