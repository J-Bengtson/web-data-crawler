package Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class LoggerArquivo extends Logger{
	
	String nomeArquivo;
	LoggerArquivo(String nomeArquivo) throws FileNotFoundException{
		super(new PrintStream(new File(nomeArquivo)));
		this.nomeArquivo = nomeArquivo;
	}
	

}
