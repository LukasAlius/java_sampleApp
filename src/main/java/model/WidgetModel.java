package model;

public class WidgetModel {

	private String fullCDNPath;
	private String apiToken;

	public WidgetModel(String version, String apiToken) {
		this.fullCDNPath = version;
		this.apiToken = apiToken;
	}

	public String getFullCDNPath() {
		return fullCDNPath;
	}

	public void setFullCDNPath(String version) {
		this.fullCDNPath = version;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
}

