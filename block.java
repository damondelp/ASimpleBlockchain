package myPackage;
import java.util.Date;
import java.math.BigInteger;

public class block{
    // Private and Public Variables
    private String prevHash_;
    private Date timestamp_;
    private String hash_;
    String signedData;
    int nonce;
	private String minerID_;
	private double minerReward_;
    private transaction exchange_;

    // Constructor
    block(String prevHash, transaction send){
        this.nonce=-1;
		this.prevHash_=prevHash;
        this.timestamp_=new Date();
        this.minerID_="NA";
        this.minerReward_=0;
        this.exchange_=send;
    }

    // Getters
    public String getPrevHash() {
        return prevHash_;
    }
    public Date getTimestamp() {
        return timestamp_;
    }
    public String getHash() {
        return hash_;
    }
    public String getSignedData() {
        return signedData;
    }
	public String getMinerID() {
		return minerID_;
	}
	public double getMinerReward() {
		return minerReward_;
	}
    public transaction getTransaction() {
        return exchange_;
    }

    // Setters
    public void setHash(String h) {
        this.hash_=h;
    }
    public void setNonce(int n) {
        this.nonce=n;
    }
	public void setSignedData(String s) {
		this.signedData=s;
	}
    public void setMinerID(String minerID) {
        this.minerID_=minerID;
    }
    public void setMinerReward(double minerReward) {
        this.minerReward_=minerReward;
    }

    // Functions
    public String computeHash() {
       return StringUtil.useSHA256(this.getPrevHash()+Long.toString(timestamp_.getTime())+this.getSignedData()+nonce);
    }

    boolean mine(int difficulty, String id, double reward) {
		String target = new String(new char[difficulty]).replace('\0', '0');
        String hash = this.computeHash();
		while(!hash.substring( 0, difficulty).equals(target)){
			       nonce++;
			       hash = this.computeHash();
		}
		System.out.println("Block mined: "+hash);
        this.setHash(hash);
		this.setMinerID(id);
        this.setMinerReward(reward);
        return true;
    }


    	//Decrpyt using the privateKey
    	public void decrpyt(BigInteger privateKey,String hash) {
    		BigInteger dec = new BigInteger(hash.getBytes());
    	    dec = dec.modPow(dec, privateKey);
    	    String n = new String(dec.toString());
    	    setSignedData(n);

    	}
    	//Encrypt the hash using the publicKey and an algorithm
    	public void encrpyt(BigInteger publicKey,String hash) {
    		BigInteger enc = new BigInteger(hash.getBytes());
    		enc = enc.modPow(enc,publicKey);
    		String n = new String(enc.toString());
    		setSignedData(n);

    	}

    @Override
    public String toString() {
        return "Previous Hash: "+prevHash_
            + "\nTimestamp: "+timestamp_.toString()
            + "\nTransaction: "+exchange_.toString();
            + "\nSigned Data: "+signedData
            + "\nNonce: "+Integer.toString(nonce)
            + "\nMiner ID: "+minerID_
            + "\nMiner Reward: "+Double.toString(minerReward_);
    }

    public static void main(String[] args) {
        //
    }
}
