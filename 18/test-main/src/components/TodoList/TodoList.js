import TodoListForm from "./TodoListForm"
import TodoListBody from "./TodoListBody"



function TodoList({addTask, listRender, search, setBorderPosition, setMouseStatus}) {

    /*
        Функция mouseDown нужна для того, что бы начать отслеживать передвижение курсора 
        и задать положение по горизонтали для rightBorder.
        
        e.preventDefault() отменяет стандартное события, вследствие чего при нажатии на кнопку мыши текст на странице не выделяется.
        
        setWidth(e.clientX) записывает положение курсора на элементе rightBorder.
        
        setMouseStatus(true) записывает состояние нажатия на кнопку, true - кнопка зажата.
    */
    
    const mouseDown = e =>
    {
        e.preventDefault()
        setBorderPosition(e.clientX)
        setMouseStatus(true)
    }
    
  return (
    <div id="TodoList">
    <div id="TodoList2">
      <TodoListForm addTask={addTask} search={search}/>
      <TodoListBody listRender={listRender} />
    </div>
    <div id="rightBorder" onMouseDown={mouseDown}>
    </div>
    </div>
    
    
  );
}


export default TodoList;
