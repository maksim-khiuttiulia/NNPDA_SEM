import React from "react";
import Checkbox from "../../components/common/checkbox/Checkbox";
import "./LoginPage.css"
import {login} from "../../services/AuthService";
import {RouteComponentProps, withRouter} from "react-router-dom";
import Loader from "../../components/content/loader/Loader";
import ErrorMessage from "../../components/common/errormessage/ErrorMessage";
import {RouterConstants} from "../../routes/RouterConstants";
import {withTranslation, WithTranslation} from "react-i18next";

interface Props extends RouteComponentProps, WithTranslation {

}

interface State {
    username: string,
    password: string,
    rememberMe: boolean,

    isLoading: boolean
    isError: boolean
}

class LoginPage extends React.Component<Props, State> {
    state: Readonly<State> = {
        username: "",
        password: "",
        rememberMe: false,

        isLoading: false,
        isError: false
    }

    _onKeyUp = (e: any) => {
        if (e.which === 13) {
            this._onSubmit();
        }
    }

    _onSubmit = (): void => {
        let {username, password, rememberMe} = this.state
        this.setState({isLoading: true})
        login(username, password, rememberMe).then(value => {
            this.setState({isLoading: false})
            this.props.history.push(RouterConstants.home)
        }).catch(reason => {
            this.setState({isLoading: false, isError: true})
        })
    }

    _onUsernameInput = (e: React.ChangeEvent<HTMLInputElement>): void => {
        this.setState({username: e.target.value, isError: false})
    }

    _onPasswordInput = (e: React.ChangeEvent<HTMLInputElement>): void => {
        this.setState({password: e.target.value, isError: false})
    }

    _onRememberMe = (value: boolean): void => {
        this.setState({rememberMe: value})
    }

    render() {
        let {t} = this.props
        return (
            <div className="h-100">
                <Loader show={this.state.isLoading}/>
                <ErrorMessage show={this.state.isError} text={t("lpCredentialsError")}/>
                <header className="pb-5">
                </header>
                <main>
                    <div className="container login-page-container">
                        <div className="row">
                            <div className="col-md-4 offset-md-4">
                                <h4 className="text-center mb-3">{t("Sign in")}</h4>

                                <div className="mb-3">
                                    <input type="text" className="form-control" placeholder={t("Username")}
                                           value={this.state.username} onChange={this._onUsernameInput}/>
                                </div>

                                <div className="mb-3">
                                    <input type="password" className="form-control" placeholder={t("Password")}
                                           value={this.state.password} onChange={this._onPasswordInput}/>
                                </div>

                                <div className="mb-3">
                                    <Checkbox text={t('Remember me')} isChecked={this.state.rememberMe}
                                              onChange={this._onRememberMe}/>
                                </div>

                                <button className="btn btn-success btn-lg btn-block focus" type="submit"
                                        onClick={this._onSubmit} onInput={this._onKeyUp}>{t('Sign in')}</button>

                            </div>
                        </div>
                    </div>
                </main>
            </div>
        )
    }
}

export default withRouter(withTranslation()(LoginPage))