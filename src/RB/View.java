package RB;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
 * @description Die Main-Klasse ist fuer die Darstellung verantwortlich
 */
public class View extends Application {
		public static KundenGenerator kg = new KundenGenerator();
		public static ServiceKraft waiter = new ServiceKraft();

	public static void main(String[] args) {

		kg.addObserver(waiter);


		Task<Boolean> task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {
				while(true){
					Thread.sleep(30000); // alle 30 Sekunden
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							int tmp = (int)(Math.random()*5+1); // 1-5 Kunden
							kg.esWerdeKunde(tmp); // Kunden generieren
							System.out.println("Erstelle " + tmp + " Kunden");
						}
					});
							
				}
			}
		} ;
		Thread thread = new Thread(task);
		thread.start();
		Application.launch(args);
	}
	
	@Override
	public void start(final Stage primaryStage) throws Exception {

		// Setup the stage with the scene
				primaryStage.setTitle("Fastfood");
				StackPane root = new StackPane(); // Kann auch Group oder VBox sein

				// Use a grid pane for organization
				final GridPane gridpane = new GridPane();
				gridpane.setPadding(new Insets(5));
				gridpane.setHgap(10);
				gridpane.setVgap(10);

				/* gridpane als erstes Kind des Wurzelknoten */
				root.getChildren().add(gridpane);
				/*******************************************/
				
				// Labels definieren
				Label header = new Label("KUNDEN:     BESTELLUNGEN:");
				Label header_queue = new Label("DETAILS:");
				Label header_kueche = new Label("BURGER:");
				Label legende = new Label("LEGENDE ZU DETAILS:");
				Label legende_text = new Label("kdNr(bestellMenge), * - bestellt, ! - bekommen");
				
				Label titel1 = new Label("Schlange 1: ");
				final Label titel2 = new Label("");
				waiter.setLabel(titel2, 1);

				Label titel3 = new Label("Schlange 2: ");
				final Label titel4 = new Label("");
				waiter.setLabel(titel4, 2);
				
				Label titel5= new Label("Abgewiesen: ");
				final Label titel6 = new Label("");
				waiter.setLabel(titel6, 3);
				
				Label titel7= new Label("Aushilfe 1: ");
				final Label titel8 = new Label("");
				waiter.setLabel(titel8, 4);
				
				Label titel9= new Label("Aushilfe 2: ");
				final Label titel10 = new Label("");
				waiter.setLabel(titel10, 5);
				
				Label titel11= new Label("Aushilfe 3: ");
				final Label titel12 = new Label("");
				waiter.setLabel(titel12, 6);
				
				Label titel13= new Label("Burger in offenen Bestellungen: ");
				final Label titel14 = new Label("");
				waiter.setLabel(titel14, 7);
				
				Label titel15= new Label("Burger auf dem Laufband: ");
				final Label titel16 = new Label("");
				waiter.setLabel(titel16, 8);
				
				final Label titel17 = new Label("");
				waiter.setLabel(titel17, 9);
				final Label titel18 = new Label("");
				waiter.setLabel(titel18, 10);

				// Button
				Button cancel = new Button(" Schliessen ");
				cancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {					
						primaryStage.close(); // Fenster Schliessen
						System.exit(0); // Programm beenden
					}

				});
				
				// Labels platzieren
				gridpane.add(header, 2, 0);
				gridpane.add(header_kueche, 2, 4);
				gridpane.add(header_queue, 4, 0);
				gridpane.add(legende, 4, 4);
				gridpane.add(legende_text, 4, 5);
				
				gridpane.add(titel1, 1, 1);
				gridpane.add(titel2, 2, 1);
				gridpane.add(titel3, 1, 2);
				gridpane.add(titel4, 2, 2);
				gridpane.add(titel5, 1, 3);
				gridpane.add(titel6, 2, 3);
				gridpane.add(titel17, 4, 1);
				gridpane.add(titel18, 4, 2);
				
				gridpane.add(titel7, 1, 5);
				gridpane.add(titel8, 2, 5);
				gridpane.add(titel9, 1, 6);
				gridpane.add(titel10,2, 6);
				gridpane.add(titel11, 1, 7);
				gridpane.add(titel12, 2, 7);
				
				gridpane.add(titel13, 1, 8);
				gridpane.add(titel14, 2, 8);
				gridpane.add(titel15, 1, 9);
				gridpane.add(titel16, 2, 9);
				gridpane.add(cancel, 4, 9);
				GridPane.setHalignment(titel1, HPos.LEFT);
				
//				/****************************************************/

				// Finalize stage
				Scene scene = new Scene(root, 900, 300, Color.WHITE);
				primaryStage.setScene(scene);
				primaryStage.show();
				
	}

}
