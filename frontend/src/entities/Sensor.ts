import i18n from "../i18n"
import {Measure} from "./Measure";

export interface Sensor {
    id: number,
    name: string,
    type: SensorType,
    measures: Measure[]
}

export enum SensorType {
    THERMOMETER = "THERMOMETER",
    WATER_FLOW_SENSOR = "WATER_FLOW_SENSOR",
    ELECTRICITY_SENSOR = "ELECTRICITY_SENSOR"
}

export function getTypeTranslated(type: SensorType): string {
    if (type === SensorType.THERMOMETER) {
        return i18n.t("Thermometer")
    }
    if (type === SensorType.WATER_FLOW_SENSOR) {
        return i18n.t("Water flow sensor")
    }
    if (type === SensorType.ELECTRICITY_SENSOR) {
        return i18n.t("Electricity sensor")
    }
    return "";
}

export function getDataTypeFormatted(type: SensorType) {
    if (type === SensorType.THERMOMETER) {
        return " C";
    }
    if (type === SensorType.ELECTRICITY_SENSOR) {
        return " kW/h"
    }
    if (type === SensorType.WATER_FLOW_SENSOR) {
        return " L/h"
    }

    return ""
}