export interface Location {
    id: number,
    alias: string,
    street: string,
    city: string,
    buildingNumber: string,
}

export function getFormattedLocation(location: Location): string {
    return location.street + " " + location.buildingNumber + ", " + location.city + " (" + location.alias + " )"
}

export function getLabel(location: Location) {
    return location.alias;
}

export function getID(location: Location) {
    return location.id;
}