import "./OrdersList.scss";
import Header from "../../components/Header/Header";
import {Badge, Button, Dropdown, DropdownButton, Pagination, Table, Form} from "react-bootstrap";
import {Plus} from "react-bootstrap-icons";
import {JSX, useEffect, useState} from "react";
import {TableSort} from "../../components/TableSort/TableSort";
import {TextFilter} from "../../components/Filters/TextFilter/TextFilter";
import {SelectFilter} from "../../components/Filters/SelectFilter/SelectFilter";
import {DateRangeFilter} from "../../components/Filters/DateRangeFilter/DateRangeFilter";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate, useSearchParams} from "react-router-dom";
import axios from "axios";

type TableData = {
    id: string,
    date: string,
    status: string,
    branch: string
}

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

export function OrdersList() {

    let [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();

    const auth = useAppSelector((state) => state.auth)
    const dispatch = useAppDispatch()
    const [cookies] = useCookies(['auth']);


    function getBadgeType(text: string): string {
        switch (text) {
            case "Новый": {
                return "warning";
            }
            case "Активный": {
                return "success";
            }
            case "Выполненный": {
                return "info";
            }
            case "Завершенный": {
                return "primary";
            }
            case "Отмененный": {
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


    const [sortState, setSortState] = useState("state_down");

    function initDateFilter() {
        let valueString = searchParams.get("date");
        let returnObj = null;

        if (valueString !== null) {
            let values = valueString.split(":")
            returnObj = {
                start: new Date(Date.parse(values[0])),
                end: new Date(Date.parse(values[1]))
            };
        }

        return returnObj;
    }

    function initStatusFilter() {
        let valueString = searchParams.get("status");
        let obj: { [key: string]: boolean } = {
            "Новый": false,
            "Активный": false,
            "Выполненный": false,
            "Завершенный": false,
            "Отмененный": false,
        };

        if (valueString !== null) {
            let values = valueString.split(":")

            for (const valuesKey of values) {
                obj[valuesKey] = true
            }
        }

        return obj;
    }

    function initBranchFilter() {
        let string = "";

        if (searchParams.has("branch")) {
            string = searchParams.get("branch")!;
        }

        return string;
    }

    const [dateFilter, setDateFilter] = useState<{ start: Date; end: Date; } | null>(initDateFilter());
    const [stateFilter, setStateFilter] = useState<{ [key: string]: boolean }>(initStatusFilter());
    const [branchFilter, setBranchFilter] = useState(initBranchFilter);

    useEffect(() => {
        const options: DateTimeFormatOptions = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
        };

        if (dateFilter !== null) {
            if (searchParams.has("date")) {
                searchParams.set("date", dateFilter.start.toLocaleDateString("en-US", options) + ":" + dateFilter.end.toLocaleDateString("en-US", options))
            } else {
                searchParams.append("date", dateFilter.start.toLocaleDateString("en-US", options) + ":" + dateFilter.end.toLocaleDateString("en-US", options))
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("date")) {
                searchParams.delete("date")
                setSearchParams(searchParams, {replace: true});
            }
        }


        let hasAnyStatusFilter = false;

        for (const key in stateFilter) {
            if (stateFilter[key]) {
                hasAnyStatusFilter = true;
                break;
            }
        }

        if (hasAnyStatusFilter) {
            let statusFilterString = "";
            for (const key in stateFilter) {
                if (stateFilter[key]) {
                    statusFilterString += key + ":"
                }
            }

            statusFilterString = statusFilterString.substring(0, statusFilterString.length - 1);

            if (searchParams.has("status")) {
                searchParams.set("status", statusFilterString)
            } else {
                searchParams.append("status", statusFilterString)
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("status")) {
                searchParams.delete("status")
                setSearchParams(searchParams, {replace: true});
            }
        }


        if (branchFilter !== "") {
            if (searchParams.has("branch")) {
                searchParams.set("branch", branchFilter)
            } else {
                searchParams.append("branch", branchFilter)
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("branch")) {
                searchParams.delete("branch")
                setSearchParams(searchParams, {replace: true});
            }
        }

        if (isInitialized) {
            loadData();
        }

    }, [dateFilter, stateFilter, branchFilter]);

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

    async function loadData() {

        if (!auth.authorized) {
            setInitialized(false);
            return;
        }

        let states: string[] = []

        for (const stateFilterKey in stateFilter) {
            if (stateFilter[stateFilterKey]) {
                let name = "";

                switch (stateFilterKey) {
                    case "Новый": {
                        name = "NEW";
                        break;
                    }
                    case "Активный": {
                        name = "ACTIVE";
                        break;
                    }
                    case "Выполненный": {
                        name = "READY";
                        break;
                    }
                    case "Завершенный": {
                        name = "COMPLETED";
                        break;
                    }
                    case "Отмененный": {
                        name = "CANCELED";
                        break;
                    }
                }

                states.push(name);
            }
        }

        let dateData = {
            start: dateFilter?.start.toUTCString(),
            end: dateFilter?.end.toUTCString()
        }

        await axios.post("/api/order/all_count", {
            dates: dateData,
            states: states,
            branch: branchFilter,
            clientId: (auth.role === "CLIENT") ? auth.id : "",
            elementsOnPage: elementsOnPage
        },{
            baseURL: "http://localhost:8080",
        }).then(async (response) => {

            setTotalPages(parseInt(response.data))

            await axios.post("/api/order/all", {
                dates: dateData,
                states: states,
                branch: branchFilter,
                clientId: (auth.role === "CLIENT") ? auth.id : "",
                elementsOnPage: elementsOnPage,
                page: currentPage
            }, {
                baseURL: "http://localhost:8080",
            }).then((response) => {

                let tmpData: TableData[] = response.data.data;

                for (const dataElement of tmpData) {
                    let date = new Date(dataElement.date);
                    dataElement.date = date.toLocaleDateString(undefined, {
                        day: "2-digit",
                        month: "2-digit",
                        year: "numeric",
                        hour: "2-digit",
                        minute: "2-digit",
                        second: "2-digit",
                        hour12: false,
                    });

                    let stateTextRu = dataElement.status;
                    switch (stateTextRu) {
                        case "NEW": {
                            dataElement.status = "Новый";
                            break;
                        }
                        case "ACTIVE": {
                            dataElement.status = "Активный";
                            break;
                        }
                        case "READY": {
                            dataElement.status = "Выполненный";
                            break;
                        }
                        case "COMPLETED": {
                            dataElement.status = "Завершенный";
                            break;
                        }
                        case "CANCELED": {
                            dataElement.status = "Отмененный";
                            break;
                        }
                    }

                }

                setTableData(tmpData);
            }).catch((error) => {
                console.log(error)
            })

        }).catch((error) => {
            console.log(error)
        })
    }


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
        <div id="orders-list-wrapper">
            <Header title="Заказы"/>
            <div id="content-wrapper">
                <div id="window">
                    <div id="controls">
                        <div id="back-part">
                            <Button onClick={() => navigate(-1)}>Назад</Button>
                        </div>
                        <div id="controls-1">
                            {
                                // TODO: This genius button can rid of order page (for a while at least)
                                auth.role !== "CLIENT" &&
                                <DropdownButton title="Действия">
                                    <Dropdown.Item key={"approve_all"}>Подтвердить все</Dropdown.Item>
                                    <Dropdown.Item key={"execute_all"}>Выполнить все</Dropdown.Item>
                                    <Dropdown.Item key={"release_all"}>Выдать все</Dropdown.Item>
                                    <Dropdown.Divider key={"divider"}/>
                                    <Dropdown.Item key={"cancel_all"}>Отменить все</Dropdown.Item>
                                </DropdownButton>
                            }
                            {
                                auth.role !== "ADMIN" &&
                                <Button id="plus-button" onClick={() => {navigate("/new-order")}}><Plus size="35px"/></Button>
                            }
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
                                <div>
                                    <div>Дата</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState} sortName="date"/>
                                        <DateRangeFilter filterData={dateFilter} setFilterData={setDateFilter}/>
                                    </div>
                                </div>
                            </th>
                            <th>
                                <div>
                                    <div>Статус</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState} sortName="state"/>
                                        <SelectFilter filterData={stateFilter} setFilterData={setStateFilter}/>
                                    </div>
                                </div>
                            </th>
                            <th>
                                <div>
                                    <div>Филиал</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState} sortName="branch"/>
                                        <TextFilter filterData={branchFilter} setFilterData={setBranchFilter}/>
                                    </div>
                                </div>
                            </th>
                            <th>
                                Действия
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            tableData.map((data: TableData) =>
                                <tr key={data.id}>
                                    <td>{data.date}</td>
                                    <td><Badge pill bg={getBadgeType(data.status)}>{data.status}</Badge></td>
                                    <td>{data.branch}</td>
                                    <td><Link to={"/order/" + data.id}>Открыть</Link></td>
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