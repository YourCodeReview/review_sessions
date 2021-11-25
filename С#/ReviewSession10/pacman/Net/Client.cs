using Common;
using GameServer.Model;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace GameServer.Net
{
    class Client
    {
        Socket clientSocket;
        Server server;
        Message msg = new Message();
        User user;
        public Client() { }
        private Room _room;
        public Room room
        {
            set { _room = value; }
            get { return _room; }
        }
        SqlConnection SqlConn;

        public SqlConnection SqlConnection { get { return SqlConn; } }

        public Client(Socket clientSocket, Server server)
        {
            this.clientSocket = clientSocket;
            this.server = server;
            SqlConn = ConnectionHelper.Connect();
        }

        public void Start()
        {
            if (clientSocket == null || !clientSocket.Connected)
                return;

            //ожидание приёма данных от клиента
            clientSocket.BeginReceive(msg.Data, msg.StartIndex, msg.RemainSize, SocketFlags.None, ReceiveCallback, null); 
        }

        public void Send(ActionCode actionCode, string data) //отправка данных клиенту
        {
            byte[] bytes = Message.PackData(actionCode, data); //провожу предварительную сериализацию через protobuf
            clientSocket.Send(bytes);
        }

        private void ReceiveCallback(IAsyncResult ar) //обработка данных от клиента
        {
            int count = clientSocket.EndReceive(ar);

            if (count == 0)
                Close();

            msg.ReadMessage(count, OnProcessMessage);

            Start();
        }

        private void Close() //закрываю подключение для клиента
        {
            ConnectionHelper.CloseConnectioin(SqlConn);

            if (clientSocket != null)
                clientSocket.Close();

            server.RemoveClient(this);
        }
             
        public void OnProcessMessage(RequestCode requestCode, ActionCode actionCode, string data) // обработка сообщения от клиента
        {
            server.HandleRequest(requestCode, actionCode, data, this);
        }

        public void SetUserData(User user)
        {
            this.user = user;
        }

        public string GetUserData()
        {
            return user.Id + "," + user.Username;
        }         

        public int GetUserId()
        {
            return user.Id;
        }

        public bool IsHouseOwner() //проверяю кто нажал на кнопку старт
        {
            return _room.IsHouseOwner(this);
        }

    }

}
