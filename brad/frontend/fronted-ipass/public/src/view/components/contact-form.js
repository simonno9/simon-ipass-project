export class ContactForm {
  constructor(formId, submitButtonId, apiUrl) {
    this.form = document.getElementById(formId);
    this.submitButton = document.getElementById(submitButtonId);
    this.apiUrl = apiUrl;
    this.init();
  }

  init() {
    this.submitButton.addEventListener('click', (event) => {
      event.preventDefault();
      this.submitForm();
    });
  }

  async submitForm() {
    const formData = new FormData(this.form);
    const data = {
      firstname: formData.get('first-name'),
      lastname: formData.get('last-name'),
      category: formData.get('category'),
      phonenumber: formData.get('phonenumber'),
      city: formData.get('city'),
      description: formData.get('description')
    };

    try {
      const response = await fetch(this.apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });

      if (response.ok) {
        const result = await response.json();
        console.log('Contact added successfully:', result);
        // Optionally, handle success (e.g., show a success message, clear the form, etc.)
      } else {
        console.error('Error adding contact:', response.statusText);
        // Optionally, handle error (e.g., show an error message)
      }
    } catch (error) {
      console.error('Error adding contact:', error);
      // Optionally, handle error (e.g., show an error message)
    }
  }
}
