import {postJson} from "../core/http.js";

export function initHomeContact(){
    const form = document.getElementById("contactForm");
    const responseBox = document.getElementById("contactResponse");

    if(!form) return;

    form.addEventListener("submit", async(e) => {
        e.preventDefault();

        const payload = {
            name: form.name.value,
            email: form.email.value,
            message: form.message.value
        };

        try{
            const response = await postJson("/contact", payload);
            responseBox.className = "response-success";
            responseBox.textContent = response.message;
            form.reset();
        }
        catch (error){
            responseBox.className = "response-failure";
            responseBox.textContent = 
                error.message || "Something went wrong. Please try again.";
        }
    })
}
