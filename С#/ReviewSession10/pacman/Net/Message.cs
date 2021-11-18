using Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GameServer.Net
{
    class Message
    {
        public byte[] Data { get { return data; } }
        public int StartIndex { get { return startIndex; } }
        public int RemainSize { get { return Data.Length - StartIndex; } }


        private byte[] data = new byte[1024];
        private int startIndex = 0;       
      
        public void ReadMessage(int newDataAmount, Action<RequestCode, ActionCode, string> OnProcessDataCallback) // чтение данных полученных от клиента
        { 
            //полученные данные читаются через звёздочку RequestCode * ActionCode * data

            byte[] receiveData = new byte[newDataAmount]; //создание массива который будет десериализовываться
            Array.Copy(data, receiveData, receiveData.Length); //копирование в созданный массив полученных данных от клиента
            string deserializeData = ProtoBufSerializer.Deserialize<string>(receiveData); //десериализация

            string[] strs = deserializeData.Split('*'); 

            RequestCode requestCode = (RequestCode)(int.Parse(strs[0]));
            ActionCode actionCode = (ActionCode)(int.Parse(strs[1]));
            string dataS = strs[2]; //данные от клиента

            OnProcessDataCallback(requestCode, actionCode, dataS); //дальнейшая обработка полученных данных
        }
       
        public static byte[] PackData(ActionCode actionCode, string data) // сериализация данных для отправки клиенту
        { 
            //через звёздочку запаковываю данные actionCode и data для клиента

            int actionCodeInt = (int)actionCode;
            string packData = actionCodeInt.ToString() + "*" + data;
            return ProtoBufSerializer.Serialize(packData);
        }
    }
}
