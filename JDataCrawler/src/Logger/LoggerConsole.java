package Logger;

public class LoggerConsole extends Logger {
	LoggerConsole(){
		super(System.out);
	}
	@Override
	public
	void log(String mensage) {
		this.getDestination().println(mensage);
	}

}
