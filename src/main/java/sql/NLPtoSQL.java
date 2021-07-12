package sql;

import java.util.ArrayList;

import nlp.ProcessarTexto;

public class NLPtoSQL {
	/**
	 * salva uma lista de scripts no banco de dados
	 * @param scripts
	 */
	public static void salvarScripts (ArrayList<String> scripts) {
		for (String script:scripts) {
			DB.updateDatabase(script);
		}
	}
	
	public static ArrayList<String> nlpToSQL(String[] tokens, String[] tags) {
		ArrayList<String> scriptsSql = new ArrayList();
		String scriptSql = "";
			
		for(int i=0; i<tokens.length; i++) {
			// se tiver um verbo no meio relacionando as entidades
			if (i > 0 && i<tokens.length && ProcessarTexto.ehVerbo(tags[i]) && 
				ProcessarTexto.ehEntidade(tags[i-1]) &&
				ProcessarTexto.ehEntidade(tags[i+1])) {
			
				// criacao de tabela de entidade
				scriptsSql.add(scriptEntidade(tokens[i-1]));
				scriptsSql.add(scriptEntidade(tokens[i+1]));
				// criacao de tabela de relacionamento
				scriptsSql.add(scriptVerbo(tokens[i-1], tokens[i+1], tokens[i]));
			}
		}
		return scriptsSql;
	}
	
	private static String scriptVerbo(String token1, String token2, String verbo) {
		String scriptSql = "CREATE TABLE IF NOT EXISTS " +token1+verbo+token2+ " (\r\n"
				+ "    id SERIAL PRIMARY KEY, \r\n"
				+ "    CONSTRAINT id_"+token1+" FOREIGN KEY (id) REFERENCES "+token1+", \r\n"
				+ "    CONSTRAINT id_"+token2+"	FOREIGN KEY (id) REFERENCES "+token2+" \r\n"
				+ ");";
		return scriptSql;
	}
	
	private static String scriptEntidade(String token) {
		String scriptSql = "CREATE TABLE IF NOT EXISTS " +token+ " (\r\n"
				+ "    id SERIAL PRIMARY KEY \r\n"
				+ ");";
		return scriptSql;
	}
}
