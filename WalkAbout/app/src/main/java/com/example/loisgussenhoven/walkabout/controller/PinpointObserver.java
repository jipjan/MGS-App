package com.example.loisgussenhoven.walkabout.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CoenB95
 */

public class PinpointObserver {

	private static PinpointObserver observer;

	private List<OnNearbyPinpointListener> nearbyListeners;
	private List<OnSkipPinpointListener> skipListeners;

	private PinpointObserver() {
		nearbyListeners = new ArrayList<>();
		skipListeners = new ArrayList<>();
	}

	private static PinpointObserver getInstance() {
		if (observer == null)
			observer = new PinpointObserver();
		return observer;
	}

	public static void addOnNearbyPinpointListener(OnNearbyPinpointListener listener) {
		getInstance().nearbyListeners.add(listener);
	}

	public static void addOnSkipPinpointListener(OnSkipPinpointListener listener) {
		getInstance().skipListeners.add(listener);
	}

	public static void notifyNearbyPinpoint(String pinpointId) {
		if (observer != null)
			for (OnNearbyPinpointListener l : observer.nearbyListeners)
				l.onNearbyPinpoint(pinpointId);
	}

	public static void notifyPinpointSkipped(String pinpointId) {
		if (observer != null)
			for (OnSkipPinpointListener l : observer.skipListeners)
				l.onSkipPinpoint(pinpointId);
	}

	public static void removeOnNearbyPinpointListener(OnNearbyPinpointListener listener) {
		getInstance().nearbyListeners.remove(listener);
	}

	public static void removeOnSkipPinpointListener(OnSkipPinpointListener listener) {
		getInstance().skipListeners.remove(listener);
	}

	@FunctionalInterface
	public interface OnNearbyPinpointListener {
		void onNearbyPinpoint(String pinpointId);
	}

	@FunctionalInterface
	public interface OnSkipPinpointListener {
		void onSkipPinpoint(String pinpointId);
	}
}
