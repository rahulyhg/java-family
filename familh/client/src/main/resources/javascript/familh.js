var modal = false;

function openCreateUser() {
    console.log('openCreateUser()');
    modal = true;
    $("#create-user").show();
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
