import {UserRole} from "./Auth";
import {Location} from "./Location";

export default interface User {
    id?: number
    username: string,
    email: string,
    firstname: string,
    lastname: string,
    role: UserRole
    locations: Location[]
}
