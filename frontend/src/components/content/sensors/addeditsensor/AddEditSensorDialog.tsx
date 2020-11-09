import React from "react";
import {Col, Dropdown, Form, Row} from "react-bootstrap";
import AddEditDialog from "../../../common/addeditdialog/AddEditDialog";
import i18n from "../../../../i18n"
import {getTypeTranslated, Sensor, SensorType} from "../../../../entities/Sensor";
import DropdownItem from "react-bootstrap/DropdownItem";


export default class AddEditSensorDialog extends AddEditDialog<Sensor> {

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

    private onSelectType = (type: SensorType) => {
        this.setState({
            item: {
                ...this.state.item,
                type: type
            }
        })
    }

    private renderChangeType = () => {
        if (!this.props.item) {
            return (
                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("Sensor type")}
                    </Form.Label>
                    <Col sm="10">
                        <Dropdown style={{width: "100%"}}>
                            <Dropdown.Toggle variant="success" id="dropdown-basic" style={{width: "100%"}}>
                                {getTypeTranslated(this.state.item.type)}
                            </Dropdown.Toggle>

                            <Dropdown.Menu style={{width: "100%"}} alignRight>
                                {Object.values(SensorType).map(((value, index) => {
                                    return <DropdownItem style={{width: "100%"}}
                                                         key={index} onClick={(event => {
                                        this.onSelectType(value)
                                    })}
                                    >
                                        {getTypeTranslated(value)}
                                    </DropdownItem>
                                }))}
                            </Dropdown.Menu>
                        </Dropdown>
                    </Col>
                </Form.Group>
            )
        }
    }

    protected renderForm(): React.ReactNode {
        let {name} = this.state.item
        return (
            <Form>
                <Form.Group as={Row}>
                    <Form.Label column sm="2">
                        {i18n.t("Sensor name")}
                    </Form.Label>
                    <Col sm="10">
                        <Form.Control type="text" onChange={this.onChangeName} value={name || ""}
                                      placeholder={i18n.t("Sensor")}/>
                    </Col>
                </Form.Group>
                {this.renderChangeType()}

            </Form>
        );
    }
}