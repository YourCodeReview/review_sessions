using TMPro;
using UnityEngine;

namespace FlappyCube {
	public class RecordView: MonoBehaviour {
		[SerializeField] private string _key = "score.record";
		[SerializeField] private string _prefix = "Best: ";
		[SerializeField] private TMP_Text _uiText;

		public void ShowRecord() {
			_uiText.text = _prefix + PlayerPrefs.GetInt(_key, 0).ToString();
		}

		protected void OnEnable() {
			ShowRecord();
		}
	}
}