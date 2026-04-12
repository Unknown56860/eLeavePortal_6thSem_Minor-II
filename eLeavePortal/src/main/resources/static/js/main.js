import { initLogin, initRegister } from "./core/auth.js";
import { initEnhanceButton } from "./pages/application.js";
import { initHomeContact } from "./pages/home.js";

const page = document.body.dataset.page;

switch (page){
    case "home":
        initHomeContact();
        break;
    case "login":
        initLogin();
        break;
    case "register":
        initRegister();
        break;
    case "applicationUpdate":
        initEnhanceButton();
        break;

    default:
        console.log("No document.body.dataset.page found!");
}

console.log("Page:", page);
