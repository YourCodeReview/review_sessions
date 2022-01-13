using UnityEngine;

namespace FlappyCube {
	public class WallMovement: MonoBehaviour {
		[SerializeField] private float _speed = 2;
		
		private GameBehaviour _gameBehaviour;

		public void Init(GameBehaviour gameBehaviour) {
			_gameBehaviour = gameBehaviour;
			_gameBehaviour.GameEnded += StopMovement;
		}
		protected void Update() {
			var movement = -Vector3.right * _speed * Time.deltaTime;
			transform.Translate(movement);
		}

		private void StopMovement() => Destroy(this);

		protected void OnDestroy() {
			_gameBehaviour.GameEnded -= StopMovement;
		}
	}
}