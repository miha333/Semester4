package RB;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Mihail Weiland (mihail.weiland@haw-hamburg.de) <br>
 * @author Edmund Schauer (edmund.schauer@haw-hamburg.de) <br>
 * 
 * @version <br>
 * 
 *          Praktikum Programmieren 2, SS2014/WI <br>
 *          Praktikumsgruppe 1 <br>
 *          Aufgabenblatt 2, "Effiziente Benutzerverwaltung" <br>
 *          Verwendete Quellen: Quelle (Begriff: )
 */

/**
 * Repraesentation eines Produktes im Webshop mit Hilfe von JavaFX-Properties
 * für die Verwendung im Viewmodel. Ein Produkt hat eine Bezeichnung und einen
 * Preis.
 */
public class Product {
	private StringProperty productName;
	private DoubleProperty price;

	public Product(String productName, double price) {
		this.setProductName(productName);
		this.setPrice(price);
	}

	/**
	 * @return productName
	 */
	public StringProperty productNameProperty() {
		if (this.productName == null)
			this.productName = new SimpleStringProperty();
		return this.productName;
	}

	/**
	 * @return price
	 */
	public DoubleProperty priceProperty() {
		if (this.price == null)
			this.price = new SimpleDoubleProperty();
		return this.price;
	}

	/**
	 * @param productName
	 *            das zu setzende Objekt productName
	 */
	public void setProductName(String productName) {
		this.productNameProperty().set(productName);
	}

	/**
	 * @param price
	 *            das zu setzende Objekt price
	 */
	public void setPrice(Double price) {
		this.priceProperty().setValue(price);
	}

	public String getProductName() {
		return this.productNameProperty().get();
	}

	public Double getPrice() {
		return this.priceProperty().get();
	}

	/*
	 * (nicht-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getProductName() + " zum Preis von " + this.getPrice()
				+ " Euro";
	}

}
