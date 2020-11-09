import React, {ReactNode} from "react";
import DropdownItem from "react-bootstrap/DropdownItem";
import {Dropdown} from "react-bootstrap";

interface Props<T> {
    items: T[]
    onSelect: (item: T) => void
    getLabel: (item: T) => string
    getID: (item: T) => number
}

interface State<T> {
    items: T[]
    selected?: T
}

export default class Combobox<T> extends React.Component<Props<T>, State<T>> {
    state: Readonly<State<T>> = {
        items: this.props.items,
    }

    private onClick = (item: T) => {
        if (item) {
            this.setState({selected: item});
            this.props.onSelect(item);
        }
    }

    renderItems = (): ReactNode[] => {
        return this.state.items.map(it => {
            return <DropdownItem style={{width: "100%"}} key={this.props.getID(it)} value={this.props.getID(it)}
                                 onClick={(event => {
                                     this.onClick(it)
                                 })}>{this.props.getLabel(it)}</DropdownItem>
        })
    }

    render() {
        let toogleText = this.state.selected ? this.props.getLabel(this.state.selected) : "Select"
        return (
            <div style={{width: "100%"}}>
                <Dropdown style={{width: "100%"}}>
                    <Dropdown.Toggle variant="success" id="dropdown-basic" style={{width: "100%"}}>
                        {toogleText}
                    </Dropdown.Toggle>

                    <Dropdown.Menu style={{width: "100%"}} alignRight>
                        {this.renderItems()}
                    </Dropdown.Menu>
                </Dropdown>
            </div>

        )
    }
}