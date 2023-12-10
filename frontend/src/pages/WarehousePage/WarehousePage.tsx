import "./WarehousePage.scss"
import Header from "../../components/Header/Header";
import React, {useEffect, useState} from "react";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate, useParams} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {Button} from "react-bootstrap";

export function WarehousePage() {

    const navigate = useNavigate();
    let {warehouseId} = useParams();

    useEffect(() => {
        console.log(warehouseId);

        setAddress("Москва, Кривоколенный переулок, 5с2")
        setBranch({
            id: "redtnstdeatdnserdb",
            address: "Москва, улица Рождественка, 20/8с16"
        })

        let schedule = []

        schedule.push({
            name: "Понедельник",
            start: "08:30",
            end: "20:00"
        })
        schedule.push({
            name: "Вторник",
            start: "08:30",
            end: "20:00"
        })
        schedule.push({
            name: "Среда",
            start: "08:30",
            end: "20:00"
        })
        schedule.push({
            name: "Четверг",
            start: "08:30",
            end: "20:00"
        })
        schedule.push({
            name: "Пятница",
            start: "08:30",
            end: "20:00"
        })

        setSchedule(schedule);

        let products = [];

        products.push({
            type: productNameTranslate["WASHING_POWDER"],
            amount: 54
        });

        products.push({
            type: productNameTranslate["BLEACH"],
            amount: 54
        });

        products.push({
            type: productNameTranslate["WASHING_MACHINE"],
            amount: 54
        });

        products.push({
            type: productNameTranslate["DRYER"],
            amount: 54
        });

        products.push({
            type: productNameTranslate["IRONING_BOARD"],
            amount: 54
        });

        products.push({
            type: productNameTranslate["IRON"],
            amount: 231354
        });

        setProducts(products);

        setCreationDate("11.10.2023 11:12:53")
        setEditDate("11.10.2023 11:12:53")

    }, [warehouseId]);


    const productNameTranslate = {
        "WASHING_POWDER": "Порошок",
        "BLEACH": "Отбеливатель",
        "WASHING_MACHINE": "Стиральная машина",
        "DRYER": "Сушильная машина",
        "IRONING_BOARD": "Гладильная доска",
        "IRON": "Утюг",
    };

    const [address, setAddress] = useState("");
    const [branch, setBranch] = useState<{ id: string, address: string }>({
        id: "",
        address: ""
    });
    const [schedule, setSchedule] = useState<{ name: string, start: string, end: string }[]>([]);
    const [products, setProducts] = useState<{ type: string, amount: number }[]>([]);

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

    if (auth.role !== "DIRECTOR" && auth.role !== "SUPERUSER") {
        return <Navigate to="/not-found" replace={true}/>
    }


    return (
        <div id="warehouse-page-wrapper">
            <Header title="Cклад"/>

            <div id="content-wrapper">
                <div id="window">
                    <div id="top">
                        <div id="data">
                            <div className="data-part-lin">
                                <div className="bold">Адрес:</div>
                                <div>{address}</div>
                            </div>
                            <div className="data-part-lin">
                                <div className="bold">Филиал:</div>
                                <Link to={"/branch-page/" + branch.id}>{branch.address}</Link>
                            </div>
                            <div id="big-part">
                                <div className="data-part-hor-main">
                                    <div className="bold">График работы:</div>
                                    <ul>
                                        {schedule.map((element) => <li key={element.name}>
                                            {element.name + ": c " + element.start + " до " + element.end}
                                        </li>)}
                                    </ul>
                                </div>
                                <div className="data-part-hor-main">
                                    <div className="bold">Доступные товары:</div>
                                    <ul>
                                        {products.map((element) => <li key={element.type}>
                                            {element.type + ": " + element.amount + " шт."}
                                        </li>)}
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div id="control-btns">
                            <Button onClick={() => navigate("./add-products", {relative: "path"})}>Добавить
                                товары</Button>
                            <Button onClick={() => navigate("./remove-products", {relative: "path"})}>Списать
                                товары</Button>
                        </div>
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