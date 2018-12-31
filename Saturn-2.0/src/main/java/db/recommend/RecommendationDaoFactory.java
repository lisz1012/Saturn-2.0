package db.recommend;

public class RecommendationDaoFactory {
	private static RecommendationDao RRCOMMENDATION_DAO = new RecommendationDaoImpl();
	
	public static RecommendationDao get() {
		return RRCOMMENDATION_DAO;
	}
}
