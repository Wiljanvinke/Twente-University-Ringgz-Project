package servernew;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import extra.Protocol;
import extra.Protocol.Extension;

/**
 * Server. 
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Server {
    private static final String USAGE
            = "usage: " + Server.class.getName() + " <port> <extention1> <extention2> <extention3>";

    /** Start een Server-applicatie op. */
    public static void main(String[] args) {
        if (args.length > 4 || args.length == 0) {
            System.out.println(USAGE);
            System.exit(0);
        }
        
    	Extension[] extensions = new Extension[args.length - 1];
    	for (int i = 0; i < args.length - 1; i++) {
        	Extension ext = null;
        	switch (args[i + 1]) {
    			case Protocol.CHAT: ext = Protocol.Extension.CHAT; break;
    			case Protocol.CHALLENGE: ext = Protocol.Extension.CHALLENGE; break;
    			case Protocol.LEADERBOARD: ext = Protocol.Extension.LEADERBOARD; break;
    		}
    		extensions[i] = ext;      	
        }
        Server server = new Server(Integer.parseInt(args[0]), extensions);

        server.run();
        
    }


    private int port;
    private List<ClientHandler> threads;
    private Extension[] extensions;
    /** Constructs a new Server object. */
    public Server(int portArg, Extension[] extArg) {
    	port = portArg;
    	extensions = extArg;
    	threads = new ArrayList<ClientHandler>();
    }
    
    /**
     * Listens to a port of this Server if there are any Clients that 
     * would like to connect. For every new socket connection a new
     * ClientHandler thread is started that takes care of the further
     * communication with the Client.
     */
    public void run() {
    	Socket socket = null;
    	ServerSocket ssocket = null;
        try {
        	ssocket = new ServerSocket(port);
        	while (true) {
        		socket = ssocket.accept();
        		ClientHandler client = new ClientHandler(this, socket);
        		addHandler(client);
        	}
        } catch (SocketException exc) {
        	exc.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
        	if (ssocket != null) {
        		try {
        			ssocket.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }
    }
    
    public List<ClientHandler> getHandlers() {
    	return threads;
    }
    
    public void print(String message) {
        System.out.println(message);
    }
    
    /**
     * Sends a message using the collection of connected ClientHandlers
     * to all connected Clients.
     * @param msg message that is send
     */
    public void broadcast(String msg) {
    	for (int i = 0; i < threads.size(); i++) {
    		threads.get(i).sendMessage(msg);
    	}
		print(msg);
    }
    
    /**
     * Add a ClientHandler to the collection of ClientHandlers.
     * @param handler ClientHandler that will be added
     */
    public void addHandler(ClientHandler handler) {
    	threads.add(handler);
		handler.start();
    }
    
    /**
     * Remove a ClientHandler from the collection of ClientHanlders. 
     * @param handler ClientHandler that will be removed
     */
	public void removeHandler(ClientHandler handler) {
		threads.remove(handler);
	}
}
