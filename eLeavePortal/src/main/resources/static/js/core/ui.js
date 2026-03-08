export function toggleMenu(menuId) {
    const menu = document.getElementById(menuId);

    if (!menu) return;

    menu.style.display =
        menu.style.display === "block" ? "none" : "block";
}
