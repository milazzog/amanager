$(document).foundation();

$(document).on("pfAjaxComplete", function() {
    var $messages = $(".ui-messages div");

    if ($messages.length) {
        var readingTimeMillis = 3000;

        setTimeout(function() {
            $messages.slideUp();
        }, readingTimeMillis);
    }
});



