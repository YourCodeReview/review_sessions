using Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace GameServer.Net
{
    class Room
    {
        enum RoomState
        {
            WaitingJoin,
            WaitinBattle,
            Battle,
            End
        }

        private List<Client> clientRoom = new List<Client>();
        private RoomState state = RoomState.WaitingJoin;
        private Server server;

        public Room(Server server) { this.server = server; }

        public void AddClient(Client client) 
        {
            clientRoom.Add(client);
            client.room = this;

            if (clientRoom.Count >= 2)
                state = RoomState.WaitinBattle; //меняю статус комнаты в ожидание старта
        }

        public bool isWaitingJoin() // проверка статуса комнаты, true если ожидает входа
        {
            return state == RoomState.WaitingJoin;
        }

        public int GetId() // возвращаю создателя комнаты (первого игрока)
        {
            if (clientRoom.Count > 0)
                return clientRoom[0].GetUserId();
            return -1;
        }

        public string GetRoomData() // возвращает данные о комнате (игроках которые в ней находятся)
        {
            StringBuilder sb = new StringBuilder();

            foreach (Client client in clientRoom)
                sb.Append(client.GetUserData() + "|");

            if (sb.Length > 0)
                sb.Remove(sb.Length - 1, 1);

            return sb.ToString();
        }

        public void BroadCastMessage(Client excludeClient, ActionCode actionCode, string data)  // передача данных другому игроку
        {
            foreach (Client client in clientRoom)            
                if (excludeClient != client) //проверяю зашёл ли я в цикле на другого игрока
                    server.SendResponse(client, actionCode, data); //если зашёл то отправляю к нему данные
            
        }

        public bool IsHouseOwner(Client client) // проверяю нажал ли на кнопку старт создатель комнаты, true если создатель комнаты (первый вошедший)
        {
            return client == clientRoom[0];
        }

        #region костыль. сложности с запуском игры
        public void StartPlay()
        {
            new Thread(RunPlay).Start();
        }

        private void RunPlay()
        {
            Thread.Sleep(250);

            BroadCastMessage(null, ActionCode.StartPlay, "r");
        }

        #endregion



    }
}
