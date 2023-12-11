import "./WarehousesList.scss";
import Header from "../../components/Header/Header";
import {Button, Form, Modal, Pagination, Table} from "react-bootstrap";
import {Plus, Trash} from "react-bootstrap-icons";
import {JSX, useEffect, useRef, useState} from "react";
import {TableSort} from "../../components/TableSort/TableSort";
import {TextFilter} from "../../components/Filters/TextFilter/TextFilter";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";
import {Link, Navigate, useNavigate, useSearchParams} from "react-router-dom";
import axios from "axios";

type TableData = {
    id: string,
    address: string,
    branch: string
}

export function WarehousesList() {

    const [tableData, setTableData] = useState<TableData[]>([]);
    const [isInitialized, setInitialized] = useState(false);

    async function loadData() {
        await axios.get("/api/warehouse/all_count", {
            baseURL: "http://localhost:8080",
            params: {
                address: addressFilter,
                branch: branchFilter,
                elementsOnPage: elementsOnPage
            }
        }).then(async (response) => {

            setTotalPages(parseInt(response.data))

            await axios.get("/api/warehouse/all", {
                baseURL: "http://localhost:8080",
                params: {
                    address: addressFilter,
                    branch: branchFilter,
                    elementsOnPage: elementsOnPage,
                    page: currentPage
                }
            }).then((response) => {
                setTableData(response.data.data);
            }).catch((error) => {
                console.log(error)
            })

        }).catch((error) => {
            console.log(error)
        })
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


    let [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();

    const [sortState, setSortState] = useState("state_down");


    function initAddressFilter() {
        let string = "";

        if (searchParams.has("address")) {
            string = searchParams.get("address")!;
        }

        return string;
    }

    function initBranchFilter() {
        let string = "";

        if (searchParams.has("branch")) {
            string = searchParams.get("branch")!;
        }

        return string;
    }

    const [addressFilter, setAddressFilter] = useState(initAddressFilter());
    const [branchFilter, setBranchFilter] = useState(initBranchFilter());

    const mainCheckRef = useRef<HTMLInputElement>(null);
    const checkboxesRef = useRef<{
        element: HTMLInputElement,
        id: string
    }[]>([]);
    const [anyChecked, setAnyChecked] = useState(false);

    const [confirmShown, setConfirmShown] = useState(false);

    const auth = useAppSelector((state) => state.auth)
    const dispatch = useAppDispatch()
    const [cookies] = useCookies(['auth']);

    useEffect(() => {
        if (addressFilter !== "") {
            if (searchParams.has("address")) {
                searchParams.set("address", addressFilter)
            } else {
                searchParams.append("address", addressFilter)
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("address")) {
                searchParams.delete("address")
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

    }, [addressFilter, branchFilter]);

    useEffect(() => {
        if (!isInitialized) {
            setInitialized(true);
        }
        loadData();
    }, []);

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

    async function deleteChecked() {

        let idsToDelete: string[] = [];

        for (const checkbox of checkboxesRef.current) {
            if (checkbox.element.checked) {
                idsToDelete.push(checkbox.id)
            }
        }

        await axios.post("/api/warehouse/delete_list", {
            idList: idsToDelete
        }, {
            baseURL: "http://localhost:8080"
        }).then(() => {
            loadData();
        }).catch((error) => {
            console.log(error)
        })

        setConfirmShown(false)
    }

    return (
        <div id="warehouses-list-wrapper">
            <Header title="Склады"/>
            <div id="content-wrapper">
                <div id="window">
                    <div id="controls">
                        <Button onClick={() => navigate(-1)}>Назад</Button>
                        <div id="controls-1">
                            <Button id="trash-button" disabled={!anyChecked}
                                    onClick={() => setConfirmShown(true)}><Trash size="25px"/></Button>
                            <Button id="plus-button" onClick={() => {
                                navigate("/new-warehouse")
                            }}><Plus size="35px"/></Button>
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
                                    <Form.Check ref={mainCheckRef} onChange={() => changeMainCheck()}/>
                                </div>
                            </th>
                            <th>
                                <div>
                                    <div>Адрес</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState}
                                                   sortName="address"/>
                                        <TextFilter filterData={addressFilter} setFilterData={setAddressFilter}/>
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
                            tableData.map((data: TableData, index: number) =>
                                <tr key={data.id}>
                                    <td><Form.Check ref={(ref: HTMLInputElement) => checkboxesRef.current[index] =
                                        {element: ref, id: data.id}
                                    } onChange={() => isAllChecked()}/></td>
                                    <td>{data.address}</td>
                                    <td>{data.branch}</td>
                                    <td>
                                        <Link to={"/warehouse-page/" + data.id}>Открыть</Link>
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