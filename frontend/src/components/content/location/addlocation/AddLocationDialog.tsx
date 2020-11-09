import React from "react";
import {Col, Form, Row} from "react-bootstrap";
import AddEditDialog from "../../../common/addeditdialog/AddEditDialog";
import i18n from "../../../../i18n"
import {Location} from "../../../../entities/Location";


export default class AddLocationDialog extends AddEditDialog<Location> {

    getHeader(): string {
        if (this.props.item) {
            return i18n.t("Edit device " + this.props.item.alias);
        }
        return i18n.t("Add location")
    }

    protected validate(): string | undefined {
        let {alias, street, buildingNumber, city} = this.state.item
        if (!alias || alias.trim().length === 0) {
            return i18n.t("Alias is empty");
        }
        if (!street || street.trim().length === 0) {
            return i18n.t("Street is empty");
        }
        if (!buildingNumber || buildingNumber.trim().length === 0) {
            return i18n.t("Building number is empty");
        }
        if (!city || city.trim().length === 0) {
            return i18n.t("City is empty");
        }

        return undefined;
    }

    protected onCancel(): void {
        this.props.onCancel();
    }

    protected onSubmit(): void {
        this.props.onSubmit(this.state.item);
    }

    private onChangeAlias = (e: any): void => {
        this.setState({
            item: {
                ...this.state.item,
                alias: e.target.value
            }
        })
    }

    private onChangeStreet = (e: any): void => {
        this.setState({
            item: {
                ...this.state.item,
                street: e.target.value
            }
        })
    }

    private onChangeBuilding = (e: any): void => {
        this.setState({
            item: {
                ...this.state.item,
                buildingNumber: e.target.value
            }
        })
    }

    private onChangeCity = (e: any): void => {
        this.setState({
            item: {
                ...this.state.item,
                city: e.target.value
            }
        })
    }

    protected renderForm(): React.ReactNode {
        let {alias, street, buildingNumber, city} = this.state.item
        return (
            <Form>
                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("Location alias")}
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control type="text" onChange={this.onChangeAlias} value={alias || ""}
                                      placeholder={i18n.t("Alias")}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("Street")}
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control type="text" onChange={this.onChangeStreet} value={street || ""}
                                      placeholder={i18n.t("Street")}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("Building number")}
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control type="text" onChange={this.onChangeBuilding} value={buildingNumber || ""}
                                      placeholder={i18n.t("Building")}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("City")}
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control type="text" onChange={this.onChangeCity} value={city || ""}
                                      placeholder={i18n.t("City")}/>
                    </Col>
                </Form.Group>

            </Form>
        );
    }
}