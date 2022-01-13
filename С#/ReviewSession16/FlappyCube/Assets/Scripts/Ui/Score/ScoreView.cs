using TMPro;
using UnityEngine;

namespace FlappyCube {
	public class ScoreView: MonoBehaviour {
		[SerializeField] private string _prefix = "Score: ";
		[SerializeField] private ScoreCounter _scoreCounter;
		[SerializeField] private TMP_Text _uiText;

		private void UpdateScoreText(int score) { 
			_uiText.text = _prefix + score.ToString(); 
		}

		protected void OnEnable() {
			_scoreCounter.Changed += UpdateScoreText;
		}
		protected void OnDisable() {
			_scoreCounter.Changed -= UpdateScoreText;
		}
	}
}