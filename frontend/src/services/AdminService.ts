import {Device} from "../entities/Device";
import API from "../utils/API";
import {Sensor} from "../entities/Sensor";
import {Measure} from "../entities/Measure";

export async function getDevicesAdmin(): Promise<Device[]> {
    let response = await API.get("api/admin/devices")
    return await response.data;
}

export async function getDeviceAdmin(id: number): Promise<Device> {
    let response = await API.get("api/admin/devices/" + id)
    return await response.data;
}

export async function addDeviceAdmin(device: Device): Promise<Device> {
    let response = await API.post("api/admin/devices", device)
    return await response.data;
}

export async function deleteDeviceAdmin(id: number): Promise<void> {
    let response = await API.delete("api/admin/devices/" + id)
    await response.data;
}

export async function getDevicesSensorsAdmin(id: number): Promise<Sensor[]> {
    let response = await API.get("api/admin/devices/" + id + "/sensors")
    return await response.data;
}

export async function getSensorAdmin(id: number): Promise<Sensor> {
    let response = await API.get("api/admin/sensors/" + id)
    return await response.data;
}

export async function addSensorAdmin(sensor: Sensor, id: number): Promise<Device> {
    let response = await API.post("api/admin/devices/" + id, sensor)
    return await response.data;
}

export async function deleteSensorAdmin(id: number): Promise<void> {
    let response = await API.delete("api/admin/devices/" + id)
    await response.data;
}

export async function getMeasuresAdmin(id: number): Promise<Measure[]> {
    let response = await API.get("api/admin/sensors/" + id + "/measures")
    return await response.data;
}