using Common;
using GameServer.Controller;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace GameServer.Net
{
    class Server
    {
        private IPEndPoint ipEndPoint;
        private Socket serverSocket;
        private List<Client> clientList = new List<Client>();
        ControllerManager controllerManager;
        private List<Room> roomList = new List<Room>();

        public Server(string ipString, int port)
        {
            ipEndPoint = new IPEndPoint(IPAddress.Parse(ipString), port);
            controllerManager = new ControllerManager(this);
        }

        public void Start()
        {

            serverSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp); //сокет - "дверь" через которое будет устанавливаться соединение

            serverSocket.Bind(ipEndPoint); //команда Bind связывает сокет с конечной точкой, указываю сокету где конкретно он должен прослушивать

            serverSocket.Listen(0); //Сокет запущен в режиме прослушивания, он готов принимать поступающие подключения

            //BeginAccept - метод для принятия подключения Он принимает в качестве аргументов имя метода для обработки завершения операции и сам сокет сервера.
            serverSocket.BeginAccept(AcceptCallback, null); //Начинает асинхронную операцию, чтобы принять попытку входящего подключения.
        }


        private void AcceptCallback(IAsyncResult ar) //принимает входящее сообщение с клиента
        {
            Socket clietnSocket = serverSocket.EndAccept(ar); //возвращаю сокет подключившегося клиента
            Client client = new Client(clietnSocket, this);
            client.Start();
            clientList.Add(client); //добавляю клиента к общему списку которые подключены к серверу
            serverSocket.BeginAccept(AcceptCallback, null); //ожидаю приёма сообщений
        }

        public void RemoveClient(Client client)
        {
            lock (clientList)
                clientList.Remove(client);
        }
       
        public void SendResponse(Client client, ActionCode actionCode, string data) // отправка ответа клиенту от сервера
        {
            client.Send(actionCode, data);
        }

        public void HandleRequest(RequestCode requestCode, ActionCode actionCode, string data, Client client)
        {
            controllerManager.HandleRequest(requestCode, actionCode, data, client);
        }

        public void CreateRoom(Client client) // создаю комнату
        {
            Room room = new Room(this);
            room.AddClient(client); //помещаю в неё создателя
            roomList.Add(room); //добавляю комнату в общий список комнат
        }

      
        public List<Room> GetRoomList() // список всех комнат
        {
            return roomList;
        }

        public Room GetRoomById(int id)
        {
            foreach (Room room in roomList)            
                if (room.GetId() == id)
                    return room;
            
            return null;
        }
    }

}
