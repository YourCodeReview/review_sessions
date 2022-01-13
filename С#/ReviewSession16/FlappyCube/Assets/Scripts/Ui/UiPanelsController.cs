using UnityEngine;
using UnityEngine.UI;

namespace FlappyCube {
	public class UiPanelsController: MonoBehaviour {
		[SerializeField] private GameoverUiPanel _gameoverPanel;
		[SerializeField] private InGameUiPanel _inGamePanel;
		[SerializeField] private MenuUiPanel _menuPanel;
		[Space]
		[SerializeField] private GameBehaviour _gameBehaviour;

		private UiPanel _activePanel;

		protected void Start() {
			_gameBehaviour.GameStarted += ShowInGamePanel;
			_gameBehaviour.GameEnded += ShowGameoverPanel;

			SetActivePanel(_menuPanel);
		}

		private void SetActivePanel(UiPanel panel) {
			if (_activePanel != null) {
				_activePanel.Hide();
			}
			_activePanel = panel;
			_activePanel.Show();
		}
		private void ShowGameoverPanel() => SetActivePanel(_gameoverPanel);
		private void ShowInGamePanel() => SetActivePanel(_inGamePanel);
		private void ShowMenuPanel() => SetActivePanel(_menuPanel);

		protected void OnDestroy() {
			_gameBehaviour.GameStarted -= ShowInGamePanel;
			_gameBehaviour.GameEnded -= ShowGameoverPanel;
		}
	}
}