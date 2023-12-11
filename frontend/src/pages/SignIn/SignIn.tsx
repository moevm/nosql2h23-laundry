import "./SignIn.scss";
import {Navigate, useNavigate} from "react-router-dom";
import {Alert, Button, Form, Image} from "react-bootstrap";
import {ExclamationTriangle, Lock, Person} from "react-bootstrap-icons";
import React, {useState} from "react";
import axios, {HttpStatusCode} from "axios"
import {useAppDispatch, useAppSelector} from "../../hooks";
import {setUser} from "../../features/auth/authSlice";
import {useCookies} from "react-cookie";


export function SignIn() {

    const navigate = useNavigate();

    const auth = useAppSelector((state) => state.auth)
    const dispatch = useAppDispatch()
    const [cookies, setCookie] = useCookies(['auth']);

    let [isAlertShown, showAlert] = useState(false);


    let [loginState, setLoginState] = useState("");
    let [passwordState, setPasswordState] = useState("");


    if (!auth.authorized) {
        if (cookies["auth"] !== undefined) {
            dispatch(setUser({
                id: cookies["auth"].id,
                login: cookies["auth"].login,
                name: cookies["auth"].name,
                role: cookies["auth"].role
            }));
            return <Navigate to="/main_page"/>;
        }
    } else {
        return <Navigate to="/main_page"/>;
    }


    function createAlert() {
        showAlert(true);
        setTimeout(() => {
            showAlert(false);
        }, 3000);
    }

    async function formAction(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();

        await axios.post("/api/login", {
            login: loginState,
            password: passwordState
        }, {
            baseURL: "http://localhost:8080"
        }).then((response) => {

            dispatch(setUser({
                id: response.data.id,
                login: loginState,
                name: response.data.name,
                role: response.data.role
            }))

            setCookie("auth", {
                id: response.data.id,
                login: loginState,
                name: response.data.name,
                role: response.data.role
            })

            navigate("/main_page")

        }).catch((error) => {

            if (error.response.status === HttpStatusCode.NotFound) {
                createAlert()
            } else {
                console.log(error)
            }

        })

    }

    return (
        <div id="sign-in-wrapper">
            <div id="big-logo">
                <Image src="/BigLogo.png" alt="LOGO"/>
            </div>
            <div id="data-part">
                <div id="data-wrapper">
                    <h1 className={"display-3"}>Авторизация</h1>

                    <Form className="form-part" onSubmit={formAction}>
                        <Form.Group className="md-3" controlId="login">
                            <Form.Label>
                                <Person/>
                                <div>Логин</div>
                            </Form.Label>
                            <Form.Control type="text" placeholder="Введите логин" name="login"
                                          value={loginState} onChange={(event) => setLoginState(event.target.value)}/>
                        </Form.Group>

                        <Form.Group className="md-3" controlId="password">
                            <Form.Label>
                                <Lock/>
                                <div>Пароль</div>
                            </Form.Label>
                            <Form.Control type="password" placeholder="Введите пароль" name="password"
                                          value={passwordState}
                                          onChange={(event) => setPasswordState(event.target.value)}/>
                        </Form.Group>

                        <div id="control-btns">
                            <Button variant="primary" type="submit">
                                Войти
                            </Button>
                            <Button variant="primary" type="button" onClick={() => {
                                navigate("/sign_up")
                            }}>
                                Регистрация
                            </Button>
                        </div>
                    </Form>

                </div>
                <Alert id="wrong-creds-alert" variant="danger" show={isAlertShown}>
                    <ExclamationTriangle/>
                    <>Неправильный логин или пароль!</>
                </Alert>
            </div>
        </div>
    );
}