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

	public static Logger getINSTANCE(int op) {
		if(INSTANCE == null) {
			if(op == 1)
				try {
					INSTANCE = new LoggerArquivo("crawlerLog");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
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
	
	public abstract void log(String mensage);
	
	
	
}
