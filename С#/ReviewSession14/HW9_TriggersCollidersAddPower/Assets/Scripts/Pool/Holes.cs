using UnityEngine;

public class Holes : MonoBehaviour
{
    [SerializeField] Vector3 _positionBlackBall;
    [SerializeField] GameObject _prefabBlackBall;

    private static Vector3 _staticPosBlackBall;
    private static GameObject _staticPrefabBlackBall;

    private void Start()
    {
        _staticPosBlackBall = _positionBlackBall;
        _staticPrefabBlackBall = _prefabBlackBall;
        ReturnBlackBall();
    }

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.CompareTag("WhiteBall"))
        {
            BlackBall.RemoveBallFromList(other.gameObject);
            Destroy(other.gameObject);
        }
        if (other.gameObject.CompareTag("BlackBall"))
        {
            Destroy(other.gameObject);
            ReturnBlackBall();
        }
    }

    public static void ReturnBlackBall()
    {
        GameObject _ball = Instantiate<GameObject>(_staticPrefabBlackBall);
        _ball.transform.position = _staticPosBlackBall;
    }
}
