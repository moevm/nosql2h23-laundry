import "./NewEmployee.scss";
import Header from "../../components/Header/Header";
import {Button, Col, Form, Row} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate} from "react-router-dom";
import {useRef} from "react";

export function NewEmployee() {
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


    function submitCreation() {

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
                                <div>
                                    <Form.Label>
                                        Логин
                                    </Form.Label>
                                    <Col>
                                        <Form.Control placeholder="Введите логин"
                                                      ref={loginRef}
                                        />
                                    </Col>
                                </div>
                                <div>
                                    <Form.Label>
                                        Пароль
                                    </Form.Label>
                                    <Col>
                                        <Form.Control placeholder="Введите пароль"
                                                      ref={passwordRef}
                                        />
                                    </Col>
                                </div>
                                <div>
                                    <Form.Label>
                                        Повторите пароль
                                    </Form.Label>
                                    <Col>
                                        <Form.Control placeholder="Повторите пароль"
                                                      ref={repeatPasswordRef}
                                        />
                                    </Col>
                                </div>
                                <div>
                                    <Form.Label>
                                        ФИО
                                    </Form.Label>
                                    <Col>
                                        <Form.Control placeholder="Введите ФИО"
                                                      ref={credsRef}
                                        />
                                    </Col>
                                </div>
                                <div>
                                    <Form.Label>
                                        Email
                                    </Form.Label>
                                    <Col>
                                        <Form.Control placeholder="Введите email"
                                                      ref={emailRef}
                                        />
                                    </Col>
                                </div>
                                <div>
                                    <Form.Label>
                                        Роль
                                    </Form.Label>
                                    <Col>
                                        <Form.Select ref={roleRef}>
                                            <option value="admin">Администратор</option>
                                            <option value="director">Директор</option>
                                            <option value="superuser">Владелец бизнеса</option>
                                        </Form.Select>
                                    </Col>
                                </div>
                            </div>
                            <div id="schedule">
                                <Form.Label>
                                    График работы
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
                            <Button>Отмена</Button>
                        </div>
                    </Form>
                </div>
            </div>

        </div>
    )

}