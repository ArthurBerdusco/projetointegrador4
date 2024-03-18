document.getElementById(`btnNovaImagem`).onclick = function() {
    let qtdFieldset = document.querySelectorAll('#inputImagem > fieldset').length;
    document.getElementById('inputImagem').insertAdjacentHTML('beforeend',
    `
    <fieldset>
        <img id="selectedImage${qtdFieldset}" onclick="document.getElementById('incluirImagem${qtdFieldset}').click()" src="/img/add.svg" style="width: 200px; cursor: pointer;" />
        <input class="form-control d-none" id="incluirImagem${qtdFieldset}" type="file" name="arquivo" onchange="displaySelectedImage(event, 'selectedImage${qtdFieldset}')" accept="image/*"/>
        <div>
            <label for="txtOrdenacao${qtdFieldset}">Ordem</label>
            <input type="number" value="${qtdFieldset+1}" id="txtOrdenacao${qtdFieldset}" name="imagens[${qtdFieldset}].ordenacao" />
        </div>
        <div>
            <label for="txtOrdenacao${qtdFieldset}">Principal: </label>
            <input type="checkbox" name="imagens[${qtdFieldset}].principal"/>
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



