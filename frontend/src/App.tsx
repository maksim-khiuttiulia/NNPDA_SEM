import React from 'react';
import "./App.css"
import LoginPage from "./pages/loginpage/LoginPage";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {RouterConstants} from "./routes/RouterConstants";
import DashboardRoute from "./routes/DashboardRoute";
import NotFound from "./components/content/notfound/NotFound";
import DeviceList from "./components/content/devices/devicelist/DeviceList"
import DeviceDetails from "./components/content/devices/devicedetails/DeviceDetails";
import {isLoggedIn, me} from "./services/AuthService";
import SensorDetails from "./components/content/sensors/sensordetails/SensorDetails";
import DeviceAdminList from "./components/content/admin/devices/devicelist/DeviceAdminList";
import DeviceAdminDetails from "./components/content/admin/devices/devicedetails/DeviceAdminDetails";
import SensorAdminDetails from "./components/content/admin/sensors/sensordetails/SensorAdminDetails";
import LocationList from "./components/content/location/locationlist/LocationList";

export default class App extends React.Component<any, any> {

    componentDidMount() {
        if (isLoggedIn()) {
            me();
        }
    }

    render() {
        return (
            <div>
                <BrowserRouter>
                    <Switch>
                        <Route exact path={RouterConstants.login} component={LoginPage}/>
                        <DashboardRoute exact path={RouterConstants.home} component={DeviceList}/>
                        <DashboardRoute exact path={RouterConstants.devices} component={DeviceList}/>
                        <DashboardRoute exact path={RouterConstants.device} component={DeviceDetails}/>
                        <DashboardRoute exact path={RouterConstants.sensor} component={SensorDetails}/>
                        <DashboardRoute exact path={RouterConstants.locations} component={LocationList}/>

                        <DashboardRoute exact path={RouterConstants.adminDevices} component={DeviceAdminList}/>
                        <DashboardRoute exact path={RouterConstants.adminDevice} component={DeviceAdminDetails}/>
                        <DashboardRoute exact path={RouterConstants.adminSensor} component={SensorAdminDetails}/>
                        <Route component={NotFound}/>
                    </Switch>
                </BrowserRouter>
            </div>)
    }
}

