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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import exceptions.InvalidMoveArgumentException;
import extra.Protocol;
import extra.Protocol.Extension;

import players.*;
import game.*;


/**
 * Connects to a <code>Server</code> to act like a <code>Player</code>.
 * @author Wouter Bezemer
 * @author Wiljan Vinke
 * @version 0.1
 */
public class Client extends Thread {
	private static final String USAGE = "usage: java server.Client "
			+ "<name> <address> <port> <human/cpu> <extention1> <extention2> <extention3>";
	private static final String HUMAN = "human";
	private static final String CPU = "cpu";

	/** Start een Client-applicatie op. */
	public static void main(String[] args) {
        if (args.length < 4) {
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
		
		boolean human = false;
		if (args[3].equals(HUMAN)) {
			human = true;
		} else if (args[3].equals(CPU)) {
			human = false;
		} else {
			print("ERROR: please specify human or computer player (human/cpu)");
			System.exit(0);
		}

        extensions = new Extension[args.length - 4];
    	for (int i = 0; i < args.length - 4; i++) {
        	Extension ext = null;
        	switch (args[i + 4]) {
    			case Protocol.CHAT: ext = Protocol.Extension.CHAT; break;
    			case Protocol.CHALLENGE: ext = Protocol.Extension.CHALLENGE; break;
    			case Protocol.LEADERBOARD: ext = Protocol.Extension.LEADERBOARD; break;
    		}
    		extensions[i] = ext;      	
        }
		try {
			Client client = new Client(args[0], host, port, human, extensions);
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
	private boolean human;
	private Game game;
	private Player player;


	/**
	 * Constructs a Client-object and tries to make a socket connection.
	 */
	public Client(String name, InetAddress host, int port, boolean human, Extension[] ext) 
			throws IOException {

		clientName = name;
		sock = new Socket(host, port);
		this.human = human;
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
    				case Protocol.GAME_STARTED: gameStarted(input); break;
    				case Protocol.NEXT_PLAYER: nextPlayer(input); break;
    			}
				System.out.println(input);
			}
		} catch (IOException e) {
			e.printStackTrace();			
			this.shutdown();
		}
	}
	
	public void checkCommand(String msg) {
		Scanner commandsc = new Scanner(msg);
		String command =  commandsc.next();
		String commandComplete = "";
		switch (command) {
			case Protocol.MAKE_GAME: commandComplete = makeGame(msg); break;
		}
		if (!commandComplete.equals("")) {
			sendMessage(commandComplete);
		}
		commandsc.close();
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
	
	public String makeGame(String input) {
		int numberOfPlayers = Integer.parseInt(removeCommand(input));
		String commands = Protocol.makeGame(numberOfPlayers);
		return commands;
	}
	
	public void gameStarted(String input) {
		String pureInput = removeCommand(input);
		List<String> colors;
		Map<String, List<String>> usersWithColors = new HashMap<String, List<String>>();
		Scanner playersc = new Scanner(pureInput);
		String[] playerncolors;
		while (playersc.hasNext()) {
			playerncolors = playersc.next().split("[(),]");
			colors = new ArrayList<String>();
			colors.add(playerncolors[1]);
			if (playerncolors.length < 4) {
				colors.add(playerncolors[2]);
			}
			usersWithColors.put(playerncolors[0], colors);
		}
		playersc.close();
		List<Player> players = new ArrayList<Player>();
		Board board = new Board();
		Thread thread = null;
		Player playertemp = null;
		switch (usersWithColors.size()) {
			case 2: 
				for (String playerName: usersWithColors.keySet()) {
					if (!human && playerName.equals(clientName)) {
						this.player = new ComputerPlayer(playerName, 
								Color.toEnum(usersWithColors.get(playerName).get(0)),
								Color.toEnum(usersWithColors.get(playerName).get(1)), board, 2);
						players.add(this.player);
					} else {
						playertemp = new HumanPlayer(playerName, 
								Color.toEnum(usersWithColors.get(playerName).get(0)),
								Color.toEnum(usersWithColors.get(playerName).get(1)), board, 2);
						players.add(playertemp);
						if (playerName.equals(clientName)) {
							this.player = playertemp;
						}
					}
				} 
				game = new Game(players.get(0), players.get(1), board);
				thread = new Thread(game);
				thread.start(); 
				break;
			case 3:
				for (String playerName: usersWithColors.keySet()) {
					if (!human && playerName.equals(clientName)) {
						this.player = new ComputerPlayer(playerName, 
								Color.toEnum(usersWithColors.get(playerName).get(0)),
								Color.toEnum(usersWithColors.get(playerName).get(1)), board, 3);
						players.add(this.player);
					} else {
						playertemp = new HumanPlayer(playerName, 
								Color.toEnum(usersWithColors.get(playerName).get(0)),
								Color.toEnum(usersWithColors.get(playerName).get(1)), board, 3);
						players.add(playertemp);
						if (playerName.equals(clientName)) {
							this.player = playertemp;
						}
					}
				} 
				// sharedColor assumes the second color of every player is shared
				Color sharedColor = Color.toEnum(usersWithColors.get(clientName).get(1));
				game = new Game(players.get(0), players.get(1), players.get(2), board, sharedColor);
				thread = new Thread(game);
				thread.start(); 
				break;
			case 4:
				for (String playerName: usersWithColors.keySet()) {
					if (!human && playerName.equals(clientName)) {
						this.player = new ComputerPlayer(playerName, 
								Color.toEnum(usersWithColors.get(playerName).get(0)), board);
						players.add(this.player);
					} else {
						playertemp = new HumanPlayer(playerName, 
								Color.toEnum(usersWithColors.get(playerName).get(0)), board);
						players.add(playertemp);
						if (playerName.equals(clientName)) {
							this.player = playertemp;
						}
					}
				} 
				game = new Game(players.get(0), players.get(1), players.get(2), players.get(4), 
						board);
				thread = new Thread(game);
				thread.start(); 
				break;
		}
		
		System.out.println(pureInput);
	}

	public void nextPlayer(String input) {
		String nextPlayer = removeCommand(input);
		String move = "";
		if (nextPlayer.equals(getClientName())) {
			boolean valid = false;
			while (!valid) {
				try {
					move = player.makeMove();
					sendMessage(move);
					valid = true;
				} catch (InvalidMoveArgumentException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		game.update();
		
	}
}
