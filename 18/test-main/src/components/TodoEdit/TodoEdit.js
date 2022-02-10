import Select from 'react-select'

function TodoEdit({ item, setItem, tasks, setTasks }) {
    

     /*
    Функция saveTask нужна для сохранения изменений в параметрах заметки.
    e.preventDefault() отменяет стандартное события submit, вследствие чего при нажатии на кнопку страница не обновляется.
    Переменная index хранит индекс выбранной заметки.
    
    tasks[index]=item заменяет в массиве объект с тем же индексом на измененный пользователем объект.
    
    setTasks([...tasks]) нужно для мгновенно обновления списка при нажатии на кнопку "сохранить".
    
    localStorage.setItem записывает изменения в локальное хранилище.
    */
    
    const saveTask = e =>
    {
        e.preventDefault()
        const index = tasks.findIndex(elem => elem.id === item.id)
        tasks[index]=item
        setTasks([...tasks])
        localStorage.setItem('tasks',JSON.stringify(tasks))
    }
    
    
     /*
    Функция deleteTask нужна для удаления заметки.
    e.preventDefault() отменяет стандартное события submit, вследствие чего при нажатии на кнопку страница не обновляется.
    
    Опретор if нужен для того, что бы функция не удаляла объекты, если ни один из них не выбран.
    
    Переменная index хранит индекс выбранной заметки.
    
    tasks.splice(index,1) удаляет из массива объект с выбранным индексом.
    
    setTasks([...tasks]) нужно для мгновенно обновления списка при нажатии на кнопку "сохранить".
    
    После этого выбранный обьект обнуляется, и форма становится пустой.
    
    localStorage.setItem записывает изменения в локальное хранилище.
    */
            
            
    const deleteTask = e =>
    {
        e.preventDefault()
        if (item.id!='')
        {
        const index = tasks.findIndex(elem => elem.id === item.id)
        tasks.splice(index,1)
        setTasks([...tasks])
        setItem({id: '', name: '', description: '', status: 0})
        localStorage.setItem('tasks',JSON.stringify(tasks))
        }
        
    }
    
    /*
    Функция hadleInput нужна для присвоения свойствам item значений, которые вводит пользователь
    */
    
    const handleInput = e =>
    {
        setItem({...item, [e.target.name]: e.target.value})
    }
    
    
     /*
    Функция handleInputStatus нужна для присвоения свойству status значения, которое выбирает пользователь.
    Отдельная функция нужна, потому что с функция handleInput react-select не рабоатет.
    */
            
    
    const handleInputStatus = (e) =>
    {
        setItem({...item,['status']: (+e.value)})
    }

    
    /*
    options хранит массив объектов для react-select. Объект - параметр для элемента select.
    */
            
    
    const options = 
    [
      { value: '0', label: 'Не начато' },
      { value: '1', label: 'В процессе' },
      { value: '2', label: 'Выполнено' }
    ]

    
  return (
    <div id="TodoEdit">
      <form >
      <input type='hidden' value={item.id}/>
      <input className="myInput" type='text' name="name" placeholder='Название' value={item.name} onChange={handleInput}/>
      <input className="myInput" type='text' name="description" placeholder='Описание' value={item.description} onChange={handleInput}/>
      <Select options={options} name="status" value={options[item.status]} onChange={handleInputStatus}/>
      <button className="myInput" onClick={saveTask}>Сохранить</button>
      <button className="myInput" onClick={deleteTask}>Удалить</button>
      </form>
    </div>
  );
}


export default TodoEdit;
