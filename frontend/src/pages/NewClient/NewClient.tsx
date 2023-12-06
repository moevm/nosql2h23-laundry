import "./NewClient.scss";
import Header from "../../components/Header/Header";
import {Alert, Button, Col, Form} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate, useNavigate} from "react-router-dom";
import React, {useRef, useState} from "react";
import axios, {HttpStatusCode} from "axios";
import {ExclamationTriangle} from "react-bootstrap-icons";

export function NewClient() {
    const navigate = useNavigate();

    let [isAlertShown, setAlertShown] = useState(false);
    let [isAlert1Shown, setAlert1Shown] = useState(false);
    let [isAlert2Shown, setAlert2Shown] = useState(false);


    let weekArray = [
        "Понедельник",
        "Вторник",
        "Среда",
        "Четверг",
        "Пятница",
        "Суббота",
    ];

    let loginRef = useRef<HTMLInputElement>(null);
    let passwordRef = useRef<HTMLInputElement>(null);
    let repeatPasswordRef = useRef<HTMLInputElement>(null);
    let credsRef = useRef<HTMLInputElement>(null);
    let emailRef = useRef<HTMLInputElement>(null);


    const auth = useAppSelector((state) => state.auth)
    const dispatch = useAppDispatch()
    const [cookies] = useCookies(['auth']);

    if (!auth.authorized) {
        if (cookies["auth"] !== undefined) {
            dispatch(setUser({
                id: cookies["auth"].id,
                login: cookies["auth"].login,
                name: cookies["auth"].name,
                role: cookies["auth"].role
            }))
        } else {
            return <Navigate to="/sign_in" replace={true}/>;
        }
    }

    if (auth.role !== "SUPERUSER") {
        return <Navigate to="/not-found" replace={true}/>
    }


    function createAlert(setAlertShown: React.Dispatch<React.SetStateAction<boolean>>) {
        setAlertShown(true);
        setTimeout(() => {
            setAlertShown(false);
        }, 3000);
    }

    async function submitCreation() {

        if ((loginRef.current !== null && loginRef.current.value === "") ||
            (passwordRef.current !== null && passwordRef.current.value === "") ||
            (repeatPasswordRef.current !== null && repeatPasswordRef.current.value === "") ||
            (credsRef.current !== null && credsRef.current.value === "") ||
            (emailRef.current !== null && emailRef.current.value === "")
        ) {
            createAlert(setAlertShown);
            return;
        }

        if (passwordRef.current!.value !== repeatPasswordRef.current!.value) {
            createAlert(setAlert1Shown);
            return;
        }

        // Create new branch
        await axios.post("/api/client/create", {
            login: loginRef.current!.value,
            password: passwordRef.current!.value,
            name: credsRef.current!.value,
            email: emailRef.current!.value
        }, {
            baseURL: "http://localhost:8080"
        }).then(() => {
            navigate("/clients-list");
        }).catch((error) => {

            if (error.response.status === HttpStatusCode.BadRequest) {
                createAlert(setAlert2Shown)
            } else {
                console.log(error)
            }
        })
    }

    return (
        <div id="new-client-wrapper">
            <Header title="Новый клиент"/>

            <div id="content-wrapper">
                <div id="window">
                    <h1>Введите данные</h1>
                    <Form>
                        <div>
                            <Form.Label>
                                <div>
                                    Логин
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>
                            <Col>
                                <Form.Control placeholder="Введите логин"
                                              ref={loginRef}
                                />
                            </Col>
                        </div>

                        <div>
                            <Form.Label>
                                <div>
                                    Пароль
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>
                            <Col>
                                <Form.Control type="password" placeholder="Введите пароль"
                                              ref={passwordRef}
                                />
                            </Col>
                        </div>

                        <div>
                            <Form.Label>
                                <div>
                                    Повторить пароль
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>
                            <Col>
                                <Form.Control type="password" placeholder="Повторить пароль"
                                              ref={repeatPasswordRef}
                                />
                            </Col>
                        </div>

                        <div>
                            <Form.Label>
                                <div>
                                    ФИО
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>
                            <Col>
                                <Form.Control placeholder="Введите ФИО"
                                              ref={credsRef}
                                />
                            </Col>
                        </div>

                        <div>
                            <Form.Label>
                                <div>
                                    Email
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>
                            <Col>
                                <Form.Control placeholder="Введите email"
                                              ref={emailRef}
                                />
                            </Col>
                        </div>


                        <div className="buttons">
                            <Button onClick={() => submitCreation()}>Создать</Button>
                            <Button onClick={() => navigate(-1)}>Отмена</Button>
                        </div>
                    </Form>
                </div>
                <Alert className="custom-alert" variant="danger" show={isAlertShown}>
                    <ExclamationTriangle/>
                    <>Вы не заполнили все обязательные поля!</>
                </Alert>
                <Alert className="custom-alert" variant="danger" show={isAlert1Shown}>
                    <ExclamationTriangle/>
                    <>Пароли не совпадают!</>
                </Alert>
                <Alert className="custom-alert" variant="danger" show={isAlert2Shown}>
                    <ExclamationTriangle/>
                    <>Клиент с таким же логином уже существует!</>
                </Alert>
            </div>

        </div>
    )

}