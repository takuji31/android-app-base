/**
 *
 */
package com.github.takuji31.appbase.app;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * @author takuji
 * 
 */
public class BaseFragment<AppClass extends BaseApp, ActivityClass extends BaseActivity<AppClass>>
		extends SherlockFragment {

	@SuppressWarnings("unchecked")
	public ActivityClass getMyActivity() {
		return (ActivityClass) getActivity();
	}

	public AppClass getMyApp() {
		return getMyActivity().getMyApp();
	}
}
