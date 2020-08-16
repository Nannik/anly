$(document).ready(() => {
    $(".checkout-variant__inner").on("click", function() {
        $(this).parents(".checkout-block").find(".checkout-variant__selected input[type='radio']").attr("checked", false);
        $(this).parents(".checkout-block").find(".checkout-variant__selected").removeClass("checkout-variant__selected");

        $(this).addClass("checkout-variant__selected");
        $(this).find("input[type='radio']").attr("checked", true);

        let dataManySelect = $(this).find("input[type='radio']").attr('data-manyselect');
        $(`input[data-manyselect='${dataManySelect}']`).attr("required", false);

        if ($(this).parents(".checkout-variant").attr("id") === "courier") {
            $("#onDelivery").removeClass("d-none");
            $("#cod").addClass("d-none");
        } else {
            $("#cod").removeClass("d-none");
            $("#onDelivery").addClass("d-none");
        }

        updateSum();
    })

    function updateSum () {
        let deliveryPrice = parseInt($(".checkout-variant__selected .checkout-variant__price").attr("price"));
        let productsPrice = parseInt($("#products .total").attr("price"));

        let sum = deliveryPrice + productsPrice;

        $(".order-price").attr("price", sum);
        $(".order-price").html(`Общая цена: ${sum}`);
    }
});