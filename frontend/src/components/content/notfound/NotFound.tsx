import React, {FC, PropsWithChildren} from "react";
import "./NotFound.css"
import Dog from "./notfoundimage.png"
import {NavLink} from "react-router-dom";
import {RouterConstants} from "../../../routes/RouterConstants";

const NotFound: FC<PropsWithChildren<any>> = () => {
    return (
        <div className="col text-center">
            <NavLink to={RouterConstants.home} className="notfound">
                <h1>4<img src={Dog} alt="0"/>4</h1>
            </NavLink>
        </div>
    )
}

export default NotFound