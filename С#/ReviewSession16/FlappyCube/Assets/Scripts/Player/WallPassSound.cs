using UnityEngine;

namespace FlappyCube {
	public class WallPassSound: MonoBehaviour {
		[SerializeField] private WallPassHandler _wallPassHandler;
		[SerializeField] private AudioSource _audioSource;

		protected void Start() {
			_wallPassHandler.Passed += PlaySound;
		}

		private void PlaySound() => _audioSource.PlayOneShot(_audioSource.clip);

		protected void OnDestroy() {
			_wallPassHandler.Passed -= PlaySound;
		}
	}
}