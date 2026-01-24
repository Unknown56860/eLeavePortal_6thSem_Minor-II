import {initHomeContact} from "./pages/home.js";

const page = document.body.dataset.page;


switch (page){
    case "home":
        initHomeContact();
        break;

    default:
        console.log("No document.body.dataset.page found!");
}

console.log("Page:", page);
console.log("Main script loaded.");