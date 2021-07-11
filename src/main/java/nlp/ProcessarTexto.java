package nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class ProcessarTexto {
	static InputStream modelIn;
	static InputStream dictLemmatizer;
	
	public ProcessarTexto() {
		this.modelIn = null;
		this.dictLemmatizer = null;
	}

	public static boolean ehVerbo(String tag) {
		if (tag.equals("VB") || tag.equals("VBD") || 
				tag.equals("VBG") || tag.equals("VBN") || 
				tag.equals("VBP") || tag.equals("VBZ")) {
			return true;
		}
		return false;
	}
	
	public static boolean ehEntidade(String tag) {
		if (tag.equals("NN") || tag.equals("NNS") || 
				tag.equals("NNP") || tag.equals("NNPS")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Método que retorna as tags e os lemmas no lugar dos tokens
	 * @param texto
	 * @return
	 */
	public static String[][] obterTokensTags(String texto) {
		ArrayList[] tokens_tagsArrayList = preProcessar(texto);
		List<String> tokensList = tokens_tagsArrayList[0];
		List<String> tagsList = tokens_tagsArrayList[1];
		
		String[] tokens = tokensList.toArray(new String[0]);
		String[] tags = tagsList.toArray(new String[0]);
		// lemas serao usados no lugar dos tokens
		String[] lemmas = obterLemma(tokens, tags);
		
		String[][] tokens_tags = {lemmas, tags};
		return tokens_tags;
	}
	
	/**
	 * Metodo que trata os textos, eliminando tokens e tags nao necessarios
	 * @param texto
	 * @return ArrayList de tokens e ArrayList de tags
	 */
	private static ArrayList[] preProcessar(String texto) {
		ArrayList<Parse> parsesResult = new ArrayList(Arrays.asList(obterTags(texto)));
		ArrayList<Parse> parsesFiltrados = new ArrayList();
		
		ArrayList<String> tokens = new ArrayList();
		ArrayList<String> tags = new ArrayList();
		
		for(Parse word:parsesResult) {
			String tag = word.getType();
			if (tag.equals("NN") || tag.equals("NNS") || tag.equals("VB") ||
					tag.equals("VBD") || tag.equals("VBG") || tag.equals("VBN") || 
					tag.equals("VBP") || tag.equals("VBZ")) {
				
				parsesFiltrados.add(word);
				tokens.add(word.toString());
				tags.add(word.getType().toString());
			}
		}
		
		ArrayList[] tokens_tags = {tokens, tags};
		return tokens_tags;
	}
	
	/**
	 * Metodo para obter as tags (classes gramaticais)
	 * @param texto
	 * @return lista de parses, palavras e suas tags
	 */
	private static Parse[] obterTags(String texto) {
		Parse[] words = null;
		
		try {
			modelIn = ProcessarTexto.class.getResourceAsStream("/en-parser-chunking.bin");
			ParserModel model = new ParserModel(modelIn);
			Parser parser = ParserFactory.create(model);
			
			Parse topParses[] = ParserTool.parseLine(texto, parser, 1);
			
			words = topParses[0].getTagNodes();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
					
				}
			}
		}
		
		return words;
	}
	
	/**
	 * Uso apenas do lema de cada token, evitando problemas com sinonimos e tabelas repetidas com nomes diferentes
	 * @param tokens
	 * @param tags
	 * @return
	 */
	public static String[] obterLemma(String [] tokens, String [] tags) {
		String [] lemmas = null;
		
		try {
			//dictLemmatizer = new FileInputStream("/en-dic-lemmatize.dict");
			dictLemmatizer = ProcessarTexto.class.getResourceAsStream("/en-dic-lemmatize.dict");
			DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
			
			lemmas = lemmatizer.lemmatize(tokens, tags);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
					
				}
			}
		}
		
		return lemmas;
	}
}
