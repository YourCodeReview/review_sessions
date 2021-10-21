#nullable disable
#pragma warning disable 8632
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using Telegram.Bot.Requests;
using Telegram.Bot.Types;
using Telegram.Bot.Types.Enums;
using Telegram.Bot.Types.InlineQueryResults;
using Telegram.Bot.Types.InputFiles;
using Telegram.Bot.Types.Payments;
using Telegram.Bot.Types.ReplyMarkups;

namespace BotFramework.Services.Extensioins
{
    public static class ClientSendExtensions
    {
        public static Task<User> GetMe(this IClient client, CancellationToken cancellationToken = default)
            => client.MakeRequest(new GetMeRequest(), cancellationToken);

        public static Task<Message> SendTextMessage(this IClient      client,
                                                    string            text,
                                                    ChatId?           chatId                = default,
                                                    ParseMode         parseMode             = default,
                                                    bool              disableWebPagePreview = default,
                                                    bool              disableNotification   = default,
                                                    int               replyToMessageId      = default,
                                                    IReplyMarkup?     replyMarkup           = default,
                                                    CancellationToken cancellationToken     = default
        ) =>
        client.MakeRequest(new SendMessageRequest(chatId ?? client.UserId, text)
        {
            ParseMode             = parseMode,
            DisableWebPagePreview = disableWebPagePreview,
            DisableNotification   = disableNotification,
            ReplyToMessageId      = replyToMessageId,
            ReplyMarkup           = replyMarkup
        }, cancellationToken);

        public static Task<Message> ForwardMessage(this IClient      client,
                                                   ChatId            fromChatId,
                                                   int               messageId,
                                                   ChatId?           chatId              = default,
                                                   bool              disableNotification = default,
                                                   CancellationToken cancellationToken   = default
        ) =>
        client.MakeRequest(new ForwardMessageRequest(chatId ?? client.UserId, fromChatId, messageId)
        {
            DisableNotification = disableNotification
        }, cancellationToken);

