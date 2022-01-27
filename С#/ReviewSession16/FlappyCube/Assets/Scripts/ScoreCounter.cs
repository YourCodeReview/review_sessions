using System;
using UnityEngine;

namespace FlappyCube {
	public class ScoreCounter: MonoBehaviour {
		[SerializeField] private string _key = "score.record";
		[Space]
		[SerializeField] private GameBehaviour _gameBehaviour;
		[SerializeField] private WallPassHandler _wallPassHandler;

		public int Score { get; private set; }

		public event Action<int> Changed;

		protected void Start() {
			_wallPassHandler.Passed += AddScorePoint;
			_gameBehaviour.GameEnded += SaveScore;
		}

		private void SaveScore() {
			var currentRecord = PlayerPrefs.GetInt(_key, 0);
			var newRecord = Mathf.Max(currentRecord, Score);
			PlayerPrefs.SetInt(_key, newRecord);
		}
		private void AddScorePoint() {
			Score += 1;
			Changed?.Invoke(Score);
		}

		protected void OnDestroy() {
			_wallPassHandler.Passed -= AddScorePoint;
			_gameBehaviour.GameEnded -= SaveScore;
		}
	}
}