import API from "../utils/API";
import {Location} from "../entities/Location";

export async function getLocations(): Promise<Location[]> {
    let response = await API.get("api/locations")
    return await response.data;
}

export async function addLocation(location: Location): Promise<Location> {
    let response = await API.post("api/locations", location)
    return await response.data;
}