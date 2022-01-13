using System;
using UnityEngine;

namespace FlappyCube {
	public class WallPassHandler: MonoBehaviour {
		public event Action Passed;

		protected void OnTriggerEnter2D(Collider2D collision) {
			if (collision.transform.TryGetComponent<WallHole>(out var hole)) {
				Passed?.Invoke();
			}
		}
	}
}