import "./ClientsList.scss";
import Header from "../../components/Header/Header";
import {Button, Pagination, Table, Form, Modal} from "react-bootstrap";
import {Dot, Plus, Trash} from "react-bootstrap-icons";
import {JSX, useEffect, useRef, useState} from "react";
import {TableSort} from "../../components/TableSort/TableSort";
import {TextFilter} from "../../components/Filters/TextFilter/TextFilter";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate, useSearchParams} from "react-router-dom";

type TableData = {
    id: number,
    name: string,
    email: string
}

export function ClientsList() {

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

    function isAllChecked() {
        let allChecked: boolean = true;
        let anyChecked: boolean = false;

        for (const checkbox of checkboxesRef.current) {
            if (!checkbox.element.checked && allChecked) {
                allChecked = false;
            }
            if (checkbox.element.checked && !anyChecked) {
                anyChecked = true;
            }

            if (anyChecked && !allChecked) {
                break;
            }
        }

        setAnyChecked(anyChecked);

        if (mainCheckRef.current !== null) {
            let curMainChecked = mainCheckRef.current.checked;

            if (allChecked && !curMainChecked) {
                mainCheckRef.current.checked = true;
            } else if (!allChecked && curMainChecked) {
                mainCheckRef.current.checked = false;
            }
        }

    }

    function changeMainCheck() {
        if (mainCheckRef.current !== null) {
            let curMainChecked = mainCheckRef.current.checked;

            for (const checkbox of checkboxesRef.current) {
                checkbox.element.checked = curMainChecked;
            }

            setAnyChecked(curMainChecked);
        }
    }

    let tableData: TableData[] = [
        {
            id: 1235,
            name: "Иванов Иван Иванович",
            email: "kratos@olimpus.god"
        },
        {
            id: 4528,
            name: "Атрей Локиевич",
            email: "boy@son.semigod"
        },
        {
            id: 6532,
            name: "Атрей Локиевич",
            email: "boy@son.semigod"
        },
        {
            id: 34682,
            name: "Атрей Локиевич",
            email: "boy@son.semigod"
        },
        {
            id: 345729,
            name: "Атрей Локиевич",
            email: "boy@son.semigod"
        },
        {
            id: 6468,
            name: "Атрей Локиевич",
            email: "boy@son.semigod"
        },
        {
            id: 192974,
            name: "Атрей Локиевич",
            email: "boy@son.semigod"
        }
    ];

    let [currentPage, setCurrentPage] = useState(1);
    let [totalPages, setTotalPages] = useState(10);
    let [elementsOnPage, setElementsOnPage] = useState(10);

    let [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();


    const [sortState, setSortState] = useState("state_down");

    function initNameFilter() {
        let string = "";

        if (searchParams.has("name")) {
            string = searchParams.get("name")!;
        }

        return string;
    }
    function initEmailFilter() {
        let string = "";

        if (searchParams.has("email")) {
            string = searchParams.get("email")!;
        }

        return string;
    }

    const [nameFilter, setNameFilter] = useState(initNameFilter());
    const [emailFilter, setEmailFilter] = useState(initEmailFilter());

    const mainCheckRef = useRef<HTMLInputElement>(null);
    const checkboxesRef = useRef<{
        element: HTMLInputElement,
        id: number
    }[]>([]);
    const [anyChecked, setAnyChecked] = useState(false);

    const [confirmShown, setConfirmShown] = useState(false);

    const auth = useAppSelector((state) => state.auth)
    const dispatch = useAppDispatch()
    const [cookies] = useCookies(['auth']);

    useEffect(() => {
        if (nameFilter !== "") {
            if (searchParams.has("name")) {
                searchParams.set("name", nameFilter)
            } else {
                searchParams.append("name", nameFilter)
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("name")) {
                searchParams.delete("name")
                setSearchParams(searchParams, {replace: true});
            }
        }

        if (emailFilter !== "") {
            if (searchParams.has("email")) {
                searchParams.set("email", emailFilter)
            } else {
                searchParams.append("email", emailFilter)
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("email")) {
                searchParams.delete("email")
                setSearchParams(searchParams, {replace: true});
            }
        }

    }, [nameFilter, emailFilter]);

    useEffect(() => {
        // TODO: call backend for new data
        console.log("New Data")

    }, [currentPage, elementsOnPage]);

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



    function deleteChecked() {

        let idsToDelete: number[] = [];

        for (const checkbox of checkboxesRef.current) {
            if (checkbox.element.checked) {
                idsToDelete.push(checkbox.id)
            }
        }

        console.log(idsToDelete)
        // TODO: add messaging with backend

        setConfirmShown(false)
    }

    return (
        <div id="clients-list-wrapper">
            <Header title="Клиенты"/>
            <div id="content-wrapper">
                <div id="window">
                    <div id="controls">
                        <Button onClick={() => navigate(-1)}>Назад</Button>
                        <div id="controls-1">
                            <Button id="trash-button" disabled={!anyChecked} onClick={() => setConfirmShown(true)}><Trash size="25px"/></Button>
                            <Button id="plus-button" onClick={() => {navigate("/new-client")}}><Plus size="35px"/></Button>
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
                                    <Form.Check ref={mainCheckRef} onChange={() => changeMainCheck()}/>
                                </div>
                            </th>
                            <th>
                                <div>
                                    <div>ФИО</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState} sortName="name"/>
                                        <TextFilter filterData={nameFilter} setFilterData={setNameFilter}/>
                                    </div>
                                </div>
                            </th>
                            <th>
                                <div>
                                    <div>Email</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState} sortName="email"/>
                                        <TextFilter filterData={emailFilter} setFilterData={setEmailFilter}/>
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
                            tableData.map((data: TableData, index: number) =>
                                <tr key={data.id}>
                                    <td><Form.Check ref={(ref: HTMLInputElement) => checkboxesRef.current[index] =
                                        {element: ref, id: data.id}
                                    } onChange={() => isAllChecked()}/></td>
                                    <td>{data.name}</td>
                                    <td>{data.email}</td>
                                    <td>
                                        <Link to={"/user/" + data.id}>Открыть</Link>
                                    </td>
                                </tr>
                            )
                        }
                        </tbody>
                    </Table>
                </div>
                <Modal show={confirmShown} onHide={() => setConfirmShown(false)}>
                    <Modal.Header>Внимание!</Modal.Header>
                    <Modal.Body>Вы уверены, что хотите произвести удаление?</Modal.Body>
                    <Modal.Footer>
                        <Button variant="danger" onClick={() => deleteChecked()}>
                            Удалить
                        </Button>
                        <Button onClick={() => setConfirmShown(false)}>
                            Отмена
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        </div>
    );
}