package citysdk.tourism.client.poi.base;

import java.util.HashMap;

public final class GeometryHashMap {
	private static HashMap<Integer, String> geometryInfo = new HashMap<Integer, String>();
	
	private GeometryHashMap() {
		
	}
	
	public static int addGeometry(String posList) {
		int key = getHash(posList);
		if (!containsKey(key)) {
			geometryInfo.put(key, posList);
		}
		return key;
	}
	
	public static void delGeometry(String posList) {
		geometryInfo.remove(posList);
	}
	
	public static String getGeometry(int key) {
		return geometryInfo.get(key);
	}
	
	public static int getGeometrySize() {
		return geometryInfo.size();
	}
	
	public static boolean containsKey(int key) {
		return geometryInfo.containsKey(key);
	}
	public static boolean containsValue(String posList) {
		return geometryInfo.containsValue(posList);
	}

	public static void clearList() {
		geometryInfo.clear();
	}
	
	private static int getHash(String posList) {
		if(posList == null) {
			return 0;
		}
		return posList.hashCode();
	}
}
