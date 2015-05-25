package RB;

import java.util.ArrayList;
import java.util.Observable;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

/**
 * WebShop besteht aus Listen f�r Kunden und Produkten. Er enthaelt Methoden zum
 * Hinzufuegen und Entfernen von Kunden und Produkten. WebShop kann von mehreren
 * "Observer" beobachtet werden. �nderungen an Listen werden den Beobachtern
 * mitgeteilt.
 *
 */
public class WebShop extends Observable {
	/* ***** Variablen ***** */
	private ArrayList<Customer> customerList;
	private ArrayList<Product> productList;
	// Variable f�r die ProgressBar
	private static float progress;
	private Task<Boolean> task;

	/* ***** Konstruktor ***** */
	public WebShop() {
		super();
		customerList = new ArrayList<Customer>();
		productList = new ArrayList<Product>();
		progress = 0f;
	}

	/* ***** Getter ***** */
	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public ArrayList<Product> getProductList() {
		return productList;
	}

	public float getProgress() {
		return progress;
	}
	
	public Task<Boolean> getTask(){
		return this.task;
	}

	/* ***** Hauptmethoden ***** */
	
public void setProgress(Label percent){	
		
		final Label label = percent;
		
		this.task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {
				float numberOfSteps = 20f;

				for (float i = 0f; i < numberOfSteps; i++) {
					Thread.sleep(100);

					final String str = "Fortschritt der Bestellbearbeitung: " + ((i+1f)/2f)*10f + "%";
					final float j = i;
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							progress = j;
							label.setText(str);

						}
					});
				setChanged();
				notifyObservers();							
				}
				return null;
			}
		} ;
		Thread thread = new Thread(this.task);
		thread.start();

	}
	/**
	 * L�schen von Kunden nach vorgegebenem Vornamen und Nachnamen mit der
	 * �nderungsmitteilung an Beobachter.
	 * 
	 * @param firstName
	 * @param surName
	 */
	public void removeCustomer(String firstName, String surName) {
		for (Customer e : customerList) {
			if (e.getFirstName().equals(firstName)
					&& e.getSurName().equals(surName)) {
				customerList.remove(e);
				break;
			}
		}
		/* Anzeigen der Ver�nderung */
		setChanged();
		/* �nderungsmitteilung an Observer */
		notifyObservers();
	}

	/**
	 * L�schen von Produkten nach vorgegebener Produktbezeichnung und dem Preis
	 * mit der �nderungsmitteilung an Beobachter.
	 * 
	 * @param productName
	 * @param price
	 */
	public void removeProduct(String productName, Double price) {
		for (Product e : productList) {
			if (e.getProductName().equals(productName)
					&& e.getPrice().equals(price)) {
				productList.remove(e);
				break;
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Hinzuf�gen von Kunden nach vorgegebenem Vornamen und Nachnamen mit der
	 * �nderungsmitteilung an Beobachter.
	 * 
	 * @param firstName
	 * @param surName
	 */
	public void addCustomerToList(String firstName, String surName) {
		customerList.add(new Customer(firstName, surName));
		setChanged();
		notifyObservers();
		// System.out.println("Customer: " + firstName + " " + surName);
	}

	/**
	 * Hinzuf�gen von Produkten nach vorgegebener Produktbezeichnung und dem
	 * Preis mit der �nderungsmitteilung an Beobachter.
	 * 
	 * @param name
	 * @param price
	 */
	public void addProductToList(String name, Double price) {
		productList.add(new Product(name, price));
		setChanged();
		notifyObservers();
		// System.out.println("Product: " + name + " " + price);
	}

}
