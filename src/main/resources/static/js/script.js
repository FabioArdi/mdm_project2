window.addEventListener('DOMContentLoaded', (event) => {
    const form = document.getElementById('styleTransferForm');
    const fileInput = document.getElementById('fileInput');
    const artistSelect = document.getElementById('artistSelect');
    const outputContainer = document.getElementById('outputContainer');
    const outputImage = outputContainer.querySelector('#outputImage');

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('file', fileInput.files[0]);
        formData.append('artist', artistSelect.value);

        const button = event.target.querySelector('button[type="submit"]');
        const spinner = button.querySelector('.spinner-border');
        button.disabled = true; // Disable the button during the request
        spinner.classList.remove('d-none'); // Show the loading spinner

        fetch('/styletransfer', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(imagePath => {
            const imageUrl = imagePath;
            outputImage.innerHTML = `<img src="${imageUrl}" alt="Transformed Image">`;
        })
        .catch(error => {
            console.error('Error:', error);
            outputImage.innerHTML = 'An error occurred.';
        })
        .finally(() => {
            button.disabled = false; // Enable the button after the response is received
            spinner.classList.add('d-none'); // Hide the loading spinner
        });
    });

});
