package application.observer;


public interface Subject {
	
	void setObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers();
}
