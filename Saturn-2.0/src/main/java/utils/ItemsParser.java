package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import entity.Item.ItemBuilder;

public class ItemsParser {
	private static final String EMBEDDED = "_embedded";
	private static final String EVENTS = "events";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String URL = "url";
	private static final String DISTANCE = "distance";
	private static final String VENUES = "venues";
	private static final String ADDRESS = "address";
	private static final String LINE1 = "line1";
	private static final String LINE2 = "line2";
	private static final String LINE3 = "line3";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String STATE_CODE = "stateCode";
	private static final String COUNTRY = "country";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String IMAGES = "images";
	private static final String CLASSIFICATIONS = "classifications";
	private static final String SEGMENT = "segment";
	
	
	public static List<Item> parseToItemList(JSONObject rawObject) {
		List<Item> list = new ArrayList<>();
		try {
			if (!rawObject.isNull(EMBEDDED)) {
				JSONObject embedded = rawObject.getJSONObject(EMBEDDED);
				if (!embedded.isNull(EVENTS)) {
					JSONArray events = embedded.getJSONArray(EVENTS);
					for (int i = 0; i < events.length(); i++) {
						JSONObject event = events.getJSONObject(i);
						list.add(parseToItem(event));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static String getAddress(JSONObject event) throws JSONException {
		JSONObject embedded = event.getJSONObject(EMBEDDED);
		if (!embedded.isNull(VENUES)) {
			JSONArray venues = embedded.getJSONArray(VENUES);
			for (int i = 0; i < venues.length(); i++) {
				JSONObject venue = venues.getJSONObject(i);
				if (venue != null) {
					StringBuffer sb = new StringBuffer();
					if (!venue.isNull(ADDRESS)) {
						JSONObject address = venue.getJSONObject(ADDRESS);
						if (!address.isNull(LINE1)) {
							sb.append(address.get(LINE1));
						}
						if (!address.isNull(LINE2)) {
							sb.append(", \n");
							sb.append(address.get(LINE2));
						}
						if (!address.isNull(LINE3)) {
							sb.append(", \n");
							sb.append(address.get(LINE3));
						}
					}
					if (!venue.isNull(CITY)) {
						JSONObject city = venue.getJSONObject(CITY);
						if (!city.isNull(NAME)) {
							sb.append(", ");
							sb.append(city.getString(NAME));
						}
						
					}
					if (!venue.isNull(STATE)) {
						JSONObject state = venue.getJSONObject(STATE);
						if (!state.isNull(STATE_CODE)) {
							sb.append(" ");
							sb.append(state.get(STATE_CODE));
						}
						
					}
					if (!venue.isNull(COUNTRY)) {
						JSONObject country = venue.getJSONObject(COUNTRY);
						if (!country.isNull(COUNTRY_CODE)) {
							sb.append(" ");
							sb.append(country.get(COUNTRY_CODE));
						}
					}
					
					return sb.toString();
				}
				
			}
		}
		return "";
	}

	private static String getImageUrl(JSONObject event) throws JSONException {
		if (!event.isNull(IMAGES)) {
			JSONArray images = event.getJSONArray(IMAGES);
			for (int i = 0; i < images.length(); i++) {
				JSONObject image = images.getJSONObject(i);
				if (image != null && !image.isNull(URL)) {
					return image.getString(URL);
				}
			}
		}
		return "";
	}
	
	private static Set<String> getCategories(JSONObject event) throws JSONException {
		Set<String> categories = new HashSet<>();
		if (!event.isNull(CLASSIFICATIONS)) {
			JSONArray classifications = event.getJSONArray(CLASSIFICATIONS);
			for (int i = 0; i < classifications.length(); i++) {
				if (!classifications.getJSONObject(i).isNull(SEGMENT)) {
					JSONObject segment = classifications.getJSONObject(i).getJSONObject(SEGMENT);
					if (!segment.isNull(NAME)) {
						categories.add(segment.getString(NAME)); 
					}
				}
			}
		}
		return categories;
	}
	
	public static Item parseToItem(JSONObject event) {
		try {
			String id = null;
			if (!event.isNull(ID)) {
				id = event.getString(ID);
			}
			String name = null;
			if (!event.isNull(NAME)) {
				name = event.getString(NAME);
			}
			String url = null;
			if (!event.isNull(URL)) {
				url = event.getString(URL);
			}
			Double distance = null;
			if (!event.isNull(DISTANCE)) {
				distance = event.getDouble(DISTANCE);
			}
			String address = getAddress(event);
			String imageUrl = getImageUrl(event);
			Set<String> categories = getCategories(event);
			
			ItemBuilder builder = new ItemBuilder();
			builder.setId(id)
				   .setName(name)
				   .setUrl(url)
				   .setDistance(distance)
				   .setAddress(address)
				   .setImageUrl(imageUrl)
				   .setCategories(categories);
			
			return builder.build();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new ItemBuilder().build();
	}
}
