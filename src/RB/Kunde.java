package RB;

public class Kunde {
	private int menge;
	private int kdNr;
	
	/**
	 * Default Konstruktor
	 */
	public Kunde(){
		menge = 0;
		kdNr = 0;
		
	}

	/**
	 * @return the kdNr
	 */
	public int getKdNr() {
		
		return kdNr;
	}

	/**
	 * @return the menge
	 */
	public int getMenge() {
		return menge;
	}
	
	public void setKdNr(int kdNr){
		this.kdNr = kdNr;
	}

	/**
	 * @param menge the menge to set
	 */
	public void setMenge(int menge) {
		this.menge = menge;
	}

}
