package model;

public class CredentialsModel {
	private String api;
	private String clientId;
	private String secretKey;
	private String resourceId;
	private String fullCDNPath;
	private String authority;

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getFullCDNPath() {
		return fullCDNPath;
	}

	public void setFullCDNPath(String version) {
		this.fullCDNPath = version;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
