import "./TableSort.scss"
import React from "react";
import {CaretDownFill, CaretUpFill} from "react-bootstrap-icons";

type tableSortParams = {
    sortName: string,
    sortState: string,
    setSortState: React.Dispatch<React.SetStateAction<string>>
}

export function TableSort(params: tableSortParams) {

    let isUpActive = (params.sortName + "_up") === params.sortState;
    let isDownActive = (params.sortName + "_down") === params.sortState;

    function setSort(type: "up" | "down") {
        params.setSortState(params.sortName + "_" + type);
    }

    return (
        <div className="sortButtons">
            <CaretUpFill
                viewBox="0 -2 16 16"
                onClick={() => setSort("up")}
                color={isUpActive ? "#0D6EFD" : ""}/>
            <CaretDownFill
                viewBox="0 2 16 16"
                onClick={() => setSort("down")}
                color={isDownActive ? "#0D6EFD" : ""}/>
        </div>
    );
}