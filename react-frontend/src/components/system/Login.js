import React, {useState} from 'react';
import { useAuthentication } from './Authentication';

function Login() {
	const auth = useAuthentication();
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [emailError, setEmailError] = React.useState('');
	const [passwordError, setPasswordError] = React.useState('');

	function validate() {
		setEmailError('');
		setPasswordError('');
		if (email === '') {
			setEmailError("Please enter email");
			return false;
		}
		if (!/^[\w-.]+@([\w-]+.)+[\w-]{2,4}$/.test(email.toLowerCase())) {
			setEmailError("Please enter a valid email");
			return false;
		}
		if (password === '') {
			setPasswordError("Please enter password");
			return false;
		}
		return true;
	}

	function handleSubmit(e) {
		e.preventDefault();
		if (validate()) {
			auth.login(email, password);
		}
	}

	return (
		<section class="vh-100">
			<div class="container-fluid" style={{height:'90%'}}>
				<div class="row d-flex justify-content-center align-items-center h-100">
					<div class="col-md-9 col-lg-6 col-xl-5">
						<img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
							class="img-fluid" alt=""></img>
					</div>
					<div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
						<form>
							{/* <!-- Email input --> */}
							<div class="">
								<label class="form-label" for="form3Example3">Email address</label>
								<input type="email" id="form3Example3" class="form-control form-control-lg"
									placeholder="Enter email address"  onChange={e => setEmail(e.target.value)}/>
								<span class="text-danger">{emailError}</span>
							</div>
							<hr></hr>

							{/* <!-- Password input --> */}
							<div class="">
								<label class="form-label" for="form3Example4">Password</label>
								<input type="password" id="form3Example4" class="form-control form-control-lg"
									placeholder="Enter password" onChange={e => setPassword(e.target.value)}/>
								<span class="text-danger">{passwordError}</span>
							</div>
							<hr></hr>

							<div class="row mt-4 pt-2">
								<button class="btn btn-primary btn-lg" onClick={e => handleSubmit(e)}>Login</button>
							</div>

						</form>
					</div>
				</div>
			</div>
			<div
				class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-dark navbar-dark fixed-bottom">
				{/* <!-- Copyright --> */}
				<div class="text-white mb-3 mb-md-0">
					Copyright © 2024. All rights reserved.
				</div>
				{/* <!-- Copyright --> */}
			</div>
		</section>
	)
}
export default Login;