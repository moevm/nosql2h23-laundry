import "./OrderPage.scss"
import Header from "../../components/Header/Header";
import React, {useEffect, useState} from "react";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate, useParams} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {Button} from "react-bootstrap";

export function OrderPage() {

    const navigate = useNavigate();
    let {orderId} = useParams();

    useEffect(() => {

        setClient({
            id: "sdfsdfgs",
            fullName: "Иванова Дарья Павловна"
        });

        setStatus("NEW")

        setBranch({
            id: "sdfgdghsdfh",
            address: "Москва, улица Рождественка, 20/8с16"
        });

        setPrice("1000")

        let product: { type: string, amount: number }[] = [];
        product.push({
            type: "WASHING",
            amount: 20
        })
        product.push({
            type: "DRYING",
            amount: 3
        })
        product.push({
            type: "IRONING",
            amount: 467356
        })
        product.push({
            type: "POWDER",
            amount: 20
        })
        product.push({
            type: "BLEACH",
            amount: 20
        })

        setProduct(product)

        setCreationDate("11.10.2023 11:12:53")
        setEditDate("11.10.2023 11:12:53")

    }, [orderId]);

    let statusTranslate = new Map();
    statusTranslate.set("NEW", {
        view: "Новый",
        class: "new-order-text"
    })
    statusTranslate.set("ACTIVE", {
        view: "Активный",
        class: "active-order-text"
    })
    statusTranslate.set("READY", {
        view: "Выполненный",
        class: "ready-order-text"
    })
    statusTranslate.set("COMPLETED", {
        view: "Завершенный",
        class: "completed-order-text"
    })
    statusTranslate.set("CANCELED", {
        view: "Отмененный",
        class: "canceled-order-text"
    })

    let serviceNameTranslate = new Map();
    serviceNameTranslate.set("WASHING", "Стирка");
    serviceNameTranslate.set("DRYING", "Сушка");
    serviceNameTranslate.set("IRONING", "Глажка");
    serviceNameTranslate.set("POWDER", "Порошок");
    serviceNameTranslate.set("BLEACH", "Отбеливатель");

    const [client, setClient] = useState<{ id: string, fullName: string }>({
        id: "",
        fullName: ""
    });
    const [status, setStatus] = useState("");
    const [branch, setBranch] = useState<{ id: string, address: string }>({
        id: "",
        address: ""
    });
    const [price, setPrice] = useState("")
    const [product, setProduct] = useState<{ type: string, amount: number }[]>([]);

    const [creationDate, setCreationDate] = useState("");
    const [editDate, setEditDate] = useState("");


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

    function cancelOrder() {

    }

    function approveOrder() {

    }

    function readyOrder() {

    }

    function completeOrder() {

    }

    return (
        <div id="order-page-wrapper">
            <Header title="Заказ"/>

            <div id="content-wrapper">
                <div id="window">
                    <div id="top">
                        <div id="data">
                            {
                                auth.role !== "CLIENT" &&
                                <div className="data-part-lin">
                                    <div className="bold">Клиент:</div>
                                    <Link to={"/user-page/" + client.id}>{client.fullName}</Link>
                                </div>
                            }
                            <div className="data-part-lin pop">
                                <div className="bold">Статус:</div>
                                {status !== "" &&
                                    <div
                                        className={"pop-text " + statusTranslate.get(status).class}>{statusTranslate.get(status).view}</div>
                                }

                            </div>
                            <div className="data-part-lin">
                                <div className="bold">Адрес Филиала:</div>
                                {
                                    auth.role === "CLIENT" &&
                                    <div>{branch.address}</div>
                                }
                                {
                                    auth.role !== "CLIENT" &&
                                    <Link to={"/branch-page/" + branch.id}>{branch.address}</Link>
                                }
                            </div>
                            <div className="data-part-lin">
                                <div className="bold">Стоимость:</div>
                                <div>{price + " руб."}</div>
                            </div>
                            <div id="big-part">
                                <div className="data-part-hor-main">
                                    <div className="bold">Услуги:</div>
                                    <ul>
                                        {product.map((element) => <li key={element.type}>
                                            {serviceNameTranslate.get(element.type) + ": " + element.amount + " шт."}
                                        </li>)}
                                    </ul>
                                </div>
                            </div>
                        </div>
                        {auth.role !== "CLIENT" &&
                            <div id="control-btns">
                                {(status !== "COMPLETED" && status !== "CANCELED") &&
                                    <Button variant="danger" onClick={() => cancelOrder()}>Отменить</Button>
                                }
                                {status === "NEW" &&
                                    <Button onClick={() => approveOrder()}>Подтвердить</Button>
                                }
                                {status === "ACTIVE" &&
                                    <Button onClick={() => readyOrder()}>Выполнить</Button>
                                }
                                {status === "READY" &&
                                    <Button onClick={() => completeOrder()}>Выдать</Button>
                                }
                            </div>
                        }

                    </div>
                    <div id="bottom">
                        <div className="date">
                            <div>Дата создания:</div>
                            <div>{creationDate}</div>
                        </div>
                        <div className="date">
                            <div>Дата редактирования:</div>
                            <div>{editDate}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}