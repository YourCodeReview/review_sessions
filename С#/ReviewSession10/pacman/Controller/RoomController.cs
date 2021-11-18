using Common;
using GameServer.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GameServer.Controller
{
    class RoomController : BaseController
    {
        public RoomController()
        {
            requestCode = RequestCode.Room;
        }

        public string CreateRoom(string data, Client client, Server server) // создаю комнату и возвращаю ответ клиенту
        {
            server.CreateRoom(client);
            return ((int)ReturnCode.Success).ToString() +","+ ((int)RoleType.Yellow).ToString();
        }

        public string ListRoom(string data, Client client, Server server) // список комнат с данными об игроках. 
        {
            StringBuilder sb = new StringBuilder();

            foreach (Room room in server.GetRoomList())            
                if (room.isWaitingJoin()) //проверка ожидает ли эта комната ещё одного игрока                
                    sb.Append(room.GetRoomData() + "|"); //если ожидает, то возвращаю данных об этой комнате (об игроках)            

            if (sb.Length == 0)
                sb.Append("0");
            else
                sb.Remove(sb.Length - 1, 1);

            return sb.ToString();
        }
      
        public string JoinRoom(string data, Client client, Server server) // вход в комнату
        {
            int id = int.Parse(data);
            Room room = server.GetRoomById(id);
            if (room == null)
            {
                return ((int)ReturnCode.NotFound).ToString();
            }
            else if (!room.isWaitingJoin()) //проверяю статус комнаты, т.е можно ли туда войти
            {
                return ((int)ReturnCode.Fail).ToString();
            }
            else
            {
                room.AddClient(client); //добавляю клиента в комнату
                string roomData = room.GetRoomData(); //получаю данные о клиентах которые находятся в комнате
                room.BroadCastMessage(client, ActionCode.UpdateRoom, roomData); //отправляю данные второму игроку
                return ((int)ReturnCode.Success).ToString() + "," + ((int)RoleType.Red).ToString() + "-" + roomData;
            }
        }

    }
}