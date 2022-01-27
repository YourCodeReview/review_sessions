using UnityEngine;

namespace FlappyCube {
	public class WallDestroyer: MonoBehaviour {
		private Transform _destroyPoint;

		public void Init(Transform destroyPoint) {
			_destroyPoint = destroyPoint;
		}
		protected void FixedUpdate() {
			if (_destroyPoint != null) {
				if (transform.position.x < _destroyPoint.position.x) {
					Destroy(gameObject);
				}
			}
		}
	}
}