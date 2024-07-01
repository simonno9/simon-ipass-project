// import { CardSlider } from './view/components/card-slider.js';
// import { SpotlightSlider } from './view/components/spotlight-slider.js';
// // Function to fetch data and initialize CardSlider for each object
// new SpotlightSlider('spotlight-slider','http://localhost:8080/api/spotlight/images');

// async function initializeSliders() {

//   try {
    
//     const response = await fetch('http://localhost:8080/api/retrieveCategory');
//     const data = await response.json();

//     const sliderContainer = document.getElementById('slider-container');
//     sliderContainer.innerHTML = ''; // Clear the container

//     data.forEach(item => {
//       const sliderId = `slider-container-${item.id}`;
      
//       // Create a new div for each slider
//       const sliderDiv = document.createElement('div');
//       sliderDiv.id = sliderId;
//       sliderContainer.appendChild(sliderDiv);
      
//       new CardSlider(sliderId, 'http://localhost:8080/api/retrieveCategory', item.name, `/nested/${item.name.toLowerCase()}.html`);
//     });
//   } catch (error) {
//     console.error('Error fetching data:', error);
//   }
// }

// // Initialize sliders on page load
// document.addEventListener('DOMContentLoaded', initializeSliders);
