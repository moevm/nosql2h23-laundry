import "./LoadCalc.scss";
import Header from "../../components/Header/Header";
import {Badge, Button, Form, Pagination, Table} from "react-bootstrap";
import React, {JSX, useEffect, useState} from "react";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate} from "react-router-dom";
import DatePicker from "react-date-picker";

type TableData = {
    branchId: string,
    branchAddress: string,
    spentProductsCoefficient: number,
    servicesCompleted: number
}

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

interface DateTimeFormatOptions {
    localeMatcher?: "best fit" | "lookup" | undefined;
    weekday?: "long" | "short" | "narrow" | undefined;
    era?: "long" | "short" | "narrow" | undefined;
    year?: "numeric" | "2-digit" | undefined;
    month?: "numeric" | "2-digit" | "long" | "short" | "narrow" | undefined;
    day?: "numeric" | "2-digit" | undefined;
    hour?: "numeric" | "2-digit" | undefined;
    minute?: "numeric" | "2-digit" | undefined;
    second?: "numeric" | "2-digit" | undefined;
    timeZoneName?: "short" | "long" | "shortOffset" | "longOffset" | "shortGeneric" | "longGeneric" | undefined;
    formatMatcher?: "best fit" | "basic" | undefined;
    hour12?: boolean | undefined;
    timeZone?: string | undefined;
}

