package RB;

/**
 * @author Mihail Weiland (mihail.weiland@haw-hamburg.de) <br>
 * @author Edmund Schauer (edmund.schauer@haw-hamburg.de) <br>
 * 
 * @version 1.0<br>
 * 
 *          Praktikum Rechnernetze und Betriebssysteme, SS2015WI <br>
 *          Praktikumsgruppe 4 <br>
 *          Aufgabe 2 - "Fastfood"<br>
 *          Verwendete Quellen: Skript, 
 *          
 * @description Die Klasse Kunde haelt Kundeneigenschaften fest.
 */
public class Kunde {
	private int menge; // die Bestellmenge (1-8) wird zufaellig bei Erstellung vergeben
	private int kdNr; // die Kundennummer wird bei Erstellung inkrementiert
	private boolean bestellt; // flag
	private boolean bezahlt; // flag
	private boolean bekommen; // flag
	private long bestellZeit; // um Prioritaet in der Warteschlange zu ermitteln
	
	/**
	 *  Konstruktor Die ersten Initialisierungen.
	 */
	public Kunde(){
		this.menge = 0;
		this.kdNr = 0;
		this.bestellt = false;
		this.bezahlt = false;
		this.bekommen = false;
		this.bestellZeit = 0;
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

	/**
	 * @return the bestellt
	 */
	public boolean isBestellt() {
		return bestellt;
	}

	/**
	 * @param bestellt the bestellt to set
	 */
	public void setBestellt(boolean bestellt) {
		this.bestellt = bestellt;
	}

	/**
	 * @return the bezahlt
	 */
	public boolean isBezahlt() {
		return bezahlt;
	}

	/**
	 * @param bezahlt the bezahlt to set
	 */
	public void setBezahlt(boolean bezahlt) {
		this.bezahlt = bezahlt;
	}

	/**
	 * @return the bekommen
	 */
	public boolean isBekommen() {
		return bekommen;
	}

	/**
	 * @param bekommen the bekommen to set
	 */
	public void setBekommen(boolean bekommen) {
		this.bekommen = bekommen;
	}

	/**
	 * @return the bestellZeit
	 */
	public long getBestellZeit() {
		return bestellZeit;
	}

	/**
	 * @param bestellZeit the bestellZeit to set
	 */
	public void setBestellZeit(long bestellZeit) {
		this.bestellZeit = bestellZeit;
	}

}
