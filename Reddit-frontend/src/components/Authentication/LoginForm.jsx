import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { useFormErrors } from "../../hooks/useFormErrors";
import FormHeader from "../Common/FormHeader";
import authenticationService from "../../services/authentication";
import FormErrorMessage from "../Common/FormErrorMessage";

const LoginForm = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();
  const { errors, setBackendErrors } = useFormErrors();
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const credentials = { username, password };

    try {
      const data = await authenticationService.login(credentials);

      login(data);

      setUsername("");
      setPassword("");

      navigate("/");
    } catch (error) {
      setBackendErrors(error);
    }
  };

  const handleClose = () => {
    navigate("/");
  };

  return (
    <div>
      <button
        className="background-btn background-blur"
        onClick={handleClose}
      ></button>

      <div className="active-form h-2/3">
        <FormHeader name="" handleClose={handleClose} />

        <div className="flex flex-col items-center">
          <h1 className="m-6 text-3xl font-bold">Log In</h1>

          <form className="auth-form" onSubmit={handleLogin}>
            <div className="m-5">
              <input
                className="auth-input focus-item"
                type="text"
                value={username}
                name="username"
                placeholder="Username"
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>

            <div className="m-5">
              <input
                className="auth-input focus-item"
                type="password"
                value={password}
                name="password"
                placeholder="Password"
                onChange={(e) => setPassword(e.target.value)}
              />

              <FormErrorMessage>{errors.message}</FormErrorMessage>
            </div>

            <div className="m-5">
              <button className="auth-btn focus-item" type="submit">
                Log in
              </button>
            </div>
          </form>

          <p className="m-3 font-medium">
            New to Reddit?{" "}
            <Link to="/signup" className="text-blue-400 focus-item">
              Sign Up
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
