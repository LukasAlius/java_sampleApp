package demo;

import com.microsoft.aad.adal4j.AuthenticationResult;

public class DefaultAuthenticationCallbackHandler implements com.microsoft.aad.adal4j.AuthenticationCallback {

	private String token;


	@Override
	public void onSuccess(AuthenticationResult result) {
		token = result.getAccessToken();
	}

	@Override
	public void onFailure(Throwable exc) {
		throw new RuntimeException("Unable to validate token", exc);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
