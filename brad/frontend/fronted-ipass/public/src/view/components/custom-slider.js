// src/components/service-slider.js

export class ServiceSlider {
  constructor(containerId, queryUrl) {
    this.containerId = containerId;
    this.queryUrl = queryUrl; // Add queryUrl as a class property
    this.currentIndex = 0;
    this.init();
  }

  init() {
    document.addEventListener('DOMContentLoaded', () => {
      this.container = document.getElementById(this.containerId);
      
      // Create and append previous and next buttons
      this.prevButton = document.createElement('div');
      this.prevButton.className = 'arrow prev';
      this.prevButton.innerHTML = '&lt;';
      this.prevButton.addEventListener('click', () => this.moveSlider(-1));
      this.container.parentElement.appendChild(this.prevButton);

      this.nextButton = document.createElement('div');
      this.nextButton.className = 'arrow next';
      this.nextButton.innerHTML = '&gt;';
      this.nextButton.addEventListener('click', () => this.moveSlider(1));
      this.container.parentElement.appendChild(this.nextButton);

      this.fetchData();
    });
  }

  fetchData() {
    fetch(this.queryUrl) // Use the queryUrl property
      .then(response => response.json())
      .then(data => this.renderCards(data))
      .catch(error => console.error('Error fetching data:', error));
  }

  renderCards(data) {
    data.forEach(service => {
      const card = document.createElement('div');
      card.className = 'card';

      const img = document.createElement('img');
      img.src = service.image;
      img.alt = service.name;

      const cardText = document.createElement('div');
      cardText.className = 'card-text';
      cardText.textContent = service.name;

      card.appendChild(img);
      card.appendChild(cardText);
      this.container.appendChild(card);
    });
  }

  moveSlider(direction) {
    const cards = this.container.children;
    const cardWidth = cards[0].clientWidth + 20; // Adding margin
    this.currentIndex = Math.min(Math.max(this.currentIndex + direction, 0), cards.length - 1);
    this.container.scrollLeft = this.currentIndex * cardWidth;
  }
}
