import {Location} from "./Location";

export interface Device {
    id: number,
    name?: string,
    location: Location
}