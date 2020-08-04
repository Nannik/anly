const addFilesUrl = "/loadFiles";
const deleteFileUrl = "/deleteFile";
const xhr = new XMLHttpRequest();

$(document).ready(function() {
    $(".message .reply").on("click", function() {
        $(this).parent().children("form").removeClass("hidden");

        let username = $(this).parent().children("input[name=username]").val();

        $(this).parent().children("form").children("input[name=text]").val(username + ", ");
    });

    $(".child-message .reply").on("click", function() {
        $(this).parent().parent().children("form").removeClass("hidden");

        let username = $(this).parent().children("input[name=username]").val();

        $(this).parent().parent().children("form").children("input[name=text]").val(username + ", ");
    })

    $(".img-add input").on("change", function () {
        loadImages()
            .then(data => addImages(data));
    });

    function addImages(data) {
        for (let i = 0; i < data.length; i++) {
            $(".imgs").prepend(
                "<div class='img-cnv'>" +
                    "<input type='text' hidden name='images' value='" + data[i] + "' form='product-form'>" +
                    "<img src='/img/" + data[i] + "'>" +
                    "<div class='delete'>" +
                        "<svg class='bi bi-x-square-fill' viewBox='0 0 16 16' fill='currentColor' xmlns='http://www.w3.org/2000/svg'>" +
                        "<path fill-rule='evenodd' d='M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm9.854 4.854a.5.5 0 0 0-.708-.708L8 7.293 4.854 4.146a.5.5 0 1 0-.708.708L7.293 8l-3.147 3.146a.5.5 0 0 0 .708.708L8 8.707l3.146 3.147a.5.5 0 0 0 .708-.708L8.707 8l3.147-3.146z'/>" +
                        "</svg>" +
                    "</div>" +
                "</div>"
            );
        }
    }

    $(".imgs").on("click", ".img-cnv .delete", function () {
        deleteImage($(this).parent().children("input").attr("value"))
            .then(data => { if (data) { $(this).parent().remove(); } })
            .catch(error => console.log(error));
    });
});

function loadImages() {
    return new Promise((resolve, reject) => {
        let formData = new FormData(document.forms.addFiles);

        xhr.open("post", addFilesUrl);
        xhr.responseType = "json";

        xhr.onload = () => {
            if (this.status < 400) {
                resolve(xhr.response);
            } else {
                reject(xhr.response.then(error => {throw error}));
            }
        }

        xhr.onerror = () => {
            reject(xhr.response.then(error => {throw error}));
        }

        xhr.send(formData);
    });
}

function deleteImage(filename) {
    return new Promise((resolve, reject) => {
        let formData = new FormData(document.forms._csrf);
        formData.append("filename", filename);

        xhr.open("post", deleteFileUrl);

        xhr.onload = () => {
            if (this.status < 400) {
                resolve(xhr.response);
            } else {
                reject(xhr.response.then(error => {throw error}));
            }
        }

        xhr.onerror = () => {
            reject(xhr.response.then(error => {throw error}));
        }

        xhr.send(formData);
    });
}