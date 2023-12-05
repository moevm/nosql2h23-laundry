import "./DateRangeFilter.scss"
import React, {useEffect, useRef, useState} from "react";
import {FunnelFill} from "react-bootstrap-icons";
import {Button, Form} from "react-bootstrap";
import DatePicker from "react-date-picker";

type dateFilterProps = {
    filterData: { start: Date; end: Date; } | null,
    setFilterData: React.Dispatch<React.SetStateAction<{ start: Date; end: Date; } | null>>
}

type ValuePiece = Date | null;

type Value = ValuePiece | [ValuePiece, ValuePiece];

export function DateRangeFilter(props: dateFilterProps) {
    function applyFilter() {

        if (startDate === null && endDate !== null) {
            setInvalid({
                start: true,
                end: false
            })
            return;
        } else if (startDate !== null && endDate === null) {
            setInvalid({
                start: false,
                end: true
            })
            return;
        }

        setInvalid({
            start: false,
            end: false
        })

        if (startDate === null && endDate === null) {
            props.setFilterData(null)
        } else {
            props.setFilterData({
                start: new Date(startDate!),
                end: new Date(endDate!)
            })
        }

        setWindowOpen(false);
    }

    function clearFilter() {
        setInvalid({
            start: false,
            end: false
        })
        setStartDate(null);
        setEndDate(null);

        props.setFilterData(null);

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

    const [startDate, setStartDate] = useState<ValuePiece>(
        (props.filterData !== null) ? new Date(props.filterData!.start) : null);
    const [endDate, setEndDate] = useState<ValuePiece>(
        (props.filterData !== null) ? new Date(props.filterData!.end) : null
    );

    useEffect(() => {
        if (props.filterData !== null) {
            setStartDate(new Date(props.filterData!.start));
            setEndDate(new Date(props.filterData!.end));
        } else {
            setStartDate(null);
            setEndDate(null);
        }
    }, [props.filterData]);

    const [isInvalid, setInvalid] = useState({
        start: false,
        end: false
    });


    return (
        <div className="date-filter-wrapper">
            <FunnelFill
                size={20}
                color={(props.filterData !== null) ? "#0D6EFD" : ""}
                onClick={() => setWindowOpen(!isWindowOpen)}
            />

            {isWindowOpen &&
                <div className="settings-window" ref={wrapperRef}>
                    <Form.Label>Поиск</Form.Label>


                    <DatePicker
                        className={isInvalid.start ? "invalid" : ""}
                        onChange={(value: Value) => {
                            if (!Array.isArray(value))
                                setStartDate(value)
                        }}
                        value={startDate}
                        maxDate={(endDate === null) ? undefined : endDate}
                        returnValue="start"
                    />

                    <DatePicker
                        className={isInvalid.end ? "invalid" : ""}
                        onChange={(value: Value) => {
                            if (!Array.isArray(value))
                                setEndDate(value)
                        }}
                        value={endDate}
                        minDate={(startDate === null) ? undefined : startDate}
                        returnValue="end"
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