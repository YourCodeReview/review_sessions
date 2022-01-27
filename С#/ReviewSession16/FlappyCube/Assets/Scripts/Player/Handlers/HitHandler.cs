using System;
using UnityEngine;

namespace FlappyCube {
	public class HitHandler: MonoBehaviour {
		public event Action Hited;

		protected void OnCollisionEnter2D(Collision2D collision) {
			Hited?.Invoke();
		}
	}
}