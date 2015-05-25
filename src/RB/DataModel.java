package RB;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Mihail Weiland (mihail.weiland@haw-hamburg.de) <br>
 * @author Edmund Schauer (edmund.schauer@haw-hamburg.de) <br>
 * 
 * @version <br>
 * 
 *          Praktikum Programmieren 2, SS2014/WI <br>
 *          Praktikumsgruppe 1 <br>
 *          Aufgabenblatt , "" <br>
 *          Verwendete Quellen: Quelle (Begriff: )
 */

/**
 * DataModel bereitet Daten für die grafische Darstellung auf. Es beinhaltet
 * Listen für Kunden und Produkte. DataModel reagiert auf Änderungen eines
 * Oservables(in unserem Fall WebShop).
 *
 */
public class DataModel implements Observer {
	/* ***** Variablen ***** */
	private ObservableList<Customer> customer = FXCollections
			.<Customer> observableArrayList();
	private ObservableList<Product> product = FXCollections
			.<Product> observableArrayList();
	private SimpleFloatProperty progress;

	/* ***** Getter und Setter ***** */

	/**
	 * Create and return a list of Customers in a ObservableList.
	 */
	public ObservableList<Customer> getCustomerList() {
		return customer;
	}

	/**
	 * Create and return a list of Products in a ObservableList.
	 */
	public ObservableList<Product> getProductList() {
		return product;
	}

	public void setCustomerList(ArrayList<Customer> customerList) {
		customer = FXCollections.<Customer> observableArrayList(customerList);
	}

	public void setProductList(ArrayList<Product> productList) {
		product = FXCollections.<Product> observableArrayList(productList);
	}

//	public void setCustomer(Customer kunde) {
//		customer.add(kunde);
//	}
//
//	public void setProduct(Product ware) {
//		product.add(ware);
//	}
//
	public void setProgress(float fortschritt) {	
		progressProperty().set((fortschritt+2f)/20f);
//		System.out.println("Model: Progress " + ((fortschritt+2f)/20f));
	}

	public  SimpleFloatProperty getProgress() {
		return progress;
	}
	
	public SimpleFloatProperty progressProperty(){
		if(progress == null)
			progress = new SimpleFloatProperty();
		return progress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		/* Referenz auf Observable-Objekt */
		WebShop shop = (WebShop) o;
		
		customer.clear();
		customer.addAll(shop.getCustomerList());
//		this.setCustomerList(shop.getCustomerList()); // hier wird ein NEUES Objekt der Liste erzeugt
		product.clear();
		product.addAll(shop.getProductList());
//		this.setProductList(shop.getProductList());
		this.setProgress(shop.getProgress());
		// System.out.println("Model: neue Liste " +
		// getCustomerList().toString());

	}
}
