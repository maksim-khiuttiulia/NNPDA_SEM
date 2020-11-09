import {Location} from "./Location";
import User from "./User";

export interface Device {
    id: number,
    name?: string,
    location: Location
    owner: User
}