using System.Linq;
using BotFramework.Abstractions;
using BotFramework.Services.Clients;
using Telegram.Bot.Types;
using Telegram.Bot.Types.ReplyMarkups;

namespace BotFramework.Services.Extensioins
{
    public static class ClientGetExtensions
    {
        public static UpdateFilter<Message> GetTextMessage(this IClient client)
        {
            return client.GetUpdateFilter()
                         .Where(u => !string.IsNullOrEmpty(u.Message?.Text))
                         .Select(t => t.Message);
        }

        public static UpdateFilter<Message> GetOnlyButtonResult(this IClient client, ReplyKeyboardMarkup replyMarkup)
        {
            return client.GetUpdateFilter()
                         .Where(u =>
                         replyMarkup.Keyboard.SelectMany(t => t).Any(t => t.Text == u.Message?.Text))
                         .Select(t => t.Message);
        }
    }
}