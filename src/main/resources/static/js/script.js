window.addEventListener('DOMContentLoaded', (event) => {
    const form = document.getElementById('styleTransferForm');
    const fileInput = document.getElementById('fileInput');
    const artistSelect = document.getElementById('artistSelect');
    const outputImage = document.getElementById('outputImage');

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('file', fileInput.files[0]);
        formData.append('artist', artistSelect.value);

        fetch('/styletransfer', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(imagePath => {
            const imageUrl = imagePath
            outputImage.innerHTML = `<img src="${imageUrl}" alt="Transformed Image">`;
        })
        .catch(error => {
            console.error('Error:', error);
            outputImage.innerHTML = 'An error occurred.';
        });
    });
});
