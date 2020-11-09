import React, {Component, ReactNode} from "react";
import {RouteComponentProps, withRouter} from "react-router";
import {withTranslation, WithTranslation} from "react-i18next";
import {NavLink} from "react-router-dom";
import {Device} from "../../../../../entities/Device";
import {getTypeTranslated, Sensor} from "../../../../../entities/Sensor";
import {
    addSensorAdmin,
    deleteDeviceAdmin,
    getDeviceAdmin,
    getDevicesSensorsAdmin
} from "../../../../../services/AdminService";
import {RouterConstants} from "../../../../../routes/RouterConstants";
import SubmitDialog from "../../../../common/submitdialog/SubmitDialog";
import Loader from "../../../loader/Loader";
import ErrorMessage from "../../../../common/errormessage/ErrorMessage";
import AddEditSensorDialog from "../../../sensors/addeditsensor/AddEditSensorDialog";
import {getFormattedLocation} from "../../../../../entities/Location";


interface Props extends RouteComponentProps<MatchParams>, WithTranslation {

}

interface MatchParams {
    id: string;
}

interface State {
    isLoading: boolean,
    isOpenDeleteDialog: boolean
    isOpenModifyDialog: boolean
    isOpenAddSensorDialog: boolean

    device: Device
    sensors: Sensor[]

    isError: boolean,
    errorText?: string
}

class DeviceAdminDetails extends Component<Props, State> {

    state: Readonly<State> = {
        isLoading: true,
        isError: false,
        isOpenDeleteDialog: false,
        device: {} as Device,
        sensors: [],

        isOpenAddSensorDialog: false,
        isOpenModifyDialog: false
    }

    componentDidMount() {
        this.loadData();
    }

    loadData = () => {
        let id = Number(this.props.match.params.id);
        let devicePromise = getDeviceAdmin(id);
        let sensorsPromise = getDevicesSensorsAdmin(id);

        Promise.all([devicePromise, sensorsPromise]).then(([device, sensors]) => {
            this.setState({isLoading: false, device: device, sensors: sensors})
        }).catch(reason => {
            this.setState({isError: true, isLoading: false});
        })
    }

    private onAddSensorButtonClick = (): void => {
        this.setState({isOpenAddSensorDialog: true})
    }

    private onAddSensorSubmit = (sensor: Sensor): void => {
        this.setState({isLoading: true, isOpenAddSensorDialog: false})
        addSensorAdmin(sensor, this.state.device.id).then(data => {
            this.loadData();
        }).catch(reason => {
            this.setState({isLoading: false, isOpenAddSensorDialog: false, isError: true})
        })
    }

    private onAddSensorCancel = (): void => {
        this.setState({isOpenAddSensorDialog: false})
    }

    private onDeleteButtonClick = (): void => {
        this.setState({isOpenDeleteDialog: true})
    }

    private onDeleteSubmit = (): void => {
        this.setState({isOpenDeleteDialog: false, isLoading: true})
        let id = Number(this.props.match.params.id);
        deleteDeviceAdmin(id).then(resp => {
            this.props.history.push(RouterConstants.adminDevices);
        }).catch(reason => {
            this.setState({isError: true, isLoading: false})
        })
    }

    private onDeleteCancel = (): void => {
        this.setState({isOpenDeleteDialog: false})
    }

    private renderDeleteDialog = (): ReactNode => {
        let header: string = "Delete device";
        let body: string = "Delete device: " + this.state.device.name + " and sensors?";

        return <SubmitDialog header={header} body={body} isOpen={this.state.isOpenDeleteDialog}
                             onSubmit={this.onDeleteSubmit} onCancel={this.onDeleteCancel}/>
    }

    private renderSensorList = () => {
        let rows = this.state.sensors.map(s => {
            const URL = RouterConstants.adminSensor.replace(":id", String(s.id))
            return <NavLink to={URL} className="card-link mb-3">
                <div className="card">
                    <div className="card-header">
                        <h5 className="card-title">{s.name + " " + getTypeTranslated(s.type)}</h5>
                    </div>
                </div>
            </NavLink>
        })

        return (
            <div className="row">
                <div className="col">
                    {rows}
                </div>
            </div>
        )
    }

    render() {
        let t = this.props.t;
        let device: Device = this.state.device;
        if (this.state.isLoading) {
            return <Loader show={true}/>
        }
        return (
            <div>
                <ErrorMessage show={this.state.isError}/>
                <AddEditSensorDialog isOpen={this.state.isOpenAddSensorDialog} onSubmit={this.onAddSensorSubmit}
                                     onCancel={this.onAddSensorCancel}/>
                {this.renderDeleteDialog()}
                <div className="row mb-5">
                    <div className="col">
                        <h1>{device.name}</h1>
                        <h6>{getFormattedLocation(device.location)}</h6>
                        <h6>{device.owner.username}</h6>
                    </div>
                    <div className="col d-flex justify-content-end align-items-center">

                    </div>
                </div>


                <div className="col">
                    <h3>Sensors</h3>
                    {this.renderSensorList()}
                </div>
            </div>
        )
    }
}

export default withTranslation()(withRouter(DeviceAdminDetails));