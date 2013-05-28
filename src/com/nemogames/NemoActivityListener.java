package com.nemogames;

import android.content.Intent;
import android.os.Bundle;

public interface NemoActivityListener 
{
	public void		onRegistered(Bundle savedInstanceState);
	public void		onRestart();
	public void		onStart();
    public void 	onStop();
	public void		onPause();
	public void		onResume();
	public void		onDestroy();
	public void 	onBackPressed();
    public void 	onActivityResult(int requestCode, int resultCode, Intent data);
    public void 	onSaveInstanceState(Bundle outState);
    
}
