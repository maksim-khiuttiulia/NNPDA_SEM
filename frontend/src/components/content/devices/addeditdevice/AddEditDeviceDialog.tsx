import React from "react";
import {Col, Form, Row} from "react-bootstrap";
import {Device} from "../../../../entities/Device";
import AddEditDialog from "../../../common/addeditdialog/AddEditDialog";
import i18n from "../../../../i18n"
import {getID, getLabel, Location} from "../../../../entities/Location";
import Combobox from "../../../common/combobox/Combobox";
import CurrentUserStore from "../../../../storage/CurrentUserStore";


export default class AddEditDeviceDialog extends AddEditDialog<Device> {

    getHeader(): string {
        if (this.props.item) {
            return i18n.t("Edit device " + this.props.item.name);
        }
        return i18n.t("Add device")
    }

    protected validate(): string | undefined {
        let {name} = this.state.item
        if (!name || name.trim().length === 0) {
            return i18n.t("Device name is empty");
        }

        return undefined;
    }

    protected onCancel(): void {
        this.props.onCancel();
    }

    protected onSubmit(): void {
        this.props.onSubmit(this.state.item);
    }

    private onChangeName = (e: any): void => {
        this.setState({
            item: {
                ...this.state.item,
                name: e.target.value
            }
        })
    }

    private onSelectLocation = (location: Location) => {
        this.setState({
            item: {
                ...this.state.item,
                location: location
            }
        })
    }

    protected renderForm(): React.ReactNode {
        let {name} = this.state.item
        return (
            <Form>
                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("Device name")}
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control type="text" onChange={this.onChangeName} value={name || ""}
                                      placeholder={i18n.t("Device")}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("Location")}
                    </Form.Label>
                    <Col sm="10">
                        <Combobox items={CurrentUserStore.locations} onSelect={this.onSelectLocation} getID={getID}
                                  getLabel={getLabel}/>
                    </Col>
                </Form.Group>
            </Form>
        );
    }
}