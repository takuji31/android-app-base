package com.github.takuji31.appbase;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseActivity<AppClass extends BaseApp> extends
		SherlockFragmentActivity {

	@SuppressWarnings("unchecked")
	public AppClass app() {
		return (AppClass) getApplication();
	}
}
