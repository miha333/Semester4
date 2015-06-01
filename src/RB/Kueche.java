package RB;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class Kueche extends Observable {
	
	private Queue<Integer> fertigeBurger; // Laufband
	private int burgerCounter; // um die Burger durch zu numerieren
	private int offeneBestellungen;
	private int [] burgerProAushilfe;
	
	/**
	 * Konstruktor
	 */
	public Kueche(){
		super();
		this.offeneBestellungen = 0;
		this.fertigeBurger = new LinkedList<Integer>();
		this.burgerCounter = 0;
		this.produziereBurger(1);
		this.produziereBurger(2);
		this.produziereBurger(3);
		this.burgerProAushilfe = new int [3];
	}	

	/**
	 * @param offeneBestellungen the offeneBestellungen to set
	 */
	public synchronized void setOffeneBestellungen(int bestellung) {
		this.offeneBestellungen += bestellung;
		setChanged();
		notifyObservers(getFertigeBurgerGesamt());
	}
	
	/**
	 * @return the offeneBestellungen
	 */
	public synchronized int getOffeneBestellungen() {
		return offeneBestellungen;
	}
	
	/**
	 * @return the fertigeBurger
	 */
	public Queue<Integer> getFertigeBurger() {
		return fertigeBurger;
	}
	
	/**
	 * @return the fertigeBurger
	 */
	public synchronized int getFertigeBurgerGesamt() {
		return fertigeBurger.size();
	}
	
	public int getBurgerProAushilfe(int ahNr){
		return this.burgerProAushilfe[ahNr];
	}
	
	public synchronized void setFertigeBurger(){
		fertigeBurger.add(burgerCounter++);
		if(getOffeneBestellungen() > 0)
			offeneBestellungen--;
		setChanged();
		notifyObservers(getFertigeBurgerGesamt());
	}
	
	/**
	 * @param Anzahl Burger
	 * Burger vom Laufband entnehmen
	 */
	public void burgerEntnehmen(int burgerAnzahl){
		for(int i = 0; i < burgerAnzahl; i++){
			fertigeBurger.poll();			
		}
		setChanged();
		notifyObservers(getFertigeBurgerGesamt());
	}

	
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
							
							if((getOffeneBestellungen() > 0 && getFertigeBurgerGesamt() < 12) || 
									(getOffeneBestellungen() == 0 && getFertigeBurgerGesamt() < 5)){
								// produzieren
								burgerProAushilfe[aushilfeNr-1]++;
								setFertigeBurger();
								
								
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
