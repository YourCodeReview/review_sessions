import { useState } from 'react'
import TodoEdit from "./components/TodoEdit/TodoEdit"
import TodoList from "./components/TodoList/TodoList"
import Task from "./components/TodoList/Task"
import React from 'react';


function App() {
    
        /*
        Если в LocalStorage нет ключа tasks, создает его и присваивает ему пустой массив.
        tasks хранит в себе массив объектов. Объекты - заметка в ToDo листе.
        Каждый объект имеет следующие поля:
            id - сгенерированная поледовательность из 9 случайных чисел
            name - имя записи, задаваемое пользователем
            description - дополнительное описание, задаваемое пользователем
            status - состояние заметки
                0 - не начато
                1 - в процессе
                2 - выполнено
        */
    
        if (localStorage.getItem('tasks') == null )
            {
                localStorage.setItem('tasks',JSON.stringify([]))
            }
        
        /*
        Обьявление хуков состояния.
        
        tasks хранит массив объектов. Объекты - заметка в ToDo листе. При обьявлении получает массив из localStorage.
        tasks нужен для отображения задач ToDo листа на странице.
        
        item хранит объект. Объекты - выбранная пользователем из списка заметка. При обьявлении пустой.
        item нужен для передачи выбранного обьекта в форму компонента <TodoEdit>.
        
        searchList хранит массив объектов. Объекты - заметка в ToDo листе, которые искал пользователь.
        При обьявлении получает null.
        searchList нужен для отображения искомых задач ToDo листа на странице.
        
        borderPosition хранит число. Число - позиция элемента rightBorder в пикселях.
        При обьявлении получает null.
        
        listCurrentWidth хранит число. Число - ширина элемента TodiList в пикселях.
        При обьявлении получает null.
        
        editCurrentWidth хранит число. Число - ширина элемента TodiEdit в пикселях.
        При обьявлении получает null.
        
        mouseStatus хранит логический тип. mouseStatus отслеживает, зажата ли левая кнопка мыши.
        При обьявлении получает false.
        */
        
        const [tasks, setTasks] = useState(JSON.parse(localStorage.getItem('tasks')))
        const [item, setItem] = useState(
        {
        id: '',
        name: '',
        description: '',
        status: 0
        })
        
        const [searchList, setSearchList] = useState(null)
        
        const [borderPosition, setBorderPosition] = useState(null)
        const [listCurrentWidth, setListCurrentWidth] = useState(null)
        const [editCurrentWidth, setEditCurrentWidth] = useState(null)
        const [mouseStatus, setMouseStatus] = useState(false)
        
        
        /*
        Функция addTask добовляет новый объект в массив tasks.
        Функция передается в компонент <TodoList />, а из него в <TodoListForm />.
        В качестве аргумента получает название нового обьекта, которое пользователь впишет в форму.
        Если форма будет пуста, новый обьект не будет создан и не будет добавлен.
        Если у аргумента будет значение, сгенерируется новый обьект с именем, заданым пользователем.
        После создания обьекта он будет добавлен в массив tasks.
        */
        
        const addTask = userInput =>
        {    
            if (userInput){
                const task = 
                {
                    id: Math.random().toString(10).substr(3,9),
                    name: userInput,
                    description:"",
                    status: 0
                }
                setTasks([...tasks, task])
            }
        }          
        
        /*    
        Функция editItem принимает обьект из массива tasks и передает его в item.
        */
        
        const editItem = itemID =>
        {
            setItem(tasks.find(task=>task.id===itemID))
        }

        /*
        Функция listRender нужна для отображения всех существующих обьектов из tasks.
        Каждому обьекту из tasks соответсвует компонент <Task />
        
        Строка localStorage.setItem('tasks',JSON.stringify(tasks)) нужна для сохранения в localStorage новых созданных обьектов.
        
        Если searchList содержит массив, то отображаются только те объекты, которые содержат введеную пользователем строку.
        
        Функция возвращает массив, обработанный методом .map
        При каждой итерации метода берется элемент массива, и на его основе создается компонент <Task />
        key - обязательное поле для метода map
        item - данные об элементе массива
        editItem - функция для изменения компонента <Task />
        */
        
        const listRender = () =>                                          
        {   
            localStorage.setItem('tasks',JSON.stringify(tasks)) 
            if (searchList==null)
                {
                    return tasks.map(task =>                                  
                                        <Task                        
                                           key={task.id}             
                                           item={task}    
                                           editItem={editItem}
                                        />)  
                }
            else
                { 
                    return searchList.map(task =>                                  
                                        <Task                        
                                           key={task.id}             
                                           item={task}    
                                           editItem={editItem}
                                        />)  
                }
                                    
        }
               
         
         /*
        Функция searsh нужна для создания массива объектов, которые содержат строку, введенную пользователем.
        Сначала создается пустой массив serashList2, что бы добовлять в него элементы, название которых
        соотвествуют введенной пользователем строке.
        Если пользователь ничего не ввел в форму, то элемент searchList становится пустым, и на экран
        выводятся все записи.
        В ином случае к массиву tasks применяется метод filter, с помощью которого нужные записи
        добовляются в массив serashList2.
        Название записи и введенная пользователем строка преобразуются в нижний регистр для упрощения поиска,
        а затем сравниваются. Если они равны, то запись добовляется в конец массива serashList2.
        После этого в searchList записывается массив serashList2.
        */
                                        
        
        const search = userSearch =>
        {
            const searchList2 = []
            if (userSearch==='') setSearchList(null)
            else
                {
                tasks.filter(element => 
                    {
                        if (element.name.toLowerCase().includes(userSearch.toLowerCase()))
                        searchList2.push(element)
                    })
                setSearchList(searchList2)
                }
            
        }         
        
        /*
        Константа tdl принимает ссылку на элемент TodoList
        Константа tde принимает ссылку на элемент TodoEdit
        */
            
        const tdl = document.getElementById('TodoList')
        const tde = document.getElementById('TodoEdit')

        /*
        Функция widthChange нужна для изменения ширины компонентов TodoList и TodoEdit.
        Эта функция привязана к событию onMouseMove, и срабатывает только если было произведено
        нажатие левой кнопки мыши на элемент rightBorder и mouseStatus == true.
        При каждом перемещении мыши ширина компонента TodoList меняется в соотвествии с формулой:
        
        cw+(mp-bp)
        
        где cw (current width) - ширина объекта до изменения
            mp (mouse position) - положение курсора на экране в пикселях
            bp (border position) - горизонтальное положение элемента rightBorder на странице в пикселях
        
        Формула для TodoEdti:
        
        cw-(mp-bp)
        */
        
        const widthChange = e =>
        {
            
            if (mouseStatus===true) {
                tdl.style.width = `calc(${listCurrentWidth==null?'30%':listCurrentWidth+'px'} + ${(e.clientX-borderPosition)}px)`
                tde.style.width = `calc(${editCurrentWidth==null?'70%':editCurrentWidth+'px'} - ${(e.clientX-borderPosition)}px)`

            }
        }
        
        /*
        Функция mouseUp нужна для того, что бы перестать отслеживать передвижение курсора после того,
        как пользователь отожмет левую кнопку мыши.
        Также по отжатию кнопки, записываются значения ширины TodoList и TodoEdit
        */
        
        const mouseUp = e =>
        {
            if (mouseStatus===true)
            {
            setMouseStatus(false)
            setListCurrentWidth(tdl.offsetWidth)
            setEditCurrentWidth(tde.offsetWidth)
            }
        }
            
  return (
    <div id="App" onMouseMove={widthChange} onMouseUp={mouseUp}>
      <div id="TODOMain">
        <TodoList listRender={listRender} addTask={addTask} search={search} setMouseStatus={setMouseStatus} setBorderPosition={setBorderPosition}/>
        <TodoEdit item={item} setItem={setItem} tasks={tasks} setTasks={setTasks}/>
      </div>
    </div>
  );
}


export default App;
