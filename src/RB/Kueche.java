package RB;

import java.util.LinkedList;
import java.util.Queue;

import javafx.application.Platform;
import javafx.concurrent.Task;

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
 * @description Die Klasse Kueche ist fuer die Produktion und Bereitstellung
 * 			der Burger verantwortlich. 
 */
public class Kueche{
	
	private Queue<Integer> fertigeBurger; // Laufband
	private int burgerCounter; // um die Burger durch zu numerieren
	private int offeneBestellungen;
	private int [] burgerProAushilfe;
	
	/**
	 * Konstruktor Die ersten Initialisierungen.
	 */
	public Kueche(){
		this.offeneBestellungen = 0;
		this.fertigeBurger = new LinkedList<Integer>();
		this.burgerCounter = 1;
		this.produziereBurger(1); // Aushilfe 1 als Thread starten
		this.produziereBurger(2); // Aushilfe 2 als Thread starten
		this.produziereBurger(3); // Aushilfe 3 als Thread starten
		this.burgerProAushilfe = new int [3]; // zur Kontrolle: welche AH wieviel gemacht hat
	}	

	/**
	 * @param offeneBestellungen the offeneBestellungen to set
	 */
	public synchronized void setOffeneBestellungen(int bestellung) {
		this.offeneBestellungen += bestellung;
		notifyAll();
	}
	
	/**
	 * @return the offeneBestellungen
	 */
	public synchronized int getOffeneBestellungen() {
		notifyAll();
		return offeneBestellungen;
	}
	
	/**
	 * @return the fertigeBurger
	 */
	public synchronized int getFertigeBurgerGesamt() {
		notifyAll();
		return fertigeBurger.size();
	}
	
	public synchronized int getBurgerProAushilfe(int ahNr){
		notifyAll();
		return this.burgerProAushilfe[ahNr];
	}
	
	public synchronized void setFertigeBurger(){
		int burger = burgerCounter++;
		fertigeBurger.add(burger);
		System.out.println("+++Burger Nr. " + burger + " wurde erstellt");
		if(getOffeneBestellungen() > 0)
			offeneBestellungen--;
		notifyAll();
	}
	
	/**
	 * @param Anzahl Burger
	 * Burger vom Laufband entnehmen
	 */
	public synchronized void burgerEntnehmen(int burgerAnzahl){
		int burger = 0;
		for(int i = 0; i < burgerAnzahl; i++){
			burger = fertigeBurger.poll();	
			System.out.println("---Burger Nr. " + burger + " wurde verkauft");
		}
		notifyAll();
	}

	/**
	 * Die Methode wird parallel von 3 Aushilfen ausgefuert
	 * @param aushilfeNr
	 */
	public void produziereBurger(final int aushilfeNr){
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {

				while (true) {
					
					final int tmp = (int) (Math.random() * 10 + 10);
					Thread.sleep(tmp * 1000);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// Regel 1) maximal 12 Burger mit Bestellung 
							// Regel 2) oder 5 ohne. 
							// Mindestens 2 Burger ohne Bestellung wird mit der 2. Regel abgegolten
							if((getOffeneBestellungen() > 0 && getFertigeBurgerGesamt() < 12) || 
									(getOffeneBestellungen() == 0 && getFertigeBurgerGesamt() < 5)){
								
								burgerProAushilfe[aushilfeNr-1]++;
								
								setFertigeBurger();	// produzieren						
							} else {
								getOffeneBestellungen(); // einfach um ein notifyAll() aufzurufen, 
														//	damit Burger vom Laufband genommen werden
							}

						}
					});

				}
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}
}
