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
import ru.currencyforecast.lib.domain.CurrencyData;
import ru.currencyforecast.lib.domain.response.Response;
import ru.currencyforecast.lib.domain.response.ResponseType;
import ru.currencyforecast.lib.model.DataModel;
import ru.currencyforecast.telegram.service.BotService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
                    Response response = dataModel.getResponse();
                    ResponseType type = response.getType();
                    String chatId = message.getChatId().toString();
                    switch (type) {
                        case DATA:
                            sendDataResponse(response, chatId);
                            break;
                        case TEXT:
                            sendTextResponse(response, chatId);
                            break;
                        case IMAGE:
                            sendTrendResponse(response, chatId);
                            break;
                    }
                    log.debug("CurrencyBot sendResponse - bot send response {}", response.getMessage());
                    break;
                }
            }
        } catch (TelegramApiException | IOException e) {
            log.info("CurrencyBot sendResponse - Telegram exception {}", e.getMessage());
        }
    }

    private void sendDataResponse(Response response, String chatId) throws TelegramApiException {
        String message = transformToText((List<CurrencyData>) response.getMessage().getData());
        sendMessage(chatId, message);
    }

    private void sendTextResponse(Response response, String chatId) throws TelegramApiException {
        String data = (String) response.getMessage().getData();
        sendMessage(chatId, data);
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

    private String transformToText(List<CurrencyData> forecast) {
        StringBuilder stringBuilder = new StringBuilder();
        forecast.forEach(currencyData -> stringBuilder.append(currencyData).append("\n"));
        return stringBuilder.toString();
    }

    private void sendMessage(String chatId, String data) throws TelegramApiException {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(data)
                .build();
        execute(sendMessage);
    }

    private File getImageFile(Response response) throws IOException {
        BufferedImage bufferedImage = (BufferedImage) response.getMessage().getData();
        Files.createDirectories(Paths.get("temp"));
        File trend = new File("temp/trend.jpg");
        ImageIO.write(bufferedImage, "jpg", trend);
        return trend;
    }
}
