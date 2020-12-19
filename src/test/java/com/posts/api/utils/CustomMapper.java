package com.posts.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomMapper {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static <T> List<T> toArrayList(JSONArray jsonArray, Class<T> clazz) {
		try {
			List<T> values = new ArrayList<>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				values.add(mapper.readValue(jsonObject.toString(), clazz));
			}

			return values;
		} catch (Exception e) {
			return null;
		}
	}

}
