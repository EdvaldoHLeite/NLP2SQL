package bot;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;

import nlp.ProcessarTexto;
import sql.NLPtoSQL;

@ManagedBean
@SessionScoped
public class BotBean {
	private String textoBot;
	private String textoEntrada;

	public String getTextoBot() {
		return textoBot;
	}

	public void setTextoBot(String textoBot) {
		this.textoBot = textoBot;
	}

	public String getTextoEntrada() {
		return textoEntrada;
	}

	public void setTextoEntrada(String textoEntrada) {
		this.textoEntrada = textoEntrada;
	}

	/**
	 * Método que chama as funções dos módulos 
	 * que irão processar o texto em liguagem natural
	 */
	public void processarTexto() {
		String[][] tokens = ProcessarTexto.obterTokensTags(textoEntrada);
		ArrayList<String> scripts = NLPtoSQL.nlpToSQL(tokens[0], tokens[1]);
		NLPtoSQL.salvarScripts(scripts);
		
		String texto = "Script das tabelas ---- ";
		for(String script:scripts)
			texto += script + "\n";
		this.textoBot = texto;
		
		if (scripts.size() == 0)
			this.textoBot = "Sem resposta";
	}
	
	/**
	 * Método de redirecionamento para página do ChatBot
	 * @throws IOException 
	 */
	public void abrirChat() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("chat.xhtml");
	}
}
