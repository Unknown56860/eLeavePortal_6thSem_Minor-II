export async function postJson(url, data){
    const res = await fetch(url, {
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
