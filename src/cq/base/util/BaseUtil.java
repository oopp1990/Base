package cq.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.hibernate.validator.util.GetClassLoader;

import cq.base.action.BaseAction;
import cq.base.annotation.Alias;
import cq.base.annotation.BB;
import cq.base.annotation.BD;
import cq.base.annotation.BS;
import cq.base.annotation.Ids;
import cq.base.dao.BaseDao;
import cq.base.entity.BaseBean;
import cq.base.entity.PageBean;
import cq.base.service.BaseService;

public class BaseUtil {

	public static int DOUBLE_LENGTH = 3;

	public static final Class[] BASETYPE = { String.class, Long.class,
			Integer.class, Character.class, Float.class, Boolean.class,
			Short.class, Byte.class, Double.class, short.class, int.class,
			long.class, float.class, double.class, boolean.class, char.class,
			byte.class };
	public static final String PATH = BaseUtil.class.getClassLoader()
			.getResource("").getFile();

	public static final String CHAR = "";

	private Map map;

	public BaseUtil() {
		map = new HashMap();
	}

	/**
	 * 复制对象的数据
	 * 
	 * @param srcObj
	 *            源对象
	 * @param destObj
	 *            目的对象
	 * @throws Exception
	 */
	public static void copyBean(Object srcObj, Object destObj, Class[] classes)
			throws Exception {

		if (!srcObj.getClass().equals(destObj.getClass())) {
			// throw new Exception("复制类型不一致");
		}
		List<Field> fields = new ArrayList<Field>();
		for (Field field : destObj.getClass().getDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : destObj.getClass().getFields()) {
			fields.add(field);
		}
		for (Field field : fields) {
			if (!isInClasses(field.getType(), classes)) {
				String setMethodName = "set"
						+ field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1);
				String getMethodName = "get"
						+ field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1);
				if (field.getType().equals(boolean.class)) {
					getMethodName = "is"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
				}
				try {
					Object obj = srcObj.getClass()
							.getMethod(getMethodName, new Class[] {})
							.invoke(srcObj, new Object[] {});
					destObj.getClass()
							.getMethod(setMethodName,
									new Class[] { field.getType() })
							.invoke(destObj, new Object[] { obj });
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 得到对象的泛型的类型
	 * 
	 * @param obj
	 * @return
	 */
	public static Class getTemplateClass(Class c) {
		ParameterizedType pt = (ParameterizedType) c.getGenericSuperclass();
		return (Class) pt.getActualTypeArguments()[0];
	}

	/**
	 * 得到实体类的别称
	 * 
	 * @param c
	 * @return
	 */
	public static String getEntityAlias(Class c) {
		Annotation[] annotations = c.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(Alias.class)) {
				return ((Alias) a).alias();
			}
		}
		return null;
	}

