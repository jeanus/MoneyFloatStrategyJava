package apis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.TreeMap;

public class DataLoader {
	public static HashMap<String, HashMap<Integer, ArrayList<String>>> data;
	public static ArrayList<String> symbols;
	public static HashMap<String, String> symbolsRicequant;

	public static void load() {
		try {
			data = new HashMap<String, HashMap<Integer, ArrayList<String>>>();
			symbols = new ArrayList<String>();
			symbolsRicequant = new HashMap<String, String>();
			BufferedReader br = FileOperation
					.openFile("E:\\Be_A_Quant\\MoneyFloatStrategy\\money_float.txt");
			String s;
			while ((s = br.readLine()) != null) {
				if (s.length() == 6) {
					HashMap<Integer, ArrayList<String>> map_1 = new HashMap<Integer, ArrayList<String>>();
					boolean duplicated = false;
					HashSet<String> dateSet = new HashSet<String>();
					int offset = 0;
					while (true) {

						String s_1;
						s_1 = br.readLine();
						try {
							if (s_1.trim().length() == 0)
								break;
						} catch (Exception e) {
							System.out.println(s);
							System.exit(0);
						}
						ArrayList<String> list = new ArrayList<String>();
						String arr[] = s_1.split("	");
						for (int i = 1; i < arr.length; i++) {
							list.add(arr[i].trim());
							if (i == 1) {
								if (dateSet.contains(arr[i].trim()))
									duplicated = true;
								else
									dateSet.add(arr[i].trim());
							}
						}
						map_1.put(offset, list);
						offset--;
					}
					if (duplicated)
						continue;
					data.put(s, map_1);
					symbols.add(s);
				}

			}
			br.close();

			br = FileOperation
					.openFile("E:\\Be_A_Quant\\MoneyFloatStrategy\\stocks_ricequant.txt");
			while ((s = br.readLine()) != null) {
				symbolsRicequant.put(s.trim().substring(0, 6), s.trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateData() {
		try {
			String originalData = "E:\\Be_A_Quant\\MoneyFloatStrategy\\money_float.txt";
			String updateData = "E:\\Be_A_Quant\\MoneyFloatStrategy\\money_float_1028.txt";
			String newData = "E:\\Be_A_Quant\\MoneyFloatStrategy\\money_float_new.txt";

			BufferedReader originalDataBr = FileOperation
					.openFile(originalData);
			
			BufferedReader updateDataBr = new BufferedReader(new InputStreamReader(
					new FileInputStream(updateData), "UTF-8")); 
			BufferedWriter newDataBw = FileOperation.writeFile(newData);
			HashMap<String, TreeMap<Integer, String>> dataFull = new HashMap<String, TreeMap<Integer, String>>();

			String s;
			while ((s = originalDataBr.readLine()) != null) {
				if (s.length() == 6) {
					TreeMap<Integer, String> map_1 = new TreeMap<Integer, String>();
					int offset = -1;
					while (true) {
						String s_1;
						s_1 = originalDataBr.readLine();
						try {
							if (s_1.trim().length() == 0)
								break;
						} catch (Exception e) {
							System.out.println(s);
							System.exit(0);
						}
						String str = s_1.substring(s_1.indexOf("	") + 1);
						map_1.put(offset, str);
						offset--;
					}
					//if (map_1.size() == 49) {
						dataFull.put(s, map_1);
					//} else {
					//	System.out.println("e "+s);
					//}
				}
			}

			while ((s = updateDataBr.readLine()) != null) {
				if (s.length() == 6 && !s.equals("000033")) {
					String s_1 = updateDataBr.readLine();
					TreeMap<Integer, String> map_1 = new TreeMap<Integer, String>();
					if (dataFull.containsKey(s))
						map_1 = dataFull.get(s);
					if (s_1.trim().length() == 0)
						break;
					String str_1 = s_1.substring(s_1.indexOf("	") + 1);
					map_1.put(0, str_1);

					/*
					 * String s_2 = updateDataBr.readLine(); TreeMap<Integer,
					 * String> map_2 = new TreeMap<Integer, String>(); if
					 * (dataFull.containsKey(s)) map_2 = dataFull.get(s); if
					 * (s_2.trim().length() == 0) break; String str_2 =
					 * s_2.substring(s_2.indexOf("	") + 1); map_2.put(-1,
					 * str_2);
					 */
					/*
					 * String s_3 = updateDataBr.readLine(); TreeMap<Integer,
					 * String> map_3 = new TreeMap<Integer, String>(); if
					 * (dataFull.containsKey(s)) map_3 = dataFull.get(s); if
					 * (s_3.trim().length() == 0) break; String str_3 =
					 * s_3.substring(s_3.indexOf("	") + 1); map_3.put(-2,
					 * str_3);
					 */

					updateDataBr.readLine();
				}
			}

			for (String symbol : dataFull.keySet()) {
				newDataBw.write(symbol);
				newDataBw.newLine();

				TreeMap<Integer, String> map_1 = dataFull.get(symbol);
				NavigableMap<Integer, String> nmap = map_1.descendingMap();

				for (int offset : nmap.keySet()) {
					String line = map_1.get(offset);
					newDataBw.write(offset + "	" + line.trim());
					newDataBw.newLine();
				}
				newDataBw.newLine();
				newDataBw.flush();
			}
			originalDataBr.close();
			System.out.println(dataFull.size());
			updateDataBr.close();
			newDataBw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String processElement(String element) {
		String ans = element;
		if (element.contains("%")) {
			ans = element.replace("%", "");
			return ans;
		}

		if (element.contains("万")) {
			ans = element.replace("万", "");
			return ans;
		}
		if (element.contains("亿")) {
			ans = new BigDecimal(10000.0 * (Double.parseDouble(element.replace(
					"亿", "")))).toString();
			return ans;
		}
		try {

			ans = new BigDecimal(Double.parseDouble(element) / 10000).setScale(
					4, BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception e) {
			ans = "0.0000";
		}

		return ans;
	}

	static void transformData() throws Exception {
		HashMap<String, HashSet<String>> output = new HashMap<String, HashSet<String>>();
		String updateData = "E:\\Be_A_Quant\\MoneyFloatStrategy\\money_float_1028.txt";
		BufferedReader updateDataBr = new BufferedReader(new InputStreamReader(
				new FileInputStream(updateData), "UTF-8"));
		String firstLine = "		最新				主力		超大单							大单							中单							小单					";
		String secondLine = "代码	名称	交易日期	收盘价(元)	涨跌幅(%)	成交金额(万元)	净流入额(万元)	净流入率(%)	流入额(万元)	流入量(万股)	流出额(万元)	流出量(万股)	净流入额(万元)	净流入量(万股)	净流入率(%)	流入额(万元)	流入量(万股)	流出额(万元)	流出量(万股)	净流入额(万元)	净流入量(万股)	净流入率(%)	流入额(万元)	流入量(万股)	流出额(万元)	流出量(万股)	净流入额(万元)	净流入量(万股)	净流入率(%)	流入额(万元)	流入量(万股)	流出额(万元)	流出量(万股)	净流入额(万元)	净流入量(万股)	净流入率(%)";
		String s = updateDataBr.readLine().trim();
		HashSet<Integer> matchedKeys = new HashSet<Integer>() {
			{
				add(4);
				add(6);
				add(7);
				add(12);
				add(14);
				add(19);
				add(21);
				add(26);
				add(28);
				add(33);
				add(35);
			}
		};

		while (s != null) {
			if (s.length() == 6) {
				String symbol = s.startsWith("6") ? (s + ".SH") : (s + ".SZ");
				// if(symbol.equals("000002.SZ"))
				System.out.println(symbol);
				s = updateDataBr.readLine().trim();
				while (s != null && s.length() > 0) {
					String[] arr = s.split("\t");
					String date = arr[1].trim();
					HashSet<String> set = new HashSet<String>();
					if (output.containsKey(date))
						set = output.get(date);
					String line = symbol + "\t" + "name" + "\t" + date + "\t"
							+ arr[2].trim();
					for (int i = 4, index = 3; i < 36; i++) {
						try {
							if (matchedKeys.contains(i))
								line = line + "\t"
										+ processElement(arr[index++].trim());
							else
								line = line + "\t" + "unknown";
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("error " + symbol);
							for (String str : arr) {
								System.out.println(str);
							}
							System.exit(1);
						}
					}
					set.add(line);
					output.put(date, set);
					// if(symbol.equals("000002.SZ"))
					// System.out.println(line);
					s = updateDataBr.readLine();
					if (s != null)
						s = s.trim();
				}
			}
			s = updateDataBr.readLine();
			if (s != null)
				s = s.trim();
		}

		for (String date : output.keySet()) {
			String fileName = date + ".txt";
			String newData = "E:\\Be_A_Quant\\MoneyFloatStrategy\\Temp\\"
					+ fileName;
			BufferedWriter newDataBw = FileOperation.writeFile(newData);
			HashSet<String> content = output.get(date);
			newDataBw.write(firstLine);
			newDataBw.newLine();
			newDataBw.write(secondLine);
			newDataBw.newLine();
			for (String line : content) {
				newDataBw.write(line);
				newDataBw.newLine();
			}
			newDataBw.flush();
			newDataBw.close();
		}
	}

	public static void main(String[] args) throws Exception {
		updateData();
//		transformData();
	}

}
