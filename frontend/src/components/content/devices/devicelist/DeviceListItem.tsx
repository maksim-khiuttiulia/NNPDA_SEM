import React, {FC, PropsWithChildren} from "react";
import {NavLink} from "react-router-dom";
import "./DeviceListItem.css"
import {Device} from "../../../../entities/Device";
import {RouterConstants} from "../../../../routes/RouterConstants";

interface Props {
    device: Device
}

const DeviceListItem: FC<Props> = (props: PropsWithChildren<Props>) => {
    let device: Device = props.device;

    const URL = RouterConstants.device.replace(":id", String(device.id))
    return (
        <NavLink to={URL} className="card-link mb-3">
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{device.name}</h5>
                    <h6 className="card-subtitle mb-2 text-muted">{device.location.alias}</h6>
                </div>
            </div>
        </NavLink>
    )
}

export default DeviceListItem;
