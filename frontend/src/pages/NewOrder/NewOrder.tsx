import "./NewOrder.scss";
import Header from "../../components/Header/Header";
import {EmojiSmileUpsideDown, ExclamationTriangle} from "react-bootstrap-icons";
import {Alert, Button, Col, Form, Row} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate, useNavigate} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import axios from "axios";
import {HttpStatusCode} from "axios/index";

export function NewOrder() {

    const navigate = useNavigate();

    let [isAlertShown, setAlertShown] = useState(false);
    let [isAlert1Shown, setAlert1Shown] = useState(false);

    let [branchesArray, setBranchesArray] = useState<string[]>([]);
    let [clientsArray, setClientsArray] = useState<string[]>([]);

    async function loadAll() {
        await axios.get("/api/branch/all_compact", {
            baseURL: "http://localhost:8080"
        }).then(async (response) => {
            setBranchesArray(response.data.branches)
        }).catch((error) => {
            console.log(error)
        })

        await axios.get("/api/client/all_compact", {
            baseURL: "http://localhost:8080"
        }).then(async (response) => {
            setClientsArray(response.data.clients)
        }).catch((error) => {
            console.log(error)
        })
    }

    useEffect(() => {
        loadAll();
    }, []);

    let serviceArray = [
        "Стирка",
        "Сушка",
        "Глажка",
        "Порошок",
        "Отбеливатель"
    ];

    let branchRef = useRef<HTMLSelectElement>(null);
    let clientRef = useRef<HTMLSelectElement>(null);
    let servicesRef = useRef<{
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

    function createAlert(setAlertShown: React.Dispatch<React.SetStateAction<boolean>>) {
        setAlertShown(true);
        setTimeout(() => {setAlertShown(false);}, 3000);
    }

    async function submitCreation() {

        if (auth.role === "CLIENT") {
            if (branchesArray.length === 0) {
                createAlert(setAlert1Shown);
                return;
            }
        } else {
            if (branchesArray.length === 0 || clientsArray.length === 0) {
                createAlert(setAlert1Shown);
                return;
            }
        }


        let anyChecked = false;

        for (const data of servicesRef.current) {
            if (data.checkbox !== null && data.value !== null && data.value.value !== "") {
                anyChecked = true;
                break;
            }
        }

        if (!anyChecked) {
            createAlert(setAlertShown);
            return;
        }

        let serviceName = [
            "WASHING",
            "DRYING",
            "IRONING",
            "POWDER",
            "BLEACH"
        ]

        let services: { type: string, count: number }[] = [];

        for (let i = 0; i < servicesRef.current.length; i++) {
            let data = servicesRef.current[i];

            if (data.checkbox !== null && data.value !== null && data.value.value !== "") {
                services.push({
                    type: serviceName[i],
                    count: parseInt(data.value.value)
                })
            }
        }

        let clientName = auth.name;

        if (auth.role === "SUPERUSER") {
            clientName = clientRef.current!.value;
        }

        // Create new branch
        await axios.post("/api/order/create", {
            branch: branchRef.current!.value,
            clientName: clientName,
            services: services
        },{
            baseURL: "http://localhost:8080"
        }).then(() => {
            navigate("/orders-list");
        }).catch((error) => {
            console.log(error)
        })
    }

    return (
        <div id="new-warehouse-wrapper">
            <Header title="Создание заказа"/>

            <div id="content-wrapper">
                <div id="window">
                    <h1>Введите данные</h1>
                    <Form>
                        <div>
                            <Form.Label>
                                <div>
                                    Филиал
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>
                            <Col>
                                <Form.Select ref={branchRef} disabled={branchesArray.length === 0}>
                                    {branchesArray.map((value) =>
                                        <option key={value} value={value}>{value}</option>
                                    )}
                                </Form.Select>
                            </Col>
                        </div>

                        {
                            auth.role === "SUPERUSER" &&

                            <div>
                                <Form.Label>
                                    <div>
                                        Клиент
                                    </div>
                                    <div className="star">
                                        *
                                    </div>
                                </Form.Label>
                                <Col>
                                    <Form.Select ref={clientRef} disabled={clientsArray.length === 0}>
                                        {clientsArray.map((value) =>
                                            <option key={value} value={value}>{value}</option>
                                        )}
                                    </Form.Select>
                                </Col>
                            </div>
                        }

                        <div id="services">
                            <Form.Label>
                                <div>
                                    Услуги
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>


                            {
                                serviceArray.map((value, index) =>
                                    <div className="list-element" key={value}>
                                        <Form.Check ref={(ref: HTMLInputElement) => {
                                            if (servicesRef.current[index] === undefined) {
                                                servicesRef.current[index] = {
                                                    checkbox: ref,
                                                    value: null
                                                }
                                            } else {
                                                servicesRef.current[index].checkbox = ref
                                            }
                                        }}/>
                                        <div className="name">{value}</div>
                                        <div className="input">
                                            <Form.Control placeholder="Введите количество" ref={(ref: HTMLInputElement) => {
                                                if (servicesRef.current[index] === undefined) {
                                                    servicesRef.current[index] = {
                                                        checkbox: null,
                                                        value: ref
                                                    }
                                                } else {
                                                    servicesRef.current[index].value = ref
                                                }
                                            }}/>
                                        </div>
                                    </div>
                                )
                            }

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
                    <>Минимум одно обязательно поле недоступно! Продолжение невозможно!</>
                </Alert>

            </div>

        </div>
    )

}