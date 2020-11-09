import {Sensor} from "./Sensor";

export interface Measure {
    id: number,
    date: Date,
    value: number,
    sensor: Sensor
}