package RB;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application {
		public static KundenGenerator kg = new KundenGenerator();
		public static ServiceKraft waiter = new ServiceKraft();
		public static IntegerProperty x = new SimpleIntegerProperty(0);

		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		kg.addObserver(waiter);


		Task<Boolean> task = new Task<Boolean>(){

			@Override
			protected Boolean call() throws Exception {

				for (int i = 0; i < 5; i++) {
					Thread.sleep(2000);
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							int tmp = (int)(Math.random()*5+1);
							kg.esWerdeKunde(tmp);
							System.out.println(tmp);
						}
					});
							
				}
				return null;
			}
		} ;
		Thread thread = new Thread(task);
		thread.start();
		Application.launch(args);
	}
	@Override
	public void start(final Stage primaryStage) throws Exception {
		
		// TODO Auto-generated method stub
		// Setup the stage with the scene
				primaryStage.setTitle("Location");
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
				Label titel1 = new Label("Anzahl Kunden in der Schlange 1: ");
				final Label titel2 = new Label("");
				waiter.setLabel(titel2, 1);

				Label titel3 = new Label("Anzahl Kunden in der Schlange 2: ");
				final Label titel4 = new Label("");
				waiter.setLabel(titel4, 2);
				
				Label titel5= new Label("Abgewiesen: ");
				final Label titel6 = new Label("");
				waiter.setLabel(titel6, 3);
				
				gridpane.add(titel1, 1, 0);
				gridpane.add(titel2, 2, 0);
				gridpane.add(titel3, 1, 1);
				gridpane.add(titel4, 2, 1);
				gridpane.add(titel5, 1, 2);
				gridpane.add(titel6, 2, 2);
				GridPane.setHalignment(titel1, HPos.LEFT);
				
//				/****************************************************/

				// Finalize stage
				Scene scene = new Scene(root, 800, 400, Color.WHITE);
				primaryStage.setScene(scene);
				primaryStage.show();
				
	}

}
