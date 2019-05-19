package myPackage;
import java.util.*;
import java.math.BigInteger;

public class user {
	// Private and Public Variables
	private double balance_;
	private Vector<transaction> transactionList_;
	private Boolean queueStatus_;
	public String ID;
	public String username;
	String password;
	String first;
	String last;
	RSA keys;


	// Constructor
	user(String firstN, String lastN, String username, String pswrd, chain blockchain){
		this.balance_=0;
		this.transactionList_= createNewTransactionList();
		this.queueStatus_=false;
		this.first=firstN;
		this.last=lastN;
		this.keys=new RSA(64);
		this.username=username;
		this.password=encrypt(keys.getPublicKey(),pswrd);
		this.ID=computeNewID(firstN, lastN, blockchain);
		blockhain.addToUserList(this);
	}


	// Getters
	public double getBalance() {
		return balance_;
	}
	public String getID() {
		return ID;
	}
	public boolean getQueueStatus() {
		return queueStatus_;
	}
	public Vector getTransactions() {
		return transactionList_;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}

	// Setters
	public void setBalance(chain blockchain) {
		this.balance_=this.computeBalance(blockchain);
	}
	public void setQueueStatus(boolean b) {
		this.queueStatus_=b;
	}
	public void getUsername(){
		return username;
	}
	public void setPassword(String s) {
		this.password = s;
	}


	// Transaction List
	public Vector<transaction> createNewTransactionList() {
		return new Vector<transaction>(0);
	}
	public void setTransactionList(chain blockchain) {
		this.transactionList_ = updateTransactionList(blockchain, this.getID());
	}
	public Vector<transaction> updateTransactionList(chain blockchain, String ID) {
		Vector<transaction> updatingTransactions = new Vector<transaction>(0);
		for (int i=0; i<blockchain.getChain().size(); i++){
			block exchange = blockchain.getChain().get(i);
			if (exchange.getTransaction().getSenderID().equals(ID) || exchange.getTransaction().getRecieverID().equals(ID)){
				updatingTransactions.add(exchange.getTransaction());
			}
		}
		return updatingTransactions;
	}

	// IDS
	public String computeNewID(String first, String last, chain blockchain) {
		Random r=new Random();
		int ID_tag=r.nextInt(900000000)+100000000;
		String newID=first+last+String.valueOf(ID_tag);
		while (blockchain.existsID(newID)){ //possibly make exists_ID take in the ID List
			ID_tag=r.nextInt(900000000)+100000000;
			newID=first+last+String.valueOf(ID_tag);
		}
		blockchain.addToIDList(newID);
		return newID;
	}

	// Balances
	public double computeBalance(chain blockchain) {
		double coin;
		double currentBalance=0.0;
		block temp;
		for (int i=0; i<blockchain.getChain().size(); i++){
			temp=blockchain.getChain().get(i);
			coin=(temp.getTransaction().getAmount());
			if (temp.getTransaction().getSenderID().equals(this.ID)){
				currentBalance=currentBalance-coin;
			}
			if (temp.getTransaction().getRecieverID().equals(this.ID)){
				currentBalance=currentBalance+coin;
			}
			if (temp.getMinerID().equals(this.ID)){
				currentBalance=currentBalance+temp.getMinerReward();
			}
		}
		return currentBalance;
	}

	public double IDBalance(chain blockchain, String id) {
		double coin;
		double currentBalance = 0.0;
		block temp;
		for (int i=0; i<blockchain.getChain().size(); i++){
			temp=blockchain.getChain().get(i);
			coin=(temp.getTransaction().getAmount());
			if (temp.getTransaction().getSenderID().equals(id)){
				currentBalance=currentBalance-coin;
			}
			if (temp.getTransaction().getRecieverID().equals(id)){
				currentBalance=currentBalance+coin;
			}
			if (temp.getMinerID().equals(id)){
				currentBalance=currentBalance+temp.getMinerReward();
			}
		}
		return currentBalance;
	}

	boolean moneyValid(transaction newTransaction, chain blockchain) {
		return IDBalance(blockchain, newTransaction.getSenderID()) >= newTransaction.getAmount();
  	}

  	// Sending Coin
    void sendCoin(double sending, String recieverID, chain blockchain) {
    	transaction newTransaction=new transaction(this.getID(), sending, recieverID);
    	if (!moneyValid(newTransaction, blockchain)){
    		System.out.println("Error: The user does not have enough coin to send");
    	} else{
    		Vector<block> chain = blockchain.getChain();
    		block newBlock=new block((chain.get(chain.size()-1).getHash()), newTransaction);
    		newBlock.setSignedData(computeSignedData(newBlock));
    		String s=newBlock.getSignedData();
    		//Encrypt the signed data before you send it over
    		BigInteger a=new BigInteger(newBlock.signedData);
    		newBlock.setSignedData(keys.encrypt(a).toString());
    		//Verify the signature (Maybe should be put to a later point in the transaction?)
    		if (verify(newBlock.getSignedData(),s,this.keys.getPublicKey(),this.keys.getModulus()) == false){
    			System.out.println("Error with verification process, decrypted message does not match original");
    		}
    		if (blockchain.unminedBlock(newBlock)){
    			System.out.println("The transaction is being processed");
    		} else{
    			System.out.println("The miner queue is empty");
    		}
    	}
    }

