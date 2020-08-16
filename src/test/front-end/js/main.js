$(document).ready(() => {
    $('#modal-message').modal('show');

    for (let i = 1; i <= 2; i++) {
        sumTotalPrice(i);
    }

    $(".select-num").on("click", ".increment", (e) => {
        let elem = $(e.delegateTarget).children(".select-num-val");

        elem.html(parseInt(elem.html()) + 1);

        sumTotalPrice($(e.delegateTarget).parent().parent().attr("group"));
    });

    $(".select-num").on("click", ".decrement", (e) => {
        let elem = $(e.delegateTarget).children(".select-num-val");

        if (parseInt(elem.html()) !== 1) elem.html(parseInt(elem.html()) - 1);

        sumTotalPrice($(e.delegateTarget).parent().parent().attr("group"));
    });

    function sumTotalPrice(group) {
        let sum = 0;

        let htmlPrices = $(`.product-linear[group=${group}]`).find(".product-linear-price");
        let htmlNums = $(`.product-linear[group=${group}]`).find(".select-num-val");

        for (let i = 0; i < htmlPrices.length; i++) {
            sum += parseInt($(htmlPrices[i]).attr("price")) * parseInt($(htmlNums[i]).html());
        }

        $(`.total[group='${group}']`).html(`Итого ${sum} грн`);
        $(`.total[group='${group}']`).attr("price", sum);
    }
});