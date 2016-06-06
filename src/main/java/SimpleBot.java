import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.IOException;
import java.util.List;

public class SimpleBot extends TelegramLongPollingBot {
 
	public static void main(String[] args) {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new SimpleBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public String getBotUsername() {
		return "deiteriy_bot";
	}
 
	@Override
	public String getBotToken() {
		return "";
	}

	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
        StringBuilder result = new StringBuilder();
        if (message != null && message.hasText()) {
			String text = message.getText();
			if (text.equals("/help"))
				sendMsg(message, "Привет, я робот");
			else if (text.equals("/javamenu")) {
                Document doc;
                try {
                    doc = Jsoup.connect("http://www.lamantin-kafe.ru/lamantin-menu-bistro-obuhov/").get();
                    Elements eles = doc.select("tbody > tr > td ");

                    int count = 0;
                    for (Element element : eles) {
                        for (TextNode node : element.textNodes()) {
                            if (count != 0) {
                                result.append(node.text());
                                result.append(" ");
                                if (count % 3 == 1) {
                                    result.append("     ");
                                }
                                if (count % 3 == 2) {
                                    result.append("гр   ");
                                }
                                if (count % 3 == 0) {
                                    result.append(" рублей");
                                    result.append(System.getProperty("line.separator"));
                                }
                            }
                            count += 1;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
		}
        sendMsg(message, result.toString());
    }
 
	private void sendMsg(Message message, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
//		sendMessage.setReplayToMessageId(message.getMessageId()); это строка отвечает за то, что бот цитирует сообщение юзера
		sendMessage.setText(text);
		try {
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
 
}
