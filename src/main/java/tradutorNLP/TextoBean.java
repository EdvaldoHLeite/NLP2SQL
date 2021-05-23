package tradutorNLP;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * 
 * @author Edvaldo
 * Classe para capturar texto, e chamar as funções de processamento
 */
@ManagedBean
@SessionScoped
public class TextoBean {
	private String texto;
	private String saidaTexto;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String getSaidaTexto() {
		return saidaTexto;
	}

	public void setSaidaTexto(String saidaTexto) {
		this.saidaTexto = saidaTexto;
	}

	/**
	 * Método que chama as funções dos módulos 
	 * que irão processar o texto em liguagem natural
	 */
	public void processarTexto() {
		this.saidaTexto = this.texto;
	}
}
