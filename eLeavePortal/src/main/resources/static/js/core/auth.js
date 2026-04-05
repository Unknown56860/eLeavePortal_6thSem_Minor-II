import { csrfFetch, postJson, postForm } from "./http.js";

export function initLogin() {
    const form = document.getElementById("loginForm");
    const responseBox = document.getElementById("loginResponse");

    // show Spring Security ?error
    const params = new URLSearchParams(window.location.search);
    if (params.has("error")) {
        responseBox.className = "response-failure";
        responseBox.textContent = "Invalid username or password.";
    }

    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const body = new URLSearchParams();
        body.append("userName", form.userName.value);
        body.append("password", form.password.value);

        try {
            const response = await postForm("/api/auth/login", body);

            if(response.redirected){
                window.location.href = response.url;
                return;
            }

            if(!response.ok) {
                throw new Error(response.message || "Invalid username or passsword.");
            }
            
        } catch(error) {
            responseBox.className = "response-failure";
            responseBox.textContent = 
                error.message || "Something went wrong. Please try again later.";
        }

        // Not needed, Spring Security needs 'form' and not the JSON.
        // const payload = {
        //     userName: form.userName.value,
        //     password: form.password.value
        // };

        // try {
        //     const response = await postJson("/api/auth/login", payload);

        //     responseBox.className = "response-success";
        //     responseBox.textContent = response.message;
        //     window.location.href = "/application/all";
        // } catch (error) {
        //     responseBox.className = "response-failure";
        //     responseBox.textContent =
        //         error.message || "Something went wrong. Please try again.";
        // }
    });
}

export function initRegister() {
    const form = document.getElementById("registerForm");
    const responseBox = document.getElementById("registerResponse");

    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const password = form.nPassword.value;
        const confirmPassword = form.cPassword.value;

        if (password !== confirmPassword) {
            responseBox.className = "response-failure";
            responseBox.textContent = "Passwords do not match.";
            return;
        }

        const payload = {
            userName: form.userName.value,
            password: password,
            role: form.userRole.value
        };

        try {
            const response = await postJson("/api/auth/register", payload);
            responseBox.className = "response-success";
            responseBox.textContent = response.message;
            window.location.href = "/login";
        } catch (error) {
            responseBox.className = "response-failure";
            responseBox.textContent =
                error.message || "Something went wrong. Please try again.";
        }
    });
}

export async function getSession() {
  const response = await csrfFetch("/session", {
    method: "GET",
    credentials: "include"
  });

  if (!response.ok) {
    throw new Error("Failed to fetch session data");
  }

  return await response.json();
}

export async function logout(path="/") {
    try {
        const response = await csrfFetch('/api/auth/logout', {
            method: 'POST',
            credentials: 'include'
        });

        if (!response.ok) {
            throw new Error('Logout request failed');
        }

        // const data = await response.json();
        // console.log(data.message);

        window.location.replace(path);

    } catch (error) {
        console.error('Error during logout:', error);
    }
}
