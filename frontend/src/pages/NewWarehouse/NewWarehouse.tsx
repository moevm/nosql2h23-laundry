import "./NewWarehouse.scss";
import Header from "../../components/Header/Header";
import {EmojiSmileUpsideDown} from "react-bootstrap-icons";
import {Button, Col, Form, Row} from "react-bootstrap";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Navigate} from "react-router-dom";
import {useRef} from "react";

export function NewWarehouse() {
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


    function submitCreation() {
        if (addressRef.current !== null) {
            console.log("Address: " + addressRef.current.value)
        }

        if (branchRef.current !== null) {
            console.log("Branch: " + branchRef.current.value)
        }

        console.log("Schedule:");

        for (const data of scheduleRef.current) {
            if (data.checkbox?.checked) {
                console.log("Start: " + data.start?.value + "; End: " + data.end?.value);
            }
        }
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
                                Адрес
                            </Form.Label>
                            <Col>
                                <Form.Control placeholder="Введите адрес склада"
                                    ref={addressRef}
                                />
                            </Col>
                        </div>
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