package servernew;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import extra.Protocol;
import extra.Protocol.Extension;

import game.*;
import players.*;

/**
 * Server class for hosting games for several players.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
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
    private Game game;
    
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
     * Remove a ClientHandler from the collection of ClientHandlers. 
     * @param handler ClientHandler that will be removed
     */
	public void removeHandler(ClientHandler handler) {
		threads.remove(handler);
	}
	
	public void newGame2() throws IndexOutOfBoundsException {
		Board board = new Board();
		Map<String, List<String>> usersWithColors = new HashMap<String, List<String>>();
		// Set up player 1
		List<String> colors = new ArrayList<String>();
		colors.add(Color.RED.toString());
		colors.add(Color.PURPLE.toString());
		usersWithColors.put(threads.get(0).getClientName(), colors);
		Player player1 = new HumanPlayer(
				threads.get(0).getClientName(), Color.RED, Color.PURPLE, board, 2);
		// Set up player 2
		colors = new ArrayList<String>();
		colors.add(Color.YELLOW.toString());
		colors.add(Color.GREEN.toString());
		usersWithColors.put(threads.get(1).getClientName(), colors);
		Player player2 = new HumanPlayer(
				threads.get(1).getClientName(), Color.YELLOW, Color.GREEN, board, 2);
		game = new Game(player1, player2, board);
		game.start();
		broadcast(Protocol.gameStarted(usersWithColors));

	}
	
	public void newGame3() throws IndexOutOfBoundsException {
		Board board = new Board();
		Map<String, List<String>> usersWithColors = new HashMap<String, List<String>>();
		// Set up player 1
		List<String> colors = new ArrayList<String>();
		colors.add(Color.RED.toString());
		colors.add(Color.GREEN.toString());
		usersWithColors.put(threads.get(0).getClientName(), colors);
		Player player1 = new HumanPlayer(
				threads.get(0).getClientName(), Color.RED, Color.GREEN, board, 3);
		// Set up player 2
		colors = new ArrayList<String>();
		colors.add(Color.PURPLE.toString());
		colors.add(Color.GREEN.toString());
		usersWithColors.put(threads.get(1).getClientName(), colors);
		Player player2 = new HumanPlayer(
				threads.get(1).getClientName(), Color.PURPLE, Color.GREEN, board, 3);
		// Set up player 3
		colors = new ArrayList<String>();
		colors.add(Color.YELLOW.toString());
		colors.add(Color.GREEN.toString());
		usersWithColors.put(threads.get(2).getClientName(), colors);
		Player player3 = new HumanPlayer(
				threads.get(2).getClientName(), Color.YELLOW, Color.GREEN, board, 3);
		game = new Game(player1, player2, player3, board, Color.GREEN);
		game.start();
		broadcast(Protocol.gameStarted(usersWithColors));
	}
	
	public void newGame4() throws IndexOutOfBoundsException {
		Board board = new Board();
		Map<String, List<String>> usersWithColors = new HashMap<String, List<String>>();
		// Set up player 1
		List<String> colors = new ArrayList<String>();
		colors.add(Color.RED.toString());
		usersWithColors.put(threads.get(0).getClientName(), colors);
		Player player1 = new HumanPlayer(
				threads.get(0).getClientName(), Color.RED, board);
		// Set up player 2
		colors = new ArrayList<String>();
		colors.add(Color.PURPLE.toString());
		usersWithColors.put(threads.get(1).getClientName(), colors);
		Player player2 = new HumanPlayer(
				threads.get(1).getClientName(), Color.PURPLE, board);
		// Set up player 3
		colors = new ArrayList<String>();
		colors.add(Color.YELLOW.toString());
		usersWithColors.put(threads.get(2).getClientName(), colors);
		Player player3 = new HumanPlayer(
				threads.get(2).getClientName(), Color.YELLOW, board);
		// Set up player 4
		colors = new ArrayList<String>();
		colors.add(Color.GREEN.toString());
		usersWithColors.put(threads.get(3).getClientName(), colors);
		Player player4 = new HumanPlayer(
				threads.get(3).getClientName(), Color.GREEN, board);
		game = new Game(player1, player2, player3, player4, board);
		Thread thread = new Thread(game);
		thread.start();
		broadcast(Protocol.gameStarted(usersWithColors));
	}
	
	/**
	 * Checks the player whose turn it currently is in the game.
	 * @return the ClientHandler whose turn it is. Returns null if there is no game yet
	 */
	public int getTurn() {
		if (getGame() != null) {
			return getGame().getTurn();
		} else {
			return -1;
		}
	}
	
	/**
	 * Returns the current Game on the Server.
	 * @return the game in session
	 */
	public Game getGame() {
		return game;
	}
	
	//TODO Gamelogic over server
}
