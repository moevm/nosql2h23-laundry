import "./ImportExport.scss"
import Header from "../../components/Header/Header";
import {Button, Form} from "react-bootstrap";
import React, {useRef} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import fileDownload from "js-file-download"

export function ImportExport() {

    const navigate = useNavigate();

    const fileInputRef = useRef<HTMLInputElement>(null);

    async function exportData() {

        await axios.get("/export", {
            baseURL: "http://localhost:8080",
            responseType: "arraybuffer"
        }).then((response) => {
            console.log(response.data)
            fileDownload(response.data, "export.json");
        }).catch((error) => {
            console.log(error)
        })

    }

    async function importData() {

        let formData = new FormData();

        if (fileInputRef.current !== null && fileInputRef.current.files !== null) {
            // console.log(fileInputRef.current.files[0])
            formData.append("file", fileInputRef.current.files[0])
        }

        await axios.post("/import", formData,{
            baseURL: "http://localhost:8080",
            headers: {
                "Content-Type": 'multipart/form-data'
            }
        }).then((response) => {
            // console.log(response.data)
            // fileDownload(response.data, "export.json");
        }).catch((error) => {
            console.log(error)
        })
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
                        <Form.Control type="file" ref={fileInputRef}/>

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