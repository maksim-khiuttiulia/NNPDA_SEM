import React from "react";
import {Redirect, Route, RouteComponentProps} from "react-router-dom";
import {isLoggedIn} from "../services/AuthService";
import {RouteProps} from "react-router";
import {RouterConstants} from "./RouterConstants";
import DashboardContainer from "../components/content/dashboardcontainer/DashboardContainer";

interface Props extends RouteProps {
    component: React.ComponentType<RouteComponentProps<any>> | React.ComponentType<any>
}

export class DashboardRoute extends Route<Props> {
    render() {
        const {component: Component, ...rest}: Props = this.props;
        if (isLoggedIn()) {
            return <Route {...rest} render={props => (
                <DashboardContainer>
                    <Component {...props} />
                </DashboardContainer>
            )}/>
        }

        return (
            <Route {...rest} render={props => <Redirect to={RouterConstants.login}/>}/>
        );
    }
}

export default DashboardRoute;