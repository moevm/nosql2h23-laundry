import "./SignUp.scss";
import {Alert, Button, Form, Image} from "react-bootstrap";
import {Navigate, useNavigate} from "react-router-dom";
import {setUser} from "../../features/auth/authSlice";
import React, {useRef, useState} from "react";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import axios, {HttpStatusCode} from "axios";
import {ExclamationTriangle} from "react-bootstrap-icons";

export function SignUp () {

    // TODO: Add redux data about user role and token
    // TODO: Alert on validation error
    // TODO: Add validation checks (login already exists, password and password_repeat are the same)

    const navigate = useNavigate();

    const auth = useAppSelector((state) => state.auth)
    const dispatch = useAppDispatch()
    const [cookies, setCookie] = useCookies(['auth']);

    let [isNotAllAlertShown, showNotAllAlert] = useState(false);
    let [isLoginTakenAlertShown, showLoginTakenAlert] = useState(false);
    let [isPasswordsDontMatchAlertShown, showPasswordsDontMatchAlert] = useState(false);

    let loginRef = useRef<HTMLInputElement>(null);
    let passwordRef = useRef<HTMLInputElement>(null);
    let passwordRepeatRef = useRef<HTMLInputElement>(null);
    let fullNameRef = useRef<HTMLInputElement>(null);
    let emailRef = useRef<HTMLInputElement>(null);

    function createAlert(showAlert: (value: (((prevState: boolean) => boolean) | boolean)) => void) {
        showAlert(true);
        setTimeout(() => {showAlert(false);}, 3000);
    }

    async function submit() {

        if (loginRef.current === null || passwordRef.current === null || passwordRepeatRef.current === null || fullNameRef.current === null || emailRef.current === null) {
            createAlert(showNotAllAlert);
            return;
        } else {
            if (passwordRef.current.value !== passwordRepeatRef.current.value) {
                createAlert(showPasswordsDontMatchAlert);
                return;
            }
        }

        let login = loginRef.current.value;
        let name = fullNameRef.current.value;

        // Protect password???

        await axios.post("/api/sign_up", {
            login: loginRef.current.value,
            password: passwordRef.current.value,
            fullName: fullNameRef.current.value,
            email: emailRef.current.value
        },{
            baseURL: "http://localhost:8080"
        }).then((response) => {

            dispatch(setUser({
                id: response.data.id,
                login: login,
                name: name,
                role: "CLIENT"
            }))

            setCookie("auth", {
                id: parseInt(response.data.id),
                login: login,
                name: name,
                role: "CLIENT"
            })

            navigate("/main_page")

        }).catch((error) => {

            if (error.response.status === HttpStatusCode.BadRequest) {
                createAlert(showLoginTakenAlert);
            } else {
                console.log(error)
            }

        })
    }

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

    return (
        <div id="sing-up-wrapper">
            <div id="big-logo">
                <Image src="/BigLogo.png" alt="LOGO"/>
            </div>
            <div id="data-part">
                <div id="data-wrapper">
                    <h1 className={"display-3"}>Регистрация</h1>
                    <Form className="form-part">
                        <Form.Group className="md-3" controlId="login">
                            <Form.Label>
                                <div>Логин</div>
                            </Form.Label>
                            <Form.Control type="text" placeholder="Введите логин" ref={loginRef}/>
                        </Form.Group>

                        <Form.Group className="md-3" controlId="password">
                            <Form.Label>
                                <div>Пароль</div>
                            </Form.Label>
                            <Form.Control type="password" placeholder="Введите пароль" ref={passwordRef}/>
                        </Form.Group>

                        <Form.Group className="md-3" controlId="password_repeat">
                            <Form.Label>
                                <div>Повторить пароль</div>
                            </Form.Label>
                            <Form.Control type="password" placeholder="Повторите пароль" ref={passwordRepeatRef}/>
                        </Form.Group>

                        <Form.Group className="md-3" controlId="full_name">
                            <Form.Label>
                                <div>ФИО</div>
                            </Form.Label>
                            <Form.Control type="text" placeholder="Введите ФИО" ref={fullNameRef}/>
                        </Form.Group>

                        <Form.Group className="md-3" controlId="email">
                            <Form.Label>
                                <div>Email</div>
                            </Form.Label>
                            <Form.Control type="email" placeholder="Введите Email" ref={emailRef}/>
                        </Form.Group>

                        <div id="control-btns">
                            <Button variant="primary" onClick={() => submit()}>
                                Создать Аккант
                            </Button>
                            <Button variant="primary" onClick={() => {navigate("/sign_in")}}>
                                Отмена
                            </Button>
                        </div>
                    </Form>
                </div>
                <Alert id="not-all-alert" variant="danger" show={isNotAllAlertShown}>
                    <ExclamationTriangle/>
                    <>Не все поля заполнены!</>
                </Alert>
                <Alert id="not-all-alert" variant="danger" show={isLoginTakenAlertShown}>
                    <ExclamationTriangle/>
                    <>Данный логин занят!</>
                </Alert>
                <Alert id="not-all-alert" variant="danger" show={isPasswordsDontMatchAlertShown}>
                    <ExclamationTriangle/>
                    <>Пароли не совпадают!</>
                </Alert>
            </div>
        </div>
    );
}