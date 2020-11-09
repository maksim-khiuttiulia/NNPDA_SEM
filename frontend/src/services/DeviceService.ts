import {Device} from "../entities/Device";
import API from "../utils/API";
import {Sensor} from "../entities/Sensor";
import {Measure} from "../entities/Measure";


export async function getDevices(): Promise<Device[]> {
    let response = await API.get("api/devices")
    return await response.data;
}

export async function getDevice(id: number): Promise<Device> {
    let response = await API.get("api/devices/" + id)
    return await response.data;
}

export async function addDevice(device: Device): Promise<Device> {
    let response = await API.post("api/devices", device)
    return await response.data;
}

export async function updateDevice(id: number, device: Device): Promise<Device> {
    let response = await API.put("api/devices/" + id, device)
    return await response.data;
}

export async function deleteDevice(id: number): Promise<void> {
    let response = await API.delete("api/devices/" + id)
    await response.data;
}


export async function getSensors(deviceId: number): Promise<Sensor[]> {
    let response = await API.get("api/devices/" + deviceId + "/sensors")
    return await response.data;
}

export async function getSensor(sensorId: number): Promise<Sensor> {
    let response = await API.get("api/sensors/" + sensorId)
    return await response.data;
}

export async function createSensor(sensor: Sensor, deviceId: number): Promise<Sensor> {
    let response = await API.post("api/devices/" + deviceId + "/sensors", sensor)
    return await response.data;
}

export async function updateSensor(sensorId: number, sensor: Sensor): Promise<Sensor> {
    let response = await API.put("api/sensors/" + sensorId, sensor)
    return await response.data;
}

export async function deleteSensor(sensorId: number): Promise<void> {
    let response = await API.delete("api/sensors/" + sensorId)
    await response.data;
}

export async function getMeasures(sensorId: number): Promise<Measure[]> {
    let response = await API.get("api/sensors/" + sensorId + "/measures")
    return await response.data;
}