package external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import utils.GeoHash;
import utils.ItemsParser;

public class TicketMasterDaoImpl implements TicketMasterDao {
	private static final String DEFAULT_KEYWORD = "events";
	private static final int DEFAULT_RADIUS = 50;
	private String apikey;
	private String url;
	private String singleEventUrl;
	
	TicketMasterDaoImpl() {}
	
	TicketMasterDaoImpl(String url,String singleEventUrl, String apiKey) {
		this.url = url;
		this.apikey = apiKey;
		this.singleEventUrl = singleEventUrl;
	}

	
	
	@Override
	public List<Item> search(double lat, double lon, String keyword) {
		if (keyword == null) {
			keyword = DEFAULT_KEYWORD;
		}
		
		try {
			keyword = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String query = String.format("%s?apikey=%s&geoPoint=%s&keyword=%s&radius=%s", 
				url, apikey, GeoHash.encodeGeohash(lat, lon, 8), keyword, DEFAULT_RADIUS);
		
		try {
			HttpURLConnection connection = (HttpURLConnection)(new URL(query).openConnection());
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				return new ArrayList<Item>();
			}
			
			BufferedReader br = null;
			String line = null;
			StringBuffer sb = new StringBuffer();
			
			br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject object = new JSONObject(sb.toString());
			
			return ItemsParser.parseToItemList(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ArrayList<Item>();
	}
	
	@Override
	public List<Item> search(Set<String> itemIds) {
		List<Item> list = new ArrayList<>();
		for (String itemId : itemIds) {
			Item item = search(itemId);
			list.add(item);
		}
		return list;
	}
	
	
	
	private Item search(String itemId) {
		String query = String.format("%s%s?apikey=%s", 
				singleEventUrl, itemId, apikey);
		try {
			HttpURLConnection connection = (HttpURLConnection)(new URL(query).openConnection());
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				return new Item.ItemBuilder().build();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject object = new JSONObject(sb.toString());
			br.close();
			
			return ItemsParser.parseToItem(object);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		
		return new Item.ItemBuilder().build();
	}

	public static void main(String[] args) {
		TicketMasterDao ticketMasterDao = TicketDaoFactory.get();
		/*List<Item> list = ticketMasterDao.search(47.741090, -122.337570, null);
		for (Item item : list) {
			System.out.println(item.toJSONObject());
		}*/
		
	Set<String> itemIds = new HashSet<>();
		itemIds.add("vvG1HZ44U3YwaO");
		itemIds.add("vvG1HZ4fXzBeSr");
		itemIds.add("vvG1HZ4K0HqbLd");
		itemIds.add("vvG1HZ4p-Dgo9z");
		ticketMasterDao.search(itemIds);
		
		/*TicketMasterDao dao = TicketDaoFactory.get();
		Item item = ((TicketMasterDaoImpl)dao).search("vvG1HZ4p-Dgo9z");
		System.out.println(item);*/
	}

}
