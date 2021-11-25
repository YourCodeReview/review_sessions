using Common;
using GameServer.DAO;
using GameServer.Model;
using GameServer.Net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GameServer.Controller
{
    class UserController : BaseController
    {
        private UserDAO userDAO = new UserDAO();
        public UserController()
        {
            requestCode = RequestCode.User;
        }
        
        public string Login(string data, Client client, Server server)
        {
            //логин, пароль

            string[] strs = data.Split(',');
            User user = userDAO.VerifyUser(client.SqlConnection, strs[0], strs[1]); //провожу проверку пользователя на наличие его в БД
            if (user == null) //если пользователь не найден
            {
                return ((int)ReturnCode.Fail).ToString();
            }
            else //иначе найден
            {
                client.SetUserData(user);
                return string.Format("{0},{1}", ((int)ReturnCode.Success).ToString(), user.Username); //успех и возвращаю имя пользователя
            }
        }

        public string Register(string data, Client client, Server server)
        {
            //логин, пароль

            string[] strs = data.Split(',');
            string username = strs[0];
            string password = strs[1];

            bool result = userDAO.GetUserByUsername(client.SqlConnection, username); //true если пользователь найден

            if (result)
                return ((int)ReturnCode.Fail).ToString();

            userDAO.AddUser(client.SqlConnection, username, password); //добавление пользователя в БД
            return ((int)ReturnCode.Success).ToString();

        }

    }

}
