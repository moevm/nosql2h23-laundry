import "./WarehousePage.scss"
import Header from "../../components/Header/Header";
import React, {useEffect, useState} from "react";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate, useParams} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {Button} from "react-bootstrap";
import axios from "axios";

export function WarehousePage() {

    const navigate = useNavigate();
    let {warehouseId} = useParams();

    async function loadData(warehouseId: string|undefined) {
        await axios.get("/api/warehouse/get", {
            baseURL: "http://localhost:8080",
            params: {
                id: warehouseId
            }
        }).then(async (response) => {
            let data = response.data;

            setAddress(data.address)
            setBranch({
                id: data.branchId,
                address: data.branchAddress
            })

            let scheduleData:string[] = data.schedule;

            let schedule = []

            for (let i = 0; i < scheduleData.length; i+=3) {
                schedule.push({
                    name: scheduleData[i],
                    start: scheduleData[i+1],
                    end: scheduleData[i+2],
                })
            }

            setSchedule(schedule);

            setProducts(data.products);

            setCreationDate(data.creationDate)
            setEditDate(data.editDate)

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
                                            {productNameTranslate.get(element.type) + ": " + element.amount + " шт."}
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