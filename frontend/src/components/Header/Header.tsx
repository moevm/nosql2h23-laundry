import "./Header.scss"
import {Image} from "react-bootstrap";
import UserIcon from "../UserIcon/UserIcon";
import {useNavigate} from "react-router-dom";

type AppProps = {
    title: string;
}

export default function Header({title}: AppProps) {

    const navigate = useNavigate();

    return (
        <div id="header">
            <div id="small_title" onClick={() => {
                navigate("/main_page", {replace: true})
            }}>
                <Image className="small-icon" src="/SmallLogo.png" alt="Small Title"/>
                <h4>Automatic Laundry Solutions</h4>
            </div>
            <h1 className="display-4">{title}</h1>
            <UserIcon/>
        </div>
    );
}
