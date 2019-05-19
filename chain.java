package myPackage;
import java.util.*;


public class chain {
	// Private variables
	private Vecctr<User> userList_;
	private Vector<block> blockchain_;
	private Vector<block> toBeMined_;
	private Vector<String> IDList_;
	private Vector<String> miners_;
	private final double price = 1.0;
	public final int difficulty=4;
	public final double mineReward=1.0;
	public final String genesisPrevHash =
		"0000000000000000000000000000000000000000000000000000000000000000";

	// Constructor
	public chain() {
		userList_=createNewUserList();
		blockchain_= createNewChain();
		toBeMined_= createNewToBeMined();
		IDList_= createNewIDList();
		miners_= createNewMinerList();

	}

	// Getters
	public Vector<block> getChain() {
		return blockchain_;
	}
	public Vector<block> getToBeMined() {
		return toBeMined_;
	}
	public Vector<String> getIDList() {
		return IDList_;
	}
	public Vector<String> getMinerList() {
		return miners_;
	}	
	public Vector<User> getUserList() {
		return userList_;
	}

	// Setters
	public void setUserList(Vector<User> userList) {
		this.userList_=userList;
	}
	public void setChain(Vector<block> blockchain) {
		this.blockchain_=blockchain;
	}
	public void setIDList(Vector<String> IDList) {
		this.IDList_=IDList;
	}
	public void setToBeMined(Vector<block> unmined) {
		this.toBeMined_=unmined;
	}

	// Functions
	public Vector<block> createNewChain() {
		// Create the new blockchain vector as called in the constructor
		return new Vector<block>(0);
	}
	public Vector<String> createNewIDList() {
		// Create the new ID List vector as called in the constructor
		return new Vector<String>(0);
	}
	public Vector<User> createNewUserList() {
		// Create the new User List vector as called in the constructor
		return new Vector<User>(0);
	}
	public Vector<block> createNewToBeMined() {
		// Create the new Too Be Added vector as called in the constructor
		return new Vector<block>(0);
	}
	public Vector<String> createNewMinerList() {
		// Create the new Miner Queue as called in the constructor
		return new Vector<String>(0);
	}
	public void addToIDList(String newID) {
		// Add User to User List
		this.IDList_.add(newID);
	}
	public void addToUserList(User newUser) {
		// Add User to User List
		this.userList_.add(newUser);
	}
	public void addToMinerList(String minerID) {
		// Add ID to miners
		this.miners_.add(minerID);
	}
	public void removeFromMinerList(String minerID) {
		// Remove ID from miners
		this.miners_.remove(minerID);
	}


	// Chain Validity
	public Boolean isChainValid() {
		// Compares consecutive hashes to check for a valid chain
		block current;
		block previous;
		if(blockchain_.size()==0){
			return true;
		} else if(blockchain_.size() == 1){
			current=blockchain_.get(0);
			if(!current.getHash().equals(current.computeHash())){
				System.out.println("The current block hash is invalid for genesis block");
				return false;
			}
			if(!genesisPrevHash.equals(current.getPrevHash())){
				System.out.println("The previous block hash is invalid for genesis block");
				return false;
			}
		} else{
			previous=blockchain_.get(0);
			for(int i=1; i<blockchain_.size(); i++){
				current = blockchain_.get(i);
				if(!current.getHash().equals(current.computeHash())){
					System.out.println("The current block hash is invalid for block "+i);
					return false;
				}
				if(!previous.getHash().equals(current.getPrevHash())){
					System.out.println("The previous block hash is invalid for block "+i);
					return false;
				}
				previous=current;
			}
		}
		return true;
	}

	// Existing ID
	public boolean existsID(String ID) {
		// Checks the ID List for if the ID exists
		Vector<String> list=this.getIDList();
		int j=list.size()-1;
		for(int i=0; i<=j; i++){
			if(list.get(i)==ID || list.get(j)==ID){
				return true;
			}
			j--;
		}
		return false;
	}

	// Blocks
	public boolean unminedBlock(block b) {
		// Allows a block to be eventually mined if there are miners
		Vector<String> empty=new Vector<String>(0);
		if (miners_==empty){
			return false;
		} else {
			toBeMined_.add(b);
			return true;
		}
	}

	public boolean addBlock(block b) {
		// Mined Blocks enter the chain if they are valid
		Vector<block> temp1=getChain();
		Vector<block> temp2=getChain();
		temp2.add(b);
		this.setChain(temp2);
		if (this.isChainValid()){
			return true;
		} else {
			this.setChain(temp1);
			return false;
		}
	}

	@Override
	public String toString(){
		return "Blockchain: "+blockchain_.toString()
		+ "\nTo Be Mined List: "+toBeMined_.toString()
		+ "\nID List: "+IDList_.toString()
		+ "\nMiner List: "+miners_.toString();
	}

	public static void main(String[] args) {

	}

}
