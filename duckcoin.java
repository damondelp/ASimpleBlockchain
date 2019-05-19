package myPackage;
import java.util.Date;
import java.math.BigInteger;





public class duckcoin {

	Vector<String> MainCommands = 
		{"Join the Miners", "Sell Coin", "Send Coin", "Get Info", "Exit"};
	Vector<String> MinerCommands = 
		{"Mine a Block", "Leave the Miners"};
	Vector<String> InfoCommands = 
		{"View Blockchain", "View ID List", "View Balance", "View Transactions", "View Miner Queue Status", "Exit Info"};

	public void setup() {
		// Demo interface
		chain blockchain = new chain();
		user bank = new user("Duckcoin","Bank", "DuckCoin", "bank1234" blockchain);
		user damop = new user("Dimitrios","Damopoulos", "Professor", "DidYouParty?", blockchain);
		user ta1 = new user("TA","one","BestTA", "worstgrader9432", blockchain);
		user ta2 = new user("TA","two", "WorstTA", "cluelessJKlmao", blockchain);
		// Genesis
		block genesis = new block(blockchain.genesisPrevHash, firstTransaction);
		genesis.setNonce(1);
		genesis.setHash(genesis.computeHash());
		blockchain.unminedBlock(genesis);
		// Mine and Send Coin
		bank.joinMinerQueue();
		for (int i=0; i<10; i++){
			bank.mine();
			bank.sendCoin(1,currentIDs.get(1),blockchain);
		}
		for (int j=0; j<10; j++){
			bank.mine();
			bank.sendCoin(1,currentIDs.get(2),blockchain);
		}
		for (int k=0; k<10; k++){
			bank.mine();
			bank.sendCoin(1,currentIDs.get(3),blockchain);
		}
		bank.leaveMinerQueue();
	}


    public static void main(String[] args) {
    	setup();
        System.out.println("Welcome user!");
        Scanner scan = new Scanner(System.in);
        Boolean login = false;
        Boolean userN = false;
        Boolean info = false;
        while (!login) {
	        System.out.println("Enter your username ...");
	        scan.nextLine();
	        String username = scan; 
	        String<User> userList = blockchain.getUserList();
	        User temp;
	        for(int i =0; i<userList.size(); i++){
	        	temp = userList.get(i);
	        	if (username == temp.username){
	        		userN = true;
	        		break;
	        	}
	        }
	        if (userN) {
		        System.out.println("Enter your password ...");
	        	scan.nextLine();
	        	String password = scan;
	        	if (decrypt(temp.kes.getPublicKey(),temp.password) == password) {
	        		login = true;
	        	} else {
	        		System.out.println("Incorrect Password");
	        	}
	        } else {
	        	System.out.println("Incorrect Username");
	        }
	    }
	   	User currentUser = temp;
        System.out.println("");
        while(login) {
        	System.out.println("Which of the following would you like to do? Enter a number or ");
        	for (int i = 0; i<5; i++){
        		System.out.println(Integer.toString(i+1) + ". " + MainCommands[i]);
        	}
	        scan.nextInt();
	        if (scan == 1){
	        		currentUser.joinMinerQueue();
	        		while (currentUser.getQueueStatus()) {
			        	System.out.println("Which of the following would you like to do? Enter a number or ");
		        		for (int i = 0; i<2; i++){
		        			System.out.println(Integer.toString(i+1) + ". " + MinerCommands[i]);
		        		}
		        		scan.nextInt();
		        		if (scan == 1){
		        			currentUser.mine();
		        		} else if (scan == 2){
		        			currentUser.leaveMinerQueue();
		        		} else{
		        			System.out.println("Invalid input ... ");
		        		}
		        	}
	        } else if (scan == 2){
	        	System.out.println("Enter the amount of coin to sell");
	        	scan.nextDouble();
	        	Double sellAmount = scan;
	        	sellCoin(sellAmount, blockchain);
	        } else if (scan == 3){
	        	System.out.println("Enter the amount of coin to send");
	        	scan.nextDouble();
	        	Double sendAmount = scan;
	        	System.out.println("Enter the reciever ID");
	        	scan.nextLine();
	        	String recieverID = scan;
	        	String<User> userList = blockchain.getUserList();
	        	Boolean sell = false;
	        	for(int i =0; i<userList.size(); i++){
	        		temp = userList.get(i);
	        		if (recieverID == temp.getID()){
	        			sell = true;
	        		}
	        	}
	        	if (sell){
	        		sellCoin(sendAmount, recieverID, blockchain);
	        	} else {
	        		System.oout.println("The reciever ID does not exist");
	        	}
	        } else if (scan == 4){
        		info = true;
        		while (info) {
		        	System.out.println("Which of the following would you like to do? Enter a number or ");
	        		for (int i = 0; i<6; i++){
	        			System.out.println(Integer.toString(i+1) + ". " + InfoCommands[i]);
	        		}
	        		scan.nextInt();
	        		if (scan == 1){
	        			blockchain.toString();
	        		} else if (scan == 2){
	        			blockchain.IDList_.toString();
	        		} else if (scan == 3){
	        			Double balance = currentUser.getBalance();
	        			System.out.println(Double.toString(balance));
	        		} else if (scan == 4){
	        			Vectr<transaction> transactions = currentUser.getTransactions();
	        			System.out.println(transactions.toString());
			        } else if (scan == 5){
	        			Boolean status = currentUser.getQueueStatus();
	        			System.out.println(status.toString());			        
	        		} else if (scan == 6){
			        	info = false;
			        } else{
	        			System.out.println("Invalid input ... ");
	        		}
	        	}
	        } else if (scan == 5){
	        	login = false;
	        } else {
	        	System.out.println("Invalid input ... ");
	        }
	    }
	    System.out.println("Bye");

	}