export class SpotlightSlider {
  constructor(containerId,queryUrl) {
    this.containerId = containerId;
    this.queryUrl = queryUrl; // Add queryUrl as a class property
    this.slideIndex = 1;
    this.init();
  }
  init() {
    this.slideshowContainer = document.getElementById(this.containerId).querySelector('.slideshow-container');
    this.rectangleContainer = document.getElementById(this.containerId).querySelector('.rectangle-container');
    this.prevButton = document.getElementById(this.containerId).querySelector('.prev');
    this.nextButton = document.getElementById(this.containerId).querySelector('.next');

    this.prevButton.addEventListener('click', () => this.plusSlides(-1));
    this.nextButton.addEventListener('click', () => this.plusSlides(1));

    this.fetchImages();
  }

  async fetchImages() {
    const apiUrl =  this.queryUrl;
    try {
      const response = await fetch(apiUrl);
      const images = await response.json();
      this.createSlides(images);
      this.showSlides(this.slideIndex);
    } catch (error) {
      console.error('Error fetching images:', error);
    }
  }

  createSlides(images) {
    images.forEach((image, index) => {
      // Create slide
      const slideDiv = document.createElement('div');
      slideDiv.className = 'mySlides fade';

      const imgElement = document.createElement('img');
      imgElement.src = image;
      imgElement.style.width = '100%';
      imgElement.className = 'pointer'
      imgElement.addEventListener('click', () => {
        window.location.href = '/nested/service.html';
      });

      slideDiv.appendChild(imgElement);
      this.slideshowContainer.insertBefore(slideDiv, this.prevButton);

      // Create rectangle
      const rectangleDiv = document.createElement('div');
      rectangleDiv.className = 'rectangle';
      rectangleDiv.addEventListener('click', () => this.currentSlide(index + 1));
      this.rectangleContainer.appendChild(rectangleDiv);
    });
  }

  plusSlides(n) {
    this.showSlides(this.slideIndex += n);
  }

  currentSlide(n) {
    this.showSlides(this.slideIndex = n);
  }

  showSlides(n) {
    let i;
    const slides = this.slideshowContainer.getElementsByClassName('mySlides');
    const rectangles = this.rectangleContainer.getElementsByClassName('rectangle');
    if (n > slides.length) { this.slideIndex = 1; }
    if (n < 1) { this.slideIndex = slides.length; }
    for (i = 0; i < slides.length; i++) {
      slides[i].style.display = 'none';
    }
    for (i = 0; i < rectangles.length; i++) {
      rectangles[i].className = rectangles[i].className.replace(' activespotlight', '');
    }
    slides[this.slideIndex - 1].style.display = 'block';
    rectangles[this.slideIndex - 1].className += ' activespotlight';
  }
}
