// src/project.js
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const projectId = urlParams.get('id');
    
    // Assuming you have an endpoint to get project details by ID
    fetch(`http://localhost:8080/api/projects/get/${projectId}`)
      .then(response => response.json())
      .then(data => renderProjectDetails(data))
      .catch(error => console.error('Error fetching project details:', error));
  });
  
  function renderProjectDetails(project) {
    console.log(project);
  
    const projectDetails = document.getElementById('project-details');
  
    const mainImage = document.createElement('img');
    mainImage.src = project.img;
    mainImage.alt = project.name;
    mainImage.classList.add('main-image');
  
  
  
    const imageContainer = document.createElement('div');
    imageContainer.classList.add('image-container');
  
    // Add the main image to the image container
    imageContainer.appendChild(mainImage);
  
    const smallImageContainer = document.createElement('div');
    smallImageContainer.classList.add('small-image-container');
  
    // Add additional images to the small image container
    project.imageLinks.forEach((imageLink, index) => {
      const image = document.createElement('img');
      image.src = imageLink;
      image.alt = project.name;
      image.classList.add('small-image');
  
      if (index >= 4) {
        smallImageContainer.appendChild(document.createElement('br'));
      }
      smallImageContainer.appendChild(image);
    });
  
    // Append the small image container to the image container
    imageContainer.appendChild(smallImageContainer);
  
    // Append elements to the project details container
    projectDetails.appendChild(imageContainer);
  
    // Render review section
    renderReviewSection(project.reviews);
  
    // Render project info section
    renderProjectInfo(project);
  }
  
  function renderReviewSection(reviews) {
    const reviewSection = document.getElementById('review-section');
  
    reviews.forEach(review => {
      const reviewContainer = document.createElement('div');
      reviewContainer.classList.add('review-container');
  
      const reviewHeader = document.createElement('div');
      reviewHeader.classList.add('review-header');
  
      const reviewTitle = document.createElement('h2');
      reviewTitle.textContent = review.titel;
  
      const reviewRating = document.createElement('div');
      reviewRating.classList.add('review-rating');
      reviewRating.innerHTML = '★'.repeat(review.rating) + '☆'.repeat(5 - review.rating);
  
      reviewHeader.appendChild(reviewTitle);
      reviewHeader.appendChild(reviewRating);
  
      const reviewDescription = document.createElement('p');
      reviewDescription.classList.add('review-description');
      reviewDescription.textContent = review.description;
  
      reviewContainer.appendChild(reviewHeader);
      reviewContainer.appendChild(reviewDescription);
  
      reviewSection.appendChild(reviewContainer);
    });
  }
  
  function renderProjectInfo(project) {
    const projectInfo = document.getElementById('project-info');
  
    const projectName = document.createElement('h2');
    projectName.textContent = project.name;
  
    const projectDescription = document.createElement('p');
    projectDescription.textContent = project.description;
  
    projectInfo.appendChild(projectName);
    projectInfo.appendChild(projectDescription);
  }
  