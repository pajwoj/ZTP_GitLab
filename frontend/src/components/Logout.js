const Logout = () => {
    performLogout()

    return (
        <>
            <div>Logout page</div>
        </>
    );
};

function performLogout() {
    fetch("/api/users/logout",
        {
            method: "post",
        })
        .then(r => {
            r.text().then(r => {
                alert(r);
            })
        })
}
export default Logout;