        public static Task<Message> SendPhoto(this IClient      client,
                                              InputOnlineFile   photo,
                                              ChatId?           chatId              = default,
                                              string            caption             = default,
                                              ParseMode         parseMode           = default,
                                              bool              disableNotification = default,
                                              int               replyToMessageId    = default,
                                              IReplyMarkup      replyMarkup         = default,
                                              CancellationToken cancellationToken   = default
        ) =>
        client.MakeRequest(new SendPhotoRequest(chatId ?? client.UserId, photo)
        {
            Caption             = caption,
            ParseMode           = parseMode,
            ReplyToMessageId    = replyToMessageId,
            DisableNotification = disableNotification,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendAudio(this IClient      client,
                                              InputOnlineFile   audio,
                                              ChatId?           chatId              = default,
                                              string?           caption             = default,
                                              ParseMode         parseMode           = default,
                                              int               duration            = default,
                                              string?           performer           = default,
                                              string?           title               = default,
                                              bool              disableNotification = default,
                                              int               replyToMessageId    = default,
                                              IReplyMarkup?     replyMarkup         = default,
                                              CancellationToken cancellationToken   = default,
                                              InputMedia?       thumb               = default
        ) =>
        client.MakeRequest(new SendAudioRequest(chatId ?? client.UserId, audio)
        {
            Caption             = caption,
            ParseMode           = parseMode,
            Duration            = duration,
            Performer           = performer,
            Title               = title,
            Thumb               = thumb,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendDocument(this IClient      client,
                                                 InputOnlineFile   document,
                                                 ChatId?           chatId              = default,
                                                 string            caption             = default,
                                                 ParseMode         parseMode           = default,
                                                 bool              disableNotification = default,
                                                 int               replyToMessageId    = default,
                                                 IReplyMarkup      replyMarkup         = default,
                                                 CancellationToken cancellationToken   = default,
                                                 InputMedia        thumb               = default
        ) =>
        client.MakeRequest(new SendDocumentRequest(chatId ?? client.UserId, document)
        {
            Caption             = caption,
            Thumb               = thumb,
            ParseMode           = parseMode,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendSticker(this IClient      client,
                                                InputOnlineFile   sticker,
                                                ChatId?           chatId              = default,
                                                bool              disableNotification = default,
                                                int               replyToMessageId    = default,
                                                IReplyMarkup      replyMarkup         = default,
                                                CancellationToken cancellationToken   = default
        ) =>
        client.MakeRequest(new SendStickerRequest(chatId ?? client.UserId, sticker)
        {
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendVideo(this IClient      client,
                                              InputOnlineFile   video,
                                              ChatId?           chatId              = default,
                                              int               duration            = default,
                                              int               width               = default,
                                              int               height              = default,
                                              string            caption             = default,
                                              ParseMode         parseMode           = default,
                                              bool              supportsStreaming   = default,
                                              bool              disableNotification = default,
                                              int               replyToMessageId    = default,
                                              IReplyMarkup      replyMarkup         = default,
                                              CancellationToken cancellationToken   = default,
                                              InputMedia        thumb               = default
        ) =>
        client.MakeRequest(new SendVideoRequest(chatId ?? client.UserId, video)
        {
            Duration            = duration,
            Width               = width,
            Height              = height,
            Thumb               = thumb,
            Caption             = caption,
            ParseMode           = parseMode,
            SupportsStreaming   = supportsStreaming,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendAnimation(this IClient      client,
                                                  InputOnlineFile   animation,
                                                  ChatId?           chatId              = default,
                                                  int               duration            = default,
                                                  int               width               = default,
                                                  int               height              = default,
                                                  InputMedia        thumb               = default,
                                                  string            caption             = default,
                                                  ParseMode         parseMode           = default,
                                                  bool              disableNotification = default,
                                                  int               replyToMessageId    = default,
                                                  IReplyMarkup      replyMarkup         = default,
                                                  CancellationToken cancellationToken   = default
        ) =>
        client.MakeRequest(new SendAnimationRequest(chatId ?? client.UserId, animation)
        {
            Duration            = duration,
            Width               = width,
            Height              = height,
            Thumb               = thumb,
            Caption             = caption,
            ParseMode           = parseMode,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendVoice(this IClient      client,
                                              InputOnlineFile   voice,
                                              ChatId?           chatId              = default,
                                              string            caption             = default,
                                              ParseMode         parseMode           = default,
                                              int               duration            = default,
                                              bool              disableNotification = default,
                                              int               replyToMessageId    = default,
                                              IReplyMarkup      replyMarkup         = default,
                                              CancellationToken cancellationToken   = default
        ) =>
        client.MakeRequest(new SendVoiceRequest(chatId ?? client.UserId, voice)
        {
            Caption             = caption,
            ParseMode           = parseMode,
            Duration            = duration,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendVideoNote(this IClient      client,
                                                  InputTelegramFile videoNote,
                                                  ChatId?           chatId              = default,
                                                  int               duration            = default,
                                                  int               length              = default,
                                                  bool              disableNotification = default,
                                                  int               replyToMessageId    = default,
                                                  IReplyMarkup      replyMarkup         = default,
                                                  CancellationToken cancellationToken   = default,
                                                  InputMedia        thumb               = default
        ) =>
        client.MakeRequest(new SendVideoNoteRequest(chatId ?? client.UserId, videoNote)
        {
            Duration            = duration,
            Length              = length,
            Thumb               = thumb,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        [Obsolete("Use the other overload of this method instead. Only photo and video input types are allowed.")]
        public static Task<Message[]> SendMediaGroup(this IClient                client,
                                                     IEnumerable<InputMediaBase> media,
                                                     ChatId?                     chatId              = default,
                                                     bool                        disableNotification = default,
                                                     int                         replyToMessageId    = default,
                                                     CancellationToken           cancellationToken   = default
        )
        {
            var inputMedia = media
                             .Select(m => m as IAlbumInputMedia)
                             .Where(m => m != null)
                             .ToArray();
            return client.MakeRequest(new SendMediaGroupRequest(chatId ?? client.UserId, inputMedia)
            {
                DisableNotification = disableNotification,
                ReplyToMessageId    = replyToMessageId
            }, cancellationToken);
        }

        public static Task<Message[]> SendMediaGroup(this IClient                  client,
                                                     IEnumerable<IAlbumInputMedia> inputMedia,
                                                     ChatId?                       chatId              = default,
                                                     bool                          disableNotification = default,
                                                     int                           replyToMessageId    = default,
                                                     CancellationToken             cancellationToken   = default
        ) =>
        client.MakeRequest(new SendMediaGroupRequest(chatId ?? client.UserId, inputMedia)
        {
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId
        }, cancellationToken);

        public static Task<Message> SendLocation(this IClient      client,
                                                 float             latitude,
                                                 float             longitude,
                                                 ChatId?           chatId              = default,
                                                 int               livePeriod          = default,
                                                 bool              disableNotification = default,
                                                 int               replyToMessageId    = default,
                                                 IReplyMarkup      replyMarkup         = default,
                                                 CancellationToken cancellationToken   = default
        ) =>
        client.MakeRequest(new SendLocationRequest(chatId ?? client.UserId, latitude, longitude)
        {
            LivePeriod          = livePeriod,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendVenue(this IClient      client,
                                              float             latitude,
                                              float             longitude,
                                              string            title,
                                              string            address,
                                              ChatId?           chatId              = default,
                                              string            foursquareId        = default,
                                              bool              disableNotification = default,
                                              int               replyToMessageId    = default,
                                              IReplyMarkup      replyMarkup         = default,
                                              CancellationToken cancellationToken   = default,
                                              string            foursquareType      = default
        ) =>
        client.MakeRequest(new SendVenueRequest(chatId ?? client.UserId, latitude, longitude, title, address)
        {
            FoursquareId        = foursquareId,
            FoursquareType      = foursquareType,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendContact(this IClient      client,
                                                string            phoneNumber,
                                                string            firstName,
                                                ChatId?           chatId              = default,
                                                string            lastName            = default,
                                                bool              disableNotification = default,
                                                int               replyToMessageId    = default,
                                                IReplyMarkup      replyMarkup         = default,
                                                CancellationToken cancellationToken   = default,
                                                string            vCard               = default
        ) =>
        client.MakeRequest(new SendContactRequest(chatId ?? client.UserId, phoneNumber, firstName)
        {
            LastName            = lastName,
            Vcard               = vCard,
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SendPoll(this IClient        client,
                                             string              question,
                                             IEnumerable<string> options,
                                             ChatId?             chatId                = default,
                                             bool                disableNotification   = default,
                                             int                 replyToMessageId      = default,
                                             IReplyMarkup        replyMarkup           = default,
                                             CancellationToken   cancellationToken     = default,
                                             bool?               isAnonymous           = default,
                                             PollType?           type                  = default,
                                             bool?               allowsMultipleAnswers = default,
                                             int?                correctOptionId       = default,
                                             bool?               isClosed              = default,
                                             string              explanation           = default,
                                             ParseMode           explanationParseMode  = default,
                                             int?                openPeriod            = default,
                                             DateTime?           closeDate             = default
        ) =>
        client.MakeRequest(new SendPollRequest(chatId ?? client.UserId, question, options)
        {
            DisableNotification   = disableNotification,
            ReplyToMessageId      = replyToMessageId,
            ReplyMarkup           = replyMarkup,
            IsAnonymous           = isAnonymous,
            Type                  = type,
            AllowsMultipleAnswers = allowsMultipleAnswers,
            CorrectOptionId       = correctOptionId,
            IsClosed              = isClosed,
            OpenPeriod            = openPeriod,
            CloseDate             = closeDate,
            Explanation           = explanation,
            ExplanationParseMode  = explanationParseMode
        }, cancellationToken);

        public static Task<Message> SendDice(this IClient      client,
                                             ChatId?           chatId              = default,
                                             bool              disableNotification = default,
                                             int               replyToMessageId    = default,
                                             IReplyMarkup      replyMarkup         = default,
                                             CancellationToken cancellationToken   = default,
                                             Emoji?            emoji               = default) =>
        client.MakeRequest(
            new SendDiceRequest(chatId ?? client.UserId)
            {
                DisableNotification = disableNotification,
                ReplyToMessageId    = replyToMessageId,
                ReplyMarkup         = replyMarkup,
                Emoji               = emoji
            },
            cancellationToken
        );

        public static Task SendChatAction(this IClient      client,
                                          ChatAction        chatAction,
                                          ChatId?           chatId            = default,
                                          CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new SendChatActionRequest(chatId ?? client.UserId, chatAction), cancellationToken);

        public static Task<UserProfilePhotos> GetUserProfilePhotos(this IClient      client,
                                                                   int               userId,
                                                                   int               offset            = default,
                                                                   int               limit             = default,
                                                                   CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new GetUserProfilePhotosRequest(userId)
        {
            Offset = offset,
            Limit  = limit
        }, cancellationToken);

        public static Task<File> GetFile(this IClient      client,
                                         string            fileId,
                                         CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new GetFileRequest(fileId), cancellationToken);

        public static Task KickChatMember(
            this IClient      client,
            int               userId,
            ChatId?           chatId            = default,
            DateTime          untilDate         = default,
            CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new KickChatMemberRequest(chatId ?? client.UserId, userId)
        {
            UntilDate = untilDate
        }, cancellationToken);

        public static Task LeaveChat(this IClient      client,
                                     ChatId?           chatId            = default,
                                     CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new LeaveChatRequest(chatId ?? client.UserId), cancellationToken);

        public static Task UnbanChatMember(this IClient      client,
                                           int               userId,
                                           ChatId?           chatId            = default,
                                           CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new UnbanChatMemberRequest(chatId ?? client.UserId, userId), cancellationToken);

        public static Task<Chat> GetChat(this IClient      client,
                                         ChatId?           chatId            = default,
                                         CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new GetChatRequest(chatId ?? client.UserId), cancellationToken);

        public static Task<ChatMember[]> GetChatAdministrators(this IClient      client,
                                                               ChatId?           chatId            = default,
                                                               CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new GetChatAdministratorsRequest(chatId ?? client.UserId), cancellationToken);

        public static Task<int> GetChatMembersCount(this IClient      client,
                                                    ChatId?           chatId            = default,
                                                    CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new GetChatMembersCountRequest(chatId ?? client.UserId), cancellationToken);

        public static Task<ChatMember> GetChatMember(this IClient      client,
                                                     int               userId,
                                                     ChatId?           chatId            = default,
                                                     CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new GetChatMemberRequest(chatId ?? client.UserId, userId), cancellationToken);

        public static Task AnswerCallbackQuery(this IClient      client,
                                               string            callbackQueryId,
                                               string            text              = default,
                                               bool              showAlert         = default,
                                               string            url               = default,
                                               int               cacheTime         = default,
                                               CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new AnswerCallbackQueryRequest(callbackQueryId)
        {
            Text      = text,
            ShowAlert = showAlert,
            Url       = url,
            CacheTime = cacheTime
        }, cancellationToken);

        public static Task RestrictChatMember(this IClient      client,
                                              int               userId,
                                              ChatPermissions   permissions,
                                              ChatId?           chatId            = default,
                                              DateTime          untilDate         = default,
                                              CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(
            new RestrictChatMemberRequest(chatId ?? client.UserId, userId, permissions)
            {
                UntilDate = untilDate
            },
            cancellationToken);

        public static Task PromoteChatMember(this IClient      client,
                                             int               userId,
                                             ChatId?           chatId             = default,
                                             bool?             canChangeInfo      = default,
                                             bool?             canPostMessages    = default,
                                             bool?             canEditMessages    = default,
                                             bool?             canDeleteMessages  = default,
                                             bool?             canInviteUsers     = default,
                                             bool?             canRestrictMembers = default,
                                             bool?             canPinMessages     = default,
                                             bool?             canPromoteMembers  = default,
                                             CancellationToken cancellationToken  = default
        ) =>
        client.MakeRequest(new PromoteChatMemberRequest(chatId ?? client.UserId, userId)
        {
            CanChangeInfo      = canChangeInfo,
            CanPostMessages    = canPostMessages,
            CanEditMessages    = canEditMessages,
            CanDeleteMessages  = canDeleteMessages,
            CanInviteUsers     = canInviteUsers,
            CanRestrictMembers = canRestrictMembers,
            CanPinMessages     = canPinMessages,
            CanPromoteMembers  = canPromoteMembers
        }, cancellationToken);

        public static Task SetChatAdministratorCustomTitle(this IClient      client,
                                                           int               userId,
                                                           string            customTitle,
                                                           ChatId?           chatId            = default,
                                                           CancellationToken cancellationToken = default)
            => client.MakeRequest(
                new SetChatAdministratorCustomTitleRequest(chatId ?? client.UserId, userId, customTitle),
                cancellationToken);

        public static Task SetChatPermissions(this IClient      client,
                                              ChatPermissions   permissions,
                                              ChatId?           chatId            = default,
                                              CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new SetChatPermissionsRequest(chatId ?? client.UserId, permissions), cancellationToken);

        public static Task<BotCommand[]> GetMyCommands(this IClient client, CancellationToken cancellationToken = default) =>
        client.MakeRequest(new GetMyCommandsRequest(), cancellationToken);

        public static Task SetMyCommands(this IClient            client,
                                         IEnumerable<BotCommand> commands,
                                         CancellationToken       cancellationToken = default) =>
        client.MakeRequest(new SetMyCommandsRequest(commands), cancellationToken);

        public static Task<Message> StopMessageLiveLocation(this IClient         client,
                                                            int                  messageId,
                                                            ChatId?              chatId            = default,
                                                            InlineKeyboardMarkup replyMarkup       = default,
                                                            CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(new StopMessageLiveLocationRequest(chatId ?? client.UserId, messageId)
        {
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task StopMessageLiveLocation(this IClient         client,
                                                   string               inlineMessageId,
                                                   InlineKeyboardMarkup replyMarkup       = default,
                                                   CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(new StopInlineMessageLiveLocationRequest(inlineMessageId)
        {
            ReplyMarkup = replyMarkup
        }, cancellationToken);

#region Inline mode

        public static Task AnswerInlineQuery(this IClient                       client,
                                             string                             inlineQueryId,
                                             IEnumerable<InlineQueryResultBase> results,
                                             int?                               cacheTime         = default,
                                             bool                               isPersonal        = default,
                                             string                             nextOffset        = default,
                                             string                             switchPmText      = default,
                                             string                             switchPmParameter = default,
                                             CancellationToken                  cancellationToken = default
        ) =>
        client.MakeRequest(new AnswerInlineQueryRequest(inlineQueryId, results)
        {
            CacheTime         = cacheTime,
            IsPersonal        = isPersonal,
            NextOffset        = nextOffset,
            SwitchPmText      = switchPmText,
            SwitchPmParameter = switchPmParameter
        }, cancellationToken);

# endregion Inline mode

        //#endregion Available methods

#region Updating messages

        public static Task<Message> EditMessageText(this IClient         client,
                                                    int                  messageId,
                                                    string               text,
                                                    ChatId?              chatId                = default,
                                                    ParseMode            parseMode             = default,
                                                    bool                 disableWebPagePreview = default,
                                                    InlineKeyboardMarkup replyMarkup           = default,
                                                    CancellationToken    cancellationToken     = default
        ) =>
        client.MakeRequest(new EditMessageTextRequest(chatId ?? client.UserId, messageId, text)
        {
            ParseMode             = parseMode,
            DisableWebPagePreview = disableWebPagePreview,
            ReplyMarkup           = replyMarkup
        }, cancellationToken);

        public static Task EditMessageText(this IClient         client,
                                           string               inlineMessageId,
                                           string               text,
                                           ParseMode            parseMode             = default,
                                           bool                 disableWebPagePreview = default,
                                           InlineKeyboardMarkup replyMarkup           = default,
                                           CancellationToken    cancellationToken     = default
        ) =>
        client.MakeRequest(new EditInlineMessageTextRequest(inlineMessageId, text)
        {
            DisableWebPagePreview = disableWebPagePreview,
            ReplyMarkup           = replyMarkup,
            ParseMode             = parseMode
        }, cancellationToken);

        public static Task<Message> EditMessageCaption(this IClient         client,
                                                       int                  messageId,
                                                       string               caption,
                                                       ChatId?              chatId            = default,
                                                       InlineKeyboardMarkup replyMarkup       = default,
                                                       CancellationToken    cancellationToken = default,
                                                       ParseMode            parseMode         = default
        ) =>
        client.MakeRequest(new EditMessageCaptionRequest(chatId ?? client.UserId, messageId, caption)
        {
            ParseMode   = parseMode,
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task EditMessageCaption(this IClient         client,
                                              string               inlineMessageId,
                                              string               caption,
                                              InlineKeyboardMarkup replyMarkup       = default,
                                              CancellationToken    cancellationToken = default,
                                              ParseMode            parseMode         = default
        ) =>
        client.MakeRequest(new EditInlineMessageCaptionRequest(inlineMessageId, caption)
        {
            ParseMode   = parseMode,
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task<Message> EditMessageMedia(this IClient         client,
                                                     int                  messageId,
                                                     InputMediaBase       media,
                                                     ChatId?              chatId            = default,
                                                     InlineKeyboardMarkup replyMarkup       = default,
                                                     CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(new EditMessageMediaRequest(chatId ?? client.UserId, messageId, media)
        {
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task EditMessageMedia(this IClient         client,
                                            string               inlineMessageId,
                                            InputMediaBase       media,
                                            InlineKeyboardMarkup replyMarkup       = default,
                                            CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(new EditInlineMessageMediaRequest(inlineMessageId, media)
        {
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task<Message> EditMessageReplyMarkup(this IClient         client,
                                                           int                  messageId,
                                                           ChatId?              chatId            = default,
                                                           InlineKeyboardMarkup replyMarkup       = default,
                                                           CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(
            new EditMessageReplyMarkupRequest(chatId ?? client.UserId, messageId, replyMarkup),
            cancellationToken);

        public static Task EditMessageReplyMarkup(this IClient         client,
                                                  string               inlineMessageId,
                                                  InlineKeyboardMarkup replyMarkup       = default,
                                                  CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(
            new EditInlineMessageReplyMarkupRequest(inlineMessageId, replyMarkup),
            cancellationToken);

        public static Task<Message> EditMessageLiveLocation(this IClient         client,
                                                            int                  messageId,
                                                            float                latitude,
                                                            float                longitude,
                                                            ChatId?              chatId            = default,
                                                            InlineKeyboardMarkup replyMarkup       = default,
                                                            CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(new EditMessageLiveLocationRequest(chatId ?? client.UserId, messageId, latitude, longitude)
        {
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task EditMessageLiveLocation(this IClient         client,
                                                   string               inlineMessageId,
                                                   float                latitude,
                                                   float                longitude,
                                                   InlineKeyboardMarkup replyMarkup       = default,
                                                   CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(new EditInlineMessageLiveLocationRequest(inlineMessageId, latitude, longitude)
        {
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task<Poll> StopPoll(this IClient         client,
                                          int                  messageId,
                                          ChatId?              chatId            = default,
                                          InlineKeyboardMarkup replyMarkup       = default,
                                          CancellationToken    cancellationToken = default
        ) =>
        client.MakeRequest(new StopPollRequest(chatId ?? client.UserId, messageId)
        {
            ReplyMarkup = replyMarkup
        }, cancellationToken);

        public static Task DeleteMessage(this IClient      client,
                                         int               messageId,
                                         ChatId?           chatId            = default,
                                         CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new DeleteMessageRequest(chatId ?? client.UserId, messageId), cancellationToken);

#endregion Updating messages

#region Payments

        public static Task<Message> SendInvoice(this IClient              client,
                                                string                    title,
                                                string                    description,
                                                string                    payload,
                                                string                    providerToken,
                                                string                    startParameter,
                                                string                    currency,
                                                IEnumerable<LabeledPrice> prices,
                                                int?                      chatId                    = default,
                                                string                    providerData              = default,
                                                string                    photoUrl                  = default,
                                                int                       photoSize                 = default,
                                                int                       photoWidth                = default,
                                                int                       photoHeight               = default,
                                                bool                      needName                  = default,
                                                bool                      needPhoneNumber           = default,
                                                bool                      needEmail                 = default,
                                                bool                      needShippingAddress       = default,
                                                bool                      isFlexible                = default,
                                                bool                      disableNotification       = default,
                                                int                       replyToMessageId          = default,
                                                InlineKeyboardMarkup      replyMarkup               = default,
                                                CancellationToken         cancellationToken         = default,
                                                bool                      sendPhoneNumberToProvider = default,
                                                bool                      sendEmailToProvider       = default
        ) =>
        client.MakeRequest(new SendInvoiceRequest(
            (chatId ?? client.UserId),
            title,
            description,
            payload,
            providerToken,
            currency,
            // ReSharper disable once PossibleMultipleEnumeration
            prices
        )
        {
            ProviderData              = providerData,
            PhotoUrl                  = photoUrl,
            PhotoSize                 = photoSize,
            PhotoWidth                = photoWidth,
            PhotoHeight               = photoHeight,
            NeedName                  = needName,
            NeedPhoneNumber           = needPhoneNumber,
            NeedEmail                 = needEmail,
            NeedShippingAddress       = needShippingAddress,
            SendPhoneNumberToProvider = sendPhoneNumberToProvider,
            SendEmailToProvider       = sendEmailToProvider,
            IsFlexible                = isFlexible,
            DisableNotification       = disableNotification,
            ReplyToMessageId          = replyToMessageId,
            ReplyMarkup               = replyMarkup
        }, cancellationToken);

        public static Task AnswerShippingQuery(this IClient                client,
                                               string                      shippingQueryId,
                                               IEnumerable<ShippingOption> shippingOptions,
                                               CancellationToken           cancellationToken = default
        ) =>
        client.MakeRequest(new AnswerShippingQueryRequest(shippingQueryId, shippingOptions), cancellationToken);

        public static Task AnswerShippingQuery(this IClient      client,
                                               string            shippingQueryId,
                                               string            errorMessage,
                                               CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new AnswerShippingQueryRequest(shippingQueryId, errorMessage), cancellationToken);

        public static Task AnswerPreCheckoutQuery(this IClient      client,
                                                  string            preCheckoutQueryId,
                                                  CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new AnswerPreCheckoutQueryRequest(preCheckoutQueryId), cancellationToken);

        public static Task AnswerPreCheckoutQuery(this IClient      client,
                                                  string            preCheckoutQueryId,
                                                  string            errorMessage,
                                                  CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new AnswerPreCheckoutQueryRequest(preCheckoutQueryId, errorMessage), cancellationToken);

#endregion Payments

#region Games

        public static Task<Message> SendGame(this IClient         client,
                                             string               gameShortName,
                                             long?                chatId              = default,
                                             bool                 disableNotification = default,
                                             int                  replyToMessageId    = default,
                                             InlineKeyboardMarkup replyMarkup         = default,
                                             CancellationToken    cancellationToken   = default
        ) =>
        client.MakeRequest(new SendGameRequest(chatId ?? client.UserId, gameShortName)
        {
            DisableNotification = disableNotification,
            ReplyToMessageId    = replyToMessageId,
            ReplyMarkup         = replyMarkup
        }, cancellationToken);

        public static Task<Message> SetGameScore(this IClient      client,
                                                 int               userId,
                                                 int               score,
                                                 int               messageId,
                                                 long?             chatId             = default,
                                                 bool              force              = default,
                                                 bool              disableEditMessage = default,
                                                 CancellationToken cancellationToken  = default
        ) =>
        client.MakeRequest(new SetGameScoreRequest(userId, score, chatId ?? client.UserId, messageId)
        {
            Force              = force,
            DisableEditMessage = disableEditMessage
        }, cancellationToken);

        public static Task SetGameScore(this IClient      client,
                                        int               userId,
                                        int               score,
                                        string            inlineMessageId,
                                        bool              force              = default,
                                        bool              disableEditMessage = default,
                                        CancellationToken cancellationToken  = default
        ) =>
        client.MakeRequest(new SetInlineGameScoreRequest(userId, score, inlineMessageId)
        {
            Force              = force,
            DisableEditMessage = disableEditMessage
        }, cancellationToken);

        public static Task<GameHighScore[]> GetGameHighScores(this IClient      client,
                                                              int               userId,
                                                              int               messageId,
                                                              long?             chatId            = default,
                                                              CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(
            new GetGameHighScoresRequest(userId, chatId ?? client.UserId, messageId),
            cancellationToken);

        public static Task<GameHighScore[]> GetGameHighScores(this IClient      client,
                                                              int               userId,
                                                              string            inlineMessageId,
                                                              CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(
            new GetInlineGameHighScoresRequest(userId, inlineMessageId),
            cancellationToken);

#endregion Games

#region Group and channel management

        public static Task<string> ExportChatInviteLink(this IClient      client,
                                                        ChatId?           chatId            = default,
                                                        CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new ExportChatInviteLinkRequest(chatId ?? client.UserId), cancellationToken);

        public static Task SetChatPhoto(this IClient      client,
                                        InputFileStream   photo,
                                        ChatId?           chatId            = default,
                                        CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new SetChatPhotoRequest(chatId ?? client.UserId, photo), cancellationToken);

        public static Task DeleteChatPhoto(this IClient      client,
                                           ChatId?           chatId            = default,
                                           CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new DeleteChatPhotoRequest(chatId ?? client.UserId), cancellationToken);

        public static Task SetChatTitle(this IClient      client,
                                        string            title,
                                        ChatId?           chatId            = default,
                                        CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new SetChatTitleRequest(chatId ?? client.UserId, title), cancellationToken);

        public static Task SetChatDescription(this IClient      client,
                                              ChatId?           chatId            = default,
                                              string            description       = default,
                                              CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new SetChatDescriptionRequest(chatId ?? client.UserId, description), cancellationToken);

        public static Task PinChatMessage(this IClient      client,
                                          int               messageId,
                                          ChatId?           chatId              = default,
                                          bool              disableNotification = default,
                                          CancellationToken cancellationToken   = default
        ) =>
        client.MakeRequest(new PinChatMessageRequest(chatId ?? client.UserId, messageId)
        {
            DisableNotification = disableNotification
        }, cancellationToken);

        public static Task UnpinChatMessage(this IClient      client,
                                            ChatId?           chatId            = default,
                                            CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new UnpinChatMessageRequest(chatId ?? client.UserId), cancellationToken);

        public static Task SetChatStickerSet(this IClient      client,
                                             string            stickerSetName,
                                             ChatId?           chatId            = default,
                                             CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new SetChatStickerSetRequest(chatId ?? client.UserId, stickerSetName), cancellationToken);

        public static Task DeleteChatStickerSet(this IClient      client,
                                                ChatId?           chatId            = default,
                                                CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new DeleteChatStickerSetRequest(chatId ?? client.UserId), cancellationToken);

#endregion

#region Stickers

        public static Task<StickerSet> GetStickerSet(this IClient      client,
                                                     string            name,
                                                     CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new GetStickerSetRequest(name), cancellationToken);

        public static Task<File> UploadStickerFile(this IClient      client,
                                                   int               userId,
                                                   InputFileStream   pngSticker,
                                                   CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new UploadStickerFileRequest(userId, pngSticker), cancellationToken);

        public static Task CreateNewStickerSet(this IClient      client,
                                               int               userId,
                                               string            name,
                                               string            title,
                                               InputOnlineFile   pngSticker,
                                               string            emojis,
                                               bool              isMasks           = default,
                                               MaskPosition      maskPosition      = default,
                                               CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new CreateNewStickerSetRequest(userId, name, title, pngSticker, emojis)
        {
            ContainsMasks = isMasks,
            MaskPosition  = maskPosition
        }, cancellationToken);

        public static Task AddStickerToSet(this IClient      client,
                                           int               userId,
                                           string            name,
                                           InputOnlineFile   pngSticker,
                                           string            emojis,
                                           MaskPosition      maskPosition      = default,
                                           CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new AddStickerToSetRequest(userId, name, pngSticker, emojis)
        {
            MaskPosition = maskPosition
        }, cancellationToken);

        public static Task CreateNewAnimatedStickerSet(this IClient      client,
                                                       int               userId,
                                                       string            name,
                                                       string            title,
                                                       InputFileStream   tgsSticker,
                                                       string            emojis,
                                                       bool              isMasks           = default,
                                                       MaskPosition      maskPosition      = default,
                                                       CancellationToken cancellationToken = default) =>
        client.MakeRequest(
            new CreateNewAnimatedStickerSetRequest(userId, name, title, tgsSticker, emojis)
            {
                ContainsMasks = isMasks,
                MaskPosition  = maskPosition
            },
            cancellationToken
        );

        public static Task AddAnimatedStickerToSet(this IClient      client,
                                                   int               userId,
                                                   string            name,
                                                   InputFileStream   tgsSticker,
                                                   string            emojis,
                                                   MaskPosition      maskPosition      = default,
                                                   CancellationToken cancellationToken = default) =>
        client.MakeRequest(
            new AddAnimatedStickerToSetRequest(userId, name, tgsSticker, emojis)
            {
                MaskPosition = maskPosition
            },
            cancellationToken
        );

        public static Task SetStickerPositionInSet(this IClient      client,
                                                   string            sticker,
                                                   int               position,
                                                   CancellationToken cancellationToken = default) =>
        client.MakeRequest(
            new SetStickerPositionInSetRequest(sticker, position),
            cancellationToken
        );

        public static Task DeleteStickerFromSet(this IClient      client,
                                                string            sticker,
                                                CancellationToken cancellationToken = default
        ) =>
        client.MakeRequest(new DeleteStickerFromSetRequest(sticker), cancellationToken);

        public static Task SetStickerSetThumb(this IClient      client,
                                              string            name,
                                              int               userId,
                                              InputOnlineFile   thumb             = default,
                                              CancellationToken cancellationToken = default) =>
        client.MakeRequest(
            new SetStickerSetThumbRequest(name, userId, thumb),
            cancellationToken
        );

#endregion
    }
}
#pragma warning restore 8632