    // Selling Coin
    void sellCoin(double selling, chain blockchain) {
    	transaction newTransaction=new transaction(this.getID(), selling);
    	if (!moneyValid(newTransaction, blockchain)){
    		System.out.println("Error: The user does not have enough coin to sell");
    	} else{
    		Vector<block> chain = blockchain.getChain();
    		block newBlock=new block((chain.get(chain.size()-1).getHash()), newTransaction);
    		newBlock.setSignedData(computeSignedData(newBlock));
    		String s=newBlock.getSignedData();
    		//Encrypt the signed data before you send it over
    		BigInteger a=new BigInteger(newBlock.signedData);
    		newBlock.setSignedData(keys.encrypt(a).toString());
    		//Verify the signature (Maybe should be put to a later point in the transaction?)
    		if (verify(newBlock.getSignedData(),s,this.keys.getPublicKey(),this.keys.getModulus()) == false){
    			System.out.println("Error with verification process, decrypted message does not match original");
    		}
    		if (blockchain.unminedBlock(newBlock)){
    			System.out.println("The transaction is being processed");
    		} else{
    			System.out.println("The miner queue is empty");
    		}
    	}
    }

    // Encryption
	String computeSignedData(block temp){
		String concat=temp.getPrevHash()+Long.toString(temp.getTimestamp().getTime())+temp.getTransaction().getSenderID()+Double.toString(temp.getTransaction().getAmount())+temp.getTransaction().getRecieverID();
		byte[] b=concat.getBytes();
		BigInteger bigint=new BigInteger(b);
		return keys.decrypt(bigint).toString();
	}

	static boolean verify(String message,String original,BigInteger publicKey,BigInteger mod) {
		BigInteger s=new BigInteger(original);
		RSA check=new RSA(publicKey,mod);
		BigInteger o=new BigInteger(message);
		return (o.compareTo(check.decrypt(s)) == 0);
	}

    	//Decrpyt using the privateKey
	public void decrpyt(BigInteger privateKey,String pswrd) {
		BigInteger dec = new BigInteger(pswrd.getBytes());
	    dec = dec.modPow(dec, privateKey);
	    String n = new String(dec.toString());
	    setSignedData(n);

	}
	//Encrypt the hash using the publicKey and an algorithm
	public void encrpyt(BigInteger publicKey,String pswrd) {
		BigInteger enc = new BigInteger(hash.getBytes());
		enc = enc.modPow(enc,publicKey);
		String n = new String(enc.toString());
		setSignedData(n);

	}



	// Mining
    public void mine(chain blockchain) {
		if(this.getQueueStatus()){
			Vector<block> temp=blockchain.getToBeMined();
			if (temp.size()==0){
				System.out.println("There are no blocks to be mined.");
			} else{
				block newBlock=temp.get(0);
				double reward=1;
				if (newBlock.mine(blockchain.difficulty, this.getID(), reward)){
					temp.remove(0);
					blockchain.setToBeMined(temp);
					blockchain.addBlock(newBlock);
					System.out.println("You have mined a block.");
				}
			}
		} else{
			System.out.println("You are not in the miner list. Join it to mine blocks.");
		}
	}

  public void joinMinerQueue(chain blockchain){
    	boolean queueStatus=this.getQueueStatus();
    	if (!queueStatus){
    		this.setQueueStatus(!queueStatus);
    		blockchain.addToMinerList(this.ID);
    		System.out.println("You are now in the miner list.");

    	} else{
    		System.out.println("You were already in the miner list.");
    	}
    }
    void leaveMinerQueue(chain blockchain){
    	boolean queueStatus=this.getQueueStatus();
    	if (queueStatus){
    		this.setQueueStatus(!queueStatus);
    		blockchain.removeFromMinerList(this.ID);
    		System.out.println("You have been removed from the miner list.");
    	} else{
    		System.out.println("You were not in the miner list.");
    	}
    }

    @Override
    public String toString() {
		return "First: "+first
				+ "\nLast: "+last
				+ "\nBalance: "+String.valueOf(balance_)
				+ "\nID: "+ID
				+ "\nQueue Status: "+Boolean.toString(queueStatus_);
	}

	public static void main(String[] args){
		//
    }
}
