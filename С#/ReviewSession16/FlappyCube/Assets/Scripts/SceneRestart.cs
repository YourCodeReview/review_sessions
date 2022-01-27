using UnityEngine;
using UnityEngine.SceneManagement;

namespace FlappyCube {
	public class SceneRestart: MonoBehaviour {
		public void Restart() {
			var index = SceneManager.GetActiveScene().buildIndex;
			SceneManager.LoadScene(index);
		}
	}
}