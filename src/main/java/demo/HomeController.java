package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.*;
import model.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.ClientCredential;

@Controller
public class HomeController {

	private String summaryURL = "https://api-beta.direct.id:444/v1/individuals";
	private String detailsURL = "https://api-beta.direct.id:444/v1/individual/";
	private String jsonIndividualDetails;
	private String jsonIndividualsSummary;

	private String reference = "5388f1dd436e46698007b79650679023";

	/**
	 * The default index controller, creates a new model to pass to the view
	 * @param model 
	 * @return link to the Index view
	 */
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("setup", new CredentialsModel());
		return "Index";
	}

	/**
	 * This method connects to Direct ID after going via the OAuth2 provider to get a valid token
	 * @param form
	 * @return
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/Connect", method = RequestMethod.POST)
	public ModelAndView connect(CredentialsModel form) throws Exception {
		trimFormDetails(form);

		jsonIndividualsSummary = getJson(form, summaryURL);
		jsonIndividualDetails = getJson(form, detailsURL + reference);

		String token = acquireUserSessionToken(new DefaultHttpClient(), form.getApi(), acquireOAuthAccessToken(form.getClientId(), form.getSecretKey(), form.getResourceId(), form.getAuthority()));

		ModelAndView modelAndView = new ModelAndView("Widget");
		modelAndView.addObject("widgetmodel", new WidgetModel(form.getFullCDNPath(), token));
		return modelAndView;
	}


	@RequestMapping("/IndividualsSummary")
	public ModelAndView individualsSummary() {
		ModelAndView modelAndView = new ModelAndView("IndividualsSummary");
		modelAndView.addObject("individualModel", populateIndividualsSummary(jsonIndividualsSummary));
		return modelAndView;
	}

	@RequestMapping("/IndividualDetails")
	public ModelAndView individualDetails() {
		ModelAndView modelAndView = new ModelAndView("IndividualDetails");
		modelAndView.addObject("individualModel", populateIndividualDetails(jsonIndividualDetails));
		return modelAndView;
	}

	private void trimFormDetails(CredentialsModel form) {
		form.setApi(form.getApi().trim());
		form.setAuthority(form.getAuthority().trim());
		form.setClientId(form.getClientId().trim());
		form.setFullCDNPath(form.getFullCDNPath().trim());
		form.setResourceId(form.getResourceId().trim());
		form.setSecretKey(form.getSecretKey().trim());	
	}

	/**
	 * Obtains an OAuth access token which can then be used to make authorized calls
	 *	to the Direct ID API.
	 * @param clientId
	 * @param clientSecret
	 * @param resourceId
	 * @param authority
	 * @return The returned value is expected to be included in the authentication header of subsequent API requests
	 * As the returned value authenticates the application, API calls made using
     * this value should only be made using server-side code
	 * @throws MalformedURLException
	 * @throws InterruptedException 
	 */
	public String acquireOAuthAccessToken(String clientId, String clientSecret, String resourceId, String authority) throws MalformedURLException, InterruptedException {
		DefaultAuthenticationCallbackHandler callback = new DefaultAuthenticationCallbackHandler();
		ExecutorService service = Executors.newFixedThreadPool(1);
		AuthenticationContext context = new AuthenticationContext(authority, true, service);
		context.acquireToken(resourceId, new ClientCredential(clientId, clientSecret), callback);
		waitForResultFor30Seconds(callback);
		return callback.getToken();
	}

	/**
	 * This is a simple sleep/wait method so the code is simple
	 * @param callback
	 * @throws InterruptedException 
	 */
	private void waitForResultFor30Seconds(DefaultAuthenticationCallbackHandler callback) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		boolean flag = false;
		while (!flag) {
			Thread.sleep(100);	
			if (callback.getToken() != null) {
				break;
			}
			if (System.currentTimeMillis() - startTime > 30000) {
				throw new RuntimeException("we have not been able to obtain a token within 3 seconds of trying");
			}
		}
	}

	/**
	 * Queries <paramref name="apiEndpoint"/> with an http request authorized with <paramref name="authenticationToken"/>.
	 * @param client
	 * @param api
	 * @param acquiredToken
	 * @return
	 */
	public String acquireUserSessionToken(DefaultHttpClient client, String api, String acquiredToken) {
		HttpGet request = new HttpGet(api);
		request.setHeader("Authorization", String.format("Bearer %s", acquiredToken));
		try {
			return extractTokenFromResponse(client.execute(request).getEntity().getContent());
		} catch (Exception e) {
			throw new RuntimeException("Unable to get the Api Token");
		}
	}

	/**
	 * Simple method to extract the api key from the response  using jackson
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws ClientProtocolException
	 */
	private String extractTokenFromResponse(InputStream stream) throws IOException, JsonParseException {
		ObjectMapper mapper = new ObjectMapper();
		TokenResult result = mapper.readValue(stream, TokenResult.class);
		return result.getToken();
	}

	/**
	 *	Getting Json using authorization token
	 */
	private String getJson(CredentialsModel credentials, String urlString) throws Exception {
		String authenticationToken = acquireOAuthAccessToken(credentials.getClientId(), credentials.getSecretKey(), credentials.getResourceId(), credentials.getAuthority());

		try {
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", String.format("Bearer %s", authenticationToken));

			InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());

			int read;
			char[] chars = new char[1024];
			StringBuffer stringBuffer = new StringBuffer();
			while ((read = streamReader.read(chars)) > 0)
			{
				stringBuffer.append(chars, 0, read);
			}
			return stringBuffer.toString();
		}	catch (Exception e) {
			throw new RuntimeException("Unable to get the Json string");
		}
	}

	/**
	* Parsing JSON string and populating a IndividualsSummary class using Gson
	*/
	private List<IndividualSummaryModel> populateIndividualsSummary(String json) {
		JsonElement jsonElement = new JsonParser().parse(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonArray jsonArray = jsonObject.getAsJsonArray("Individuals");

		List<IndividualSummaryModel> individuals = new ArrayList<>();

		for(int i = 0; i < jsonArray.size(); i++)
		{
			jsonObject = jsonArray.get(i).getAsJsonObject();
			String reference = jsonObject.get("Reference").toString();
			String timestamp = jsonObject.get("Timestamp").toString();
			String name = jsonObject.get("Name").toString();
			String emailAddress = jsonObject.get("EmailAddress").toString();
			String userID = jsonObject.get("UserID").toString();

			IndividualSummaryModel individual = new IndividualSummaryModel(reference, timestamp, name, emailAddress, userID);
			individuals.add(individual);
		}

		return individuals;
	}

	/**
	 * Parsing JSON string and populating a IndividualDetails class using Gson
	 */
	private IndividualDetailsModel populateIndividualDetails(String json) {
		JsonElement jsonElement = new JsonParser().parse(json);
		JsonObject jsonObject = jsonElement.getAsJsonObject();

		String reference = jsonObject.getAsJsonObject("Individual").get("Reference").toString();
		jsonObject = jsonObject.getAsJsonObject("Individual").getAsJsonObject("Global").getAsJsonObject("Bank").getAsJsonArray("Providers").get(0).getAsJsonObject();
		String provider = jsonObject.get("Provider").toString();

		JsonArray jsonArray = jsonObject.getAsJsonArray("Accounts");
		List<AccountDetails> accounts = new ArrayList<>();
		getAccounts(jsonArray, accounts);

		return new IndividualDetailsModel(reference, provider, accounts);
	}

	/**
	 * Populating a AccountDetails class using Gson
	 */
	private void getAccounts(JsonArray jsonArray, List<AccountDetails> accounts) {
		JsonObject jsonObject;
		for(int i = 0; i < jsonArray.size(); i++)
		{
			jsonObject = jsonArray.get(i).getAsJsonObject();
			String accountName = jsonObject.get("AccountName").toString();
			String accountHolder = jsonObject.get("AccountHolder").toString();
			String accountType = jsonObject.get("AccountType").toString();
			String activityAvailableFrom = jsonObject.get("ActivityAvailableFrom").toString();
			String accountNumber = jsonObject.get("AccountNumber").toString();
			String sortCode = jsonObject.get("SortCode").toString();
			String balance = jsonObject.get("Balance").toString();
			String balanceFormatted = jsonObject.get("BalanceFormatted").toString();
			String currencyCode = jsonObject.get("CurrencyCode").toString();
			String verifiedOn = jsonObject.get("VerifiedOn").toString();

			JsonArray array = jsonObject.getAsJsonArray("Transactions");
			List<Transaction> transactions = new ArrayList<>();

			for (int j = 0; j < array.size(); j++)
			{
				JsonObject object = array.get(j).getAsJsonObject();
				String date = object.get("Date").toString();
				String description = object.get("Description").toString();
				String amount = object.get("Amount").toString();
				String type = object.get("Type").toString();
				transactions.add(new Transaction(date, description, amount, type));
			}

			AccountDetails accountDetails = new AccountDetails(accountName, accountHolder, accountType, activityAvailableFrom, accountNumber, sortCode, balance, balanceFormatted, transactions, currencyCode, verifiedOn);
			accounts.add(accountDetails);
		}
	}
}