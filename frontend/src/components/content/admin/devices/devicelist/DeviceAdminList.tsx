import React, {ReactNode} from "react";
import DeviceAdminListItem from "./DeviceAdminListItem";
import {WithTranslation, withTranslation} from "react-i18next";
import {Device} from "../../../../../entities/Device";
import {addDevice} from "../../../../../services/DeviceService";
import Loader from "../../../loader/Loader";
import ErrorMessage from "../../../../common/errormessage/ErrorMessage";
import AddEditDeviceDialog from "../../../devices/addeditdevice/AddEditDeviceDialog";
import {getDevicesAdmin} from "../../../../../services/AdminService";

interface Props extends WithTranslation {

}

interface State {
    devices: Device[]

    addNewDialogOpen: boolean

    isLoading: boolean
    isError: boolean
}


class DeviceAdminList extends React.Component<Props, State> {
    state: Readonly<State> = {
        devices: [],

        addNewDialogOpen: false,
        isLoading: false,
        isError: false,
    }

    componentDidMount() {
        this.loadDevices();
    }

    loadDevices = (): void => {
        this.setState({isLoading: true})
        getDevicesAdmin().then(value => {
            this.setState({devices: value, isLoading: false});
        }).catch(reason => {
            this.setState({isLoading: false, isError: true,})
        })
    }

    onAddNewDevice = (): void => {
        this.setState({addNewDialogOpen: true})
    }

    onAddNewDeviceSubmit = (device: Device) => {
        this.setState({addNewDialogOpen: false, isLoading: true})
        addDevice(device).then(value => {
            this.loadDevices();
        }).catch(reason => {
            this.setState({isLoading: false, isError: true})
        })
    }

    onAddNewClinicCancel = () => {
        this.setState({addNewDialogOpen: false})
    }

    _renderDeviceList = (): ReactNode => {
        let elements: ReactNode[] = this.state.devices.map(device => {
            return <DeviceAdminListItem device={device} key={device.id}/>
        })

        return (
            <div className="row">
                <div className="col">
                    {elements}
                </div>
            </div>
        )
    }

    render() {
        let t = this.props.t
        return (
            <div>
                <Loader show={this.state.isLoading}/>
                <ErrorMessage show={this.state.isError}/>
                <AddEditDeviceDialog onSubmit={this.onAddNewDeviceSubmit} onCancel={this.onAddNewClinicCancel}
                                     isOpen={this.state.addNewDialogOpen}/>
                <div className="row mb-5">
                    <div className="col">
                        <h1>{t("Devices")}</h1>
                    </div>
                    <div className="col d-flex justify-content-end align-items-center">
                        <button type="button" className="btn btn-success px-4"
                                onClick={this.onAddNewDevice}>{t("add")}</button>
                    </div>
                </div>
                {this._renderDeviceList()}
            </div>
        );
    }
}

export default withTranslation()(DeviceAdminList)