package ru.currencyforecast.telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.currencyforecast.lib.domain.Response;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.telegram.factory.CurrencyBotFactory;
import ru.currencyforecast.telegram.service.BotService;

import static ru.currencyforecast.telegram.common.Constant.CURRENCY_BOT_NAME;
import static ru.currencyforecast.telegram.common.Constant.CURRENCY_BOT_TOKEN;

public class CurrencyBot extends TelegramLongPollingBot {
    private final BotService botService;
    private final DataModel dataModel;

    public CurrencyBot() {
        this.dataModel = CurrencyBotFactory.getDataModel();
        this.botService = CurrencyBotFactory.getBotService(dataModel);
    }

    @Override
    public String getBotToken() {
        return CURRENCY_BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return CURRENCY_BOT_NAME;
    }

    public void launch() {
        try {
            CurrencyBot currencyBot = new CurrencyBot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyBot);
        } catch (TelegramApiException e) {
            //todo logger
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String command = message.getText().toLowerCase();
                botService.execute(command);
                sendResponse(message);
            }
        }
    }

    private void sendResponse(Message message) {
        try {
            SendMessage sendMessage;
            while (true) {
                if (!dataModel.isEmpty()) {
                    Response response = dataModel.getResponseData();
                    if (response.isPicture()) {
                        sendMessage = SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Picture here")
                                .build();
                    } else {
                        sendMessage = SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text((String) response.getMessage().getMessageData())
                                .build();
                    }
                    break;
                }
            }
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo logger
        }
    }
}
