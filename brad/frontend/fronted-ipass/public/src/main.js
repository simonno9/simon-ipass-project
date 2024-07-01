// src/main.js
import { SpotlightSlider } from './view/components/spotlight-slider.js';
import { ReviewSlider } from './view/components/review-slider.js';
import { ServiceSlider } from './view/components/Service-slider.js';

// Initialize the service slider component
new ServiceSlider('service-container','http://localhost:8080/api/retrieveCategory');

// Initialize the spotlight slider component

    new SpotlightSlider('spotlight-slider','http://localhost:8080/api/spotlight/images');

new ReviewSlider('reviews');
