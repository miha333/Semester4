package RB;

/**
 * @author Mihail Weiland (mihail.weiland@haw-hamburg.de) <br>
 * @author Edmund Schauer (edmund.schauer@haw-hamburg.de) <br>
 * 
 * @version <br>
 * 
 *          Praktikum Programmieren 2, SS2014/WI <br>
 *          Praktikumsgruppe 1 <br>
 *          Aufgabenblatt 3, "Parallele Bestellungen" <br>
 *          Verwendete Quellen: Quelle (Begriff: )
 */

/**
 * Klasse Order repräsentiert eine Bestellung. Eine Bestellung hat eine Referenz
 * auf einen Kunden und eine Referenz auf ein Produkt.
 * 
 */
public class Order {
	/* ***** Variablen ***** */
	private Customer kunde;
	private Product ware;

	/**
	 * Default-Konstruktor
	 */

	/*
	 * Die Schreibweise, wie Compiler einen nicht angegebenen Konstruktor
	 * überstetzt.
	 */
	public Order() {
		this.kunde = null;
		this.ware = null;
	}

	/* ***** Getter und Setter ***** */

	/**
	 * @return Kunde
	 */
	public Customer getCustomer() {
		return this.kunde;
	}

	/**
	 * @return Produkt
	 */
	public Product getProduct() {
		return this.ware;
	}

	/**
	 * @param Kunde
	 */
	public void setCustomer(Customer kunde) {
		this.kunde = kunde;
	}

	/**
	 * @param Produkt
	 */
	public void setProduct(Product ware) {
		this.ware = ware;
	}

	/* ***** Standard-Methoden ***** */

	public String toString() {
		return this.getCustomer().toString() + " bestellt " + this.getProduct().toString();
	}
}
