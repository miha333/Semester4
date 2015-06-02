package RB;

import java.util.Date;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

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
 * @description Die Klasse ServiceKraft beobachtet was in der Klasse
 *              KundenGenerator passiert und verwaltet Geschaeftslogik. Auch die
 *              entsprechenden Ausgaben fuer die GUI werden hier vorbereitet.
 */
public class ServiceKraft implements Observer {

	private Queue<Kunde> queue1, queue2; // Die Warteschlangen
	private int abgewiesen; // Counter fuer abgewiesene Kunden
	private Kueche kueche;

	/**
	 * Konstruktor Die ersten Initialisierungen.
	 */
	public ServiceKraft() {
		this.queue1 = new LinkedList<Kunde>();
		this.queue2 = new LinkedList<Kunde>();
		this.abgewiesen = 0;
		this.bedienungAnfangen(queue1); // die Verwaltung der ersten Schlange
										// wird als
		// Thread gestartet
		this.bedienungAnfangen(queue2); // die Verwaltung der zweiten Schlange
										// wird als
		// Thread gestartet
		this.kueche = new Kueche();
	}

	/**
	 * 
	 * @param queue
	 *            eine der Warteschlangen wird hier erwartet.
	 * @return Anzahl der Kunden, die schon bestellt haben, und warten. Darunter
	 *         auch Kunden, die ihre Bestellung schon bekommen haben, stehen
	 *         aber noch rum (zaehlen Rueckgeld).
	 */
	public int getBestellungen(Queue<Kunde> queue) {
		int result = 0;
		for (Kunde k : queue) {
			if (k.isBestellt())
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

	/**
	 * Alle 5-10 Sekunden werden in einer Endlosschleife Bestellungen
	 * entgegengenommen.
	 * 
	 * @param queue
	 *            queue1 oder queue2
	 * 
	 */
	public void bedienungAnfangen(final Queue<Kunde> queue) {
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {

				while (true) {
					final int tmp = (int) (Math.random() * 5 + 5);
					Thread.sleep(tmp * 1000);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							bestellungAnnehmen(queue);
						}
					});

				}
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}

	/**
	 * Die Servicekraefte bedienen ihre Warteschlangen parallel zu einander
	 * 
	 * @param queue
	 *            queue1 oder queue2
	 * 
	 */
	private void bestellungAnnehmen(final Queue<Kunde> queue) {
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Date time = new Date();
						for (Kunde k : queue) {
							if (!k.isBestellt()) {
								k.setBestellt(true);
								k.setBestellZeit(time.getTime());
								kueche.setOffeneBestellungen(k.getMenge()); // Bestellung
																			// an
																			// die
																			// Kueche
																			// weiterleiten
								System.out.println("Kunde " + k.getKdNr()
										+ " hat " + k.getMenge()
										+ " Burger bestellt");
								// hier wird versucht die Bestellung gleich
								// auszugeben, wenn genug Burger fertig sind
								bestellungAusgeben(queue);
								break; // es wird nur fuer den ersten Treffer
										// bestellt
							}
						}
					}
				});
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}

	/**
	 * @param queue
	 *            eine der Warteschlangen
	 */
	public void bestellungAusgeben(Queue<Kunde> queue) {

		Kunde k = getFavorit(queue); // zuerst wird geschaut, wer die erste
										// Prioritaet hat

		// erst wenn genug fertige Burger vorliegen, wird die Bestellung
		// ausgegeben
		if (k != null && k.getMenge() <= kueche.getFertigeBurgerGesamt()) {
			kueche.burgerEntnehmen(k.getMenge());
			k.setBezahlt(true);
			k.setBekommen(true);
			System.out.println("Kunde " + k.getKdNr() + " hat seine "
					+ k.getMenge() + " Burger bekommen");
			locationVerlassen(k, queue);

		}
	}

	/**
	 * 
	 * @param queue
	 *            eine der Warteschlangen
	 * @return der Kunde mit dem hoechsten Prioritaet in seiner Warteschlange
	 */
	public Kunde getFavorit(Queue<Kunde> queue) {
		Kunde result = null;
		int menge = 0;
		Date time = new Date();
		long currentTime = time.getTime();
		long wartezeit = 0;
		long maxWartezeit = 0;

		// Mit peek() wird einfach der erste Kunde aus der Schlange genommen.
		// Wenn schon er nichts bestellt hat, dann brauchen wir nicht weiter zu
		// machen.
		// Anderenfalls wird nach der kleinsten Bestellmenge bzw. laengsten
		// Wartezeit ueber 60 Sekunden gesucht. Die Kunden auf die es trifft
		// werden zwischengespeichert.
		if (queue.peek().isBestellt() && !queue.peek().isBekommen()) {
			menge = queue.peek().getMenge();
			Kunde resultNachMenge = null;
			Kunde resultNachWartezeit = null;

			for (Kunde k : queue) {
				if (k.isBestellt() && !k.isBekommen()) {
					if (k.getMenge() < menge) {
						menge = k.getMenge();
						resultNachMenge = k;
					}

					wartezeit = (currentTime - k.getBestellZeit()) / 1000;

					if (wartezeit > 60 && maxWartezeit < wartezeit) {
						maxWartezeit = wartezeit;
						resultNachWartezeit = k;
					}
				}
			}

			if (maxWartezeit > 0) {
				result = resultNachWartezeit;
				System.out.println("Kunde " + result.getKdNr()
						+ " hat lange gewartet: (" + maxWartezeit + ")");
			} else {
				result = resultNachMenge;
			}
		}
		return result;
	}

	/**
	 * Der Kunde braucht 10-20 Sekunden um das Local zu verlassen
	 * 
	 * @param kunde
	 *            der Kunde, der seine Bestellung schon bekommen hat
	 * @param queue
	 *            seine Warteschlange
	 */
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
						System.out.println("Kunde " + kunde.getKdNr()
								+ " ist weg");
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
	 *            das Label, das aktualisiert werden muss
	 * @param labelNr
	 *            die laufende Nummer fuer die Fallunterscheidung
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
							String bestellt = "";
							String bekommen = "";

							switch (labelNr) {
							case 1: // Anzahl Kunden und Bestellungen in der 1.
									// Warteschlange
								lab.setText("\t" + getQueue1().size()
										+ "\t\t\t"
										+ getBestellungen(getQueue1()));
								break;
							case 2: // Anzahl Kunden und Bestellungen in der 2.
									// Warteschlange
								lab.setText("\t" + getQueue2().size()
										+ "\t\t\t"
										+ getBestellungen(getQueue2()));
								break;
							case 3: // Anzahl der abgewiesenen Kunden
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
							case 9: // die Abbildung der 1. Warteschlange
								for (Kunde k : getQueue1()) {
									bestellt = k.isBestellt() ? "*" : "";
									bekommen = k.isBekommen() ? "!" : "";
									alleKunden += k.getKdNr() + "("
											+ k.getMenge() + bestellt
											+ bekommen + ") - ";
								}
								lab.setText(alleKunden);
								break;
							case 10: // die Abbildung der 2. Warteschlange
								for (Kunde k : getQueue2()) {
									bestellt = k.isBestellt() ? "*" : "";
									bekommen = k.isBekommen() ? "!" : "";
									alleKunden += k.getKdNr() + "("
											+ k.getMenge() + bestellt
											+ bekommen + ") - ";
								}
								lab.setText(alleKunden);
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
		// sobald neue Kunden generiert werden, teilen wir sie
		// gleichmaessig in unsere 2 Warteschlangen ein.

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

		}
	}
}
