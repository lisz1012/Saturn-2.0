package utils;

public enum HttpCode {
	OK(200), NO_AUTH(403), INTERNAL_ERROR(500);
	
	private int code;

	private HttpCode(int code) {
		this.code = code;
	}
	
	public int value() {
		return code;
	}
}
