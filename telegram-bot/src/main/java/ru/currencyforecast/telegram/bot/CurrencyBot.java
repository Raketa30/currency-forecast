package ru.currencyforecast.telegram.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.telegram.service.BotService;

import static ru.currencyforecast.telegram.common.Constant.CURRENCY_BOT_NAME;
import static ru.currencyforecast.telegram.common.Constant.CURRENCY_BOT_TOKEN;

@Slf4j
@AllArgsConstructor
public class CurrencyBot extends TelegramLongPollingBot {
    private final BotService botService;
    private final DataModel dataModel;

    @Override
    public String getBotToken() {
        return CURRENCY_BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return CURRENCY_BOT_NAME;
    }

    public void launch() {
        log.info("CurrencyBot launch.....");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.info("CurrencyBot launch failure: {}", e.getMessage());
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
                log.debug("CurrencyBot onUpdateReceived - bot received command {}", command);
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
                    log.debug("CurrencyBot sendResponse - bot send response {}", response.getMessage());
                    break;
                }
            }
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.info("CurrencyBot sendResponse - Telegram exception {}", e.getMessage());
        }
    }
}
