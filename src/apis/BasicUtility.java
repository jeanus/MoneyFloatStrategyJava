package apis;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicUtility {
	
	// 获取某一股票 某个offset上 某一个index上的值
	public static String getElement(HashMap<String,HashMap<Integer,ArrayList<String>>> map,String symbol,int offset,int index){
		String ret = "";
		try{
			ret = map.get(symbol).get(offset).get(index);
		}catch(Exception e){
		//	e.printStackTrace();
			return "";
		}
		return ret;
	}
	
	// 获取某一股票 某一段offset区间里 某一个index上的值
	public static HashMap<Integer,String> getElementMap(HashMap<String,HashMap<Integer,ArrayList<String>>> map,String symbol,int offsetStart,int offsetEnd,int index){
		HashMap<Integer,String>  ret = new HashMap<Integer,String>();
		for(int offset = offsetStart; offset>=offsetEnd;offset--){
			String temp = "";
			temp = map.get(symbol).get(offset).get(index);
			ret.put(offset, temp);
		}
		return ret;
	}
	
	// 获取某一股票 某一段offset区间里 所有index上的值
	public static HashMap<Integer,ArrayList<String>> getElementFullMap(HashMap<String,HashMap<Integer,ArrayList<String>>> map,String symbol,int offsetStart,int offsetEnd){
		HashMap<Integer,ArrayList<String>> ret = new HashMap<Integer,ArrayList<String>>();
		for(int offset = offsetStart; offset>=offsetEnd;offset--){
			ret.put(offset, map.get(symbol).get(offset));
		}
		return ret;
	}
	
	public static HashMap<Integer,Double> parseToDouble(HashMap<Integer,String> map){
		HashMap<Integer,Double>  ret = new HashMap<Integer,Double>();
		for(Integer key:map.keySet()){
			double db = Double.parseDouble(map.get(key));
			ret.put(key, db);
		}
		return ret;
	}
	
	
	public static HashMap<String,Double> getAfterPriceInfo(HashMap<String,HashMap<Integer,ArrayList<String>>> map,String symbol,int offset){
		HashMap<String,Double> ret = new HashMap<String,Double>();
		HashMap<Integer,Double>  map1 =  parseToDouble(getElementMap( map,symbol,0, offset,1));
		double current = map1.get(offset);
		ret.put("current", current);
		map1.remove(offset);
		double high = Double.MIN_VALUE;
		double low = Double.MAX_VALUE;
		for(Integer key:map1.keySet()){
			double db = map1.get(key);
			if(db>=high){
				high = db;
			}
			if(db<=low){
				low = db;
			}			
		}
		ret.put("high", high);
		ret.put("low", low);
		return ret;
	}
	
}
