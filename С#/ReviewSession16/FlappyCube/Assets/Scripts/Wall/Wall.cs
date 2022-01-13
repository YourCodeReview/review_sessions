using UnityEngine;

namespace FlappyCube {
	public class Wall: MonoBehaviour {
		[SerializeField] private float _wallHeight = 10;
		[SerializeField] private float _holeHeight = 2;
		[Space]
		[SerializeField] private WallPlatform _bottomPlatform;
		[SerializeField] private WallPlatform _topPlatform;
		[SerializeField] private WallHole _hole;
		[SerializeField] private WallMovement _movement;
		[SerializeField] private WallDestroyer _destroyer;

		public void Init(GameBehaviour gameBehaviour, float holePosition, Transform _destroyPoint) {
			var bottomPlatformHeight = holePosition - _holeHeight / 2;
			var topPlatformHeight = _wallHeight - holePosition - _holeHeight / 2;

			_bottomPlatform.Init(bottomPlatformHeight);
			_topPlatform.Init(topPlatformHeight);
			_hole.Init(holePosition, _holeHeight);
			_movement.Init(gameBehaviour);
			_destroyer.Init(_destroyPoint);
		}
	}
}