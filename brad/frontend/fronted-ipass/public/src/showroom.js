// src/showroom.js
import { CardSlider } from './view/components/card-slider.js';
import { SpotlightSlider } from './view/components/spotlight-slider.js';

new SpotlightSlider('spotlight-slider', 'http://localhost:8080/api/spotlight/images');

async function initializeSliders() {
  try {
    const response = await fetch('http://localhost:8080/api/categoryprojects/all');
    const data = await response.json();

    const sliderContainer = document.getElementById('slider-container');
    sliderContainer.innerHTML = ''; // Clear the container

    data.forEach(item => {
      const sliderId = `slider-container-${item.id}`;
      
      // Create a new div for each slider
      const sliderDiv = document.createElement('div');
      sliderDiv.id = sliderId;
      sliderContainer.appendChild(sliderDiv);

      new CardSlider(sliderId, `http://localhost:8080/api/projects/category/${item.id}`, item.name, `/nested/project.html?name=${item.name.toLowerCase()}`);
    });
  } catch (error) {
    console.error('Error fetching data:', error);
  }
}

// Initialize sliders on page load
document.addEventListener('DOMContentLoaded', initializeSliders);
