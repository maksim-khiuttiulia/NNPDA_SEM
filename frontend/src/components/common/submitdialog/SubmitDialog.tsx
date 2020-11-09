import React, {Component} from "react";
import {Button, Modal, ModalBody, ModalFooter} from "react-bootstrap";
import ModalHeader from "react-bootstrap/ModalHeader";
import "./SubminDialog.css"
import {withTranslation, WithTranslation} from "react-i18next";

interface Props extends WithTranslation {
    header: string,
    body?: string
    isOpen: boolean
    onSubmit: () => void
    onCancel: () => void
}

interface State {
    isOpen: boolean,
    header: string,
    body?: string
}

class SubmitDialog<T> extends Component<Props, State> {

    state: Readonly<State> = {
        header: this.props.header,
        body: this.props.body,
        isOpen: this.props.isOpen,
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>, snapshot?: any) {
        if (prevProps !== this.props) {
            this.setState({
                isOpen: this.props.isOpen
            })
        }
    }

    render() {
        let t = this.props.t
        return (
            <Modal className="submit-dialog-body" show={this.state.isOpen} size="lg" onHide={this.props.onCancel}>
                <ModalHeader>{this.state.header}</ModalHeader>
                <ModalBody>
                    {this.state.body}
                </ModalBody>
                <ModalFooter>
                    <Button variant="success" onClick={this.props.onSubmit} style={{minWidth: 100}}>{t("yes")}</Button>
                    <Button variant="secondary" onClick={this.props.onCancel} style={{minWidth: 100}}>{t("no")}</Button>
                </ModalFooter>
            </Modal>
        )
    }
}

export default withTranslation()(SubmitDialog);