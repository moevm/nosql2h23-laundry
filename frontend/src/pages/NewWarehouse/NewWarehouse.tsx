import "./NewWarehouse.scss";
import Header from "../../components/Header/Header";
import {EmojiSmileUpsideDown, ExclamationTriangle} from "react-bootstrap-icons";
import {Alert, Button, Col, Form, Row} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate, useNavigate} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import axios, {HttpStatusCode} from "axios";

export function NewWarehouse() {

    const navigate = useNavigate();

    let [isAlertShown, setAlertShown] = useState(false);
    let [isAlert1Shown, setAlert1Shown] = useState(false);
    let [isNoBranchAlertShown, setNoBranchAlertShown] = useState(false);

    let [branchesArray, setBranchesArray] = useState<string[]>([]);

    async function loadAll() {
        await axios.get("/api/branch/get_branches_without_warehouse", {
            baseURL: "http://localhost:8080"
        }).then(async (response) => {
            setBranchesArray(response.data.names)
        }).catch((error) => {
            console.log(error)
        })
    }

    useEffect(() => {
        loadAll();
    }, []);

    let weekArray = [
        "Понедельник",
        "Вторник",
        "Среда",
        "Четверг",
        "Пятница",
        "Суббота",
    ];

    let addressRef = useRef<HTMLInputElement>(null);
    let branchRef = useRef<HTMLSelectElement>(null);
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
        setTimeout(() => {setAlertShown(false);}, 3000);
    }

    async function submitCreation() {

        if (branchesArray.length === 0) {
            createAlert(setNoBranchAlertShown);
            return;
        }

        if (addressRef.current !== null && addressRef.current.value === "") {
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
        await axios.post("/api/warehouse/create", {
            address: addressRef.current!.value,
            branchAddress: branchRef.current!.value,
            schedule: schedule
        },{
            baseURL: "http://localhost:8080"
        }).then(() => {
            navigate("/warehouses-list");
        }).catch((error) => {

            if (error.response.status === HttpStatusCode.BadRequest) {
                createAlert(setAlert1Shown)
            } else {
                console.log(error)
            }
        })
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
                                <div>
                                    Адрес
                                </div>
                                <div className="star">
                                    *
                                </div>
                            </Form.Label>
                            <Col>
                                <Form.Control placeholder="Введите адрес склада"
                                    ref={addressRef}
                                />
                            </Col>
                        </div>
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
                                                    <option value="8:00">8:00</option>
                                                    <option value="8:30">8:30</option>
                                                    <option value="9:00">9:00</option>
                                                    <option value="9:30">9:30</option>
                                                    <option value="10:00">10:00</option>
                                                    <option value="10:30">10:30</option>
                                                    <option value="11:00">11:00</option>
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
                                                    <option value="17:00">17:00</option>
                                                    <option value="17:30">17:30</option>
                                                    <option value="18:00">18:00</option>
                                                    <option value="18:30">18:30</option>
                                                    <option value="19:00">19:00</option>
                                                    <option value="19:30">19:30</option>
                                                    <option value="20:00">20:00</option>
                                                </Form.Select>
                                            </div>
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
                <Alert id="not-all-alert" variant="danger" show={isAlertShown}>
                    <ExclamationTriangle/>
                    <>Вы не заполнили все обязательные поля!</>
                </Alert>
                <Alert id="not-all-alert" variant="danger" show={isAlert1Shown}>
                    <ExclamationTriangle/>
                    <>Склад с данным адресом уже зарегистрирован в системе!</>
                </Alert>
                <Alert id="not-all-alert" variant="danger" show={isNoBranchAlertShown}>
                    <ExclamationTriangle/>
                    <>Не найдено свободных складов. Продолжение невозможно!</>
                </Alert>
            </div>

        </div>
    )

}