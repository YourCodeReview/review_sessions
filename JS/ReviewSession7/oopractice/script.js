class Slider {
  constructor( targetElement, colorsArray, withPagers = false, withButtons = false ) {
    this.targetElement = targetElement;
    this.colorsArray = colorsArray;
    this.slidesArray = [];
    this.pagersArray = [];
    this.withPagers = withPagers;
    this.withButtons = withButtons;
    this.slideWidth = "600";
    this.currentSlideIndex = 0;
    // this.delta = 0;
    this.startPos = {x: 0, y: 0};
    // this.endPos = {x: 0, y: 0};
    this.render();
    this.renderSlides();
    this.renderPagers();
    this.setActiveCurrentSlideIndex(this.currentSlideIndex);
    this.renderButtons();
    // this.startAutoSwitch();
    this.handleTouchStart = this.handleTouchStart.bind(this);
    this.handleTouchEnd = this.handleTouchEnd.bind(this);
    this.targetElement.addEventListener("touchstart", this.handleTouchStart)
    this.targetElement.addEventListener("touchend", this.handleTouchEnd)
  }

  handleTouchStart(e) {
      this.startPos = {x: e.touches[0].pageX, y: e.touches[0].screenY};
  }
  handleTouchEnd(e) {
    const endPos = {x: e.changedTouches[0].pageX, y: e.changedTouches[0].screenY};
    const delta = Math.sqrt(endPos.x ^ 2) - Math.sqrt(this.startPos.x ^ 2); 
    if(delta < 0) {
      this.nextSlide();
      console.log(this);
    } else {
      this.prevSlide();
    }
}
  // stopAutoSwitch() {
  //   clearInterval(this.intervalId);
  // }
  // startAutoSwitch() {
  //   this.intervalId = setInterval(() => {
  //     this.nextSlide();
  //   }, 3000);
  // }
  render() {
    this.slider = document.createElement("div");
    this.sliderViewport = document.createElement("div");
    this.sliderTransform = document.createElement("div");

    this.slider.classList.add("slider");
    this.sliderViewport.classList.add("slider__viewport");
    this.sliderTransform.classList.add("slider__transform");

    // this.slider.onmouseenter = () => this.stopAutoSwitch();
    // this.slider.onmouseleave = () => this.startAutoSwitch();
    this.sliderTransform.style.width = `${80 * this.colorsArray.length}vw`;
    this.sliderTransform.style.transform = `translateX(${
      -80 * this.currentSlideIndex
    }vw)`;

    this.sliderViewport.appendChild(this.sliderTransform);
    this.slider.appendChild(this.sliderViewport);
    this.targetElement.appendChild(this.slider);
  }
  setSlides() {
    this.colorsArray.forEach((slideColor) => {
      this.slide = document.createElement("div");
      this.slide.classList.add("slide");

      if(slideColor.match(/https:/|/.jpg/|/.png/g)?.length) {
        this.image = document.createElement('img');
        this.image.setAttribute("src", slideColor);
        this.slide.appendChild(this.image);
  
      }

      this.slide.style.backgroundColor = slideColor;
      this.slide.setAttribute(
        "data-index",
        this.colorsArray.indexOf(slideColor)
      );
      this.slidesArray.push(this.slide);
    });
  }
  renderSlides() {
    this.setSlides();
    this.slidesArray.forEach((slide) => {
      this.sliderTransform.appendChild(slide);
    });
  }
  setPagers() {
    this.colorsArray.forEach((color) => {
      this.pager = document.createElement("li");
      this.pager.classList.add("pager");
      this.pager.setAttribute("data-index", this.colorsArray.indexOf(color));
      this.pager.style.cursor = "pointer";
      this.pagersArray.push(this.pager);
    });
  }
  switchOffAllPagers() {
    this.pagersArray.forEach((pager) => pager.classList.remove("active"));
  }
  setActiveCurrentSlideIndex(currentSlideIndex) {
    if (this.pagersArray.length > 0) {
      this.switchOffAllPagers();
      this.pagersArray[currentSlideIndex].classList.add("active");
    }
  }
  nextSlide() {
    this.currentSlideIndex = this.validateNextSlide()
      ? this.currentSlideIndex + 1
      : 0;
    this.sliderTransform.style.transform = `translateX(${
      -80 * this.currentSlideIndex
    }vw)`;
    this.setActiveCurrentSlideIndex(this.currentSlideIndex);
  }
  prevSlide() {
    this.currentSlideIndex = this.validatePrevSlide()
      ? this.currentSlideIndex - 1
      : this.slidesArray.length - 1;
    this.sliderTransform.style.transform = `translateX(${
      -80 * this.currentSlideIndex
    }vw)`;
    this.setActiveCurrentSlideIndex(this.currentSlideIndex);
  }
  renderPagers() {
    if (this.withPagers) {
      this.pagerContainer = document.createElement("ul");
      this.pagerContainer.classList.add("slider__pagers");
      this.setPagers();
      this.pagersArray.forEach((pager) => {
        pager.onclick = () => {
          this.sliderTransform.style.transform = `translateX(${
            -80 * pager.getAttribute("data-index")
          }vw)`;
          this.currentSlideIndex = parseInt(pager.getAttribute("data-index"));
          this.setActiveCurrentSlideIndex(this.currentSlideIndex);
          console.log(this.currentSlideIndex);

        };
        this.pagerContainer.appendChild(pager);
      });
      this.slider.appendChild(this.pagerContainer);
    }
  }
  validateNextSlide() {
    return this.currentSlideIndex + 1 < this.slidesArray.length;
  }
  validatePrevSlide() {
    return this.currentSlideIndex > 0;
  }

  renderButtons() {
    if (this.withButtons) {
      this.buttonsContainer = document.createElement("div");
      this.buttonNext = document.createElement("button");
      this.buttonPrev = document.createElement("button");

      this.buttonNext.classList.add("btn", "btn-next");
      this.buttonPrev.classList.add("btn", "btn-prev");
      this.buttonsContainer.classList.add("buttons");

      this.buttonNext.textContent = "Next";
      this.buttonPrev.textContent = "Prev";

      this.buttonNext.onclick = () => this.nextSlide();
      this.buttonPrev.onclick = () => this.prevSlide();
      this.buttonsContainer.appendChild(this.buttonPrev);
      this.buttonsContainer.appendChild(this.buttonNext);
      this.slider.appendChild(this.buttonsContainer);
    }
  }
}

// const slider = new Slider(
//   document.querySelector(".OOP-Container"),
//   ["red", "green", "blue", "black", "lime", "orange", "purple"],
//   false,
//   true
// );
// const slider1 = new Slider(
//   document.querySelector(".OOP-Container"),
//   ["red", "green", "blue", "black", "lime", "orange", "purple"],
//   true
// );
const slider2 = new Slider(
  document.querySelector(".OOP-Container"),
  [
  "https://images.unsplash.com/photo-1627940180241-0f6d1ab48a74?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8NXx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60"
  , "https://images.unsplash.com/photo-1601880348117-25c1127a95df?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8Nnx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60"
  , "https://images.unsplash.com/photo-1628029799784-50d803e64ea0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MTd8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
  , "https://images.unsplash.com/photo-1628359363939-468016799633?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MjN8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
  , "https://images.unsplash.com/photo-1627987671809-f6ef4654a6ba?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MjZ8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
  , "https://images.unsplash.com/photo-1627985693739-0b8d52edd648?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MzZ8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
  , "https://images.unsplash.com/photo-1628239019751-ca690c91040b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8NDF8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60"
],
  true,
  true
);
