import "./SelectFilter.scss"
import React, {useEffect, useRef, useState} from "react";
import {FunnelFill} from "react-bootstrap-icons";
import {Button, Form} from "react-bootstrap";

type selectFilterProps = {
    filterData: { [key: string]: boolean },
    setFilterData: React.Dispatch<React.SetStateAction<{ [key: string]: boolean }>>
}

export function SelectFilter(props: selectFilterProps) {

    function applyFilter() {

        let newObject: { [key: string]: boolean } = {};

        for (const refArrayElement in refInputs.current) {
            newObject[refArrayElement] = refInputs.current[refArrayElement].checked;
        }

        props.setFilterData(newObject);

        setWindowOpen(false);
    }

    function clearFilter() {
        let newObject: { [key: string]: boolean } = {};

        for (const refArrayElement in refInputs.current) {
            refInputs.current[refArrayElement].checked = false;

            newObject[refArrayElement] = false;
        }

        props.setFilterData(newObject);

        setWindowOpen(false);
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


    let valuesArray: string[] = [];
    let refInputs = useRef<{ [key: string]: HTMLInputElement }>({});
    let isEmpty: boolean = true;

    for (const key in props.filterData) {

        let value: boolean = props.filterData[key];
        if (isEmpty && value) {
            isEmpty = false
        }

        valuesArray.push(key);
    }

    return (
        <div className="select-filter-wrapper">
            <FunnelFill
                size={20}
                color={(!isEmpty) ? "#0D6EFD" : ""}
                onClick={() => setWindowOpen(!isWindowOpen)}
            />

            {isWindowOpen &&
                <div className="settings-window" ref={wrapperRef}>
                    <Form.Label>Поиск</Form.Label>

                    <div className="checks">
                        {valuesArray.map((value) => (
                            <Form.Check key={value}
                                        type={"checkbox"}
                                        ref={(ref: HTMLInputElement) => {
                                            refInputs.current[value] = ref
                                        }}
                                        label={value}
                                        defaultChecked={props.filterData[value]}
                                        size={10}
                            />
                        ))}
                    </div>

                    <div className="control-buttons">
                        <Button onClick={() => applyFilter()}>OK</Button>
                        <Button onClick={() => clearFilter()}>Сбросить</Button>
                    </div>
                </div>
            }

        </div>
    );
}