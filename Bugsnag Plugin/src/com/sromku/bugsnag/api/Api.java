package com.sromku.bugsnag.api;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.sromku.bugsnag.model.Account;
import com.sromku.bugsnag.model.Comment;
import com.sromku.bugsnag.model.Event;
import com.sromku.bugsnag.model.Error;
import com.sromku.bugsnag.model.Project;
import com.sromku.bugsnag.utils.NetworkUtils;

public class Api {

	private static final String BASE_URL = "https://api.bugsnag.com/";
	private static Api instance = null;
	
	private Api() {
	}

	public static Api getInstance() {
		if (instance == null) {
			instance = new Api();
		}
		return instance;
	}

	public Account getAccount(String authToken) {
		String url = BASE_URL + "account/";
		Account account = NetworkUtils.get(authToken, url, Account.class);
		return account;
	}

	public List<Project> getProjects(String authToken, String accountId) {
		String url = BASE_URL + "account/" + accountId + "/projects/";
		Type type = new TypeToken<List<Project>>(){}.getType();
		return NetworkUtils.get(authToken, url, type);
	}
	
	public List<Error> getErrors(String authToken, String projectId) {
		String url = BASE_URL + "projects/" + projectId + "/errors/";
		Type type = new TypeToken<List<Error>>(){}.getType();
		return NetworkUtils.get(authToken, url, type);
	}
	
	public List<Event> getEvents(String authToken, String errorId) {
		String url = BASE_URL + "errors/" + errorId + "/events/";
		Type type = new TypeToken<List<Event>>(){}.getType();
		return NetworkUtils.get(authToken, url, type);
	}
	
	public List<Comment> getComments(String authToken, String errorId) {
		String url = BASE_URL + "errors/" + errorId + "/comments/";
		Type type = new TypeToken<List<Comment>>(){}.getType();
		return NetworkUtils.get(authToken, url, type);
	}

	public interface PageListener {
		void onPerform(int currentPage, int fromPages);
	}

}
