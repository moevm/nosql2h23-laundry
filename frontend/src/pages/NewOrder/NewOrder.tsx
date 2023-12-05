import "./NewOrder.scss";
import Header from "../../components/Header/Header";
import {EmojiSmileUpsideDown} from "react-bootstrap-icons";
import {Button, Col, Form, Row} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate} from "react-router-dom";
import {useRef} from "react";

export function NewOrder() {
    let serviceArray = [
        "Стирка",
        "Сушка",
        "Глажка",
        "Порошок",
        "Отбеливатель"
    ];

    let branchRef = useRef<HTMLSelectElement>(null);
    let clientRef = useRef<HTMLSelectElement>(null);
    let scheduleRef = useRef<{
        checkbox: HTMLInputElement | null,
        value: HTMLInputElement | null,
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

    if (auth.role !== "SUPERUSER" && auth.role !== "CLIENT") {
        return <Navigate to="/not-found" replace={true}/>
    }


    function submitCreation() {

    }

    return (
        <div id="new-warehouse-wrapper">
            <Header title="Создание склада"/>

            <div id="content-wrapper">
                <div id="window">
                    <h1>Введите данные</h1>
                    <Form>
                        <div>
                            <Form.Label>
                                Филиал
                            </Form.Label>
                            <Col>
                                <Form.Select ref={branchRef}>
                                    <option value="123">KEK123</option>
                                    <option value="1">KEK1</option>
                                    <option value="2">KEK2</option>
                                    <option value="3">KEK3</option>
                                </Form.Select>
                            </Col>
                        </div>

                        {
                            auth.role === "SUPERUSER" &&

                            <div>
                                <Form.Label>
                                    Клиент
                                </Form.Label>
                                <Col>
                                    <Form.Select ref={clientRef}>
                                        <option value="123">KEK123</option>
                                        <option value="1">KEK1</option>
                                        <option value="2">KEK2</option>
                                        <option value="3">KEK3</option>
                                    </Form.Select>
                                </Col>
                            </div>
                        }

                        <div id="services">
                            <Form.Label>
                                Услуги
                            </Form.Label>


                            {
                                serviceArray.map((value, index) =>
                                    <div className="list-element" key={value}>
                                        <Form.Check ref={(ref: HTMLInputElement) => {
                                            if (scheduleRef.current[index] === undefined) {
                                                scheduleRef.current[index] = {
                                                    checkbox: ref,
                                                    value: null
                                                }
                                            } else {
                                                scheduleRef.current[index].checkbox = ref
                                            }
                                        }}/>
                                        <div className="name">{value}</div>
                                        <div className="input">
                                            <Form.Control placeholder="Введите количество" ref={(ref: HTMLInputElement) => {
                                                if (scheduleRef.current[index] === undefined) {
                                                    scheduleRef.current[index] = {
                                                        checkbox: null,
                                                        value: ref
                                                    }
                                                } else {
                                                    scheduleRef.current[index].value = ref
                                                }
                                            }}/>
                                        </div>
                                    </div>
                                )
                            }

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