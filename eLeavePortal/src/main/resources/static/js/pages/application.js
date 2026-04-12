import { enhanceApplication } from "../core/ai_enhance.js";

export function initEnhanceButton() {
    const button = document.querySelector(".btn-ai-enhance");
    if (!button) return;

    button.addEventListener("click", async () => {
        const reasonInput = document.querySelector("[name='reason']");
        const explanationInput = document.querySelector("[name='explanation']");

        const reason = reasonInput.value;
        const explanation = explanationInput.value;

        try {
            button.disabled = true;

            const data = await enhanceApplication({ reason, explanation });

            explanationInput.value = data.result;

        } catch (err) {
            console.error(err);
            alert("AI enhancement failed");
        } finally {
            button.disabled = false;
        }
    });
}
