import "./NewClient.scss";
import Header from "../../components/Header/Header";
import {EmojiSmileUpsideDown} from "react-bootstrap-icons";
import {Button, Col, Form, Row} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate} from "react-router-dom";
import {useRef} from "react";

export function NewClient() {
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


    function submitCreation() {

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
                                Повторить пароль
                            </Form.Label>
                            <Col>
                                <Form.Control placeholder="Повторить пароль"
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