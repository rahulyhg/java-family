function adjustStyle(width) {
    width = parseInt(width);
    if (width < 1280) {
        $("#size-stylesheet").attr("href", "css/width-1024.css");
    } else if (width < 1920) {
        $("#size-stylesheet").attr("href", "css/width-1280.css");
    } else {
        $("#size-stylesheet").attr("href", "css/width-1920.css");
    }
}

$(function() {
    adjustStyle($(this).width());
    $(window).resize(function() {
        adjustStyle($(this).width());
    });
});
