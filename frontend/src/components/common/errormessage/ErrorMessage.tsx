import React, {FC} from "react";
import {Alert} from "react-bootstrap";
import {withTranslation, WithTranslation} from "react-i18next";

interface Props extends WithTranslation {
    show: boolean,
    text?: string
}

const ErrorMessage: FC<Props> = (props => {

    let onClick = () => {
        window.location.reload();
    }

    if (props.text) {
        return (
            <Alert show={props.show} variant="warning" className="text-center w-100">
                {props.text}
            </Alert>
        )
    }

    return (
        <Alert show={props.show} variant="warning" className="text-center w-100">
            {props.t("error")}
        </Alert>
    )
})

export default withTranslation()(ErrorMessage);