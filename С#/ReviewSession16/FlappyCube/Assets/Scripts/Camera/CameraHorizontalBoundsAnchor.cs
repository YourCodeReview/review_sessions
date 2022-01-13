using UnityEngine;

namespace FlappyCube {
	[ExecuteAlways]
	public class CameraHorizontalBoundsAnchor: MonoBehaviour {
		[SerializeField] private BoundsSide _side;
		[SerializeField] private float _offset = 0;

		protected void Update() {
			var bounds = Camera.main.GetOrthographicBounds();
			var position = transform.position;
			position.x = _side switch {
				BoundsSide.Right => bounds.max.x,
				BoundsSide.Left => bounds.min.x,
				_ => 0
			};
			position.x += _offset;
			transform.position = position;
		}
	}
	public enum BoundsSide {
		Right, Left
	}
}