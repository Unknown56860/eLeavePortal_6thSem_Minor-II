import {logout} from "../core/auth.js"

const toggle = document.getElementById("userToggle");
const menu = document.querySelector(".user-menu");

if(toggle && menu){
    toggle.addEventListener("click", function (e) {
        e.stopPropagation();
        menu.classList.toggle("open");
    });

    document.addEventListener("click", function () {
        menu.classList.remove("open");
    });
}

window.logoutHandler = function(event){
    event.preventDefault();
    logout();
}
