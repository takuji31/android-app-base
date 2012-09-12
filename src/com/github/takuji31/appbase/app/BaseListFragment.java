package com.github.takuji31.appbase.app;

import com.actionbarsherlock.app.SherlockListFragment;

public class BaseListFragment<AppClass extends BaseApp, ActivityClass extends BaseActivity<AppClass>>
		extends SherlockListFragment {

	public AppClass getMyApp() {
		return getMyActivity().getMyApp();
	}

	@SuppressWarnings("unchecked")
	public ActivityClass getMyActivity() {
		return (ActivityClass) getActivity();
	}
}
