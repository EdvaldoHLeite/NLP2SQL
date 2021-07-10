package tradutorNLP;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class NLPtoSQL {
	private String texto;
	
	public NLPtoSQL(String texto) {
		super();
		this.texto = texto;
	}

	public String toSQL() {
		Parse[] parses = this.processarTexto(this.texto);
		String t = null;
		
		for(Parse word:parses){
			t += word + ": " + word.getType() + ", ";
		}
		
		return t;
	}
	
	private Parse[] processarTexto(String texto) {
		InputStream modelIn = null;
		Parse[] words = null;
		
		try {
			modelIn = NLPtoSQL.class.getResourceAsStream("/en-parser-chunking.bin");
			//modelIn = new FileInputStream("en-parser-chumking.bin");
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
}
