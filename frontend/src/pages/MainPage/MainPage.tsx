import "./MainPage.scss";
import Header from "../../components/Header/Header";
import {Button, Card, Col, Row} from "react-bootstrap";
import {Navigate, useNavigate} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {setUser} from "../../features/auth/authSlice";

export function MainPage() {

    const navigate = useNavigate();

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
            return <Navigate to="/sign_in"/>;
        }
    }

    let content = <div></div>;

    let title = "";

    switch (auth.role) {
        case "CLIENT": {
            title = "Клиент";
            content =
                <>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Текущие заказы</Card.Title>
                            <Card.Text>Список незавершенных заказов.</Card.Text>
                            <Button variant="primary" onClick={() => {
                                navigate("/orders-list?status=Новый%3AАктивный%3AВыполненный")
                            }}>Открыть</Button>
                        </Card.Body>
                    </Card>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Новый заказ</Card.Title>
                            <Card.Text>Создать новый заказ.</Card.Text>
                            <Button variant="primary" onClick={() => {
                                navigate("/new-order")
                            }}>Создать</Button>
                        </Card.Body>
                    </Card>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>История заказов</Card.Title>
                            <Card.Text>История всех совершенных заказов.</Card.Text>
                            <Button variant="primary" onClick={() => {
                                navigate("/orders-list?status=Завершенный")
                            }}>Открыть</Button>
                        </Card.Body>
                    </Card>
                </>;
            break;
        }
        case "ADMIN": {
            title = "Администратор";
            content =
                <>
                    <Card style={{width: "300px"}}>
                        <Card.Img variant="top" src="/BranchBackgroundSmall.png"/>
                        <Card.Body>
                            <Card.Title>Ваш филиал</Card.Title>
                            <Card.Text>Страница филиала, закрпленного за вами.</Card.Text>
                            <Button variant="primary" className="text-center" onClick={() => {
                                // TODO - получить id филиала
                                let id: number = 23;
                                navigate("/branch/" + id)
                            }}>Открыть</Button>
                        </Card.Body>
                    </Card>
                    <Card style={{width: "300px"}}>
                        <Card.Img variant="top" src="/OrderBackgroundSmall.png"/>
                        <Card.Body>
                            <Card.Title>Все заказы</Card.Title>
                            <Card.Text>Список всех заказов филиалом, закрепленного за вами.</Card.Text>
                            <Button variant="primary" onClick={() => {
                                // TODO - получить название филиала
                                let name: string = "Москва, улица Рождественка, 20/8с16";
                                navigate("/orders-list/?branch=" + encodeURIComponent(name))
                            }}>Открыть</Button>
                        </Card.Body>
                    </Card>
                </>;
            break;
        }
        case "DIRECTOR": {
            title = "Директор Филиала";
            content =
                <>
                    <Card style={{width: "300px"}}>
                        <Card.Img variant="top" src="/BranchBackgroundSmall.png"/>
                        <Card.Body>
                            <Card.Title>Ваш филиал</Card.Title>
                            <Card.Text>Страница филиала, закрпленного за вами.</Card.Text>
                            <Button variant="primary" className="text-center" onClick={() => {
                                // TODO - получить id филиала
                                let id: number = 23;
                                navigate("/branch/" + id)
                            }}>Открыть</Button>
                        </Card.Body>
                    </Card>
                    <Card style={{width: "300px"}}>
                        <Card.Img variant="top" src="/WarehouseBackgroundSmall.png"/>
                        <Card.Body>
                            <Card.Title>Ваш склад</Card.Title>
                            <Card.Text>Страница склада, закрпленного за вами.</Card.Text>
                            <Button variant="primary" onClick={() => {
                                // TODO - получить id склада
                                let id: number = 23;
                                navigate("/warehouse/" + id)
                            }}>Открыть</Button>
                        </Card.Body>
                    </Card>
                </>;
            break;
        }
        case "SUPERUSER": {
            title = "Суперпользователь";
            content = <>
                <div className="column">
                    <Card className="mb-3">
                        <Row className="g-0">
                            <Col className="col-md-4">
                                <Card.Img style={{width: "100%", height: "100%"}} className="img-fluid rounded-start" variant="left" src="/ClientBackgroundSmall.png"/>
                            </Col>
                            <Col className="">
                                <Card.Body>
                                    <Card.Title>Клиенты</Card.Title>
                                    <Card.Text>Список всех клиентов в системе.</Card.Text>
                                    <Button variant="primary" className="text-center" onClick={() => {
                                        navigate("/clients-list")
                                    }}>Открыть</Button>
                                </Card.Body>
                            </Col>
                        </Row>
                    </Card>
                    <Card className="mb-3">
                        <Row className="g-0">
                            <Col className="col-md-4">
                                <Card.Img style={{width: "100%", height: "100%"}} className="img-fluid rounded-start" variant="left" src="/OrderBackgroundSmall.png"/>
                            </Col>
                            <Col className="">
                                <Card.Body>
                                    <Card.Title>Заказы</Card.Title>
                                    <Card.Text>Список всех заказов в системе.</Card.Text>
                                    <Button variant="primary" className="text-center" onClick={() => {
                                        navigate("/orders-list")
                                    }}>Открыть</Button>
                                </Card.Body>
                            </Col>
                        </Row>
                    </Card>
                </div>
                <div className="column">
                    <Card className="mb-3">
                        <Row className="g-0">
                            <Col className="col-md-4">
                                <Card.Img style={{width: "100%", height: "100%"}} className="img-fluid rounded-start" variant="left" src="/EmployeeBackgroundSmall.png"/>
                            </Col>
                            <Col className="">
                                <Card.Body>
                                    <Card.Title>Сотрудники</Card.Title>
                                    <Card.Text>Список всех сотрудников в системе.</Card.Text>
                                    <Button variant="primary" className="text-center" onClick={() => {
                                        navigate("/employees-list")
                                    }}>Открыть</Button>
                                </Card.Body>
                            </Col>
                        </Row>
                    </Card>
                    <Card className="mb-3">
                        <Row className="g-0">
                            <Col className="col-md-4">
                                <Card.Img style={{width: "100%", height: "100%"}} className="img-fluid rounded-start" variant="left" src="/BranchBackgroundSmall2.png"/>
                            </Col>
                            <Col className="">
                                <Card.Body>
                                    <Card.Title>Филиалы</Card.Title>
                                    <Card.Text>Список всех филиалов в системе.</Card.Text>
                                    <Button variant="primary" className="text-center" onClick={() => {
                                        navigate("/branches-list")
                                    }}>Открыть</Button>
                                </Card.Body>
                            </Col>
                        </Row>
                    </Card>
                    <Card className="mb-3">
                        <Row className="g-0">
                            <Col className="col-md-4">
                                <Card.Img style={{width: "100%", height: "100%"}} className="img-fluid rounded-start" variant="left" src="/WarehouseBackgroundSmall2.png"/>
                            </Col>
                            <Col className="">
                                <Card.Body>
                                    <Card.Title>Склады</Card.Title>
                                    <Card.Text>Список всех складов в системе.</Card.Text>
                                    <Button variant="primary" className="text-center" onClick={() => {
                                        navigate("/warehouses-list")
                                    }}>Открыть</Button>
                                </Card.Body>
                            </Col>
                        </Row>
                    </Card>
                </div>
                <div className="column">
                    <Card>
                        <Card.Img variant="left" src="/ImportExportCardImg.png"/>
                        <Card.Body>
                            <Card.Title>Экспорт/Импорт данных</Card.Title>
                            <Card.Text>Страница экспортирования/испортирования данных</Card.Text>
                            <Button variant="primary">Открыть</Button>
                        </Card.Body>
                    </Card>
                </div>

            </>;
            break;
        }
    }

    return (
        <div id="main-page-wrapper">
            <Header title={title}/>
            <div id="content-wrapper">
                {content}
            </div>
        </div>
    );
}