package apis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Main {
	public static HashMap<String, HashMap<Integer, ArrayList<String>>> dataMap;
	public static ArrayList<String> symbols;
	public static HashSet<String> blackList = new HashSet<String>();
	private static Random random = new Random();
	private static int MaxOffset = -45;
	public static HashMap<String, String> symbolsRicequant;

	public static void changePosition() {
		for (int index = symbols.size() - 1; index >= 0; index--) {
			// 从0到index处之间随机取一个值，跟index处的元素交换
			exchange(random.nextInt(index + 1), index);
		}
	}

	// 交换位置
	private static void exchange(int p1, int p2) {
		String temp = symbols.get(p1);
		symbols.set(p1, symbols.get(p2));
		symbols.set(p2, temp);
	}

	public static ArrayList<String> continuousIncome(int total, int upLimit,
			int holdDuration) {
		ArrayList<String> ret = new ArrayList<String>();
		int success = 0;
		int number = 0;
		double profit = 1;
		TreeMap<String, String> orderMap = new TreeMap<String, String>();
		// HashSet<String> boughtDays = new HashSet<String>();
		changePosition();
		for (String symbol : symbols) {
			if (blackList.contains(symbol)
					|| !BasicUtility.getElement(dataMap, symbol, MaxOffset, 0)
							.equals("2016-07-25"))
				continue;
			for (int current = MaxOffset; current <= 0; current++) {

				if ((30 + current) < total)
					continue;
				// System.out.println(current);
				HashMap<Integer, String> map1 = BasicUtility.getElementMap(
						dataMap, symbol, current, current - total + 1, 3);
				int upTemp = 0;
				for (Integer key : map1.keySet()) {
					String change = map1.get(key);
					if (change.startsWith("-"))
						upTemp++;
				}
				if (upTemp >= upLimit) {

					if (ret.size() > 0
							&& !ret.get(ret.size() - 1).equals(symbol))
						ret.add(symbol);
					// HashMap<String,Double> map2 =
					// BasicUtility.getAfterPriceInfo(dataMap, symbol, current);

					try {
						int sellDay = current + holdDuration + 1;
						if (sellDay > 0)
							continue;
						double sellPrice = Double.parseDouble(BasicUtility
								.getElement(dataMap, symbol, sellDay, 1));
						// double currentPrice =
						// Double.parseDouble(BasicUtility.getElement(dataMap,
						// symbol, current, 1));
						double currentPrice = Double.parseDouble(BasicUtility
								.getElement(dataMap, symbol, current + 1, 1));
						String currentMoney = BasicUtility.getElement(dataMap,
								symbol, current + 1, 3);
						if (!currentMoney.startsWith("-")) {
							// if(!boughtDays.contains(BasicUtility.getElement(dataMap,
							// symbol, current+1, 0)))
							// {
							if (!(BasicUtility.getElement(dataMap, symbol,
									current, 0).startsWith("2016-07")
									|| BasicUtility.getElement(dataMap, symbol,
											current, 0).startsWith("2016-08") || BasicUtility
									.getElement(dataMap, symbol, current, 0)
									.startsWith("2016-09")))
								continue;
							// if(BasicUtility.getElement(dataMap, symbol,
							// current+1, 3).startsWith("-"))
							// continue;
							// System.out.println((BasicUtility.getElement(dataMap,
							// symbol, current+1, 3)));
							number++;
							// System.out.println(number);
							// System.out.println("buy "+ symbol + " on " +
							// BasicUtility.getElement(dataMap, symbol, current,
							// 0));
							// System.out.println("sell "+ symbol + " on " +
							// BasicUtility.getElement(dataMap, symbol, sellDay,
							// 0));
							// System.out.println("change " +
							// (sellPrice-currentPrice)/currentPrice);

							// profit = profit *(sellPrice/currentPrice-0.001);
							// System.out.println("profit "+profit);
							if (!symbol.startsWith("30")
									&& !BasicUtility.getElement(dataMap,
											symbol, current + 1, 2).startsWith(
											"9.")
									&& !BasicUtility.getElement(dataMap,
											symbol, current + 1, 2).startsWith(
											"10."))
								orderMap.put(BasicUtility.getElement(dataMap,
										symbol, current + 1, 0), symbol + ","
										+ (sellPrice / currentPrice - 0.001));
							if (sellPrice > currentPrice) {
								success++;
							}

							// boughtDays.add(BasicUtility.getElement(dataMap,
							// symbol, current+1, 0));
						}
						// }
					} catch (Exception e) {

					}
				}
				// System.out.println(symbol+" "+ current+" "+total+" "+upTemp);
			}
		}

		for (String key : orderMap.keySet()) {
			profit = profit
					* Double.parseDouble(orderMap.get(key).split(",")[1]);
			// System.out.println(key+" "+
			// orderMap.get(key).split(",")[0]+" "+profit);
		}
		// System.out.println(ret.size());

		// System.out.println();
		// System.out.println(total);
		// System.out.println(number);
		// System.out.println(success);
		// System.out.println((double)success/(double)number);
		// System.out.println(profit);
		// System.out.println(profit/(double)number);
		ret.add(0, profit + "");
		return ret;
	}

	public void finalStategy(int totol, int back) {
		for (int i = 0; i > -30; i--) {

		}
	}

	public static int tongHao = 0;
	public static int yiHao = 0;
	public static int futureUp = 0;
	public static int futureDown = 0;
	public static TreeMap<String, TreeMap<String, Double>> orderMap = new TreeMap<String, TreeMap<String, Double>>();

	public static HashSet<String> chance = new HashSet<String>();

	public static ArrayList<String> continuousIncomeStockList(int offset,
			int total, int upDay, int backAmout, int hold) {
		ArrayList<String> ret = new ArrayList<String>();

		if ((Math.abs(MaxOffset) + 1 + offset) < (total + backAmout))
			return ret;

		for (String symbol : symbols) {
			if (!BasicUtility.getElement(dataMap, symbol, MaxOffset, 0).equals(
					"2016-07-25")
					|| symbol.startsWith("30"))
				continue;

			HashMap<Integer, String> map1 = BasicUtility.getElementMap(dataMap,
					symbol, offset, offset - total + 1, 3);
			int upTemp = 0;
			for (Integer key : map1.keySet()) {
				String change = map1.get(key);
				if (!change.startsWith("-"))
					upTemp++;
			}

			double currentPrice = Double.parseDouble(BasicUtility.getElement(
					dataMap, symbol, offset, 1));
			double previousPrice = Double.parseDouble(BasicUtility.getElement(
					dataMap, symbol, offset - total + 1, 1));
			HashMap<Integer, String> amountMap = BasicUtility.getElementMap(
					dataMap, symbol, offset, offset - total + 1, 3);
			HashMap<Integer, String> amountMapPrevious = BasicUtility
					.getElementMap(dataMap, symbol, offset - total, offset
							- total - backAmout + 1, 3);

			double amount = 0;
			for (int key : amountMap.keySet()) {
				String str = amountMap.get(key);
				double temp = 0;
				if (str.contains("万")) {
					int index = str.indexOf("万");
					double db = Double.parseDouble(str.substring(0, index));
					temp = db * 10000;
				} else if (str.contains("亿")) {
					int index = str.indexOf("亿");
					double db = Double.parseDouble(str.substring(0, index));
					temp = db * 100000000;
				} else {
					double db = Double.parseDouble(str);
					temp = db;
				}
				amount = temp + amount;
			}

			double amountPrevious = 0;
			for (int key : amountMapPrevious.keySet()) {
				String str = amountMapPrevious.get(key);
				double temp = 0;
				if (str.contains("万")) {
					int index = str.indexOf("万");
					double db = Math.abs(Double.parseDouble(str.substring(0,
							index)));
					temp = db * 10000;
				} else if (str.contains("亿")) {
					int index = str.indexOf("亿");
					double db = Math.abs(Double.parseDouble(str.substring(0,
							index)));
					temp = db * 100000000;
				} else {
					double db = Math.abs(Double.parseDouble(str));
					temp = db;
				}
				amountPrevious = temp + amountPrevious;
			}
			amountPrevious = amountPrevious / amountMapPrevious.size();
			amount = amount / amountPrevious;

			int buyday = offset + 1;
			double buyPrice = 1;
			double sellPrice = 1;
			try {
				sellPrice = Double.parseDouble(BasicUtility.getElement(dataMap,
						symbol, buyday + hold, 1));
				buyPrice = Double.parseDouble(BasicUtility.getElement(dataMap,
						symbol, buyday, 1));
			} catch (Exception e) {
				buyPrice = 1;
				sellPrice = 1;
			}
			if (upTemp == upDay) {
				ret.add(symbol + " " + (currentPrice / previousPrice - 1) + " "
						+ (sellPrice / currentPrice - 1));
				if ((currentPrice / previousPrice - 1) > -0.02
						&& (currentPrice / previousPrice - 1) < 0
						&& !BasicUtility.getElement(dataMap, symbol, buyday, 2)
								.startsWith("9.")
						&& !BasicUtility.getElement(dataMap, symbol, buyday, 2)
								.startsWith("10.")) {
					String today = BasicUtility.getElement(dataMap, "600365",
							offset, 0);
					double profit = sellPrice / buyPrice - 1 - 0.0015;
					double change = currentPrice / previousPrice - 1;
					double weight = Math.abs(change * profit * 100);
					if (!orderMap.containsKey(today)) {
						TreeMap<String, Double> tree = new TreeMap<String, Double>();
						tree.put(symbol, profit);
						tree.put("0", change);
						tree.put("1", amount);
						tree.put("2", weight);
						// tree.put("3", amountPrevious);
						// tree.put("4", amount*amountPrevious);
						orderMap.put(today, tree);

					} else {
						double changeInside = orderMap.get(today).get("0");
						double amountInside = orderMap.get(today).get("1");
						double weightInside = orderMap.get(today).get("2");
						if (amountInside < amount) {
							orderMap.remove(today);
							TreeMap<String, Double> tree = new TreeMap<String, Double>();
							tree.put(symbol, profit);
							tree.put("0", change);
							tree.put("1", amount);
							tree.put("2", weight);
							// tree.put("3", amountPrevious);
							// tree.put("4", amount*amountPrevious);
							orderMap.put(today, tree);
						}
					}
					chance.add(symbol
							+ " "
							+ BasicUtility.getElement(dataMap, "600365",
									offset, 0) + " "
							+ (sellPrice / buyPrice - 1));
					if (((currentPrice / previousPrice - 1) * (sellPrice
							/ buyPrice - 1)) > 0)
						tongHao++;
					if (((currentPrice / previousPrice - 1) * (sellPrice
							/ buyPrice - 1)) < 0)
						yiHao++;
					if ((sellPrice / buyPrice - 1) > 0)
						futureUp++;
					if ((sellPrice / buyPrice - 1) < 0)
						futureDown++;
				}
			}
		}
		return ret;
	}

	public static String targetDate = "2016-09-27";
	public static TreeMap<Double, String> targets = new TreeMap<Double, String>();

	public static ArrayList<String> findHugeOutputButLittleIncrease(
			int averagePeriod) {
		ArrayList<String> ret = new ArrayList<String>();
		TreeMap<Double, String> treeMap = new TreeMap<Double, String>();
		ArrayList<String> buyList = new ArrayList<String>();
		ArrayList<String> sellList = new ArrayList<String>();
		for (String symbol : symbols) {
			try {
				if (!BasicUtility.getElement(dataMap, symbol, 0, 0).equals(
						"2016-09-28")
						|| !BasicUtility.getElement(dataMap, symbol, MaxOffset,
								0).equals("2016-07-25")
						|| symbol.startsWith("30"))
					continue;
				HashMap<Integer, String> mapAmount = BasicUtility
						.getElementMap(dataMap, symbol, 0, MaxOffset, 3);
				HashMap<Integer, Double> mapAmountDouble = new HashMap<Integer, Double>();
				HashMap<Integer, String> mapChange = BasicUtility
						.getElementMap(dataMap, symbol, 0, MaxOffset, 2);
				HashMap<Integer, String> mapTime = BasicUtility.getElementMap(
						dataMap, symbol, 0, MaxOffset, 0);
				for (int key1 : mapAmount.keySet()) {

					String str1 = mapAmount.get(key1);
					if (str1.startsWith("-")
							|| (key1 - averagePeriod) < MaxOffset)
						continue;
					double todayAmount = 0;
					if (str1.contains("万")) {
						int index = str1.indexOf("万");
						double db = Math.abs(Double.parseDouble(str1.substring(
								0, index)));
						todayAmount = db * 10000;
					} else if (str1.contains("亿")) {
						int index = str1.indexOf("亿");
						double db = Math.abs(Double.parseDouble(str1.substring(
								0, index)));
						todayAmount = db * 100000000;
					} else {
						double db = Math.abs(Double.parseDouble(str1));
						todayAmount = db;
					}
					double amountPrevious = 0;
					HashMap<Integer, String> amountMapPrevious = BasicUtility
							.getElementMap(dataMap, symbol, key1 - 1, key1
									- averagePeriod, 3);
					for (int key2 : amountMapPrevious.keySet()) {
						String str = amountMapPrevious.get(key2);
						double temp = 0;
						if (str.contains("万")) {
							int index = str.indexOf("万");
							double db = Math.abs(Double.parseDouble(str
									.substring(0, index)));
							temp = db * 10000;
						} else if (str.contains("亿")) {
							int index = str.indexOf("亿");
							double db = Math.abs(Double.parseDouble(str
									.substring(0, index)));
							temp = db * 100000000;
						} else {
							double db = Math.abs(Double.parseDouble(str));
							temp = db;
						}
						amountPrevious = temp + amountPrevious;
					}
					todayAmount = todayAmount / amountPrevious;
					mapAmountDouble.put(key1, todayAmount);
				}

				for (int key : mapAmountDouble.keySet()) {
					double amount = mapAmountDouble.get(key);
					double change = 10;
					try {
						change = Double.parseDouble(mapChange.get(key)
								.substring(0, mapChange.get(key).indexOf("%"))) + 10;
					} catch (Exception e) {
					}
					change = change / 10;
					treeMap.put(
							(amount / change),
							symbol + "\t" + mapChange.get(key) + "\t"
									+ mapTime.get(key) + "\t" + key);

				}
			} catch (Exception e) {
				System.out.println(symbol);
				System.exit(1);
			}
		}

		for (double key : treeMap.keySet()) {
			System.out.println(treeMap.get(key) + "\t" + key);
			if (treeMap.get(key).split("\t")[2].equals(targetDate)) {
				targets.put(key, treeMap.get(key));
			}
		}
		System.out.println();
		NavigableMap<Double, String> targetsNmap = targets.descendingMap();
		for (double key : targetsNmap.keySet()) {
			double change = 10;
			try {
				change = Double.parseDouble(targetsNmap.get(key).split("\t")[1]
						.substring(0, targetsNmap.get(key).split("\t")[1]
								.lastIndexOf("%")));
			} catch (Exception e) {
			}

			if (change > 3 || change < 2)
				continue;
			double currentPrice = Double.parseDouble(BasicUtility.getElement(
					dataMap, targetsNmap.get(key).split("\t")[0], -1, 1));
			System.out.println(key + "\t" + targetsNmap.get(key) + "\t"
					+ currentPrice + "\t\""
					+ targetsNmap.get(key).split("\t")[0] + "\",");
		}

		NavigableMap<Double, String> nmap = treeMap.descendingMap();
		int count = 0;
		int up = 0;
		double totalProfit = 0;
		HashMap<String, Integer> everydayCount = new HashMap<String, Integer>();
		System.out.println(nmap.size());
		// 当日流入进入流量表，并且当日涨跌幅在2%至3%之间接，则根据流量表的顺序，由高到低排列，第三日价格相对当日跌幅在-2%至1%之间，则第三日买入
		for (double key : nmap.keySet()) {
			// System.out.println(nmap.get(key)+"\t"+key);

			int offset = Integer.parseInt(nmap.get(key).split("\t")[3]);

			String symbol = nmap.get(key).split("\t")[0];
			// System.out.println(offset);
			int buyDay = offset + 2;
			int sellDay = offset + 5;
			if (sellDay > 0)
				continue;
			double change = 10;
			try {
				change = Double.parseDouble(nmap.get(key).split("\t")[1]
						.substring(0,
								nmap.get(key).split("\t")[1].lastIndexOf("%")));
			} catch (Exception e) {
			}

			if (change > 3 || change < 2)
				continue;
			double currentPrice = Double.parseDouble(BasicUtility.getElement(
					dataMap, symbol, offset, 1));
			double sellPrice = Double.parseDouble(BasicUtility.getElement(
					dataMap, symbol, sellDay, 1));
			double buyPrice = Double.parseDouble(BasicUtility.getElement(
					dataMap, symbol, buyDay, 1));
			double profit = sellPrice / buyPrice - 1.001;
			if ((buyPrice / currentPrice - 1) < -0.02
					|| (buyPrice / currentPrice - 1) > 0.01)
				continue;
			String sellDate = BasicUtility.getElement(dataMap, symbol, sellDay,
					0);
			String buyDate = BasicUtility
					.getElement(dataMap, symbol, buyDay, 0);

			if (everydayCount.containsKey(buyDate)) {
				int cc = everydayCount.get(buyDate);
				cc++;
				everydayCount.put(buyDate, cc);
			} else
				everydayCount.put(buyDate, 1);
			if (everydayCount.get(buyDate) > 1)
				continue;
			totalProfit = totalProfit + profit;
			if (sellPrice > buyPrice)
				up++;

			buyList.add(buyDate + "	" + symbolsRicequant.get(symbol));
			sellList.add(sellDate + "	" + symbolsRicequant.get(symbol));
			count++;
			// System.out.println(nmap.get(key)+"	"+profit);
			// if(count==1000)
			// break;
		}
		for (String str : buyList) {
			System.out.println(str);
		}
		System.out.println();
		for (String str : sellList) {
			System.out.println(str);
		}
		System.out.println(count);
		System.out.println(up);
		System.out.println((double) up / (double) count);
		System.out.println(totalProfit / count);
		return ret;
	}

	public static void main(String[] args) {
		DataLoader.load();
		// blackList.add("300441");
		dataMap = DataLoader.data;
		symbols = DataLoader.symbols;
		symbolsRicequant = DataLoader.symbolsRicequant;

		findHugeOutputButLittleIncrease(3);
		/*
		 * 
		 * for(int i = 0;i>= MaxOffset;i--) continuousIncomeStockList( i,
		 * 4,4,3,1); System.out.println("Tong hao "+tongHao);
		 * System.out.println("Yi hao "+yiHao);
		 * System.out.println("Future Up "+futureUp);
		 * System.out.println("Future Down "+futureDown); double profit =1; int
		 * count = 0; int success = 0; for(String today:orderMap.keySet()){
		 * System.out.print(today+" ");
		 * 
		 * Set<String> set = new HashSet<String>(orderMap.get(today).keySet());
		 * set.remove("0"); set.remove("1"); set.remove("2"); //set.remove("3");
		 * // set.remove("4"); for(String key:set){ System.out.print(key+" "+
		 * orderMap.get(today).get(key)+" "); profit = profit *(1+
		 * orderMap.get(today).get(key)); if(orderMap.get(today).get(key)>0)
		 * success++; count++; }
		 * System.out.println(orderMap.get(today).get("1")); }
		 * System.out.println(success); System.out.println(count);
		 * System.out.println((double)success/(double)count);
		 * System.out.println(orderMap.size()); System.out.println(profit);
		 */
		/*
		 * double max = Double.MIN_VALUE; String maxCon = ""; double min =
		 * Double.MAX_VALUE; String minCon = ""; double rate = 0; for(int total
		 * = 3;total<11;total++) for(int upday=0;upday<=total;upday++) for(int
		 * backAmount=1;backAmount<=5;backAmount++) {
		 * System.out.println(total+" "+upday+" "+backAmount); orderMap.clear();
		 * for(int i = 0;i>=MaxOffset;i--) continuousIncomeStockList( i,
		 * total,upday,backAmount,1); //System.out.println("Tong hao "+tongHao);
		 * //System.out.println("Yi hao "+yiHao);
		 * //System.out.println("Future Up "+futureUp);
		 * //System.out.println("Future Down "+futureDown); double profit =1;
		 * int count = 0; int success = 0; for(String today:orderMap.keySet()){
		 * // System.out.print(today+" ");
		 * 
		 * Set<String> set = new HashSet<String>(orderMap.get(today).keySet());
		 * set.remove("0"); set.remove("1"); set.remove("2"); for(String
		 * key:set){ // System.out.print(key+" "+
		 * orderMap.get(today).get(key)+" "); profit = profit *(1+
		 * orderMap.get(today).get(key)); if(orderMap.get(today).get(key)>0)
		 * success++; count++; } //
		 * System.out.println(orderMap.get(today).get("1")); } //
		 * System.out.println(count); // System.out.println(orderMap.size());
		 * System.out.println(profit);
		 * System.out.println((double)success/(double)count);
		 * if(((double)success/(double)count)>max){ max =
		 * (double)success/(double)count; maxCon =
		 * total+" "+upday+" "+backAmount; }
		 * 
		 * if(profit>max){ max = profit; maxCon =
		 * total+" "+upday+" "+backAmount; } if(profit<min){ min = profit;
		 * minCon = total+" "+upday+" "+backAmount; } }
		 * 
		 * System.out.println(maxCon+" "+max);
		 * System.out.println(minCon+" "+min);
		 */

		// System.out.print(BasicUtility.getAfterPriceInfo(dataMap, "600149",
		// -11));
		/*
		 * double profit = 0; for(int i=0;i<100;i++){ System.out.println(i);
		 * profit = profit + Double.parseDouble(continuousIncome(,6,1).get(0));
		 * } profit = profit/100; System.out.print(profit);
		 */
		/*
		 * double max = Double.MIN_VALUE; String maxCon = ""; double min =
		 * Double.MAX_VALUE; String minCon = ""; for(int total =
		 * 3;total<15;total++) for(int limit = 1;limit<=total;limit++) for(int
		 * hold = 1;hold<2;hold++){
		 * System.out.println(total+" "+limit+" "+hold); double profit = 0;
		 * for(int times = 0;times<20;times++){ List<String> list =
		 * continuousIncome(total,limit,hold); profit = profit +
		 * Double.parseDouble(list.get(0)); } profit = profit/20;
		 * if(profit>max){ max = profit; maxCon = total+" "+limit+" "+hold; }
		 * if(profit<min){ min = profit; minCon = total+" "+limit+" "+hold; } }
		 * System.out.println(maxCon+" "+max);
		 * System.out.println(minCon+" "+min);
		 */

	}
}
