import React, {ReactNode} from "react";
import LocationListItem from "./LocationListItem";
import {WithTranslation, withTranslation} from "react-i18next";
import Loader from "../../loader/Loader";
import ErrorMessage from "../../../common/errormessage/ErrorMessage";
import {Location} from "../../../../entities/Location";
import {addLocation, getLocations} from "../../../../services/LocationService";
import AddLocationDialog from "../addlocation/AddLocationDialog";
import CurrentUserStore from "../../../../storage/CurrentUserStore";

interface Props extends WithTranslation {

}

interface State {
    locations: Location[]

    addNewDialogOpen: boolean

    isLoading: boolean
    isError: boolean
}


class LocationList extends React.Component<Props, State> {
    state: Readonly<State> = {
        locations: [],

        addNewDialogOpen: false,
        isLoading: false,
        isError: false,
    }

    componentDidMount() {
        this.loadDevices();
    }

    loadDevices = (): void => {
        this.setState({isLoading: true})
        getLocations().then(value => {
            this.setState({locations: value, isLoading: false});
        }).catch(reason => {
            this.setState({isLoading: false, isError: true,})
        })
    }

    onAddNew = (): void => {
        this.setState({addNewDialogOpen: true})
    }

    onAddNewSubmit = (location: Location) => {
        this.setState({addNewDialogOpen: false, isLoading: true})
        addLocation(location).then(value => {
            this.loadDevices();
            getLocations().then(data => {
                CurrentUserStore.locations = data;
            })
        }).catch(reason => {
            this.setState({isLoading: false, isError: true})
        })
    }

    onAddNewClinicCancel = () => {
        this.setState({addNewDialogOpen: false})
    }

    _renderLocationsList = (): ReactNode => {
        let elements: ReactNode[] = this.state.locations.map(location => {
            return <LocationListItem location={location} key={location.id}/>
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
                <AddLocationDialog onSubmit={this.onAddNewSubmit} onCancel={this.onAddNewClinicCancel}
                                     isOpen={this.state.addNewDialogOpen}/>
                <div className="row mb-5">
                    <div className="col">
                        <h1>{t("Locations")}</h1>
                    </div>
                    <div className="col d-flex justify-content-end align-items-center">
                        <button type="button" className="btn btn-success px-4"
                                onClick={this.onAddNew}>{t("add")}</button>
                    </div>
                </div>
                {this._renderLocationsList()}
            </div>
        );
    }
}

export default withTranslation()(LocationList)