import "./UserPage.scss"
import Header from "../../components/Header/Header";
import React, {useEffect, useState} from "react";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate, useParams} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import axios from "axios";

export function UserPage() {

    const navigate = useNavigate();
    let {userId} = useParams();

    async function loadData(userId: string|undefined) {
        await axios.get("/api/user/get", {
            baseURL: "http://localhost:8080",
            params: {
                id: userId
            }
        }).then(async (response) => {
            let data = response.data;

            setFullName(data.name);
            setEmail(data.email);
            setPhone(data.phone)
            setBranch({
                id: data.branchId,
                address: data.branchAddress
            })
            setWarehouse({
                id: data.warehouseId,
                address: data.warehouseAddress
            })

            setRole(data.role);

            if (data.schedule !== null) {
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
            }
            
            setCreationDate(data.creationDate)
            setEditDate(data.editDate)

        }).catch((error) => {
            console.log(error)
        })
    }

    useEffect(() => {

        loadData(userId);

    }, [userId]);

    let roleTranslate = new Map();
    roleTranslate.set("CLIENT", "Клиент")
    roleTranslate.set("ADMIN", "Администратор")
    roleTranslate.set("DIRECTOR", "Директор Филиала")
    roleTranslate.set("SUPERUSER", "Владелец бизнеса / Суперпользователь")

    const [fullName, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [branch, setBranch] = useState<{ id: string, address: string }>({
        id: "",
        address: ""
    });
    const [warehouse, setWarehouse] = useState<{ id: string, address: string }>({
        id: "",
        address: ""
    });
    const [role, setRole] = useState("")
    const [schedule, setSchedule] = useState<{ name: string, start: string, end: string }[]>([]);

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

    return (
        <div id="user-page-wrapper">
            <Header title={role === auth.role ? "Личный кабинет" : "Страница пользователя"}/>

            <div id="content-wrapper">
                <div id="window">
                    <div id="top">
                        <div id="data">
                            <div className="data-part-lin">
                                <div className="bold">ФИО:</div>
                                <div>{fullName}</div>
                            </div>
                            <div className="data-part-lin">
                                <div className="bold">Email:</div>
                                <div>{email}</div>
                            </div>
                            {role !== "CLIENT" &&
                                <div className="data-part-lin">
                                    <div className="bold">Телефон:</div>
                                    <div>{phone}</div>
                                </div>
                            }
                            {((role === "ADMIN" || role === "DIRECTOR") && branch.id !== "") &&
                                <div className="data-part-lin">
                                    <div className="bold">Филиал:</div>
                                    <Link to={"/branch-page/" + branch.id}>{branch.address}</Link>
                                </div>
                            }
                            {(role === "DIRECTOR" && warehouse.id !== "") &&
                                <div className="data-part-lin">
                                    <div className="bold">Склад:</div>
                                    <Link to={"/warehouse-page/" + warehouse.id}>{warehouse.address}</Link>
                                </div>
                            }
                            <div className="data-part-lin">
                                <div className="bold">Роль:</div>
                                <div>{roleTranslate.get(role)}</div>
                            </div>
                        </div>

                        {role !== "CLIENT" &&
                            <div id="big-part">
                                <div className="data-part-hor-main">
                                    <div className="bold">График работы:</div>
                                    <ul>
                                        {schedule.map((element) => <li key={element.name}>
                                            {element.name + ": c " + element.start + " до " + element.end}
                                        </li>)}
                                    </ul>
                                </div>
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