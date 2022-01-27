using System;
using UnityEngine;

namespace FlappyCube {
	public class UiPanel: MonoBehaviour {
		public event Action Showed;
		public event Action Hided;

		public void Show() {
			gameObject.SetActive(true);
			Showed?.Invoke();
		}
		public void Hide() {
			gameObject.SetActive(false);
			Hided?.Invoke();
		}
	}
}