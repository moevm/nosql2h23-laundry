import "./NotFound.scss";
import {useNavigate, useRouteError} from "react-router-dom";
import Header from "../../components/Header/Header";
import {EmojiSmileUpsideDown} from "react-bootstrap-icons";
import {Button} from "react-bootstrap";

export function NotFound() {

    const navigate = useNavigate();

    return (
        <div id="not-found-wrapper">
            <Header title="404"/>

            <div id="content">
                <div className="text">
                    <h1 className="display-5">Ой!</h1>
                    <h1 className="display-5">Как вы сюда попали?</h1>
                </div>

                <EmojiSmileUpsideDown size={128}/>

                <Button size={"lg"} onClick={() => {navigate("/main_page", {replace: true})}}>Вернуться на главный экран</Button>
            </div>

        </div>
    )
}