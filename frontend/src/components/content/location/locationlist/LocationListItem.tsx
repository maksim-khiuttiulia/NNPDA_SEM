import React, {FC, PropsWithChildren} from "react";
import "./LocationListItem.css"
import {getFormattedLocation, Location} from "../../../../entities/Location";


interface Props {
    location: Location
}

const LocationListItem: FC<Props> = (props: PropsWithChildren<Props>) => {
    let location: Location = props.location;

    return (
        <div className="card">
            <div className="card-body">
                <h6 className="card-subtitle mb-2 text-muted">{getFormattedLocation(location)}</h6>
            </div>
        </div>
    )
}

export default LocationListItem;
