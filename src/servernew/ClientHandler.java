package servernew;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import extra.Protocol;
import extra.Protocol.Extension;

/**
 * Class for handling individual <code>Client</code>s connected to a <code>Server</code>.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class ClientHandler extends Thread {
    private Server server;
    private BufferedReader in;
    private BufferedWriter out;
    private String clientName;
    private Extension[] extensions;

    /**
     * Constructs a ClientHandler object
     * Initialises both Data streams.
     */
    //@ requires serverArg != null && sockArg != null;
    public ClientHandler(Server serverArg, Socket sockArg) throws IOException {
    	server = serverArg;
	 	this.in = new BufferedReader(new InputStreamReader(sockArg.getInputStream()));
	 	this.out = new BufferedWriter(new OutputStreamWriter(sockArg.getOutputStream()));
    }
    
    public String getClientName() {
    	return clientName;
    }

    /**
     * Reads the name of a Client from the input stream and sends 
     * a broadcast message to the Server to signal that the Client
     * is participating in the chat. Notice that this method should 
     * be called immediately after the ClientHandler has been constructed.
     */
    public void login(String input) throws IOException {
    	String pureinput = removeCommand(input);
    	Scanner scan = new Scanner(pureinput);
    	if (scan.hasNext()) {
    		String name = scan.next();
    		if (name.matches("^[a-zA-Z0-9]+$")) {
    			boolean validname = true;
    			for (int i = 0; i < server.getHandlers().size() - 1; i++) {
    				if (server.getHandlers().get(i).getClientName().equals(name)) {
    					sendMessage(Protocol.loginFail(1));
    					validname = false;
    					shutdown();
    				}
				}
				if (validname) {
					clientName = name;
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
					extensions = new Extension[exttemp.size()];
					for (int i = 0; i < exttemp.size(); i++) {
						extensions[i] = exttemp.get(i);
					}
					sendMessage(Protocol.loginOk(extensions));
					server.broadcast("[" + clientName + " has entered]");
				}
			} else {
				sendMessage(Protocol.loginFail(0));
				shutdown();
			}
		}
    	scan.close();
    }

    /**
     * This method takes care of sending messages from the Client.
     * Every message that is received, is preprended with the name
     * of the Client, and the new message is offered to the Server
     * for broadcasting. If an IOException is thrown while reading
     * the message, the method concludes that the socket connection is
     * broken and shutdown() will be called. 
     */
    public void run() {
    	try {
    		while (true) {
    			String input = in.readLine();
    			Scanner commandsc = new Scanner(input);
    			String command =  commandsc.next();
    			switch (command) {
    				case Protocol.LOGIN: login(input); break;
    				case Protocol.MAKE_GAME: makeGame(input); break;
    			}
    			server.print(clientName + ": ");
    		}
		} catch (IOException e) {
			e.printStackTrace();
			this.shutdown();
		}
		
    }

    /**
     * This method can be used to send a message over the socket
     * connection to the Client. If the writing of a message fails,
     * the method concludes that the socket connection has been lost
     * and shutdown() is called.
     */
	public void sendMessage(String msg) {
		try {
			out.write(msg + "\n");
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
			this.shutdown();
		}
	}

    /**
     * This ClientHandler signs off from the Server and subsequently
     * sends a last broadcast to the Server to inform that the Client
     * is no longer participating in the chat. 
     */
    private void shutdown() {
        server.removeHandler(this);
        server.broadcast("[" + clientName + " has left]");
    }
    
    private String removeCommand(String command) {
    	String[] newstring = command.split(" ", 2);
    	return newstring[1];
    }
    
    private void makeGame(String input) {
    	int numberOfPlayers = Integer.parseInt(removeCommand(input));
    	try {
    		switch (numberOfPlayers) {   	
    			case 2: server.newGame2(); break;
    			case 3: server.newGame3(); break;
    			case 4: server.newGame4(); break;
    			default: break;
    		}
    	} catch (IndexOutOfBoundsException e) {
    		sendMessage("Invalid Arguments: make-game");
    	}
    }
}
