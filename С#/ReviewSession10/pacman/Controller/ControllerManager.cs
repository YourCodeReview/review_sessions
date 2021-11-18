using Common;
using GameServer.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace GameServer.Controller
{
    class ControllerManager
    {
        private Dictionary<RequestCode, BaseController> controllerDict = new Dictionary<RequestCode, BaseController>();
        private Server server;

        public ControllerManager(Server server)
        {
            this.server = server;
            Init();
        }

        private void Init()
        {
            DefaultController defaultController = new DefaultController();
            controllerDict.Add(defaultController.RequestCode, new DefaultController());
            controllerDict.Add(RequestCode.User, new UserController());
            controllerDict.Add(RequestCode.Room, new RoomController());
            controllerDict.Add(RequestCode.Game, new GameController());
        }

     
        public void HandleRequest(RequestCode requestCode, ActionCode actionCode, string data, Client client) // обработчик запросов
        {
            BaseController controller;
            bool isGet = controllerDict.TryGetValue(requestCode, out controller);
            if (!isGet)
            {
                Console.WriteLine("не найден контроллер: " + requestCode);
                return;

            }
            string methodName = Enum.GetName(typeof(ActionCode), actionCode);
            MethodInfo mi = controller.GetType().GetMethod(methodName); //метод который необходимо обработать 
            if (mi == null)
            {
                Console.WriteLine("отсутствует метод: " + methodName);
                return;
            }
            object[] parameters = new object[] { data, client, server };
            object o = mi.Invoke(controller, parameters); //запуск полученного метода
            if (o == null) return;
            server.SendResponse(client, actionCode, o as string);

        }

    }

}
