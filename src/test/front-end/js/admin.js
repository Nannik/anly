$(document).ready(function() {
    var editor = new FroalaEditor('#fr-editor')

    $("table tr").on("click", function() {
        $(".products").addClass("d-none");
        $(`.products[data-id='${$(this).attr("data-id")}']`).removeClass('d-none');
    });

    $("#sidebar button").on("click", function() {
        $("#sidebar button").removeClass("btn-custom-secondary-2");
        $("#sidebar button").addClass("btn-custom-secondary-1");

        $(this).removeClass("btn-custom-secondary-1");
        $(this).addClass("btn-custom-secondary-2");

        $(".group").addClass("d-none");
        $(`.group[data-group='${$(this).attr("data-group")}']`).removeClass('d-none');
    });
});