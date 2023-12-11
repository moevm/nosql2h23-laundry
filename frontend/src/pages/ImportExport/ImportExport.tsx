import "./ImportExport.scss"
import Header from "../../components/Header/Header";
import {Button, Form} from "react-bootstrap";
import React from "react";
import {useNavigate} from "react-router-dom";

export function ImportExport() {

    const navigate = useNavigate();

    function exportData() {
    }

    function importData() {
    }

    return (
        <div id="import-export-wrapper">
            <Header title="Экспорт/Импорт"/>

            <div id="content-wrapper">
                <div className="window">
                    <h1>Экспорт</h1>
                    <div>
                        <div>Внимание, вы собираетесь экспортировать ВСЕ данные из системы!</div>
                        <div>В них входят:</div>
                        <ul>
                            <li>Клиенты</li>
                            <li>Сотрудники</li>
                            <li>Заказы</li>
                            <li>Филиалы</li>
                            <li>Склады</li>
                        </ul>
                        <div className="text-danger text-center">Вы уверены, что хотите продолжить?</div>
                    </div>
                    <div className="buttons">
                        <Button variant={"danger"} onClick={() => exportData()}>Да</Button>
                        <Button onClick={() => navigate(-1)}>Нет</Button>
                    </div>
                </div>
                <div className="window">
                    <h1>Импорт</h1>
                    <div>
                        <div>Внимание, вы собираетесь исмортировать данные в систему!</div>
                        <div>Данное действие удалит все данные из системы и заменит из новыми из файла.</div>
                        <div className="text-danger text-center">Если вы уверены, что хотите продолжить, вставьте файл и
                            нажмите
                            "Импорт"
                        </div>
                    </div>
                    <Form>

                        <Form.Control type="file"/>

                        <div className="buttons">
                            <Button variant={"danger"} onClick={() => importData()}>Импорт</Button>
                            <Button onClick={() => navigate(-1)}>Отмена</Button>
                        </div>
                    </Form>
                </div>
            </div>
        </div>
    )
}