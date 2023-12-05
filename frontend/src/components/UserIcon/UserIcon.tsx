import "./UserIcon.scss"
import {Image} from "react-bootstrap";
import {BoxArrowRight, Person} from "react-bootstrap-icons";
import {useEffect, useRef, useState} from "react";
import {useAppDispatch, useAppSelector} from "../../hooks";
import {useCookies} from "react-cookie";
import {resetUser} from "../../features/auth/authSlice";
import {useNavigate} from "react-router-dom";

export default function UserIcon() {

    const auth = useAppSelector((state) => state.auth)
    const navigate = useNavigate();

    const dispatch = useAppDispatch()
    const [,, removeCookie] = useCookies(['auth']);

    function toggleDropdown() {
        showDropDown(!isDropdownShown);
    }

    let [isDropdownShown, showDropDown] = useState(false);

    const wrapperRef = useRef<HTMLDivElement>(null);
    useEffect(() => {
        function handleClickOutside(event:any) {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
                showDropDown(false)
            }
        }
        // Bind the event listener
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [wrapperRef]);

    if (!auth.authorized) return <></>

    return (
        <div id="user-icon">
            <Image id="user_icon" src="" alt="Icon" onClick={toggleDropdown}/>

            { isDropdownShown &&
                <div id="dropdown-actions" ref={wrapperRef}>
                    <div className="dropdown-element" onClick={() => {
                        navigate("/user/" + auth.id)
                    }}>
                        <Person/>
                        <h5>Личный кабинет</h5>
                    </div>
                    <div className="dropdown-element" onClick={() => {
                        removeCookie("auth");
                        dispatch(resetUser());
                        navigate("/sign_in", {replace: true})
                    }}>
                        <BoxArrowRight/>
                        <h5>Выход</h5>
                    </div>
                </div>
            }
        </div>
    );
}