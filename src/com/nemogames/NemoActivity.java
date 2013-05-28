package com.nemogames;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerNativeActivity;

public class NemoActivity extends UnityPlayerNativeActivity
{
	public enum NemoActivityEvent
	{
		OnBackPressed(1);
		
		int value;
		NemoActivityEvent(int val) { value = val; }
		public int getValue() { return value; }
	}
	
	private String		ListenerGameObject = "";
	private String		ListenerFunction = "";
	private boolean		inited = false;
	private Bundle		CurrentBundle;
	private ArrayList<NemoActivityListener>		ActivityListeners;
	
	private static NemoActivity		_instance;
	public static NemoActivity		instance() { return _instance; }
	
	private void	SendListenerEvent(NemoActivityEvent event)
	{
		if (!inited) return;
		JSONObject obj = new JSONObject();
		try {
			obj.put("eid", event.getValue());
		} catch (JSONException e) { e.printStackTrace(); } finally 
		{
			UnityPlayer.UnitySendMessage(ListenerGameObject, ListenerFunction, obj.toString());
		}
	}
	
	public void		init(String gameobject, String function)
	{
		this.ListenerGameObject = gameobject;
		this.ListenerFunction = function;
		this.inited = true;
	}
	
	public void		RegisterActivityListener(NemoActivityListener listener)
	{
		ActivityListeners.add(listener);
		listener.onRegistered(CurrentBundle);
	}
	
	//------------- Activity Implementations
	
	@Override
	protected void	onCreate(Bundle bundle)
	{
		_instance = this;
		CurrentBundle = bundle;
		ActivityListeners = new ArrayList<NemoActivityListener>();
		super.onCreate(bundle);
		
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onRegistered(CurrentBundle);
	}

	@Override
	protected void	onStart()
	{
		super.onStart();
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onStart();
	}

	@Override
	protected void	onStop()
	{
		super.onStop();
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onStop();
	}
	
	@Override
	protected void	onPause()
	{
		super.onPause();
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onPause();
	}
	
	@Override
	protected void	onResume()
	{
		super.onResume();
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onResume();
	}
	
	@Override
	protected void	onDestroy()
	{
		super.onDestroy();
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onDestroy();
	}
	
	@Override
	protected void	onRestart()
	{
		super.onRestart();
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onRestart();
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		for (int i = 0; i < ActivityListeners.size(); i++) ActivityListeners.get(i).onBackPressed();
		SendListenerEvent(NemoActivityEvent.OnBackPressed);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		for (int i = 0; i < ActivityListeners.size(); i++)
			ActivityListeners.get(i).onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
		for (int i = 0; i < ActivityListeners.size(); i++)
			ActivityListeners.get(i).onSaveInstanceState(outState);
	}
}
