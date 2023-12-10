import "./TextFilter.scss"
import React, {useEffect, useRef, useState} from "react";
import {Search} from "react-bootstrap-icons";
import {Button, Form} from "react-bootstrap";

type textFilterProps = {
    filterData: string,
    setFilterData: React.Dispatch<React.SetStateAction<string>>
}

export function TextFilter(props: textFilterProps) {

    function applyFilter() {
        if (inputRef.current)
            props.setFilterData(inputRef.current.value);
        setWindowOpen(false)
    }

    function clearFilter() {
        props.setFilterData("");
        if (inputRef.current)
            inputRef.current.value = ""
        setWindowOpen(false)
    }

    const [isWindowOpen, setWindowOpen] = useState(false);

    const wrapperRef = useRef<HTMLDivElement>(null);
    useEffect(() => {
        /**
         * Alert if clicked on outside of element
         */
        function handleClickOutside(event: any) {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target)) {
                setWindowOpen(false);
            }
        }

        // Bind the event listener
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [wrapperRef]);

    const inputRef = useRef<HTMLInputElement>(null);

    return (
        <div className="text-filter-wrapper">
            <Search
                size={20}
                color={(props.filterData !== "") ? "#0D6EFD" : ""}
                onClick={() => setWindowOpen(!isWindowOpen)}
            />

            {isWindowOpen &&
                <div className="settings-window" ref={wrapperRef}>
                    <Form.Label>Поиск</Form.Label>
                    <Form.Control
                        ref={inputRef}
                        defaultValue={props.filterData}
                        type="text"
                    />

                    <div className="control-buttons">
                        <Button onClick={() => applyFilter()}>OK</Button>
                        <Button onClick={() => clearFilter()}>Сбросить</Button>
                    </div>
                </div>
            }

        </div>
    );
}