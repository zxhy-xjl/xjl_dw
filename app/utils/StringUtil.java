package utils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */
public class StringUtil {

	public static final char UNDERLINE = '_';

	private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");

	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String getClassName(String table) {
		String tString = underlineToCamel(table);
		return tString.substring(0, 1).toUpperCase() + tString.substring(1);
	}

	public static String firstLowerCase(String param) {
		return param.substring(0, 1).toLowerCase() + param.substring(1);
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel2(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			// String.valueOf(Character.toUpperCase(sb.charAt(position)));
			sb.replace(position - 1, position + 1,
					sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

	public static Double getDouble(String value) {
		if (value == null || value.trim().length() == 0)
			return null;

		return Double.parseDouble(value);
	}
	public static Double getDouble(String value, Double defValue) {
		if (value == null || value.trim().length() == 0)
			return defValue;

		return Double.parseDouble(value);
	}
	
	public static Long getLong(String value) {
		if (value == null || value.trim().length() == 0)
			return null;

		return Long.parseLong(value);
	}

	public static Long getLong(String value, Long defValue) {
		if (value == null || value.trim().length() == 0) {
			return defValue;
		}
		return Long.parseLong(value);
	}

	public static Integer getInteger(String value) {
		if (value == null || value.trim().length() == 0)
			return null;

		return Integer.parseInt(value);
	}

	public static Integer getInteger(String value, Integer defValue) {
		if (value == null || value.trim().length() == 0) {
			return defValue;
		}
		return Integer.parseInt(value);
	}

	public static String urlEncode(String url) throws Exception {
		if (url == null)
			return null;
		return URLEncoder.encode(url, "utf-8").replaceAll("%2F", "/");
	}

	public static boolean eq(String s1, String s2) {
		return (s1 == null && s2 == null) || (s1 != null && s1.equals(s2));
	}

	static Pattern pattern = Pattern.compile("<img.*?src.*?=.*?\\\"(.*?)\\\"");

	public static String pickFirstImage(String content) {
		Matcher m = pattern.matcher(content);
		List<String> result = new ArrayList();
		while (m.find()) {
			result.add(m.group(1).replaceAll("\"|'", ""));
		}
		return result.size() > 0 ? result.get(0) : null;
	}

	public static boolean isSet(String value) {
		return value != null && value.trim().length() > 0;
	}

	public static boolean isEmpty(String value) {
		return value == null || "null".equalsIgnoreCase(value)
				|| value.trim().length() == 0;
	}

	public static boolean isNotEmpty(String value) {
		return value != null && !"null".equalsIgnoreCase(value)
				&& value.trim().length() > 0;
	}
	public static boolean isNotEmpty(Object value) {
		return value != null && !"null".equalsIgnoreCase(value.toString())
				&& value.toString().trim().length() > 0;
	}

	public static boolean inArray(String[] array, String str) {
		if (array == null || str == null)
			return false;
		for (String t : array) {
			if (str.equals(t))
				return true;
		}
		return false;
	}

	public static Pattern bw = null;

	public static String doFilter(String str) {
		if (bw != null && !"".equals(str.trim())) {
			Matcher m = bw.matcher(str);
			str = m.replaceAll("*");
		}
		return str;
	}

	public static String ifEmptySetDefault(String value, String setValue) {

		if ("".equals(value) || null == value || "null".equals(value)) {
			return setValue;
		} else {
			return value;
		}
	}

	public static String maskPhoneNumber(String phone) {
		if (phone == null)
			phone = "";
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		if (m.matches()) {
			phone = phone.substring(0, 3) + "****" + phone.substring(7);
		}
		return phone;
	}

	public static String getSubStrBySep(String str, String sep) {

		return str.substring(0, str.indexOf(sep));

	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().toUpperCase();
	}

	public static String padLeft(String s, int totalWidth, char paddingChar) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < totalWidth - s.length(); i++) {
			sb.append(paddingChar);
		}
		sb.append(s);
		return sb.toString();
	}

	public static String padRight(String s, int totalWidth, char paddingChar) {
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		for (int i = 0; i < totalWidth - s.length(); i++) {
			sb.append(paddingChar);
		}
		return sb.toString();
	}

	public static boolean isNumeric(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = numericPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	public static boolean isNotNumeric(String src) {
		return !isNumeric(src);
	}

	public static void main(String[] args) {
		System.out.println(StringUtil.doFilter("洗"));
		System.out.println(getSubStrBySep("aa.png", "."));

		System.out.println(StringUtil.underlineToCamel2("PROJECT_ID"));

		System.out.println(StringUtil.camelToUnderline("projectId"));

	}
}