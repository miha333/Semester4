package RB;

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
 * Repraesentation eines Kunden im Webshop mit Hilfe von JavaFX-Properties für
 * die Verwendung im Viewmodel. Ein Kunde hat einen Vornamen, einen Nachnamen
 * und eine eindeutige ID.
 */
public class Customer {
	/* ***** Variablen ***** */
	/* Klasse StringProperty definiert Eigenschaften einer Variable */
	private StringProperty firstName;
	private StringProperty surName;
	/* eindeutige ID */
	private final int id;
	private static int counter = 0;

	/* ***** Konstruktor ***** */
	/**
	 * Custom constructor
	 * 
	 * @param Vorname
	 * @param Nachname
	 */
	public Customer(String firstName, String surName) {
		this.setFirstName(firstName);
		this.setSurName(surName);
		this.id = counter++;
	}

	/* **** Setter **** */
	/**
	 * @param surName
	 */
	public void setSurName(String surName) {

		this.surNameProperty().set(surName);
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {

		this.firstNameProperty().set(firstName);
	}

	/* **** Getter **** */

	/**
	 * @return Vorname
	 */
	public String getFirstName() {
		return this.firstNameProperty().get();
	}

	/**
	 * @return Nachname
	 */
	public String getSurName() {
		return this.surNameProperty().get();
	}

	/* **** Instanz von StringProperty **** */

	public StringProperty firstNameProperty() {
		if (this.firstName == null)
			this.firstName = new SimpleStringProperty();
		return this.firstName;
	}

	public StringProperty surNameProperty() {
		if (this.surName == null)
			this.surName = new SimpleStringProperty();
		return this.surName;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return this.id;
	}

	/*
	 * (nicht-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getFirstName() + " " + this.getSurName();
	}
}
