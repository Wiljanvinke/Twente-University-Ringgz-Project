package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import extra.Protocol;
import extra.Protocol.Extension;
import game.Color;

public class Client {
	private static final String USAGE
    	= "usage: java server.Client <name> <address> <port> <extention1>"
    			+ "<extention2> <extention3>";
	
	/** Starts a Client application. */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println(USAGE);
            System.exit(0);
        }

        String name = args[0];
        InetAddress addr = null;
        int port = 0;
        Socket sock = null;
    	Extension[] extensions = null;
        if (args.length > 3) {
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
        }
        // check args[1] - the IP-adress
        try {
            addr = InetAddress.getByName(args[1]);
        } catch (UnknownHostException e) {
            System.out.println(USAGE);
            System.out.println("ERROR: host " + args[1] + " unknown");
            System.exit(0);
        }

        // parse args[2] - the port
        try {
            port = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println(USAGE);
            System.out.println("ERROR: port " + args[2]
            		           + " is not an integer");
            System.exit(0);
        }

        // try to open a Socket to the server
        try {
            sock = new Socket(addr, port);
        } catch (IOException e) {
            System.out.println("ERROR: could not create a socket on " + addr
                    + " and port " + port);
        }

        // create Peer object and start the two-way communication
        try {
            Peer client = new Peer(name, sock, extensions);
            Thread streamInputHandler = new Thread(client);
            streamInputHandler.start();
            client.handleTerminalInput();
            client.shutDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
