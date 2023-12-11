import "./RemoveProducts.scss"
import {Navigate, useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import axios from "axios";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import Header from "../../components/Header/Header";
import {Alert, Button, Form} from "react-bootstrap";
import {ExclamationTriangle} from "react-bootstrap-icons";

export function RemoveProducts() {
    const navigate = useNavigate();
    let {warehouseId} = useParams();

    let [isAlertShown, setAlertShown] = useState(false);
    let [isAlert1Shown, setAlert1Shown] = useState(false);

    let [availableProducts, setAvailableProducts] = useState<{ type: string, amount: number }[]>([]);

    async function loadData(warehouseId: string | undefined) {
        await axios.get("/api/warehouse/get", {
            baseURL: "http://localhost:8080",
            params: {
                id: warehouseId
            }
        }).then((response) => {
            setAvailableProducts(response.data.products)
        }).catch((error) => {
            console.log(error)
        })
    }

    useEffect(() => {
        loadData(warehouseId);
    }, [warehouseId]);

    const productNameTranslate = new Map();
    productNameTranslate.set("WASHING_POWDER", "Порошок");
    productNameTranslate.set("BLEACH", "Отбеливатель");
    productNameTranslate.set("WASHING_MACHINE", "Стиральная машина");
    productNameTranslate.set("DRYER", "Сушильная машина");
    productNameTranslate.set("IRONING_BOARD", "Гладильная доска");
    productNameTranslate.set("IRON", "Утюг");

    const productsTypes = [
        "WASHING_POWDER",
        "BLEACH",
        "WASHING_MACHINE",
        "DRYER",
        "IRONING_BOARD",
        "IRON"
    ];

    const productsTypesRu = [
        "Порошок",
        "Отбеливатель",
        "Стиральная машина",
        "Сушильная машина",
        "Гладильная доска",
        "Утюг"
    ];

    let productsRef = useRef<{
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

    if (auth.role !== "SUPERUSER" && auth.role !== "DIRECTOR") {
        return <Navigate to="/not-found" replace={true}/>
    }

    function createAlert(setAlertShown: React.Dispatch<React.SetStateAction<boolean>>) {
        setAlertShown(true);
        setTimeout(() => {
            setAlertShown(false);
        }, 3000);
    }

    async function submitCreation() {

        let anyChecked = false;

        for (const data of productsRef.current) {
            if (data.checkbox !== null && data.value !== null && data.value.value !== "") {
                anyChecked = true;
                break;
            }
        }

        if (!anyChecked) {
            createAlert(setAlertShown);
            return;
        }

        let products: { name: string, count: number }[] = [];

        for (let i = 0; i < productsRef.current.length; i++) {
            let data = productsRef.current[i];

            if (data.checkbox !== null && data.value !== null && data.value.value !== "") {

                let count = parseInt(data.value.value);

                let available = 0;

                for (const availableProduct of availableProducts) {
                    if (availableProduct.type === productsTypes[i]) {
                        available = availableProduct.amount;
                        break;
                    }
                }

                if (count > available || count < 0) {
                    createAlert(setAlert1Shown);
                    return;
                }

                products.push({
                    name: productsTypes[i],
                    count: parseInt(data.value.value)
                })
            }
        }


        await axios.post("/api/warehouse/remove_product", {
            warehouse: warehouseId,
            products: products,
        }, {
            baseURL: "http://localhost:8080"
        }).then(() => {
            navigate(-1);
        }).catch((error) => {
            console.log(error)
        })
    }

    return (
        <div id="remove-products-wrapper">
            <Header title="Списать товары"/>

            <div id="content-wrapper">
                <div id="window">
                    <h1>Введите данные</h1>
                    <Form>
                        <div id="pop">
                            <div id="available-products">
                                <Form.Label>
                                    <div>
                                        Доступные товары:
                                    </div>
                                </Form.Label>

                                <ul>
                                    {
                                        productsTypes.map((value, index) => {

                                                let amount = 0;

                                                for (const availableProduct of availableProducts) {
                                                    if (availableProduct.type === value) {
                                                        amount = availableProduct.amount;
                                                        break;
                                                    }
                                                }

                                                return <li key={value}>
                                                    {productNameTranslate.get(value) + ": " + amount + " шт."}
                                                </li>
                                            }
                                        )
                                    }
                                </ul>

                            </div>
                            <div id="products">
                                <Form.Label>
                                    <div>
                                        Товары
                                    </div>
                                    <div className="star">
                                        *
                                    </div>
                                </Form.Label>


                                {
                                    productsTypesRu.map((value, index) =>
                                        <div className="list-element" key={value}>
                                            <Form.Check ref={(ref: HTMLInputElement) => {
                                                if (productsRef.current[index] === undefined) {
                                                    productsRef.current[index] = {
                                                        checkbox: ref,
                                                        value: null
                                                    }
                                                } else {
                                                    productsRef.current[index].checkbox = ref
                                                }
                                            }}/>
                                            <div className="name">{value}</div>
                                            <div className="input">
                                                <Form.Control placeholder="Введите количество"
                                                              ref={(ref: HTMLInputElement) => {
                                                                  if (productsRef.current[index] === undefined) {
                                                                      productsRef.current[index] = {
                                                                          checkbox: null,
                                                                          value: ref
                                                                      }
                                                                  } else {
                                                                      productsRef.current[index].value = ref
                                                                  }
                                                              }}/>
                                            </div>
                                        </div>
                                    )
                                }

                            </div>
                        </div>

                        <div className="buttons">
                            <Button onClick={() => submitCreation()}>Списать</Button>
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
                    <>Проверьте, что вы не списываете больше товаров, чем есть на складе!</>
                </Alert>

            </div>

        </div>
    )
}