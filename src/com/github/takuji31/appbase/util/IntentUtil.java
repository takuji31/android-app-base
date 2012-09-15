package com.github.takuji31.appbase.util;
import android.content.Intent;
import android.net.Uri;

public class IntentUtil {
	public static Intent getOpenBrowserIntent(String urlString) {
		return getOpenBrowserIntent(Uri.parse(urlString));
	}

	public static Intent getOpenBrowserIntent(Uri uri) {
		return new Intent(Intent.ACTION_VIEW, uri);
	}

	public static Intent getTextShareIntent(String text) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, text);
		return Intent.createChooser(intent, null);
	}
}