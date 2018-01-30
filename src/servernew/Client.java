package servernew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import extra.Protocol;
import extra.Protocol.Extension;


/**
 * Client class for a simple client-server application.
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Client extends Thread {
	private static final String USAGE = "usage: java server.Client "
			+ "<name> <address> <port> <extention1> <extention2> <extention3>";

	/** Start een Client-applicatie op. */
	public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println(USAGE);
            System.exit(0);
        }

		
		InetAddress host = null;
		int port = 0;
    	Extension[] extensions = null;

		try {
			host = InetAddress.getByName(args[1]);
		} catch (UnknownHostException e) {
			print("ERROR: no valid hostname!");
			System.exit(0);
		}

		try {
			port = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			print("ERROR: no valid portnummer!");
			System.exit(0);
		}	

        extensions = new Extension[args.length - 3];
    	for (int i = 0; i < args.length - 3; i++) {
        	Extension ext = null;
        	switch (args[i + 3]) {
    			case Protocol.CHAT: ext = Protocol.Extension.CHAT; break;
    			case Protocol.CHALLENGE: ext = Protocol.Extension.CHALLENGE; break;
    			case Protocol.LEADERBOARD: ext = Protocol.Extension.LEADERBOARD; break;
    		}
    		extensions[i] = ext;      	
        }
		try {
			Client client = new Client(args[0], host, port, extensions);
			client.login();
			client.loginVerify();
			client.start();
			do {
				String input = readString("");
				client.sendMessage(input);
			} while (true);
			
		} catch (IOException e) {
			print("ERROR: couldn't construct a client object!");
			System.exit(0);
		}

	}
	
	private String clientName;
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
    protected Extension[] extensions;
    private Extension[] serverextensions = new Extension[0];
	public static final String EXIT = "exit";


	/**
	 * Constructs a Client-object and tries to make a socket connection.
	 */
	public Client(String name, InetAddress host, int port, Extension[] ext) throws IOException {

		clientName = name;
		sock = new Socket(host, port);
		if (ext != null) {
			this.extensions = ext;
		}
		this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

	}

	/**
	 * Reads the messages in the socket connection. Each message will
	 * be forwarded to the MessageUI
	 */
	public void run() {
		String input;
		try {
			while (true) {
				input = in.readLine();
				Scanner commandsc = new Scanner(input);
    			String command =  commandsc.next();
    			switch (command) {
    				//case Protocol.LOGIN: loginVerify(); break;
    			}
				System.out.println(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
			this.shutdown();
		}
	}

	/** send a message to a ClientHandler. */
	public void sendMessage(String msg) {
		try {
			out.write(msg + "\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			this.shutdown();
		}
	}

	/** close the socket connection. */
	public void shutdown() {
		print("Closing socket connection...");
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/** returns the client name. */
	public String getClientName() {
		return clientName;
	}
	
	private static void print(String message) {
		System.out.println(message);
	}
	
	public static String readString(String tekst) {
		System.out.print(tekst);
		String antw = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			antw = in.readLine();
		} catch (IOException e) {
		}

		return (antw == null) ? "" : antw;
	}
	
    private String removeCommand(String command) {
    	String[] newstring = command.split(" ", 2);
    	return newstring[1];
    }
	
	public void login() {
		sendMessage(Protocol.login(clientName, extensions));
	}
	
	public void loginVerify() throws IOException {
		String input = in.readLine();
		if (input.contains(Protocol.LOGIN_OK)) {
			String pureinput = removeCommand(input);
			Scanner scan = new Scanner(pureinput);
			List<Extension> exttemp = new ArrayList<Extension>();
			while (scan.hasNext()) {
				switch (scan.next()) {
					case Protocol.CHAT:
						exttemp.add(Protocol.Extension.CHAT);
						break;
					case Protocol.CHALLENGE:
						exttemp.add(Protocol.Extension.CHALLENGE);
						break;
					case Protocol.LEADERBOARD:
						exttemp.add(Protocol.Extension.LEADERBOARD);
						break;
				}
			}
			serverextensions = new Extension[exttemp.size()];
			for (int i = 0; i < exttemp.size(); i++) {
				serverextensions[i] = exttemp.get(i);
			}
			scan.close();
		} else if (input.contains(Protocol.LOGIN_FAIL)) {
			if (input.contains("0")) {
				System.out.println("The username is not in a valid format. A username "
						+ "can only contain letters (upper- and lowercase) and numbers.");
				shutdown();
			}
			if (input.contains("1")) {
				System.out.println("The username is already in use by another client.");
				shutdown();
			}
		}
	}
}
