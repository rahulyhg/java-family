var modal = false;

function openCreateUser() {
    console.log('openCreateUser()');
    modal = true;
    $("#create-user").show();
}

function openLoginUser() {
    modal = true;
    $("#log-in-user").show();
}

function onPage() {
    if (modal) {
        blurPage();
    }
    else {
        unblurPage();
    }
}

function unblurPage() {
    $("#page").removeClass('blur');
    $(".popin").hide();
}

function blurPage() {
    $("#page").addClass('blur');
    modal = false;
}

function connectUser() {

    if (authenticationValid($("#log-in-user-login").val(), $("#log-in-user-password").val())) {
        onPage();
        $("#header-user-profile").show();
        $("#header-user-logout").show();
        $("#header-create-user").hide()
        $("#header-user-sign-in").hide();
    }


}

function authenticationValid(login, password) {
    return true;
}

function logoutUser() {
        $("#header-user-profile").hide();
        $("#header-user-logout").hide();
        $("#header-create-user").show()
        $("#header-user-sign-in").show();
}

