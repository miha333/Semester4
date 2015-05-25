package RB;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * @author Papa
 *
 */
public class KundenGenerator extends Observable {
	private List<Kunde> queue = new LinkedList<Kunde>();
	private int kdNr;
	
	/**
	 * Konstruktor
	 */
	public KundenGenerator(){
		super();
		kdNr = 0;
	}  
	
	/**
	 * @return the queue
	 */
	public List<Kunde> getQueue() {
		return queue;
	}

	public void esWerdeKunde(int kunden) {
		if(this.queue.size() > 0)
			this.queue.clear();
		for(int i = 0; i < kunden; i++){
			Kunde customer = new Kunde();
			int tmp = (int)(Math.random()*8+1);
			customer.setKdNr(kdNr++);
			customer.setMenge(tmp);
			queue.add(customer);
			System.out.println("  "+ customer.getKdNr());
			setChanged();
			notifyObservers();
		}

	}

}
