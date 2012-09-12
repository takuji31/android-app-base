package com.github.takuji31.appbase.app;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class BaseDialogFragment<AppClass extends BaseApp, ActivityClass extends BaseActivity<AppClass>>
		extends SherlockDialogFragment {

	public AppClass getMyApp() {
		return getMyActivity().getMyApp();
	}

	@SuppressWarnings("unchecked")
	public ActivityClass getMyActivity() {
		return (ActivityClass) getActivity();
	}
}
