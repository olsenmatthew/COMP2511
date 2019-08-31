package unsw.dungeon.items.bag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import unsw.dungeon.Dungeon;
import unsw.dungeon.items.Item;
import unsw.dungeon.items.key.Key;
import unsw.dungeon.items.sword.Sword;

/**
 * {@link Bag} extends {@link Item}, {@link Bag} is the composite item in
 * {@link Item}'s composite pattern
 */
public class Bag extends Item {

	/**
	 * Maps class generic to list of items of same class
	 */
	private Map<Class<?>, List<Item>> itemsMap;
	/**
	 * cls is the selected class
	 */
	private Class<?> cls;
	/**
	 * selected is a simple name of cls
	 */
	private StringProperty selected;
	/**
	 * map property keys are simple names of classes of itemsMap key set, map
	 * property values are the count of itemsMap for the corresponding class
	 */
	private MapProperty<String, Integer> itemsProperty;

	/**
	 * @param dungeon
	 * @param x
	 * @param y
	 */
	public Bag(Dungeon dungeon, int x, int y) {
		super(dungeon, x, y);
		this.itemsMap = new HashMap<Class<?>, List<Item>>();
		this.cls = null;
		this.selected = new SimpleStringProperty("");
		this.itemsProperty = new SimpleMapProperty<String, Integer>(FXCollections.observableHashMap());
	}

	/*
	 * use the next item matching the cls if item is used up, remove item clean bag
	 * afterwards
	 * 
	 * @see unsw.dungeon.items.Item#use()
	 * 
	 * @return true if item is successfully used, false otherwise
	 */
	@Override
	public boolean use() {
		if (isAvailable(cls)) {
			List<Item> itemsList = itemsMap.get(cls);
			Item item = itemsList.get(0);
			boolean result = item.use();
			if (item.remove()) {
				itemsList.remove(item);
			}
			clean();
			return result;
		}
		return false;
	}

	/**
	 * Add item to bag, add update cls and setItemsProperty
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		if (item != null) {
			clean();
			if (cls == null || itemsMap.keySet().isEmpty()) {
				cls = item.getClass();
			}
			getDungeon().removeEntity(item);
			addToItemsMap(item);
			if (!cls.getSimpleName().equals(selected.get())) {
				this.selected.set(cls.getSimpleName());
			}
			setItemsProperty();
		}
	}

	/**
	 * @param item
	 */
	private void addToItemsMap(Item item) {
		if (isAvailable(item.getClass())) {
			System.out.println("HERE " + item.getClass().toString());
			if ((item.getClass().equals(Key.class) || item.getClass().equals(Sword.class))
					&& itemsMap.get(item.getClass()).size() >= 1) {
				Item removing = itemsMap.get(item.getClass()).remove(0);
				getDungeon().addEntity(removing);
			}
			itemsMap.get(item.getClass()).add(item);
		} else {
			List<Item> inserting = new ArrayList<Item>();
			inserting.add(item);
			itemsMap.put(item.getClass(), inserting);
		}
	}

	/**
	 * @param cls
	 * @return return true items if this cls are held in the bag, false otherwise
	 */
	private boolean isAvailable(Class<?> cls) {
		return (!this.itemsMap.isEmpty() && itemsMap.get(cls) != null && itemsMap.get(cls).size() > 0);
	}

	/**
	 * @return next item in bag of selected type
	 */
	public Item removeItem() {
		clean();
		Item item = null;
		if (!isAvailable(cls)) {
			switchItem();
		}
		if (isAvailable(cls)) {
			item = itemsMap.get(cls).remove(0);
			if (itemsMap.get(cls).isEmpty()) {
				switchItem();
			}
			setItemsProperty();
			return item;
		}
		setItemsProperty();
		return item;
	}

	/**
	 * switch selected to the next item in bag, cls is class of first item if there
	 * are no items in the bag, cls if null if no next item & first item is null
	 */
	public void switchItem() {
		clean();
		int i = 0;
		int k = 0;
		Class<?> first = null;
		Class<?> next = null;
		for (Class<?> c : itemsMap.keySet()) {
			if (k == 0) {
				first = c;
			}
			if (k > i) {
				next = c;
				break;
			}
			if (!c.equals(cls)) {
				i++;
			}
			k++;
		}
		this.cls = (next == null) ? first : next;
		if (selected != null && cls != null) {
			this.selected.set(cls.getSimpleName());
		}
	}

	/*
	 * get value of each item in bag
	 * 
	 * @see unsw.dungeon.items.Item#getValue()
	 * 
	 * @return value of bag (+all of it's items)
	 */
	public int getValue() {
		int value = 0;
		for (Class<?> c : itemsMap.keySet()) {
			if (isAvailable(c)) {
				for (Item i : itemsMap.get(c)) {
					value += i.getValue();
				}
			}
		}
		return value;
	}

	/**
	 * @param cls
	 * @return number of items of type of passed in class
	 */
	public int countType(Class<?> cls) {
		int count = 0;
		if (isAvailable(cls)) {
			count += (itemsMap.get(cls)).size();
		}
		return count;
	}

	/*
	 * setY() for self and all available items
	 * 
	 * @see unsw.dungeon.Entity#setY(int)
	 */
	@Override
	public void setY(int y) {
		for (Class<?> i : itemsMap.keySet()) {
			if (isAvailable(i)) {
				for (Item item : itemsMap.get(i)) {
					item.setY(y);
				}
			}
		}
		super.setX(y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see unsw.dungeon.Entity#setX(int)
	 */
	@Override
	public void setX(int x) {
		for (Class<?> i : itemsMap.keySet()) {
			if (isAvailable(i)) {
				for (Item item : itemsMap.get(i)) {
					item.setX(x);
				}
			}
		}
		super.setX(x);
	}

	/*
	 * setX() for self and all available items
	 * 
	 * @see unsw.dungeon.items.Item#remove()
	 */
	@Override
	public boolean remove() {
		return itemsMap.keySet().isEmpty();
	}

	/**
	 * remove all items that should be removed based off of their remove boolean
	 * function, remove null items and those mappings in itemsMap with a value list
	 * size of 0
	 */
	private void clean() {
		List<Class<?>> rKeys = new ArrayList<Class<?>>();
		for (Class<?> c : itemsMap.keySet()) {
			if (isAvailable(c)) {
				List<Item> remove = new ArrayList<Item>();
				for (Item i : itemsMap.get(c)) {
					if (i.remove()) {
						remove.add(i);
					}
				}
				itemsMap.get(c).removeAll(remove);
			} else {
				rKeys.add(c);
			}
		}
		for (Class<?> c : rKeys) {
			itemsMap.remove(c);
		}
		setItemsProperty();
	}

	/**
	 * set items property to represent itemsMap
	 * 
	 * itemsProperty: K: simple names of classes from itemsMap keyset, V: number
	 * items of each corresponding class in itemsMap value list size
	 * 
	 */
	private void setItemsProperty() {
		try {
			itemsProperty.clear();
			for (Class<?> c : itemsMap.keySet()) {
				if (c != null && itemsMap.get(c).size() > 0) {
					itemsProperty.put(c.getSimpleName(), itemsMap.get(c).size());
				}
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return itemsProperty, K: simple names of classes, V: number items of each
	 *         corresponding class
	 */
	public MapProperty<String, Integer> getItems() {
		return itemsProperty;
	}

	/**
	 * @return simple name of selected class
	 */
	public StringProperty getSelected() {
		return selected;
	}

}
