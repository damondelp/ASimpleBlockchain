package myPackage;
import java.util.*;


public class test {

	public static void main(String[] args){
		chain blockchain = new chain();

		// Ten users
		user one = new user("Duckcoin","Bank", blockchain);
		user two = new user("Ali","Brancale", blockchain);
		user three = new user("Hunter","Devlin", blockchain);
		user four = new user("Paul","Chua", blockchain);
		user five = new user("Danny","Collins", blockchain);
		user six = new user("John","Dyer", blockchain);
		user seven = new user("Brandon","Soong", blockchain);
		user eight = new user("Alex","Saltstein", blockchain);
		user nine = new user("Damon","Del Priore", blockchain);
		user ten = new user("Ian","Baker", blockchain);

		// IDs
		Vector<String> currentIDs = blockchain.getIDList();
		String ID;
		String userNum;
		System.out.println("");
		System.out.println("Current ID List after adding 10 users: ");
		System.out.println("ID List Size: " + String.valueOf(blockchain.getIDList().size()));
		for (int i = 0; i < 10; i++) {
			ID = currentIDs.get(i);
			userNum = String.valueOf(i);
			System.out.println("User " + userNum + ": " + ID);
		}
		System.out.println("");
		System.out.println("User 1:");
		System.out.println(one.toString());
		System.out.println("");
		System.out.println("Existing IDs:");
		boolean IDtest1 = blockchain.existsID(one.getID());
		boolean IDtest2 = blockchain.existsID("not an id");
		System.out.println("Real ID: " + Boolean.toString(IDtest1));
		System.out.println("Fake ID: " + Boolean.toString(IDtest2));
		System.out.println("");

		// Blocks
		int chainsize;
		transaction firstTransaction = new transaction("First Transaction", 0, "Genesis Block");
		block genesis = new block(blockchain.genesisPrevHash, firstTransaction);
		genesis.setNonce(1);
		genesis.setHash(genesis.computeHash());
		chainsize = blockchain.getChain().size();
		System.out.println("Blockchain Size before genesis: " + String.valueOf(chainsize));		
		System.out.println("");
		System.out.println("Genesis Block Before Mine:");
		System.out.println(genesis.toString());
		System.out.println("");

		// Valid Chain
		boolean validTest = blockchain.isChainValid();
		System.out.println("Chain validity: " + Boolean.toString(validTest));

		// Rewarding the first miner
		three.joinMinerQueue(blockchain);
		blockchain.unminedBlock(genesis);
		System.out.println("User 3 mines a block ... ");
		three.mine(blockchain);
		chainsize = blockchain.getChain().size();
		System.out.println("");
		System.out.println("Genesis Block After Mine:");
		System.out.println(genesis.toString());
		System.out.println("");
		System.out.println("Blockchain Size after genesis: " + String.valueOf(chainsize));	

		// Valid Chain
		validTest = blockchain.isChainValid();
		System.out.println("Chain validity: " + Boolean.toString(validTest));

		// Sending Coin
		three.sendCoin(1,currentIDs.get(0),blockchain);
		three.mine(blockchain);
		three.sendCoin(1,currentIDs.get(1),blockchain);
		three.mine(blockchain);
		System.out.println("User 3:");
		System.out.println(three.toString());
		System.out.println("");
		three.leaveMinerQueue(blockchain);
		System.out.println("");
		System.out.println("Miner Leaves Queue");
		System.out.println("Miner Queue Size: " + String.valueOf(blockchain.getMinerList().size()));
		System.out.println("To Be Mined Size: " + String.valueOf(blockchain.getToBeMined().size()));
		chainsize = blockchain.getChain().size();
		System.out.println("Blockchain after mining: " + String.valueOf(chainsize));	
		System.out.println("");

		// Blocks 
		System.out.println("Block0:" + blockchain.getChain().get(0).toString());
		System.out.println("Genesis Hash: " + genesis.getHash());
		System.out.println("");
		System.out.println("Block1:" + blockchain.getChain().get(1).toString());
		System.out.println("Block Hash: " + blockchain.getChain().get(1).getHash());
		System.out.println("");
		System.out.println("Block2:" + blockchain.getChain().get(2).toString());
		System.out.println("Block Hash: " + blockchain.getChain().get(2).getHash());
		System.out.println("");

		// User Balances
		one.setBalance(blockchain);
		two.setBalance(blockchain);
		three.setBalance(blockchain);
		System.out.println("");
		System.out.println("User 1 Balance: " + String.valueOf(one.getBalance()));
		System.out.println("User 2 Balance: " + String.valueOf(two.getBalance()));
		System.out.println("User 3 Balance: " + String.valueOf(three.getBalance()));
		System.out.println("");

		// Valid Chain
		validTest = blockchain.isChainValid();
		System.out.println("Chain validity: " + Boolean.toString(validTest));

		
		// Loop of transactions
		System.out.println("10 transactions from the miner");
		three.joinMinerQueue(blockchain);
		for(int k = 0; k < 10; k ++){
			if(k!=2){
				three.sendCoin(1,currentIDs.get(k),blockchain);
				three.mine(blockchain);
			}
		}
		System.out.println("");
		three.leaveMinerQueue(blockchain);		

		one.setBalance(blockchain);
		two.setBalance(blockchain);
		three.setBalance(blockchain);
		four.setBalance(blockchain);
		five.setBalance(blockchain);
		six.setBalance(blockchain);
		seven.setBalance(blockchain);
		eight.setBalance(blockchain);
		nine.setBalance(blockchain);
		ten.setBalance(blockchain);
		System.out.println("");
		System.out.println("User 1 Balance: " + String.valueOf(one.getBalance()));
		System.out.println("User 2 Balance: " + String.valueOf(two.getBalance()));
		System.out.println("User 3 Balance: " + String.valueOf(three.getBalance()));
		System.out.println("User 4 Balance: " + String.valueOf(four.getBalance()));
		System.out.println("User 5 Balance: " + String.valueOf(five.getBalance()));
		System.out.println("User 6 Balance: " + String.valueOf(six.getBalance()));
		System.out.println("User 7 Balance: " + String.valueOf(seven.getBalance()));
		System.out.println("User 8 Balance: " + String.valueOf(eight.getBalance()));
		System.out.println("User 9 Balance: " + String.valueOf(nine.getBalance()));
		System.out.println("User 10 Balance: " + String.valueOf(ten.getBalance()));

		System.out.println("");

		validTest = blockchain.isChainValid();
		System.out.println("Chain validity: " + Boolean.toString(validTest));
		
		System.out.println(blockchain);


	}

}