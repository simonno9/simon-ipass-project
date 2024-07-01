export class ServicePageCard {
  constructor(containerId, apiUrl) {
    this.containerId = containerId;
    this.apiUrl = apiUrl;
    this.init();
  }

  init() {
    document.addEventListener('DOMContentLoaded', () => {
      this.container = document.getElementById(this.containerId);
      this.fetchData();
    });
  }

  fetchData() {
    fetch(this.apiUrl)
      .then(response => response.json())
      .then(data => this.renderCards(data))
      .catch(error => console.error('Error fetching data:', error));
  }

  renderCards(array) {
    try {
      array.forEach(data => {
        console.log(data);

        const card = document.createElement('div');
        card.className = 'servicepage-card';

        const imgContainer = document.createElement('div');
        imgContainer.className = 'servicepage-image';
        const img = document.createElement('img');
        img.src = data.image;
        img.alt = 'Service Image';
        imgContainer.appendChild(img);

        const contentContainer = document.createElement('div');
        contentContainer.className = 'servicepage-content';

        const title = document.createElement('div');
        title.className = 'servicepage-title';
        const titleText = document.createElement('p');
        titleText.textContent = data.name;
        titleText.style.fontWeight = 'bold';
        title.appendChild(titleText);

        const description = document.createElement('div');
        description.className = 'servicepage-description';
        const descText = document.createElement('p');
        descText.textContent = data.description; // assuming the description field
        description.appendChild(descText);

        contentContainer.appendChild(title);
        contentContainer.appendChild(description);

        card.appendChild(imgContainer);
        card.appendChild(contentContainer);
        this.container.appendChild(card);
      });

      this.updateLayout();
    } catch (error) {
      console.error('Error rendering cards:', error);
    }
  }

  updateLayout() {
    const cards = Array.from(this.container.children);
    let row;
    cards.forEach((card, index) => {
      if (index % 2 === 0) {
        row = document.createElement('div');
        row.className = 'card-row';
        this.container.appendChild(row);
      }
      row.appendChild(card);
    });
  }
}
