import "./BranchesList.scss";
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
    warehouse: string,
    director: string
}

export function BranchesList() {
    const navigate = useNavigate();


    const [tableData, setTableData] = useState<TableData[]>([]);
    const [isInitialized, setInitialized] = useState(false);

    async function loadData() {
        await axios.get("/api/branch/all_count", {
            baseURL: "http://localhost:8080",
            params: {
                address: addressFilter,
                warehouse: warehouseFilter,
                director: directorFilter,
                elementsOnPage: elementsOnPage
            }
        }).then(async (response) => {

            setTotalPages(parseInt(response.data))

            await axios.get("/api/branch/all", {
                baseURL: "http://localhost:8080",
                params: {
                    address: addressFilter,
                    warehouse: warehouseFilter,
                    director: directorFilter,
                    elementsOnPage: elementsOnPage,
                    page: currentPage
                }
            }).then((response) => {
                let data: TableData[] = response.data.data;

                for (const dataPart of data) {
                    if (dataPart.warehouse === "null") {
                        dataPart.warehouse = "Склад отсутствует"
                    }
                }

                setTableData(data);
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
    let [totalPages, setTotalPages] = useState(1);
    let [elementsOnPage, setElementsOnPage] = useState(10);

    useEffect(() => {
        if (isInitialized) {
            loadData();
        }
    }, [currentPage, elementsOnPage]);


    let [searchParams, setSearchParams] = useSearchParams();
    const [sortState, setSortState] = useState("state_down");


    function initAddressFilter() {
        let string = "";

        if (searchParams.has("address")) {
            string = searchParams.get("address")!;
        }

        return string;
    }

    function initWarehouseFilter() {
        let string = "";

        if (searchParams.has("warehouse")) {
            string = searchParams.get("warehouse")!;
        }

        return string;
    }

    function initDirectorFilter() {
        let string = "";

        if (searchParams.has("director")) {
            string = searchParams.get("director")!;
        }

        return string;
    }

    const [addressFilter, setAddressFilter] = useState(initAddressFilter());
    const [warehouseFilter, setWarehouseFilter] = useState(initWarehouseFilter());
    const [directorFilter, setDirectorFilter] = useState(initDirectorFilter());

    const mainCheckRef = useRef<HTMLInputElement>(null);
    const checkboxesRef = useRef<{
        element: HTMLInputElement,
        id: string
    }[]>([]);
    const [anyChecked, setAnyChecked] = useState(false);

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

        if (warehouseFilter !== "") {
            if (searchParams.has("warehouse")) {
                searchParams.set("warehouse", warehouseFilter)
            } else {
                searchParams.append("warehouse", warehouseFilter)
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("warehouse")) {
                searchParams.delete("warehouse")
                setSearchParams(searchParams, {replace: true});
            }
        }


        if (directorFilter !== "") {
            if (searchParams.has("director")) {
                searchParams.set("director", directorFilter)
            } else {
                searchParams.append("director", directorFilter)
            }
            setSearchParams(searchParams, {replace: true});
        } else {
            if (searchParams.has("director")) {
                searchParams.delete("director")
                setSearchParams(searchParams, {replace: true});
            }
        }

        if (isInitialized) {
            console.log("2")
            loadData();
        }

    }, [addressFilter, warehouseFilter, directorFilter]);

    useEffect(() => {
        if (!isInitialized) {
            setInitialized(true);
        }
        loadData();
    }, []);

    const [confirmShown, setConfirmShown] = useState(false);


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

    if (auth.role !== "SUPERUSER") {
        return <Navigate to="/not-found" replace={true}/>
    }


    async function deleteChecked() {

        let idsToDelete: string[] = [];

        for (const checkbox of checkboxesRef.current) {
            if (checkbox.element.checked) {
                idsToDelete.push(checkbox.id)
            }
        }

        await axios.post("/api/branch/delete_list", {
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

    // TODO: Implement sort
    return (
        <div id="branches-list-wrapper">
            <Header title="Филиалы"/>
            <div id="content-wrapper">
                <div id="window">
                    <div id="controls">
                        <div id="back-part">
                            <Button onClick={() => navigate(-1)}>Назад</Button>
                        </div>
                        <div id="controls-1">
                            <Button className="etc-button">Нагрузка</Button>
                            <Button className="etc-button">Прибыль</Button>
                            <Button id="trash-button" disabled={!anyChecked}
                                    onClick={() => setConfirmShown(true)}><Trash size="25px"/></Button>
                            <Button id="plus-button" onClick={() => {
                                navigate("/new-branch")
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
                                    <div>Склад</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState}
                                                   sortName="warehouse"/>
                                        <TextFilter filterData={warehouseFilter} setFilterData={setWarehouseFilter}/>
                                    </div>
                                </div>
                            </th>
                            <th>
                                <div>
                                    <div>Директор</div>
                                    <div className="sort-filter">
                                        <TableSort sortState={sortState} setSortState={setSortState}
                                                   sortName="director"/>
                                        <TextFilter filterData={directorFilter} setFilterData={setDirectorFilter}/>
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
                                    <td>{data.warehouse}</td>
                                    <td>{data.director}</td>
                                    <td>
                                        <Link to={"/branch-page/" + data.id}>Открыть</Link>
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