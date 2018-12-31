package db.recommend;

import java.util.Set;


public interface RecommendationDao {

	Set<String> getRecommendedCategories(String userId);

}
