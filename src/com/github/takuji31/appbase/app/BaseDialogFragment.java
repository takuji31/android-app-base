package com.github.takuji31.appbase.app;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class BaseDialogFragment<AppClass extends BaseApp, ActivityClass extends BaseActivity<AppClass>>
		extends SherlockDialogFragment {

	public AppClass app() {
		return activity().app();
	}

	@SuppressWarnings("unchecked")
	public ActivityClass activity() {
		return (ActivityClass) getActivity();
	}
}
