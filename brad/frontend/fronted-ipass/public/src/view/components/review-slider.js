// src/components/review-slider.js

export class ReviewSlider {
  constructor(containerId) {
    this.containerId = containerId;
    this.currentIndex = 0;
    this.init();
  }

  init() {
    document.addEventListener('DOMContentLoaded', () => {
      this.container = document.getElementById(this.containerId);

      // Create and append previous and next buttons within the correct container
      this.prevButton = document.createElement('div');
      this.prevButton.className = 'arrow prev';
      this.prevButton.innerHTML = '&lt;';
      this.prevButton.addEventListener('click', () => this.moveSlider(-2));
      this.container.parentElement.appendChild(this.prevButton);

      this.nextButton = document.createElement('div');
      this.nextButton.className = 'arrow next';
      this.nextButton.innerHTML = '&gt;';
      this.nextButton.addEventListener('click', () => this.moveSlider(2));
      this.container.parentElement.appendChild(this.nextButton);

      this.fetchData();
    });
  }

  fetchData() {
    fetch('http://localhost:8080/api/reviews/all')
      .then(response => response.json())
      .then(data => this.renderCards(data))
      .catch(error => console.error('Error fetching data:', error));
  }

  renderCards(data) {
    data.forEach(review => {
      const card = document.createElement('div');
      card.className = 'review-card';

      const imgContainer = document.createElement('div');
      imgContainer.className = 'review-image';
      const img = document.createElement('img');
      img.src = review.projectImage;
      img.alt = 'Reviewer Image';
      imgContainer.appendChild(img);

      const contentContainer = document.createElement('div');
      contentContainer.className = 'review-content';

      const textContainer = document.createElement('div');
      textContainer.className = 'review-text';
      const title = document.createElement('p');
      title.className = 'review-title';
      title.textContent = review.titel;
      const rating = document.createElement('div');
      rating.className = 'review-rating';
      rating.innerHTML = '&#9733; '.repeat(review.rating) + '&#9734; '.repeat(5 - review.rating);
      textContainer.appendChild(title);
      textContainer.appendChild(rating);

      const category = document.createElement('p');
      category.className = 'review-category';
      category.textContent = review.categoryName;

      const description = document.createElement('div');
      description.className = 'review-description';
      const descText = document.createElement('p');
      descText.textContent = review.description;
      description.appendChild(descText);

      contentContainer.appendChild(textContainer);
      contentContainer.appendChild(category);
      contentContainer.appendChild(description);

      card.appendChild(imgContainer);
      card.appendChild(contentContainer);
      this.container.appendChild(card);
    });

    // Initially display only the first two cards
    this.updateVisibleCards();
  }

  updateVisibleCards() {
    const cards = Array.from(this.container.children);
    cards.forEach((card, index) => {
      if (index >= this.currentIndex && index < this.currentIndex + 2) {
        card.style.display = 'flex'; // Show the card
      } else {
        card.style.display = 'none'; // Hide the card
      }
    });
  }

  moveSlider(direction) {
    const cards = this.container.children;
    this.currentIndex = Math.min(Math.max(this.currentIndex + direction, 0), cards.length - 2);
    this.updateVisibleCards();
  }
}
