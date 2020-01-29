package Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public abstract class Logger{
	private PrintStream destination;
	
	protected Logger(PrintStream destination) {
		this.destination = destination;
	}
	
	
	public PrintStream getDestination() {
		return destination;
	}

	public void setDestination(PrintStream destination) {
		this.destination = destination;
	}

	
	public static Logger getINSTANCE() {
		if(INSTANCE == null)
			INSTANCE = new LoggerConsole();
		
		return INSTANCE;
	}
	
	public static Logger getINSTANCE(String src) {
		if(INSTANCE == null) {
			if(src != null && !src.isEmpty())
				try {
					INSTANCE = new LoggerArquivo(src);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			else
				INSTANCE = new LoggerConsole();
		}
		return INSTANCE;
	}

	public static void setINSTANCE(Logger iNSTANCE) {
		INSTANCE = iNSTANCE;
	}


	private static Logger INSTANCE;
	
	public void log(String msg) {
		this.getDestination().println(++row +" " + msg);
	};
	
	
	protected long row = 0;
	
	
	
}
