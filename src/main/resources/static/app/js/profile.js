$(document).ready(() => {
   $(".edit").on('click', (e) => {
       $("input").prop('disabled', false)
       $("input.hidden").removeClass("hidden")
   })
});