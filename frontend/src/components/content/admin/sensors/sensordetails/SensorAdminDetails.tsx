import React, {Component, ReactNode} from "react";
import {RouteComponentProps, withRouter} from "react-router";
import {withTranslation, WithTranslation} from "react-i18next";
import {getDataTypeFormatted, getTypeTranslated, Sensor} from "../../../../../entities/Sensor";
import {Measure} from "../../../../../entities/Measure";
import {deleteSensor, getMeasures, getSensor} from "../../../../../services/DeviceService";
import SubmitDialog from "../../../../common/submitdialog/SubmitDialog";
import Loader from "../../../loader/Loader";
import ErrorMessage from "../../../../common/errormessage/ErrorMessage";


interface Props extends RouteComponentProps<MatchParams>, WithTranslation {

}

interface MatchParams {
    id: string;
}

interface State {
    isLoading: boolean,
    isOpenDeleteDialog: boolean
    sensor: Sensor
    measures: Measure[]

    isError: boolean,
    errorText?: string
}

class SensorAdminDetails extends Component<Props, State> {

    state: Readonly<State> = {
        isLoading: true,
        isError: false,
        isOpenDeleteDialog: false,
        sensor: {} as Sensor,
        measures: []
    }

    componentDidMount() {
        this.loadData();
    }

    loadData = () => {
        let id = Number(this.props.match.params.id);
        let sensorPromise = getSensor(id);
        let measurePromise = getMeasures(id);

        Promise.all([sensorPromise, measurePromise]).then(([sensor, measure]) => {
            this.setState({isLoading: false, sensor: sensor, measures: measure})
        }).catch(reason => {
            this.setState({isError: true, isLoading: false});
        })
    }

    private onDeleteButtonClick = (): void => {
        this.setState({isOpenDeleteDialog: true})
    }

    private onDeleteSubmit = (): void => {
        this.setState({isOpenDeleteDialog: false, isLoading: true})
        let id = Number(this.props.match.params.id);
        deleteSensor(id).then(resp => {
            this.props.history.goBack()
        }).catch(reason => {
            this.setState({isError: true, isLoading: false})
        })
    }

    private onDeleteCancel = (): void => {
        this.setState({isOpenDeleteDialog: false})
    }

    private renderDeleteDialog = (): ReactNode => {
        let header: string = "Delete sensor";
        let body: string = "Delete sensor: " + this.state.sensor.name + " and measures?";

        return <SubmitDialog header={header} body={body} isOpen={this.state.isOpenDeleteDialog}
                             onSubmit={this.onDeleteSubmit} onCancel={this.onDeleteCancel}/>
    }

    private renderMeasureList = () => {
        let rows = this.state.measures.map(s => {
            return (<div className="card">
                <div className="card-header">
                    <h6 className="card-title">{Math.round(s.value * 100) / 100 + getDataTypeFormatted(s.sensor.type) + " " + new Date(s.date).toUTCString()}</h6>
                </div>
            </div>)
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
        let sensor: Sensor = this.state.sensor;
        if (this.state.isLoading) {
            return <Loader show={true}/>
        }
        return (
            <div>
                <ErrorMessage show={this.state.isError}/>
                {this.renderDeleteDialog()}
                <div className="row mb-5">
                    <div className="col">
                        <h1>{sensor.name}</h1>
                        <h6>{getTypeTranslated(sensor.type)}</h6>
                    </div>
                    <div className="col d-flex justify-content-end align-items-center">
                        <button type="button" className="btn btn-danger px-4"
                                onClick={this.onDeleteButtonClick}>{t("delete")}</button>
                    </div>
                </div>


                <div className="col">
                    <h3>Measures</h3>
                    {this.renderMeasureList()}
                </div>
            </div>
        )
    }
}

export default withTranslation()(withRouter(SensorAdminDetails))