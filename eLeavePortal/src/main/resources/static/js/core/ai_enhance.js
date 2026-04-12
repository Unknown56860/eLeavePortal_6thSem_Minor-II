import { csrfFetch } from "./http.js";

export async function enhanceApplication({ reason, explanation }) {
    const res = await csrfFetch("/application/enhance", {
        method: "POST",
        headers: { 
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            reason,
            explanation
        })
    });

    if (!res.ok) {
        throw new Error("Enhancement request failed");
    }

    return res.json();
}
