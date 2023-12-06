import "./NewEmployee.scss";
import Header from "../../components/Header/Header";
import {Alert, Button, Col, Form, Row} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate, useNavigate} from "react-router-dom";
import React, {useRef, useState} from "react";
import {ExclamationTriangle} from "react-bootstrap-icons";
import axios, {HttpStatusCode} from "axios";

export function NewEmployee() {
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
    let phoneRef = useRef<HTMLInputElement>(null);
    let roleRef = useRef<HTMLSelectElement>(null);

    let scheduleRef = useRef<{
        checkbox: HTMLInputElement | null,
        start: HTMLSelectElement | null,
        end: HTMLSelectElement | null
    }[]>([]);

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
            (emailRef.current !== null && emailRef.current.value === "") ||
            (phoneRef.current !== null && phoneRef.current.value === "")
        ) {
            createAlert(setAlertShown);
            return;
        }

        let anyChecked = false;

        for (const data of scheduleRef.current) {
            if (data.checkbox !== null && data.checkbox.checked) {
                anyChecked = true;
                break;
            }
        }

        if (!anyChecked) {
            createAlert(setAlertShown);
            return;
        }

        if (passwordRef.current!.value !== repeatPasswordRef.current!.value) {
            createAlert(setAlert1Shown);
            return;
        }

        let schedule: string[] = [];

        for (let i = 0; i < scheduleRef.current.length; i++) {
            let data = scheduleRef.current[i];

            if (data.checkbox !== null && data.start !== null && data.end !== null &&
                data.checkbox.checked) {
                schedule.push(weekArray[i])
                schedule.push(data.start.value)
                schedule.push(data.end.value)
            }
        }

        // Create new branch
        await axios.post("/api/employee/create", {
            login: loginRef.current!.value,
            password: passwordRef.current!.value,
            name: credsRef.current!.value,
            email: emailRef.current!.value,
            phone: phoneRef.current!.value,
            role: roleRef.current!.value,
            schedule: schedule
        }, {
            baseURL: "http://localhost:8080"
        }).then(() => {
            navigate("/employees-list");
        }).catch((error) => {

            if (error.response.status === HttpStatusCode.BadRequest) {
                createAlert(setAlert2Shown)
            } else {
                console.log(error)
            }
        })
    }

    return (
        <div id="new-employee-wrapper">
            <Header title="Новый сотрудник"/>

            <div id="content-wrapper">
                <div id="window">
                    <h1>Введите данные</h1>
                    <Form>
                        <div id="inputs">
                            <div id="else-data">
                                <div className="column">
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
                                    <div>
                                        <Form.Label>
                                            <div>
                                                Телефон
                                            </div>
                                            <div className="star">
                                                *
                                            </div>
                                        </Form.Label>
                                        <Col>
                                            <Form.Control placeholder="Введите телефон"
                                                          ref={phoneRef}
                                            />
                                        </Col>
                                    </div>
                                </div>
                                <div className="column">
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
                                            <Form.Control placeholder="Введите пароль"
                                                          ref={passwordRef}
                                            />
                                        </Col>
                                    </div>
                                    <div>
                                        <Form.Label>
                                            <div>
                                                Повторите пароль
                                            </div>
                                            <div className="star">
                                                *
                                            </div>
                                        </Form.Label>
                                        <Col>
                                            <Form.Control placeholder="Повторите пароль"
                                                          ref={repeatPasswordRef}
                                            />
                                        </Col>
                                    </div>
                                    <div>
                                        <Form.Label>
                                            <div>
                                                Роль
                                            </div>
                                            <div className="star">
                                                *
                                            </div>
                                        </Form.Label>
                                        <Col>
                                            <Form.Select ref={roleRef}>
                                                <option value="ADMIN">Администратор</option>
                                                <option value="DIRECTOR">Директор</option>
                                                <option value="SUPERUSER">Владелец бизнеса</option>
                                            </Form.Select>
                                        </Col>
                                    </div>
                                </div>
                            </div>
                            <div id="schedule">
                                <Form.Label>
                                    <div>
                                        График работы
                                    </div>
                                    <div className="star">
                                        *
                                    </div>
                                </Form.Label>


                                {
                                    weekArray.map((value, index) =>
                                        <div className="list-element" key={value}>
                                            <Form.Check ref={(ref: HTMLInputElement) => {
                                                if (scheduleRef.current[index] === undefined) {
                                                    scheduleRef.current[index] = {
                                                        checkbox: ref,
                                                        start: null,
                                                        end: null
                                                    }
                                                } else {
                                                    scheduleRef.current[index].checkbox = ref
                                                }
                                            }}/>
                                            <div className="name">{value}</div>
                                            <div className="inputs">
                                                <div>
                                                    <div className="input-name">с:</div>
                                                    <Form.Select ref={(ref: HTMLSelectElement) => {
                                                        if (scheduleRef.current[index] === undefined) {
                                                            scheduleRef.current[index] = {
                                                                checkbox: null,
                                                                start: ref,
                                                                end: null
                                                            }
                                                        } else {
                                                            scheduleRef.current[index].start = ref
                                                        }
                                                    }}>
                                                        <option value="8">8:00</option>
                                                        <option value="8.5">8:30</option>
                                                        <option value="9">9:00</option>
                                                        <option value="9.5">9:30</option>
                                                        <option value="10">10:00</option>
                                                        <option value="10.5">10:30</option>
                                                        <option value="11">11:00</option>
                                                    </Form.Select>
                                                </div>
                                                <div>
                                                    <div className="input-name">до:</div>
                                                    <Form.Select ref={(ref: HTMLSelectElement) => {
                                                        if (scheduleRef.current[index] === undefined) {
                                                            scheduleRef.current[index] = {
                                                                checkbox: null,
                                                                start: null,
                                                                end: ref
                                                            }
                                                        } else {
                                                            scheduleRef.current[index].end = ref
                                                        }
                                                    }}>
                                                        <option value="17">17:00</option>
                                                        <option value="17.5">17:30</option>
                                                        <option value="18">18:00</option>
                                                        <option value="18.5">18:30</option>
                                                        <option value="19">19:00</option>
                                                        <option value="19.5">19:30</option>
                                                        <option value="20">20:00</option>
                                                    </Form.Select>
                                                </div>
                                            </div>
                                        </div>
                                    )
                                }

                            </div>
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
                    <>Сотрудник с таким же логином уже существует!</>
                </Alert>
            </div>

        </div>
    )

}