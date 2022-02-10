import { useState } from 'react'


function TodoListForm({ addTask, search }) {
    
    
    /*
    userInput хранит строку. Строка - название новой записи. При обьявлении получает пустую строку.
    userInput нужен для хранения введеного пользователем названия записи.
        
    userSearsh хранит строку. Строка - подстрока искомой записи. При обьявлении получает пустую строку.
    userInput нужен для хранения введеноой пользователем подстроки.
    */
            

    const [userInput, setUserInput] = useState('')
    const [userSearch, setUserSearch] = useState('')
    
    
     /*
    Функция createTask нужна для создания новой заметки в ToDo листе.
    
    e.preventDefault() отменяет стандартное события, вследствие чего при нажатии на кнопку не происходит
    обновлени страницы.
    
    addTask(userInput) использует функцию addTask из App.js, и передает ей введенную пользователем строку.
    
    setUserInput('') очищает поле ввода после сохранения новой записи.
    */
            
    
    const createTask = e =>
    {
        e.preventDefault()
        addTask(userInput)
        setUserInput('')
    }
    
     /*
    Функция listSearsh нужна для поиска заметки в ToDo листе по названию.
    
    e.preventDefault() отменяет стандартное события, вследствие чего при нажатии на кнопку не происходит
    обновлени страницы.
    
    search(userSearsh) использует функцию search из App.js, и передает ей введенную пользователем строку.
    */
    
    const listSearsh = e =>
    {
        e.preventDefault()
        search(userSearch)
    }
    
     /*
    Функция handleInputCreate нужна для присвоения userInput значения, которые вводит пользователь
    */
    
    const handleInputCreate = e =>
    {
        setUserInput(e.currentTarget.value)
    }
    
     /*
    Функция handleInputSearch нужна для присвоения userSearch значения, которые вводит пользователь
    */
    
    const handleInputSearch = e =>
    {
        setUserSearch(e.currentTarget.value)
    }
    
  return (
      
      <div id = "TodoListForm">
      
        <form onSubmit={listSearsh}>
        <input className="myInput" type="text" value={userSearch} onChange={handleInputSearch} placeholder="Поиск"></input>
        <button className="myInput">Найти</button>
        </form>
      
        <form onSubmit={createTask}>
        <input className="myInput" value={userInput} onChange={handleInputCreate} type="text" placeholder="Название"></input>
        <button className="myInput">Добавить</button>  
        </form>
      
      </div>
      
      

  );
}


export default TodoListForm;
