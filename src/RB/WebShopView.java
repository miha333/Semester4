package RB;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Mihail Weiland (mihail.weiland@haw-hamburg.de) <br>
 * @author Edmund Schauer (edmund.schauer@haw-hamburg.de) <br>
 * 
 * @version <br>
 * 
 *          Praktikum RB, SS2015/WI <br>
 *          Praktikumsgruppe 1 <br>
 *          Aufgabenblatt , "" <br>
 *          Verwendete Quellen: Quelle (Begriff: )
 */


public class WebShopView extends Application {

	public static WebShop webshop = new WebShop();
	public static DataModel model = new DataModel();
	public static final ProgressBar progressBar = new ProgressBar();

	public static void main(String[] args) {
		
		/* Registrierung von Observer "model" bei Observable "webshop" */
		webshop.addObserver(model);

		webshop.addCustomerToList("Jogi", "Löw");
		webshop.addCustomerToList("Uli", "Hoeneß");
		webshop.addCustomerToList("Hansi", "Flick");
		webshop.addCustomerToList("Dieter", "Hoeneß");
		webshop.addCustomerToList("Mehmet", "Scholl");
		webshop.addCustomerToList("Marco", "Reus");
		webshop.addCustomerToList("Michael", "Ballack");
		webshop.addCustomerToList("Mihail", "Weiland");
		webshop.addCustomerToList("Edmund", "Schauer");
		webshop.addCustomerToList("Philipp", "Jenke");

		webshop.addProductToList("Handy", 99.99);
		webshop.addProductToList("Kugelschreiber", 4.99);
		webshop.addProductToList("Laptop", 1234.99);
		webshop.addProductToList("Camera", 499.99);
		webshop.addProductToList("Auto", 35000.0);
		webshop.addProductToList("iPad", 399.99);
		webshop.addProductToList("Radiergummi", 0.99);
		webshop.addProductToList("Uhr", 34.99);
		webshop.addProductToList("Telefon", 49.99);
		webshop.addProductToList("Elefant", 13000.0);

		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		// System.out.println("geht");

		// Setup the stage with the scene
		primaryStage.setTitle("Tables");
		StackPane root = new StackPane(); // Kann auch Group oder VBox sein

		// Use a grid pane for organization
		final GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(10);
		gridpane.setVgap(10);

		/* gridpane als erstes Kind des Wurzelknoten */
		root.getChildren().add(gridpane);
		/*******************************************/

		// Title Customers
		Label titel1 = new Label("Customers");
		gridpane.add(titel1, 2, 0);
		GridPane.setHalignment(titel1, HPos.LEFT);

		// Create the table Customers
		final TableView<Customer> tableCustomers = new TableView<>();
		tableCustomers.setPrefWidth(350);
		tableCustomers.setPrefHeight(300);

		this.FillCustomerTable(tableCustomers);

		gridpane.add(tableCustomers, 2, 1);

		/**************************************************/

		// Title Products
		Label titel2 = new Label("Products");

		gridpane.add(titel2, 4, 0);
		GridPane.setHalignment(titel2, HPos.LEFT);
		// Create the table Products
		final TableView<Product> tableProducts = new TableView<>();
		tableProducts.setPrefWidth(350);
		tableProducts.setPrefHeight(300);
		FillProductTable(tableProducts);
		gridpane.add(tableProducts, 4, 1);

		/******************************************************/

		// Button: Add customer
		/**
		 * Mit einem Klick auf den Button wird im neuen Fenster ein neuer Kunde
		 * hinzugefügt.
		 */
		Button buttonAddCustomer = new Button("Kunden hinzufügen");
		buttonAddCustomer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Neues Dialog-Fenster für das Hinzufügen von Kunden */
				final Stage dialog = new Stage();
				dialog.setTitle("Neuer Kunde");
				GridPane grid = new GridPane();
				grid.setPadding(new Insets(5));
				grid.setHgap(10);
				grid.setVgap(10);
				Group wurzel = new Group();
				wurzel.getChildren().add(grid);

				Label firstNameLabel = new Label("Vorname:");
				final TextField firstNameTextField = new TextField();
				Label surNameLabel = new Label("Nachname:");
				final TextField surNameTextField = new TextField();

				grid.add(firstNameLabel, 1, 1);
				grid.add(firstNameTextField, 1, 2);
				grid.add(surNameLabel, 2, 1);
				grid.add(surNameTextField, 2, 2);

				/* Rückmeldung an Benutzer in Form eines Textes */
				final Text dialog_meldung = new Text();

				// Button: Speichern
				Button submit = new Button("Speichern");
				submit.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String firstName = firstNameTextField.getText().trim();
						String surName = surNameTextField.getText().trim();
						if (!firstName.isEmpty() && !surName.isEmpty()
						        && firstName.matches("[A-Za-zöäüßÖÄÜ-]+")
						        && surName.matches("[A-Za-zöäüßÖÄÜ-]+")) {
							webshop.addCustomerToList(firstNameTextField.getText().trim(), surNameTextField.getText().trim());
							firstNameTextField.clear();
							surNameTextField.clear();
//							tableCustomers.getColumns().clear();
							/* Hilfsmethode zum befüllen der Tabelle */
//							FillCustomerTable(tableCustomers);
							dialog.close();
						} else {
							dialog_meldung
							        .setText("Füllen Sie bitte\nalle Felder aus!");

						}
					}

				});

				grid.add(dialog_meldung, 1, 4);
				grid.add(submit, 1, 3);

				Button cancel = new Button("Abbrechen");
				cancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dialog.close();
					}

				});

				grid.add(cancel, 2, 3);

				Scene dialog_scene = new Scene(wurzel, 400, 300, Color.WHITE);
				dialog.setScene(dialog_scene);
				dialog.show();

			}
		});

		gridpane.add(buttonAddCustomer, 2, 2);

		/******************************************************/

		// Button: Add product
		Button buttonAddProduct = new Button("Produkt hinzufügen");
		buttonAddProduct.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				final Stage dialog2 = new Stage();
				dialog2.setTitle("Neues Produkt");
				GridPane grid2 = new GridPane();
				grid2.setPadding(new Insets(5));
				grid2.setHgap(10);
				grid2.setVgap(10);
				Group wurzel2 = new Group();
				wurzel2.getChildren().add(grid2);

				Label productNameLabel = new Label("Bezeichnung:");
				final TextField productNameTextField = new TextField();
				Label priceLabel = new Label("Preis:");
				final TextField priceTextField = new TextField();

				grid2.add(productNameLabel, 1, 1);
				grid2.add(productNameTextField, 1, 2);
				grid2.add(priceLabel, 2, 1);
				grid2.add(priceTextField, 2, 2);

				final Text dialog_meldung2 = new Text();

				Button submit2 = new Button("Speichern");
				submit2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String productName = productNameTextField.getText()
						        .trim();
						String price = priceTextField.getText().trim();
						dialog_meldung2.setText("");
						if (!productName.isEmpty()
						        && !price.isEmpty()
						        && productName
						                .matches("[A-Za-zöäüßÖÄÜ0-9-_\":;.,\\s]+")
						        && price.matches("\\d+[.]?\\d{0,2}")) {
							webshop.addProductToList(productName,
							        Double.parseDouble(price));
							productNameTextField.clear();
							priceTextField.clear();
							tableProducts.getColumns().clear();
							/* Hilfsmethode zum befüllen der Tabelle */
							FillProductTable(tableProducts);
							dialog2.close();
						} else {

							dialog_meldung2
							        .setText("Füllen Sie bitte\nalle Felder aus!");

						}
					}

				});
				grid2.add(dialog_meldung2, 1, 4);
				grid2.add(submit2, 1, 3);

				Button cancel2 = new Button("Abbrechen");
				cancel2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dialog2.close();
					}

				});

				grid2.add(cancel2, 2, 3);

				Scene dialog_scene2 = new Scene(wurzel2, 400, 300, Color.WHITE);
				dialog2.setScene(dialog_scene2);
				dialog2.show();

			}
		});

		gridpane.add(buttonAddProduct, 4, 2);

		/********************************************************/

		final Text meldung_links = new Text();

		// Button: Remove selected customer
		Button buttonRemoveCustomer = new Button("Kunden löschen");
		buttonRemoveCustomer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Customer e = (Customer) tableCustomers.getSelectionModel()
				        .getSelectedItem();
				if (e != null) {
					webshop.removeCustomer(e.getFirstName(), e.getSurName());
					meldung_links.setText("");
					String text = e.getFirstName() + " " + e.getSurName()
					        + " wurde gelöscht";
					tableCustomers.getColumns().clear();
					/* Hilfsmethode zum befüllen der Tabelle */
					FillCustomerTable(tableCustomers);
					meldung_links.setText(text);
				} else
					meldung_links
					        .setText("Wählen Sie einen Kunden zum Löschen\ndurch einen Mausklick");

			}
		});

		tableCustomers.getSelectionModel().clearSelection();
		gridpane.add(meldung_links, 2, 5);
		gridpane.add(buttonRemoveCustomer, 2, 3);

		/********************************************************/

		final Text meldung_rechts = new Text();
		// Button: Remove selected customer
		Button buttonRemoveProduct = new Button("Produkt löschen");
		buttonRemoveProduct.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Product e = (Product) tableProducts.getSelectionModel()
				        .getSelectedItem();
				if (e != null) {
					webshop.removeProduct(e.getProductName(), e.getPrice());
					meldung_rechts.setText("");
					String text = e.getProductName() + " für " + e.getPrice()
					        + " Euro wurde gelöscht";
					tableProducts.getColumns().clear();
					/* Hilfsmethode zum befüllen der Tabelle */
					FillProductTable(tableProducts);
					meldung_rechts.setText(text);
				} else
					meldung_rechts
					        .setText("Wählen Sie ein Produkt zum Löschen\ndurch einen Mausklick");

			}
		});

		tableProducts.getSelectionModel().clearSelection();
		gridpane.add(meldung_rechts, 4, 5);
		gridpane.add(buttonRemoveProduct, 4, 3);

		/****************************************************/

		// Button: Place order
		Button buttonPlaceOrder = new Button("Bestellen");
		/* Zu Beginn ist die ProgressBar nicht zu sehen. */
		progressBar.visibleProperty().set(false);
		final Label percent = new Label("0 %");
		/* Zu Beginn ist die Prozentanzeige nicht zu sehen. */
		percent.visibleProperty().set(false);

		buttonPlaceOrder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/* Aktionen bei der Auswahl einer Zeile in den Tabellen */
				Customer kunde = (Customer) tableCustomers.getSelectionModel()
				        .getSelectedItem();
				Product ware = (Product) tableProducts.getSelectionModel()
				        .getSelectedItem();

				if (kunde != null && ware != null) {
					Order order = new Order();
					order.setCustomer(kunde);
					order.setProduct(ware);
					webshop.setProgress(percent);
					meldung_rechts.setText(order.toString());
					progressBar.visibleProperty().set(true);
					percent.visibleProperty().set(true);
					progressBar.progressProperty().unbind();
					progressBar.progressProperty().bind(model.getProgress());

					/* Zurücksetzen der Zeilenauswahl */
					tableCustomers.getSelectionModel().clearSelection();
					tableProducts.getSelectionModel().clearSelection();
					
				} else {

					if (kunde == null) {
						meldung_links.setText("");
						meldung_links
						        .setText("Bitte wählen Sie einen Kunden aus!");
					} else
						meldung_links.setText("");

					if (ware == null) {
						meldung_rechts.setText("");
						meldung_rechts
						        .setText("Bitte wählen Sie ein Produkt aus!");
					} else
						meldung_rechts.setText("");
				}
			}
		});

		gridpane.add(buttonPlaceOrder, 2, 4);
		gridpane.add(progressBar, 4, 4);
		gridpane.add(percent, 4, 6);

		/****************************************************/

		// Finalize stage
		Scene scene = new Scene(root, 800, 600, Color.WHITE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Tabelle der Kunden mit den Werten aus dem Model befüllen.
	 * 
	 * @param table
	 * @return
	 */
	private TableView<Customer> FillCustomerTable(TableView<Customer> table) {
		table.setItems(model.getCustomerList());

		// Setup the first column: Vorname
		TableColumn<Customer, String> firstNameCol = new TableColumn<>("VORNAME");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
		firstNameCol.setPrefWidth(table.getPrefWidth() / 2);

		// Setup the second colum: Nachname
		TableColumn<Customer, String> lastNameCol = new TableColumn<>(
		        "NACHNAME");
		lastNameCol
		        .setCellValueFactory(new PropertyValueFactory<Customer, String>(
		                "surName"));
		lastNameCol.setPrefWidth(table.getPrefWidth() / 2);

		/* Spalten in eine Tabelle zusammenführen */
		table.getColumns().add(firstNameCol);
		table.getColumns().add(lastNameCol);

		return table;
	}

	/**
	 * Tabelle der Produkte mit den Werten aus dem Model befüllen.
	 * 
	 * @param table
	 * @return
	 */
	private TableView<Product> FillProductTable(TableView<Product> table) {
		table.setItems(model.getProductList());

		// Setup the first column: Produktname
		TableColumn<Product, String> productNameCol = new TableColumn<>(
		        "BEZEICHNUNG");
		productNameCol
		        .setCellValueFactory(new PropertyValueFactory<Product, String>(
		                "productName"));
		productNameCol.setPrefWidth(table.getPrefWidth() / 2);

		// Setup the second colum: Preis
		TableColumn<Product, String> priceCol = new TableColumn<>("PREIS");
		priceCol.setCellValueFactory(new PropertyValueFactory<Product, String>(
		        "price"));
		priceCol.setPrefWidth(table.getPrefWidth() / 2);

		/* Spalten in eine Tabelle zusammenführen */
		table.getColumns().add(productNameCol);
		table.getColumns().add(priceCol);
		return table;
	}

}
