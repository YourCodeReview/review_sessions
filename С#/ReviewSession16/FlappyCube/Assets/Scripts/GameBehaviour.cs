using System;
using UnityEngine;

namespace FlappyCube {
	public class GameBehaviour: MonoBehaviour {
		[SerializeField] private HitHandler _playerHitHandler;

		public event Action GameStarted;
		public event Action GameEnded;

		protected void Start() {
			_playerHitHandler.Hited += StopGame;
		}

		public void StartGame() {
			GameStarted?.Invoke();
		}

		private void StopGame() {
			GameEnded?.Invoke();
		}
		protected void OnDestroy() {
			_playerHitHandler.Hited -= StopGame;
		}
	}
}