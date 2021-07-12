package bot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;

public class BotManaged {
	Bot bot;
	Chat chatSession;
	
	public BotManaged() {
		Bot bot = new Bot(BotConfiguration.builder()
				.name("alice")
				.path("src/main/resources")
				.build());
		this.chatSession = new Chat(bot);
	}
	
	public String resposta(String texto) {
		return this.chatSession.multisentenceRespond(texto);
	}
}
