document.getElementById(`btnNovaImagem`).onclick = function () {
    let qtdFieldset = document.querySelectorAll('#inputImagem > fieldset').length;
    document.getElementById('inputImagem').insertAdjacentHTML('beforeend',
        `
    <fieldset  class="col-md-12 card mx-2 " style="max-width: 250px; max-height: 350px;">
        <img id="selectedImage${qtdFieldset}" onclick="document.getElementById('incluirImagem${qtdFieldset}').click()" src="/img/add-img.svg" class="card-img-top" style="cursor: pointer;" />
        <input class="form-control d-none" id="incluirImagem${qtdFieldset}" type="file" name="arquivo" onchange="displaySelectedImage(event, 'selectedImage${qtdFieldset}')" accept="image/*"/>
        <div class="d-flex align-items-center my-2" style="gap: 10px;">
            <label class="card-text" for="txtOrdenacao${qtdFieldset}">Ordem</label>
            <input class="form-control" type="number" value="${qtdFieldset + 1}" id="txtOrdenacao${qtdFieldset}" name="imagens[${qtdFieldset}].ordenacao" />
        </div>
        <div class="d-flex" style="gap: 10px;">
            <input class="checkbox" type="checkbox" name="imagens[${qtdFieldset}].principal"/>
            <label class="form-check-label" for="txtOrdenacao${qtdFieldset}">Principal: </label>
        </div>
    </fieldset>
    `);
}

function displaySelectedImage(event, elementId) {
    const selectedImage = document.getElementById(elementId);
    const fileInput = event.target;

    if (fileInput.files && fileInput.files[0]) {
        const reader = new FileReader();

        reader.onload = function (e) {
            selectedImage.src = e.target.result;
        };

        reader.readAsDataURL(fileInput.files[0]);
    }
}



