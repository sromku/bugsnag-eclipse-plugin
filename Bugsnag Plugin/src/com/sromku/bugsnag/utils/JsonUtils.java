package com.sromku.bugsnag.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {

	private static Gson create() {
		return new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.setDateFormat("YYYY-MM-DDTHH:MM:SSZ")
			.create();
	}

	/**
	 * Parse Object to String in JSON format
	 * 
	 * @param obj
	 * @return String in JSON format
	 */
	public static String toJson(Object obj) {
		Gson gson = create();
		return gson.toJson(obj);
	}

	/**
	 * Get JSON string and convert to T (Object) you need
	 * 
	 * @param json
	 * @return Object filled with JSON string data
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		if (json == null || json.equals("")) {
			return null;
		}

		Gson gson = create();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);

		// check if the Class type is array but the Json is an object
		if (cls != null && cls.isArray() && element instanceof JsonArray == false) {
			JsonArray jsonArray = new JsonArray();
			jsonArray.add(element);

			Type listType = new TypeToken<T>() {
			}.getType();
			return gson.fromJson(jsonArray, listType);
		}

		try {
			return gson.fromJson(json, cls);
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get JSON string and convert to T (Object) you need
	 * 
	 * @param json
	 * @return Object filled with JSON string data
	 */
	public static <T> T fromJson(String json, Type type) {
		Gson gson = create();

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);

		return gson.fromJson(element, type);
	}

}
