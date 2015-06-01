package RB;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class ServiceKraft implements Observer {

	private Queue<Kunde> queue1, queue2;
	private int abgewiesen;
	private Kueche kueche;

	/**
	 * Konstruktor
	 */
	public ServiceKraft() {
		this.queue1 = new LinkedList<Kunde>();
		this.queue2 = new LinkedList<Kunde>();
		this.abgewiesen = 0;
		this.bestellung();
		this.kueche = new Kueche();
		kueche.addObserver(this);
	}

	public int getBestellungen(Queue<Kunde> queue) {
		int result = 0;
		for (Kunde k : queue) {
			if (k.isBestellt() && !k.isBekommen())
				result++;
		}
		return result;
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

	public void bestellung() {
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {

				while (true) {
					final int tmp = (int) (Math.random() * 5 + 5);
					Thread.sleep(tmp * 1000);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if (getBestellungen(getQueue1()) - getBestellungen(getQueue2()) < 3) {
								bestellungAnnehmen(getQueue1());
							} else {
								bestellungAnnehmen(getQueue2());
							}
						}
					});

				}
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}

	private void bestellungAnnehmen(Queue<Kunde> queue) {

		for (Kunde k : queue) {
			if (!k.isBestellt()) {
				k.setBestellt(true);
				k.setBestellZeit(System.currentTimeMillis());
				kueche.setOffeneBestellungen(k.getMenge());
				System.out.println("Kunde " + k.getKdNr() + " hat " + k.getMenge() + " Burger bestellt");
				break;
			}
		}
	}

	public void bestellungAusgeben(int burgerAnzahl, Queue<Kunde> queue) {

			Kunde k = getFavorit(queue);
			if (k != null && k.getMenge() <= burgerAnzahl) {
				kueche.burgerEntnehmen(k.getMenge());
				k.setBezahlt(true);
				k.setBekommen(true);
				System.out.println("Kunde " + k.getKdNr() + " hat seine " + k.getMenge() + " Burger bekommen");
				locationVerlassen(k, queue);
				
			}
	}

	public Kunde getFavorit(Queue<Kunde> queue) {
		Kunde result = null;
		int menge = 0;
		long currentTime = System.currentTimeMillis();
		
		if (queue.peek().isBestellt() && !queue.peek().isBekommen()) {
			menge = queue.peek().getMenge();
			for (Kunde k : queue) {
				long wartezeit = currentTime - k.getBestellZeit();
				System.out.println("Kunde " + k.getKdNr() + " hat lange gewartet: (" + wartezeit + ")");
				if (k.getMenge() < menge && k.isBestellt() && !k.isBekommen()) {
					if (wartezeit > 60000) {
						result = k;
						
						break;
					}
					menge = k.getMenge();
					result = k;
				}

			}
		}
		return result;
	}

	public void locationVerlassen(final Kunde kunde, final Queue<Kunde> queue) {
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {

				
					final int tmp = (int) (Math.random() * 10 + 10);
					Thread.sleep(tmp * 1000);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							queue.remove(kunde);
							if(queue.contains(kunde));
							System.out.println("Kunde " + kunde.getKdNr() + " ist weg");
						}
					});
					return null;
			}
		};
		Thread thread = new Thread(task);
		thread.start();
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
							String alleKunden = "";
							switch (labelNr) {
							case 1:
								for(Kunde k : getQueue1()){
									alleKunden += k.getKdNr() + "(" + k.getMenge() + ") - ";
								}
								lab.setText("\t" + getQueue1().size()
										+ "\t\t\t"
										+ getBestellungen(getQueue1()) + " : " + alleKunden);
								break;
							case 2:
								for(Kunde k : getQueue2()){
									alleKunden += k.getKdNr() + "(" + k.getMenge() + ") - ";
								}
								lab.setText("\t" + getQueue2().size()
										+ "\t\t\t"
										+ getBestellungen(getQueue2()) + " : " + alleKunden);
								break;
							case 3:
								lab.setText(abgewiesen + "");
								break;
							case 4:
								lab.setText("\t"
										+ kueche.getBurgerProAushilfe(0) + "");
								break;
							case 5:
								lab.setText("\t"
										+ kueche.getBurgerProAushilfe(1) + "");
								break;
							case 6:
								lab.setText("\t"
										+ kueche.getBurgerProAushilfe(2) + "");
								break;
							case 7:
								lab.setText(kueche.getOffeneBestellungen() + "");
								break;
							case 8:
								lab.setText(kueche.getFertigeBurgerGesamt()
										+ "");
								break;
							default:
								lab.setText("Error");
								System.out
										.println("Die Label-Parameter sind ungueltig");
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
		if (o instanceof KundenGenerator) {
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

		} else if (o instanceof Kueche) {
			// Kueche kitchen = (Kueche) o;
			if (getQueue1().size() > 0)
				this.bestellungAusgeben((int) arg, getQueue1());
			if (getQueue2().size() > 0)
				this.bestellungAusgeben((int) arg, getQueue2());
		}

	}
}
