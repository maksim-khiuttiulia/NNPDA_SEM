import React, {FC} from "react";
import "./Checkbox.css"

interface Props {
    text: string
    isChecked: boolean,
    onChange: (value: boolean) => void
}

const CheckBox: FC<Props> = (props: Props) => {

    const _onChange = (e: any) => {
        props.onChange(!props.isChecked);
    }

    return (<div className="custom-control custom-checkbox">
        <input type="checkbox" className="custom-control-input" checked={props.isChecked} onChange={_onChange}
               id={props.text}/>
        <label className="custom-control-label" htmlFor={props.text}>{props.text}</label>
    </div>)
}

export default CheckBox