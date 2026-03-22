
function getCsrf() {
    const token = document.querySelector('meta[name="_csrf"]')?.content;
    const header = document.querySelector('meta[name="_csrf_header"]')?.content;
    
    return {token, header};
}

export async function csrfFetch(url, options={}) {
    const {token, header} = getCsrf();

    const config = {
        credentials: "include",
        ...options,
        headers: {
            ...(options.headers || {}),
            ...(token && header ? {[header]:token} : {})
        }
    };

    return fetch(url, config);
}

export async function postJson(url, data){
    const res = await csrfFetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const body = await res.json();

    if(!res.ok){
        throw new Error(body.message || "Request failed.");
    }

    return body;
}

export async function postForm(url, formData) {
    return csrfFetch(url, {
        method: "POST",
        body: formData
    });
}
