import i18n from "i18next";
import {initReactI18next} from "react-i18next";
import {CzechTranslations} from "./translations";

const resources = {
    cz: {
        translation: CzechTranslations
    }
};

i18n
    .use(initReactI18next)
    .init({
        resources,
        lng: "cz",
        fallbackLng: "cz",

        keySeparator: false,

        interpolation: {
            escapeValue: false
        }
    });

export default i18n;