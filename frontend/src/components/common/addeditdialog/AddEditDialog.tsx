import React, {Component, ReactNode} from "react";
import {Button, Modal, ModalBody, ModalFooter} from "react-bootstrap";
import ModalHeader from "react-bootstrap/ModalHeader";
import ErrorMessage from "../errormessage/ErrorMessage";
import "./AddEditDialog.css"
import i18n from "../../../i18n";

interface Props<T> {
    isOpen: boolean,

    item?: T
    onSubmit: (item: T) => void
    onCancel: () => void

}

interface State<T> {
    item: T

    isOpen: boolean,
    isError: boolean,
    errorMessage?: string,
}

abstract class AddEditDialog<T> extends Component<Props<T>, State<T>> {

    abstract getHeader(): string

    state: Readonly<State<T>> = {
        item: this.props.item || {} as T,
        isError: false,
        isOpen: this.props.isOpen,
    }

    componentDidUpdate(prevProps: Readonly<Props<T>>, prevState: Readonly<State<T>>, snapshot?: any) {
        if (prevProps !== this.props) {
            this.setState({
                item: this.props.item || {} as T,
                isError: false,
                isOpen: this.props.isOpen
            })
        }
    }

    protected abstract validate(): string | undefined;

    protected abstract onSubmit(): void;

    protected abstract onCancel(): void;

    protected abstract renderForm(): ReactNode;

    private _onSubmit = () => {
        let errorMessage: string | undefined = this.validate();
        if (errorMessage) {
            this.setState({isError: true, errorMessage: errorMessage})
        } else {
            this.onSubmit();
        }
    }

    private _onCancel = () => {
        this._close();
        this.onCancel();
    }

    private _close = () => {
        this.setState({
            isOpen: false,
            isError: false,
            errorMessage: ""
        })
    }

    render() {
        return (
            <Modal className="add-edit-dialog-body" show={this.state.isOpen} size="lg" onHide={this._close}>
                <ModalHeader>{this.getHeader()}</ModalHeader>
                <ErrorMessage show={this.state.isError} text={this.state.errorMessage}/>
                <ModalBody>
                    {this.renderForm()}
                </ModalBody>
                <ModalFooter>
                    <Button variant="success" onClick={this._onSubmit} style={{minWidth: 100}}>{i18n.t("save")}</Button>
                    <Button variant="secondary" onClick={this._onCancel}
                            style={{minWidth: 100}}>{i18n.t("cancel")}</Button>
                </ModalFooter>
            </Modal>
        )
    }
}

export default AddEditDialog;