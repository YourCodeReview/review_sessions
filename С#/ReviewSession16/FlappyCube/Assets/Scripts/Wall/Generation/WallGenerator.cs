using System.Collections;
using UnityEngine;

namespace FlappyCube {
	public class WallGenerator: MonoBehaviour {
		[SerializeField] private float _spawnDelay = 2f;
		[SerializeField] private float _minHolePosition = 2f;
		[SerializeField] private float _maxHolePosition = 8f;
		[SerializeField] private Wall _wallPrefab;
		[SerializeField] private GameBehaviour _gameBehaviour;
		[SerializeField] private Transform _wallDestroyPoint;

		private int _wallSpawned = 0;
		private Coroutine _spawnLoop;

		protected void Start() {
			_gameBehaviour.GameStarted += StartGeneration;
		}

		private void StartGeneration() {
			_gameBehaviour.GameStarted -= StartGeneration;
			_gameBehaviour.GameEnded += StopGeneration;
			_spawnLoop = StartCoroutine(WallSpawnLoop());
		}
		private void StopGeneration() {
			_gameBehaviour.GameStarted += StartGeneration;
			_gameBehaviour.GameEnded -= StopGeneration;
			StopCoroutine(_spawnLoop);
		}

		private IEnumerator WallSpawnLoop() {
			while(true) {
				var holePosition = Random.Range(_minHolePosition, _maxHolePosition);
				var wall = Instantiate(_wallPrefab, transform);
				wall.Init(_gameBehaviour, holePosition, _wallDestroyPoint);
				_wallSpawned++;
				yield return new WaitForSeconds(_spawnDelay);
			}
		}

		protected void OnDestroy() {
			_gameBehaviour.GameStarted -= StartGeneration;
			_gameBehaviour.GameEnded -= StopGeneration;
		}
	}
}