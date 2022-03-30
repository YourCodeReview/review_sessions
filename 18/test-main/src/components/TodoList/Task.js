import editIcon from './editIcon.png'
import React from 'react'


function Task({item, editItem}) {
    
    
        /*
        Функция clr задает стили компоненту <Task /> в зависимости от их состояния (0, 1 или 2)
        0: серый цвет
        1: синий цвет
        2: зеленый цвет
        */
        
        const clr = status =>
        {
            switch (status){
                case 0 :
                return('Task Task0')
                case 1 :
                return('Task Task1')
                case 2 :
                return('Task Task2')
                default:
                break
            }
        }
        
        /*
        Функция selectThisItem передает в App.js ID выбранного компонента, что бы записать его в состояние item.
        */
        
        const selectThisItem = () =>
        {
            editItem(item.id)
        }

      return (
            <div className={clr(item.status)} id={item.id}>
                <div className="TaskDisc">
                    <p className="Name">{item.name}</p>
                    <p className="Disc">{item.description}</p>
                </div>
                <div className="TaskEdit" onClick={selectThisItem}>
                     <img src={editIcon} alt='editIcon'/> 
                </div>
            </div>
      );

}



export default Task;
