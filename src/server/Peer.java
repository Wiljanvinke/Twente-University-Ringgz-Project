package server;

import game.*;
import players.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Peer implements Runnable {
	public static final String EXIT = "exit";

    protected String name;
    protected Socket sock;
    protected BufferedReader in;
    protected BufferedWriter out;
    
    //*@ requires (nameArg != null) && (sockArg != null);
    /**
     * Constructor. creates a peer object based in the given parameters.
     * @param   nameArg name of the Peer-proces
     * @param   sockArg Socket of the Peer-proces
     */
    public Peer(String nameArg, Socket sockArg) throws IOException {
	 	if (nameArg == null || sockArg == null) {
	 		System.err.println("Please use correct input.");
	 	} else {
	 		this.name = nameArg;
	 		this.sock = sockArg;
	 	}
	 	this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	 	this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
    }
    
    /**
     * Reads strings of the stream of the socket-connection and
     * writes the characters to the default output.
     */
    public void run() {
    	String message;
    	try {
    		while ((message = in.readLine()) != null) {
    			System.out.println(message);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    		this.shutDown();
    	}
    }

    /**
     * Reads a string from the console and sends this string over
     * the socket-connection to the Peer process.
     * On Peer.EXIT the method ends
     */
    public void handleTerminalInput() {
    	try {
    		while (true) {
    			String input = readString(name);
    			if (input != null && !input.equals(EXIT)) {
    				out.write(input + "\n");
    				out.flush();
    			} else {
    				this.shutDown();
    			}
    		}
		} catch (IOException e) {
			e.printStackTrace();
			this.shutDown();
		}
	}


    /**
     * Closes the connection, the sockets will be terminated.
     */
    public void shutDown() {
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**  returns name of the peer object. */
    public String getName() {
        return name;
    }

    /** read a line from the default input. */
    static public String readString(String tekst) {
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
}