	/**
	 * 得到servive
	 * 
	 * @param c
	 * @return
	 */
	public static BaseService getBaseService(BaseAction baseAction) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : baseAction.getClassDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : baseAction.getClassFields()) {
			fields.add(field);
		}
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType().equals(BS.class)) {
					String getMethodName = "get"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					try {
						Object value = baseAction.getClass()
								.getMethod(getMethodName, new Class[] {})
								.invoke(baseAction, new Object[] {});
						return (BaseService) value;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 得到dao
	 * 
	 * @param c
	 * @return
	 */
	public static BaseDao getBaseDao(BaseService baseService) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : baseService.getClassDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : baseService.getClassFields()) {
			fields.add(field);
		}
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType().equals(BD.class)) {
					String getMethodName = "get"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					try {
						Object value = baseService.getClass()
								.getMethod(getMethodName, new Class[] {})
								.invoke(baseService, new Object[] {});
						return (BaseDao) value;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 得到bean
	 * 
	 * @param c
	 * @return
	 */
	public static BaseBean getBaseBean(BaseAction baseAction) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : baseAction.getClassDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : baseAction.getClassFields()) {
			fields.add(field);
		}
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType().equals(BB.class)) {
					String getMethodName = "get"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					try {
						Object value = baseAction.getClass()
								.getMethod(getMethodName, new Class[] {})
								.invoke(baseAction, new Object[] {});
						return (BaseBean) value;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 得到实体类的IDS
	 * 
	 * @param c
	 * @return
	 */
	public static Serializable[] getEntityIds(BaseBean baseBean) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : ((BaseBean) baseBean).getClass().getDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : ((BaseBean) baseBean).getClass().getFields()) {
			fields.add(field);
		}
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType().equals(Ids.class)) {
					String getMethodName = "get"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					try {
						Object value = baseBean.getClass()
								.getMethod(getMethodName, new Class[] {})
								.invoke(baseBean, new Object[] {});
						return (Serializable[]) value;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 得到实体类的主键
	 * 
	 * @param c
	 * @return
	 */
	public static Serializable getEntityId(BaseBean baseBean) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : ((BaseBean) baseBean).getClass().getDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : ((BaseBean) baseBean).getClass().getDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType().equals(Id.class)) {
					String getMethodName = "get"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					try {
						Object value = baseBean.getClass()
								.getMethod(getMethodName, new Class[] {})
								.invoke(baseBean, new Object[] {});
						return (Serializable) value;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 把对象转换成json格式数据
	 * 
	 * @param obj
	 * @return
	
	public String getPageBeanJsonStr(PageBean pageBean) {
		List baseList = pageBean.getDataList();
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("{");
		List<Field> fields = new ArrayList<Field>();
		for (Field field : pageBean.getClass().getDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : pageBean.getClass().getFields()) {
			fields.add(field);
		}
		for (Field field : fields) {
			String getMethodName = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			if (field.getType().equals(boolean.class)) {
				getMethodName = "is"
						+ field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1);
			}
			try {
				Object value = pageBean.getClass()
						.getMethod(getMethodName, new Class[] {})
						.invoke(pageBean, new Object[] {});

				if (isBaseType(field.getType()) || value == null) {
					jsonStr.append("\"" + field.getName() + "\"");
					jsonStr.append(":\"");
					jsonStr.append(value);
					jsonStr.append("\",");
				}

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// System.out.println("在"+pageBean.getClass().getName()+"里面找不到该方法:"+getMethodName);
			}
		}

		jsonStr.append("\"rows\":[");
		if (pageBean.isMapDataType()) {
			int i = 0;
			if (baseList != null) {
				for (Object obj : baseList) {
					Map map = (Map) obj;
					if (i > 0) {
						jsonStr.append(",");
					}
					jsonStr.append("{");
					Iterator it = map.keySet().iterator();
					int j = 0;
					while (it.hasNext()) {
						String key = (String) it.next();
						Object value = map.get(key);
						if (j > 0) {
							jsonStr.append(",");
						}
						if (value != null
								&& value.getClass().equals(String.class)) {
							jsonStr.append("\""
									+ key
									+ "\":\""
									+ (value + "").replaceAll("\n", "")
											.replaceAll("\t", "")
											.replaceAll("\"", "\'") + "\"");
						}
						// else
						// if(value!=null&&value.getClass().equals(Double.class)){
						// jsonStr.append("\""+key+"\":\""+getDoubleStr(Double.parseDouble(value+""))+"\"");
						// }
						else {
							jsonStr.append("\"" + key + "\":\"" + value + "\"");
						}
						j++;
					}
					jsonStr.append("}");
					i++;
				}
			}

		} else {
			int i = 0;
			if (baseList != null) {
				for (Object obj : baseList) {
					BaseBean baseBean = (BaseBean) obj;
					baseBean.setFields(pageBean.getFields());
					if (i > 0) {
						jsonStr.append(",");
					}
					String str = baseBean.toString();
					str = str.substring(0, str.length() - 1)
							+ ",\"primaryKey\":\"" + baseBean.getId() + "\"}";
					jsonStr.append(str);
					i++;
				}
			}
		}
		jsonStr.append("]");
		jsonStr.append("}");
		return jsonStr.toString();

	}
 */
	/**
	 * 把对象转换成json格式数据
	 * 
	 * @param obj
	 * @return
	 */
	public String getBaseBeanJsonStr(Object obj, String[] fieldNames) {
		if (map.get(obj.hashCode()) != null) {
			return map.get(obj.hashCode()).toString();
		}
		StringBuffer jsonStr = new StringBuffer();
		map.put(obj.hashCode(), "");
		// jsonStr.append(obj.getClass().getSimpleName());
		// jsonStr.append(":");
		jsonStr.append("{");
		jsonStr.append(CHAR);
		List<Field> fields = new ArrayList<Field>();
		for (Field field : obj.getClass().getDeclaredFields()) {
			fields.add(field);
		}
		for (Field field : obj.getClass().getFields()) {
			fields.add(field);
		}
		if (obj instanceof BaseBean && fieldNames != null) {
			fields = new ArrayList();
			for (Field field : ((BaseBean) obj).getClass().getDeclaredFields()) {
				fields.add(field);
			}
			for (Field field : ((BaseBean) obj).getClass().getFields()) {
				fields.add(field);
			}
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				boolean flag = true;
				for (String fieldName : fieldNames) {
					if (field.getName().equals(
							fieldName.indexOf(".") > 0 ? fieldName.substring(0,
									fieldName.indexOf(".")) : fieldName)) {
						flag = false;
					}
				}
				if (flag) {
					fields.remove(i);
					i--;
				}
			}
		}
		for (Field field : fields) {
			String getMethodName = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			if (field.getType().equals(boolean.class)) {
				getMethodName = "is"
						+ field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1);
				if (field.getName().indexOf("is") == 0) {
					getMethodName = field.getName();
				}
			}
			try {
				Object value = obj.getClass()
						.getMethod(getMethodName, new Class[] {})
						.invoke(obj, new Object[] {});

				if (field.getType().equals(String.class)) {
					jsonStr.append("\"" + field.getName() + "\"");
					jsonStr.append(":\"");
					jsonStr.append((value + "").replaceAll("\n", "")
							.replaceAll("\t", "").replaceAll("\"", "\'"));
					jsonStr.append("\",");
				}
				// else if(field.getType().equals(double.class)){
				// jsonStr.append("\""+field.getName()+"\"");
				// jsonStr.append(":\"");
				// jsonStr.append(getDoubleStr(Double.parseDouble(value+"")));
				// jsonStr.append("\",");
				// }
				else if (isBaseType(field.getType()) || value == null) {
					jsonStr.append("\"" + field.getName() + "\"");
					jsonStr.append(":\"");
					jsonStr.append(value);
					jsonStr.append("\",");

				} else if (value instanceof BaseBean) {
					List sonfieldNameList = new ArrayList();
					if (fieldNames != null) {
						for (String fieldName : fieldNames) {
							if (field.getName().equals(
									fieldName.indexOf(".") > 0 ? fieldName
											.substring(0,
													fieldName.indexOf("."))
											: fieldName)) {
								sonfieldNameList.add(fieldName
										.substring(fieldName.indexOf(".") + 1));
							}
						}
					}
					String[] sonfieldNames = new String[sonfieldNameList.size()];
					sonfieldNameList.toArray(sonfieldNames);
					if (fieldNames == null) {
						sonfieldNames = null;
					}
					String str = getBaseBeanJsonStr(value, sonfieldNames);
					if (str != null && str.length() > 0) {
						jsonStr.append("\"" + field.getName() + "\"");
						jsonStr.append(":");
						jsonStr.append(str);
						jsonStr.append(",");
					}
				} else if (field.getType().equals(java.util.Date.class)
						|| field.getType().equals(java.sql.Timestamp.class)) {
					jsonStr.append("\"" + field.getName() + "\"");
					jsonStr.append(":\"");
					jsonStr.append(getDateTimeStr((Date) value));
					jsonStr.append("\",");
				} else if (field.getType().equals(java.sql.Date.class)
						|| (field.getType().getSuperclass() != null && field
								.getType().getSuperclass()
								.equals(java.sql.Date.class))) {
					jsonStr.append("\"" + field.getName() + "\"");
					jsonStr.append(":\"");
					jsonStr.append(getDateStr((java.sql.Date) value));
					jsonStr.append("\",");
				} else if (field.getType().equals(java.sql.Time.class)
						|| (field.getType().getSuperclass() != null && field
								.getType().getSuperclass()
								.equals(java.sql.Time.class))) {
					jsonStr.append("\"" + field.getName() + "\"");
					jsonStr.append(":\"");
					jsonStr.append(getTimeStr((java.sql.Time) value));
					jsonStr.append("\",");
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// System.out.println("在"+obj.getClass().getName()+"里面找不到该方法:"+getMethodName);
			}
		}
		if (jsonStr.indexOf(",") > 0) {
			jsonStr.delete(jsonStr.lastIndexOf(","),
					jsonStr.lastIndexOf(",") + 1);
		}
		jsonStr.append(CHAR);
		jsonStr.append("}");
		map.put(obj.hashCode(), jsonStr.toString());
		return jsonStr.toString();
	}

	/**
	 * 验证某个类是否实现了某个接口
	 * 
	 * @param c
	 *            验证类
	 * @param i
	 *            接口类
	 * @return
	 */
	public static boolean isInterface(Class c, Class i) {
		for (Class cs : c.getInterfaces()) {
			if (cs.equals(i)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInClasses(Class c, Class[] classes) {
		if (c != null && classes != null) {
			for (Class cs : classes) {
				if (cs.equals(c)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isBaseType(Class c) {
		return isInClasses(c, BASETYPE);
	}

	public static Date getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String source = sdf.format(Calendar.getInstance().getTime());
		try {
			return sdf.parse(source);
		} catch (Exception e) {

		}
		return null;
	}

	public static Date getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String source = sdf.format(Calendar.getInstance().getTime());
		try {
			return sdf.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 得到时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTimeStr(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 得到时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date) {
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}

	/**
	 * 得到当前时间的字符串
	 * 
	 * @return
	 */
	public static String getDateTimeStr() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar
				.getInstance().getTime());
	}

	/**
	 * 得到当前时间的字符串
	 * 
	 * @return
	 */
	public static String getDateStr() {
		return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance()
				.getTime());
	}

	/**
	 * 得到当前时间的字符串
	 * 
	 * @return
	 */
	public static String getTimeStr() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance()
				.getTime());
	}

	/**
	 * 得到当前时间的字符串
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String source) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
	}

	/**
	 * 控制小数位显示
	 * 
	 * @param value
	 * @param length
	 * @return 字符串
	 */
	public static String getDoubleStr(Object value, int length) {
		String str = "#";
		int i = 1;
		while (i <= length) {
			if (str.length() == 1) {
				str += ".";
			}
			str += "#";
			i++;
		}
		DecimalFormat format = new DecimalFormat(str);
		String val = format.format(value);
		if (length > 0) {
			if (val.indexOf('.') < 0) {
				val += ".";
			}
			while (val.substring(val.indexOf('.')).length() <= length) {
				val += "0";
			}
		}
		return val;
	}

	/**
	 * 控制小数位显示
	 * 
	 * @param value
	 * @return 字符串
	 */
	public static String getDoubleStr(Object value) {
		return getDoubleStr(value, DOUBLE_LENGTH);
	}

	/**
	 * 得到当前日期
	 * 
	 * @return
	 */
	public static java.sql.Date getNowWithSqlDate() {
		return new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	}

	/**
	 * 去除字符串的前面的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String firstTrim(String str) {
		if (str.indexOf(" ") == 0) {
			return firstTrim(str.substring(str.indexOf(" ") + 1));
		} else {
			return str;
		}
	}

	public static int getIndex(Object str, Object[] strs) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(str)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 读取文件
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public static void writeFile(InputStream is, OutputStream os)
			throws IOException {
		byte[] frame = new byte[1024 * 1024];
		int i = 0;
		while ((i = is.read(frame)) != -1) {
			os.write(frame, 0, i);
		}
	}

}
