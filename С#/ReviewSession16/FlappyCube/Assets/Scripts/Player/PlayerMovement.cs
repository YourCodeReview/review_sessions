using UnityEngine;

namespace FlappyCube {
	[RequireComponent(typeof(Rigidbody2D))]
	[RequireComponent(typeof(HitHandler))]
	public class PlayerMovement: MonoBehaviour {
		[SerializeField] private float _jumpForce = 5f;
		[SerializeField] private GameBehaviour _gameBehaviour;

		private Rigidbody2D _rigidbody;
		private HitHandler _hitHandler;

		protected void Awake() {
			_rigidbody = GetComponent<Rigidbody2D>();
			_hitHandler = GetComponent<HitHandler>();
			_rigidbody.bodyType = RigidbodyType2D.Static;
			_gameBehaviour.GameStarted += EnableMovement;
		}

		public void Jump() {
			_rigidbody.velocity = Vector2.up * _jumpForce;
		}
		private void EnableMovement() {
			_rigidbody.bodyType = RigidbodyType2D.Dynamic;
			_hitHandler.Hited += DisableMovement;
			_gameBehaviour.GameStarted -= EnableMovement;
		}
		private void DisableMovement() { 
			_rigidbody.bodyType = RigidbodyType2D.Static;
			_hitHandler.Hited -= DisableMovement;
			_gameBehaviour.GameStarted += EnableMovement;
		}

		protected void OnDestroy() {
			_hitHandler.Hited -= DisableMovement;
			_gameBehaviour.GameStarted -= EnableMovement;
		}
	}
}