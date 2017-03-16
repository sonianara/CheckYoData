package backend;

public class InventoryItem implements DatabaseObject{
	private String name;
	private int count;


    public InventoryItem() {
    }

    public InventoryItem(String name, int count) {
        super();
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return this.name;
    }

    public int getCount() {
        return this.count;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String getKeys() {
        return "name, count";
    }

    @Override
    public String getValues() {
        return "'" + this.name + "', " + this.count;
    } 

    @Override
    public String getTable() {
        return "inventory";
    }

    @Override
    public String getKeyIdentifier() {
        return "name";
    }



	
	
}
