package RB;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class ServiceKraft implements Observer {

	private Queue<Kunde> queue1;
	private Queue<Kunde> queue2;
	private int abgewiesen;

	/**
	 * Konstruktor
	 */
	public ServiceKraft() {

		queue1 = new LinkedList<Kunde>();
		queue2 = new LinkedList<Kunde>();
		abgewiesen = 0;
	}

	/**
	 * @return the queue1
	 */
	public Queue<Kunde> getQueue1() {
		return queue1;
	}

	/**
	 * @return the queue2
	 */
	public Queue<Kunde> getQueue2() {
		return queue2;
	}

	/**
	 * @param lab
	 */
	public void setLabel(final Label lab, final int labelNr) {

		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {

				while (true) {
					Thread.sleep(100);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							switch (labelNr) {
							case 1:
								lab.setText(getQueue1().size() + "");
								break;
							case 2:
								lab.setText(getQueue2().size() + "");
								break;
							case 3:
								lab.setText(abgewiesen + "");
								break;
							default:
								lab.setText("Error");
								System.out
										.println("Die Label-Parameter sind ungültig");
								break;
							}
						}
					});

				}
			}
		};
		Thread thread = new Thread(task);
		thread.start();

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		KundenGenerator kg = (KundenGenerator) o;
		for (Kunde k : kg.getQueue()) {
			if (queue1.size() + queue2.size() < 20) {
				if (queue1.size() - queue2.size() < 1)
					queue1.add(k);
				else
					queue2.add(k);
			} else
				abgewiesen++;
		}
		// for(Kunde q: kg.getQueue()){
		// System.out.println(" "+q.getKdNr());
		// }

	}

}
