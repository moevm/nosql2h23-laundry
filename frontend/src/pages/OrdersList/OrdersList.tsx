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

type TableData = {
    id: number,
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

        items.push(
            <Pagination.Item key={totalPages} active={currentPage === totalPages} onClick={() => {
                if (currentPage !== totalPages)
                    setCurrentPage(totalPages)
            }}>
                {totalPages}
            </Pagination.Item>
        );

        return items;
    }

    let tableData: TableData[] = [
        {
            id: 1235,
            date: "16.03.2352",
            status: "Новый",
            branch: "Москва, улица Рождественка, 20/8с16"
        },
        {
            id: 2145,
            date: "23.01.2022",
            status: "Активный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 3674,
            date: "23.01.2022",
            status: "Выполненный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 424386,
            date: "23.01.2022",
            status: "Завершенный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 163235,
            date: "23.01.2022",
            status: "Отмененный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 5838,
            date: "23.01.2022",
            status: "Активный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 637045,
            date: "23.01.2022",
            status: "Активный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 64586,
            date: "23.01.2022",
            status: "Активный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 24528,
            date: "23.01.2022",
            status: "Активный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        },
        {
            id: 20865,
            date: "23.01.2022",
            status: "Активный",
            branch: "Большой проспект П.С., 88, Санкт-Петербург, 197136"
        }
    ];

    let [currentPage, setCurrentPage] = useState(1);
    let [totalPages, setTotalPages] = useState(10);
    let [elementsOnPage, setElementsOnPage] = useState(10);

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
    const [statusFilter, setStatusFilter] = useState<{ [key: string]: boolean }>(initStatusFilter());
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

        for (const key in statusFilter) {
            if (statusFilter[key]) {
                hasAnyStatusFilter = true;
                break;
            }
        }

        if (hasAnyStatusFilter) {
            let statusFilterString = "";
            for (const key in statusFilter) {
                if (statusFilter[key]) {
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

    }, [dateFilter, statusFilter, branchFilter]);

    useEffect(() => {
        // TODO: call backend for new data
        console.log("New Data")

    }, [currentPage, elementsOnPage]);

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
                                <Button id="plus-button"><Plus size="35px"/></Button>
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

                                // TODO: call backend for new total

                                setTotalPages(15)
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
                                        <SelectFilter filterData={statusFilter} setFilterData={setStatusFilter}/>
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