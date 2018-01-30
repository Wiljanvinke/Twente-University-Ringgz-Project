package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server {
//	private static final String USAGE
//		= "usage: " + Server.class.getName() + " <name> <port>";
//	
//	/** Starts a Server-application. */
//    public static void main(String[] args) {
//    	if (args.length != 2) {
//            System.out.println(USAGE);
//            System.exit(0);
//        }
//    	
//    	String name = args[0];
//        int port = 0;
//        Socket socket = null;
//
//        // parse args[1] - the port
//        try {
//            port = Integer.parseInt(args[1]);
//        } catch (NumberFormatException e) {
//            System.out.println(USAGE);
//            System.out.println("ERROR: port " + args[1]
//             		           + " is not an integer");
//            System.exit(0);
//        }
//         
//        // create a new socket on the server'
//        ServerSocket ssocket = null;
//        try {
//        	ssocket = new ServerSocket(port);
//        	while (true) {
//        		socket = ssocket.accept();
//        		Peer client = new Peer(name, socket);
//        		Thread streamInputHandler = new Thread(client);
//        		streamInputHandler.start();
//                client.handleTerminalInput();
//                client.shutDown();
//        	}
//        } catch (SocketException exc) {
//        	exc.printStackTrace();
//        } catch (IOException e) {
//        	e.printStackTrace();
//            //System.out.println("ERROR: could not create a socket on " + addr
//            //         + " and port " + port);
//        } finally {
//        	if (ssocket != null) {
//        		try {
//        			ssocket.close();
//        		} catch (IOException e) {
//        			e.printStackTrace();
//        		}
//        	}
//        }
//    }
}
