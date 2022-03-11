package ru.currencyforecast.telegram.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.telegram.service.BotService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            while (true) {
                if (dataModel.isNotEmpty()) {
                    Response response = dataModel.getResponseData();
                    String chatId = message.getChatId().toString();
                    if (response.isPicture()) {
                        sendTrendResponse(response, chatId);
                    } else {
                        sendTextResponse(response, chatId);
                    }
                    log.debug("CurrencyBot sendResponse - bot send response {}", response.getMessage());
                    break;
                }
            }
        } catch (TelegramApiException | IOException e) {
            log.info("CurrencyBot sendResponse - Telegram exception {}", e.getMessage());
        }
    }

    private void sendTrendResponse(Response response, String chatId) throws TelegramApiException, IOException {
        File trend = getImageFile(response);
        if (trend.exists()) {
            SendPhoto sendPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(trend, "trend"))
                    .build();
            execute(sendPhoto);
        }
    }

    private void sendTextResponse(Response response, String chatId) throws TelegramApiException {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text((String) response.getMessage().getMessageData())
                .build();
        execute(sendMessage);
    }

    private File getImageFile(Response response) throws IOException {
        BufferedImage bufferedImage = (BufferedImage) response.getMessage().getMessageData();
        Files.createDirectories(Paths.get("temp"));
        File trend = new File("temp/trend.jpg");
        ImageIO.write(bufferedImage, "jpg", trend);
        return trend;
    }
}
