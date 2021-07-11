package tradutorNLP;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * 
 * @author Edvaldo
 * Classe para capturar texto, e chamar as fun��es de processamento
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
	 * M�todo que chama as fun��es dos m�dulos 
	 * que ir�o processar o texto em liguagem natural
	 */
	public void processarTexto() {
		NLPtoSQL conversor = new NLPtoSQL(this.texto);
		//System.getProperty("java.classpath");
		this.saidaTexto = conversor.toSQL();
	}
}
