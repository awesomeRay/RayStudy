package utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Json 操作相关
 * 
 * @author jiangxu
 * @author kevin
 * 
 */
public class JsonUtil {
	public static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static final ThreadLocal<ObjectMapper> OBJECT_MAPPER_CACHE = new ThreadLocal<ObjectMapper>();

	/** 默认 json 值 */
	public static final String DEFAULT_JSON_VALUE = "{}";

	/** 返回指定对象的 json 内容. 当 obj 为 null 或出现异常时返回 "{}" */
	public static String generateJsonWithDefaultValue(Object obj) {
		return toJson(obj, DEFAULT_JSON_VALUE);
	}

	/**
	 * 返回指定对象的 json 内容
	 * 
	 * @param obj
	 *            要转换json内容的对象
	 * @param defaultValue
	 *            当 obj 为 null 或出现异常时返回的值
	 */
	public static String toJson(Object obj, String defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("Fail to convert object to json.", e);
			return defaultValue;
		}
	}

	/**
	 * 返回指定对象的 json 内容
	 * 
	 * @param obj
	 *            要转换json内容的对象
	 * @param defaultValue
	 *            当 obj 为 null 或出现异常时返回的值
	 * @see #toJsonString(Object, String)
	 */
	@Deprecated
	public static String generateJson(Object obj, String defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("Fail to convert object to json.", e);
			return defaultValue;
		}
	}

	/**
	 * 返回指定对象的 json 内容
	 * 
	 * @param obj
	 *            要处理的对象
	 * @return 对象转换的字符串，如果转换过程发生异常，包装成 RuntimeException 抛出
	 */
	public static String toJsonString(Object obj) {
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("Fail to convert object to json.", e);
		}
	}

	/**
	 * 以byte[]返回指定对象的 json 内容
	 * 
	 * @param obj
	 *            要处理的对象
	 * @return 对象转换的字符串，如果转换过程发生异常，包装成 RuntimeException 抛出
	 */
	public static byte[] toJsonByte(Object obj) {
		try {
			return getObjectMapper().writeValueAsBytes(obj);
		} catch (Exception e) {
			throw new RuntimeException("Fail to convert object to json.", e);
		}
	}

	/**
	 * 返回指定对象的 json 内容 <br>
	 * 
	 * @param obj
	 *            要处理的对象
	 * @return 对象转换的字符串，如果转换过程发生异常，包装成 RuntimeException 抛出
	 * @see JsonUtil#toJson(Object)
	 */
	@Deprecated
	public static String generateJson(Object obj) {
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("Fail to convert object to json.", e);
		}
	}

	/**
	 * 从 json 字符串组装对象
	 * 
	 * @param content
	 *            要组装成对象的 json 字符串
	 * @param valueType
	 *            目标对象的类型
	 * @return 返回组装的对象，如果 content 为 null 或空字符串，返回 null，如果转换过程发生异常，包装成
	 *         RuntimeException 抛出
	 */
	public static <T> T fromJson(String content, Class<T> valueType) {
		if (StringUtils.isBlank(content)) {
			return null;
		}

		try {
			return getObjectMapper().readValue(content, valueType);
		} catch (Exception e) {
			throw new RuntimeException("Fail to parse json string. content:" + content, e);
		}
	}

	/**
	 * 从 json 字符串组装对象
	 * 
	 * @param content
	 *            要组装成对象的 json 字符串
	 * @param valueType
	 *            目标对象的类型
	 * @return 返回组装的对象，如果 content 为 null 或空字符串，返回 null，如果转换过程发生异常，包装成
	 *         RuntimeException 抛出
	 */
	public static <T> T fromJson(byte[] content, Class<T> valueType) {
		if (content == null || content.length == 0) {
			return null;
		}

		try {
			return getObjectMapper().readValue(content, valueType);
		} catch (Exception e) {
			throw new RuntimeException("Fail to parse json string. content:" + content, e);
		}
	}

	/**
	 * 从 json 字符串组装对象
	 * 
	 * @param content
	 *            要组装成对象的 json 字符串
	 * @param valueType
	 *            目标对象的类型
	 * @return 返回组装的对象，如果 content 为 null 或空字符串，返回 null，如果转换过程发生异常，包装成
	 *         RuntimeException 抛出
	 * @see JsonUtil#fromJson(String, Class)
	 */
	@Deprecated
	public static <T> T readObjectFromJson(String content, Class<T> valueType) {
		if (StringUtils.isBlank(content)) {
			return null;
		}

		try {
			return getObjectMapper().readValue(content, valueType);
		} catch (Exception e) {
			throw new RuntimeException("Fail to parse json string. content:" + content, e);
		}
	}

	private static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = OBJECT_MAPPER_CACHE.get();
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			OBJECT_MAPPER_CACHE.set(objectMapper);
		}
		// objectMapper.enableDefaultTyping(); 
		return objectMapper;
	}
}
