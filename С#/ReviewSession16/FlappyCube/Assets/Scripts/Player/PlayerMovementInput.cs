using UnityEngine;
using UnityEngine.EventSystems;

namespace FlappyCube {
	public class PlayerMovementInput: MonoBehaviour {
		[SerializeField] private PlayerMovement _movement;

		protected void Update() {
			if (Input.GetMouseButtonDown(0)) {
                if (IsPointerOverGameObject() == false) {
                    _movement.Jump();
                }
			}
		}
        public bool IsPointerOverGameObject() {
            if (EventSystem.current.IsPointerOverGameObject()) {
                return true;
            }

            if (Input.touchCount > 0 && Input.touches[0].phase == TouchPhase.Began) {
                if (EventSystem.current.IsPointerOverGameObject(Input.touches[0].fingerId)) {
                    return true;
                }
            }

            return false;
        }
    }
}