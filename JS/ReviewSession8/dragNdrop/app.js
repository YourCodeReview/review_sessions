let dragItems = document.querySelectorAll(".item");
const placeholders = document.querySelectorAll(".placeholder");
const addCardBtn = document.querySelector(".addCard");
let currentDraggingItem = null;

placeholders.forEach((placeholder) => {
  placeholder.addEventListener("dragover", (e) => handleDragOver(e));
  placeholder.addEventListener("dragenter", (e) => handleDragEnter(e));
  placeholder.addEventListener("dragleave", (e) => handleDragLeave(e));
  placeholder.addEventListener("drop", (e) => handleDrop(e));
});

const handleDoubleClick = (event) => {
  const cardText = event.target.innerText;
  const textarea = document.createElement("textarea");
  textarea.classList.add("item__tarea");
  event.target.append(textarea);
  event.target.classList.add("editable");
  event.target.setAttribute("draggable", false);
  textarea.value = cardText;
};

const handleDragStart = (event) => {
  currentDraggingItem = event.target;
  event.target.classList.add("hold");
  setTimeout(() => event.target.classList.add("hide"), 0);
};
const handleDragEnd = (event) => {
  currentDraggingItem = null;
  event.target.classList.remove("hold", "hide");
};

const handleDragOver = (event) => {
  event.preventDefault();
};
const handleDragEnter = (event) => {
  event.target.classList.add("hovered");
};
const handleDragLeave = (event) => {
  event.target.classList.remove("hovered");
};
const handleDrop = (event) => {
  // Получаем объект с размерами и координатами
  const currentElementCoord = event.target.getBoundingClientRect();
  // Находим вертикальную координату центра текущего элемента
  const currentElementCenter =
    currentElementCoord.y + currentElementCoord.height / 2;

  // Если курсор выше центра элемента, возвращаем текущий элемент
  // В ином случае — следующий DOM-элемент
  const nextElement =
    event.clientY < currentElementCenter
      ? event.target
      : event.target.nextElementSibling;

  const position =
    event.target.classList.contains("item") && nextElement !== event.target
      ? "afterend"
      : event.target.classList.contains("item") &&
        event.target.tagName !== "span"
      ? "beforebegin"
      : "afterbegin";

  const container = event.target;

  container.insertAdjacentElement(position, currentDraggingItem);
  event.target.classList.remove("hovered");
};

document.body.addEventListener("click", (e) => {
  if (
    e.path[0].classList[0] !== "item" &&
    e.path[0].classList[0] !== "item__tarea"
  ) {
    dragItems.forEach((dragItem) => {
      if (dragItem.className === "item editable") {
        dragItem.innerText = dragItem.querySelector(".item__tarea")?.value;
      }
      dragItem.classList.remove("editable");
      dragItem.setAttribute("draggable", true);
    });
  }
});

class DragCard {
  constructor() {
    this.card = document.createElement("div");
    this.card.classList.add("item");
    this.card.setAttribute("draggable", true);
    this.card.textContent = `Перетащи меня ${new Date().getSeconds()}`;

    this.removeBtn = document.createElement("button");
    this.removeBtn.classList.add("removeCard");
    this.removeBtn.innerHTML = "&times;";

    this.removeBtn.addEventListener("click", () => this.handleRemoveCard());
    this.card.addEventListener("dragstart", () => this.handleDragStart());
    this.card.addEventListener("dragend", () => this.handleDragEnd());
    this.card.addEventListener("dblclick", () => this.handleOpenEditMode());
  }

  handleRemoveCard() {
    this.card.remove();
  }

  handleCloseEditMode() {
    window.addEventListener("click", (e) => {
      if (
        e.path[0].classList[0] !== "item" &&
        e.path[0].classList[0] !== "item__tarea"
      ) {
        this.card.innerText = this.textarea.value;
        this.card.classList.remove("editable");
        this.card.setAttribute("draggable", true);
        this.card.append(this.removeBtn);
      }
    });
  }

  handleOpenEditMode() {
    this.removeBtn.remove();
    const cardText = this.card.innerText;

    this.textarea = document.createElement("textarea");
    this.textarea.classList.add("item__tarea");
    this.card.append(this.textarea);

    this.card.classList.add("editable");
    this.card.setAttribute("draggable", false);
    this.textarea.value = cardText;

    this.handleCloseEditMode();
  }

  handleDragStart() {
    currentDraggingItem = this.card;
    this.card.classList.add("hold");
    setTimeout(() => this.card.classList.add("hide"), 0);
  }
  handleDragEnd = () => {
    currentDraggingItem = null;
    this.card.classList.remove("hold", "hide");
  };

  render(container) {
    this.card.append(this.removeBtn);
    container.insertAdjacentElement("afterbegin", this.card);
  }
}

addCardBtn.onclick = (e) => {
  const card = new DragCard();
  card.render(placeholders[0]);
};
