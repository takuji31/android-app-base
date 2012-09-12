package com.github.takuji31.appbase.app;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseActivity<AppClass extends BaseApp> extends
		SherlockFragmentActivity {

	@SuppressWarnings("unchecked")
	public AppClass getMyApp() {
		return (AppClass) getApplication();
	}
}
