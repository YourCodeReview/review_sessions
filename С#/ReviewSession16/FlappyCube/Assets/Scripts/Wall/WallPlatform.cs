using UnityEngine;

namespace FlappyCube {
	[RequireComponent(typeof(SpriteRenderer))]
	[RequireComponent(typeof(BoxCollider2D))]
	public class WallPlatform: MonoBehaviour {
		public void Init(float height) {
			var renderer = GetComponent<SpriteRenderer>();
			var collider = GetComponent<BoxCollider2D>();

			renderer.size = new Vector2(1, height);
			collider.size = new Vector2(1, height);
			collider.offset = new Vector2(0, height / 2);
		}
	}
}