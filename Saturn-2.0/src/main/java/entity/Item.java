package entity;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	private String id;
	private String name;
	private String url;
	private String imageUrl;
	private Set<String> categories;
	private Double distance;
	private double rating;
	private String address;
	
	public Item(ItemBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.url = builder.url;
		this.imageUrl = builder.imageUrl;
		this.categories = builder.categories;
		this.distance = builder.distance;
		this.rating = builder.rating;
		this.address = builder.address;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public Set<String> getCategories() {
		return categories;
	}
	public double getDistance() {
		return distance;
	}
	public double getRating() {
		return rating;
	}
	public String getAddress() {
		return address;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id)
			   		  .put("name", name)
			   		  .put("url", url)
			   		  .put("imageUrl", imageUrl)
			   		  .put("categories", categories)
			   		  .put("distance", distance)
			   		  .put("rating", rating)
			   		  .put("address", address);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	@Override
	public String toString() {
		return id + " - " + name + " - " + url + " - " + 
			   imageUrl + " - " + categories + " - " + 
				distance + " - " + rating + " - " + address;
	}
	
	public static class ItemBuilder {
		private String id;
		private String name;
		private String url;
		private String imageUrl;
		private Set<String> categories;
		private Double distance;
		private double rating;
		private String address;
		public ItemBuilder setId(String id) {
			this.id = id;
			return this;
		}
		public ItemBuilder setName(String name) {
			this.name = name;
			return this;
		}
		public ItemBuilder setUrl(String url) {
			this.url = url;
			return this;
		}
		public ItemBuilder setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}
		public ItemBuilder setCategories(Set<String> categories) {
			this.categories = categories;
			return this;
		}
		public ItemBuilder setDistance(Double distance) {
			this.distance = distance;
			return this;
		}
		public ItemBuilder setRating(double rating) {
			this.rating = rating;
			return this;
		}
		public ItemBuilder setAddress(String address) {
			this.address = address;
			return this;
		}
		
		public Item build() {
			return new Item(this);
		}
	}
}
