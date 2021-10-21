using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using Telegram.Bot.Types;

namespace BotFramework.Services.Clients
{
    public class UpdateFilter<T>
    {
        public readonly List<Func<Update, bool>> Filters;
        public readonly Func<Update, T>          Mapper;
        public readonly IClient                  client;

        public UpdateFilter(IClient client, Func<Update, T> mapper, List<Func<Update, bool>> filters)
        {
            this.client = client;
            Mapper      = mapper;
            Filters     = filters;
        }

        public ValueTaskAwaiter<T> GetAwaiter() => GetTask().GetAwaiter();

        public async ValueTask<T> GetTask()
        {
            return Mapper(await client.GetUpdate(u =>
                          Filters.Aggregate(true, (current, filter) => current && filter(u))));
        }
    }
}