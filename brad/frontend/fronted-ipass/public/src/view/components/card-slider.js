export class CardSlider {
  constructor(containerId, queryUrl, headerText, viewAllLink) {
    this.containerId = containerId;
    this.queryUrl = queryUrl; // Add queryUrl as a class property
    this.headerText = headerText; // Add headerText as a class property
    this.viewAllLink = viewAllLink; // Add viewAllLink as a class property
    this.currentIndex = 0;
    this.init();
  }

  init() {
    this.container = document.getElementById(this.containerId);
    console.log(this.containerId);
    console.log(this.container);

    // Create the slider structure
    this.createSliderStructure();

    this.prevButton = this.container.querySelector('.prev');
    this.nextButton = this.container.querySelector('.next');

    // Add event listeners with console logs
    this.prevButton.addEventListener('click', () => {
      console.log('Prev button clicked');
      this.moveSlider(-1);
    });
    this.nextButton.addEventListener('click', () => {
      console.log('Next button clicked');
      this.moveSlider(1);
    });

    this.fetchData();
  }

  createSliderStructure() {
    const slider = document.createElement('div');
    slider.className = 'slider';
  
    const headerContainer = document.createElement('div');
    headerContainer.className = 'header-container';
  
    const h1 = document.createElement('h1');
    h1.textContent = this.headerText;
  
    const viewAllAnchor = document.createElement('a');
    viewAllAnchor.href = this.viewAllLink;
    viewAllAnchor.textContent = 'View All';
  
    headerContainer.appendChild(h1);
    headerContainer.appendChild(viewAllAnchor);
  
    const prevArrow = document.createElement('div');
    prevArrow.className = 'arrow prev';
    prevArrow.innerHTML = '&lt;';
  
    const nextArrow = document.createElement('div');
    nextArrow.className = 'arrow next';
    nextArrow.innerHTML = '&gt;';
  
    const sliderContainer = document.createElement('div');
    sliderContainer.className = 'slider-container service-container';
    sliderContainer.id = `service-container-${this.containerId}`;
  
    slider.appendChild(headerContainer);
    slider.appendChild(prevArrow);
    slider.appendChild(sliderContainer);
    slider.appendChild(nextArrow);
  
    this.container.appendChild(slider);
  }

  fetchData() {
    fetch(this.queryUrl)
      .then(response => response.json())
      .then(data => this.renderCards(data))
      .catch(error => console.error('Error fetching data:', error));
  }

  renderCards(data) {
    console.log(data);
    const serviceContainer = document.getElementById(`service-container-${this.containerId}`);
    data.forEach(service => {
      const card = document.createElement('div');
      card.className = 'card';
      console.log('inside render card !!!', service)
      const img = document.createElement('img');
      img.src = service.img;
      img.alt = service.name;
      img.className = 'pointer';
      img.addEventListener('click', () => {
        window.location.href = `/nested/project.html?id=${service.id}`;
      });

      const cardText = document.createElement('div');
      cardText.className = 'card-text';
      cardText.textContent = service.name;

      card.appendChild(img);
      card.appendChild(cardText);
      serviceContainer.appendChild(card);
    });
  }

  moveSlider(direction) {
    console.log('Moving slider:', direction);
    const cards = document.getElementById(`service-container-${this.containerId}`).children;
    const cardWidth = cards[0].clientWidth + 20; // Adding margin
    console.log('Card width:', cardWidth);
    console.log('Current index before move:', this.currentIndex);
    this.currentIndex = Math.min(Math.max(this.currentIndex + direction, 0), cards.length - 1);
    console.log('Current index after move:', this.currentIndex);
    const scrollLeftValue = this.currentIndex * cardWidth;
    console.log('Setting scrollLeft to:', scrollLeftValue);
    this.container.querySelector('.slider-container').scrollLeft = scrollLeftValue;
  }
}
