package extra;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://gitlab.com/arthurrump/ringgz.protocol
 * 
 * This class contains constants for all keywords/commands used in the protocol and
 * static methods to create all commands with their arguments. The constants and
 * methods are tagged with [SERVER] or [CLIENT] to indicate which type of program 
 * can send that message. Messages that are part of an extension are tagged with 
 * [EXT (extension name)].
 * @author Arthur Rump
 * @version 1.0.0
 */
public class Protocol {
    // Hide the constructor
    // private Commands() { }

    /**
     * The extensions in the protocol. These are not required to implement.
     * Consult docs/extensions for more information.
     */
    public enum Extension {
        CHAT,
        CHALLENGE,
        LEADERBOARD;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * [CLIENT] The keyword indicating that a client want to join the server.
     * [SERVER] The keyword indicating a response from the server to a login request.
     */
    public static final String LOGIN = "login";

    /**
     * [CLIENT] Indicate that a computer wants to join the server to play a game,
     * supporting some extensions.
     * @param username the username the player wants to use, containing only letters and numbers
     * @param extensions extensions the client supports
     */
    public static String login(String username, Extension[] extensions) {
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Username can only contain letters and numbers.");
        }

        String command = LOGIN + " " + username;
        if (extensions.length > 0) {
            command += " ";
            command += toSpaceSeperatedString(extensions);
        }
        return command;
    }

    /**
     * [SERVER] First argument of login response, in case the login failed.
     */
    public static final String LOGIN_FAIL = "fail";

    /**
     * [SERVER] Indicate that the login failed, supplying the reason as an error code.
     * @param errorCode a code indicating why the login failed, see docs/general for a list of codes
     */
    public static String loginFail(int errorCode) {
        return LOGIN + " " + LOGIN_FAIL + " " + errorCode;
    }

    /**
     * [SERVER] First argument of login response, in case the login succeeded.
     */
    public static final String LOGIN_OK = "ok";

    /**
     * [SERVER] Indicate that the login was successful, supplying extensions that are supported
     * on the server.
     * @param extensions extensions the server supports
     */
    public static String loginOk(Extension[] extensions) {
        String command = LOGIN + " " + LOGIN_OK;
        if (extensions.length > 0) {
            command += " ";
            command += toSpaceSeperatedString(extensions);
        }
        return command;
    }

    /**
     * [CLIENT] The keyword indicating the request to start a game.
     */
    public static final String MAKE_GAME = "make-game";

    /**
     * [CLIENT] Request to start a game, indicating the number of players you
     * want to play with.
     * @param numberOfPlayers the number of players in the game, should be 2, 3 or 4
     */
    public static String makeGame(int numberOfPlayers) {
        return MAKE_GAME + " " + numberOfPlayers;
    }

    /**
     * [SERVER] The keyword indicating a new game was started.
     */
    public static final String GAME_STARTED = "game-started";

    /**
     * [SERVER] Indicate that a game has started, supplying the players of the game
     * and with which colors they play.
     * @param usersWithColors a map with as key the username of the player, and as the value
     *                        a list of colors they play with. See docs/general for the coding
     *                        of colors
     */
    public static String gameStarted(Map<String, List<String>> usersWithColors) {
        return GAME_STARTED + " " +
                usersWithColors.entrySet().stream()
                .map(entry -> entry.getKey() + "(" +
                        entry.getValue().stream()
                                .collect(Collectors.joining(","))
                        + ")")
                .collect(Collectors.joining(" "));
    }

    /**
     * [CLIENT] The keyword indicating a new move, i.e. placing a ring on the board.
     */
    public static final String MAKE_MOVE = "make-move";

    /**
     * [CLIENT] Indicate the placement of a new ring on the board.
     * @param boardRow the row the ring is placed in, with index starting at 0
     * @param boardColumn the column the ring is placed in, with index starting at 0
     * @param ringColor the color of the ring, see docs/general for the coding of colors
     * @param ringSize the size of the ring, see docs/general for the coding of sizes
     */
    public static String makeMove(int boardRow, int boardColumn, String ringColor, int ringSize) {
        return MAKE_MOVE + " " + moveArguments(boardRow, boardColumn, ringColor, ringSize);
    }

    /**
     * [SERVER] The keyword indicating the server received an
     * invalid move from the client.
     */
    public static final String INVALID_MOVE = "invalid-move";

    /**
     * [SERVER] Indicate that the received move was not a valid one.
     */
    public static String invalidMove() {
        return INVALID_MOVE;
    }

    /**
     * [SERVER] The keyword indicating a new move from a player.
     */
    public static final String MOVE_MADE = "move-made";

    /**
     * [SERVER] Broadcast a move made by a player to the other clients, so they
     * can update their internal gamestate.
     * @param boardRow the row the ring is placed in, with index starting at 0
     * @param boardColumn the column the ring is placed in, with index starting at 0
     * @param ringColor the color of the ring, see docs/general for the coding of colors
     * @param ringSize the size of the ring, see docs/general for the coding of sizes
     */
    public static String moveMade(int boardRow, int boardColumn, String ringColor, int ringSize) {
        return MOVE_MADE + " " + moveArguments(boardRow, boardColumn, ringColor, ringSize);
    }

    /**
     * [SERVER] The keyword indicating who is the next player expected to make a move.
     */
    public static final String NEXT_PLAYER = "next-player";

    /**
     * [SERVER] Indicate which player is expected to make a move next. A player could
     * be skipped in the case that there are no valid moves to be made by them.
     * @param username the username of the next player
     */
    public static String nextPlayer(String username) {
        return NEXT_PLAYER + " " + username;
    }

    /**
     * [SERVER] The keyword indicating that the game is finished.
     */
    public static final String GAME_OVER = "game-over";

