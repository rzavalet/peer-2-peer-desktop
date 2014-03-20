package com.distributedsystems.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PeerClient {

	private PeerNode peerNode;
	private SnakeView snakeView = null;	
	private BufferedReader br;
	
	private boolean shutdown = false;
	private static final boolean debug = true;

	private class PeerName implements HandlerInterface {
		private String myId;
		
		public PeerName(String peer) {
			myId = peer;
		}
		
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			PeerMessage messageName = null;
			Debug.print("... Replying with peer name: " + myId, debug);
			
			messageName = new PeerMessage(PeerNode.REPLY, myId);
			try {
				connection.sendData(messageName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class PeerList implements HandlerInterface {
		private PeerNode peer;
		
		public PeerList(PeerNode peer) {
			this.peer = peer;
		}
		
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			PeerMessage messageList = null;
			Debug.print("Listing peers", debug);
			
			messageList = new PeerMessage(PeerNode.REPLY, String.valueOf(peer.getNumberOfPeers()));
			try {
				connection.sendData(messageList);
				for (String currentPeerId : peer.getPeerKeys()) {
					PeerInformation currentPeerInformation= peer.getPeer(currentPeerId);
					messageList = new PeerMessage(PeerNode.REPLY, 
							currentPeerInformation.getPeerId() + " " + currentPeerInformation.getHost() + 
							" " + currentPeerInformation.getPort());
					connection.sendData(messageList);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private class AddPeer implements HandlerInterface {
		private PeerNode peer;
		
		public AddPeer(PeerNode peer) {
			this.peer = peer;
		}
		
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			String[] dataList = message.getMessageData().split(" ");
			String peerId = dataList[0];
			String host = dataList[1];
			String port = dataList[2];
			
			PeerInformation peerInformation = new PeerInformation(peerId, host, Integer.valueOf(port));
			
			if (peer.getPeerKeys().contains(peerId) == false) {
				peer.insertPeer(peerInformation);
			}
						
			PeerMessage replyMessage = new PeerMessage(PeerNode.REPLY, "Peer added: " + peerId);
			try {
				connection.sendData(replyMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	

	private class StartGame implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** STARTING GAME");
		}
	}
	
	private class EndGame implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** END GAME");
		}
	}
	
	private class MoveRight implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** MOVING RIGHT");
		}
	}
	
	private class MoveLeft implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** MOVING LEFT");
		}
	}
	
	private class MoveUp implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** MOVING UP");
		}
	}
	
	private class MoveDown implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** MOVING DOWN");
		}
	}
	private class NewLeader implements HandlerInterface {
		private PeerNode peer;
		
		public NewLeader(PeerNode peer) {
			this.peer = peer;
		}
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			PeerMessage messageName = null;
			Debug.print("... Setting new leader: " + message.getMessageData(), debug);
			peer.setLeaderId(message.getMessageData());
		}
		
	}
	
	private class GetLeader implements HandlerInterface {
		private PeerNode peer;
		
		public GetLeader(PeerNode peer) {
			this.peer = peer;
		}
		
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			PeerMessage messageName = null;
			Debug.print("... Replying with leader name: " + peer.getLeaderId(), debug);
			
			messageName = new PeerMessage(PeerNode.REPLY, peer.getLeaderId());
			try {
				connection.sendData(messageName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
	private class SendSnake implements HandlerInterface {
		private SnakeView snake;
		
		public SendSnake(SnakeView snake) {
			this.snake = snake;
		}
		
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			PeerMessage messageList = null;
			Debug.print("Sending snake", debug);
			
			messageList = new PeerMessage(PeerNode.REPLY, String.valueOf(snake.getmSnakeTrail().size()));
			
			try {
				connection.sendData(messageList);
				for (Coordinate currentCoordinate : snake.getmSnakeTrail()) {
					messageList = new PeerMessage(PeerNode.REPLY, 
							currentCoordinate.x + " " + currentCoordinate.y);
					connection.sendData(messageList);
				}			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private class SendApples implements HandlerInterface {
		private SnakeView snake;
		
		public SendApples(SnakeView snake) {
			this.snake = snake;
		}
		
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			PeerMessage messageList = null;
			Debug.print("Sending apples", debug);
			
			messageList = new PeerMessage(PeerNode.REPLY, String.valueOf(snake.getmAppleList().size()));
			
			try {
				connection.sendData(messageList);
				for (Coordinate currentCoordinate : snake.getmAppleList()) {
					messageList = new PeerMessage(PeerNode.REPLY, 
							currentCoordinate.x + " " + currentCoordinate.y);
					connection.sendData(messageList);
				}			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private class SendConfig implements HandlerInterface {
		private SnakeView snake;
		
		public SendConfig(SnakeView snake) {
			this.snake = snake;
		}
		
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			PeerMessage messageList = null;
			Debug.print("Sending configuration", debug);
			
			String[] size = message.getMessageData().split(" ");
			snake.resetView(Integer.parseInt(size[0]), Integer.parseInt(size[1]));
			
			messageList = new PeerMessage(PeerNode.REPLY, snake.getmMoveDelay() + " " + snake.getmNextDirection() + " " + snake.getmScore()
					+ " " + snake.getWidth() + " " + snake.getHeight());
			
			try {
				connection.sendData(messageList);		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	private class BlockGame implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** BLOCKING GAME");
			PeerMessage replyMessage = new PeerMessage(PeerNode.REPLY, "Peer Blocked");
			try {
				connection.sendData(replyMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class UnblockGame implements HandlerInterface {
		@Override
		public void handleMessage(PeerConnection connection, PeerMessage message) {
			System.out.println("*** UNBLOCKING GAME");
			PeerMessage replyMessage = new PeerMessage(PeerNode.REPLY, "Peer Unblocked");
			try {
				connection.sendData(replyMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public PeerClient(String id, int port, PeerInformation trackerPeerInformation) {
		peerNode = new PeerNode(id, port, trackerPeerInformation);
		snakeView = new SnakeView();
		
		//Add handlers
		HandlerInterface peerName = new PeerName(peerNode.getPeerId());
		peerNode.addHandler(PeerNode.GET_PEER_NAME, peerName);
		
		HandlerInterface peerList = new PeerList(peerNode);
		peerNode.addHandler(PeerNode.GET_PEER_LIST, peerList);
		
		HandlerInterface addPeer = new AddPeer(peerNode);
		peerNode.addHandler(PeerNode.ADD_PEER, addPeer);
		
		HandlerInterface startGame = new StartGame();
		peerNode.addHandler(PeerNode.START_GAME, startGame);
		
		HandlerInterface endGame = new EndGame();
		peerNode.addHandler(PeerNode.END_GAME, endGame);
		
		HandlerInterface moveRight = new MoveRight();
		peerNode.addHandler(PeerNode.MOVE_RIGHT, moveRight);
		
		HandlerInterface moveLeft = new MoveLeft();
		peerNode.addHandler(PeerNode.MOVE_LEFT, moveLeft);
		
		
		HandlerInterface moveUp = new MoveUp();
		peerNode.addHandler(PeerNode.MOVE_UP, moveUp);
		
		HandlerInterface moveDown = new MoveDown();
		peerNode.addHandler(PeerNode.MOVE_DOWN, moveDown);	
		
		HandlerInterface getLeader = new GetLeader(peerNode);
		peerNode.addHandler(PeerNode.GET_LEADER, getLeader);		
		
		HandlerInterface sendSnake = new SendSnake(snakeView);
		peerNode.addHandler(PeerNode.GET_SNAKE, sendSnake);	
		
		HandlerInterface sendApples = new SendApples(snakeView);
		peerNode.addHandler(PeerNode.GET_APPLES, sendApples);	
		
		HandlerInterface sendConfig = new SendConfig(snakeView);
		peerNode.addHandler(PeerNode.GET_CONFIG, sendConfig);	
		
		HandlerInterface blockGame = new BlockGame();
		peerNode.addHandler(PeerNode.BLOCK, blockGame);	
		
		HandlerInterface unblockGame = new UnblockGame();
		peerNode.addHandler(PeerNode.UNBLOCK, unblockGame);	
		
		HandlerInterface newLeader = new NewLeader(peerNode);
		peerNode.addHandler(PeerNode.NEW_LEADER, newLeader);	
		
		/*br = new BufferedReader(new InputStreamReader(System.in));*/
		
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void runPeer() {
		Debug.print("... Starting threads", debug);
		console();
	}
	
	public boolean validateCommand(String[] command) {
		int numArguments;
		String commandName = null;
		
		numArguments = command.length;
		if (numArguments < 1) {
			return false;
		}
		
		commandName = command[0];
		HandlerInterface handler = peerNode.getHandler(commandName);
		if (handler == null) {
			return false;
		}
		
		return true;
	}
	
	public void printMyPeers() {
		Debug.print("... Printing peers", debug);
		for (String currentPeerId : peerNode.getPeerKeys()) {
			PeerInformation currentPeerInformation= peerNode.getPeer(currentPeerId);
			System.out.println("<" + currentPeerInformation.getPeerId() + 
					", " + currentPeerInformation.getHost() + ", " + currentPeerInformation.getPort());
		}
	}
	
	public void printHelp() {
		Debug.print("... Printing help", debug);
		System.out.println("MY_LIST: Print the list of available peers");
		System.out.println("MY_NAME: Print info of this peer");
		System.out.println("BYE: Quit");
	}


	public void moveRight() {
		Debug.print("Moving to the Right...", debug);
		peerNode.broadcastMessage(PeerNode.MOVE_RIGHT, "");
	}

	public void moveUp() {
		Debug.print("Moving Up...", debug);
		peerNode.broadcastMessage(PeerNode.MOVE_UP, "");
	}

	public void moveDown() {
		Debug.print("Moving Down...", debug);
		peerNode.broadcastMessage(PeerNode.MOVE_DOWN, "");
	}

	public void moveLeft() {
		Debug.print("Moving to the Left...", debug);
		peerNode.broadcastMessage(PeerNode.MOVE_LEFT, "");
	}
	public void endGame() {
		Debug.print("Finishing game...", debug);
		peerNode.broadcastMessage(PeerNode.END_GAME, "");
	}

	public void startGame() {
		Debug.print("Starting new game...", debug);
		peerNode.broadcastMessage(PeerNode.START_GAME, "");
	}

    private void initNewGame() {

		
        snakeView.getmSnakeTrail().clear();
        snakeView.getmAppleList().clear();
        
        if (peerNode.getNumberOfPeers() > 0) {
            /*
             * At this point we should already know some peers.
             * Steps to coordinate the game on startup is as follows:
             * 
             * 1) Ask the tracker who the leader is now
             * 2) Block the leader and ask for the current layout
             * 3) Unblock the leader
             */
        	Debug.print("Obtaining game from peer", debug);
        	String myLeader = peerNode.askForLeader();
        	if (myLeader != null && myLeader.equals(peerNode.getPeerId()) == false) {
	        	Layout myLayout = peerNode.askForLayout(myLeader, snakeView.getWidth(), snakeView.getHeight());
	        	for (Coordinate coordinate : myLayout.snake) {
	        		snakeView.getmSnakeTrail().add(coordinate);
	        	}
	        	for (Coordinate coordinate : myLayout.apples) {
	        		snakeView.getmAppleList().add(coordinate);
	        	}

	        	snakeView.setmNextDirection(myLayout.mNextDirection);
	        	snakeView.setmMoveDelay(myLayout.mMoveDelay);
	        	snakeView.setmScore(myLayout.mScore);
        	}
        }
        else {
	        // For now we're just going to load up a short default eastbound snake
	        // that's just turned north
        	Debug.print("Creating new game", debug);
	        snakeView.initSnakeView();
        }
        
        snakeView.printGame();
        startGame();
    }
    
	public void console() {
		String[] commands = null;
		
		Debug.print("... Starting console", debug);
		
		while (shutdown == false) {
			commands = readCommandLine();
			
			if (commands[0].equals("BYE")) {
				Debug.print("... Shutting down threads", debug);
				shutdown = true;
				this.peerNode.setShutdown(shutdown);
				continue;
			}
			
			if (commands[0].equals("MY_LIST")) {
				printMyPeers();
				continue;
			}
			
			if (commands[0].equals("MY_NAME")) {
				System.out.println("I am: " + peerNode.getPeerId() + " ( " + peerNode.getHost() + ", " + peerNode.getPort() + ") ");
				continue;
			}
			
			if (commands[0].equals("MY_LEADER")) {
				System.out.println("My Leader is: " + peerNode.getLeaderId());
				continue;
			}
			
			if (commands[0].equals("MY_GAME")) {
				if (snakeView == null) {
					System.out.println("Game has not started");
				}
				else {
					snakeView.printGame();
				}
				continue;
			}
			
			if (commands[0].equals("HELP")) {
				printHelp();
				continue;
			}
			
			if (commands[0].equals("MOVE_LEFT")) {
				moveLeft();
				continue;
			}
			
			if (commands[0].equals("MOVE_RIGHT")) {
				moveRight();
				continue;
			}
			if (commands[0].equals("MOVE_UP")) {
				moveUp();
				continue;
			}
			if (commands[0].equals("MOVE_DOWN")) {
				moveDown();
				continue;
			}
			if (commands[0].equals("NEW_GAME")) {
				initNewGame();
				continue;
			}
			
			if (validateCommand(commands) == false) {
				System.out.println("Invalid command");
				continue;
			}
		}
	}
	
	public String[] readCommandLine() {
		String[] commands = null;
		
		restoreDisplay();
        try {
			String s = br.readLine();
			commands = s.split(" ");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return commands;
	}
	
	public void restoreDisplay() {
		System.out.print("Type a command: ");
	}
	
	
	/**
	 * This is the main routine. 
	 * @param args
	 * args[0] = myId
	 * args[1] = myPort
	 * 
	 * Args[2] and args[3] are optional
	 * args[2] = Tracker IP
	 * args[3] = Tracker PORT
	 */
	public static void main(String[] args) {
		int port = 8080;
		String myId = null;
		
		String trackerIp = null;
		int trackerPort = -1;
		PeerInformation tracker = null;
		
		
		Debug.print("*** STARTING PEER ****", debug);
		
		if (args.length != 2 && args.length != 4) {
			Debug.print("ERROR: invalid number of parameters: Args Count: " + args.length, debug);
			return;
		}
	
		myId = args[0];
		port = Integer.parseInt(args[1]);
		
		Debug.print("I am: "+ myId, debug);
		
		
		if (args.length == 4) {
			trackerIp = args[2];
			trackerPort = Integer.parseInt(args[3]);
			
			Debug.print("Tracker: (" + trackerIp + ", " + trackerPort + ")", debug);
			tracker = new PeerInformation(null, trackerIp, trackerPort);			
		}
		else {
			Debug.print("...Creating first Node", debug);			
		}
		
		final PeerClient myClient = new PeerClient(myId, port, tracker);
		/*
		new Thread("ConnectionHandler") {

			@Override
			public void run() {
				myClient.peerNode.keepAlive();
			}
			
		}.start();
		 */
		new Thread("ConnectionHandler") {

			@Override
			public void run() {
				myClient.peerNode.connectionHandler();
			}
			
		}.start();
		
		myClient.console();
	}
	
	
}
