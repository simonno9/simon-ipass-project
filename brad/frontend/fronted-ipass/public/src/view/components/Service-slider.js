

// src/components/service-slider.js

export class ServiceSlider {
    constructor(containerId,queryUrl) {
      this.containerId = containerId;
      this.queryUrl = queryUrl; // Add queryUrl as a class property
      this.currentIndex = 0;
      this.init();
    }
  
    init() {
      document.addEventListener('DOMContentLoaded', () => {
        this.container = document.getElementById(this.containerId);
        this.prevButton = document.querySelector('.prev');
        this.nextButton = document.querySelector('.next');
  
        this.prevButton.addEventListener('click', () => this.moveSlider(-1));
        this.nextButton.addEventListener('click', () => this.moveSlider(1));
  
        this.fetchData();
      });
    }
    
  
    fetchData() {
      fetch(this.queryUrl)
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
        img.className = 'pointer'
        img.addEventListener('click', () => {
        window.location.href = '/nested/service.html';
      });
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
  