    /**
     * [SERVER] First argument for game over, in case of a winner.
     */
    public static final String GO_WINNER = "winner";

    /**
     * [SERVER] First argument for game over, in case of a draw.
     */
    public static final String GO_DRAW = "draw";

    /**
     * [SERVER] Indicate that the game is finished and that there's a winner.
     * @param username the username of the winner
     */
    public static String gameOverWinner(String username) {
        return GAME_OVER + " " + GO_WINNER + " " + username;
    }

    /**
     * [SERVER] Indicate that the game is finished and it's a draw.
     */
    public static String gameOverDraw() {
        return GAME_OVER + " " + GO_DRAW;
    }

    /**
     * [EXT CHALLENGE][CLIENT] The keyword indicating a request for a list of players.
     */
    public static final String REQUEST_PLAYERS = "request-players";

    /**
     * [EXT CHALLENGE][CLIENT] Request a list of players that can be challenged for
     * playing a game.
     */
    public static String requestPlayers() {
        return REQUEST_PLAYERS;
    }

    /**
     * [EXT CHALLENGE][SERVER] The keyword indicating the response to a request
     * for a list of players.
     */
    public static final String PLAYERS = "players";

    /**
     * [EXT CHALLENGE][SERVER] The response to a request for a list of players, containing
     * a list of usernames of players on the server that also support the challenge extension.
     * @param usernames a list of usernames of challenge supporting players on the server
     */
    public static final String players(String[] usernames) {
        return PLAYERS + " " + toSpaceSeperatedString(usernames);
    }

    /**
     * [EXT CHALLENGE]
     * [CLIENT] The keyword indicating a request to challenge players.
     * [SERVER] The keyword indicating the result of a challenge.
     */
    public static final String CHALLENGE = "challenge";

    /**
     * [EXT CHALLENGE][CLIENT] A request to challenge players for a game, supplying
     * a list of 1 to 3 usernames to challenge.
     * @param usernames the usernames of the players you want to challenge
     */
    public static String challenge(String[] usernames) {
        return CHALLENGE + " " + toSpaceSeperatedString(usernames);
    }

    /**
     * [EXT CHALLENGE]
     * [SERVER] The keyword indicating an invite for a challenge.
     * [CLIENT] The keyword indicating a response to an invite.
     */
    public static final String INVITE = "invite";

    /**
     * [EXT CHALLENGE][SERVER] A message send to players that are being challenged,
     * listing all participants in the game.
     * @param usernames the usernames of all players that are challenged for the game
     */
    public static String invite(String[] usernames) {
        return INVITE + " " + toSpaceSeperatedString(usernames);
    }

    /**
     * [EXT CHALLENGE][CLIENT] First argument for invite response, if accepted.
     */
    public static final String INVITE_ACCEPTED = "accepted";

    /**
     * [EXT CHALLENGE][CLIENT] Indicate that you accept the challenge.
     */
    public static String inviteAccepted() {
        return INVITE + " " + INVITE_ACCEPTED;
    }

    /**
     * [EXT CHALLENGE][CLIENT] First argument for invite response, if denied.
     */
    public static final String INVITE_DENIED = "denied";

    /**
     * [EXT CHALLENGE][CLIENT] Indicate that you deny the challenge.
     */
    public static String inviteDenied() {
        return INVITE + " " + INVITE_DENIED;
    }

    /**
     * [EXT CHALLENGE][SERVER] Message send to all players in a challenge when
     * one of the challenged players has denied the challenge.
     */
    public static String challengeDenied() {
        return CHALLENGE + " " + INVITE_DENIED;
    }

    /**
     * [EXT LEADERBOARD][CLIENT] The keyword indicating a request for the leaderboard.
     */
    public static final String GET_LEADERBOARD = "get-leaderboard";

    /**
     * [EXT LEADERBOARD][CLIENT] Request the leaderboard from the server.
     */
    public static String getLeaderboard() {
        return GET_LEADERBOARD;
    }

    /**
     * [EXT LEADERBOARD][SERVER] The keyword indicating a message containing the leaderboard.
     */
    public static final String LEADERBOARD = "leaderboard";

    /**
     * [EXT LEADERBOARD][SERVER] A message containing the leaderboard as pairs of usernames
     * and scores. Send to a client as response to the get-leaderboard message.
     * @param usernamesWithScores a map with usernames as keys and scores as values
     */
    public static String leaderboard(Map<String, Integer> usernamesWithScores) {
        return LEADERBOARD + " " +
                usernamesWithScores.entrySet().stream()
                        .map(entry -> entry.getKey() + "(" + entry.getValue() + ")")
                        .collect(Collectors.joining(" "));
    }

    /**
     * [EXT CHAT][CLIENT] The keyword indicating the request to send a chat message.
     */
    public static final String SEND_CHAT = "send-chat";

    /**
     * [EXT CHAT][CLIENT] Request to send a chat message to all other players
     * of the game you're participating in.
     * @param message the message you want to send
     */
    public static String sendChat(String message) {
        return SEND_CHAT + " " + message;
    }

    /**
     * [EXT CHAT][SERVER] The keyword indicating a chat message.
     */
    public static final String CHAT = "chat";

    /**
     * [EXT CHAT][SERVER] A chat message to send to all players of a game.
     * @param username the username of the sender of the message
     * @param message the chat message
     */
    public static String chat(String username, String message) {
        return CHAT + " " + username + " " + message;
    }

    // Some utility methods to improve the code
    private static <E> String toSpaceSeperatedString(E[] list) {
        return Arrays.stream(list)
                .map(e -> e.toString())
                .collect(Collectors.joining(" "));
    }

    private static String moveArguments(int row, int column, String color, int size) {
        return row + " " + column + " " + color + " " + size;
    }
}