export function LoadCalc() {

    const navigate = useNavigate();

    const auth = useAppSelector((state) => state.auth)
    const dispatch = useAppDispatch()
    const [cookies] = useCookies(['auth']);


    function getBadgeType(text: string): string {
        switch (text) {
            case "Низкая": {
                return "secondary";
            }
            case "Ниже средней": {
                return "info";
            }
            case "Средняя": {
                return "success";
            }
            case "Выше средней": {
                return "warning";
            }
            case "Высокая": {
                return "danger";
            }
            default: {
                return "secondary"
            }
        }
    }


    function createPaginationElements(): JSX.Element[] {
        const pagesShown: number = 5;

        let items: JSX.Element[] = [];

        items.push(
            <Pagination.Item key={1} active={currentPage === 1} onClick={() => {
                if (currentPage !== 1)
                    setCurrentPage(1)
            }}>
                1
            </Pagination.Item>
        );

        let startNumber = currentPage - Math.floor(pagesShown / 2);
        if (startNumber > (totalPages - pagesShown + 1)) {
            startNumber = totalPages - pagesShown + 1;
        }

        if (startNumber > 2) {
            items.push(
                <Pagination.Ellipsis key={"ellipsis_1"} disabled/>
            );
        } else {
            startNumber = 2;
        }

        let endNumber = currentPage + Math.floor(pagesShown / 2);
        if (endNumber < pagesShown) {
            endNumber = pagesShown;
        }

        if (endNumber > (totalPages - 1)) {
            endNumber = totalPages - 1;
        }

        for (let number = startNumber; number <= endNumber; number++) {
            items.push(
                <Pagination.Item key={number} active={number === currentPage} onClick={() => {
                    if (currentPage !== number)
                        setCurrentPage(number)
                }}>
                    {number}
                </Pagination.Item>
            )
        }

        if (endNumber < (totalPages - 1)) {
            items.push(
                <Pagination.Ellipsis key={"ellipsis_end"} disabled/>
            );
        }

        if (totalPages > 1) {
            items.push(
                <Pagination.Item key={totalPages} active={currentPage === totalPages} onClick={() => {
                    if (currentPage !== totalPages)
                        setCurrentPage(totalPages)
                }}>
                    {totalPages}
                </Pagination.Item>
            );
        }


        return items;
    }

    let [currentPage, setCurrentPage] = useState(1);
    let [totalPages, setTotalPages] = useState(10);
    let [elementsOnPage, setElementsOnPage] = useState(10);

    useEffect(() => {
        if (isInitialized) {
            loadData();
        }
    }, [currentPage, elementsOnPage]);


    useEffect(() => {
        if (!isInitialized) {
            setInitialized(true);
        }

        if (!auth.authorized) {
            if (cookies["auth"] !== undefined) {
                dispatch(setUser({
                    id: cookies["auth"].id,
                    login: cookies["auth"].login,
                    name: cookies["auth"].name,
                    role: cookies["auth"].role
                }))
            }
        }

        loadData();
    }, []);


    const [tableData, setTableData] = useState<TableData[]>([]);
    const [isInitialized, setInitialized] = useState(false);

    const [startDate, setStartDate] = useState<ValuePiece>(null);
    const [endDate, setEndDate] = useState<ValuePiece>(null);

    async function loadData() {

        if (!auth.authorized) {
            setInitialized(false);
            return;
        }

    }

    useEffect(() => {
        if (startDate === null || endDate === null) {

            return;
        }

    }, [startDate, endDate]);

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

    function createLoadBadge(data: TableData) {
        let load = (data.spentProductsCoefficient + data.servicesCompleted) / 50;

        let text = ""

        if (load < 10) {
            text = "Низкая"
        } else if (load >= 10 && load < 15) {
            text = "Ниже средней"
        } else if (load >= 15 && load < 20) {
            text = "Средняя"
        } else if (load >= 20 && load < 25) {
            text = "Выше средней"
        } else if (load >= 25) {
            text = "Высокая"
        }

        return <Badge pill bg={getBadgeType(text)}>{text}</Badge>

    }

    return (
        <div id="load-calc-wrapper">
            <Header title="Расчет нагрузки"/>
            <div id="content-wrapper">
                <div id="window">
                    <div className="controls-dates">
                        <h4>
                            Период:
                        </h4>

                        <DatePicker
                            onChange={(value: Value) => {
                                if (!Array.isArray(value))
                                    setStartDate(value)
                            }}
                            value={startDate}
                            maxDate={(endDate === null) ? undefined : endDate}
                            returnValue="start"
                        />

                        <DatePicker
                            onChange={(value: Value) => {
                                if (!Array.isArray(value))
                                    setEndDate(value)
                            }}
                            value={endDate}
                            minDate={(startDate === null) ? undefined : startDate}
                            returnValue="end"
                        />

                    </div>
                    <div className="controls">
                        <div id="back-part">
                            <Button onClick={() => navigate(-1)}>Назад</Button>
                        </div>
                        <div id="controls-1">
                            <Pagination>
                                <Pagination.Prev key="prev" onClick={() => {
                                    if (currentPage !== 1)
                                        setCurrentPage(currentPage - 1)
                                }}/>
                                {createPaginationElements()}
                                <Pagination.Next key={"next"} onClick={() => {
                                    if (currentPage !== totalPages)
                                        setCurrentPage(currentPage + 1)
                                }}/>
                            </Pagination>
                            <Form.Select onChange={(event) => {
                                let value: number = parseInt(event.target.value);

                                setElementsOnPage(value)

                                setCurrentPage(1)
                            }}>
                                <option value="10" key={10}>10 / page</option>
                                <option value="15" key={15}>15 / page</option>
                                <option value="20" key={20}>20 / page</option>
                            </Form.Select>
                        </div>
                    </div>
                    <Table id="table">
                        <thead>
                        <tr>
                            <th>
                                Филиал
                            </th>
                            <th>
                                Коэф. списанных товаров
                            </th>
                            <th>
                                Выполнено услуг
                            </th>
                            <th>
                                Нагрузка
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            tableData.map((data: TableData) =>
                                <tr key={data.branchId}>
                                    <td>
                                        <Link to={"/branch-page/" + data.branchId}>{data.branchAddress}</Link>
                                    </td>
                                    <td>{data.spentProductsCoefficient}</td>
                                    <td>{data.servicesCompleted}</td>
                                    <td>{createLoadBadge(data)}</td>
                                </tr>
                            )
                        }
                        </tbody>
                    </Table>
                </div>
            </div>
        </div>
    );
}