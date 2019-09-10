package application.save;

public class SaveManager {
	private Save saveObject;
	
	public SaveManager(Save saveObject) {
		this.saveObject=saveObject;
	}
	
	public void save()
	{
		saveObject.save();
	}
}
