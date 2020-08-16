$(document).ready(function () {
    $("button[data-dismiss='edit']").on("click", function () {
        $("button[data-dismiss='edit']").addClass("d-none");
        $(this).parent().children("button[type='submit']").removeClass("d-none");
        $(this).parent().find('input').attr('disabled', false);
    });
});