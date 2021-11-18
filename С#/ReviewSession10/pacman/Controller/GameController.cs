using Common;
using GameServer.Net;

namespace GameServer.Controller
{
    class GameController:BaseController
    {
        public GameController() { requestCode = RequestCode.Game; }


        public string StartGame(string data, Client client, Server server)
        {
            if (client.IsHouseOwner()) //проверяю кто нажал на старт. true если создатель комнаты
            {
                Room room = client.room;
                room.BroadCastMessage(client, ActionCode.StartGame, ((int)ReturnCode.Success).ToString());
                room.StartPlay();
                return ((int)ReturnCode.Success).ToString();
            }
            else
            {
                return ((int)ReturnCode.Fail).ToString();
            }

        }

        public string Move(string data, Client client, Server server) // обработка данных о движении другого игрока
        {
            //moveCode | forward | позиция

            string[] strs = data.Split('|');

            MovementCode movementCode = (MovementCode)int.Parse(strs[0]);
            bool direction = System.Convert.ToBoolean(strs[1]);
            float[] pos = Parse(strs[2]);            

            switch (movementCode)
            {
                case MovementCode.None:
                    break;
                case MovementCode.W:
                    pos[2] += 1.5f;
                    break;
                case MovementCode.S:
                    pos[2] -= 1.5f;
                    break;
                case MovementCode.A:
                    pos[0] -= 1.5f;
                    break;
                case MovementCode.D:
                    pos[0] += 1.5f;
                    break;
                case MovementCode.WS:
                    if (direction)
                        pos[2] += 1.5f;
                    else
                        pos[2] -= 1.5f;
                    break;
                case MovementCode.AD:

                    if (direction)
                        pos[0] -= 1.5f;
                    else
                        pos[0] += 1.5f;
                    break;
                default:
                    break;
            }

            int mCode = (int)movementCode;
            string dataCode = mCode.ToString();
            string position = pos[0].ToString() + "/" + pos[1].ToString() + "/" + pos[2].ToString();
            string dataSend = dataCode + "|" + position;   

            Room room = client.room;
            room.BroadCastMessage(client, ActionCode.Move, dataSend + "|" + "remote"); //moveCode | позиция | роль
            return dataSend + "|" + "local"; //moveCode | позиция | роль

        }
        private float[] Parse(string str)
        {
            string[] strs = str.Split('/');
            float x = float.Parse(strs[0]);
            float y = float.Parse(strs[1]);
            float z = float.Parse(strs[2]);
            return new float[3] { x, y, z };
        }

    }
}
