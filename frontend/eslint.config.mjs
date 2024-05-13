import globals from "globals";
import pluginReactConfig from "eslint-plugin-react/configs/recommended.js";

export default [
    pluginReactConfig,
    {
        languageOptions: {
            globals: globals.browser
        },
        settings: {
            react: {
                version: "detect"
            }
        },
        rules: {
            "react/prop-types": "off",
            "react/display-name": "off",
            "semi": 2
        }
    },
];