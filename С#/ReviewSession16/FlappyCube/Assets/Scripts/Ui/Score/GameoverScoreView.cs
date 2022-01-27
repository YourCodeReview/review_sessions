using TMPro;
using UnityEngine;

namespace FlappyCube {
	public class GameoverScoreView: MonoBehaviour {
		[SerializeField] private string _prefix = "Score: ";
		[SerializeField] private ScoreCounter _scoreCounter;
		[SerializeField] private GameBehaviour _gameBehaviour;
		[SerializeField] private TMP_Text _uiText;

		private void ShowScore() {
			_uiText.text = _prefix + _scoreCounter.Score.ToString();
		}

		protected void OnEnable() {
			_gameBehaviour.GameEnded += ShowScore;
			ShowScore();
		}
		protected void OnDisable() {
			_gameBehaviour.GameEnded -= ShowScore;
		}
	}
}